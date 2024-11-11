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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
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
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.enums.RoleTypeEnum;
import com.intentlabs.common.user.operation.RoleOperation;
import com.intentlabs.common.user.view.RoleModuleRightsView;
import com.intentlabs.common.user.view.RoleView;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.KeyValueView;

/**
 * This controller maps all Role related APIs.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@Controller
@RequestMapping("/private/role")
public class RolePrivateController extends AbstractController<RoleView> {

	@Autowired
	private RoleOperation roleOperation;

	@Override
	public BaseOperation<RoleView> getOperation() {
		return roleOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.ADD)
	public Response save(@RequestBody RoleView roleView) throws EndlosAPIException {
		if (roleView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}

		isValidSaveData(roleView);
		try {
			return roleOperation.doSave(roleView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.ROLE_ALREADY_EXIST.getCode(),
					ResponseCode.ROLE_ALREADY_EXIST.getMessage());
		}

	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody RoleView roleView) throws EndlosAPIException {
		if (roleView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (roleView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(roleView);
		return roleOperation.doUpdate(roleView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.LIST)
	public Response search(@RequestBody RoleView roleView, @RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return roleOperation.doSearch(roleView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(RoleView roleView) throws EndlosAPIException {
		Validator.STRING
				.isValid(new InputField("ROLE", roleView.getName(), true, 30, RegexEnum.ALPHA_NUMERIC_WITH_SPACE));
		if (!StringUtils.isBlank(roleView.getDescription())) {
			Validator.STRING.isValid(new InputField("DESCRIPTION", roleView.getDescription(), true, 256));
		}
		validateModuleRights(roleView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return roleOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return roleOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		try {
			return roleOperation.doDelete(id);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.CANT_DELETE_ROLE.getCode(),
					ResponseCode.CANT_DELETE_ROLE.getMessage());
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

	/**
	 * This method is used get list of roles without record size.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.ROLE, rights = RightsEnum.LIST)
	public Response dropdown() throws EndlosAPIException {
		return roleOperation.doSearch(new RoleView(), null, null, null, null);
	}

	/**
	 * This method is used get list of roles type without record size.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-type")
	@ResponseBody
	@AccessLog
	public Response dropdownType() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		RoleTypeEnum.MAP.forEach((key, value) -> keyValueViews.add(KeyValueView.create(key, value.getName())));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to validate module & rights data.
	 * 
	 * @param roleView
	 * @throws EndlosAPIException
	 */
	private void validateModuleRights(RoleView roleView) throws EndlosAPIException {
		if (roleView.getRoleModuleRightsViews() == null
				|| (roleView.getRoleModuleRightsViews() != null && roleView.getRoleModuleRightsViews().isEmpty())) {
			throw new EndlosAPIException(ResponseCode.MODULE_RIGHT_IS_MISSING.getCode(),
					ResponseCode.MODULE_RIGHT_IS_MISSING.getMessage());
		}
		for (RoleModuleRightsView roleModuleRightsView : roleView.getRoleModuleRightsViews()) {
			if (roleModuleRightsView.getModuleView() == null) {
				throw new EndlosAPIException(ResponseCode.MODULE_IS_MISSING.getCode(),
						ResponseCode.MODULE_IS_MISSING.getMessage());
			}
			if (roleModuleRightsView.getRightsView() == null) {
				throw new EndlosAPIException(ResponseCode.RIGHT_IS_MISSING.getCode(),
						ResponseCode.RIGHT_IS_MISSING.getMessage());
			}
			ModuleEnum moduleEnum = ModuleEnum.fromId(roleModuleRightsView.getModuleView().getId());
			if (moduleEnum == null) {
				throw new EndlosAPIException(ResponseCode.MODULE_IS_INVALID.getCode(),
						ResponseCode.MODULE_IS_INVALID.getMessage());
			}
			if (RightsEnum.fromId(roleModuleRightsView.getRightsView().getId()) == null) {
				throw new EndlosAPIException(ResponseCode.RIGHT_IS_INVALID.getCode(),
						ResponseCode.RIGHT_IS_INVALID.getMessage());
			}
		}
	}

	/**
	 * This method is used get list of role by user type.
	 * 
	 * @return
	 * @throws AxisIotAPIException
	 */
	@GetMapping(value = "/dropdown-by-user-type")
	@ResponseBody
	@AccessLog
	public Response dropdown(@RequestParam(name = "isCustomer", required = true) boolean isCustomer)
			throws EndlosAPIException {
		return roleOperation.doDropdown(isCustomer);
	}
}