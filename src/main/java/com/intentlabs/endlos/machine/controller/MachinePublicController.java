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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.machine.operation.MachineOperation;

/**
 * This controller maps all Machine related public apis.
 * 
 * @author hemil.Shah
 * @since 18/11/2021
 */
@Controller
@RequestMapping("/public/machine")
public class MachinePublicController {
	@Autowired
	private MachineOperation machineOperation;

	/**
	 * This method is used to validate serial number of machine.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping("/validate-machineid")
	@ResponseBody
	@AccessLog
	public Response validateMachineId(@RequestParam(name = "machineId", required = true) String machineId)
			throws EndlosAPIException {
		if (machineId == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doValidateMachineId(machineId);
	}

//	/**
//	 * This method is used to get barcode.
//	 * 
//	 * @return
//	 * @throws EndlosAPIException
//	 */
//	@GetMapping("/get-barcode")
//	@ResponseBody
//	@AccessLog
//	public byte[] getBarcode() throws EndlosAPIException {
//		return machineOperation.doBarcode();
//	}

	@GetMapping("/get-by-machineid")
	@ResponseBody
	@AccessLog
	public Response viewMachineByMachineId(@RequestParam(name = "machineId", required = true) String machineId)
			throws EndlosAPIException {
		if (machineId == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return machineOperation.doGetByMachineId(machineId);
	}

	@GetMapping("/get-all-machine-state")
	@ResponseBody
	@AccessLog
	public Response getAllMachineState() {

		return machineOperation.getAllMachineState();

	}
}