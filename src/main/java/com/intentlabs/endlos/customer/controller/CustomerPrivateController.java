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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.enums.CustomerOrderParameterEnum;
import com.intentlabs.endlos.customer.operation.CustomerOperation;
import com.intentlabs.endlos.customer.view.CustomerView;

/**
 * This controller maps all customer related apis.
 * 
 * @author Hemil.Shah
 * @since 18/11/2021
 */
@Controller
@RequestMapping("/private/customer")
public class CustomerPrivateController extends AbstractController<CustomerView> {

	@Autowired
	private CustomerOperation customerOperation;

	@Override
	public BaseOperation<CustomerView> getOperation() {
		return customerOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ADD)
	public Response save(@RequestBody CustomerView customerView) throws EndlosAPIException {
		try {
			if (customerView == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			isValidSaveData(customerView);
			return customerOperation.doSave(customerView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.CUSTOMER_ALREADY_EXIST.getCode(),
					ResponseCode.CUSTOMER_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return customerOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return customerOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody CustomerView customerView) throws EndlosAPIException {
		try {
			if (customerView == null || customerView.getId() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			isValidSaveData(customerView);
			return customerOperation.doUpdate(customerView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.CUSTOMER_ALREADY_EXIST.getCode(),
					ResponseCode.CUSTOMER_ALREADY_EXIST.getMessage());
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
			return customerOperation.doDelete(id);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.CANT_DELETE_CUSTOMER.getCode(),
					ResponseCode.CANT_DELETE_CUSTOMER.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ACTIVATION)
	public Response activeInActive(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return customerOperation.doActiveInActive(id);
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
	public Response search(@RequestBody CustomerView customerView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return customerOperation.doSearch(customerView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(CustomerView customerView) throws EndlosAPIException {
		CustomerView.isValid(customerView);
		if (customerView.getUserView() == null) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"Spoc details " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
//		if (customerView.getUserView().getStateView() == null
//				|| customerView.getUserView().getStateView().getKey() == null) {
//			throw new EndlosAPIException(ResponseCode.STATE_IS_MISSING.getCode(),
//					ResponseCode.STATE_IS_MISSING.getMessage());
//		}
//		if (customerView.getUserView().getCityView() == null
//				|| customerView.getUserView().getCityView().getKey() == null) {
//			throw new EndlosAPIException(ResponseCode.CITY_IS_MISSING.getCode(),
//					ResponseCode.CITY_IS_MISSING.getMessage());
//		}
		UserView.isSpocPersonValid(customerView.getUserView());
	}

	@GetMapping("/dropdown")
	@ResponseBody
	@AccessLog
	public Response dropdown() {
		return customerOperation.doDropdown();
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (CustomerOrderParameterEnum customerOrderParameter : CustomerOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(customerOrderParameter.getId(), customerOrderParameter.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to export customers excel.
	 * 
	 * @param customerView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody CustomerView customerView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return customerOperation.doExport(customerView, orderType, orderParam);
	}
}