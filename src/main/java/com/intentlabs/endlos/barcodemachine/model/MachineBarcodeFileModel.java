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

import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;

import java.util.List;

/**
 *
 * @author Milan Gohil
 * @since 18/08/2023
 */
public class MachineBarcodeFileModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private Long noOfMachineAssigned;
	private Long totalNoOfBarcodes;
	private Long plastic;
	private Long glass;
	private Long alluminium;
	private String fileStatus;
	//private List<MachineBarcodeItemModel> machineBarcodeItemModels;
	private String barcodeFileName;
	private Integer barcodefileid;

	public Integer getBarcodefileid() {
		return barcodefileid;
	}

	public void setBarcodefileid(Integer barcodefileid) {
		this.barcodefileid = barcodefileid;
	}

	public Long getNoOfMachineAssigned() {
		return noOfMachineAssigned;
	}

	public void setNoOfMachineAssigned(Long noOfMachineAssigned) {
		this.noOfMachineAssigned = noOfMachineAssigned;
	}

	public Long getTotalNoOfBarcodes() {
		return totalNoOfBarcodes;
	}

	public void setTotalNoOfBarcodes(Long totalNoOfBarcodes) {
		this.totalNoOfBarcodes = totalNoOfBarcodes;
	}

	public Long getPlastic() {
		return plastic;
	}

	public void setPlastic(Long plastic) {
		this.plastic = plastic;
	}

	public Long getGlass() {
		return glass;
	}

	public void setGlass(Long glass) {
		this.glass = glass;
	}

	public Long getAlluminium() {
		return alluminium;
	}

	public void setAlluminium(Long alluminium) {
		this.alluminium = alluminium;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}


	/*public List<MachineBarcodeItemModel> getMachineBarcodeItemModels() {
		return machineBarcodeItemModels;
	}

	public void setMachineBarcodeItemModels(List<MachineBarcodeItemModel> machineBarcodeItemModels) {
		this.machineBarcodeItemModels = machineBarcodeItemModels;
	}*/

	public String getBarcodeFileName() {
		return barcodeFileName;
	}

	public void setBarcodeFileName(String barcodeFileName) {
		this.barcodeFileName = barcodeFileName;
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