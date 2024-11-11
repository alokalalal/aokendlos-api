package com.intentlabs.common.notification.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;

/**
 * This class is used to represent notification object in json/in customer
 * response
 * 
 * @author Nirav.Shah
 * @Since 23/05/2020
 */

@JsonInclude(Include.NON_NULL)
public class NotificationView extends IdentifierView {

	private static final long serialVersionUID = -7632495476077413752L;
	private String name;
	private boolean email;
	private boolean push;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}
}