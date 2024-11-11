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
package com.intentlabs.endlos.machine.controller;

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
import com.intentlabs.endlos.machine.enums.MachineCapacityParameterEnum;
import com.intentlabs.endlos.machine.operation.MachineCapacityOperation;
import com.intentlabs.endlos.machine.view.MachineCapacityView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller maps all Machine Logs related apis.
 * 
 * @author hemil.Shah
 * @since 13/06/2023
 */
@Controller
@RequestMapping("/private/machine-capacity")
public class MachineCapacityPrivateController extends AbstractController<MachineCapacityView> {

	@Autowired
	private MachineCapacityOperation machineCapacityOperation;

	@Override
	public BaseOperation<MachineCapacityView> getOperation() {
		return machineCapacityOperation;
	}

	@Override
	@AccessLog
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	public Response save(@RequestBody MachineCapacityView machineCapacityView) throws EndlosAPIException {
		if (machineCapacityView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(machineCapacityView);
		try {
			return machineCapacityOperation.doSave(machineCapacityView);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.MACHINE_ID_ALREADY_EXIST.getCode(),
					ResponseCode.MACHINE_ID_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	public Response update(@RequestBody MachineCapacityView machineCapacityView) throws EndlosAPIException {
		if (machineCapacityView == null || machineCapacityView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		try {
			return machineCapacityOperation.doUpdate(machineCapacityView);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE_LOG, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineCapacityOperation.doView(id);
	}

	@Override
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineCapacityOperation.doEdit(id);

	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE_LOG, rights = RightsEnum.LIST)
	public Response search(@RequestBody MachineCapacityView machineView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineCapacityOperation.doSearch(machineView, start, recordSize, orderType, orderParam);
	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public void isValidSaveData(MachineCapacityView view) throws EndlosAPIException {
		// do nothing
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MachineCapacityParameterEnum MachineCapacityParameterEnum : MachineCapacityParameterEnum.values()) {
			keyValueViews.add(
					KeyValueView.create(MachineCapacityParameterEnum.getId(), MachineCapacityParameterEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to update machine capacity.
	 * 
	 * @param machineCapacityView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/update-machine-capacity", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	Response export(@RequestBody MachineCapacityView machineCapacityView) throws EndlosAPIException {
		if (machineCapacityView.getMachineView() == null || machineCapacityView.getMachineView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.MACHINE_NAME_IS_MISSING.getCode(),
					ResponseCode.MACHINE_NAME_IS_MISSING.getMessage());
		}
		return machineCapacityOperation.doUpdateMachineCapacity(machineCapacityView);
	}
}