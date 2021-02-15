// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mfamodule.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Microflows
{
	// These are the microflows for the MFAmodule module
	public static boolean aSU_MFA(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.ASU_MFA").withParams(params).execute(context);
	}
	/**
	 * Called from JAVA Action
	 */
	public static mfamodule.proxies.MFA dS_MFA_GET(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		IMendixObject result = (IMendixObject)Core.microflowCall("MFAmodule.DS_MFA_GET").withParams(params).execute(context);
		return result == null ? null : mfamodule.proxies.MFA.initialize(context, result);
	}
	public static boolean sUB_MFA_CreateCode(IContext context, mfamodule.proxies.MFA _mFA)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("MFA", _mFA == null ? null : _mFA.getMendixObject());
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.SUB_MFA_CreateCode").withParams(params).execute(context);
	}
	/**
	 * Called from JAVA Action
	 */
	public static boolean sUB_MFA_UserDisabledCheck(IContext context, java.lang.String _username)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("Username", _username);
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.SUB_MFA_UserDisabledCheck").withParams(params).execute(context);
	}
	/**
	 * Called from JAVA Action
	 */
	public static boolean sUB_MFA_Validate(IContext context, mfamodule.proxies.MFA _mFA, java.lang.String _mFAUsername)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("MFA", _mFA == null ? null : _mFA.getMendixObject());
		params.put("MFAUsername", _mFAUsername);
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.SUB_MFA_Validate").withParams(params).execute(context);
	}
	public static boolean sUB_MFA_ValidateCode(IContext context, mfamodule.proxies.MFA _mFA)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("MFA", _mFA == null ? null : _mFA.getMendixObject());
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.SUB_MFA_ValidateCode").withParams(params).execute(context);
	}
	public static boolean sUB_Username_CheckMFANeeded(IContext context, java.lang.String _username)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("Username", _username);
		return (java.lang.Boolean) Core.microflowCall("MFAmodule.SUB_Username_CheckMFANeeded").withParams(params).execute(context);
	}
}