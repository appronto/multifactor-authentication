// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package twilioconnector.proxies.constants;

import com.mendix.core.Core;

public class Constants
{
	// These are the constants for the TwilioConnector module

	/**
	* This key will be used to encrypt and decrypt values.
	* The length of this key must be 16 characters.
	*/
	public static java.lang.String getEncryptionKey()
	{
		return (java.lang.String)Core.getConfiguration().getConstantValue("TwilioConnector.EncryptionKey");
	}

	/**
	* Prefix for stored encrypted values.
	* This value is used to determine whether the value has been already encrypted.
	*/
	public static java.lang.String getEncryptionPrefix()
	{
		return (java.lang.String)Core.getConfiguration().getConstantValue("TwilioConnector.EncryptionPrefix");
	}
}