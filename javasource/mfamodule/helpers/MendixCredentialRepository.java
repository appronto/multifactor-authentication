package mfamodule.helpers;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;

import mfamodule.proxies.UserCredential;

import java.util.Base64;
import java.util.Collections;
import java.util.Set;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;

/**
 * Mendix implementation of WebAuthn CredentialRepository
 */
public class MendixCredentialRepository implements CredentialRepository {
    
	private static final String LOG_NODE = mfamodule.proxies.constants.Constants.getLognode(); 
    private static final ILogNode LOG = Core.getLogger(LOG_NODE);
    
    private final IContext context;

    public MendixCredentialRepository(IContext context) {
        this.context = context;
    }
    
    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            LOG.warn("getCredentialIdsForUsername called with null/empty username");
            return Collections.emptySet();
        }
        
        try {
            List<UserCredential> credentials = mfamodule.proxies.microflows.Microflows.sUB_Username_GetUserCredentials(
                context, username.trim());
            
            Set<PublicKeyCredentialDescriptor> result = new HashSet<>();
            for (UserCredential cred : credentials) {
                String credentialIdBase64 = cred.getcredentialId(context);
                if (credentialIdBase64 != null && !credentialIdBase64.trim().isEmpty()) {
                    try {
                        byte[] idBytes = Base64.getUrlDecoder().decode(credentialIdBase64);
                        result.add(PublicKeyCredentialDescriptor.builder()
                            .id(new ByteArray(idBytes))
                            .build());
                    } catch (IllegalArgumentException e) {
                        LOG.error("Invalid Base64 credential ID for user " + username + ": " + credentialIdBase64, e);
                    }
                }
            }
            
            LOG.debug("Found " + result.size() + " credentials for username: " + username);
            return result;
            
        } catch (Exception e) {
            LOG.error("Failed to retrieve credentials for username: " + username, e);
            return Collections.emptySet();
        }
    }
    
    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        if (userHandle == null) {
            LOG.warn("getUsernameForUserHandle called with null userHandle");
            return Optional.empty();
        }
        
        try {
            String userHandleString = WebAuthnHelper.byteArrayToString(userHandle);
            String username = mfamodule.proxies.microflows.Microflows.sUB_UserHandle_GetUserName(
                context, userHandleString);
            
            if (username == null || username.trim().isEmpty()) {
                LOG.debug("No username found for userHandle: " + userHandleString);
                return Optional.empty();
            }
            
            LOG.debug("Found username " + username + " for userHandle: " + userHandleString);
            return Optional.of(username.trim());
            
        } catch (Exception e) {
            LOG.error("Failed to get username for userHandle", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            LOG.warn("getUserHandleForUsername called with null/empty username");
            return Optional.empty();
        }
        
        try {
            String userHandleString = mfamodule.proxies.microflows.Microflows.sUB_Username_GetUserHandle(
                context, username.trim());
            
            if (userHandleString == null || userHandleString.trim().isEmpty()) {
                LOG.debug("No userHandle found for username: " + username);
                return Optional.empty();
            }
            
            ByteArray userHandle = WebAuthnHelper.stringToByteArray(userHandleString);
            LOG.debug("Found userHandle for username " + username + ": " + userHandleString);
            return Optional.of(userHandle);
            
        } catch (Exception e) {
            LOG.error("Failed to get userHandle for username: " + username, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        try {
            UserCredential cred = findCredential(credentialId, userHandle);
            
            if (cred == null) {
                LOG.debug("No credential found for lookup");
                return Optional.empty();
            }
            
            RegisteredCredential registeredCredential = buildRegisteredCredential(cred);
            LOG.debug("Successfully retrieved credential for user: " + findUsername(cred));
            return Optional.of(registeredCredential);
            
        } catch (Exception e) {
            LOG.error("Failed to lookup credential", e);
            return Optional.empty();
        }
    }
    
    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        if (credentialId == null) {
            LOG.warn("lookupAll called with null credentialId");
            return Collections.emptySet();
        }
        
        try {
            String credentialIdBase64 = WebAuthnHelper.encodeToBase64Url(credentialId);
            List<UserCredential> credentials = mfamodule.proxies.microflows.Microflows.sUB_UserCredentials_RetrieveAllByCredentialId(
                context, credentialIdBase64);
            
            if (credentials.isEmpty()) {
                LOG.debug("No credentials found for credentialId: " + credentialIdBase64);
                return Collections.emptySet();
            }
            
            Set<RegisteredCredential> result = new HashSet<>();
            for (UserCredential cred : credentials) {
                try {
                    RegisteredCredential registeredCredential = buildRegisteredCredential(cred);
                    result.add(registeredCredential);
                } catch (Exception e) {
                    LOG.error("Failed to build RegisteredCredential for user: " + findUsername(cred), e);
                }
            }
            
            LOG.debug("Retrieved " + result.size() + " credentials for credentialId: " + credentialIdBase64);
            return result;
            
        } catch (Exception e) {
            LOG.error("Failed to lookup all credentials", e);
            return Collections.emptySet();
        }
    }
    
    /**
     * Finds a credential by credentialId or userHandle
     */
    private UserCredential findCredential(ByteArray credentialId, ByteArray userHandle) throws Exception {
        UserCredential cred = null;
        
        if (credentialId != null) {
            String credentialIdBase64 = WebAuthnHelper.encodeToBase64Url(credentialId);
            cred = mfamodule.proxies.microflows.Microflows.sUB_UserCredential_RetrieveByCredentialId(
                context, credentialIdBase64);
            LOG.debug("Searched by credentialId: " + credentialIdBase64);
        } 
        
        if (cred == null && userHandle != null) {
            String userHandleString = WebAuthnHelper.byteArrayToString(userHandle);
            cred = mfamodule.proxies.microflows.Microflows.sUB_UserCredential_RetrieveByUserHandle(
                context, userHandleString);
            LOG.debug("Searched by userHandle: " + userHandleString);
        }
        
        return cred;
    }
    
    
    /**
     * Finds a username for a userCredential
     */
    private String findUsername(UserCredential cred) throws Exception {
    	String username = mfamodule.proxies.microflows.Microflows.sUB_UserCredential_GetUserName(context, cred);
    	return username;
    }

    
    /**
     * Builds a RegisteredCredential from a UserCredential entity
     */
    private RegisteredCredential buildRegisteredCredential(UserCredential cred) throws Exception {
        if (cred == null) {
            throw new IllegalArgumentException("UserCredential cannot be null");
        }
        
        // Validate required fields
        String credentialIdBase64 = cred.getcredentialId(context);
        String userHandle = cred.getuserHandle(context);
        String publicKeyBase64 = cred.getpublicKey(context);
        Long signatureCount = cred.getsignatureCount(context);
        
        if (credentialIdBase64 == null || credentialIdBase64.trim().isEmpty()) {
            throw new IllegalStateException("Credential ID cannot be null or empty");
        }
        if (userHandle == null || userHandle.trim().isEmpty()) {
            throw new IllegalStateException("User handle cannot be null or empty");
        }
        if (publicKeyBase64 == null || publicKeyBase64.trim().isEmpty()) {
            throw new IllegalStateException("Public key cannot be null or empty");
        }
        if (signatureCount == null) {
            throw new IllegalStateException("Signature count cannot be null");
        }
        
        return RegisteredCredential.builder()
            .credentialId(new ByteArray(Base64.getUrlDecoder().decode(credentialIdBase64)))
            .userHandle(WebAuthnHelper.stringToByteArray(userHandle))
            .publicKeyCose(new ByteArray(Base64.getUrlDecoder().decode(publicKeyBase64)))
            .signatureCount(signatureCount.intValue())
            .build();
    }
}