
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

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;
import com.intentlabs.endlos.machine.enums.*;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.view.MachineView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 18/11/2021
 */
@Service(value = "machineService")
public class MachineServiceImpl extends AbstractService<MachineModel> implements MachineService {

	@Override
	public String getEntityName() {
		return MACHINE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		criteria.createAlias("customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);
		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null
				&& Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
			criteria.add(Restrictions.eq("customerModel.id", Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		MachineView machineView = (MachineView) searchObject;
		if (!StringUtils.isEmpty(machineView.getFullTextSearch())) {
			commonCriteria.add(Restrictions.disjunction()
					.add(Restrictions.ilike("machineId", machineView.getFullTextSearch(), MatchMode.ANYWHERE)));
		}
		if (machineView.getMachineViews() != null && !machineView.getMachineViews().isEmpty()) {
			List<Long> ids = new ArrayList<Long>();
			for (MachineView id : machineView.getMachineViews()) {
				ids.add(id.getId());
			}
			commonCriteria.add(Restrictions.in("id", ids));
		}
		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);

		if (machineView.getMachineId() != null) {
			commonCriteria.add(Restrictions.eq("machineId", machineView.getMachineId()));
		}
		if (machineView.getCustomerView() != null) {
			commonCriteria.add(Restrictions.eq("customerModel.id", machineView.getCustomerView().getId()));
		}
		if (machineView.getMachineDevelopmentStatus() != null
				&& machineView.getMachineDevelopmentStatus().getKey() != null) {
			MachineDevelopmentStatusEnum machineDevelopmentStatusEnum = MachineDevelopmentStatusEnum
					.fromId(machineView.getMachineDevelopmentStatus().getKey().intValue());
			if (machineDevelopmentStatusEnum != null) {
				commonCriteria.add(Restrictions.eq("machineDevelopmentStatus", machineDevelopmentStatusEnum.getId()));
			}
		}
		if (machineView.getMachineActivityStatus() != null && machineView.getMachineActivityStatus().getKey() != null) {
			MachineActivityStatusEnum machineActivityStatusEnum = MachineActivityStatusEnum
					.fromId(machineView.getMachineActivityStatus().getKey().intValue());
			if (machineActivityStatusEnum != null) {
				commonCriteria.add(Restrictions.eq("machineActivityStatus", machineActivityStatusEnum.getId()));
			}
		}
		if (machineView.getBinFullStatus() != null && machineView.getBinFullStatus().getKey() != null) {
			MachineBinFullStatusEnum machineBinFullStatusEnum = MachineBinFullStatusEnum
					.fromId(machineView.getBinFullStatus().getKey().intValue());
			if (machineBinFullStatusEnum != null) {
				commonCriteria.add(Restrictions.eq("binFullStatus", machineBinFullStatusEnum.getId()));
			}
		}
		if (machineView.getMachineType() != null && machineView.getMachineType().getKey() != null) {
			MachineTypeEnum machineTypeEnum = MachineTypeEnum.fromId(machineView.getMachineType().getKey().intValue());
			if (machineTypeEnum != null) {
				commonCriteria.add(Restrictions.eq("machineType", machineTypeEnum.getId()));
			}
		}
		if (machineView.getLocationView() != null && machineView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", machineView.getLocationView().getId()));
		}
		if (machineView.getCityView() != null && machineView.getCityView().getId() != null) {
			commonCriteria.createAlias("locationModel.cityModel", "cityModel", JoinType.LEFT_OUTER_JOIN);
			commonCriteria.add(Restrictions.eq("cityModel.id", machineView.getCityView().getId()));
		}
		return commonCriteria;
	}

	@Override
	public MachineModel getByMachineId(String machineId) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		criteria.add(Restrictions.eq("machineId", machineId));
		return (MachineModel) criteria.uniqueResult();
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return MachineOrderParameterEnum.class;
	}

	@Override
	public List<MachineModel> findAllMachine(MachineView machineView) {
		Criteria commonCriteria = setCommonCriteria(LIGHT_MACHINE_MODEL);
		commonCriteria = setSearchCriteria(machineView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		return (List<MachineModel>) commonCriteria.list();
	}

	@Override
	public List<MachineModel> doExport(MachineView machineView, Integer orderType, Integer orderParam) {
		/*Criteria commonCriteria = setCommonCriteria(MACHINE_MODEL);
		commonCriteria = setSearchCriteria(machineView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<MachineModel>) commonCriteria.list();*/

		Criteria commonCriteria = setCommonCriteria(SUPER_LIGHT_MACHINE_MODEL);
		long records = (Long) commonCriteria.setProjection(Projections.rowCount()).uniqueResult();
		commonCriteria = setSearchCriteria(machineView, commonCriteria);
		commonCriteria.add(Restrictions.eq("active", true));
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<MachineModel>) commonCriteria.list();
	}

	@Override
	public List<MachineModel> findByBarcodeTemplateId(Long barcodeTemplateId) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		criteria.createAlias("barcodeTemplateModel", "barcodeTemplateModel");
		criteria.add(Restrictions.eq("barcodeTemplateModel.id", barcodeTemplateId));
		return (List<MachineModel>) criteria.list();
	}

	@Override
	public List<MachineModel> findAll() {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		return (List<MachineModel>) criteria.list();
	}

	@Override
	public MachineModel getLastMachine() {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		return (MachineModel) criteria.uniqueResult();
	}

	@Override
	public List<MachineModel> getByLocation(Long id) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		criteria.createAlias("locationModel", "locationModel");
		criteria.add(Restrictions.eq("locationModel.id", id));
		return (List<MachineModel>) criteria.list();
	}

	@Override
	public List<MachineModel> getByCustomer(Long id) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		criteria.add(Restrictions.eq("customerModel.id", id));
		return (List<MachineModel>) criteria.list();
	}

	public static void main(String[] args) {
		List<Long> ids = new ArrayList<Long>();
		for (Long i = 0l; i < 10l; i++) {
			ids.add(i);
		}
		System.out.println(ids);
	}

	@Override
	public String machineLastNumberByLocation(MachineView machineView) {
		/*
		 * //this is for textbox for auto assign branch-wise machine number Criteria
		 * criteria = setCommonCriteria(MACHINE_MODEL);
		 * 
		 * criteria.createAlias("locationModel", "locationModel",
		 * JoinType.LEFT_OUTER_JOIN); if (machineView.getLocationView() != null &&
		 * machineView.getLocationView().getId() != null) {
		 * criteria.add(Restrictions.eq("locationModel.id",
		 * machineView.getLocationView().getId())); } if (machineView.getCustomerView()
		 * != null && machineView.getCustomerView().getId() != null) {
		 * criteria.add(Restrictions.eq("customerModel.id",
		 * machineView.getCustomerView().getId())); }
		 * criteria.addOrder(Order.desc("updateDate")); criteria.setMaxResults(1);
		 * MachineModel machine = (MachineModel) criteria.uniqueResult();
		 * 
		 * if (machine == null || machine.getBranchMachineNumber() == null ||
		 * machine.getBranchMachineNumber().isEmpty()) { return "1"; } return "" +
		 * (Integer.parseInt(machine.getBranchMachineNumber()) + 1);
		 */

		Criteria criteria = setCommonCriteria(MACHINE_MODEL);

		criteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		if (machineView.getLocationView() != null && machineView.getLocationView().getId() != null) {
			criteria.add(Restrictions.eq("locationModel.id", machineView.getLocationView().getId()));
		}
		if (machineView.getCustomerView() != null && machineView.getCustomerView().getId() != null) {
			criteria.add(Restrictions.eq("customerModel.id", machineView.getCustomerView().getId()));
		}

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.max("branchMachineNumber"));

		criteria.setProjection(projectionList);

		Object result = criteria.uniqueResult();

		if (result == null) {
			return "1";
		}

		// Handle the case where "branchMachineNumber" is stored as a String
		String maxBranchMachineNumber = (String) result;

		if (maxBranchMachineNumber.isEmpty()) {
			return "1";
		}

		return String.valueOf(Integer.parseInt(maxBranchMachineNumber) + 1);
	}

	@Override
	public List<MachineModel> usedMachineNumberByLocation(MachineView machineView) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);

		criteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		if (machineView.getLocationView() != null && machineView.getLocationView().getId() != null) {
			criteria.add(Restrictions.eq("locationModel.id", machineView.getLocationView().getId()));
		}
		if (machineView.getCustomerView() != null && machineView.getCustomerView().getId() != null) {
			criteria.add(Restrictions.eq("customerModel.id", machineView.getCustomerView().getId()));
		}

		criteria.add(Restrictions.isNotNull("branchMachineNumber"));
		criteria.add(Restrictions.not(Restrictions.eq("branchMachineNumber", "")));

		return (List<MachineModel>) criteria.list();
	}

	@Override
	public List<MachineModel> getByMachineIds(List<MachineView> machineViews) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		List<Long> ids = machineViews.stream().map(MachineView::getId).collect(Collectors.toList());
		criteria.add(Restrictions.in("id", ids));
		criteria.createAlias("locationModel", "locationModel");
//		criteria.addOrder(Order.asc("customerModel.id"));
		criteria.addOrder(Order.asc("locationModel.positionRoute"));
		return (List<MachineModel>) criteria.list();
	}

	@Override
	public PageModel getByPickedupRoute(DailyPickupAssigneeView dailyPickupAssigneeView, Integer start,
			Integer recordSize) {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL);
//		setSearchCriteria(locationView, commonCriteria);
		long records = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		criteria.createAlias("locationModel", "locationModel");
		if (start != null && recordSize != null) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(recordSize);
		}
		if (dailyPickupAssigneeView.getCustomerView() != null) {
			criteria.add(Restrictions.eq("customerModel.id", dailyPickupAssigneeView.getCustomerView().getId()));
		}
		if (dailyPickupAssigneeView.getLocationView() != null) {
			criteria.add(Restrictions.eq("locationModel.id", dailyPickupAssigneeView.getLocationView().getId()));
		}
		criteria.createAlias("locationModel.pickupRouteModel", "pickupRouteModel"); // corrected alias
		criteria.addOrder(Order.asc("pickupRouteModel.pickupRouteName"));

		if (dailyPickupAssigneeView.getFullTextSearch() != null && !dailyPickupAssigneeView.getFullTextSearch().isEmpty()) {
			criteria.add(Restrictions.ilike("pickupRouteModel.pickupRouteName", dailyPickupAssigneeView.getFullTextSearch(), MatchMode.ANYWHERE));
		}
		if (dailyPickupAssigneeView.getPickupRouteView() != null) {
			criteria.add(Restrictions.eq("pickupRouteModel.id", dailyPickupAssigneeView.getPickupRouteView().getId()));
		}
		if(dailyPickupAssigneeView.getMachineView() !=null) {
			criteria.add(Restrictions.eq("id", dailyPickupAssigneeView.getMachineView().getId()));
		}
		if(dailyPickupAssigneeView.getLocationView() !=null) {
			criteria.add(Restrictions.eq("locationModel.id", dailyPickupAssigneeView.getLocationView().getId()));
		}
		if(dailyPickupAssigneeView.getLastPickupDate() !=null) {
			criteria.add(Restrictions.eq("locationModel.id", dailyPickupAssigneeView.getLocationView().getId()));
		}
		criteria.addOrder(Order.asc("customerModel.id"));
		criteria.addOrder(Order.asc("locationModel.positionRoute"));
//		setOrder(criteria, orderType, orderParam);
		return PageModel.create((List<MachineModel>) criteria.list(), records);
	}

	@Override
	public List<MachineModel> dropDown() {
		Criteria criteria = setCommonCriteria(EXTRA_LIGHT_MACHINE_MODEL);
		return (List<MachineModel>) criteria.list();
	}

	@Override
	public PageModel machineList(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(SUPER_LIGHT_MACHINE_MODEL);
		long records = (Long) commonCriteria.setProjection(Projections.rowCount()).uniqueResult();
		commonCriteria = setSearchCriteria(machineView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		commonCriteria.setFirstResult(start);
		commonCriteria.setMaxResults(recordSize);
		setOrder(commonCriteria, orderType, orderParam);
		return PageModel.create((List<MachineModel>) commonCriteria.list(), records);
	}

	@Override
	public PageModel machineListForAssignBarcodeTemplate(MachineView machineView, Integer start, Integer recordSize, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(EXTRA_SMALL_MACHINE_MODEL);
		long records = (Long) commonCriteria.setProjection(Projections.rowCount()).uniqueResult();
		commonCriteria = setSearchCriteria(machineView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		commonCriteria.setFirstResult(start);
		commonCriteria.setMaxResults(recordSize);
		setOrder(commonCriteria, orderType, orderParam);
		return PageModel.create((List<MachineModel>) commonCriteria.list(), records);
	}

	@Override
	public MachineModel getByMachineIdFromSmallModel(Long id) {
		Criteria criteria = setCommonCriteria(EXTRA_SMALL_MACHINE_MODEL);
		criteria.add(Restrictions.eq("id", id));
		return (MachineModel) criteria.uniqueResult();
	}

	@Override
	public List<MachineModel> findAllMachineState() {
		Criteria criteria = setCommonCriteria(MACHINE_MODEL_ALL_STATE);
		criteria.add(Restrictions.eq("active", true));
		return (List<MachineModel>) criteria.list();
	}
}