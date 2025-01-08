// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package approntocommon.actions;

import java.io.File;
import java.io.FileInputStream;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class FileFromResourceToFileDocument extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String Filename;
	private IMendixObject __TargetFile;
	private system.proxies.FileDocument TargetFile;

	public FileFromResourceToFileDocument(IContext context, java.lang.String Filename, IMendixObject TargetFile)
	{
		super(context);
		this.Filename = Filename;
		this.__TargetFile = TargetFile;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.TargetFile = this.__TargetFile == null ? null : system.proxies.FileDocument.initialize(getContext(), __TargetFile);

		// BEGIN USER CODE
		String filenamePath = Core.getConfiguration().getResourcesPath().getAbsolutePath() + File.separator + Filename;
		TargetFile.setName(Filename);
		FileInputStream fis = new FileInputStream(filenamePath);
		{
			Core.storeFileDocumentContent(getContext(), TargetFile.getMendixObject(), fis);
		}
		
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "FileFromResourceToFileDocument";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
