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
package com.intentlabs.endlos.logistic.controller;

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
import com.intentlabs.endlos.logistic.enums.PickupRouteOrderParameterEnum;
import com.intentlabs.endlos.logistic.operation.PickupRouteOperation;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller maps all Pickup Route related apis.
 * 
 * @author Milan.Gohil
 * @since 11/12/2023
 */
@Controller
@RequestMapping("/private/pickuproute")
public class PickupRoutePrivateController extends AbstractController<PickupRouteView> {

	@Autowired
	private PickupRouteOperation pickupRouteOperation;

	@Override
	public BaseOperation<PickupRouteView> getOperation() {
		return pickupRouteOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	// @Authorization(modules = ModuleEnum.PICKUP_ROUTE, rights = RightsEnum.ADD)
	public Response save(@RequestBody PickupRouteView pickupRouteView) throws EndlosAPIException {
		try {
			if (pickupRouteView == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			// isValidSaveData(pickupRouteView);
			return pickupRouteOperation.doSave(pickupRouteView);
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
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return pickupRouteOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return pickupRouteOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody PickupRouteView pickupRouteView) throws EndlosAPIException {
		try {
			if (pickupRouteView == null || pickupRouteView.getId() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			return pickupRouteOperation.doUpdate(pickupRouteView);
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
			return pickupRouteOperation.doDelete(id);
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
	public Response search(@RequestBody PickupRouteView pickupRouteView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return pickupRouteOperation.doSearch(pickupRouteView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(PickupRouteView pickupRouteView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@GetMapping("/dropdown")
	@ResponseBody
	@AccessLog
	public Response all() throws EndlosAPIException {
		return pickupRouteOperation.doDropdown();
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (PickupRouteOrderParameterEnum pickupRouteOrderParameterEnum : PickupRouteOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(pickupRouteOrderParameterEnum.getId(),
					pickupRouteOrderParameterEnum.getName()));
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
	Response export(@RequestBody PickupRouteView pickupRouteView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return pickupRouteOperation.doExport(pickupRouteView, orderType, orderParam);
	}

	@RequestMapping(value = "/daily-pickup-assignee", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.LIST)
	public Response dailyPickupAssignee(@RequestBody DailyPickupAssigneeView dailyPickupAssigneeView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		return pickupRouteOperation.doGetDailyPickupAssignee(dailyPickupAssigneeView, start, recordSize);
	}

	@RequestMapping(value = "/generate-plan", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.LIST)
	public Response dailyPickupAssignee(@RequestBody DailyPickupAssigneeView dailyPickupAssigneeView)
			throws EndlosAPIException {
		if (dailyPickupAssigneeView.getMachineViews() == null || dailyPickupAssigneeView.getMachineViews().isEmpty()) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return pickupRouteOperation.doGeneratePlan(dailyPickupAssigneeView);
	}
}