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
package com.intentlabs.endlos.machine.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.TransactionView;

import java.util.concurrent.CompletableFuture;

/**
 * @author Hemil Shah.
 * @version 1.0
 * @since 18/11/2021
 */
public interface MachineOperation extends BaseOperation<MachineView> {
	/**
	 * This method is used to import data table.
	 * 
	 * @param fileId
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doImportDataTable(MachineView machineView) throws EndlosAPIException;

	/**
	 * This method is used to validate barcode.
	 * 
	 * @param barcode
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doValidateBarcode(String barcode) throws EndlosAPIException;

	/**
	 * This method is used to start transaction.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doTransactionStart() throws EndlosAPIException;

	/**
	 * This method is used to save transaction.
	 * 
	 * @param barcode
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doTransactionSave(TransactionView transactionView) throws EndlosAPIException;

	/**
	 * This method is used to close transaction.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doTransactionClose(String transactionId) throws EndlosAPIException;

	/**
	 * This method is used to get transaction data.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doGetData(String transactionId) throws EndlosAPIException;

	/**
	 * This method is used to assign machine to Customer and location.
	 * 
	 * @param machineView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doAssignMachine(MachineView machineView) throws EndlosAPIException;

	/**
	 * This method is used to validate serial number of machine.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doValidateMachineId(String machineId) throws EndlosAPIException;

	/**
	 * This method is used to get barcode.
	 * 
	 * @param transactionId
	 * @return
	 * @throws EndlosAPIException
	 */
//	byte[] doBarcode() throws EndlosAPIException;

	/**
	 * This method is used get list of machine.
	 * 
	 * @return
	 */
	Response doDropdown();

	/**
	 * This method is used export machines.
	 * 
	 * @return
	 */
	Response doExport(MachineView machineView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	Response doGetByMachineId(String machineID) throws EndlosAPIException;

	Response doDropDownByLocation(MachineView machineView);

	Response getLastBranchwiseMachineNo(MachineView machineView) throws EndlosAPIException;

	Response usedMachineNumberByLocation(MachineView machineView) throws EndlosAPIException;

    Response machineList(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	Response machineListForDashboard(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	Response machineListForAssignBarcodeTemplate(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	Response doViewAssignBarcodeTemplate(Long id) throws EndlosAPIException;

	CompletableFuture<Response> doExportAsync(MachineView machineView, Integer orderType, Integer orderParam);

	Response getAllMachineState();
}