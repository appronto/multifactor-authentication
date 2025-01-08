// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mfamodule.proxies;

public enum Enum_DefaultMessages
{
	Login_Maximum_attempts_exceeded(new java.lang.String[][] { new java.lang.String[] { "en_US", "Maximum attempts exceeded" } }),
	MFA_Maximum_attempts_exceeded(new java.lang.String[][] { new java.lang.String[] { "en_US", "Maximum attempts exceeded" } }),
	MFA_Code_is_incorrect(new java.lang.String[][] { new java.lang.String[] { "en_US", "Code is incorrect" } }),
	MFA_CodeSent(new java.lang.String[][] { new java.lang.String[] { "en_US", "We have sent you the code" } }),
	MFA_Could_not_create_code(new java.lang.String[][] { new java.lang.String[] { "en_US", "Could not send a code to you, please contact us!" } });

	private final java.util.Map<java.lang.String, java.lang.String> captions;

	private Enum_DefaultMessages(java.lang.String[][] captionStrings)
	{
		this.captions = new java.util.HashMap<>();
		for (java.lang.String[] captionString : captionStrings) {
			captions.put(captionString[0], captionString[1]);
		}
	}

	public java.lang.String getCaption(java.lang.String languageCode)
	{
		return captions.getOrDefault(languageCode, "en_US");
	}

	public java.lang.String getCaption()
	{
		return captions.get("en_US");
	}
}
