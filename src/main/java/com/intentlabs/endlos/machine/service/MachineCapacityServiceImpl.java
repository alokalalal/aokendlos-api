
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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.machine.enums.MachineCapacityParameterEnum;
import com.intentlabs.endlos.machine.model.MachineCapacityModel;
import com.intentlabs.endlos.machine.view.MachineCapacityView;
import com.intentlabs.endlos.machine.view.MachineLogView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Service(value = "machieCapacityService")
public class MachineCapacityServiceImpl extends AbstractService<MachineCapacityModel>
		implements MachineCapacityService {

	@Override
	public String getEntityName() {
		return MACHINE_CAPACITY_MODLE;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		MachineCapacityView machineCapacityView = (MachineCapacityView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (machineCapacityView.getMachineView() != null && machineCapacityView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", machineCapacityView.getMachineView().getId()));
		}
		if (machineCapacityView.getStartDate() != null && machineCapacityView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("createDate", machineCapacityView.getStartDate()));
			commonCriteria.add(Restrictions.le("createDate", machineCapacityView.getEndDate()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return MachineCapacityParameterEnum.class;
	}
}