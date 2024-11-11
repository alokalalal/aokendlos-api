/*******************************************************************************
| * Copyright -2019 @intentlabs
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
package com.intentlabs.common.setting.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.setting.model.SystemSettingModel;

/**
 * This class used to implement all database related operation that will be
 * performed on system setting table.
 * 
 * @author Nirav
 * @since 20/12/2019
 */
@Service(value = "systemSettingService")
public class SystemSettingServiceImpl extends AbstractService<SystemSettingModel>
		implements SystemSettingService, CustomInitializationBean {

	@Override
	public String getEntityName() {
		return SYSTEM_SETTING_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}

	@Override
	public void onStartUp() throws EndlosAPIException {
		List<SystemSettingModel> systemSettingModels = findAll();
		for (SystemSettingModel systemSettingModel : systemSettingModels) {
			SystemSettingModel.getMAP().put(systemSettingModel.getKey(), systemSettingModel.getValue());
		}
	}

	@Override
	public SystemSettingModel get(String key) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.add(Restrictions.eq("key", key));
		return (SystemSettingModel) criteria.uniqueResult();
	}
}