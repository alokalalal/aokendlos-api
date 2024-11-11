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
package com.intentlabs.endlos.barcodemachine.model;

import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;

import java.math.BigDecimal;

/**
 *
 * @author Milan Gohil
 * @since 18/08/2023
 */
public class MachineBarcodeItemModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private String barcodeName;
	private Integer materialType;
	//private String material;
	private BigDecimal itemVolume;
	private BigDecimal itemWeight;
	private BigDecimal itemValue;
	private Integer barcodefileid;
	private MachineBarcodeFileModel machineBarcodeFileModel;


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

	/*public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}*/

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

	public Integer getBarcodefileid() {
		return barcodefileid;
	}

	public void setBarcodefileid(Integer barcodefileid) {
		this.barcodefileid = barcodefileid;
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