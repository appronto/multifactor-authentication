// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package twilioconnector.proxies;

public class Root_SMSStatus
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject root_SMSStatusMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "TwilioConnector.Root_SMSStatus";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		SmsSid("SmsSid"),
		SmsStatus("SmsStatus"),
		MessageStatus("MessageStatus"),
		To("To"),
		MessageSid("MessageSid"),
		AccountSid("AccountSid"),
		From("From"),
		ApiVersion("ApiVersion");

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

	public Root_SMSStatus(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "TwilioConnector.Root_SMSStatus"));
	}

	protected Root_SMSStatus(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject root_SMSStatusMendixObject)
	{
		if (root_SMSStatusMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("TwilioConnector.Root_SMSStatus", root_SMSStatusMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a TwilioConnector.Root_SMSStatus");

		this.root_SMSStatusMendixObject = root_SMSStatusMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'Root_SMSStatus.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static twilioconnector.proxies.Root_SMSStatus initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return twilioconnector.proxies.Root_SMSStatus.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static twilioconnector.proxies.Root_SMSStatus initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new twilioconnector.proxies.Root_SMSStatus(context, mendixObject);
	}

	public static twilioconnector.proxies.Root_SMSStatus load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return twilioconnector.proxies.Root_SMSStatus.initialize(context, mendixObject);
	}

	public static java.util.List<twilioconnector.proxies.Root_SMSStatus> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<twilioconnector.proxies.Root_SMSStatus> result = new java.util.ArrayList<twilioconnector.proxies.Root_SMSStatus>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//TwilioConnector.Root_SMSStatus" + xpathConstraint))
			result.add(twilioconnector.proxies.Root_SMSStatus.initialize(context, obj));
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
	 * @return value of SmsSid
	 */
	public final java.lang.String getSmsSid()
	{
		return getSmsSid(getContext());
	}

	/**
	 * @param context
	 * @return value of SmsSid
	 */
	public final java.lang.String getSmsSid(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.SmsSid.toString());
	}

	/**
	 * Set value of SmsSid
	 * @param smssid
	 */
	public final void setSmsSid(java.lang.String smssid)
	{
		setSmsSid(getContext(), smssid);
	}

	/**
	 * Set value of SmsSid
	 * @param context
	 * @param smssid
	 */
	public final void setSmsSid(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String smssid)
	{
		getMendixObject().setValue(context, MemberNames.SmsSid.toString(), smssid);
	}

	/**
	 * @return value of SmsStatus
	 */
	public final java.lang.String getSmsStatus()
	{
		return getSmsStatus(getContext());
	}

	/**
	 * @param context
	 * @return value of SmsStatus
	 */
	public final java.lang.String getSmsStatus(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.SmsStatus.toString());
	}

	/**
	 * Set value of SmsStatus
	 * @param smsstatus
	 */
	public final void setSmsStatus(java.lang.String smsstatus)
	{
		setSmsStatus(getContext(), smsstatus);
	}

	/**
	 * Set value of SmsStatus
	 * @param context
	 * @param smsstatus
	 */
	public final void setSmsStatus(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String smsstatus)
	{
		getMendixObject().setValue(context, MemberNames.SmsStatus.toString(), smsstatus);
	}

	/**
	 * @return value of MessageStatus
	 */
	public final java.lang.String getMessageStatus()
	{
		return getMessageStatus(getContext());
	}

	/**
	 * @param context
	 * @return value of MessageStatus
	 */
	public final java.lang.String getMessageStatus(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.MessageStatus.toString());
	}

	/**
	 * Set value of MessageStatus
	 * @param messagestatus
	 */
	public final void setMessageStatus(java.lang.String messagestatus)
	{
		setMessageStatus(getContext(), messagestatus);
	}

	/**
	 * Set value of MessageStatus
	 * @param context
	 * @param messagestatus
	 */
	public final void setMessageStatus(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String messagestatus)
	{
		getMendixObject().setValue(context, MemberNames.MessageStatus.toString(), messagestatus);
	}

	/**
	 * @return value of To
	 */
	public final java.lang.String getTo()
	{
		return getTo(getContext());
	}

	/**
	 * @param context
	 * @return value of To
	 */
	public final java.lang.String getTo(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.To.toString());
	}

	/**
	 * Set value of To
	 * @param to
	 */
	public final void setTo(java.lang.String to)
	{
		setTo(getContext(), to);
	}

	/**
	 * Set value of To
	 * @param context
	 * @param to
	 */
	public final void setTo(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String to)
	{
		getMendixObject().setValue(context, MemberNames.To.toString(), to);
	}

	/**
	 * @return value of MessageSid
	 */
	public final java.lang.String getMessageSid()
	{
		return getMessageSid(getContext());
	}

	/**
	 * @param context
	 * @return value of MessageSid
	 */
	public final java.lang.String getMessageSid(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.MessageSid.toString());
	}

	/**
	 * Set value of MessageSid
	 * @param messagesid
	 */
	public final void setMessageSid(java.lang.String messagesid)
	{
		setMessageSid(getContext(), messagesid);
	}

	/**
	 * Set value of MessageSid
	 * @param context
	 * @param messagesid
	 */
	public final void setMessageSid(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String messagesid)
	{
		getMendixObject().setValue(context, MemberNames.MessageSid.toString(), messagesid);
	}

	/**
	 * @return value of AccountSid
	 */
	public final java.lang.String getAccountSid()
	{
		return getAccountSid(getContext());
	}

	/**
	 * @param context
	 * @return value of AccountSid
	 */
	public final java.lang.String getAccountSid(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.AccountSid.toString());
	}

	/**
	 * Set value of AccountSid
	 * @param accountsid
	 */
	public final void setAccountSid(java.lang.String accountsid)
	{
		setAccountSid(getContext(), accountsid);
	}

	/**
	 * Set value of AccountSid
	 * @param context
	 * @param accountsid
	 */
	public final void setAccountSid(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String accountsid)
	{
		getMendixObject().setValue(context, MemberNames.AccountSid.toString(), accountsid);
	}

	/**
	 * @return value of From
	 */
	public final java.lang.String getFrom()
	{
		return getFrom(getContext());
	}

	/**
	 * @param context
	 * @return value of From
	 */
	public final java.lang.String getFrom(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.From.toString());
	}

	/**
	 * Set value of From
	 * @param from
	 */
	public final void setFrom(java.lang.String from)
	{
		setFrom(getContext(), from);
	}

	/**
	 * Set value of From
	 * @param context
	 * @param from
	 */
	public final void setFrom(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String from)
	{
		getMendixObject().setValue(context, MemberNames.From.toString(), from);
	}

	/**
	 * @return value of ApiVersion
	 */
	public final java.lang.String getApiVersion()
	{
		return getApiVersion(getContext());
	}

	/**
	 * @param context
	 * @return value of ApiVersion
	 */
	public final java.lang.String getApiVersion(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ApiVersion.toString());
	}

	/**
	 * Set value of ApiVersion
	 * @param apiversion
	 */
	public final void setApiVersion(java.lang.String apiversion)
	{
		setApiVersion(getContext(), apiversion);
	}

	/**
	 * Set value of ApiVersion
	 * @param context
	 * @param apiversion
	 */
	public final void setApiVersion(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String apiversion)
	{
		getMendixObject().setValue(context, MemberNames.ApiVersion.toString(), apiversion);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return root_SMSStatusMendixObject;
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
			final twilioconnector.proxies.Root_SMSStatus that = (twilioconnector.proxies.Root_SMSStatus) obj;
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
		return "TwilioConnector.Root_SMSStatus";
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
