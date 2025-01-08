// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
import { Big } from "big.js";

// BEGIN EXTRA CODE
// END EXTRA CODE

/**
 * This JavaScript action can open url in various targets.
 * @param {string} url - Url to open
 * @param {"ApprontoCommon.Enum_Target.ParentFrame"|"ApprontoCommon.Enum_Target.SameBrowserPage"|"ApprontoCommon.Enum_Target.NewBrowserPage"|"ApprontoCommon.Enum_Target.Mobile"} target - Options: ParentFrame, SameBrowserPage, NewBrowserPage, Mobile
 * @returns {Promise.<boolean>}
 */
export async function OpenURL_WithOptions(url, target) {
	// BEGIN USER CODE
	if (!url) {
        return Promise.reject(new Error("Input parameter 'Url' is required"));
    }
    
	if(target == "ParentFrame")
	{
		window.parent.location.href = url;
	}		
	else 
	{
		if(target == "SameBrowserPage")
		{
			document.location.href = url;
		}
		else
		{
			if(target == "Mobile")
			{
				window.open(url,"_system", "location=yes");
				
			}
			else
			{
				window.open(url);
			}
		}
	}

    return Promise.resolve(true);
	// END USER CODE
}