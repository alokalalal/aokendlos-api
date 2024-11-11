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
import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;

/**
 * This is Error view which maps Error table to class.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@JsonInclude(Include.NON_NULL)
public class ReportView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;
	private MachineView machineView;
	private LocationView locationView;
	private long patBottleCount;
	private long smallPatBottleCount;
	private long bigPatBottleCount;
	private long aluBottleCount;
	private long glassBottleCount;
	private long smallGlassBottleCount;
	private long bigGlassBottleCount;
	private long totalBottleCount;
	private BigDecimal totalValue;
	private Long startDate;
	private Long endDate;
	private Long createDate;
	private CustomerView customerView;
	private long totalPatBottleCount;
	private long totalSmallPatBottleCount;
	private long totalBigPatBottleCount;
	private long totalAluBottleCount;
	private long totalGlassBottleCount;
	private long totalSmallGlassBottleCount;
	private long totalBigGlassBottleCount;
	private long allTotalBottleCount;
	private BigDecimal allTotalValue;

	public MachineView getMachineView() {
		return machineView;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public long getPatBottleCount() {
		return patBottleCount;
	}

	public void setPatBottleCount(long patBottleCount) {
		this.patBottleCount = patBottleCount;
	}

	public long getSmallPatBottleCount() {
		return smallPatBottleCount;
	}

	public void setSmallPatBottleCount(long smallPatBottleCount) {
		this.smallPatBottleCount = smallPatBottleCount;
	}

	public long getBigPatBottleCount() {
		return bigPatBottleCount;
	}

	public void setBigPatBottleCount(long bigPatBottleCount) {
		this.bigPatBottleCount = bigPatBottleCount;
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

	public long getSmallGlassBottleCount() {
		return smallGlassBottleCount;
	}

	public void setSmallGlassBottleCount(long smallGlassBottleCount) {
		this.smallGlassBottleCount = smallGlassBottleCount;
	}

	public long getBigGlassBottleCount() {
		return bigGlassBottleCount;
	}

	public void setBigGlassBottleCount(long bigGlassBottleCount) {
		this.bigGlassBottleCount = bigGlassBottleCount;
	}

	public long getTotalBottleCount() {
		return totalBottleCount;
	}

	public void setTotalBottleCount(long totalBottleCount) {
		this.totalBottleCount = totalBottleCount;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
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

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public long getTotalPatBottleCount() {
		return totalPatBottleCount;
	}

	public long getTotalSmallPatBottleCount() {
		return totalSmallPatBottleCount;
	}

	public long getTotalBigPatBottleCount() {
		return totalBigPatBottleCount;
	}

	public long getTotalAluBottleCount() {
		return totalAluBottleCount;
	}

	public long getTotalGlassBottleCount() {
		return totalGlassBottleCount;
	}

	public long getTotalSmallGlassBottleCount() {
		return totalSmallGlassBottleCount;
	}

	public long getTotalBigGlassBottleCount() {
		return totalBigGlassBottleCount;
	}

	public long getAllTotalBottleCount() {
		return allTotalBottleCount;
	}

	public BigDecimal getAllTotalValue() {
		return allTotalValue;
	}

	public void setTotalPatBottleCount(long totalPatBottleCount) {
		this.totalPatBottleCount = totalPatBottleCount;
	}

	public void setTotalSmallPatBottleCount(long totalSmallPatBottleCount) {
		this.totalSmallPatBottleCount = totalSmallPatBottleCount;
	}

	public void setTotalBigPatBottleCount(long totalBigPatBottleCount) {
		this.totalBigPatBottleCount = totalBigPatBottleCount;
	}

	public void setTotalAluBottleCount(long totalAluBottleCount) {
		this.totalAluBottleCount = totalAluBottleCount;
	}

	public void setTotalGlassBottleCount(long totalGlassBottleCount) {
		this.totalGlassBottleCount = totalGlassBottleCount;
	}

	public void setTotalSmallGlassBottleCount(long totalSmallGlassBottleCount) {
		this.totalSmallGlassBottleCount = totalSmallGlassBottleCount;
	}

	public void setTotalBigGlassBottleCount(long totalBigGlassBottleCount) {
		this.totalBigGlassBottleCount = totalBigGlassBottleCount;
	}

	public void setAllTotalBottleCount(long allTotalBottleCount) {
		this.allTotalBottleCount = allTotalBottleCount;
	}

	public void setAllTotalValue(BigDecimal allTotalValue) {
		this.allTotalValue = allTotalValue;
	}
}