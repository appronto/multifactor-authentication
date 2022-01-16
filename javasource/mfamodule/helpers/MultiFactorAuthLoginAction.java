package mfamodule.helpers;

import java.util.Map;
import java.util.UUID;

import com.mendix.core.Core;
import com.mendix.core.action.user.LoginAction;
import com.mendix.logging.ILogNode;
import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.systemwideinterfaces.core.AuthenticationRuntimeException;
import com.mendix.systemwideinterfaces.core.UserBlockedException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.ISession;
import com.mendix.systemwideinterfaces.core.IUser;

import mfamodule.proxies.MFA;


public class MultiFactorAuthLoginAction extends LoginAction{
	private final static ILogNode _logNode = Core.getLogger("MFAmodule");

	private String userName;
	private String password;
	private IMxRuntimeRequest request;
	private String currentSessionId;
	public final static String USER_NAME_PARAM = "userName";
	public final static String PASSWORD_PARAM = "password";
	private Map<String, ? extends Object> params;
	

	public MultiFactorAuthLoginAction(Map<String, ? extends Object> params) {
		super(Core.createSystemContext(), params);
		this.params = params;
		this.userName = (String) params.get(USER_NAME_PARAM);
		this.password = (String) params.get(PASSWORD_PARAM);
		this.currentSessionId = (String)params.get("currentSessionId");
	    this.request = (IMxRuntimeRequest)params.get("request");   
	}

	@Override
	public ISession execute() throws Exception {
		
		//
		//	if you have existing java code which extends the LoginAction copy the code from that action below
		//
		IContext sysContext = Core.createSystemContext();
		ISession oldSession = null;
		MFA userMfaObj = null;
		
		if(currentSessionId != null && currentSessionId != "") {			
			oldSession = Core.getSessionById(UUID.fromString(currentSessionId));
			if(oldSession != null) {
				//check if there is mfa for the user from the first call
				userMfaObj = mfamodule.proxies.microflows.Microflows.dS_MFA_GET(oldSession.createContext());
			
				if(userMfaObj != null && userMfaObj.getMendixObject().getMember(oldSession.createContext(),"Username").hasReadAccess(oldSession.createContext()) && userMfaObj.getUsername() != null ) {
					if ( mfamodule.proxies.microflows.Microflows.sUB_MFA_Validate(oldSession.createContext(), userMfaObj, userMfaObj.getUsername()) ) {
						IUser user = Core.getUser(sysContext, userMfaObj.getUsername());
						return Core.initializeSession(user, this.currentSessionId);
					} 
					else {
						return oldSession;
					}
				}
			}
		}
		
		//Do not expose the specific errormessage.
		IUser user = Core.getUser(sysContext, this.userName);
		if (user == null) {
			_logNode.debug( "Custom Login FAILED: unknown user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException( "Custom Login FAILED for user '" + this.userName + "'." );
		}
		else if (user.isWebserviceUser()) {
			_logNode.debug( "Custom Login FAILED: client login attempt for web service user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isAnonymous()) {
			_logNode.debug( "Custom Login FAILED: client login attempt for Anonymous user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isActive() == false) {
			_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' is not active." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isBlocked() == true) {
			_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' is blocked." );
			throw new UserBlockedException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.getUserRoleNames().isEmpty()) {
			_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' does not have any user roles." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if( !Core.authenticate(sysContext, user, this.password)) {	
			Object obj = (Integer)user.getMendixObject().getValue(sysContext,"FailedLogins")+1;
			user.getMendixObject().setValue(sysContext,"FailedLogins",obj);
			if ( (Integer)user.getMendixObject().getValue(sysContext,"FailedLogins") >= mfamodule.proxies.constants.Constants.getMaxLoginAttempts()) {
				user.getMendixObject().setValue(sysContext,"Blocked",true);
				Core.commit(sysContext, user.getMendixObject());
				_logNode.debug( "Custom Login FAILED:  user '" + this.userName + "' blocked" );
				Core.getLogger("Core").info( "User blocked: '" + this.userName + "'" );
				if (oldSession != null) { Core.logout(oldSession); }
				throw new UserBlockedException("Custom login: User '"+ this.userName + "' blocked");
			}
			Core.commit(sysContext, user.getMendixObject());
			_logNode.debug( "Custom Login FAILED: invalid password for user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}

		// from this point user+pass is validated:
		if ( oldSession != null && mfamodule.proxies.constants.Constants.getEnabledMFA() 
				&& mfamodule.proxies.microflows.Microflows.sUB_MFA_Validate(oldSession.createContext(), userMfaObj, this.userName) ) {
			
				if( !mfamodule.proxies.microflows.Microflows.sUB_MFA_UserDisabledCheck(sysContext, this.userName) ) {
					_logNode.debug("MFA enabled and Mendix session available ready for "+ this.userName);
					return oldSession;
				}
				else {
					_logNode.debug("MFA enabled and Mendix session available redirect for "+ this.userName);
					return super.execute();
				}
		}
		else if( mfamodule.proxies.constants.Constants.getEnabledMFA() 
				&& !mfamodule.proxies.microflows.Microflows.sUB_MFA_UserDisabledCheck(sysContext, this.userName) ) {
			//login call without a session is from the default login.html. can't do anything with that.
			_logNode.info("No session found for request. MFA is enabled in the application and enabled for "+ this.userName);
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else {
			_logNode.debug("MFA disabled or disabled for user so create a session for the user here "+ this.userName);
			return super.execute();
		}
	}
}
