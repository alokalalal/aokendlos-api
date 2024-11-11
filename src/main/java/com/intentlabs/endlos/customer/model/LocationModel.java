/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.customer.model;

import com.intentlabs.common.location.model.CityModel;
import com.intentlabs.common.location.model.CountryModel;
import com.intentlabs.common.location.model.StateModel;
import com.intentlabs.common.model.ActivationModel;
import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.logistic.model.PickupRouteModel;
import com.intentlabs.endlos.machine.model.MachineModel;

/**
 * This is Location model which maps Location table to class.
 * 
 * @author Hemil Shah
 * @since 04/10/2021
 */
public class LocationModel extends ActivationModel {

	private static final long serialVersionUID = 6981951404561215786L;
	private String name;
	private CustomerModel customerModel;
	private String address;
	private String area;
	private CountryModel countryModel;
	private StateModel stateModel;
	private CityModel cityModel;
	private String pincode;
	private String latitude;
	private String longitude;
	private String altitude;
	private String branchNumber;
	private MachineModel machineModel;

	// logistic
	private PickupRouteModel pickupRouteModel;
	private Integer positionRoute;
	private Boolean workDuringWeekends;
	private Boolean pickupEveryday;
	private Integer numberOfGlassTanks;
	private Integer glassFullnessPercentageForPickup;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomerModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public StateModel getStateModel() {
		return stateModel;
	}

	public void setStateModel(StateModel stateModel) {
		this.stateModel = stateModel;
	}

	public CityModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CityModel cityModel) {
		this.cityModel = cityModel;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public CountryModel getCountryModel() {
		return countryModel;
	}

	public void setCountryModel(CountryModel countryModel) {
		this.countryModel = countryModel;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}


	public PickupRouteModel getPickupRouteModel() {
		return pickupRouteModel;
	}

	public void setPickupRouteModel(PickupRouteModel pickupRouteModel) {
		this.pickupRouteModel = pickupRouteModel;
	}

	public Integer getPositionRoute() {
		return positionRoute;
	}

	public void setPositionRoute(Integer positionRoute) {
		this.positionRoute = positionRoute;
	}

	public Boolean getWorkDuringWeekends() {
		return workDuringWeekends;
	}

	public void setWorkDuringWeekends(Boolean workDuringWeekends) {
		this.workDuringWeekends = workDuringWeekends;
	}

	public Boolean getPickupEveryday() {
		return pickupEveryday;
	}

	public void setPickupEveryday(Boolean pickupEveryday) {
		this.pickupEveryday = pickupEveryday;
	}

	public Integer getNumberOfGlassTanks() {
		return numberOfGlassTanks;
	}

	public void setNumberOfGlassTanks(Integer numberOfGlassTanks) {
		this.numberOfGlassTanks = numberOfGlassTanks;
	}

	public Integer getGlassFullnessPercentageForPickup() {
		return glassFullnessPercentageForPickup;
	}

	public void setGlassFullnessPercentageForPickup(Integer glassFullnessPercentageForPickup) {
		this.glassFullnessPercentageForPickup = glassFullnessPercentageForPickup;
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