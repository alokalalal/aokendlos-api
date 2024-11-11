/*******************************************************************************
 * Copyright -2018 @intentlabs
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
package com.intentlabs.common.notification.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.ArchiveView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This Class is used to represent email object in json/in client response
 * 
 * @author Nirav.Shah
 * @Since 12/07/2018
 */
@JsonInclude(Include.NON_NULL)
public class EmailAccountView extends ArchiveView {

	private static final long serialVersionUID = 2396198127017769592L;

	private String name;
	private String host;
	private String port;
	private String userName;
	private String password;
	private String replyToEmail;
	private String emailFrom;
	private Long ratePerHour;
	private Long updateRatePerHour;
	private Long ratePerDay;
	private Long updateRatePerDay;
	private KeyValueView authenticationMethod;
	private KeyValueView authenticationSecurity;
	private Long timeOut;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReplyToEmail() {
		return replyToEmail;
	}

	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailForm) {
		this.emailFrom = emailForm;
	}

	public Long getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(Long ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	public Long getUpdateRatePerHour() {
		return updateRatePerHour;
	}

	public void setUpdateRatePerHour(Long updateRatePerHour) {
		this.updateRatePerHour = updateRatePerHour;
	}

	public Long getRatePerDay() {
		return ratePerDay;
	}

	public void setRatePerDay(Long ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public Long getUpdateRatePerDay() {
		return updateRatePerDay;
	}

	public void setUpdateRatePerDay(Long updateRatePerDay) {
		this.updateRatePerDay = updateRatePerDay;
	}

	public KeyValueView getAuthenticationMethod() {
		return authenticationMethod;
	}

	public void setAuthenticationMethod(KeyValueView authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public KeyValueView getAuthenticationSecurity() {
		return authenticationSecurity;
	}

	public void setAuthenticationSecurity(KeyValueView authenticationSecurity) {
		this.authenticationSecurity = authenticationSecurity;
	}

	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}

}