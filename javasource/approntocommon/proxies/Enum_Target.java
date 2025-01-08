// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package approntocommon.proxies;

public enum Enum_Target
{
	ParentFrame(new java.lang.String[][] { new java.lang.String[] { "en_US", "Parent frame" } }),
	SameBrowserPage(new java.lang.String[][] { new java.lang.String[] { "en_US", "Same browser page" } }),
	NewBrowserPage(new java.lang.String[][] { new java.lang.String[] { "en_US", "New browser page" } }),
	Mobile(new java.lang.String[][] { new java.lang.String[] { "en_US", "Mobile" } });

	private final java.util.Map<java.lang.String, java.lang.String> captions;

	private Enum_Target(java.lang.String[][] captionStrings)
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