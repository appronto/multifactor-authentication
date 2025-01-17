// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package approntocommon.proxies;

public class RegexResult
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject regexResultMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "ApprontoCommon.RegexResult";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		GroupValue_1("GroupValue_1"),
		GroupValue_2("GroupValue_2"),
		GroupValue_3("GroupValue_3"),
		GroupValue_4("GroupValue_4"),
		GroupValue_5("GroupValue_5"),
		GroupValue_6("GroupValue_6");

		private final java.lang.String metaName;

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

	public RegexResult(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, entityName));
	}

	protected RegexResult(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject regexResultMendixObject)
	{
		if (regexResultMendixObject == null) {
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		}
		if (!com.mendix.core.Core.isSubClassOf(entityName, regexResultMendixObject.getType())) {
			throw new java.lang.IllegalArgumentException(String.format("The given object is not a %s", entityName));
		}	

		this.regexResultMendixObject = regexResultMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'RegexResult.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static approntocommon.proxies.RegexResult initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return approntocommon.proxies.RegexResult.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 * @param context The context to be used
	 * @param mendixObject The Mendix object for the new instance
	 * @return a new instance of this proxy class
	 */
	public static approntocommon.proxies.RegexResult initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new approntocommon.proxies.RegexResult(context, mendixObject);
	}

	public static approntocommon.proxies.RegexResult load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return approntocommon.proxies.RegexResult.initialize(context, mendixObject);
	}

	/**
	 * Commit the changes made on this proxy object.
	 * @throws com.mendix.core.CoreException
	 */
	public final void commit() throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Commit the changes made on this proxy object using the specified context.
	 * @throws com.mendix.core.CoreException
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
	 * @return value of GroupValue_1
	 */
	public final java.lang.String getGroupValue_1()
	{
		return getGroupValue_1(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_1
	 */
	public final java.lang.String getGroupValue_1(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_1.toString());
	}

	/**
	 * Set value of GroupValue_1
	 * @param groupvalue_1
	 */
	public final void setGroupValue_1(java.lang.String groupvalue_1)
	{
		setGroupValue_1(getContext(), groupvalue_1);
	}

	/**
	 * Set value of GroupValue_1
	 * @param context
	 * @param groupvalue_1
	 */
	public final void setGroupValue_1(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_1)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_1.toString(), groupvalue_1);
	}

	/**
	 * @return value of GroupValue_2
	 */
	public final java.lang.String getGroupValue_2()
	{
		return getGroupValue_2(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_2
	 */
	public final java.lang.String getGroupValue_2(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_2.toString());
	}

	/**
	 * Set value of GroupValue_2
	 * @param groupvalue_2
	 */
	public final void setGroupValue_2(java.lang.String groupvalue_2)
	{
		setGroupValue_2(getContext(), groupvalue_2);
	}

	/**
	 * Set value of GroupValue_2
	 * @param context
	 * @param groupvalue_2
	 */
	public final void setGroupValue_2(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_2)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_2.toString(), groupvalue_2);
	}

	/**
	 * @return value of GroupValue_3
	 */
	public final java.lang.String getGroupValue_3()
	{
		return getGroupValue_3(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_3
	 */
	public final java.lang.String getGroupValue_3(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_3.toString());
	}

	/**
	 * Set value of GroupValue_3
	 * @param groupvalue_3
	 */
	public final void setGroupValue_3(java.lang.String groupvalue_3)
	{
		setGroupValue_3(getContext(), groupvalue_3);
	}

	/**
	 * Set value of GroupValue_3
	 * @param context
	 * @param groupvalue_3
	 */
	public final void setGroupValue_3(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_3)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_3.toString(), groupvalue_3);
	}

	/**
	 * @return value of GroupValue_4
	 */
	public final java.lang.String getGroupValue_4()
	{
		return getGroupValue_4(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_4
	 */
	public final java.lang.String getGroupValue_4(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_4.toString());
	}

	/**
	 * Set value of GroupValue_4
	 * @param groupvalue_4
	 */
	public final void setGroupValue_4(java.lang.String groupvalue_4)
	{
		setGroupValue_4(getContext(), groupvalue_4);
	}

	/**
	 * Set value of GroupValue_4
	 * @param context
	 * @param groupvalue_4
	 */
	public final void setGroupValue_4(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_4)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_4.toString(), groupvalue_4);
	}

	/**
	 * @return value of GroupValue_5
	 */
	public final java.lang.String getGroupValue_5()
	{
		return getGroupValue_5(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_5
	 */
	public final java.lang.String getGroupValue_5(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_5.toString());
	}

	/**
	 * Set value of GroupValue_5
	 * @param groupvalue_5
	 */
	public final void setGroupValue_5(java.lang.String groupvalue_5)
	{
		setGroupValue_5(getContext(), groupvalue_5);
	}

	/**
	 * Set value of GroupValue_5
	 * @param context
	 * @param groupvalue_5
	 */
	public final void setGroupValue_5(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_5)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_5.toString(), groupvalue_5);
	}

	/**
	 * @return value of GroupValue_6
	 */
	public final java.lang.String getGroupValue_6()
	{
		return getGroupValue_6(getContext());
	}

	/**
	 * @param context
	 * @return value of GroupValue_6
	 */
	public final java.lang.String getGroupValue_6(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.GroupValue_6.toString());
	}

	/**
	 * Set value of GroupValue_6
	 * @param groupvalue_6
	 */
	public final void setGroupValue_6(java.lang.String groupvalue_6)
	{
		setGroupValue_6(getContext(), groupvalue_6);
	}

	/**
	 * Set value of GroupValue_6
	 * @param context
	 * @param groupvalue_6
	 */
	public final void setGroupValue_6(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String groupvalue_6)
	{
		getMendixObject().setValue(context, MemberNames.GroupValue_6.toString(), groupvalue_6);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return regexResultMendixObject;
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
		if (obj == this) {
			return true;
		}
		if (obj != null && getClass().equals(obj.getClass()))
		{
			final approntocommon.proxies.RegexResult that = (approntocommon.proxies.RegexResult) obj;
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
		return entityName;
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
