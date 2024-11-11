/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.notification.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.notification.enums.EmailAuthenticationMethod;
import com.intentlabs.common.notification.enums.EmailAuthenticationSecurity;
import com.intentlabs.common.notification.operation.EmailAccountOperation;
import com.intentlabs.common.notification.view.EmailAccountView;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.KeyValueView;

/**
 * This Controller Maps all Email Account related Apis
 * 
 * @author Nisha.Panchal
 * @since 17/07/2018
 *
 */
@Controller
@RequestMapping("/private/email-account")
public class EmailAccountPrivateController extends AbstractController<EmailAccountView> {

	@Autowired
	EmailAccountOperation emailAccountOperation;

	@Override
	public BaseOperation<EmailAccountView> getOperation() {
		return emailAccountOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.ADD)
	public Response save(@RequestBody EmailAccountView emailAccountView) throws EndlosAPIException {
		if (emailAccountView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(emailAccountView);
		return emailAccountOperation.doSave(emailAccountView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody EmailAccountView emailAccountView) throws EndlosAPIException {
		if (emailAccountView == null || emailAccountView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(emailAccountView);
		return emailAccountOperation.doUpdate(emailAccountView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.LIST)
	public Response search(@RequestBody EmailAccountView emailAccountView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return emailAccountOperation.doSearch(emailAccountView, start, recordSize, orderType, orderParam);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return emailAccountOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return emailAccountOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.EMAIL_ACCOUNT, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		try {
			return emailAccountOperation.doDelete(id);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DELETE_EMAIL_ACCOUNT.getCode(),
					ResponseCode.UNABLE_TO_DELETE_EMAIL_ACCOUNT.getMessage());
		}
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public void isValidSaveData(EmailAccountView emailAccountView) throws EndlosAPIException {
		Validator.STRING.isValid(new InputField("EMAIL_ACCOUNT_NAME", emailAccountView.getName(), true, 100));
		Validator.STRING.isValid(new InputField("HOST", emailAccountView.getHost(), true, 500));
		Validator.STRING.isValid(new InputField("PORT", emailAccountView.getPort(), true, 0, 65555, RegexEnum.NUMERIC));
		Validator.STRING.isValid(new InputField("EMAIL_USER_NAME", emailAccountView.getUserName(), true, 100));
		Validator.STRING.isValid(new InputField("PASSWORD", emailAccountView.getPassword(), true, 6, 500));
		Validator.STRING
				.isValid(new InputField("REPLY_TO", emailAccountView.getReplyToEmail(), true, 100, RegexEnum.EMAIL));
		Validator.STRING.isValid(new InputField("EMAIL_FROM", emailAccountView.getEmailFrom(), true, 500));
		if (emailAccountView.getRatePerHour() != null) {
			Pattern pattern = Pattern.compile(RegexEnum.NUMERIC.getValue());
			Matcher matcher = pattern.matcher(String.valueOf(emailAccountView.getRatePerHour()));
			if (!matcher.matches()) {
				throw new EndlosAPIException(ResponseCode.RATE_PER_HOUR_INVALID.getCode(),
						ResponseCode.RATE_PER_HOUR_INVALID.getMessage());
			}

		}

		if (emailAccountView.getRatePerDay() != null) {
			Pattern pattern = Pattern.compile(RegexEnum.NUMERIC.getValue());
			Matcher matcher = pattern.matcher(String.valueOf(emailAccountView.getRatePerDay()));
			if (!matcher.matches()) {
				throw new EndlosAPIException(ResponseCode.RATE_PER_DAY_INVALID.getCode(),
						ResponseCode.RATE_PER_DAY_INVALID.getMessage());
			}
		}

		if (emailAccountView.getAuthenticationMethod() == null
				|| emailAccountView.getAuthenticationMethod().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_METHOD_IS_MISSING.getCode(),
					ResponseCode.AUTHENTICATION_METHOD_IS_MISSING.getMessage());
		}
		if (EmailAuthenticationMethod.fromId(
				Integer.parseInt(String.valueOf(emailAccountView.getAuthenticationMethod().getKey()))) == null) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_METHOD_IS_INVALID.getCode(),
					ResponseCode.AUTHENTICATION_METHOD_IS_INVALID.getMessage());
		}
		if (emailAccountView.getAuthenticationSecurity() == null
				|| emailAccountView.getAuthenticationSecurity().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_SECURITY_IS_MISSING.getCode(),
					ResponseCode.AUTHENTICATION_SECURITY_IS_MISSING.getMessage());
		}
		if (EmailAuthenticationSecurity.fromId(
				Integer.parseInt(String.valueOf(emailAccountView.getAuthenticationSecurity().getKey()))) == null) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_SECURITY_IS_INVALID.getCode(),
					ResponseCode.AUTHENTICATION_SECURITY_IS_INVALID.getMessage());
		}
		if (emailAccountView.getTimeOut() != null) {
			Pattern pattern = Pattern.compile(RegexEnum.NUMERIC.getValue());
			Matcher matcher = pattern.matcher(String.valueOf(emailAccountView.getTimeOut()));
			if (!matcher.matches()) {
				throw new EndlosAPIException(ResponseCode.TIMEOUT_IS_INVALID.getCode(),
						ResponseCode.TIMEOUT_IS_INVALID.getMessage());
			}
		}
	}

	@GetMapping(value = "/auth-method")
	@ResponseBody
	@AccessLog
	public Response authMethod() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		EmailAuthenticationMethod.MAP
				.forEach((key, value) -> keyValueViews.add(KeyValueView.create(key, value.getName())));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	@GetMapping(value = "/auth-security")
	@ResponseBody
	@AccessLog
	public Response authSecurity() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		EmailAuthenticationSecurity.MAP
				.forEach((key, value) -> keyValueViews.add(KeyValueView.create(key, value.getName())));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	@GetMapping(value = "/dropdown")
	@ResponseBody
	@AccessLog
	public Response dropdown() {
		return emailAccountOperation.doDropdown();
	}
}