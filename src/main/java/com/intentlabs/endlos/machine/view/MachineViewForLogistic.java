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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.intentlabs.common.view.ActivationView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This class is used to represent Machine object in json/in customer response.
 * 
 * @author Milan.Gohil
 * @since 23/05/24
 */
public class MachineViewForLogistic extends ActivationView {

	private static final long serialVersionUID = -1942730193814291940L;
	private String machineId;
	private String machineType;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String branchName;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String branchNumber;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String city;
	private TransactionView transactionView;
	private String fullTextSearch;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String plasticCurrentPercentage;
	private String aluminiumnCurrentPercentage;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String glassCurrentPercentage;
	private String branchMachineNumber;
	private Long startDate;
	private Long endDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long hardResetDatePlastic;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long hardResetDateGlass;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long hardResetDateAluminiumn;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long plasticCurrentUnits;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long glassCurrentUnits;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long aluminiumCurrentUnits;

	private double glassCapacityUnits;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Double glassCapacityUnitsWrapper;
	private double plasticCapacityUnits;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Double plasticCapacityUnitsWrapper;
	private double aluminiumCapacityUnits;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Double aluminiumCapacityUnitsWrapper;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long lastStatusUpdate;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long softResetDatePlastic;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long softResetDateGlass;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long softResetDateAluminiumn;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Boolean isPickupTomorrow;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Integer numberOfGlassTanks;

	private KeyValueView machineActivityStatus;

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public TransactionView getTransactionView() {
		return transactionView;
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public String getPlasticCurrentPercentage() {
		return plasticCurrentPercentage;
	}

	public void setPlasticCurrentPercentage(String plasticCurrentPercentage) {
		this.plasticCurrentPercentage = plasticCurrentPercentage;
	}

	public String getAluminiumnCurrentPercentage() {
		return aluminiumnCurrentPercentage;
	}

	public void setAluminiumnCurrentPercentage(String aluminiumnCurrentPercentage) {
		this.aluminiumnCurrentPercentage = aluminiumnCurrentPercentage;
	}

	public String getGlassCurrentPercentage() {
		return glassCurrentPercentage;
	}

	public void setGlassCurrentPercentage(String glassCurrentPercentage) {
		this.glassCurrentPercentage = glassCurrentPercentage;
	}

	public String getBranchMachineNumber() {
		return branchMachineNumber;
	}

	public void setBranchMachineNumber(String branchMachineNumber) {
		this.branchMachineNumber = branchMachineNumber;
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

	public Long getHardResetDatePlastic() {
		return hardResetDatePlastic;
	}

	public void setHardResetDatePlastic(Long hardResetDatePlastic) {
		this.hardResetDatePlastic = hardResetDatePlastic;
	}

	public Long getHardResetDateGlass() {
		return hardResetDateGlass;
	}

	public void setHardResetDateGlass(Long hardResetDateGlass) {
		this.hardResetDateGlass = hardResetDateGlass;
	}

	public Long getHardResetDateAluminiumn() {
		return hardResetDateAluminiumn;
	}

	public void setHardResetDateAluminiumn(Long hardResetDateAluminiumn) {
		this.hardResetDateAluminiumn = hardResetDateAluminiumn;
	}

	public Long getSoftResetDatePlastic() {
		return softResetDatePlastic;
	}

	public void setSoftResetDatePlastic(Long softResetDatePlastic) {
		this.softResetDatePlastic = softResetDatePlastic;
	}

	public Long getSoftResetDateGlass() {
		return softResetDateGlass;
	}

	public void setSoftResetDateGlass(Long softResetDateGlass) {
		this.softResetDateGlass = softResetDateGlass;
	}

	public Long getSoftResetDateAluminiumn() {
		return softResetDateAluminiumn;
	}

	public void setSoftResetDateAluminiumn(Long softResetDateAluminiumn) {
		this.softResetDateAluminiumn = softResetDateAluminiumn;
	}

	public Long getPlasticCurrentUnits() {
		return plasticCurrentUnits;
	}

	public void setPlasticCurrentUnits(Long plasticCurrentUnits) {
		this.plasticCurrentUnits = plasticCurrentUnits;
	}

	public Long getGlassCurrentUnits() {
		return glassCurrentUnits;
	}

	public void setGlassCurrentUnits(Long glassCurrentUnits) {
		this.glassCurrentUnits = glassCurrentUnits;
	}

	public Long getAluminiumCurrentUnits() {
		return aluminiumCurrentUnits;
	}

	public void setAluminiumCurrentUnits(Long aluminiumCurrentUnits) {
		this.aluminiumCurrentUnits = aluminiumCurrentUnits;
	}

	public double getGlassCapacityUnits() {
		return glassCapacityUnits;
	}

	public void setGlassCapacityUnits(double glassCapacityUnits) {
		this.glassCapacityUnits = glassCapacityUnits;
		this.glassCapacityUnitsWrapper = glassCapacityUnits;
	}

	public double getPlasticCapacityUnits() {
		return plasticCapacityUnits;
	}

	public void setPlasticCapacityUnits(double plasticCapacityUnits) {
		this.plasticCapacityUnits = plasticCapacityUnits;
		this.plasticCapacityUnitsWrapper = plasticCapacityUnits;
	}

	public double getAluminiumCapacityUnits() {
		return aluminiumCapacityUnits;
	}

	public void setAluminiumCapacityUnits(double aluminiumCapacityUnits) {
		this.aluminiumCapacityUnits = aluminiumCapacityUnits;
		this.aluminiumCapacityUnitsWrapper = aluminiumCapacityUnits;
	}

	public Long getLastStatusUpdate() {
		return lastStatusUpdate;
	}

	public void setLastStatusUpdate(Long lastStatusUpdate) {
		this.lastStatusUpdate = lastStatusUpdate;
	}

	public Boolean getIsPickupTomorrow() {
		return isPickupTomorrow;
	}

	public void setIsPickupTomorrow(Boolean pickupTomorrow) {
		isPickupTomorrow = pickupTomorrow;
	}

	public Integer getNumberOfGlassTanks() {
		return numberOfGlassTanks;
	}

	public void setNumberOfGlassTanks(Integer numberOfGlassTanks) {
		this.numberOfGlassTanks = numberOfGlassTanks;
	}

	public KeyValueView getMachineActivityStatus() {
		return machineActivityStatus;
	}

	public void setMachineActivityStatus(KeyValueView machineActivityStatus) {
		this.machineActivityStatus = machineActivityStatus;
	}

	@JsonGetter("plasticCapacityUnits")
	public Double getPlasticCapacityUnitsWrapper() {
		return plasticCapacityUnitsWrapper;
	}

	public void setPlasticCapacityUnitsWrapper(Double plasticCapacityUnitsWrapper) {
		this.plasticCapacityUnitsWrapper = plasticCapacityUnitsWrapper;
		if (plasticCapacityUnitsWrapper != null) {
			this.plasticCapacityUnits = plasticCapacityUnitsWrapper;
		}
	}

	@JsonGetter("glassCapacityUnits")
	public Double getGlassCapacityUnitsWrapper() {
		return glassCapacityUnitsWrapper;
	}

	public void setGlassCapacityUnitsWrapper(Double glassCapacityUnitsWrapper) {
		this.glassCapacityUnitsWrapper = glassCapacityUnitsWrapper;
		if (glassCapacityUnitsWrapper != null) {
			this.glassCapacityUnits = glassCapacityUnitsWrapper;
		}
	}

	@JsonGetter("aluminiumCapacityUnits")
	public Double getAluminiumCapacityUnitsWrapper() {
		return aluminiumCapacityUnitsWrapper;
	}

	public void setAluminiumCapacityUnitsWrapper(Double aluminiumCapacityUnitsWrapper) {
		this.aluminiumCapacityUnitsWrapper = aluminiumCapacityUnitsWrapper;
		if (aluminiumCapacityUnitsWrapper != null) {
			this.aluminiumCapacityUnits = aluminiumCapacityUnitsWrapper;
		}
	}
}