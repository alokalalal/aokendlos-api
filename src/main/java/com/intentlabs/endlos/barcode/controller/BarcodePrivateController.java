///*******************************************************************************
// * Copyright -2019 @intentlabs
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License.  You may obtain a copy
// * of the License at
// * 
// *   http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
// * License for the specific language governing permissions and limitations under
// * the License.
// ******************************************************************************/
//package com.intentlabs.endlos.barcode.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.intentlabs.common.aop.AccessLog;
//import com.intentlabs.common.enums.ResponseCode;
//import com.intentlabs.common.exception.EndlosAPIException;
//import com.intentlabs.common.response.Response;
//import com.intentlabs.common.validation.InputField;
//import com.intentlabs.common.validation.Validator;
//import com.intentlabs.endlos.barcode.operation.BarcodeOperation;
//import com.intentlabs.endlos.barcode.view.BarcodeView;
//
///**
// * This controller maps all Machine related apis.
// * 
// * @author hemil.Shah
// * @since 18/11/2021
// */
//@Controller
//@RequestMapping("/private/barcode")
//public class BarcodePrivateController {
//	@Autowired
//	private BarcodeOperation barcodeOperation;
//
//	@GetMapping(value = "/add")
//	@AccessLog
//	@ResponseBody
//	public Response add() throws EndlosAPIException {
//		return barcodeOperation.doAdd();
//	}
//
//	@GetMapping(value = "/view")
//	@AccessLog
//	@ResponseBody
//	public Response view(@RequestParam(name = "machineId", required = true) Long machineId,
//			@RequestParam(name = "barcode", required = true) Long barcode) throws EndlosAPIException {
//		if (machineId == null || barcode == null) {
//			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
//					ResponseCode.INVALID_REQUEST.getMessage());
//		}
//		return barcodeOperation.doView(machineId, barcode);
//	}
//
//	@GetMapping(value = "/edit")
//	@AccessLog
//	@ResponseBody
//	public Response edit(@RequestParam(name = "machineId", required = true) Long machineId,
//			@RequestParam(name = "barcode", required = true) Long barcode) throws EndlosAPIException {
//		if (machineId == null || barcode == null) {
//			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
//					ResponseCode.INVALID_REQUEST.getMessage());
//		}
//		return barcodeOperation.doEdit(machineId, barcode);
//	}
//
//	@PutMapping(value = "/update")
//	@AccessLog
//	@ResponseBody
//	public Response update(@RequestBody BarcodeView barcodeView) throws EndlosAPIException {
//		if (barcodeView == null || barcodeView.getMachineView() == null) {
//			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
//					ResponseCode.INVALID_REQUEST.getMessage());
//		}
//		isValidUpdateData(barcodeView);
//		return barcodeOperation.doUpdate(barcodeView);
//	}
//
//	private void isValidUpdateData(BarcodeView barcodeView) throws EndlosAPIException {
//		Validator.STRING.isValid(new InputField("BARCODE", barcodeView.getBarcode(), true, 100));
//		Validator.STRING.isValid(new InputField("DISCRIPTION", barcodeView.getDescription(), true, 100));
//		Validator.STRING.isValid(new InputField("DATA_ACQUISITION", barcodeView.getDataAcquisition(), true, 10));
//		Validator.STRING.isValid(new InputField("ITEAM_REDEEM_VALUE", barcodeView.getIteamRedeemValue(), true, 10));
//		Validator.STRING.isValid(new InputField("VOLUMN", barcodeView.getVolumn(), true, 10));
//	}
//
//	@PostMapping(value = "/search")
//	@ResponseBody
//	@AccessLog
//	public Response search(@RequestBody BarcodeView barcodeView,
//			@RequestParam(name = "start", required = true) Integer start,
//			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
//		// if (barcodeView.getMachineView() == null) {
//		// throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
//		// ResponseCode.INVALID_REQUEST.getMessage());
//		// }
//		return barcodeOperation.doSearch(barcodeView, start, recordSize);
//	}
//}