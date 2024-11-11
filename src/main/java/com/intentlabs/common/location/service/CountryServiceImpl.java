/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.location.service;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.location.model.CountryModel;
import com.intentlabs.common.service.AbstractService;

/**
 * This class used to implement all database related operation that will be
 * performed on country table.
 * 
 * @author Nirav
 * @since 10/11/2017
 */
@Service(value = "countryService")
public class CountryServiceImpl extends AbstractService<CountryModel>
		implements CountryService, CustomInitializationBean {

	@Override
	public String getEntityName() {
		return COUNTRY_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		return getSession().createCriteria(getEntityName());
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}

	@Override
	public void onStartUp() {
		for (CountryModel countryModel : findAll()) {
			CountryModel.addCountry(countryModel);
			CountryModel.addCountryState(countryModel);
		}
	}
}