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

import java.time.Instant;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.notification.enums.EmailStatusEnum;
import com.intentlabs.common.notification.model.TransactionalEmailModel;
import com.intentlabs.common.service.AbstractService;

/**
 * This is definition of Email service which defines database operation which
 * can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 12/08/2017
 */
@Service(value = "transactionEmailService")
public class TransactionalEmailServiceImpl extends AbstractService<TransactionalEmailModel>
		implements TransactionalEmailService {

	@Override
	public String getEntityName() {
		return "transactionalEmailModel";
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}

	@Override
	public List<TransactionalEmailModel> getEmailList(int limit) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.setLockMode(LockMode.UPGRADE);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("status", EmailStatusEnum.NEW.getId()));
		criteria.add(Restrictions.le("dateSend", Instant.now().toEpochMilli()));
		return updateStatus(criteria, false);
	}

	@Override
	public List<TransactionalEmailModel> getFailedEmailList(int limit) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.setLockMode(LockMode.UPGRADE);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("status", EmailStatusEnum.FAILED.getId()));
		criteria.addOrder(Order.asc("retryCount"));
		return updateStatus(criteria, true);
	}

	private List<TransactionalEmailModel> updateStatus(Criteria criteria, boolean isRetryAttempt) {
		List<TransactionalEmailModel> emailList = criteria.list();
		for (TransactionalEmailModel transactionalEmail : emailList) {
			transactionalEmail.setStatus((EmailStatusEnum.SENT.getId()));
			transactionalEmail.setDateSent(Instant.now().toEpochMilli());
			if (isRetryAttempt) {
				transactionalEmail.setRetryCount(transactionalEmail.getRetryCount() + 1);
			}
			update(transactionalEmail);
		}
		return emailList;
	}
}
