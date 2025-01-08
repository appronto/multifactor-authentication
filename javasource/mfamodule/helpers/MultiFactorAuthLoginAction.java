package mfamodule.helpers;

import java.util.Map;
import java.util.UUID;

import com.mendix.core.Core;
import com.mendix.core.action.user.LoginAction;
import com.mendix.core.conf.Configuration;
import com.mendix.logging.ILogNode;
import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.systemwideinterfaces.core.AuthenticationRuntimeException;
import com.mendix.systemwideinterfaces.core.UserBlockedException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.ISession;
import com.mendix.systemwideinterfaces.core.IUser;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import mfamodule.proxies.DatadogLog;
import mfamodule.proxies.DatadogOutcome_Enum;
import mfamodule.proxies.MFA;
import mfamodule.proxies.UserHelper;
import mfamodule.proxies.microflows.Microflows;


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
		
		DatadogLog ddlog = new DatadogLog(getContext());
		String remoteAddress = request.getRemoteAddr();
		String forwardedFor = request.getHeader("X-Forwarded-For");
		String realIP = request.getHeader("X-Real-IP"); // This is the header which should be used to check the IP address range.	 			
		if( realIP == null )
			realIP = forwardedFor;
		if( realIP == null )
			realIP = remoteAddress;
		
		ddlog.setnetwork_client_ip(realIP);
		String useragent = request.getHeader("User-Agent");
		ddlog.sethttp_useragent(useragent);
		
		if(currentSessionId != null && currentSessionId != "") {			
			oldSession = Core.getSessionById(UUID.fromString(currentSessionId));
			if(oldSession != null) {
				//check if there is mfa for the user from the first call
				userMfaObj = mfamodule.proxies.microflows.Microflows.dS_MFA_GET(oldSession.createContext());
				userMfaObj.setIPaddress(sysContext, realIP);
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
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "unknown user", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 
			//_logNode.debug( "Custom Login FAILED: unknown user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException( "Custom Login FAILED for user '" + this.userName + "'." );
		}
		else if (user.isWebserviceUser()) {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "client login attempt for web service user", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 
			//_logNode.debug( "Custom Login FAILED: client login attempt for web service user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isAnonymous()) {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "client login attempt for Anonymous user", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 
			//_logNode.debug( "Custom Login FAILED: client login attempt for Anonymous user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isActive() == false) {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user is not active", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

			//_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' is not active." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.isBlocked() == true) {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user blocked", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

			//_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' is blocked." );
			throw new UserBlockedException("Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if (user.getUserRoleNames().isEmpty()) {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user does not have any user roles", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

			//_logNode.debug( "Custom Login FAILED: user '" + this.userName + "' does not have any user roles." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else if( !Core.authenticate(sysContext, user, this.password)) {	
			//NO MFA for user:
			if( mfamodule.proxies.microflows.Microflows.sUB_MFA_UserDisabledCheck(sysContext, this.userName) ) {
				Object obj = (Integer)user.getMendixObject().getValue(sysContext,"FailedLogins")+1;
				user.getMendixObject().setValue(sysContext,"FailedLogins",obj);
				if ( (Integer)user.getMendixObject().getValue(sysContext,"FailedLogins") >= mfamodule.proxies.constants.Constants.getMaxLoginAttempts()) {
					user.getMendixObject().setValue(sysContext,"Blocked",true);			
					if(!Configuration.RUNTIME_VERSION.toString().startsWith("8")) {
						user.getMendixObject().setValue(sysContext,"BlockedSince", LocalDateTime.now(ZoneOffset.UTC));
					}
					Core.commit(sysContext, user.getMendixObject());
					mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user blocked", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

					//_logNode.debug( "Custom Login FAILED:  user '" + this.userName + "' blocked" );
					//Core.getLogger("Core").info( "Custom Login FAILED:  user '" + this.userName + "' blocked" );
					if (oldSession != null) { Core.logout(oldSession); }
					throw new UserBlockedException("Custom Login FAILED:  user '" + this.userName + "' blocked");
				}
				user.getMendixObject().setValue(sysContext,"BlockedSince", new java.util.Date());
				Core.commit(sysContext, user.getMendixObject());
				
			}
			//MFA for user, use own block check
			else {
				UserHelper userHelper = mfamodule.proxies.microflows.Microflows.sUB_MFA_GetUserHelper(getContext(), userMfaObj, this.userName);
				if( !mfamodule.proxies.microflows.Microflows.sUB_CheckMaxLogin(getContext(), userHelper)) {
					mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user blocked", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

					//_logNode.debug( "Custom Login FAILED:  user '" + this.userName + "' blocked" );
					//Core.getLogger("Core").info( "Custom Login FAILED:  user '" + this.userName + "' blocked" );
					throw new UserBlockedException("Custom Login FAILED:  user '" + this.userName + "' blocked");
				}
			}
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "invalid password", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 

			//_logNode.debug( "Custom Login FAILED: invalid password for user '" + this.userName + "'." );
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
			
			
		}

		// from this point user+pass is validated:
		if ( oldSession != null && mfamodule.proxies.constants.Constants.getEnabledMFA() 
				&& mfamodule.proxies.microflows.Microflows.sUB_MFA_Validate(oldSession.createContext(), userMfaObj, this.userName) ) {
			
				if( !mfamodule.proxies.microflows.Microflows.sUB_MFA_UserDisabledCheck(sysContext, this.userName) ) {
					mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user login with MFA", DatadogOutcome_Enum.success, userName, null, "authentication",ddlog,realIP); 
					//_logNode.debug("MFA enabled and Mendix session available ready for "+ this.userName);
					return oldSession;
				}
				else {
					mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user login with MFA redirect", DatadogOutcome_Enum.success, userName, null, "authentication",ddlog,realIP); 
	
					//_logNode.debug("MFA enabled and Mendix session available redirect for "+ this.userName);
					return super.execute();
				}
		}
		else if( mfamodule.proxies.constants.Constants.getEnabledMFA() 
				&& !mfamodule.proxies.microflows.Microflows.sUB_MFA_UserDisabledCheck(sysContext, this.userName) ) {
			//login call without a session is from the default login.html. can't do anything with that.
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "MFA enabled but no session found", DatadogOutcome_Enum.failure, userName, null, "authentication",ddlog,realIP); 
			_logNode.debug("No session found for request. MFA is enabled in the application and enabled for "+ this.userName);
			throw new AuthenticationRuntimeException(" Custom Login FAILED for user '" + this.userName + "'.");
		}
		else {
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "user login without MFA", DatadogOutcome_Enum.success, userName, null, "authentication",ddlog,realIP); 
			mfamodule.proxies.microflows.Microflows.sUB_DatadogLog_Default(sysContext, "MFA disabled or disabled for user, login ok", DatadogOutcome_Enum.success, userName, null, "authentication",ddlog,realIP); 
			
			_logNode.debug("MFA disabled or disabled for user so create a session for the user here "+ this.userName);
			return super.execute();
		}
	}
}
