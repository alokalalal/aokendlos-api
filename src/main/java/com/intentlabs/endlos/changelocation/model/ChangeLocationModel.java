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
package com.intentlabs.endlos.changelocation.model;

import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.changelocation.enums.ChangeLocationStatus;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.machine.model.MachineModel;

/**
 * This is Change Location model which maps Change Location table to class.
 * 
 * @author hemil.Shah
 * @since 07/09/2022
 */
public class ChangeLocationModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private MachineModel machineModel;
	private CustomerModel oldCustomerModel;
	private LocationModel oldLocationModel;
	private BarcodeTemplateModel oldBarcodeTemplateModel;
	private MachineBarcodeFileModel oldMachineBarcodeFileModel;
	private CustomerModel customerModel;
	private LocationModel locationModel;
	private BarcodeTemplateModel barcodeTemplateModel;
	private Integer status;
	private Long requestDate;
	private Long responseDate;
	private String oldBranchMachineNumber;//Old Branch Wise Machine No
	private String newBranchMachineNumber;//New Branch Wise Machine No
	private MachineBarcodeFileModel machineBarcodeFileModel;

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public CustomerModel getOldCustomerModel() {
		return oldCustomerModel;
	}

	public void setOldCustomerModel(CustomerModel oldCustomerModel) {
		this.oldCustomerModel = oldCustomerModel;
	}

	public LocationModel getOldLocationModel() {
		return oldLocationModel;
	}

	public void setOldLocationModel(LocationModel oldLocationModel) {
		this.oldLocationModel = oldLocationModel;
	}

	public BarcodeTemplateModel getOldBarcodeTemplateModel() {
		return oldBarcodeTemplateModel;
	}

	public void setOldBarcodeTemplateModel(BarcodeTemplateModel oldBarcodeTemplateModel) {
		this.oldBarcodeTemplateModel = oldBarcodeTemplateModel;
	}

	public CustomerModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}

	public BarcodeTemplateModel getBarcodeTemplateModel() {
		return barcodeTemplateModel;
	}

	public void setBarcodeTemplateModel(BarcodeTemplateModel barcodeTemplateModel) {
		this.barcodeTemplateModel = barcodeTemplateModel;
	}

	public ChangeLocationStatus getStatus() {
		return ChangeLocationStatus.fromId(status);
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Long requestDate) {
		this.requestDate = requestDate;
	}

	public Long getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Long responseDate) {
		this.responseDate = responseDate;
	}

	public String getOldBranchMachineNumber() {
		return oldBranchMachineNumber;
	}

	public void setOldBranchMachineNumber(String oldBranchMachineNumber) {
		this.oldBranchMachineNumber = oldBranchMachineNumber;
	}

	public String getNewBranchMachineNumber() {
		return newBranchMachineNumber;
	}

	public void setNewBranchMachineNumber(String newBranchMachineNumber) {
		this.newBranchMachineNumber = newBranchMachineNumber;
	}

	public MachineBarcodeFileModel getOldMachineBarcodeFileModel() {
		return oldMachineBarcodeFileModel;
	}

	public void setOldMachineBarcodeFileModel(MachineBarcodeFileModel oldMachineBarcodeFileModel) {
		this.oldMachineBarcodeFileModel = oldMachineBarcodeFileModel;
	}

	public MachineBarcodeFileModel getMachineBarcodeFileModel() {
		return machineBarcodeFileModel;
	}

	public void setMachineBarcodeFileModel(MachineBarcodeFileModel machineBarcodeFileModel) {
		this.machineBarcodeFileModel = machineBarcodeFileModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}