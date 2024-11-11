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
import com.intentlabs.endlos.machine.enums.MachineActivityStatusEnum;

/**
 * This is Error model which maps error table to class.
 * 
 * @author Hemil.Shah
 * @since 08/07/2022
 */
public class ErrorLogModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private ErrorModel errorModel;
	private MachineModel machineModel;
	private Integer machineActivityStatus;
	private Long createDate;

	public ErrorModel getErrorModel() {
		return errorModel;
	}

	public void setErrorModel(ErrorModel errorModel) {
		this.errorModel = errorModel;
	}

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public MachineActivityStatusEnum getMachineActivityStatus() {
		return MachineActivityStatusEnum.fromId(machineActivityStatus);
	}

	public void setMachineActivityStatus(Integer machineActivityStatus) {
		this.machineActivityStatus = machineActivityStatus;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}