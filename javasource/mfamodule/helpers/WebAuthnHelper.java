package mfamodule.helpers;

import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredentialType;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.ByteArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Optional;

/**
 * Helper class for common WebAuthn operations to improve code reusability and readability
 */
public class WebAuthnHelper {
    
    /**
     * Ensures the RelyingParty is initialized, throws exception if not
     */
    public static void ensureRelyingPartyInitialized() {
        if (!WebAuthnConfig.isRelyingPartyInitialized()) {
            throw new IllegalStateException("WebAuthn RelyingParty is not initialized. Please call CreateWebAuthnRelyingParty first.");
        }
    }
    
    /**
     * Parses a registration credential from JSON
     */
    public static PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> 
            parseRegistrationCredential(JsonNode credentialJson) throws Exception {
        
        validateCredentialJson(credentialJson, "registration");
        
        return PublicKeyCredential.<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs>builder()
            .id(decodeBase64UrlToByteArray(credentialJson.get("id").asText()))
            .response(AuthenticatorAttestationResponse.builder()
                .attestationObject(decodeBase64UrlToByteArray(credentialJson.get("response").get("attestationObject").asText()))
                .clientDataJSON(decodeBase64UrlToByteArray(credentialJson.get("response").get("clientDataJSON").asText()))
                .build())
            .clientExtensionResults(ClientRegistrationExtensionOutputs.builder().build())
            .type(parseCredentialType(credentialJson.get("type").asText()))
            .build();
    }
    
    /**
     * Parses an authentication credential from JSON
     */
    public static PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> 
            parseAuthenticationCredential(JsonNode credentialJson) throws Exception {
        
        validateCredentialJson(credentialJson, "authentication");
        
        // Handle optional userHandle
        Optional<ByteArray> userHandle = Optional.empty();
        JsonNode userHandleNode = credentialJson.get("response").get("userHandle");
        if (userHandleNode != null && !userHandleNode.isNull() && !userHandleNode.asText().isEmpty()) {
            userHandle = Optional.of(decodeBase64UrlToByteArray(userHandleNode.asText()));
        }
        
        return PublicKeyCredential.<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs>builder()
            .id(decodeBase64UrlToByteArray(credentialJson.get("id").asText()))
            .response(AuthenticatorAssertionResponse.builder()
                .authenticatorData(decodeBase64UrlToByteArray(credentialJson.get("response").get("authenticatorData").asText()))
                .clientDataJSON(decodeBase64UrlToByteArray(credentialJson.get("response").get("clientDataJSON").asText()))
                .signature(decodeBase64UrlToByteArray(credentialJson.get("response").get("signature").asText()))
                .userHandle(userHandle)
                .build())
            .clientExtensionResults(ClientAssertionExtensionOutputs.builder().build())
            .type(parseCredentialType(credentialJson.get("type").asText()))
            .build();
    }
    
    /**
     * Parses a registration request from stored JSON
     */
    public static PublicKeyCredentialCreationOptions parseRegistrationRequest(String requestJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(requestJson);
        JsonNode publicKeyNode = root.get("publicKey");
        
        if (publicKeyNode == null) {
            throw new IllegalArgumentException("Missing 'publicKey' key in request JSON");
        }
        
        return mapper.treeToValue(publicKeyNode, PublicKeyCredentialCreationOptions.class);
    }
    
    /**
     * Validates that the credential JSON has required fields
     */
    private static void validateCredentialJson(JsonNode credentialJson, String credentialType) {
        if (credentialJson == null) {
            throw new IllegalArgumentException("Credential JSON cannot be null");
        }
        if (!credentialJson.has("id")) {
            throw new IllegalArgumentException("Missing 'id' field in " + credentialType + " credential");
        }
        if (!credentialJson.has("type")) {
            throw new IllegalArgumentException("Missing 'type' field in " + credentialType + " credential");
        }
        if (!credentialJson.has("response")) {
            throw new IllegalArgumentException("Missing 'response' field in " + credentialType + " credential");
        }
    }
    
    /**
     * Decodes Base64 URL to ByteArray with proper error handling
     */
    private static ByteArray decodeBase64UrlToByteArray(String base64Url) {
        if (base64Url == null || base64Url.trim().isEmpty()) {
            throw new IllegalArgumentException("Base64 URL string cannot be null or empty");
        }
        try {
            return new ByteArray(Base64.getUrlDecoder().decode(base64Url));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 URL encoding: " + base64Url, e);
        }
    }
    
    /**
     * Parses credential type with proper error handling
     */
    private static PublicKeyCredentialType parseCredentialType(String typeString) {
        return PublicKeyCredentialType.fromId(typeString)
            .orElseThrow(() -> new IllegalArgumentException("Invalid credential type: " + typeString));
    }
    
    /**
     * Encodes ByteArray to Base64 URL without padding
     */
    public static String encodeToBase64Url(ByteArray byteArray) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteArray.getBytes());
    }
    
    /**
     * Safely converts string to ByteArray using UTF-8
     */
    public static ByteArray stringToByteArray(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        return new ByteArray(str.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
    
    /**
     * Safely converts ByteArray to string using UTF-8
     */
    public static String byteArrayToString(ByteArray byteArray) {
        if (byteArray == null) {
            throw new IllegalArgumentException("ByteArray cannot be null");
        }
        return new String(byteArray.getBytes(), java.nio.charset.StandardCharsets.UTF_8);
    }
}