/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.logistic.service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.customer.enums.CustomerOrderParameterEnum;
import com.intentlabs.endlos.logistic.model.DailyPickupLogModel;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "dailyPickupLogService")
public class DailyPickupLogServiceImpl extends AbstractService<DailyPickupLogModel> implements DailyPickupLogService {

	@Override
	public String getEntityName() {
		return DAILYPICKUPLOGMODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null
				&& Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
			criteria.add(Restrictions.eq("id", Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof PickupRouteView) {
			PickupRouteView pickupRouteView = (PickupRouteView) searchObject;
			if (!StringUtils.isEmpty(pickupRouteView.getName())) {
				commonCriteria
						.add(Restrictions.ilike("pickupRouteName", pickupRouteView.getName(), MatchMode.ANYWHERE));
			}
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return CustomerOrderParameterEnum.class;
	}

	@Override
	public DailyPickupLogModel getByPickupRoute(Long pickupRouteModel, Long startEpoch, Long endEpoch) {
		Criteria criteria = setCommonCriteria(DAILYPICKUPLOGMODEL);
		criteria.createAlias("pickupRouteModel", "pickupRouteModel", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("pickupRouteModel.id", pickupRouteModel));
		criteria.add(Restrictions.ge("generatePlanDate", startEpoch));
		criteria.add(Restrictions.le("generatePlanDate", endEpoch));
		criteria.setMaxResults(1);
		return (DailyPickupLogModel) criteria.uniqueResult();
	}

	@Override
	public List<DailyPickupLogModel> getAllPickupRoute(String fullTextSearch, Long startEpoch, Long endEpoch) {
		Criteria criteria = setCommonCriteria(DAILYPICKUPLOGMODEL);
		criteria.createAlias("pickupRouteModel", "pickupRouteModel", JoinType.LEFT_OUTER_JOIN);
		if (fullTextSearch != null && !fullTextSearch.isEmpty()) {
			criteria.add(Restrictions.ilike("pickupRouteModel.pickupRouteName", fullTextSearch));
		}
		if (startEpoch != null && endEpoch != null) {
			criteria.add(Restrictions.ge("generatePlanDate", startEpoch));
			criteria.add(Restrictions.le("generatePlanDate", endEpoch));
		}
		return criteria.list();
	}

}