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
package com.intentlabs.endlos.systemspecification.service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.customer.enums.CustomerOrderParameterEnum;
import com.intentlabs.endlos.systemspecification.model.SystemSpecificationModel;
import com.intentlabs.endlos.systemspecification.view.SystemSpecificationView;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "SystemSpecificationService")
public class SystemSpecificationServiceImpl extends AbstractService<SystemSpecificationModel> implements SystemSpecificationService {

	@Override
	public String getEntityName() {
		return SYSTEM_SPECIFICATION_MODEL;
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
		SystemSpecificationView systemSpecificationView = (SystemSpecificationView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		if (systemSpecificationView.getMachineView() != null && systemSpecificationView.getMachineView().getMachineId() != null) {
			commonCriteria.add(Restrictions.ilike("machineModel.machineId", systemSpecificationView.getMachineView().getMachineId(), MatchMode.ANYWHERE));
		}
		return commonCriteria;


		/*if (searchObject instanceof SystemSpecificationView) {
			SystemSpecificationView systemSpecificationView = (SystemSpecificationView) searchObject;
			commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
			if (machineBarcodeFileView.getFileView() != null) {
				commonCriteria.add(Restrictions.like("fileModel.name", machineBarcodeFileView.getFileView().getFileId(), MatchMode.ANYWHERE));
			}
		}
		return commonCriteria;*/
	}

	@Override
	public SystemSpecificationModel getLight(long id) {
		return get(id, SYSTEM_SPECIFICATION_MODEL);
	}

	@Override
	public SystemSpecificationModel getExtraLight(long id) {
		return get(id, SYSTEM_SPECIFICATION_MODEL);
	}

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	protected List<SystemSpecificationModel> findAll(String entityName) {
		Criteria criteria = setCommonCriteria(SYSTEM_SPECIFICATION_MODEL);
		return (List<SystemSpecificationModel>) criteria.list();
	}

	@Override
	public void hardDelete(SystemSpecificationModel systemSpecificationModel) {
		getSession().delete(systemSpecificationModel);
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return CustomerOrderParameterEnum.class;
	}

	@Override
	public List<SystemSpecificationModel> doExport(SystemSpecificationView systemSpecificationView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(SYSTEM_SPECIFICATION_MODEL);
		commonCriteria = setSearchCriteria(systemSpecificationView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<SystemSpecificationModel>) commonCriteria.list();
	}
}