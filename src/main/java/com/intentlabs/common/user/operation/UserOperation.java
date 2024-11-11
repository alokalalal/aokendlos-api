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
package com.intentlabs.common.user.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.view.UserView;

/**
 * @author Dhruvang.Joshi
 * @since 30/11/2018
 */
public interface UserOperation extends BaseOperation<UserView> {

	/**
	 * Validate users credential, session and device to allow him to login into a
	 * system.
	 * 
	 * @param userView
	 * @param isLoginThroughEmail
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doLogin(UserView userView, boolean isLoginThroughEmail) throws EndlosAPIException;

	/**
	 * This method is used to remove user's auth token.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doLogout(String session) throws EndlosAPIException;

	/**
	 * It is used to validate user's email id and activate his/her account.
	 * 
	 * @param token
	 * @param isAdminLogin
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doActivate(String token) throws EndlosAPIException;

	/**
	 * This method is used to send reset password link.
	 * 
	 * @param userView
	 * @param isLoginThroughEmail
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doSendResetLink(UserView userView, boolean isLoginThroughEmail) throws EndlosAPIException;

	/**
	 * This method is used to reset user's password.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doResetPassword(UserView userView) throws EndlosAPIException;

	/**
	 * This method is used to change user's password.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doChangePassword(UserView userView) throws EndlosAPIException;

	/**
	 * This method validates User's reset password token.
	 * 
	 * @param token
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doResetPasswordVerification(String token) throws EndlosAPIException;

	/**
	 * This method is used to get islogged in.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doIsLoggedIn() throws EndlosAPIException;

	/**
	 * It is used to validate user's email id and activate his/her account.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doActivateThroughOTP(UserView userView) throws EndlosAPIException;

	/**
	 * It is used to resend activation OTP.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doResentActivationOTP() throws EndlosAPIException;

	/**
	 * This method is used to get list of users based on device cookie for given
	 * domain.
	 * 
	 * @param deviceCookie
	 * @param domain
	 * @return
	 */
	Response doAccessedByDevice(String deviceCookie, String domain) throws EndlosAPIException;

	/**
	 * this method will use to get new access token.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doGetAccessToken(UserView userView) throws EndlosAPIException;

	/**
	 * This method is used to set First time password change request
	 * 
	 * @param userView
	 * @return
	 */
	Response doFirstTimePasswordChange(UserView userView) throws EndlosAPIException;

	/**
	 * This method is used export users.
	 * 
	 * @return
	 */
	Response doExport(UserView userView, Integer orderType, Integer orderParam) throws EndlosAPIException;
}