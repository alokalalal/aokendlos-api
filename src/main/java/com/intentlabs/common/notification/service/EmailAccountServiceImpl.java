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
package com.intentlabs.common.notification.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.model.EmailAccountModel;
import com.intentlabs.common.notification.view.EmailAccountView;
import com.intentlabs.common.service.AbstractService;

/**
 * This is definition of Email Account service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 12/08/2017
 */
@Service(value = "emailAccountService")
public class EmailAccountServiceImpl extends AbstractService<EmailAccountModel>
		implements EmailAccountService, CustomInitializationBean {
	@Override
	public void onStartUp() throws EndlosAPIException {
		for (EmailAccountModel emailAccountModel : findAllByLight()) {
			EmailAccountModel.getMAP().put(emailAccountModel.getId(), emailAccountModel);
		}
	}

	@Override
	public String getEntityName() {
		return EMAIL_ACCOUNT;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		criteria.add(Restrictions.eq("archive", false));
		criteria.add(Restrictions.eq("active", true));
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof EmailAccountView) {
			EmailAccountView emailAccountView = (EmailAccountView) searchObject;
			if (!StringUtils.isBlank(emailAccountView.getName())) {
				commonCriteria.add(Restrictions.ilike("name", emailAccountView.getName(), MatchMode.START));
			}
		}
		return commonCriteria;
	}

	@Override
	public EmailAccountModel getLight(Long id) {
		return get(id, LIGHT_EMAIL_ACCOUNT);
	}

	@Override
	public List<EmailAccountModel> findAllByLight() {
		return findAll(LIGHT_EMAIL_ACCOUNT);
	}

	@Override
	public PageModel searchByLight(EmailAccountView emailAccountView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		return search(emailAccountView, start, recordSize, LIGHT_EMAIL_ACCOUNT,orderType,orderParam);
	}

	@Override
	public EmailAccountModel getByName(String name) {
		Criteria criteria = getSession().createCriteria(LIGHT_EMAIL_ACCOUNT);
		criteria.add(Restrictions.eq("name", name));
		return (EmailAccountModel) criteria.uniqueResult();
	}

	@Override
	public void hardDelete(EmailAccountModel emailAccountModel) {
		getSession().delete(emailAccountModel);
	}
}