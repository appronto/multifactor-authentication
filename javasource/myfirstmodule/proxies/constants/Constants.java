// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package myfirstmodule.proxies.constants;

import com.mendix.core.Core;

public class Constants
{
	// These are the constants for the MyFirstModule module

	public static java.lang.String getAttributNameMFA()
	{
		return (java.lang.String)Core.getConfiguration().getConstantValue("MyFirstModule.AttributNameMFA");
	}
}