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

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.view.MachineView;

import java.util.List;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 18/11/2021
 */
public interface MachineService extends BaseService<MachineModel> {

	String MACHINE_MODEL = "machineModel";
	String LIGHT_MACHINE_MODEL = "lightMachineModel";
	String EXTRA_LIGHT_MACHINE_MODEL = "extraLightMachineModel";

	String SUPER_LIGHT_MACHINE_MODEL = "superLightMachineModel";

	String EXTRA_SMALL_MACHINE_MODEL = "extraSmallMachineModel";
	String MACHINE_MODEL_ALL_STATE = "lightMachineModelForAllMachineState";
	/**
	 * This method is used to get MachineModel By machineId .
	 * 
	 * @param machineId
	 */
	MachineModel getByMachineId(String machineId);

	/**
	 * This method is used to get MachineModel By machineId .
	 * 
	 * @param machineView
	 */
	List<MachineModel> findAllMachine(MachineView machineView);

	/**
	 * This method is used to export machines .
	 * 
	 * @param machineView
	 */
	List<MachineModel> doExport(MachineView machineView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to find machines by barcode template .
	 * 
	 * @param barcodeTemplateId
	 */
	List<MachineModel> findByBarcodeTemplateId(Long barcodeTemplateId);

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	List<MachineModel> findAll();

	MachineModel getLastMachine();

	List<MachineModel> getByLocation(Long id);

	List<MachineModel> getByCustomer(Long id);

	String machineLastNumberByLocation(MachineView machineView);

	List<MachineModel> usedMachineNumberByLocation(MachineView machineView);

	List<MachineModel> getByMachineIds(List<MachineView> machineViews);

	PageModel getByPickedupRoute(DailyPickupAssigneeView dailyPickupAssigneeView, Integer start, Integer recordSize);

	List<MachineModel> dropDown();

    PageModel machineList(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	PageModel machineListForAssignBarcodeTemplate(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	MachineModel getByMachineIdFromSmallModel(Long id);

	List<MachineModel> findAllMachineState();
}