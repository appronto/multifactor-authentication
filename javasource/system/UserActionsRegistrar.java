package system;

import com.mendix.core.actionmanagement.IActionRegistrator;

public class UserActionsRegistrar
{
  public void registerActions(IActionRegistrator registrator)
  {
    registrator.bundleComponentLoaded();
    registrator.registerUserAction(googleauthenticator.actions.CreateCredentials.class);
    registrator.registerUserAction(googleauthenticator.actions.IsCodeValid.class);
    registrator.registerUserAction(mfamodule.actions.JA_GetAdminUserName.class);
    registrator.registerUserAction(mfamodule.actions.OverwriteDefaultLoginAction.class);
    registrator.registerUserAction(mfamodule.actions.ThrowException.class);
    registrator.registerUserAction(myfirstmodule.actions.HasMFAatribute.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
    registrator.registerUserAction(twilioconnector.actions.DecryptString.class);
    registrator.registerUserAction(twilioconnector.actions.EncryptString.class);
    registrator.registerUserAction(twilioconnector.actions.SendSMS_JA.class);
  }
}
