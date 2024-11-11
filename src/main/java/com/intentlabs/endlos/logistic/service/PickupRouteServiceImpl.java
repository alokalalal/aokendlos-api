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
import com.intentlabs.endlos.logistic.model.PickupRouteModel;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "pickupRouteService")
public class PickupRouteServiceImpl extends AbstractService<PickupRouteModel> implements PickupRouteService {

	@Override
	public String getEntityName() {
		return PICKUPROUTE_MODEL;
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
				commonCriteria.add(Restrictions.ilike("pickupRouteName", pickupRouteView.getName(), MatchMode.ANYWHERE));
			}
		}
		return commonCriteria;
	}

	@Override
	public PickupRouteModel getLight(long id) {
		return get(id, PICKUPROUTE_MODEL);
	}

	@Override
	public PickupRouteModel getExtraLight(long id) {
		return get(id, PICKUPROUTE_MODEL);
	}

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	protected List<PickupRouteModel> findAll(String entityName) {
		Criteria criteria = setCommonCriteria(PICKUPROUTE_MODEL);
		return (List<PickupRouteModel>) criteria.list();
	}

	@Override
	public void hardDelete(PickupRouteModel pickupRouteModel) {
		getSession().delete(pickupRouteModel);
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return CustomerOrderParameterEnum.class;
	}

	@Override
	public List<PickupRouteModel> doExport(PickupRouteView pickupRouteView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(PICKUPROUTE_MODEL);
		commonCriteria = setSearchCriteria(pickupRouteView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<PickupRouteModel>) commonCriteria.list();
	}

	@Override
	public PickupRouteModel getByName(String name, String currentPickupRouteName) {
		Criteria criteria = setCommonCriteria(PICKUPROUTE_MODEL);
		criteria.add(Restrictions.ilike("pickupRouteName", name));

		if (currentPickupRouteName != null) {
			criteria.add(Restrictions.ne("pickupRouteName", currentPickupRouteName));
		}
		return (PickupRouteModel) criteria.uniqueResult();
	}
	@Override
	public PickupRouteModel getByNumber(Integer pickupRouteNo, Integer currentPickupRouteNo) {
		Criteria criteria = setCommonCriteria(PICKUPROUTE_MODEL);
		criteria.add(Restrictions.eq("pickupRouteNo", pickupRouteNo));

		if (currentPickupRouteNo != null) {
			criteria.add(Restrictions.ne("pickupRouteNo", currentPickupRouteNo));
		}
		return (PickupRouteModel) criteria.uniqueResult();
	}
}