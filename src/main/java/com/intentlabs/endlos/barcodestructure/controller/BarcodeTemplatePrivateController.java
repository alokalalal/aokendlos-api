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
import com.intentlabs.endlos.barcodestructure.enums.BarcodeTemplateOrderEnum;
import com.intentlabs.endlos.barcodestructure.operation.BarcodeTemplateOperation;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller maps all Barcode Structure Template related apis.
 * 
 * @author hemil.Shah
 * @since 19/07/2022
 */
@Controller
@RequestMapping("/private/barcode-template")
public class BarcodeTemplatePrivateController extends AbstractController<BarcodeTemplateView> {
	@Autowired
	private BarcodeTemplateOperation barcodeTemplateOperation;

	@Override
	public BaseOperation<BarcodeTemplateView> getOperation() {
		return barcodeTemplateOperation;
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.BARCODE_STRUCTURE, rights = RightsEnum.ADD)
	public Response add() throws EndlosAPIException {
		return barcodeTemplateOperation.doAdd();
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.BARCODE_STRUCTURE, rights = RightsEnum.ADD)
	public Response save(@RequestBody BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		try {
			if (barcodeTemplateView == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			isValidSaveData(barcodeTemplateView);
			return barcodeTemplateOperation.doSave(barcodeTemplateView);
		} catch (ConstraintViolationException constraintViolationException) {
			LoggerService.exception(constraintViolationException);
			throw new EndlosAPIException(ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getCode(),
					ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.BARCODE_STRUCTURE, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return barcodeTemplateOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.BARCODE_STRUCTURE, rights = RightsEnum.ADD)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return barcodeTemplateOperation.doEdit(id);
	}

	@Override
	public void isValidSaveData(BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		BarcodeTemplateView.isValid(barcodeTemplateView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		if (barcodeTemplateView == null || barcodeTemplateView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(barcodeTemplateView);
		try {
			return barcodeTemplateOperation.doUpdate(barcodeTemplateView);
		} catch (ConstraintViolationException | DataIntegrityViolationException dataIntegrityViolationException) {
			LoggerService.exception(dataIntegrityViolationException);
			throw new EndlosAPIException(ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getCode(),
					ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getMessage());
		}
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.USER, rights = RightsEnum.DELETE)
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return barcodeTemplateOperation.doDelete(id);
	}

	@PostMapping(value = "/search")
	@ResponseBody
	@AccessLog
	@Override
	public Response search(@RequestBody BarcodeTemplateView barcodeTemplateView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return barcodeTemplateOperation.doSearch(barcodeTemplateView, start, recordSize, orderType, orderParam);
	}

	@GetMapping("/dropdown")
	@ResponseBody
	@AccessLog
	public Response dropdown() {
		return barcodeTemplateOperation.doDropdown();
	}

	@GetMapping("/get-order-parameter")
	@ResponseBody
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (BarcodeTemplateOrderEnum barcodeTemplateOrderEnum : BarcodeTemplateOrderEnum.values()) {
			keyValueViews
					.add(KeyValueView.create(barcodeTemplateOrderEnum.getId(), barcodeTemplateOrderEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to export transactions excel.
	 * 
	 * @param employeeView
	 * @return
	 * @throws EndlosAPIException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.TRANSACTION, rights = RightsEnum.DOWNLOAD)
	Response export(@RequestBody BarcodeTemplateView barcodeTemplateView,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return barcodeTemplateOperation.doExport(barcodeTemplateView, orderType, orderParam);
	}

	/**
	 * This method is used to assign Barcode template.
	 * 
	 * @param employeeView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping("/assign-machine")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	public Response assignMachine(@RequestBody BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		return barcodeTemplateOperation.doAssignMachine(barcodeTemplateView);
	}
}