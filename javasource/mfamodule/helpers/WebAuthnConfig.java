package mfamodule.helpers;

import com.yubico.webauthn.RelyingParty;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Simple global configuration class for WebAuthn functionality in Mendix.
 * This class stores the RelyingParty instance globally using the singleton pattern.
 */
public class WebAuthnConfig {
    
	private static final String LOG_NODE = mfamodule.proxies.constants.Constants.getLognode(); 
    private static final ILogNode LOG = Core.getLogger(LOG_NODE);
    
    // Thread-safe singleton instance
    private static volatile WebAuthnConfig instance;
    private static final Object instanceLock = new Object();
    
    // RelyingParty instance and its lock for thread safety
    private volatile RelyingParty relyingParty;
    private final ReentrantReadWriteLock relyingPartyLock = new ReentrantReadWriteLock();
    
    // Private constructor to prevent direct instantiation
    private WebAuthnConfig() {
        LOG.debug("WebAuthnConfig instance created");
    }
    
    /**
     * Gets the singleton instance of WebAuthnConfig
     * @return WebAuthnConfig instance
     */
    public static WebAuthnConfig getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new WebAuthnConfig();
                }
            }
        }
        return instance;
    }
    
    /**
     * Sets the RelyingParty instance (thread-safe)
     * @param relyingParty RelyingParty instance to store
     */
    public void setRelyingParty(RelyingParty relyingParty) {
        relyingPartyLock.writeLock().lock();
        try {
            this.relyingParty = relyingParty;
            LOG.debug("RelyingParty instance stored in WebAuthnConfig");
        } finally {
            relyingPartyLock.writeLock().unlock();
        }
    }
    
    /**
     * Gets the RelyingParty instance (thread-safe)
     * @return RelyingParty instance
     * @throws IllegalStateException if RelyingParty is not set
     */
    public RelyingParty getRelyingParty() {
        relyingPartyLock.readLock().lock();
        try {
            if (relyingParty == null) {
                throw new IllegalStateException("RelyingParty not initialized. Call CreateWebAuthnRelyingParty first.");
            }
            return relyingParty;
        } finally {
            relyingPartyLock.readLock().unlock();
        }
    }
    
    /**
     * Checks if the RelyingParty is initialized
     * @return true if RelyingParty is set, false otherwise
     */
    public boolean isInitialized() {
        relyingPartyLock.readLock().lock();
        try {
            return relyingParty != null;
        } finally {
            relyingPartyLock.readLock().unlock();
        }
    }
    
    /**
     * Static convenience method to set the RelyingParty
     * @param relyingParty RelyingParty instance
     */
    public static void setRelyingPartyInstance(RelyingParty relyingParty) {
        getInstance().setRelyingParty(relyingParty);
    }
    
    /**
     * Static convenience method to get the RelyingParty instance
     * @return RelyingParty instance
     * @throws IllegalStateException if not initialized
     */
    public static RelyingParty getRelyingPartyInstance() {
        return getInstance().getRelyingParty();
    }
    
    /**
     * Static convenience method to check if initialized
     * @return true if initialized, false otherwise
     */
    public static boolean isRelyingPartyInitialized() {
        return getInstance().isInitialized();
    }
    

}