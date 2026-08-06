'use strict';

const crypto = require('crypto');
const {
  DEFAULT_NSE_CURRENT_PROJECT_VERSION,
  DEFAULT_NSE_MARKETING_VERSION,
} = require('./initNseCore');

function patchXcodeProjectForNotifyKitNse(proj, options) {
  const {
    targetName,
    bundleIdentifier,
    parentTargetName,
    deploymentTarget = '15.1',
    marketingVersion = DEFAULT_NSE_MARKETING_VERSION,
    currentProjectVersion = DEFAULT_NSE_CURRENT_PROJECT_VERSION,
  } = options;
  const warnings = [];
  const existingTargetUuid = findTargetByName(proj, targetName);

  if (existingTargetUuid) {
    const didChange = setBuildSettings(
      proj,
      existingTargetUuid,
      targetName,
      bundleIdentifier,
      deploymentTarget,
      marketingVersion,
      currentProjectVersion,
    );

    if (!didChange) {
      return { didChange: false, warnings };
    }

    return {
      didChange: true,
      targetUuid: existingTargetUuid,
      hostTargetUuid: findHostTarget(proj, parentTargetName) ?? undefined,
      warnings,
    };
  }

  const target = proj.addTarget(targetName, 'app_extension', targetName);

  if (!target || !target.uuid) {
    throw new Error(`xcode library failed to create target '${targetName}'`);
  }

  const targetUuid = target.uuid;
  const productUuid = getTargetProductUuid(target);

  fixProductFileReference(proj, targetName);

  proj.addBuildPhase([], 'PBXSourcesBuildPhase', 'Sources', targetUuid);
  proj.addBuildPhase([], 'PBXFrameworksBuildPhase', 'Frameworks', targetUuid);
  proj.addBuildPhase([], 'PBXResourcesBuildPhase', 'Resources', targetUuid);

  const group = proj.addPbxGroup([], targetName, targetName);
  proj.addSourceFile(
    `${targetName}/NotificationService.swift`,
    {
      target: targetUuid,
    },
    group.uuid,
  );

  const hostUuid = findHostTarget(proj, parentTargetName);
  if (hostUuid) {
    addTargetDependencyManual(proj, hostUuid, targetUuid, targetName);
    stripRnfbInfoPlistInputPath(proj, hostUuid);
  }

  setBuildSettings(
    proj,
    targetUuid,
    targetName,
    bundleIdentifier,
    deploymentTarget,
    marketingVersion,
    currentProjectVersion,
  );

  return {
    didChange: true,
    targetUuid,
    productUuid,
    hostTargetUuid: hostUuid ?? undefined,
    warnings,
  };
}

function findHostTarget(proj, parentTargetName) {
  if (parentTargetName) {
    return findTargetByName(proj, parentTargetName);
  }

  const targets = proj.pbxNativeTargetSection();
  for (const [key, value] of Object.entries(targets)) {
    if (typeof value !== 'object' || key.endsWith('_comment')) continue;
    const target = value;
    const productType = String(target.productType ?? '');
    if (productType.includes('application')) {
      return key;
    }
  }
  return null;
}

function findTargetByName(proj, name) {
  const targets = proj.pbxNativeTargetSection();
  for (const [key, value] of Object.entries(targets)) {
    if (typeof value !== 'object') continue;
    const target = value;
    const targetName = String(target.name ?? '').replace(/"/g, '');
    if (targetName === name) {
      return key;
    }
  }
  return null;
}

function getTargetProductUuid(target) {
  const productReference = target.pbxNativeTarget && target.pbxNativeTarget.productReference;
  return typeof productReference === 'string' ? productReference : undefined;
}

function setBuildSettings(
  proj,
  targetUuid,
  targetName,
  bundleId,
  deploymentTarget,
  marketingVersion,
  currentProjectVersion,
) {
  let didChange = false;

  for (const settings of getTargetBuildSettings(proj, targetUuid, targetName)) {
    didChange =
      setBuildSetting(settings, 'INFOPLIST_FILE', `"${targetName}/Info.plist"`) || didChange;
    didChange =
      setBuildSetting(settings, 'PRODUCT_BUNDLE_IDENTIFIER', `"${bundleId}"`) || didChange;
    didChange = setBuildSetting(settings, 'TARGETED_DEVICE_FAMILY', `"1,2"`) || didChange;
    didChange =
      setBuildSetting(settings, 'IPHONEOS_DEPLOYMENT_TARGET', deploymentTarget) || didChange;
    didChange = setBuildSetting(settings, 'MARKETING_VERSION', marketingVersion) || didChange;
    didChange =
      setBuildSetting(settings, 'CURRENT_PROJECT_VERSION', currentProjectVersion) || didChange;
    didChange = setBuildSetting(settings, 'SWIFT_VERSION', '5.0') || didChange;
    didChange =
      setBuildSetting(
        settings,
        'CODE_SIGN_ENTITLEMENTS',
        `"${targetName}/${targetName}.entitlements"`,
      ) || didChange;
    didChange = setBuildSetting(settings, 'GENERATE_INFOPLIST_FILE', 'NO') || didChange;
  }

  return didChange;
}

function getTargetBuildSettings(proj, targetUuid, targetName) {
  const target = proj.pbxNativeTargetSection()[targetUuid];
  const buildConfigurationListUuid = target && target.buildConfigurationList;
  const configs = proj.pbxXCBuildConfigurationSection();
  const settingsByConfigurationList = getBuildSettingsFromConfigurationList(
    proj,
    typeof buildConfigurationListUuid === 'string' ? buildConfigurationListUuid : undefined,
    configs,
  );

  if (settingsByConfigurationList.length > 0) {
    return settingsByConfigurationList;
  }

  return Object.entries(configs)
    .filter(([, value]) => typeof value === 'object' && value !== null)
    .map(([, value]) => value)
    .filter(config => {
      const settings = config.buildSettings;
      return settings?.PRODUCT_NAME === `"${targetName}"`;
    })
    .map(config => config.buildSettings);
}

function getBuildSettingsFromConfigurationList(proj, buildConfigurationListUuid, configs) {
  if (!buildConfigurationListUuid) {
    return [];
  }

  const configurationLists = proj.hash.project.objects.XCConfigurationList;
  const configurationList = configurationLists && configurationLists[buildConfigurationListUuid];
  const buildConfigurations = Array.isArray(configurationList?.buildConfigurations)
    ? configurationList.buildConfigurations
    : [];

  return buildConfigurations
    .map(ref => ref?.value)
    .filter(uuid => typeof uuid === 'string')
    .map(uuid => configs[uuid])
    .filter(config => typeof config === 'object' && config !== null)
    .map(config => {
      if (typeof config.buildSettings !== 'object' || config.buildSettings === null) {
        config.buildSettings = {};
      }
      return config.buildSettings;
    });
}

function setBuildSetting(settings, key, value) {
  if (settings[key] === value) {
    return false;
  }

  settings[key] = value;
  return true;
}

function fixProductFileReference(proj, targetName) {
  const fileRefs = proj.hash.project.objects.PBXFileReference;
  if (!fileRefs) return;
  for (const [, value] of Object.entries(fileRefs)) {
    if (typeof value !== 'object') continue;
    const ref = value;
    if (String(ref.name ?? '').replace(/"/g, '') === `${targetName}.appex`) {
      delete ref.fileEncoding;
      delete ref.lastKnownFileType;
    }
  }
}

function genUuid() {
  return crypto.randomBytes(12).toString('hex').toUpperCase().slice(0, 24);
}

function addTargetDependencyManual(proj, hostUuid, extensionUuid, extensionName) {
  const proxyUuid = genUuid();
  const depUuid = genUuid();

  const objects = proj.hash.project.objects;

  const rootObject = proj.hash.project.rootObject;

  if (!objects.PBXContainerItemProxy) objects.PBXContainerItemProxy = {};
  objects.PBXContainerItemProxy[proxyUuid] = {
    isa: 'PBXContainerItemProxy',
    containerPortal: rootObject,
    proxyType: 1,
    remoteGlobalIDString: extensionUuid,
    remoteInfo: `"${extensionName}"`,
  };
  objects.PBXContainerItemProxy[`${proxyUuid}_comment`] = 'PBXContainerItemProxy';

  if (!objects.PBXTargetDependency) objects.PBXTargetDependency = {};
  objects.PBXTargetDependency[depUuid] = {
    isa: 'PBXTargetDependency',
    target: extensionUuid,
    targetProxy: proxyUuid,
  };
  objects.PBXTargetDependency[`${depUuid}_comment`] = 'PBXTargetDependency';

  const hostTarget = objects.PBXNativeTarget[hostUuid];
  if (hostTarget && Array.isArray(hostTarget.dependencies)) {
    hostTarget.dependencies.push({ value: depUuid, comment: 'PBXTargetDependency' });
  }
}

function stripRnfbInfoPlistInputPath(proj, hostUuid) {
  const objects = proj.hash.project.objects;
  const hostTarget = objects.PBXNativeTarget?.[hostUuid];
  const shellPhases = objects.PBXShellScriptBuildPhase;

  if (!hostTarget || !Array.isArray(hostTarget.buildPhases) || !shellPhases) {
    return;
  }

  for (const phaseRef of hostTarget.buildPhases) {
    const phaseUuid = phaseRef?.value;
    if (!phaseUuid) continue;

    const phase = shellPhases[phaseUuid];
    if (!phase) continue;

    const phaseName = String(phase.name ?? '').replace(/"/g, '');
    if (phaseName !== '[CP-User] [RNFB] Core Configuration') {
      continue;
    }

    const inputPaths = Array.isArray(phase.inputPaths) ? phase.inputPaths : [];
    const filteredInputPaths = inputPaths.filter(
      entry => String(entry) !== '"$(BUILT_PRODUCTS_DIR)/$(INFOPLIST_PATH)"',
    );

    if (filteredInputPaths.length > 0) {
      phase.inputPaths = filteredInputPaths;
    } else {
      delete phase.inputPaths;
    }
  }
}

module.exports = {
  patchXcodeProjectForNotifyKitNse,
};
