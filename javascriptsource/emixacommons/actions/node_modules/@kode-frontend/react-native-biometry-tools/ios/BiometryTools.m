#import "BiometryTools.h"

@implementation BiometryTools

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(getSupportedBiometryType:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  NSError *aerr = nil;
  LAContext *context = [LAContext new];
  BOOL canBeProtected = [context canEvaluatePolicy:LAPolicyDeviceOwnerAuthentication error:&aerr];

  if (!aerr && canBeProtected) {
    return resolve([self getBiometryType:context]);
  }

  return resolve([NSNull null]);
}

RCT_EXPORT_METHOD(isSensorAvailable:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    LAContext *context = [[LAContext alloc] init];
    NSError *error;

    if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
        resolve([self getBiometryType:context]);
    } else {
        NSString *code;
        NSString *message;

        switch (error.code) {
            case LAErrorBiometryNotAvailable:
                code = @"BiometryScannerNotAvailable";
                message = @"Biometry scanner is not available";
                break;
                
            case LAErrorBiometryNotEnrolled:
                code = @"BiometryScannerNotEnrolled";
                message = @"Biometry scanner is not enrolled";
                break;

            case LAErrorBiometryLockout:
                code = @"DeviceLockedPermanent";
                message = @"Device locked permanent";
                break;
            
            case LAErrorPasscodeNotSet:
                code = @"PasscodeNotSet";
                message = @"Passcode not set";
                break;

            default:
                code = @"BiometryScannerNotSupported";
                message = @"Biometry scanner is not supported";
                break;
        }

        reject(code, message, nil);
        return;
    }
}

- (NSString *)getBiometryType:(LAContext *)context
{
    if (@available(iOS 11, *)) {
        return context.biometryType == LABiometryTypeFaceID ? @"Face ID" : @"Touch ID";
    }

    return @"Touch ID";
}

@end
