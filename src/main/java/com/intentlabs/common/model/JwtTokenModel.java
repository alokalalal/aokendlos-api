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
package com.intentlabs.common.model;

/**
 * This is Jwt Token model which maps jwt token references for every request.
 * 
 * @author Jaydip
 * @since 13/03/2021
 */
public class JwtTokenModel {

	private boolean isResetPasswordToken;
	private boolean isActivationToken;
	private boolean isLoginToken;
	private boolean isCaptchaToken;
	private boolean isTwoFactorToken;
	private String uniqueToken;
	private boolean isFirstLoginToken;
	private boolean isRegistrationToken;
	private boolean isMachineToken;

	public JwtTokenModel() {
		super();
	}

	public JwtTokenModel(boolean isResetPasswordToken, boolean isActivationToken, boolean isLoginToken,
			boolean isCaptchaToken, boolean isTwoFactorToken, boolean isFirstLoginToken, boolean isRegistrationToken,
			boolean isMachineToken) {
		super();
		this.isResetPasswordToken = isResetPasswordToken;
		this.isActivationToken = isActivationToken;
		this.isLoginToken = isLoginToken;
		this.isCaptchaToken = isCaptchaToken;
		this.isTwoFactorToken = isTwoFactorToken;
		this.isRegistrationToken = isRegistrationToken;
		this.isMachineToken = isMachineToken;
	}

	public static JwtTokenModel createResetPasswordToken() {
		return new JwtTokenModel(true, false, false, false, false, false, false, false);
	}

	public static JwtTokenModel createActivationToken() {
		return new JwtTokenModel(false, true, false, false, false, false, false, false);
	}

	public static JwtTokenModel createLoginToken() {
		return new JwtTokenModel(false, false, true, false, false, false, false, false);
	}

	public static JwtTokenModel createCaptchaToken() {
		return new JwtTokenModel(false, false, false, true, false, false, false, false);
	}

	public static JwtTokenModel createTwoFactorToken() {
		return new JwtTokenModel(true, false, false, false, true, false, false, false);
	}

	public static JwtTokenModel createFirstLoginToken() {
		return new JwtTokenModel(false, false, false, false, true, true, false, false);
	}

	public static JwtTokenModel createRegistrationToken() {
		return new JwtTokenModel(false, false, false, false, false, false, true, false);
	}

	public static JwtTokenModel createMachineToken() {
		return new JwtTokenModel(false, false, false, false, false, false, false, true);
	}

	public boolean isResetPasswordToken() {
		return isResetPasswordToken;
	}

	public void setResetPasswordToken(boolean isResetPasswordToken) {
		this.isResetPasswordToken = isResetPasswordToken;
	}

	public boolean isActivationToken() {
		return isActivationToken;
	}

	public void setActivationToken(boolean isActivationToken) {
		this.isActivationToken = isActivationToken;
	}

	public boolean isLoginToken() {
		return isLoginToken;
	}

	public void setLoginToken(boolean isLoginToken) {
		this.isLoginToken = isLoginToken;
	}

	public boolean isCaptchaToken() {
		return isCaptchaToken;
	}

	public void setCaptchaToken(boolean isCaptchaToken) {
		this.isCaptchaToken = isCaptchaToken;
	}

	public boolean isTwoFactorToken() {
		return isTwoFactorToken;
	}

	public void setTwoFactorToken(boolean isTwoFactorToken) {
		this.isTwoFactorToken = isTwoFactorToken;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	public boolean isFirstLoginToken() {
		return isFirstLoginToken;
	}

	public void setFirstLoginToken(boolean isFirstLoginToken) {
		this.isFirstLoginToken = isFirstLoginToken;
	}

	public boolean isRegistrationToken() {
		return isRegistrationToken;
	}

	public void setRegistrationToken(boolean isRegistrationToken) {
		this.isRegistrationToken = isRegistrationToken;
	}

	public boolean isMachineToken() {
		return isMachineToken;
	}

	public void setMachineToken(boolean isMachineToken) {
		this.isMachineToken = isMachineToken;
	}
}