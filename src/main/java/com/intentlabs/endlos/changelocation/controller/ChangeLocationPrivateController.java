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
package com.intentlabs.endlos.changelocation.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.changelocation.enums.ChangeLocationStatus;
import com.intentlabs.endlos.changelocation.operation.ChangeLocationOperation;
import com.intentlabs.endlos.changelocation.view.ChangeLocationView;
import com.intentlabs.endlos.machine.enums.ChangeLocationOrderParameterEnum;

/**
 * This controller maps all change location related apis.
 * 
 * @author hemil.Shah
 * @since 07/09/2022
 */
@Controller
@RequestMapping("/private/change-location")
public class ChangeLocationPrivateController extends AbstractController<ChangeLocationView> {

	@Autowired
	private ChangeLocationOperation changeLocationOperation;

	@Override
	public BaseOperation<ChangeLocationView> getOperation() {
		return changeLocationOperation;
	}

	@Override
	@AccessLog
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	@AccessLog
	public Response save(@RequestBody ChangeLocationView machineView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response update(@RequestBody ChangeLocationView machineView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CHANGE_LOCATION, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return changeLocationOperation.doView(id);
	}

	@Override
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.CHANGE_LOCATION, rights = RightsEnum.LIST)
	public Response search(@RequestBody ChangeLocationView changeLocationView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return changeLocationOperation.doSearch(changeLocationView, start, recordSize, orderType, orderParam);
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
	public void isValidSaveData(ChangeLocationView view) throws EndlosAPIException {
		// do nothing
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (ChangeLocationOrderParameterEnum machineOrderParameter : ChangeLocationOrderParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(machineOrderParameter.getId(), machineOrderParameter.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to export changelocation excel.
	 * 
	 * @param changeLocationView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CHANGE_LOCATION, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody ChangeLocationView ChangeLocationView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return changeLocationOperation.doExport(ChangeLocationView, orderType, orderParam);
	}

	@GetMapping("/approve-reject")
	@AccessLog
	@ResponseBody
	@Authorization(modules = ModuleEnum.CHANGE_LOCATION, rights = RightsEnum.VIEW)
	public Response approveReject(@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "approve", required = true) Boolean approve) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return changeLocationOperation.doApproveOrReject(id, approve);
	}

	@PostMapping("/assign-changelocation")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CHANGE_LOCATION, rights = RightsEnum.VIEW)
	public Response assignChangeLocation(@RequestBody ChangeLocationView ChangeLocationView) throws EndlosAPIException {
		if (ChangeLocationView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (ChangeLocationView.getCustomerView() == null || ChangeLocationView.getCustomerView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.CUSTOMER_IS_MISSING.getCode(),
					ResponseCode.CUSTOMER_IS_MISSING.getMessage());
		}
		if (ChangeLocationView.getLocationView() == null || ChangeLocationView.getLocationView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.LOCATION_NAME_IS_MISSING.getCode(),
					ResponseCode.LOCATION_NAME_IS_MISSING.getMessage());
		}
		if (ChangeLocationView.getBarcodeTemplateView() == null
				|| ChangeLocationView.getBarcodeTemplateView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.BARCODE_IS_MISSING.getCode(),
					ResponseCode.BARCODE_IS_MISSING.getMessage());
		}
		if (ChangeLocationView.getNewBranchMachineNumber() == null) {
			throw new EndlosAPIException(ResponseCode.BRANCH_MACHINE_NUMBER_IS_MISSING.getCode(),
					ResponseCode.BRANCH_MACHINE_NUMBER_IS_MISSING.getMessage());
		}
		return changeLocationOperation.doAssignChangLocation(ChangeLocationView);
	}

	/**
	 * This method is used to get Change Location Status Enum as dropdown.
	 * 
	 * @Param
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-status")
	@ResponseBody
	@AccessLog
	public Response dropdownStatus() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (ChangeLocationStatus changeLocationStatus : ChangeLocationStatus.values()) {
			keyValueViews.add(KeyValueView.create(changeLocationStatus.getId(), changeLocationStatus.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}
}