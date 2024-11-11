
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
import com.intentlabs.endlos.machine.enums.ReportOrderParameterEnum;
import com.intentlabs.endlos.machine.model.ReportModel;
import com.intentlabs.endlos.machine.view.ReportView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@Service(value = "reportService")
public class ReportServiceImpl extends AbstractService<ReportModel> implements ReportService {

	@Override
	public String getEntityName() {
		return REPORT_MODEL;
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
		ReportView reportView = (ReportView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		//commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);
		/*if (!StringUtils.isEmpty(reportView.getMachineView().getFullTextSearch())) {
			commonCriteria.add(Restrictions.disjunction()
					.add(Restrictions.ilike("id", reportView.getMachineView().getFullTextSearch(), MatchMode.ANYWHERE)));
		}*/
		if (reportView.getMachineView() != null && reportView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", reportView.getMachineView().getId()));
		}
		if (reportView.getStartDate() != null && reportView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("createDate", reportView.getStartDate()));
			commonCriteria.add(Restrictions.le("createDate", reportView.getEndDate()));
		}
		if (reportView.getCustomerView() != null && reportView.getCustomerView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.customerModel.id", reportView.getCustomerView().getId()));
		}
		if (reportView.getLocationView() != null && reportView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", reportView.getLocationView().getId()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return ReportOrderParameterEnum.class;
	}

	@Override
	public List<ReportModel> doExport(ReportView reportView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(REPORT_MODEL);
		commonCriteria = setSearchCriteria(reportView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<ReportModel>) commonCriteria.list();
	}

	@Override
	public ReportModel getRecords(Long dateEnd, Long locationId, Long machineId) {
		Criteria criteria = setCommonCriteria(REPORT_MODEL);
		criteria.createAlias("machineModel", "machineModel");
		criteria.add(Restrictions.eq("createDate", dateEnd));
		criteria.add(Restrictions.eq("locationModel.id", locationId));
		criteria.add(Restrictions.eq("machineModel.id", machineId));
		return (ReportModel) criteria.uniqueResult();
	}

	@Override
	public List<ReportModel> doCalculateBinCount(ReportView reportView) {
		Criteria commonCriteria = setCommonCriteria(REPORT_MODEL);
		commonCriteria = setSearchCriteria(reportView, commonCriteria);
		return (List<ReportModel>) commonCriteria.list();
	}

}