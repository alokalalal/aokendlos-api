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
import com.intentlabs.endlos.machine.enums.MaterialEnum;

/**
 * This is Transaction Log model which maps Transaction table to class.
 * 
 * @author Hemil.Shah
 * @since 13/10/2021
 */
public class TransactionLogModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private TransactionModel transactionModel;
	private String reason;
	private String barcode;
	private Integer material;
	private Integer status;
	private BigDecimal volumn;
	private BigDecimal weight;
	private FileModel imageModel;

	public TransactionModel getTransactionModel() {
		return transactionModel;
	}

	public void setTransactionModel(TransactionModel transactionModel) {
		this.transactionModel = transactionModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public MaterialEnum getMaterial() {
		return MaterialEnum.fromId(material);
	}

	public void setMaterial(Integer material) {
		this.material = material;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public FileModel getImageModel() {
		return imageModel;
	}

	public void setImageModel(FileModel imageModel) {
		this.imageModel = imageModel;
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