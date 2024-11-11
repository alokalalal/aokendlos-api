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
 * This is Error model which maps error table to class.
 * 
 * @author Hemil.Shah
 * @since 08/07/2022
 */
public class ErrorModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private String errorName;
	private Long resolveDate;
	private Long createDate;
	private boolean isResolve;
	private MachineModel machineModel;
	private LocationModel locationModel;

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public Long getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(Long resolveDate) {
		this.resolveDate = resolveDate;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public boolean isResolve() {
		return isResolve;
	}

	public void setResolve(boolean isResolve) {
		this.isResolve = isResolve;
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
}