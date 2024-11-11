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

import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.customer.model.LocationModel;

/**
 * This is Error model which maps error table to class.
 * 
 * @author Hemil.Shah
 * @since 08/07/2022
 */
public class ReportModel extends IdentifierModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private MachineModel machineModel;
	private LocationModel locationModel;
	private long patBottleCount;
	private long smallPatBottleCount;
	private long bigPatBottleCount;
	private long aluBottleCount;
	private long glassBottleCount;
	private long smallGlassBottleCount;
	private long bigGlassBottleCount;
	private long totalBottleCount;
	private BigDecimal totalValue;
	private Long createDate;

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
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

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}