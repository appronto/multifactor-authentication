// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package approntocommon.actions;

import java.util.List;
import mxmodelreflection.proxies.*;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class ExecuteUnverifiedMicroflowWithGUIDparam extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String GUIDparam;
	private java.lang.String MicroflowToExecute;

	public ExecuteUnverifiedMicroflowWithGUIDparam(IContext context, java.lang.String GUIDparam, java.lang.String MicroflowToExecute)
	{
		super(context);
		this.GUIDparam = GUIDparam;
		this.MicroflowToExecute = MicroflowToExecute;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE

		if (this.GUIDparam != null && this.GUIDparam != "")
		{
			
			Microflows microflow = Microflows.initialize(this.getContext(), Core.retrieveXPathQuery(this.getContext(), "//" + Microflows.entityName + "[CompleteName='"+this.MicroflowToExecute+"']").get(0));
			
			if(microflow == null)
			{
				return false;
			}
			List<Parameter> inputParameter = microflow.getMicroflows_InputParameter();
			
			Parameter param = null;
			if(inputParameter != null  && inputParameter.size() > 0)
			{
				param = inputParameter.get(0);
			}
			else
			{	
				return  false;
			}
			
			MxObjectType referredObject = param.getParameter_MxObjectType();
			
			List<IMendixObject> objects = Core.retrieveXPathQuery(this.getContext(), "//" + referredObject.getModule() + "."+ referredObject.getName() + "[id='"+this.GUIDparam+"']");
			if(objects.size() > 0)
			{
				IMendixObject object = objects.get(0);
				Core.microflowCall(microflow.getCompleteName())
				.withParam(param.getName(), object)
				.execute(getContext());
			}
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
		return "ExecuteUnverifiedMicroflowWithGUIDparam";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
