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
package com.intentlabs.common.user.controller;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.operation.UserOperation;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.util.Constant;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.util.WebUtil;
import com.intentlabs.common.validation.RegexEnum;

/**
 * This controller maps all user related apis.
 * 
 * @author Dhruvang.Joshi
 * @since 24/11/2018
 */
@Controller
@RequestMapping("/public/user")
public class UserPublicController {

	@Autowired
	private UserOperation userOperation;

	/**
	 * Validate users credentials and allow him to login into a system.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/login")
	@ResponseBody
	@AccessLog
	public Response login(@RequestBody UserView userView) throws EndlosAPIException {
		if (userView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		boolean isLoginThroughEmail = isValidLoginDetail(userView);
		if (StringUtils.isEmpty(userView.getPassword())) {
			throw new EndlosAPIException(ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getCode(),
					ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getMessage());
		}
		return userOperation.doLogin(userView, isLoginThroughEmail);
	}

	/**
	 * To Get Reset Password link.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/send-reset-link")
	@ResponseBody
	@AccessLog
	public Response sendResetLink(@RequestBody UserView userView) throws EndlosAPIException {
		if (userView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		boolean isLoginThroughEmail = isValidLoginDetail(userView);
		return userOperation.doSendResetLink(userView, isLoginThroughEmail);
	}

	/**
	 * This method is used to reset password token
	 * 
	 * @param token
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/reset-password-verification")
	@ResponseBody
	@AccessLog
	public Response resetPasswordVerification(@RequestParam("resetPasswordVerification") String token)
			throws EndlosAPIException {
		if (StringUtils.isBlank(token)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doResetPasswordVerification(token);
	}

	private boolean isValidLoginDetail(UserView userView) throws EndlosAPIException {
		if (StringUtils.isBlank(userView.getLoginId())) {
			throw new EndlosAPIException(ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getCode(),
					ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getMessage());
		}
		boolean isLoginThroughEmail = false;
		if (Utility.isValidPattern(userView.getLoginId(), RegexEnum.EMAIL.getValue())) {
			isLoginThroughEmail = true;
		}
		if (!isLoginThroughEmail && !Utility.isValidPattern(userView.getLoginId(), RegexEnum.PHONE_NUMBER.getValue())) {
			throw new EndlosAPIException(ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getCode(),
					ResponseCode.INVALID_EMAIL_OR_MOBILE_NUMBER.getMessage());
		}
		return isLoginThroughEmail;
	}

	/**
	 * This method is used to get list of users who has access the device based
	 * on domain key.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/accessed-by-device")
	@ResponseBody
	@AccessLog
	public Response accessedByDevice() throws EndlosAPIException {
		String deviceCookie = null;
		Cookie[] cookies = WebUtil.getCurrentRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constant.DEVICE_TOKEN.equals(cookie.getName())) {
					deviceCookie = cookie.getValue();
				}
			}
		}

		if (StringUtils.isBlank(deviceCookie)) {
			return CommonResponse.create(ResponseCode.DEVICE_NOT_USED.getCode(),
					ResponseCode.DEVICE_NOT_USED.getMessage());
		}
		return userOperation.doAccessedByDevice(deviceCookie, null);
	}

	/**
	 * This method is used to validate password.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/validate-password")
	@ResponseBody
	@AccessLog
	public Response validatePassword(@RequestBody UserView userView) throws EndlosAPIException {
		UserView.validatePassword(userView.getPassword());
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}
}