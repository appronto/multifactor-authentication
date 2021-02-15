package mfamodule.helpers;

import com.mendix.core.action.user.LoginAction;
import com.mendix.core.actionmanagement.ActionListener;


public class LoginActionListener extends ActionListener<LoginAction>{

	public LoginActionListener(Class<LoginAction> arg0) {
		super(arg0);
	}

	@Override
	public boolean check(LoginAction action) {
		return true;
	}
}
