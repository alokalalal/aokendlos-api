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
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 *
 * @author Hemil Shah.
 * @version 1.0
 * @since 13/06/2023
 */

public interface MachineLogOperation extends BaseOperation<MachineLogView> {

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(MachineLogView machineLogView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	Response getLogisticCurrentFullnessLogs(MachineView machineView) throws EndlosAPIException;

	Response getLogisticPickupLogsPerMachine(MachineView machineView);

	Response getLogisticPickupLogsPerRoute(MachineLogView machineLogView);

	Response doExportCurrentfullnessLog(MachineView machineView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	Response doExportPickupLogsperMachine(MachineView machineView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	Response doExportPickupLogsperRoute(MachineView machineView, Integer orderType, Integer orderParam) throws EndlosAPIException;
}