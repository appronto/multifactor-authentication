// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package unittesting.proxies;

public enum UnitTestResult
{
	_1_Running(new java.lang.String[][] { new java.lang.String[] { "en_US", "Running" }, new java.lang.String[] { "nl_NL", "Uitvoeren" } }),
	_2_Failed(new java.lang.String[][] { new java.lang.String[] { "en_US", "Failed" }, new java.lang.String[] { "nl_NL", "mislukt" } }),
	_3_Success(new java.lang.String[][] { new java.lang.String[] { "en_US", "Success" }, new java.lang.String[] { "nl_NL", "Succes" } });

	private final java.util.Map<java.lang.String, java.lang.String> captions;

	private UnitTestResult(java.lang.String[][] captionStrings)
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
