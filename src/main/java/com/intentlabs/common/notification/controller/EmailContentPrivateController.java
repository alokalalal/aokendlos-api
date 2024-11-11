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

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.intentlabs.common.notification.enums.CommunicationFields;
import com.intentlabs.common.notification.operation.EmailContentOperation;
import com.intentlabs.common.notification.view.EmailContentView;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;

/**
 * This controller maps all emailcontent related apis
 * 
 * @author Nirav.Shah
 * @since 23/07/2018
 *
 */
@Controller
@RequestMapping("/private/email-content")
public class EmailContentPrivateController extends AbstractController<EmailContentView> {

	@Autowired
	EmailContentOperation emailContentOperation;

	@Override
	public BaseOperation<EmailContentView> getOperation() {
		return emailContentOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response save(@RequestBody EmailContentView emailContentView) throws EndlosAPIException {
		if (emailContentView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(emailContentView);
		try {
			return emailContentOperation.doSave(emailContentView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getCode(),
					ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response view(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return emailContentOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return emailContentOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody EmailContentView emailContentView) throws EndlosAPIException {
		if (emailContentView == null || emailContentView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(emailContentView);
		return emailContentOperation.doUpdate(emailContentView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response delete(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return emailContentOperation.doDelete(id);
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.LIST)
	public Response search(@RequestBody EmailContentView emailContentView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return emailContentOperation.doSearch(emailContentView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(EmailContentView emailContentView) throws EndlosAPIException {
		EmailContentView.isValid(emailContentView);
	}

	@GetMapping(value = "/communication-field")
	@ResponseBody
	@AccessLog
	public Response communicationFields() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		CommunicationFields.MAP.forEach((key, value) -> keyValueViews.add(KeyValueView.create(key, value.getName())));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}
}