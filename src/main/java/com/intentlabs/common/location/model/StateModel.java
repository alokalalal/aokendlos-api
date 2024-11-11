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
package com.intentlabs.common.location.model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.intentlabs.common.model.IdentifierModel;

/**
 * This is State model which maps state list table to class.
 * 
 * @author Nirav
 * @since 06/06/2018
 *
 */
public class StateModel extends IdentifierModel {

	private static final long serialVersionUID = 6653648434546572167L;
	private String name;
	private CountryModel countryModel;
	private Set<CityModel> cities;

	private static Map<Long, StateModel> states = new ConcurrentHashMap<>();
	private static Map<Long, Set<CityModel>> stateWiseCities = new ConcurrentHashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryModel getCountryModel() {
		return countryModel;
	}

	public void setCountryModel(CountryModel countryModel) {
		this.countryModel = countryModel;
	}

	public Set<CityModel> getCities() {
		return cities;
	}

	public void setCities(Set<CityModel> cities) {
		this.cities = cities;
	}

	public static void addState(StateModel stateModel) {
		states.put(stateModel.getId(), stateModel);
	}

	public static void removeState(StateModel stateModel) {
		states.remove(stateModel.getId());
	}

	public static Map<Long, StateModel> getStates() {
		return states;
	}

	public static void addStateCity(Long id, Set<CityModel> cityModelList) {
		stateWiseCities.put(id, cityModelList);
	}

	public static Map<Long, Set<CityModel>> getStateWiseCities() {
		return stateWiseCities;
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