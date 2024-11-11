
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.machine.enums.MachineLogOrderParameterEnum;
import com.intentlabs.endlos.machine.enums.PrintLogOrderParameterEnum;
import com.intentlabs.endlos.machine.model.MachineLogModel;
import com.intentlabs.endlos.machine.model.PrintLogModel;
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.PrintLogView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Service(value = "printLogService")
public class PrintLogServiceImpl extends AbstractService<PrintLogModel> implements PrintLogService {

	@Override
	public String getEntityName() {
		return PRINT_LOG_MODEL;
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
		MachineLogView machineLogView = (MachineLogView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (machineLogView.getMachineView() != null && machineLogView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", machineLogView.getMachineView().getId()));
		}
		if (machineLogView.getStartDate() != null && machineLogView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("createDate", machineLogView.getStartDate()));
			commonCriteria.add(Restrictions.le("createDate", machineLogView.getEndDate()));
		}
		if (machineLogView.getCustomerView() != null && machineLogView.getCustomerView().getId() != null) {
			commonCriteria
					.add(Restrictions.eq("locationModel.customerModel.id", machineLogView.getCustomerView().getId()));
		}
		if (!StringUtils.isEmpty(machineLogView.getFullTextSearch())) {
			commonCriteria.add(Restrictions.like("errorName", machineLogView.getFullTextSearch(), MatchMode.ANYWHERE));
		}
		if (machineLogView.getLocationView() != null && machineLogView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", machineLogView.getLocationView().getId()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return PrintLogOrderParameterEnum.class;
	}

	@Override
	public List<PrintLogModel> doExport(PrintLogView printLogView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(PRINT_LOG_MODEL);
		commonCriteria = setSearchCriteria(printLogView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<PrintLogModel>) commonCriteria.list();
	}

	@Override
	public PrintLogModel getLatestResetDate(Long id) {
		Criteria commonCriteria = setCommonCriteria(PRINT_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		commonCriteria.addOrder(Order.desc("resetDate"));
		commonCriteria.setMaxResults(1);
		return (PrintLogModel) commonCriteria.uniqueResult();
	}
}