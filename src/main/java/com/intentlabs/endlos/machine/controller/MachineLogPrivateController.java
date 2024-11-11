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
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.machine.enums.ErrorOrderParameterEnum;
import com.intentlabs.endlos.machine.operation.MachineLogOperation;
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.MachineView;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/private/machine-log")
public class MachineLogPrivateController extends AbstractController<MachineLogView> {

	@Autowired
	private MachineLogOperation machineLogOperation;

	@Override
	public BaseOperation<MachineLogView> getOperation() {
		return machineLogOperation;
	}

	@Override
	@AccessLog
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	public Response save(@RequestBody MachineLogView machineView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response update(@RequestBody MachineLogView machineView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE_LOG, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineLogOperation.doView(id);
	}

	@Override
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE_LOG, rights = RightsEnum.LIST)
	public Response search(@RequestBody MachineLogView machineView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.doSearch(machineView, start, recordSize, orderType, orderParam);
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
	public void isValidSaveData(MachineLogView view) throws EndlosAPIException {
		// do nothing
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (ErrorOrderParameterEnum machineOrderParameter : ErrorOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(machineOrderParameter.getId(), machineOrderParameter.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to export errors excel.
	 * 
	 * @param employeeView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE_LOG, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody MachineLogView machineLogView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
//		if (machineLogView.getMachineView() == null || machineLogView.getMachineView().getId() == null) {
//			throw new EndlosAPIException(ResponseCode.MACHINE_ID_IS_MISSING.getCode(),
//					ResponseCode.MACHINE_ID_IS_MISSING.getMessage());
//		}
//		if (machineLogView.getStartDate() == null || machineLogView.getEndDate() == null) {
//			throw new EndlosAPIException(ResponseCode.DATE_IS_MANDATORY.getCode(),
//					ResponseCode.DATE_IS_MANDATORY.getMessage());
//		}
		return machineLogOperation.doExport(machineLogView, orderType, orderParam);
	}

	@PostMapping(value = "/logistic-current-fullness-logs")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getLogisticCurrentFullnessLogs(@RequestBody MachineView machineView,
												   @RequestParam(name = "start", required = true) Integer start,
												   @RequestParam(name = "recordSize", required = true) Integer recordSize,
												   @RequestParam(name = "orderType", required = false) Integer orderType,
												   @RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.getLogisticCurrentFullnessLogs(machineView);
	}

	@PostMapping(value = "/logistic-pickup-logs-per-machine")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getLogisticPickupLogsPerMachine(@RequestBody MachineView machineView,
													@RequestParam(name = "start", required = true) Integer start,
													@RequestParam(name = "recordSize", required = true) Integer recordSize,
													@RequestParam(name = "orderType", required = false) Integer orderType,
													@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.getLogisticPickupLogsPerMachine(machineView);
	}

	@PostMapping(value = "/logistic-pickup-logs-per-route")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getLogisticPickupLogsPerRoute(@RequestBody MachineLogView machineView,
												  @RequestParam(name = "start", required = true) Integer start,
												  @RequestParam(name = "recordSize", required = true) Integer recordSize,
												  @RequestParam(name = "orderType", required = false) Integer orderType,
												  @RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.getLogisticPickupLogsPerRoute(machineView);
	}


	@RequestMapping(value = "/export-current-fullness-log", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	Response exportCurrentFullnessLog(@RequestBody MachineView machineView,
					@RequestParam(name = "orderType", required = false) Integer orderType,
					@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.doExportCurrentfullnessLog(machineView, orderType, orderParam);
	}

	@RequestMapping(value = "/export-pickup-logs-per-machine", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	Response exportPickupLogsPerMachine(@RequestBody MachineView machineView,
									  @RequestParam(name = "orderType", required = false) Integer orderType,
									  @RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.doExportPickupLogsperMachine(machineView, orderType, orderParam);
	}

	@RequestMapping(value = "/export-pickup-logs-per-route", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	Response exportPickupLogsPerRoute(@RequestBody MachineView machineView,
										@RequestParam(name = "orderType", required = false) Integer orderType,
										@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineLogOperation.doExportPickupLogsperRoute(machineView, orderType, orderParam);
	}
}