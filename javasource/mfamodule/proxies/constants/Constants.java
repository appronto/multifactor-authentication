// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mfamodule.proxies.constants;

import com.mendix.core.Core;

public class Constants
{
	// These are the constants for the MFAmodule module

	public static boolean getEnabledMFA()
	{
		return (java.lang.Boolean)Core.getConfiguration().getConstantValue("MFAmodule.EnabledMFA");
	}

	public static java.lang.String getLognode()
	{
		return (java.lang.String)Core.getConfiguration().getConstantValue("MFAmodule.Lognode");
	}
}