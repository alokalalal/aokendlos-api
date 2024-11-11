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
package com.intentlabs.endlos.customer.service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.customer.enums.LocationOrderParameterEnum;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "locationService")
public class LocationServiceImpl extends AbstractService<LocationModel> implements LocationService {

	@Override
	public String getEntityName() {
		return LOCATION_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof LocationView) {
			LocationView locationView = (LocationView) searchObject;
			if (!StringUtils.isEmpty(locationView.getName())) {
				commonCriteria.add(Restrictions.ilike("name", locationView.getName(), MatchMode.START));
			}
			if (locationView.getCustomerView() != null && locationView.getCustomerView().getId() != null) {
				commonCriteria.createAlias("customerModel", "customerModel");
				commonCriteria.add(Restrictions.eq("customerModel.id", locationView.getCustomerView().getId()));
			}
			if (locationView.getActive() != null) {
				commonCriteria.add(Restrictions.eq("active", true));
			}
		}
		return commonCriteria;
	}

	@Override
	public LocationModel nonActivatedCustomer(Long id) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.add(Restrictions.eq("id", id));
		return (LocationModel) criteria.uniqueResult();
	}

	@Override
	public List<LocationModel> getByCustomer(Long id) {
		Criteria criteria = setCommonCriteria(LIGHT_LOCATION_MODEL);
		criteria.createAlias("customerModel", "customerModel");
		criteria.add(Restrictions.eq("customerModel.id", id));
		return (List<LocationModel>) criteria.list();
	}

	@Override
	public LocationModel getLight(long id) {
		return get(id, LIGHT_LOCATION_MODEL);
	}

	@Override
	public PageModel searchLight(LocationView locationView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(LIGHT_LOCATION_MODEL);
		setSearchCriteria(locationView, commonCriteria);
		return getResults(commonCriteria, start, recordSize, orderType, orderParam);
	}

	@Override
	public List<LocationModel> doExport(LocationView locationView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(LOCATION_MODEL);
		commonCriteria = setSearchCriteria(locationView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<LocationModel>) commonCriteria.list();
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return LocationOrderParameterEnum.class;
	}
	@Override
	public void hardDelete(LocationModel locationModel) {
		getSession().delete(locationModel);
	}

	@Override
	public List<LocationModel> getByPickedupRoute(DailyPickupAssigneeView dailyPickupAssigneeView, Integer start,
												  Integer recordSize) {
		Criteria criteria = setCommonCriteria(LOCATION_MODEL);
//		setSearchCriteria(locationView, commonCriteria);
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		if (start != null && recordSize != null) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(recordSize);
		}
		criteria.createAlias("customerModel", "customerModel");
		criteria.addOrder(Order.asc("customerModel.id"));
		criteria.addOrder(Order.asc("positionRoute"));

//		setOrder(criteria, orderType, orderParam);
		return (List<LocationModel>) criteria.list();
	}

	@Override
	public List<LocationModel> getByLocationIds(List<LocationView> locationViews) {
		Criteria criteria = setCommonCriteria(LOCATION_MODEL);
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		List<Long> ids = locationViews.stream().map(LocationView::getId).collect(Collectors.toList());
		criteria.add(Restrictions.in("id", ids));
		criteria.createAlias("customerModel", "customerModel");
		criteria.addOrder(Order.asc("customerModel.id"));
		criteria.addOrder(Order.asc("positionRoute"));
		return (List<LocationModel>) criteria.list();
	}
}