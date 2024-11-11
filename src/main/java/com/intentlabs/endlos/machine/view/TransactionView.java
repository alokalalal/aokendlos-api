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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;

/**
 * This is Transaction view which maps Transaction table to class.
 * 
 * @author Hemil.Shah
 * @since 08/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class TransactionView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;
	private String transactionId;
	private long patBottleCount;
	private long aluBottleCount;
	private long glassBottleCount;
	private long totalInsertedBottleCount;
	private BigDecimal patBottleValue;
	private BigDecimal aluBottleValue;
	private BigDecimal glassBottleValue;
	private BigDecimal totalValue;
	private MachineView machineView;
	private String barcode;
	private Long dateStart;
	private Long dateUpdate;
	private Long dateEnd;
	private KeyValueView status;
	private String reason;
	private List<TransactionLogView> transactionLogViews;

	private Long startDate;
	private Long endDate;
	private KeyValueView material;
	private String fullTextSearch;
	private CustomerView customerView;
	private boolean offline;
	private LocationView locationView;

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

	public long getTotalInsertedBottleCount() {
		return totalInsertedBottleCount;
	}

	public void setTotalInsertedBottleCount(long totalInsertedBottleCount) {
		this.totalInsertedBottleCount = totalInsertedBottleCount;
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

	public KeyValueView getStatus() {
		return status;
	}

	public void setStatus(KeyValueView status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public KeyValueView getMaterial() {
		return material;
	}

	public void setMaterial(KeyValueView material) {
		this.material = material;
	}

	public List<TransactionLogView> getTransactionLogViews() {
		return transactionLogViews;
	}

	public void setTransactionLogViews(List<TransactionLogView> transactionLogViews) {
		this.transactionLogViews = transactionLogViews;
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

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}
}