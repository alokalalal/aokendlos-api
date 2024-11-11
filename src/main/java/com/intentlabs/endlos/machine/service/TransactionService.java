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
package com.intentlabs.endlos.machine.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.machine.model.TransactionModel;
import com.intentlabs.endlos.machine.view.TransactionSummary;
import com.intentlabs.endlos.machine.view.TransactionView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 08/10/2021
 */
public interface TransactionService extends BaseService<TransactionModel> {

	String TRANSACTION_MODEL = "transactionModel";
	String LIGHT_TRANSACTION_MODEL = "lightTransactionModel";
	String TRANSACTION_MODEL_FOR_ALL_MACHINE_STATE = "extraLightTransactionModelForAllMachineState";

	/**
	 * This method is used to get TransactionModel By transcationId .
	 * 
	 * @param transcationId
	 */
	TransactionModel getByTransactionId(String transactionId);

	/**
	 * This method is used to get TransactionModel By transcationId .
	 * 
	 * @param transcationId
	 * @return
	 */
	List<TransactionModel> getListByTransactionId(String transactionId);

	/**
	 * This method is used to get last TransactionModel By machineId .
	 * 
	 * @param machineId
	 */
	TransactionModel getLastTransactionByMachineId(Long machineId);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param transactionView
	 */
	List<TransactionModel> doExport(TransactionView transactionView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param transactionView
	 */
	TransactionSummary findAllTransaction(TransactionView transactionView);

	List<TransactionModel> fetchTransaction();

	Map<String, Long> getTotalBottleCount(Long id, Long resetDate, Long locationId);

	Long getTotalVoucher(Long id, Long hardResetDateOfMachine, Long locationId);

	BigDecimal getTotalWeight(Long id, Long hardResetDateOfMachine, Long locationId);

	Map<String, Long> getTotalBottleCountByMachineIdAndLocationIdAndLastDateOfTransaction(Long id, Long resetDate, Long locationId, Long endDate);

	Long getLastTransactionByMachineIdForAllMachineState(Long machineId);
}