/*******************************************************************************
| * Copyright -2017 @intentlabs
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

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import com.intentlabs.common.location.model.CityModel;
import com.intentlabs.common.service.AbstractService;

/**
 * This class used to implement all database related operation that will be
 * performed on state table.
 * 
 * @author Nirav
 * @since 10/11/2017
 */
@Service(value = "cityService")
public class CityServiceImpl extends AbstractService<CityModel> implements CityService {

	@Override
	public String getEntityName() {
		return CITY_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		return getSession().createCriteria(entityName);
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}
}