
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
package com.intentlabs.endlos.machine.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.machine.model.TransactionLogModel;
import com.intentlabs.endlos.machine.view.TransactionLogView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 08/10/2021
 */
@Service(value = "transactionLogService")
public class TransactionLogServiceImpl extends AbstractService<TransactionLogModel> implements TransactionLogService {

	@Override
	public String getEntityName() {
		return TRANSACTION_LOG_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		TransactionLogView transactionLogView = (TransactionLogView) searchObject;
		if (!StringUtils.isEmpty(transactionLogView.getBarcode())) {
			commonCriteria.add(Restrictions.ilike("barcode", transactionLogView.getBarcode(), MatchMode.START));
		}
		if (transactionLogView.getTransactionView() != null
				&& transactionLogView.getTransactionView().getId() != null) {
			commonCriteria.createAlias("transactionModel", "transactionModel");
			commonCriteria.add(
					Restrictions.eq("transactionModel.transactionId", transactionLogView.getTransactionView().getId()));
		}
		return commonCriteria;
	}

	@Override
	public List<TransactionLogModel> getByTransactionId(Long transactionId) {
		Criteria criteria = setCommonCriteria(TRANSACTION_LOG_MODEL);
		criteria.createAlias("transactionModel", "transactionModel");
		criteria.add(Restrictions.eq("transactionModel.id", transactionId));
		return (List<TransactionLogModel>) criteria.list();
	}
}