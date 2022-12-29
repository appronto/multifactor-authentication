// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package approntocommon.proxies;

public class ConversionTask
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject conversionTaskMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "ApprontoCommon.ConversionTask";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		Microflow("Microflow"),
		StartDate("StartDate"),
		EndDate("EndDate"),
		RunOnce("RunOnce"),
		DontRunAfter("DontRunAfter"),
		Result("Result");

		private java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@java.lang.Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public ConversionTask(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "ApprontoCommon.ConversionTask"));
	}

	protected ConversionTask(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject conversionTaskMendixObject)
	{
		if (conversionTaskMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("ApprontoCommon.ConversionTask", conversionTaskMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a ApprontoCommon.ConversionTask");

		this.conversionTaskMendixObject = conversionTaskMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'ConversionTask.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static approntocommon.proxies.ConversionTask initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return approntocommon.proxies.ConversionTask.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static approntocommon.proxies.ConversionTask initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new approntocommon.proxies.ConversionTask(context, mendixObject);
	}

	public static approntocommon.proxies.ConversionTask load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return approntocommon.proxies.ConversionTask.initialize(context, mendixObject);
	}

	public static java.util.List<approntocommon.proxies.ConversionTask> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<approntocommon.proxies.ConversionTask> result = new java.util.ArrayList<approntocommon.proxies.ConversionTask>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//ApprontoCommon.ConversionTask" + xpathConstraint))
			result.add(approntocommon.proxies.ConversionTask.initialize(context, obj));
		return result;
	}

	/**
	 * Commit the changes made on this proxy object.
	 */
	public final void commit() throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Commit the changes made on this proxy object using the specified context.
	 */
	public final void commit(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Delete the object.
	 */
	public final void delete()
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}

	/**
	 * Delete the object using the specified context.
	 */
	public final void delete(com.mendix.systemwideinterfaces.core.IContext context)
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}
	/**
	 * @return value of Microflow
	 */
	public final java.lang.String getMicroflow()
	{
		return getMicroflow(getContext());
	}

	/**
	 * @param context
	 * @return value of Microflow
	 */
	public final java.lang.String getMicroflow(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Microflow.toString());
	}

	/**
	 * Set value of Microflow
	 * @param microflow
	 */
	public final void setMicroflow(java.lang.String microflow)
	{
		setMicroflow(getContext(), microflow);
	}

	/**
	 * Set value of Microflow
	 * @param context
	 * @param microflow
	 */
	public final void setMicroflow(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String microflow)
	{
		getMendixObject().setValue(context, MemberNames.Microflow.toString(), microflow);
	}

	/**
	 * @return value of StartDate
	 */
	public final java.util.Date getStartDate()
	{
		return getStartDate(getContext());
	}

	/**
	 * @param context
	 * @return value of StartDate
	 */
	public final java.util.Date getStartDate(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.util.Date) getMendixObject().getValue(context, MemberNames.StartDate.toString());
	}

	/**
	 * Set value of StartDate
	 * @param startdate
	 */
	public final void setStartDate(java.util.Date startdate)
	{
		setStartDate(getContext(), startdate);
	}

	/**
	 * Set value of StartDate
	 * @param context
	 * @param startdate
	 */
	public final void setStartDate(com.mendix.systemwideinterfaces.core.IContext context, java.util.Date startdate)
	{
		getMendixObject().setValue(context, MemberNames.StartDate.toString(), startdate);
	}

	/**
	 * @return value of EndDate
	 */
	public final java.util.Date getEndDate()
	{
		return getEndDate(getContext());
	}

	/**
	 * @param context
	 * @return value of EndDate
	 */
	public final java.util.Date getEndDate(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.util.Date) getMendixObject().getValue(context, MemberNames.EndDate.toString());
	}

	/**
	 * Set value of EndDate
	 * @param enddate
	 */
	public final void setEndDate(java.util.Date enddate)
	{
		setEndDate(getContext(), enddate);
	}

	/**
	 * Set value of EndDate
	 * @param context
	 * @param enddate
	 */
	public final void setEndDate(com.mendix.systemwideinterfaces.core.IContext context, java.util.Date enddate)
	{
		getMendixObject().setValue(context, MemberNames.EndDate.toString(), enddate);
	}

	/**
	 * @return value of RunOnce
	 */
	public final java.lang.Boolean getRunOnce()
	{
		return getRunOnce(getContext());
	}

	/**
	 * @param context
	 * @return value of RunOnce
	 */
	public final java.lang.Boolean getRunOnce(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.RunOnce.toString());
	}

	/**
	 * Set value of RunOnce
	 * @param runonce
	 */
	public final void setRunOnce(java.lang.Boolean runonce)
	{
		setRunOnce(getContext(), runonce);
	}

	/**
	 * Set value of RunOnce
	 * @param context
	 * @param runonce
	 */
	public final void setRunOnce(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean runonce)
	{
		getMendixObject().setValue(context, MemberNames.RunOnce.toString(), runonce);
	}

	/**
	 * @return value of DontRunAfter
	 */
	public final java.util.Date getDontRunAfter()
	{
		return getDontRunAfter(getContext());
	}

	/**
	 * @param context
	 * @return value of DontRunAfter
	 */
	public final java.util.Date getDontRunAfter(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.util.Date) getMendixObject().getValue(context, MemberNames.DontRunAfter.toString());
	}

	/**
	 * Set value of DontRunAfter
	 * @param dontrunafter
	 */
	public final void setDontRunAfter(java.util.Date dontrunafter)
	{
		setDontRunAfter(getContext(), dontrunafter);
	}

	/**
	 * Set value of DontRunAfter
	 * @param context
	 * @param dontrunafter
	 */
	public final void setDontRunAfter(com.mendix.systemwideinterfaces.core.IContext context, java.util.Date dontrunafter)
	{
		getMendixObject().setValue(context, MemberNames.DontRunAfter.toString(), dontrunafter);
	}

	/**
	 * Set value of Result
	 * @param result
	 */
	public final approntocommon.proxies.Result getResult()
	{
		return getResult(getContext());
	}

	/**
	 * @param context
	 * @return value of Result
	 */
	public final approntocommon.proxies.Result getResult(com.mendix.systemwideinterfaces.core.IContext context)
	{
		Object obj = getMendixObject().getValue(context, MemberNames.Result.toString());
		if (obj == null)
			return null;

		return approntocommon.proxies.Result.valueOf((java.lang.String) obj);
	}

	/**
	 * Set value of Result
	 * @param result
	 */
	public final void setResult(approntocommon.proxies.Result result)
	{
		setResult(getContext(), result);
	}

	/**
	 * Set value of Result
	 * @param context
	 * @param result
	 */
	public final void setResult(com.mendix.systemwideinterfaces.core.IContext context, approntocommon.proxies.Result result)
	{
		if (result != null)
			getMendixObject().setValue(context, MemberNames.Result.toString(), result.toString());
		else
			getMendixObject().setValue(context, MemberNames.Result.toString(), null);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return conversionTaskMendixObject;
	}

	/**
	 * @return the IContext instance of this proxy, or null if no IContext instance was specified at initialization.
	 */
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final approntocommon.proxies.ConversionTask that = (approntocommon.proxies.ConversionTask) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@java.lang.Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

	/**
	 * @return String name of this class
	 */
	public static java.lang.String getType()
	{
		return "ApprontoCommon.ConversionTask";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@java.lang.Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}