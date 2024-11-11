/*******************************************************************************
 * Copyright -2018 @Intentlabs
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
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.config.locale.LocaleConfiguration;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.setting.model.ResponseMessageModel;
import com.intentlabs.common.setting.view.ResponseMessageView;

/**
 * This class used to implement all database related operation that will be
 * performed on language Supported Model.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@Service(value = "responseMessageService")
@DependsOn("localeConfiguration")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ResponseMessageServiceImpl extends AbstractService<ResponseMessageModel>
		implements ResponseMessageService, CustomInitializationBean {

	@Autowired
	LocaleConfiguration localeConfiguration;

	@Override
	public String getEntityName() {
		return RESPONSE_MESSAGE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		return getSession().createCriteria(entityName);
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof ResponseMessageView) {
			ResponseMessageView responseMessageView = (ResponseMessageView) searchObject;
			if (responseMessageView.getLocale() != null) {
				commonCriteria.add(Restrictions.eq("locale", responseMessageView.getLocale()));
			}
			if (responseMessageView.getCode() != null) {
				commonCriteria.add(Restrictions.eq("code", responseMessageView.getCode()));
			}
		}
		return commonCriteria;
	}

	@Override
	public ResponseMessageModel get(int code, String locale) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.add(Restrictions.eq("locale", locale));
		criteria.add(Restrictions.eq("code", code));
		return (ResponseMessageModel) criteria.uniqueResult();
	}

	@Override
	public void onStartUp() throws EndlosAPIException {
		List<ResponseMessageModel> responseMessageModels = findAll();
		for (ResponseCode responseCode : ResponseCode.values()) {
			ResponseMessageModel responseMessageModel = new ResponseMessageModel();
			List<ResponseMessageModel> tempResponseMessageModels = responseMessageModels.stream()
					.filter(model -> Integer.valueOf(responseCode.getCode()).equals(model.getCode()))
					.collect(Collectors.toList());
			if (!tempResponseMessageModels.isEmpty()) {
				tempResponseMessageModels.stream().forEach(ResponseMessageModel::add);
				if (!tempResponseMessageModels.stream()
						.noneMatch(model -> localeConfiguration.getDefaultLocale().equals(model.getLocale()))) {
					continue;
				}

			}
			create(responseCode, responseMessageModel);
			ResponseMessageModel.add(responseMessageModel);
		}
	}

	private void create(ResponseCode responseCode, ResponseMessageModel responseMessageModel) {
		responseMessageModel.setCode(responseCode.getCode());
		responseMessageModel.setLocale(localeConfiguration.getDefaultLocale());
		responseMessageModel.setMessage(responseCode.getMessage());
		create(responseMessageModel);
	}
}