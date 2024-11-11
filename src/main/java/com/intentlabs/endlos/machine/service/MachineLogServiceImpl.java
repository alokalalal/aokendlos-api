
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
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.logistic.model.PickupRouteModel;
import com.intentlabs.endlos.machine.enums.MachineLogOrderParameterEnum;
import com.intentlabs.endlos.machine.model.MachineLogModel;
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.MachineView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Service(value = "machineLogService")
public class MachineLogServiceImpl extends AbstractService<MachineLogModel> implements MachineLogService {

	@Override
	public String getEntityName() {
		return MACHINE_LOG_MODEL;
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
		//commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

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
		return MachineLogOrderParameterEnum.class;
	}

	@Override
	public List<MachineLogModel> doExport(MachineLogView machineLogView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria = setSearchCriteria(machineLogView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<MachineLogModel>) commonCriteria.list();
	}

	@Override
	public MachineLogModel getLatestResetDate(Long id, int materialId) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		commonCriteria.add(Restrictions.eq("materialType", materialId));
		commonCriteria.addOrder(Order.desc("hardResetDate"));
		commonCriteria.setMaxResults(1);
		return (MachineLogModel) commonCriteria.uniqueResult();
	}

	@Override
	public Long getLastHardResetDateOfMachine(Long id,Long locationId) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
//		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.isNotNull("hardResetDate")); // Add this line
		commonCriteria.add(Restrictions.ne("hardResetDate", 0l)); // Add this line to check for non-zero values
		commonCriteria.setProjection(Projections.property("hardResetDate"));
		commonCriteria.addOrder(Order.desc("hardResetDate"));
		commonCriteria.setMaxResults(1);
		return (Long) commonCriteria.uniqueResult();
	}

	@Override
	public Long getLastSoftResetDateOfMachine(Long id,Long locationId) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		//commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.eq("materialType", 1));
		commonCriteria.add(Restrictions.isNotNull("resetDate"));
		commonCriteria.add(Restrictions.ne("resetDate", 0l));
		commonCriteria.addOrder(Order.desc("resetDate"));
		commonCriteria.setMaxResults(1);
		MachineLogModel machineLogModel = (MachineLogModel) commonCriteria.uniqueResult();
		if (machineLogModel !=null)
			return machineLogModel.getResetDate();
		else
			return 0L;
	}

	@Override
	public Long getLastHardResetDateOfLocation(Long id) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.add(Restrictions.eq("locationModel.id", id));
		commonCriteria.setProjection(Projections.property("hardResetDate"));
		commonCriteria.add(Restrictions.isNotNull("hardResetDate")); // Add this line
		commonCriteria.addOrder(Order.desc("hardResetDate"));
		commonCriteria.setMaxResults(1);
		return (Long) commonCriteria.uniqueResult();
	}

	@Override
	public Long getMaterialBinCount(Long id, int materialId,Long resetDate,Long locationId) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
//		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.eq("materialType", materialId));
		commonCriteria.add(Restrictions.ge("resetDate", resetDate));
		commonCriteria.setProjection(Projections.rowCount());
		Long totalRows = (Long) commonCriteria.uniqueResult();
		return totalRows;
	}

	public List<MachineLogModel> getLatestResetDateToTillDateSoftReset() {
		//Fetching only highest date for each machine
		/* -----start comment
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.machineModel " +
				"WHERE ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.machineModel.id = ml.machineModel.id)";

		org.hibernate.Query query = getSession().createQuery(hql);
		return query.list();
		 -----end comment */

		// For highest machine wise latest resetDate data
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.machineModel " +
				"WHERE (ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.machineModel.id = ml.machineModel.id) " +
				"       OR ml.resetDate > (SELECT MAX(ml3.hardResetDate) FROM machineLogModel ml3 WHERE ml3.machineModel.id = ml.machineModel.id)) " +
				"ORDER BY ml.machineModel.id, ml.hardResetDate DESC, ml.resetDate DESC";

		org.hibernate.Query query = getSession().createQuery(hql);

		return query.list();

	}

	@Override
	public List<MachineLogModel> getHighestHardResetDateOfEachMachine(MachineView machineView) {
		/*//Fetching only highest date for each machine
		StringBuilder hqlBuilder = new StringBuilder();
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.machineModel " +
				"WHERE ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.machineModel.id = ml.machineModel.id) and ishardreset = true";
		hqlBuilder.append(hql);
		if (machineView != null) {
			hqlBuilder.append(" AND (COALESCE(:machineId, '') = '' OR ml.machineModel.machineId = :machineId)");
		}
		String finalHql = hqlBuilder.toString();

		org.hibernate.Query query = getSession().createQuery(finalHql);

		if (machineView != null) {
			String machineId = machineView.getMachineId();
			query.setParameter("machineId", machineId);
		} else {
			query.setParameter("machineId", "");
		}

		return query.list();*/

		StringBuilder hqlBuilder = new StringBuilder();
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.machineModel " +
				"WHERE ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.machineModel.id = ml.machineModel.id) and ishardreset = true";
		hqlBuilder.append(hql);

		if (machineView != null) {
			hqlBuilder.append(" AND (COALESCE(:machineId, '') = '' OR ml.machineModel.machineId = :machineId)");
		}
		if (machineView.getCustomerView() != null) {
			hqlBuilder.append(" AND ml.machineModel.customerModel.id = :customerId");
		}

		if (machineView.getLocationView() != null) {
			hqlBuilder.append(" AND ml.machineModel.locationModel.id = :locationId");
		}
		if (machineView.getStartDate() != null) {
			hqlBuilder.append(" AND ml.hardResetDate >= :startDate");
		}

		if (machineView.getEndDate() != null) {
			hqlBuilder.append(" AND ml.hardResetDate <= :endDate");
		}
		if (machineView.getFullTextSearch() != null) {
			hqlBuilder.append(" AND LOWER(ml.locationModel.pickupRouteModel.pickupRouteName) LIKE LOWER(:fullTextSearch)");
		}

		String finalHql = hqlBuilder.toString();
		org.hibernate.Query query = getSession().createQuery(finalHql);

		if (machineView != null) {
			String machineId = machineView.getMachineId();
			query.setParameter("machineId", machineId);
		} else {
			query.setParameter("machineId", "");
		}
		if (machineView != null) {
			if (machineView.getCustomerView() != null) {
				query.setParameter("customerId", machineView.getCustomerView().getId());
			}

			if (machineView.getLocationView() != null) {
				query.setParameter("locationId", machineView.getLocationView().getId());
			}
			if (machineView.getStartDate() != null) {
				query.setParameter("startDate", machineView.getStartDate());
			}

			if (machineView.getEndDate() != null) {
				query.setParameter("endDate", machineView.getEndDate());
			}
			if (machineView.getFullTextSearch() != null) {
				query.setParameter("fullTextSearch", "%" + machineView.getFullTextSearch() + "%");
			}
		}

		return query.list();
	}

	@Override
	public List<MachineLogModel> getHighestHardResetDateOfEachRoute(String fullTextSearch) {
		/*//working without filter
		String hql = "SELECT ml, ml.locationModel.pickupRouteModel FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.locationModel " +
				"WHERE ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.locationModel.pickupRouteModel.id = ml.locationModel.pickupRouteModel.id) and ishardreset = true" ;

		org.hibernate.query.Query<Object[]> query = getSession().createQuery(hql, Object[].class);
		List<Object[]> results = query.list();

		// Transform the result into a list of MachineLogModel
		List<MachineLogModel> machineLogModels = new ArrayList<>();
		for (Object[] result : results) {
			MachineLogModel machineLogModel = (MachineLogModel) result[0];
			LocationModel locationModel = machineLogModel.getLocationModel();
			PickupRouteModel pickupRouteModel = (PickupRouteModel) result[1];
			locationModel.setPickupRouteModel(pickupRouteModel);
			machineLogModel.setLocationModel(locationModel);
			machineLogModels.add(machineLogModel);
		}
		return machineLogModels;*/



		StringBuilder hqlBuilder = new StringBuilder();
		String hql = "SELECT ml, ml.locationModel.pickupRouteModel FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.locationModel " +
				"WHERE ml.hardResetDate = (SELECT MAX(ml2.hardResetDate) FROM machineLogModel ml2 WHERE ml2.locationModel.pickupRouteModel.id = ml.locationModel.pickupRouteModel.id) and ishardreset = true" ;
		hqlBuilder.append(hql);
		if(fullTextSearch != null && !fullTextSearch.trim().isEmpty()) {
			hqlBuilder.append(" AND (COALESCE(:pickupRouteName, '') = '' OR ml.locationModel.pickupRouteModel.pickupRouteName = :pickupRouteName)");
		}
		String finalHql = hqlBuilder.toString();
		org.hibernate.query.Query<Object[]> query = getSession().createQuery(finalHql, Object[].class);

		if(fullTextSearch != null && !fullTextSearch.trim().isEmpty()) {
			String pickupRouteName = fullTextSearch;
			query.setParameter("pickupRouteName", pickupRouteName);
		}

		List<Object[]> results = query.list();

		// Transform the result into a list of MachineLogModel
		List<MachineLogModel> machineLogModels = new ArrayList<>();
		for (Object[] result : results) {
			MachineLogModel machineLogModel = (MachineLogModel) result[0];
			LocationModel locationModel = machineLogModel.getLocationModel();
			PickupRouteModel pickupRouteModel = (PickupRouteModel) result[1];
			locationModel.setPickupRouteModel(pickupRouteModel);
			machineLogModel.setLocationModel(locationModel);
			machineLogModels.add(machineLogModel);
		}
		return machineLogModels;
	}

	@Override
	public List<MachineLogModel> getLatestResetDateToTillDateSoftResetForLogPerRoute(Long locationId) {
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.locationModel " +
				"WHERE ml.locationModel.id = :locationId " +
				"        and ml.resetDate >= (SELECT MAX(ml3.hardResetDate) FROM machineLogModel ml3 WHERE ml3.locationModel.id = :locationId) and ishardreset != true " +
				"ORDER BY ml.hardResetDate DESC, ml.resetDate DESC";
		org.hibernate.Query query = getSession().createQuery(hql);
		query.setParameter("locationId", locationId);
		return query.list();
	}
	@Override
	public List<MachineLogModel> getLatestResetDateToTillDateSoftReset(Long machineId) {
		String hql = "SELECT ml FROM machineLogModel ml " +
				"LEFT JOIN FETCH ml.machineModel " +
				"WHERE ml.machineModel.id = :machineId " +
				"        and ml.resetDate >= (SELECT MAX(ml3.hardResetDate) FROM machineLogModel ml3 WHERE ml3.machineModel.id = :machineId) and ishardreset != true " +
				"ORDER BY ml.hardResetDate DESC, ml.resetDate DESC";
		org.hibernate.Query query = getSession().createQuery(hql);
		query.setParameter("machineId", machineId);
		return query.list();
	}

	@Override
	public MachineLogModel getLatestSoftResetDate(Long id, int materialId) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		commonCriteria.add(Restrictions.eq("materialType", materialId));
		commonCriteria.add(Restrictions.isNotNull("resetDate"));
		commonCriteria.add(Restrictions.ne("resetDate", 0l));
		commonCriteria.addOrder(Order.desc("resetDate"));
		commonCriteria.setMaxResults(1);
		return (MachineLogModel) commonCriteria.uniqueResult();
	}

	@Override
	public Map<Integer, Long> getLatestHardResetDatesInSingleQuery(Long id) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL_ALL_MACHINE_STATE);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		commonCriteria.add(Restrictions.in("materialType", Arrays.asList(1, 2, 3)));
		commonCriteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("materialType"))
				.add(Projections.max("hardResetDate")));

		List<Object[]> results = commonCriteria.list();

		Map<Integer, Long> resultMap = new HashMap<>();
		for (Object[] result : results) {
			Integer materialId = (Integer) result[0];
			Long hardResetDate = (Long) result[1];
			resultMap.put(materialId, hardResetDate);
		}

		return resultMap;
	}

	@Override
	public Map<Integer, Long> getLatestSoftResetDatesInSingleQuery(Long id) {
		Criteria commonCriteria = setCommonCriteria(MACHINE_LOG_MODEL_ALL_MACHINE_STATE);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		commonCriteria.add(Restrictions.in("materialType", Arrays.asList(1, 2, 3)));
		commonCriteria.add(Restrictions.isNotNull("resetDate"));
		commonCriteria.add(Restrictions.ne("resetDate", 0L));
		commonCriteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("materialType"))
				.add(Projections.max("resetDate")));

		List<Object[]> results = commonCriteria.list();

		Map<Integer, Long> resultMap = new HashMap<>();
		for (Object[] result : results) {
			Integer materialId = (Integer) result[0];
			Long resetDate = (Long) result[1];
			resultMap.put(materialId, resetDate);
		}

		return resultMap;
	}
}