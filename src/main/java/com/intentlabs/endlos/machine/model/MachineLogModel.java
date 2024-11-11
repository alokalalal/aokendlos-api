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
package com.intentlabs.endlos.machine.model;

import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.machine.enums.MaterialEnum;

/**
 * This is Error model which maps machine log table to class.
 * 
 * @author Hemil.Shah
 * @since 13/06/2023
 */
public class MachineLogModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private Long resetDate;
	private Integer materialType;
	private Long materialCount;
	private Long createDate;
	private MachineModel machineModel;
	private LocationModel locationModel;
	private boolean hardReset;
	private Long hardResetDate;

	public MaterialEnum getMaterialType() {
		return MaterialEnum.fromId(materialType);
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public Long getResetDate() {
		return resetDate;
	}

	public void setResetDate(Long resetDate) {
		this.resetDate = resetDate;
	}

	public Long getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(Long materialCount) {
		this.materialCount = materialCount;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}

	public boolean isHardReset() {
		return hardReset;
	}

	public void setHardReset(boolean hardReset) {
		this.hardReset = hardReset;
	}

	public Long getHardResetDate() {
		return hardResetDate;
	}

	public void setHardResetDate(Long hardResetDate) {
		this.hardResetDate = hardResetDate;
	}
}