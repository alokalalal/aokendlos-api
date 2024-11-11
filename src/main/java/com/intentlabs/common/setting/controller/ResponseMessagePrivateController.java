/*******************************************************************************
 * Copyright -2019 @Intentlbas
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
package com.intentlabs.common.setting.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.setting.enums.LocaleEnum;
import com.intentlabs.common.setting.operation.ResponseMessageOperation;
import com.intentlabs.common.setting.view.ResponseMessageView;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;

/**
 * This controller maps response message APIs.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@Controller
@RequestMapping("/private/response-message")
public class ResponseMessagePrivateController extends AbstractController<ResponseMessageView> {

	@Autowired
	private ResponseMessageOperation responseMessageOperation;

	@Override
	public BaseOperation<ResponseMessageView> getOperation() {
		return responseMessageOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.ADD)
	public Response save(@RequestBody ResponseMessageView responseMessageView) throws EndlosAPIException {
		if (responseMessageView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(responseMessageView);
		try {
			return responseMessageOperation.doSave(responseMessageView);
		} catch (DataIntegrityViolationException e) {
			throw new EndlosAPIException(ResponseCode.RESPONSE_MESSAGE_ALREADY_EXIST.getCode(),
					ResponseCode.RESPONSE_MESSAGE_ALREADY_EXIST.getMessage());
		}
	}

	/**
	 * This method is used to get data for view.
	 * 
	 * @param code
	 * @param locale
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/view-edit")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.VIEW)
	public Response viewEdit(@RequestParam(name = "code", required = true) Integer code,
			@RequestParam(name = "locale", required = true) String locale) throws EndlosAPIException {
		if (code == null) {
			throw new EndlosAPIException(ResponseCode.CODE_IS_MISSING.getCode(),
					ResponseCode.CODE_IS_MISSING.getMessage());
		}
		if (ResponseCode.fromId(code) == null) {
			throw new EndlosAPIException(ResponseCode.CODE_IS_INVALID.getCode(),
					ResponseCode.CODE_IS_INVALID.getMessage());
		}
		if (StringUtils.isEmpty(locale)) {
			throw new EndlosAPIException(ResponseCode.LANGUAGE_IS_MISSING.getCode(),
					ResponseCode.LANGUAGE_IS_MISSING.getMessage());
		}
		if (LocaleEnum.fromLanguageTag(locale) == null) {
			throw new EndlosAPIException(ResponseCode.LANGUAGE_IS_INVALID.getCode(),
					ResponseCode.LANGUAGE_IS_INVALID.getMessage());
		}
		return responseMessageOperation.doViewEdit(code, locale);
	}

	@Override
	@AccessLog
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody ResponseMessageView responseMessageView) throws EndlosAPIException {
		if (responseMessageView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(responseMessageView);
		try {
			return responseMessageOperation.doUpdate(responseMessageView);
		} catch (DataIntegrityViolationException e) {
			throw new EndlosAPIException(ResponseCode.RESPONSE_MESSAGE_ALREADY_EXIST.getCode(),
					ResponseCode.RESPONSE_MESSAGE_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.LIST)
	public Response search(@RequestBody ResponseMessageView responseMessageView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return responseMessageOperation.doSearch(responseMessageView, start, recordSize, orderType, orderParam);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.RESPONSE_MESSAGE, rights = RightsEnum.LIST)
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public void isValidSaveData(ResponseMessageView responseMessageView) throws EndlosAPIException {
		ResponseMessageView.isValid(responseMessageView);
	}
}