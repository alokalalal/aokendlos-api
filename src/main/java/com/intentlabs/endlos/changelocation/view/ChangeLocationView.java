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
package com.intentlabs.endlos.changelocation.view;

import com.intentlabs.common.view.AuditableView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This class is used to represent Change Location object in json/in change
 * location response.
 * 
 * @author Hemil Shah.
 * @version 1.0
 * @since 07/09/2022
 */
public class ChangeLocationView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private MachineView machineView;
	private CustomerView oldCustomerView;
	private LocationView oldLocationView;
	private BarcodeTemplateView oldBarcodeTemplateView;
	private MachineBarcodeFileView oldMachineBarcodeFileView;
	private CustomerView customerView;
	private LocationView locationView;
	private BarcodeTemplateView barcodeTemplateView;
	private KeyValueView status;
	private String requestDate;
	private String responseDate;
	private String oldBranchMachineNumber;
	private String newBranchMachineNumber;
	private Long startDate;
	private Long endDate;
	private MachineBarcodeFileView barcodeMachineView;

	public MachineView getMachineView() {
		return machineView;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public CustomerView getOldCustomerView() {
		return oldCustomerView;
	}

	public void setOldCustomerView(CustomerView oldCustomerView) {
		this.oldCustomerView = oldCustomerView;
	}

	public LocationView getOldLocationView() {
		return oldLocationView;
	}

	public void setOldLocationView(LocationView oldLocationView) {
		this.oldLocationView = oldLocationView;
	}

	public BarcodeTemplateView getOldBarcodeTemplateView() {
		return oldBarcodeTemplateView;
	}

	public void setOldBarcodeTemplateView(BarcodeTemplateView oldBarcodeTemplateView) {
		this.oldBarcodeTemplateView = oldBarcodeTemplateView;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public BarcodeTemplateView getBarcodeTemplateView() {
		return barcodeTemplateView;
	}

	public void setBarcodeTemplateView(BarcodeTemplateView barcodeTemplateView) {
		this.barcodeTemplateView = barcodeTemplateView;
	}

	public KeyValueView getStatus() {
		return status;
	}

	public void setStatus(KeyValueView status) {
		this.status = status;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getNewBranchMachineNumber() {
		return newBranchMachineNumber;
	}

	public void setNewBranchMachineNumber(String newBranchMachineNumber) {
		this.newBranchMachineNumber = newBranchMachineNumber;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getOldBranchMachineNumber() {
		return oldBranchMachineNumber;
	}

	public void setOldBranchMachineNumber(String oldBranchMachineNumber) {
		this.oldBranchMachineNumber = oldBranchMachineNumber;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public MachineBarcodeFileView getOldMachineBarcodeFileView() {
		return oldMachineBarcodeFileView;
	}

	public void setOldMachineBarcodeFileView(MachineBarcodeFileView oldMachineBarcodeFileView) {
		this.oldMachineBarcodeFileView = oldMachineBarcodeFileView;
	}

	public MachineBarcodeFileView getBarcodeMachineView() {
		return barcodeMachineView;
	}

	public void setBarcodeMachineView(MachineBarcodeFileView barcodeMachineView) {
		this.barcodeMachineView = barcodeMachineView;
	}
}