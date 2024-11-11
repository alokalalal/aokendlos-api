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
package com.intentlabs.endlos.customer.controller;

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
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.enums.LocationOrderParameterEnum;
import com.intentlabs.endlos.customer.operation.LocationOperation;
import com.intentlabs.endlos.customer.view.LocationView;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller maps all location related apis.
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
@Controller
@RequestMapping("/private/location")
public class LocationPrivateController extends AbstractController<LocationView> {

	@Autowired
	private LocationOperation locationOperation;

	@Override
	public BaseOperation<LocationView> getOperation() {
		return locationOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.ADD)
	public Response save(@RequestBody LocationView locationView) throws EndlosAPIException {
		try {
			if (locationView == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			isValidSaveData(locationView);
			return locationOperation.doSave(locationView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.LOCATION_ALREADY_EXIST.getCode(),
					ResponseCode.LOCATION_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return locationOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return locationOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody LocationView locationView) throws EndlosAPIException {
		try {
			if (locationView == null || locationView.getId() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			isValidSaveData(locationView);
			return locationOperation.doUpdate(locationView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.LOCATION_ALREADY_EXIST.getCode(),
					ResponseCode.LOCATION_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		try {
			return locationOperation.doDelete(id);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.CANT_DELETE_BRANCH.getCode(),
					ResponseCode.CANT_DELETE_BRANCH.getMessage());
		}

	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.ACTIVATION)
	public Response activeInActive(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return locationOperation.doActiveInActive(id);
	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.LOCATION, rights = RightsEnum.LIST)
	public Response search(@RequestBody LocationView locationView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return locationOperation.doSearch(locationView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(LocationView locationView) throws EndlosAPIException {
		LocationView.isValid(locationView);
	}

	@PostMapping("/dropdown-by-customer-id")
	@ResponseBody
	@AccessLog
	public Response dropdownByCustomer(@RequestBody LocationView locationView) throws EndlosAPIException {
		if (locationView.getCustomerView() == null || locationView.getCustomerView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return locationOperation.doDropDownBycustomer(locationView);
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (LocationOrderParameterEnum locationOrderParameterEnum : LocationOrderParameterEnum.values()) {
			keyValueViews
					.add(KeyValueView.create(locationOrderParameterEnum.getId(), locationOrderParameterEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to export locations excel.
	 * 
	 * @param locationView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.TRANSACTION, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody LocationView locationView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return locationOperation.doExport(locationView, orderType, orderParam);
	}

	@GetMapping("/dropdown")
	@ResponseBody
	@AccessLog
	public Response dropdown() {
		return locationOperation.doDropdown();
	}
}