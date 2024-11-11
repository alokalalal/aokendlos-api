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
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.customer.enums.CustomerOrderParameterEnum;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.view.CustomerView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl extends AbstractService<CustomerModel> implements CustomerService {

	@Override
	public String getEntityName() {
		return CUSTOMER_MODEL;
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
		if (searchObject instanceof CustomerView) {
			CustomerView customerView = (CustomerView) searchObject;
			if (!StringUtils.isEmpty(customerView.getName())) {
				commonCriteria.add(Restrictions.ilike("name", customerView.getName(), MatchMode.START));
			}
			if (customerView.getActive() != null) {
				commonCriteria.add(Restrictions.eq("active", customerView.getActive()));
			}
		}
		return commonCriteria;
	}

	@Override
	public CustomerModel getLight(long id) {
		return get(id, CUSTOMER_MODEL);
	}

	@Override
	public CustomerModel getExtraLight(long id) {
		return get(id, CUSTOMER_MODEL);
	}

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	protected List<CustomerModel> findAll(String entityName) {
		Criteria criteria = setCommonCriteria(CUSTOMER_MODEL);
		return (List<CustomerModel>) criteria.list();
	}

	@Override
	public void hardDelete(CustomerModel customerModel) {
		getSession().delete(customerModel);
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return CustomerOrderParameterEnum.class;
	}

	@Override
	public List<CustomerModel> doExport(CustomerView customerView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(CUSTOMER_MODEL);
		commonCriteria = setSearchCriteria(customerView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<CustomerModel>) commonCriteria.list();
	}

	@Override
	public CustomerModel getByName(String name) {
		Criteria criteria = setCommonCriteria(CUSTOMER_MODEL);
		criteria.add(Restrictions.eq("name", name));
		return (CustomerModel) criteria.uniqueResult();
	}
	@Override
	public PageModel searchLight(CustomerView customerView, Integer start, Integer recordSize, Integer orderType,
								 Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(SUPER_LIGHT_CUSTOMER_MODEL);
		setSearchCriteria(customerView, commonCriteria);
		return getResults(commonCriteria, start, recordSize, orderType, orderParam);
	}
}