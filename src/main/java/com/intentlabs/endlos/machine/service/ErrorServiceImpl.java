
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
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.machine.enums.ErrorOrderParameterEnum;
import com.intentlabs.endlos.machine.model.ErrorModel;
import com.intentlabs.endlos.machine.view.ErrorView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@Service(value = "errrorService")
public class ErrorServiceImpl extends AbstractService<ErrorModel> implements ErrorService {

	@Override
	public String getEntityName() {
		return ERROR_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("locationModel.customerModel", "locationModel.customerModel", JoinType.LEFT_OUTER_JOIN);
		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null
				&& Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
			criteria.add(Restrictions.eq("locationModel.customerModel.id",
					Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		ErrorView errorView = (ErrorView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		//commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (errorView.getMachineView() != null && errorView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", errorView.getMachineView().getId()));
		}
		if (errorView.getStartDate() != null && errorView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("createDate", errorView.getStartDate()));
			commonCriteria.add(Restrictions.le("createDate", errorView.getEndDate()));
		}
		if (errorView.getCustomerView() != null && errorView.getCustomerView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.customerModel.id", errorView.getCustomerView().getId()));
		}
		if (!StringUtils.isEmpty(errorView.getFullTextSearch())) {
			commonCriteria.add(Restrictions.like("errorName", errorView.getFullTextSearch(), MatchMode.ANYWHERE));
		}
		if (errorView.getLocationView() != null && errorView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", errorView.getLocationView().getId()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return ErrorOrderParameterEnum.class;
	}

	@Override
	public List<ErrorModel> doExport(ErrorView errorView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(ERROR_MODEL);
		commonCriteria = setSearchCriteria(errorView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<ErrorModel>) commonCriteria.list();
	}
}