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
package com.intentlabs.common.setting.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.setting.operation.SystemSettingOperation;
import com.intentlabs.common.setting.view.SystemSettingView;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.Validator;

/**
 * This controller maps all setting related apis.
 * 
 * @author Nirav
 * @since 24/11/2018
 */
@Controller
@RequestMapping("/private/system-setting")
public class SystemSettingPrivateController {

	@Autowired
	SystemSettingOperation systemSettingOperation;

	/**
	 * This method is used to handle search request coming from client for any
	 * module.
	 * 
	 * @param systemSettingView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/search")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.SETTING, rights = RightsEnum.LIST)
	public Response search(@RequestBody(required = false) SystemSettingView systemSettingView, Integer start,
			Integer recordSize) throws EndlosAPIException {
		return systemSettingOperation.doSearch(systemSettingView, start, recordSize);
	}

	/**
	 * This method is used to handle update request coming from client for any
	 * module.
	 * 
	 * @param systemSettingView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PutMapping(value = "/update")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.SETTING, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody SystemSettingView systemSettingView) throws EndlosAPIException {
		if (systemSettingView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(systemSettingView);
		return systemSettingOperation.doUpdate(systemSettingView);
	}

	public void isValidSaveData(SystemSettingView systemSettingView) throws EndlosAPIException {
		Validator.STRING.isValid(new InputField("PROPERTY", systemSettingView.getKey(), true, 100));
		Validator.STRING.isValid(new InputField("PROPERTY_VALUE", systemSettingView.getValue(), true, 100));
	}

	/**
	 * This method is used to handle view request coming from client for any
	 * module.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/view")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.SETTING, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "key", required = true) String key) throws EndlosAPIException {
		if (StringUtils.isBlank(key)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return systemSettingOperation.doView(key);
	}

	/**
	 * This method is used to handle edit request coming from client for any
	 * module.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/edit")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.SETTING, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "key", required = true) String key) throws EndlosAPIException {
		if (StringUtils.isBlank(key)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return systemSettingOperation.doEdit(key);
	}

	/**
	 * This method is used to handle update bulk request coming from client for
	 * any module.
	 * 
	 * @param systemSettingViews
	 * @return
	 * @throws EndlosAPIException
	 */
	@PutMapping(value = "/updatebulk")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.SETTING, rights = RightsEnum.UPDATE)
	public Response updateBulk(@RequestBody List<SystemSettingView> systemSettingViews) throws EndlosAPIException {
		if (systemSettingViews == null || systemSettingViews.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		for (SystemSettingView systemSettingView : systemSettingViews) {
			isValidSaveData(systemSettingView);
		}
		return systemSettingOperation.doUpdateBulk(systemSettingViews);
	}
}