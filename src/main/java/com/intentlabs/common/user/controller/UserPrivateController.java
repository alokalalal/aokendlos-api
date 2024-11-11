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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.enums.UserGenderEnum;
import com.intentlabs.common.user.enums.UserOrderParameterEnum;
import com.intentlabs.common.user.operation.UserOperation;
import com.intentlabs.common.user.view.RoleView;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This controller maps all user related apis.
 * 
 * @author Dhruvang.Joshi
 * @since 24/11/2018
 */
@Controller
@RequestMapping("/private/user")
public class UserPrivateController extends AbstractController<UserView> {

	@Autowired
	private UserOperation userOperation;

	@Override
	public BaseOperation<UserView> getOperation() {
		return userOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.ADD)
	public Response save(@RequestBody UserView userView) throws EndlosAPIException {
		if (userView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(userView);
		return userOperation.doSave(userView);
	}

	@Override
	@AccessLog
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doView(id);
	}

	@Override
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doEdit(id);
	}

	@Override
	@AccessLog
	public Response update(@RequestBody UserView userView) throws EndlosAPIException {
		if (userView == null || userView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidUpdateData(userView);
		try {
			return userOperation.doUpdate(userView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.USER_ALREADY_EXIST.getCode(),
					ResponseCode.USER_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doDelete(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.LIST)
	public Response search(@RequestBody UserView userView, @RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return userOperation.doSearch(userView, start, recordSize, orderType, orderParam);
	}

	/**
	 * Remove user's current session.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/logout")
	@ResponseBody
	@AccessLog
	public Response logout(HttpServletRequest httpServletRequest) throws EndlosAPIException {
		String jwtAccesssTokenHeader = httpServletRequest.getHeader("Authorization");
		String[] accessToken = jwtAccesssTokenHeader.split(" ");
		String jwtAccessToken = accessToken[1];
		return userOperation.doLogout(jwtAccessToken);
	}

	/**
	 * This method is used to set a new password.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/reset-password")
	@ResponseBody
	@AccessLog
	public Response resetPassword(@RequestBody UserView userView) throws EndlosAPIException {
		validatePasswordDetails(userView);
		return userOperation.doResetPassword(userView);
	}

	/**
	 * This method is used to change user's password.
	 * 
	 * @param userView
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/change-password")
	@ResponseBody
	@AccessLog
	public Response changePassword(@RequestBody UserView userView) throws EndlosAPIException {
		if (StringUtils.isBlank(userView.getOldPassword())) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_IS_MISSING.getCode(),
					ResponseCode.PASSWORD_IS_MISSING.getMessage());
		}
		validatePasswordDetails(userView);
		UserView.validatePassword(userView.getPassword());
		return userOperation.doChangePassword(userView);
	}

	private void validatePasswordDetails(UserView userView) throws EndlosAPIException {
		if (userView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		UserView.validatePassword(userView.getPassword());
		if (!userView.getPassword().equals(userView.getConfirmPassword())) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_NOT_MATCH.getCode(),
					ResponseCode.PASSWORD_NOT_MATCH.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.LIST)
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.ACTIVATION)
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (Auditor.getAuditor().getId().equals(id)) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getCode(),
					ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getMessage());
		}
		return userOperation.doActiveInActive(id);
	}

	/**
	 * This method is used to check session of user.
	 * 
	 * @param token
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/is-loggedIn")
	@ResponseBody
	@AccessLog
	public Response isLoggedIn() throws EndlosAPIException {
		return userOperation.doIsLoggedIn();
	}

	@Override
	public void isValidSaveData(UserView userView) throws EndlosAPIException {
		UserView.isValid(userView);
		if (userView.getRoleViews() == null || userView.getRoleViews().isEmpty()) {
			throw new EndlosAPIException(ResponseCode.ROLE_IS_MISSING.getCode(),
					ResponseCode.ROLE_IS_MISSING.getMessage());
		}
		for (RoleView roleView : userView.getRoleViews()) {
			if (roleView != null && roleView.getId() == null) {
				throw new EndlosAPIException(ResponseCode.ROLE_IS_MISSING.getCode(),
						ResponseCode.ROLE_IS_MISSING.getMessage());
			}
		}
		isValidOtherData(userView);
	}

	public void isValidUpdateData(UserView userView) throws EndlosAPIException {
		UserView.isValid(userView);
		if (userView.getRoleViews() != null && !userView.getRoleViews().isEmpty()) {
			for (RoleView roleView : userView.getRoleViews()) {
				if (roleView != null && roleView.getId() == null) {
					throw new EndlosAPIException(ResponseCode.ROLE_IS_MISSING.getCode(),
							ResponseCode.ROLE_IS_MISSING.getMessage());
				}
			}
		}
		isValidOtherData(userView);
	}

	private void isValidOtherData(UserView userView) throws EndlosAPIException {
		if (userView.getProfilepic() != null && StringUtils.isBlank(userView.getProfilepic().getFileId())) {
			throw new EndlosAPIException(ResponseCode.PROFILE_IS_INVALID.getCode(),
					ResponseCode.PROFILE_IS_INVALID.getMessage());
		}
	}

	/**
	 * This method is used to get users gender.
	 * 
	 * @Param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-gender")
	@ResponseBody
	@AccessLog
	public Response dropdownGender() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		UserGenderEnum.MAP.forEach((key, value) -> keyValueViews.add(KeyValueView.create(key, value.getName())));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * this api will use to get new access token when it is expired.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/get-access-token")
	@ResponseBody
	@AccessLog
	public Response getAccessToken(@RequestBody UserView userView) throws EndlosAPIException {
		if (userView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (StringUtils.isBlank(userView.getAccessToken()) || StringUtils.isBlank(userView.getRefreshToken())) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doGetAccessToken(userView);
	}

	/**
	 * This method is used to validate user's email id.
	 * 
	 * @param otp
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/activate")
	@ResponseBody
	@AccessLog
	public Response activate(@RequestParam(value = "verificationOtp") String otp) throws EndlosAPIException {
		if (StringUtils.isBlank(otp)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return userOperation.doActivate(otp);
	}

	/**
	 * This method is used to export users excel.
	 * 
	 * @param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody UserView userView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return userOperation.doExport(userView, orderType, orderParam);
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (UserOrderParameterEnum machineOrderParameter : UserOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(machineOrderParameter.getId(), machineOrderParameter.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}
}