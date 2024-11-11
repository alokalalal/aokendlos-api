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
import com.intentlabs.endlos.machine.model.MachineLogModel;
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.MachineView;

import java.util.List;
import java.util.Map;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
public interface MachineLogService extends BaseService<MachineLogModel> {

	String MACHINE_LOG_MODEL = "machineLogModel";
	String MACHINE_LOG_MODEL_ALL_MACHINE_STATE = "machineLogModelForAllMachineState";

	/**
	 * This method is used to export transactions .
	 * 
	 * @param machineLogView
	 */
	List<MachineLogModel> doExport(MachineLogView machineLogView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param machineLogView
	 */
	MachineLogModel getLatestResetDate(Long id, int id2);
	Long getLastHardResetDateOfMachine(Long id,Long locationId);
	Long getLastSoftResetDateOfMachine(Long id,Long locationId);

	Long getLastHardResetDateOfLocation(Long id);

	Long getMaterialBinCount(Long id,int material,Long resetDate,Long locationId);

	List<MachineLogModel> getLatestResetDateToTillDateSoftReset();

	List<MachineLogModel> getLatestResetDateToTillDateSoftReset(Long machineId);

	List<MachineLogModel> getHighestHardResetDateOfEachMachine(MachineView machineView);

	List<MachineLogModel> getHighestHardResetDateOfEachRoute(String fullTextSearch);

	List<MachineLogModel> getLatestResetDateToTillDateSoftResetForLogPerRoute(Long locationId);


	MachineLogModel getLatestSoftResetDate(Long id, int materialId);

	Map<Integer, Long> getLatestHardResetDatesInSingleQuery(Long id);

	Map<Integer, Long> getLatestSoftResetDatesInSingleQuery(Long id);
}