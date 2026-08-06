package com.reactnativebiometrytools;

import androidx.annotation.NonNull;
import android.content.pm.PackageManager;
import androidx.biometric.BiometricManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = BiometryToolsModule.NAME)
public class BiometryToolsModule extends ReactContextBaseJavaModule {
    private static final String NONE = null;
    private static final String FINGERPRINT = "Fingerprint";
    private static final String FACE = "Face";
    private static final String IRIS = "Iris";

    public static final String NAME = "BiometryTools";

    public BiometryToolsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    @ReactMethod
    public void getSupportedBiometryType(@NonNull final Promise promise) {
        promise.resolve(getAvailableFeature());
    }

    @ReactMethod
    public void isSensorAvailable(@NonNull final Promise promise) {
        BiometricManager biometricManager;
        String biometrySupportedType = getAvailableFeature();

        if (biometrySupportedType != NONE){

            biometricManager = BiometricManager.from(getReactApplicationContext());

            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    promise.resolve(biometrySupportedType);
                    break;
                default:
                    promise.reject("BiometryScannerNotEnrolled", "Biometry scanner is not enrolled");
                    break;
            }

        } else {
            promise.reject("BiometryScannerNotSupported", "Biometry scanner is not supported");
        }

    }

    private String getAvailableFeature() {
        if (getReactApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return FINGERPRINT;
        } if (getReactApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FACE)) {
            return FACE;
        } else if (getReactApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_IRIS)) {
            return IRIS;
        } else {
            return NONE;
        }
    }
}
