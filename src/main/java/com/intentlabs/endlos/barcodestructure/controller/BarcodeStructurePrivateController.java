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
package com.intentlabs.endlos.barcodestructure.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodestructure.enums.DynamicValueEnum;
import com.intentlabs.endlos.barcodestructure.operation.BarcodeStructureOperation;
import com.intentlabs.endlos.barcodestructure.view.BarcodeStructureView;

/**
 * This controller maps all Barcode Structure related apis.
 * 
 * @author hemil.Shah
 * @since 19/07/2022
 */
@Controller
@RequestMapping("/private/barcode-structure")
public class BarcodeStructurePrivateController extends AbstractController<BarcodeStructureView> {
	@Autowired
	private BarcodeStructureOperation barcodeStructureOperation;

	@Override
	public BaseOperation<BarcodeStructureView> getOperation() {
		return barcodeStructureOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return barcodeStructureOperation.doAdd();
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.ADD)
	public Response save(@RequestBody BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		if (barcodeStructureView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (barcodeStructureView.getBarcodeTemplateView() == null
				|| barcodeStructureView.getBarcodeTemplateView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(barcodeStructureView);
		try {
			return barcodeStructureOperation.doSave(barcodeStructureView);
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
		return barcodeStructureOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return barcodeStructureOperation.doEdit(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		if (barcodeStructureView == null || barcodeStructureView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(barcodeStructureView);
		try {
			return barcodeStructureOperation.doUpdate(barcodeStructureView);
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
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public void isValidSaveData(BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		BarcodeStructureView.isValid(barcodeStructureView);
	}

	@PostMapping(value = "/search")
	@ResponseBody
	@AccessLog
	@Override
	public Response search(@RequestBody BarcodeStructureView barcodeStructureView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return barcodeStructureOperation.doSearch(barcodeStructureView, start, recordSize, orderType, orderParam);
	}

	@GetMapping("/get-dynamic-value")
	@ResponseBody
	@AccessLog
	public Response getDynamicValueList() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (DynamicValueEnum DynamicValueEnum : DynamicValueEnum.values()) {
			keyValueViews.add(KeyValueView.create(DynamicValueEnum.getId(), DynamicValueEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

}