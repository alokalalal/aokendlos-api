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
 * This is Country model which maps country list table to class.
 * 
 * @author Nirav
 * @since 06/06/2018
 *
 */
public class CountryModel extends IdentifierModel {

	private static final long serialVersionUID = 6653648434546572167L;
	private String name;
	private Set<StateModel> states;

	private static Map<Long, CountryModel> countries = new ConcurrentHashMap<>();
	private static Map<Long, Set<StateModel>> countryWiseStates = new ConcurrentHashMap<>();

	public CountryModel() {
		super();
	}

	public CountryModel(Long id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<StateModel> getStates() {
		return states;
	}

	public void setStates(Set<StateModel> states) {
		this.states = states;
	}

	public static void addCountry(CountryModel countryModel) {
		countries.put(countryModel.getId(), countryModel);
	}

	public static void removeCountry(CountryModel countryModel) {
		countries.remove(countryModel.getId());
	}

	public static Map<Long, CountryModel> getCountries() {
		return countries;
	}

	public static void addCountryState(CountryModel countryModel) {
		countryWiseStates.put(countryModel.getId(), countryModel.getStates());
	}

	public static Map<Long, Set<StateModel>> getCountryWiseStates() {
		return countryWiseStates;
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