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
package com.intentlabs.endlos.barcode.view;

import com.intentlabs.common.view.AuditableView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This class is used to represent Barcode object in json response.
 * 
 * @author Hemil.Shah
 * @since 27/04/2022
 */
public class BarcodeView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private MachineView machineView;
	private String barcode;
	private String description;
	private String dataAcquisition;
	private Long dateCreate;
	private String iteamRedeemValue;
	private String volumn;

	public MachineView getMachineView() {
		return machineView;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataAcquisition() {
		return dataAcquisition;
	}

	public void setDataAcquisition(String dataAcquisition) {
		this.dataAcquisition = dataAcquisition;
	}

	public Long getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Long dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getIteamRedeemValue() {
		return iteamRedeemValue;
	}

	public void setIteamRedeemValue(String iteamRedeemValue) {
		this.iteamRedeemValue = iteamRedeemValue;
	}

	public String getVolumn() {
		return volumn;
	}

	public void setVolumn(String volumn) {
		this.volumn = volumn;
	}
}