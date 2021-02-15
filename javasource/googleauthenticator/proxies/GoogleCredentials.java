// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package googleauthenticator.proxies;

public class GoogleCredentials
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject googleCredentialsMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "GoogleAuthenticator.GoogleCredentials";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		ga_SecretKey("ga_SecretKey"),
		ga_OtpAuthURL("ga_OtpAuthURL"),
		code("code"),
		GoogleCredentials_User("GoogleAuthenticator.GoogleCredentials_User");

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

	public GoogleCredentials(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "GoogleAuthenticator.GoogleCredentials"));
	}

	protected GoogleCredentials(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject googleCredentialsMendixObject)
	{
		if (googleCredentialsMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("GoogleAuthenticator.GoogleCredentials", googleCredentialsMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a GoogleAuthenticator.GoogleCredentials");

		this.googleCredentialsMendixObject = googleCredentialsMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'GoogleCredentials.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static googleauthenticator.proxies.GoogleCredentials initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return googleauthenticator.proxies.GoogleCredentials.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static googleauthenticator.proxies.GoogleCredentials initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new googleauthenticator.proxies.GoogleCredentials(context, mendixObject);
	}

	public static googleauthenticator.proxies.GoogleCredentials load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return googleauthenticator.proxies.GoogleCredentials.initialize(context, mendixObject);
	}

	public static java.util.List<googleauthenticator.proxies.GoogleCredentials> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<googleauthenticator.proxies.GoogleCredentials> result = new java.util.ArrayList<googleauthenticator.proxies.GoogleCredentials>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//GoogleAuthenticator.GoogleCredentials" + xpathConstraint))
			result.add(googleauthenticator.proxies.GoogleCredentials.initialize(context, obj));
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
	 * @return value of ga_SecretKey
	 */
	public final java.lang.String getga_SecretKey()
	{
		return getga_SecretKey(getContext());
	}

	/**
	 * @param context
	 * @return value of ga_SecretKey
	 */
	public final java.lang.String getga_SecretKey(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ga_SecretKey.toString());
	}

	/**
	 * Set value of ga_SecretKey
	 * @param ga_secretkey
	 */
	public final void setga_SecretKey(java.lang.String ga_secretkey)
	{
		setga_SecretKey(getContext(), ga_secretkey);
	}

	/**
	 * Set value of ga_SecretKey
	 * @param context
	 * @param ga_secretkey
	 */
	public final void setga_SecretKey(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String ga_secretkey)
	{
		getMendixObject().setValue(context, MemberNames.ga_SecretKey.toString(), ga_secretkey);
	}

	/**
	 * @return value of ga_OtpAuthURL
	 */
	public final java.lang.String getga_OtpAuthURL()
	{
		return getga_OtpAuthURL(getContext());
	}

	/**
	 * @param context
	 * @return value of ga_OtpAuthURL
	 */
	public final java.lang.String getga_OtpAuthURL(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ga_OtpAuthURL.toString());
	}

	/**
	 * Set value of ga_OtpAuthURL
	 * @param ga_otpauthurl
	 */
	public final void setga_OtpAuthURL(java.lang.String ga_otpauthurl)
	{
		setga_OtpAuthURL(getContext(), ga_otpauthurl);
	}

	/**
	 * Set value of ga_OtpAuthURL
	 * @param context
	 * @param ga_otpauthurl
	 */
	public final void setga_OtpAuthURL(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String ga_otpauthurl)
	{
		getMendixObject().setValue(context, MemberNames.ga_OtpAuthURL.toString(), ga_otpauthurl);
	}

	/**
	 * @return value of code
	 */
	public final java.lang.String getcode()
	{
		return getcode(getContext());
	}

	/**
	 * @param context
	 * @return value of code
	 */
	public final java.lang.String getcode(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.code.toString());
	}

	/**
	 * Set value of code
	 * @param code
	 */
	public final void setcode(java.lang.String code)
	{
		setcode(getContext(), code);
	}

	/**
	 * Set value of code
	 * @param context
	 * @param code
	 */
	public final void setcode(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String code)
	{
		getMendixObject().setValue(context, MemberNames.code.toString(), code);
	}

	/**
	 * @return value of GoogleCredentials_User
	 */
	public final system.proxies.User getGoogleCredentials_User() throws com.mendix.core.CoreException
	{
		return getGoogleCredentials_User(getContext());
	}

	/**
	 * @param context
	 * @return value of GoogleCredentials_User
	 */
	public final system.proxies.User getGoogleCredentials_User(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		system.proxies.User result = null;
		com.mendix.systemwideinterfaces.core.IMendixIdentifier identifier = getMendixObject().getValue(context, MemberNames.GoogleCredentials_User.toString());
		if (identifier != null)
			result = system.proxies.User.load(context, identifier);
		return result;
	}

	/**
	 * Set value of GoogleCredentials_User
	 * @param googlecredentials_user
	 */
	public final void setGoogleCredentials_User(system.proxies.User googlecredentials_user)
	{
		setGoogleCredentials_User(getContext(), googlecredentials_user);
	}

	/**
	 * Set value of GoogleCredentials_User
	 * @param context
	 * @param googlecredentials_user
	 */
	public final void setGoogleCredentials_User(com.mendix.systemwideinterfaces.core.IContext context, system.proxies.User googlecredentials_user)
	{
		if (googlecredentials_user == null)
			getMendixObject().setValue(context, MemberNames.GoogleCredentials_User.toString(), null);
		else
			getMendixObject().setValue(context, MemberNames.GoogleCredentials_User.toString(), googlecredentials_user.getMendixObject().getId());
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return googleCredentialsMendixObject;
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
			final googleauthenticator.proxies.GoogleCredentials that = (googleauthenticator.proxies.GoogleCredentials) obj;
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
		return "GoogleAuthenticator.GoogleCredentials";
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
