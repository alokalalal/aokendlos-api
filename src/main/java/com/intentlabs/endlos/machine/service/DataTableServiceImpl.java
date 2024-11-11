
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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.machine.model.DataTableModel;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 05/10/2021
 */
@Service(value = "dataTableService")
public class DataTableServiceImpl extends AbstractService<DataTableModel> implements DataTableService {

	@Override
	public String getEntityName() {
		return DATA_TABLE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		MachineView machineView = (MachineView) searchObject;
		if (!StringUtils.isEmpty(machineView.getMachineId())) {
			commonCriteria.add(Restrictions.ilike("machineID", machineView.getMachineId(), MatchMode.START));
		}
		return commonCriteria;
	}

	@Override
	public DataTableModel getByBarcode(String barcode) {
		Criteria criteria = setCommonCriteria(DATA_TABLE_MODEL);
		criteria.add(Restrictions.eq("barcode", barcode));
		return (DataTableModel) criteria.uniqueResult();
	}
}