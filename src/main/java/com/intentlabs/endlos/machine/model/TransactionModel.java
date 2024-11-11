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

import java.math.BigDecimal;

import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.customer.model.LocationModel;

/**
 * This is Transaction model which maps device table to class.
 * 
 * @author Hemil.Shah
 * @since 08/10/2021
 */
public class TransactionModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private String transactionId;
	private long patBottleCount;
	private long aluBottleCount;
	private long glassBottleCount;
	private BigDecimal patBottleValue = BigDecimal.valueOf(0.0);
	private BigDecimal aluBottleValue = BigDecimal.valueOf(0.0);
	private BigDecimal glassBottleValue = BigDecimal.valueOf(0.0);
	private BigDecimal totalValue;
	private Long dateStart;
	private Long dateUpdate;
	private Long dateEnd;
	private String barcode;
	private MachineModel machineModel;
	private FileModel barcodeFileModel;
	private String machineId;
	private boolean offline;
	private LocationModel locationModel;
	private boolean fetch;
	private BigDecimal weight;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getPatBottleCount() {
		return patBottleCount;
	}

	public void setPatBottleCount(long patBottleCount) {
		this.patBottleCount = patBottleCount;
	}

	public long getAluBottleCount() {
		return aluBottleCount;
	}

	public void setAluBottleCount(long aluBottleCount) {
		this.aluBottleCount = aluBottleCount;
	}

	public long getGlassBottleCount() {
		return glassBottleCount;
	}

	public void setGlassBottleCount(long glassBottleCount) {
		this.glassBottleCount = glassBottleCount;
	}

	public BigDecimal getPatBottleValue() {
		return patBottleValue;
	}

	public void setPatBottleValue(BigDecimal patBottleValue) {
		this.patBottleValue = patBottleValue;
	}

	public BigDecimal getAluBottleValue() {
		return aluBottleValue;
	}

	public void setAluBottleValue(BigDecimal aluBottleValue) {
		this.aluBottleValue = aluBottleValue;
	}

	public BigDecimal getGlassBottleValue() {
		return glassBottleValue;
	}

	public void setGlassBottleValue(BigDecimal glassBottleValue) {
		this.glassBottleValue = glassBottleValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Long getDateStart() {
		return dateStart;
	}

	public void setDateStart(Long dateStart) {
		this.dateStart = dateStart;
	}

	public Long getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Long dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Long dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public FileModel getBarcodeFileModel() {
		return barcodeFileModel;
	}

	public void setBarcodeFileModel(FileModel barcodeFileModel) {
		this.barcodeFileModel = barcodeFileModel;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}

	public boolean isFetch() {
		return fetch;
	}

	public void setFetch(boolean fetch) {
		this.fetch = fetch;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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