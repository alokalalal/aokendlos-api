
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
package com.intentlabs.endlos.changelocation.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.changelocation.model.ChangeLocationModel;
import com.intentlabs.endlos.changelocation.view.ChangeLocationView;
import com.intentlabs.endlos.machine.enums.ChangeLocationOrderParameterEnum;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 07/09/2022
 */
@Service(value = "changeLocationService")
public class ChangeLocationServiceImpl extends AbstractService<ChangeLocationModel> implements ChangeLocationService {

	@Override
	public String getEntityName() {
		return CHANGE_LOCATION_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.createAlias("oldLocationModel", "oldLocationModel", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("oldCustomerModel", "oldCustomerModel", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("oldBarcodeTemplateModel", "oldBarcodeTemplateModel", JoinType.LEFT_OUTER_JOIN);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		ChangeLocationView changeLocationView = (ChangeLocationView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		//commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (changeLocationView.getMachineView() != null && changeLocationView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", changeLocationView.getMachineView().getId()));
		}
		if (changeLocationView.getStartDate() != null && changeLocationView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("requestDate", changeLocationView.getStartDate()));
			commonCriteria.add(Restrictions.le("requestDate", changeLocationView.getEndDate()));
		}
		/*if (changeLocationView.getOldCustomerView() != null
				&& changeLocationView.getOldCustomerView().getId() != null) {
			commonCriteria.add(Restrictions.eq("oldCustomerModel.id", changeLocationView.getOldCustomerView().getId()));
		}*/
		if (changeLocationView.getStatus() != null && changeLocationView.getStatus().getKey() != null) {
			commonCriteria.add(Restrictions.eq("status", changeLocationView.getStatus().getKey().intValue()));
		}
		/*if (changeLocationView.getOldLocationView() != null
				&& changeLocationView.getOldLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("oldLocationModel.id", changeLocationView.getOldLocationView().getId()));
		}*/

		if (changeLocationView.getCustomerView() != null) {
			commonCriteria.add(Restrictions.eq("customerModel.id", changeLocationView.getCustomerView().getId()));
		}

		/*if (changeLocationView.getMachineView() != null && changeLocationView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", changeLocationView.getMachineView().getId()));
		}*/
		if (changeLocationView.getLocationView() != null && changeLocationView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", changeLocationView.getLocationView().getId()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return ChangeLocationOrderParameterEnum.class;
	}

	@Override
	public List<ChangeLocationModel> doExport(ChangeLocationView changeLocationView, Integer orderType,
			Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(CHANGE_LOCATION_MODEL);
		commonCriteria = setSearchCriteria(changeLocationView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<ChangeLocationModel>) commonCriteria.list();
	}
}