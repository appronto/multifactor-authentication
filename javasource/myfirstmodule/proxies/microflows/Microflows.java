// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package myfirstmodule.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;

public class Microflows
{
	// These are the microflows for the MyFirstModule module
	public static void aCT_2FA_CheckCode(IContext context, googleauthenticator.proxies.GoogleCredentials _googleCredentials)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("GoogleCredentials", _googleCredentials == null ? null : _googleCredentials.getMendixObject());
		Core.microflowCall("MyFirstModule.ACT_2FA_CheckCode").withParams(params).execute(context);
	}
	public static void aCT_GenerateNumberRandom(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		Core.microflowCall("MyFirstModule.ACT_GenerateNumberRandom").withParams(params).execute(context);
	}
	public static void aCT_GoogleCredentials_GenerateSecretKey(IContext context, googleauthenticator.proxies.GoogleCredentials _googleCredentials)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("GoogleCredentials", _googleCredentials == null ? null : _googleCredentials.getMendixObject());
		Core.microflowCall("MyFirstModule.ACT_GoogleCredentials_GenerateSecretKey").withParams(params).execute(context);
	}
	public static void aCT_GoogleCredentials_RetrieveAndShowPage(IContext context, administration.proxies.Account _account)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("Account", _account == null ? null : _account.getMendixObject());
		Core.microflowCall("MyFirstModule.ACT_GoogleCredentials_RetrieveAndShowPage").withParams(params).execute(context);
	}
	public static boolean aSU(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.Boolean) Core.microflowCall("MyFirstModule.ASU").withParams(params).execute(context);
	}
	public static java.lang.String getTest(IContext context, system.proxies.HttpRequest _httpRequest, system.proxies.HttpResponse _httpResponse)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("httpRequest", _httpRequest == null ? null : _httpRequest.getMendixObject());
		params.put("httpResponse", _httpResponse == null ? null : _httpResponse.getMendixObject());
		return (java.lang.String) Core.microflowCall("MyFirstModule.GetTest").withParams(params).execute(context);
	}
	public static void getVErsion(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		Core.microflowCall("MyFirstModule.GetVErsion").withParams(params).execute(context);
	}
}