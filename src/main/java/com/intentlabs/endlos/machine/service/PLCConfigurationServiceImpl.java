package com.intentlabs.endlos.machine.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.machine.enums.PLCConfigurationParameterEnum;
import com.intentlabs.endlos.machine.model.PLCConfigurationModel;
import com.intentlabs.endlos.machine.view.PLCConfigurationView;

@Service(value = "plcConfigurationService")
public class PLCConfigurationServiceImpl extends AbstractService<PLCConfigurationModel>
		implements PLCConfigurationService {

	@Override
	public String getEntityName() {
		return PLC_CONFIGURATION_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		PLCConfigurationView plcConfigurationView = (PLCConfigurationView) searchObject;
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (plcConfigurationView.getMachineView() != null && plcConfigurationView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", plcConfigurationView.getMachineView().getId()));
		}
		if (plcConfigurationView.getStartDate() != null && plcConfigurationView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("createDate", plcConfigurationView.getStartDate()));
			commonCriteria.add(Restrictions.le("createDate", plcConfigurationView.getEndDate()));
		}
		return commonCriteria;
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return PLCConfigurationParameterEnum.class;
	}
}
