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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.model.EmailContentModel;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.view.EmailContentView;
import com.intentlabs.common.service.AbstractService;

/**
 * This is definition of Email Content service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 12/08/2017
 */
@Service(value = "emailContentService")
public class EmailContentServiceImpl extends AbstractService<EmailContentModel> implements EmailContentService {

	@Override
	public String getEntityName() {
		return EMAIL_CONTENT;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof EmailContentView) {
			EmailContentView emailContentView = (EmailContentView) searchObject;
			if (emailContentView.getNotificationView() != null
					&& emailContentView.getNotificationView().getKey() != null) {
				commonCriteria.createAlias("notificationModel", "notificationModel");
				commonCriteria
						.add(Restrictions.eq("notificationModel.id", emailContentView.getNotificationView().getKey()));
			}
		}
		return commonCriteria;
	}

	@Override
	public PageModel searchLight(EmailContentView emailContentView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		return search(emailContentView, start, recordSize, LIGHT_EMAIL_CONTENT, orderType, orderParam);
	}

	@Override
	public void hardDelete(EmailContentModel emailContentModel) {
		getSession().delete(emailContentModel);
	}

	@Override
	public EmailContentModel findByNotification(NotificationModel notificationModel) {
		Criteria criteria = getSession().createCriteria(LIGHT_EMAIL_CONTENT);
		criteria.createAlias("notificationModel", "notificationModel");
		criteria.add(Restrictions.eq("notificationModel.id", notificationModel.getId()));
		return (EmailContentModel) criteria.uniqueResult();
	}
}