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
package com.intentlabs.endlos.customer.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.ActivationView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import org.apache.commons.lang3.StringUtils;

/**
 * This is Location view which maps Location table to class.
 *
 * @author Hemil.Shah
 * @since 04/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class LocationView extends ActivationView {

	private static final long serialVersionUID = -70823349628419359L;
	private String name;
	private CustomerView customerView;
	private String address;
	private String area;
	private KeyValueView countryView;
	private KeyValueView stateView;
	private KeyValueView cityView;
	private String pincode;
	private String latitude;
	private String longitude;
	private String altitude;
	private String branchNumber;
	private KeyValueView machineView;

	// logistic
	private KeyValueView pickupRouteView;
	private Integer positionRoute;
	private Boolean workDuringWeekends;
	private Boolean pickupEveryday;
	private Integer numberOfGlassTanks;
	private Integer glassFullnessPercentageForPickup;

	private PickupRouteView pickupRouteView1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
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

	public KeyValueView getCountryView() {
		return countryView;
	}

	public void setCountryView(KeyValueView countryView) {
		this.countryView = countryView;
	}

	public KeyValueView getStateView() {
		return stateView;
	}

	public void setStateView(KeyValueView stateView) {
		this.stateView = stateView;
	}

	public KeyValueView getCityView() {
		return cityView;
	}

	public void setCityView(KeyValueView cityView) {
		this.cityView = cityView;
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

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public KeyValueView getMachineView() {
		return machineView;
	}

	public void setMachineView(KeyValueView machineView) {
		this.machineView = machineView;
	}

	public KeyValueView getPickupRouteView() {
		return pickupRouteView;
	}

	public void setPickupRouteView(KeyValueView pickupRouteView) {
		this.pickupRouteView = pickupRouteView;
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

	public PickupRouteView getPickupRouteView1() {
		return pickupRouteView1;
	}

	public void setPickupRouteView1(PickupRouteView pickupRouteView1) {
		this.pickupRouteView1 = pickupRouteView1;
	}

	public static void isValid(LocationView locationView) throws EndlosAPIException {
		Validator.STRING.isValid(new InputField("LOCATION_NAME", locationView.getName(), true, 100,
				RegexEnum.ALPHANUMERIC_WITH_SPACE_HYPEN_DOT_AND));

		if (locationView.getCustomerView() == null
				|| (locationView.getCustomerView() != null && locationView.getCustomerView().getId() == null)) {
			throw new EndlosAPIException(ResponseCode.CUSTOMER_IS_MISSING.getCode(),
					ResponseCode.CUSTOMER_IS_MISSING.getMessage());
		}
		Validator.STRING.isValid(new InputField("ADDRESS", locationView.getAddress(), false, 100));

		if (!StringUtils.isBlank(locationView.getArea())) {
			Validator.STRING.isValid(new InputField("ADDRESS", locationView.getArea(), false, 100));
		}
		if (locationView.getCountryView() != null && locationView.getCountryView().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"Country " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
		if (locationView.getStateView() != null && locationView.getStateView().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"State " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
		if (locationView.getCityView() != null && locationView.getCityView().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"City " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
		Validator.STRING.isValid(new InputField("PINCODE", locationView.getPincode(), true, 4, 7, RegexEnum.NUMERIC));

		if (locationView.getLatitude() != null) {
			Validator.STRING
					.isValid(new InputField("LATITUDE", locationView.getLatitude(), false, 20, RegexEnum.NUMERIC));
		}
		if (locationView.getLongitude() != null) {
			Validator.STRING
					.isValid(new InputField("LONGITUDE", locationView.getLatitude(), false, 20, RegexEnum.NUMERIC));
		}
		if (locationView.getAltitude() != null) {
			Validator.STRING
					.isValid(new InputField("ALTITUDE", locationView.getLatitude(), false, 20, RegexEnum.NUMERIC));
		}
		Validator.STRING.isValid(
				new InputField("BRANCH_NUMBER", locationView.getBranchNumber(), true, 4, 4, RegexEnum.NUMERIC));
	}
}