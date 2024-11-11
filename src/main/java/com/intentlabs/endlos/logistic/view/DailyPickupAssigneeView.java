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

import java.math.BigDecimal;
import java.util.List;

import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This class is used to represent PickupRoute object in json/in Branch
 * response.
 *
 * @author Milan Gohil
 * @since 11/12/2023
 */
public class DailyPickupAssigneeView extends IdentifierView {

	private static final long serialVersionUID = -1942730193814291940L;
	private CustomerView customerView;
	private LocationView locationView;
	private List<MachineView> machineViews;
	private Long lastPickupDate;
	private String hoursFromPickup;
	private Long numbersOfGlassReset;
	private Long numbersOfPatReset;
	private Long numbersOfAluReset;
	private Long totalBottles;
	private Long totalVouchers;
	private BigDecimal totalWeight;
	private List<LocationView> locationViews;
	private PickupRouteView pickupRouteView;
	private String fullTextSearch;
	private MachineView machineView;
	private Long startDate;
	private Long endDate;


	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public List<MachineView> getMachineViews() {
		return machineViews;
	}

	public void setMachineViews(List<MachineView> machineViews) {
		this.machineViews = machineViews;
	}

	public Long getLastPickupDate() {
		return lastPickupDate;
	}

	public void setLastPickupDate(Long lastPickupDate) {
		this.lastPickupDate = lastPickupDate;
	}

	public String getHoursFromPickup() {
		return hoursFromPickup;
	}

	public void setHoursFromPickup(String hoursFromPickup) {
		this.hoursFromPickup = hoursFromPickup;
	}

	public Long getNumbersOfGlassReset() {
		return numbersOfGlassReset;
	}

	public void setNumbersOfGlassReset(Long numbersOfGlassReset) {
		this.numbersOfGlassReset = numbersOfGlassReset;
	}

	public Long getNumbersOfPatReset() {
		return numbersOfPatReset;
	}

	public void setNumbersOfPatReset(Long numbersOfPatReset) {
		this.numbersOfPatReset = numbersOfPatReset;
	}

	public Long getNumbersOfAluReset() {
		return numbersOfAluReset;
	}

	public void setNumbersOfAluReset(Long numbersOfAluReset) {
		this.numbersOfAluReset = numbersOfAluReset;
	}

	public Long getTotalBottles() {
		return totalBottles;
	}

	public void setTotalBottles(Long totalBottles) {
		this.totalBottles = totalBottles;
	}

	public Long getTotalVouchers() {
		return totalVouchers;
	}

	public void setTotalVouchers(Long totalVouchers) {
		this.totalVouchers = totalVouchers;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public List<LocationView> getLocationViews() {
		return locationViews;
	}

	public void setLocationViews(List<LocationView> locationViews) {
		this.locationViews = locationViews;
	}

	public PickupRouteView getPickupRouteView() {
		return pickupRouteView;
	}

	public void setPickupRouteView(PickupRouteView pickupRouteView) {
		this.pickupRouteView = pickupRouteView;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}
	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public MachineView getMachineView() {
		return machineView;
	}
	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
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
}