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
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.notification.enums.NotificationEnum;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.view.NotificationView;
import com.intentlabs.common.service.AbstractService;

/**
 * This is definition of Notification service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 23/05/2020
 */
@Service(value = "notificationService")
public class NotificationServiceImpl extends AbstractService<NotificationModel>
		implements NotificationService, CustomInitializationBean {

	@Override
	public String getEntityName() {
		return NOTIFICATION_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof NotificationView) {
			NotificationView notificationView = (NotificationView) searchObject;
			if (!StringUtils.isEmpty(notificationView.getName())) {
				commonCriteria.add(Restrictions.ilike("name", notificationView.getName(), MatchMode.START));
			}
		}
		return commonCriteria;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void onStartUp() throws EndlosAPIException {
		for (Entry<Integer, NotificationEnum> entry : NotificationEnum.MAP.entrySet()) {
			NotificationModel notificationModel = get(entry.getKey());
			if (notificationModel == null) {
				notificationModel = new NotificationModel();
				notificationModel.setId(Long.valueOf(entry.getKey()));
				notificationModel.setName(entry.getValue().getName());
				notificationModel.setEmail(false);
				notificationModel.setPush(false);
				create(notificationModel);
			}
			NotificationModel.getMAP().put(notificationModel.getId(), notificationModel);
		}
	}

	@Override
	public List<NotificationModel> customFindAll() {
		Criteria criteria = getSession().createCriteria(NOTIFICATION_MODEL);
		criteria.addOrder(Order.asc("id"));
		return (List<NotificationModel>) criteria.list();
	}
}
