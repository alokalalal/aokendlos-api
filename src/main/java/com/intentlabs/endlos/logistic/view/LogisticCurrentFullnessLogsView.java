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
package com.intentlabs.endlos.logistic.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.enums.MaterialEnum;
import com.intentlabs.endlos.machine.view.MachineView;

import java.math.BigDecimal;

/**
 * This is Machine Log view which maps machine log table to class.
 * 
 * @author Milan Gohil.
 * @version 1.0ka
 * @since 19/12/2023
 */
@JsonInclude(Include.NON_NULL)
public class LogisticCurrentFullnessLogsView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;
	private CustomerView customerView;
	private LocationView locationView;
	private MachineView machineView;
	private PickupRouteView pickupRouteView;
	private Long date;//Soft Reset
	private Long hardResetDate;//Hard Reset
	private KeyValueView materialType;
	private Long hoursFromLastTimeStempTillNow;
	private Integer numberOfPlasticBinResetsSinceFullReset;
	private Integer numberOfAluminumBinResetsSinceFullReset;
	private Integer numberOfGlassBinResetsSinceFullReset;
	private Integer numberOfPlasticBottlesSinceLastFullReset;
	private Integer numberOfAluminumBottlesSinceLastFullReset;
	private Integer numberOfGlassBottlesSinceLastFullReset;

	private Long totalWeightOfPickup;

	private boolean isHardReset;
	private Integer totalNumberOfPickups;

	//Planned Data Route Wise
	private Long generatePlanDate;
	private Long plannedNumberOfPatBottle;
	private Long plannedNumberOfAluBottle;
	private Long plannedNumberOfGlassBottle;
	private Long plannedTotalNumberOfPickup;
	private BigDecimal plannedTotalWeight;

	public Long getDate() {
		return date;
	}

	public KeyValueView getMaterialType() {
		return materialType;
	}

	public MachineView getMachineView() {
		return machineView;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}


	public LocationView getLocationView() {
		return locationView;
	}

	public void setDate(Long resetDate) {
		this.date = resetDate;
	}

	public void setMaterialType(KeyValueView materialType) {
		this.materialType = materialType;
	}

	public static KeyValueView setMaterialType(Integer materialType) {
		MaterialEnum materialEnum = MaterialEnum.fromId(materialType);
		return KeyValueView.create(materialEnum.getId(), materialEnum.getName());
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public Long getHardResetDate() {
		return hardResetDate;
	}

	public boolean isHardReset() {
		return isHardReset;
	}

	public void setHardResetDate(Long hardResetDate) {
		this.hardResetDate = hardResetDate;
	}

	public void setHardReset(boolean isHardReset) {
		this.isHardReset = isHardReset;
	}

	public Long getHoursFromLastTimeStempTillNow() {
		return hoursFromLastTimeStempTillNow;
	}

	public void setHoursFromLastTimeStempTillNow(Long hoursFromLastTimeStempTillNow) {
		this.hoursFromLastTimeStempTillNow = hoursFromLastTimeStempTillNow;
	}

	public Integer getNumberOfPlasticBinResetsSinceFullReset() {
		return numberOfPlasticBinResetsSinceFullReset;
	}

	public void setNumberOfPlasticBinResetsSinceFullReset(Integer numberOfPlasticBinResetsSinceFullReset) {
		this.numberOfPlasticBinResetsSinceFullReset = numberOfPlasticBinResetsSinceFullReset;
	}

	public Integer getNumberOfAluminumBinResetsSinceFullReset() {
		return numberOfAluminumBinResetsSinceFullReset;
	}

	public void setNumberOfAluminumBinResetsSinceFullReset(Integer numberOfAluminumBinResetsSinceFullReset) {
		this.numberOfAluminumBinResetsSinceFullReset = numberOfAluminumBinResetsSinceFullReset;
	}

	public Integer getNumberOfGlassBinResetsSinceFullReset() {
		return numberOfGlassBinResetsSinceFullReset;
	}

	public void setNumberOfGlassBinResetsSinceFullReset(Integer numberOfGlassBinResetsSinceFullReset) {
		this.numberOfGlassBinResetsSinceFullReset = numberOfGlassBinResetsSinceFullReset;
	}

	public Integer getNumberOfPlasticBottlesSinceLastFullReset() {
		return numberOfPlasticBottlesSinceLastFullReset;
	}

	public void setNumberOfPlasticBottlesSinceLastFullReset(Integer numberOfPlasticBottlesSinceLastFullReset) {
		this.numberOfPlasticBottlesSinceLastFullReset = numberOfPlasticBottlesSinceLastFullReset;
	}

	public Integer getNumberOfAluminumBottlesSinceLastFullReset() {
		return numberOfAluminumBottlesSinceLastFullReset;
	}

	public void setNumberOfAluminumBottlesSinceLastFullReset(Integer numberOfAluminumBottlesSinceLastFullReset) {
		this.numberOfAluminumBottlesSinceLastFullReset = numberOfAluminumBottlesSinceLastFullReset;
	}

	public Integer getNumberOfGlassBottlesSinceLastFullReset() {
		return numberOfGlassBottlesSinceLastFullReset;
	}

	public void setNumberOfGlassBottlesSinceLastFullReset(Integer numberOfGlassBottlesSinceLastFullReset) {
		this.numberOfGlassBottlesSinceLastFullReset = numberOfGlassBottlesSinceLastFullReset;
	}

	public PickupRouteView getPickupRouteView() {
		return pickupRouteView;
	}

	public void setPickupRouteView(PickupRouteView pickupRouteView) {
		this.pickupRouteView = pickupRouteView;
	}

	public Long getTotalWeightOfPickup() {
		return totalWeightOfPickup;
	}

	public void setTotalWeightOfPickup(Long totalWeightOfPickup) {
		this.totalWeightOfPickup = totalWeightOfPickup;
	}

	public Integer getTotalNumberOfPickups() {
		return totalNumberOfPickups;
	}

	public void setTotalNumberOfPickups(Integer totalNumberOfPickups) {
		this.totalNumberOfPickups = totalNumberOfPickups;
	}

	public Long getGeneratePlanDate() {
		return generatePlanDate;
	}

	public void setGeneratePlanDate(Long generatePlanDate) {
		this.generatePlanDate = generatePlanDate;
	}

	public Long getPlannedNumberOfPatBottle() {
		return plannedNumberOfPatBottle;
	}

	public void setPlannedNumberOfPatBottle(Long plannedNumberOfPatBottle) {
		this.plannedNumberOfPatBottle = plannedNumberOfPatBottle;
	}

	public Long getPlannedNumberOfAluBottle() {
		return plannedNumberOfAluBottle;
	}

	public void setPlannedNumberOfAluBottle(Long plannedNumberOfAluBottle) {
		this.plannedNumberOfAluBottle = plannedNumberOfAluBottle;
	}

	public Long getPlannedNumberOfGlassBottle() {
		return plannedNumberOfGlassBottle;
	}

	public void setPlannedNumberOfGlassBottle(Long plannedNumberOfGlassBottle) {
		this.plannedNumberOfGlassBottle = plannedNumberOfGlassBottle;
	}

	public Long getPlannedTotalNumberOfPickup() {
		return plannedTotalNumberOfPickup;
	}

	public void setPlannedTotalNumberOfPickup(Long plannedTotalNumberOfPickup) {
		this.plannedTotalNumberOfPickup = plannedTotalNumberOfPickup;
	}

	public BigDecimal getPlannedTotalWeight() {
		return plannedTotalWeight;
	}

	public void setPlannedTotalWeight(BigDecimal plannedTotalWeight) {
		this.plannedTotalWeight = plannedTotalWeight;
	}
}
