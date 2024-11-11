/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.systemspecification.controller;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.endlos.systemspecification.operation.SystemSpecificationOperation;
import com.intentlabs.endlos.systemspecification.view.SystemSpecificationView;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * This controller maps all Pickup Route related apis.
 * 
 * @author Milan.Gohil
 * @since 1/2/2024
 */
@Controller
@RequestMapping("/private/system-specification-detail")
public class SystemSpecificationPrivateController extends AbstractController<SystemSpecificationView> {

	@Autowired
	private SystemSpecificationOperation systemSpecificationOperation;

	@Override
	public BaseOperation<SystemSpecificationView> getOperation() {
		return systemSpecificationOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	public Response save(@RequestBody SystemSpecificationView  systemSpecificationView) throws EndlosAPIException {
		try {
			if (systemSpecificationView == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			return systemSpecificationOperation.doSave(systemSpecificationView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
				ResponseCode.INVALID_REQUEST.getMessage());

	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return systemSpecificationOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody SystemSpecificationView systemSpecificationView) throws EndlosAPIException {
		try {
			if (systemSpecificationView == null || systemSpecificationView.getId() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			return systemSpecificationOperation.doUpdate(systemSpecificationView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getCode(),
					ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		try {
			return systemSpecificationOperation.doDelete(id);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.CANT_DELETE_PICKUP_ROUTE.getCode(),
					ResponseCode.CANT_DELETE_PICKUP_ROUTE.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ACTIVATION)
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
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.LIST)
	public Response search(@RequestBody SystemSpecificationView systemSpecificationView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return systemSpecificationOperation.doSearch(systemSpecificationView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(SystemSpecificationView systemSpecificationView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	/**
	 * This method is used to export customers excel.
	 * 
	 * @param systemSpecificationView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody SystemSpecificationView systemSpecificationView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return systemSpecificationOperation.doExport(systemSpecificationView, orderType, orderParam);
	}
}