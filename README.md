# Multi-factor authentication for Mendix

## What is Multi-factor Authentication?

Multi-factor authentication (MFA; also known as Two-factor authentication or 2FA, along with similar terms) is an electronic authentication method in which a user is granted access to a website or application only after successfully presenting two or more pieces of evidence (or factors) to an authentication mechanism: knowledge (something only the user knows), possession (something only the user has), and inherence (something only the user is). MFA protects the user from an unknown person trying to access their data such as personal ID details or financial assets.

A third-party authenticator (TPA) app enables two-factor authentication, usually by showing a randomly generated and constantly refreshing code to use for authentication. (source:Wikipedia)

## What is the problem we solve?

The available multi-factor implementations in the Marketplace offer the end-user of an Mendix application during the login procedure to enter the code from a SMS, E-mail or generated by an authenticator (Google or other) after the actual login (and creation of the user session). See the diagram below:

![alt text](https://github.com/appronto/multifactor-authentication/blob/main/Output/Swimlane1.png?raw=true)

[https://swimlanes.io/d/zEPQlLV3T](https://swimlanes.io/d/zEPQlLV3T)

At a successful login and prior to entering an MFA code, the user already has a valid user session and access to the user&#39;s authorized pages, microflows etc. (but you cannot see them nor easy access them). This approach only works when dynamic role assignment is done after a valid MFA code (which is not default Mendix functionality).

## What is the solution?

We have built a MFA module that extends the Mendix LoginAction, documented at [https://apidocs.rnd.mendix.com/7/runtime/com/mendix/core/action/user/LoginAction.html](https://apidocs.rnd.mendix.com/7/runtime/com/mendix/core/action/user/LoginAction.html)
The MFA code is validated first and only then the module creates a user session which grants the user access to their pages, microflows etc.

![alt text](https://github.com/appronto/multifactor-authentication/blob/main/Output/Swimlane2.png?raw=true)

[https://swimlanes.io/u/4o7jaAOjY](https://swimlanes.io/u/4o7jaAOjY)

We depricated the login.html compatibility in commbination of MFA. This will make the code more simple and safer. 

## How did we prove that this module is secure?
At the point in time after login in the first step:

`mx.data.get({ xpath:'//System.User', callback:function(data){console.log(data);} })`

still returns the anonymous User object:

![alt text](https://github.com/appronto/multifactor-authentication/blob/main/Output/Code1.png?raw=true)

Scenarios to cover:

- Default login via login.html for accounts with MFA disabled.
- Default login via widgets for accounts with MFA disabled.
- Default login for webservice and REST accounts.
- Login by default widgets but extended with ability to enter MFA code with MFA enabled.
- Native mobile login 

## Configuration

1. Download the module from the Mendix Marketplace here:

Or from Github: [https://github.com/appronto/multifactor-authentication](https://github.com/appronto/multifactor-authentication)

2. Download the Nanoflow Commons from the Mendix Marketplace

**There are a few things to configure:**

After startup configuration:
1. Add `ASU_MFA` Microflow in your After Startup.

2. Change `SUB_MFA_UserDisabledCheck` to call your logic to determine if the logged in user needs to be multi-factor authenticated.

	*Example* is available in `SUB_MFA_UserDisabledCheck` (copy this to your own module). If you use this example please Add the new attributes **`HasMFAenabled`(Boolean)** and **`LastLogin2FA` (datetime)** to the Account entity.

3. Add your method(s) of multifactor authentication in `SUB_MFA_ValidateCode`.

	*Examples* are available:

	- Google authenticator connector
	  - Module download: [https://marketplace.mendix.com/link/component/2948](https://marketplace.mendix.com/link/component/2948)
	  - Google Play Store: [https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2&amp;hl=en](https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2&amp;hl=en)
	  - Apple App store: [https://apps.apple.com/us/app/google-authenticator/id388497605](https://apps.apple.com/us/app/google-authenticator/id388497605)
	- SMS
	  - Change `SUB_MFA_CreateCode` to send the SMS
	  - Send a SMS with for example Twilio:
	  - Try Twilio here: [http://appron.to/try-twilio](http://appron.to/try-twilio) and create an API key on [https://www.twilio.com/console/project/settings](https://www.twilio.com/console/project/settings)
	- Email verification with code to verify
	  - Change `SUB_MFA_CreateCode` to send the e-mail
	  - Use default Emailtemplate module or Sendgrid API for example to compose this e-mail to your user.


4. Add snippet `SN_MFA_LoginPage` / `SN_Login_Native` to your login page

5. Set the constant `EnabledMFA` to true to get started!

**Keep in mind when upgrading the module from the Appstore in the future:**

It will break the login mechanism, but you will be notified because by default an exception will be raised and warnings will be shown when the module with your MFA logic hasn&#39;t been configured correcty. Like this &quot;An error has occurred while handling the request: java.lang.Exception: `SUB_MFA_UserDisabledCheck microflow not yet implemented. Did you upgrade?` &quot;.

## What we learned

We preferred a non-persistent approach to keep track of the anonymous user login steps (first with login, second with MFA code). We found out when the first attempt was successfully validated, in the second step when passing the MFA code, the context/relation to the previous anonymous session was deleted. Therefore we could not correlate this to the first step. When we use a persistent MFA entity (and commit the MFA object after the first login step) it works.

![alt text](https://github.com/appronto/multifactor-authentication/blob/main/Output/entity.png?raw=true)

For native mobile we needed to change the sign in nanoflow activity to save the login object in the local mobile device storage(that is not possible in web for security reasons).

**Advanced java challenges:**

When extending the LoginAction class and trying to set parameters from this class in our extended class, we found out this was not possible in combination with the super.execute() method. We decided to use createSession. We have already validated the username and password in the first step and the MFA object can&#39;t be modified/created by the anonymous user (and is also checked twice).

## Please report issues

Have you found an issue or a vulnerability in this module, please reach out to [pim@appronto.nl](mailto:pim@appronto.nl). I will reward you with a nice goodie bag and will publish the new version to the Marketplace.
