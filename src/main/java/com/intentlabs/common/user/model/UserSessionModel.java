/*******************************************************************************
 * Copyright -2019 @intentlabs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.intentlabs.common.user.model;

import com.intentlabs.common.model.IdentifierModel;

/**
 * This is User Session model which maps user session table to class.
 * 
 * @author Vishwa.Shah
 * @since 08/02/2018
 *
 */
public class UserSessionModel extends IdentifierModel {
	private static final long serialVersionUID = -2950420391918498866L;

	private String browser;
	private String os;
	private String ip;
	private String deviceCookie;
	private Long deviceCookieDate;
	private Long lastLoginDate;
	private UserModel userModel;

	public UserSessionModel() {
		super();
	}

	public UserSessionModel(String browser, String os, String ip, String deviceCookie, Long deviceCookieDate,
			Long lastLoginDate, UserModel userModel) {
		super();
		this.browser = browser;
		this.os = os;
		this.ip = ip;
		this.deviceCookie = deviceCookie;
		this.deviceCookieDate = deviceCookieDate;
		this.lastLoginDate = lastLoginDate;
		this.userModel = userModel;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceCookie() {
		return deviceCookie;
	}

	public void setDeviceCookie(String deviceCookie) {
		this.deviceCookie = deviceCookie;
	}

	public Long getDeviceCookieDate() {
		return deviceCookieDate;
	}

	public void setDeviceCookieDate(Long deviceCookieDate) {
		this.deviceCookieDate = deviceCookieDate;
	}

	public Long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}