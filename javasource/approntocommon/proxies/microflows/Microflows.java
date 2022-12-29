// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package approntocommon.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Microflows
{
	// These are the microflows for the ApprontoCommon module
	public static administration.proxies.Account createAccountIfNotExists(IContext context, java.lang.String _username, java.lang.String _role, java.lang.String _password, boolean _webserviceUser)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("Username", _username);
		params.put("Role", _role);
		params.put("Password", _password);
		params.put("WebserviceUser", _webserviceUser);
		IMendixObject result = (IMendixObject)Core.microflowCall("ApprontoCommon.CreateAccountIfNotExists").withParams(params).execute(context);
		return result == null ? null : administration.proxies.Account.initialize(context, result);
	}
}