/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.barcodemachine.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.ActivationView;

import java.math.BigDecimal;

/**
 * 
 * @author Milan Gohil
 * @since 23/08/2023
 */
@JsonInclude(Include.NON_NULL)
public class MachineBarcodeItemView extends ActivationView {

	private String barcodeName;
	private Integer materialType;
	//private String material;
	private BigDecimal itemVolume;
	private BigDecimal itemWeight;
	private BigDecimal itemValue;
	private Integer barcodeFileId;
	private MachineBarcodeFileView machineBarcodeFileView;


	public String getBarcodeName() {
		return barcodeName;
	}

	public void setBarcodeName(String barcodeName) {
		this.barcodeName = barcodeName;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public BigDecimal getItemVolume() {
		return itemVolume;
	}

	public void setItemVolume(BigDecimal itemVolume) {
		this.itemVolume = itemVolume;
	}

	public BigDecimal getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(BigDecimal itemWeight) {
		this.itemWeight = itemWeight;
	}

	public BigDecimal getItemValue() {
		return itemValue;
	}

	public void setItemValue(BigDecimal itemValue) {
		this.itemValue = itemValue;
	}

	public Integer getBarcodeFileId() {
		return barcodeFileId;
	}

	public void setBarcodeFileId(Integer barcodeFileId) {
		this.barcodeFileId = barcodeFileId;
	}

	public MachineBarcodeFileView getMachineBarcodeFileView() {
		return machineBarcodeFileView;
	}

	public void setMachineBarcodeFileView(MachineBarcodeFileView machineBarcodeFileView) {
		this.machineBarcodeFileView = machineBarcodeFileView;
	}

	/*public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}*/
}