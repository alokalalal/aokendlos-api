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

/**
 * This is Error model which maps machine log table to class.
 * 
 * @author Hemil.Shah
 * @since 13/06/2023
 */
public class PrintLogModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private Long resetDate;
	private Long printCount;
	private Long createDate;
	private MachineModel machineModel;
	private LocationModel locationModel;

	public Long getResetDate() {
		return resetDate;
	}

	public Long getPrintCount() {
		return printCount;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setResetDate(Long resetDate) {
		this.resetDate = resetDate;
	}

	public void setPrintCount(Long printCount) {
		this.printCount = printCount;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}
}