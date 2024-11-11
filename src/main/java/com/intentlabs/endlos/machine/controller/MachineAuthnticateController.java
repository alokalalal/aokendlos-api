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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.endlos.machine.operation.MachineOperation;
import com.intentlabs.endlos.machine.view.TransactionView;

/**
 * This controller maps all Machine related apis.
 * 
 * @author hemil.Shah
 * @since 05/10/2021
 */
@Controller
@RequestMapping("/machine-req/machine")
public class MachineAuthnticateController {

	@Autowired
	private MachineOperation machineOperation;

	/**
	 * This method is used to validate barcode.
	 * 
	 * @param barcode
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping("/validate-barcode")
	@ResponseBody
	@AccessLog
	public Response validateBarcode(@RequestParam(name = "barcode") String barcode) throws EndlosAPIException {
		if (barcode == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doValidateBarcode(barcode);
	}

	/**
	 * This method is used to start Transaction.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping("/transaction-start")
	@ResponseBody
	@AccessLog
	public Response transactionStart() throws EndlosAPIException {
		return machineOperation.doTransactionStart();
	}

	/**
	 * This method is used to save Transaction.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping("/transaction-save")
	@ResponseBody
	@AccessLog
	public Response transactionSave(@RequestBody TransactionView transactionView) throws EndlosAPIException {
		if (transactionView == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (transactionView.getReason() != null) {
			Validator.STRING.isValid(new InputField("REASON", transactionView.getReason(), false, 256));
		}
		if (StringUtils.isEmpty(transactionView.getTransactionId())) {
			throw new EndlosAPIException(ResponseCode.TRANSACTION_ID_IS_MISSING.getCode(),
					ResponseCode.TRANSACTION_ID_IS_MISSING.getMessage());
		}
		return machineOperation.doTransactionSave(transactionView);
	}

	/**
	 * This method is used to close Transaction.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping("/transaction-close")
	@ResponseBody
	@AccessLog
	public Response transactionClose(@RequestParam(name = "transactionId", required = false) String transactionId)
			throws EndlosAPIException {
		if (transactionId == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doTransactionClose(transactionId);
	}

	/**
	 * This method is used to close Transaction.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping("/get-data")
	@ResponseBody
	@AccessLog
	public Response getData(@RequestParam(name = "transactionId", required = false) String transactionId)
			throws EndlosAPIException {
		if (transactionId == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doGetData(transactionId);
	}
}