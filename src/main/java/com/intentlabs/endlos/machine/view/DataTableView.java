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
package com.intentlabs.endlos.machine.view;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.AuditableView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This is DataTable view which maps DataTable table to class.
 * 
 * @author Hemil.Shah
 * @since 07/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class DataTableView extends AuditableView {

	private static final long serialVersionUID = -8686919838798454529L;
	private String barcode;
	private KeyValueView material;
	private String description;
	private BigDecimal volumn;
	private BigDecimal weight;
	private BigDecimal redeemvalue;
	private String dataacquisition;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public KeyValueView getMaterial() {
		return material;
	}

	public void setMaterial(KeyValueView material) {
		this.material = material;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getRedeemvalue() {
		return redeemvalue;
	}

	public void setRedeemvalue(BigDecimal redeemvalue) {
		this.redeemvalue = redeemvalue;
	}

	public String getDataacquisition() {
		return dataacquisition;
	}

	public void setDataacquisition(String dataacquisition) {
		this.dataacquisition = dataacquisition;
	}
}