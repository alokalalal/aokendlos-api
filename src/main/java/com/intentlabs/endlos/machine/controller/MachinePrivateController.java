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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.endlos.machine.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.machine.operation.MachineOperation;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This controller maps all Machine related apis.
 * 
 * @author hemil.Shah
 * @since 18/11/2021
 */
@Controller
@RequestMapping("/private/machine")
public class MachinePrivateController extends AbstractController<MachineView> {
	@Autowired
	private MachineOperation machineOperation;

	@Override
	public BaseOperation<MachineView> getOperation() {
		return machineOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return machineOperation.doAdd();
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.ADD)
	public Response save(@RequestBody MachineView machineView) throws EndlosAPIException {
		if (machineView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(machineView);
		try {
			return machineOperation.doSave(machineView);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.MACHINE_ID_ALREADY_EXIST.getCode(),
					ResponseCode.MACHINE_ID_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody MachineView machineView) throws EndlosAPIException {
		if (machineView == null || machineView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(machineView);
		try {
			return machineOperation.doUpdate(machineView);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.MACHINE_ID_ALREADY_EXIST.getCode(),
					ResponseCode.MACHINE_ID_ALREADY_EXIST.getMessage());
		}
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
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return getOperation().doDelete(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.ACTIVATION)
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (Auditor.getAuditor().getId().equals(id)) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getCode(),
					ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getMessage());
		}
		return machineOperation.doActiveInActive(id);
	}

	@Override
	public void isValidSaveData(MachineView machineView) throws EndlosAPIException {
		MachineView.isValid(machineView);
	}

	@PostMapping("/import-dataTable")
	@ResponseBody
	@AccessLog
	public Response importDataTable(@RequestBody MachineView machineView) throws EndlosAPIException {
		if (machineView.getIsApplicableForAll() == null) {
			throw new EndlosAPIException(ResponseCode.IS_APPLICABLE_FOR_ALL.getCode(),
					ResponseCode.IS_APPLICABLE_FOR_ALL.getMessage());
		}
		if (Boolean.FALSE.equals(machineView.getIsApplicableForAll())
				&& (machineView.getMachineViews() == null || machineView.getMachineViews().isEmpty())) {
			throw new EndlosAPIException(ResponseCode.MACHINE_ID_IS_MISSING.getCode(),
					ResponseCode.MACHINE_ID_IS_MISSING.getMessage());
		}
		return machineOperation.doImportDataTable(machineView);
	}

	@PostMapping("/assign-machine")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response assignMachine(@RequestBody MachineView machineView) throws EndlosAPIException {
		validateMachineCustomerLocation(machineView);
		return machineOperation.doAssignMachine(machineView);
	}

	/**
	 * This method is used to validate machine Customer Location.
	 * 
	 * @param machineView
	 * @throws EndlosAPIException
	 */
	private void validateMachineCustomerLocation(MachineView machineView) throws EndlosAPIException {
		if (machineView == null || machineView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (machineView.getCustomerView() == null || machineView.getCustomerView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.CUSTOMER_IS_MISSING.getCode(),
					ResponseCode.CUSTOMER_IS_MISSING.getMessage());
		}
		if (machineView.getLocationView() == null || machineView.getLocationView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.LOCATION_NAME_IS_MISSING.getCode(),
					ResponseCode.LOCATION_NAME_IS_MISSING.getMessage());
		}
//		Validator.STRING.isValid(new InputField("BRANCH_MACHINE_NUMBER", machineView.getBranchMachineNumber(), true, 1,
//				1, RegexEnum.NUMERIC));
	}

	@PostMapping(value = "/search")
	@ResponseBody
	@AccessLog
	@Override
	public Response search(@RequestBody MachineView machineView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		// return machineOperation.doSearch(machineView, start, recordSize, orderType,
		// orderParam);
		return machineOperation.machineList(machineView, start, recordSize, orderType, orderParam);
	}

	@PostMapping(value = "/search-dashboard")
	@ResponseBody
	@AccessLog
	public Response searchDashBoard(@RequestBody MachineView machineView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineOperation.machineListForDashboard(machineView, start, recordSize, orderType, orderParam);

	}

	@PostMapping(value = "/search-assign-barcode-template")
	@ResponseBody
	@AccessLog
	public Response searchAssignBarcodeTemplate(@RequestBody MachineView machineView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return machineOperation.machineListForAssignBarcodeTemplate(machineView, start, recordSize, orderType,
				orderParam);
	}

	@GetMapping(value = "/edit-assign-barcode-template")
	@ResponseBody
	@AccessLog
	public Response searchDashBoard(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {

		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doViewAssignBarcodeTemplate(id);

	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MachineOrderParameterEnum machineOrderParameter : MachineOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(machineOrderParameter.getId(), machineOrderParameter.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to get Machine Activity Status Enum as dropdown.
	 * 
	 * @Param
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-machine-activity-status")
	@ResponseBody
	@AccessLog
	public Response dropdownMachineActivityStatus() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MachineActivityStatusEnum machineActivityStatusEnum : MachineActivityStatusEnum.values()) {
			keyValueViews
					.add(KeyValueView.create(machineActivityStatusEnum.getId(), machineActivityStatusEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to get Machine Development Status Enum as dropdown.
	 * 
	 * @Param
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-machine-development-status")
	@ResponseBody
	@AccessLog
	public Response dropdownMachineDevelopmentStatus() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MachineDevelopmentStatusEnum machineDevelopmentStatusEnum : MachineDevelopmentStatusEnum.values()) {
			keyValueViews.add(
					KeyValueView.create(machineDevelopmentStatusEnum.getId(), machineDevelopmentStatusEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	@GetMapping("/dropdown")
	@ResponseBody
	@AccessLog
	public Response dropdown() {
		return machineOperation.doDropdown();
	}

	/**
	 * This method is used to export machines excel.
	 * 
	 * @param machineView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.TRANSACTION, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody MachineView machineView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		//return machineOperation.doExport(machineView, orderType, orderParam);

		CompletableFuture<Response> future = machineOperation.doExportAsync(machineView, orderType, orderParam);

		return future.thenApply(response -> {
			return response;
		}).exceptionally(ex -> {
			return CommonResponse.create(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to export file: " + ex.getMessage());
		}).join();
	}

	/**
	 * This method is used to get Machine Type Enum as dropdown.
	 * 
	 * @Param
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-machine-type")
	@ResponseBody
	@AccessLog
	public Response dropdownMachineTYpe() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MachineTypeEnum machineTypeEnum : MachineTypeEnum.values()) {
			keyValueViews.add(KeyValueView.create(machineTypeEnum.getId(), machineTypeEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	@PostMapping("/dropdown-by-location-id")
	@ResponseBody
	@AccessLog
	public Response dropdownByCustomer(@RequestBody MachineView machineView) throws EndlosAPIException {
		if (machineView.getLocationView() == null || machineView.getLocationView().getId() == null) {
			if (machineView.getCustomerView() == null || machineView.getCustomerView().getId() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
		}
		return machineOperation.doDropDownByLocation(machineView);
	}

	@PostMapping(value = "/get-last-branchwise-machine-no")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getLastBranchwiseMachineNo(@RequestBody MachineView machineView) throws EndlosAPIException {
		return machineOperation.getLastBranchwiseMachineNo(machineView);
	}

	@PostMapping(value = "/get-all-used-branch-wise-machine-no")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getAllSsedBranchWiseMachineNo(@RequestBody MachineView machineView) throws EndlosAPIException {
		return machineOperation.usedMachineNumberByLocation(machineView);
	}


	@GetMapping(value = "/dropdown-material-enum")
	@ResponseBody
	@AccessLog
	public Response dropdownMaterialEnum() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (MaterialEnum materialEnum : MaterialEnum.values()) {
			keyValueViews.add(KeyValueView.create(materialEnum.getId(), materialEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}
}