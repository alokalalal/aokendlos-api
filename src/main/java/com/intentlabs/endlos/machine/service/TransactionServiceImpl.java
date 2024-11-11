
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
import com.intentlabs.endlos.machine.enums.TransactionOrderParameterEnum;
import com.intentlabs.endlos.machine.model.TransactionModel;
import com.intentlabs.endlos.machine.view.TransactionSummary;
import com.intentlabs.endlos.machine.view.TransactionView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 08/10/2021
 */
@Service(value = "transactionService")
public class TransactionServiceImpl extends AbstractService<TransactionModel> implements TransactionService {

	@Override
	public String getEntityName() {
		return TRANSACTION_MODEL;
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
		TransactionView transactionView = (TransactionView) searchObject;
		if (!StringUtils.isEmpty(transactionView.getBarcode())) {
			commonCriteria.add(Restrictions.like("barcode", transactionView.getBarcode(), MatchMode.START));
		}
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		//commonCriteria.createAlias("machineModel.customerModel", "customerModel", JoinType.LEFT_OUTER_JOIN);

		if (transactionView.getMachineView() != null && transactionView.getMachineView().getId() != null) {
			commonCriteria.add(Restrictions.eq("machineModel.id", transactionView.getMachineView().getId()));
		}
		if (transactionView.getCustomerView() != null && transactionView.getCustomerView().getId() != null) {
			commonCriteria
					.add(Restrictions.eq("locationModel.customerModel.id", transactionView.getCustomerView().getId()));
		}
		if (transactionView.getLocationView() != null && transactionView.getLocationView().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.id", transactionView.getLocationView().getId()));
		}
		if (transactionView.getTransactionId() != null) {
			commonCriteria
					.add(Restrictions.ilike("transactionId", transactionView.getTransactionId(), MatchMode.START));
		}
		if (transactionView.getStartDate() != null && transactionView.getEndDate() != null) {
			commonCriteria.add(Restrictions.ge("dateEnd", transactionView.getStartDate()));
			commonCriteria.add(Restrictions.le("dateEnd", transactionView.getEndDate()));
		}
		if (!StringUtils.isEmpty(transactionView.getFullTextSearch())) {
			commonCriteria.add(Restrictions.like("barcode", transactionView.getFullTextSearch(), MatchMode.ANYWHERE));
		}
		commonCriteria.add(Restrictions.ne("totalValue", new BigDecimal(0)));
//		commonCriteria.addOrder(Order.desc("dateEnd"));
		return commonCriteria;
	}

	@Override
	public TransactionModel getByTransactionId(String transactionId) {
		Criteria criteria = setCommonCriteria(TRANSACTION_MODEL);
		criteria.setLockMode(LockMode.UPGRADE);
		criteria.add(Restrictions.eq("transactionId", transactionId));
		return (TransactionModel) criteria.uniqueResult();
	}

	@Override
	public List<TransactionModel> getListByTransactionId(String transactionId) {
		Criteria criteria = setCommonCriteria(TRANSACTION_MODEL);
		criteria.add(Restrictions.eq("transactionId", transactionId));
		return (List<TransactionModel>) criteria.list();
	}

	@Override
	public TransactionModel getLastTransactionByMachineId(Long machineId) {
		Criteria criteria = setCommonCriteria(TRANSACTION_MODEL);
		criteria.createAlias("machineModel", "machineModel");
		criteria.add(Restrictions.eq("machineModel.id", machineId));
		criteria.addOrder(Order.desc("dateEnd"));
		criteria.setMaxResults(1);
		return (TransactionModel) criteria.uniqueResult();
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return TransactionOrderParameterEnum.class;
	}

	@Override
	public List<TransactionModel> doExport(TransactionView transactionView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = getSession().createCriteria(TRANSACTION_MODEL);
		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.createAlias("locationModel.customerModel", "locationModel.customerModel", JoinType.LEFT_OUTER_JOIN);
		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null && Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
			commonCriteria.add(Restrictions.eq("locationModel.customerModel.id",Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		commonCriteria = setSearchCriteria(transactionView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return commonCriteria.list();
	}

	@Override
	public TransactionSummary findAllTransaction(TransactionView transactionView) {
		Criteria commonCriteria = setCommonCriteria(TRANSACTION_MODEL);
		commonCriteria = setSearchCriteria(transactionView, commonCriteria);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		commonCriteria.setProjection(Projections.projectionList().add(Projections.sum("patBottleCount"))
				.add(Projections.sum("aluBottleCount")).add(Projections.sum("glassBottleCount"))
				.add(Projections.sum("patBottleValue")).add(Projections.sum("aluBottleValue"))
				.add(Projections.sum("glassBottleValue")).add(Projections.sum("totalValue"))
				.add(Projections.rowCount()));

		Object[] result = (Object[]) commonCriteria.uniqueResult();

		TransactionSummary summary = new TransactionSummary();
		summary.setTotalPatBottleCount(result[0] != null ? (Long) result[0] : 0L);
		summary.setTotalAluBottleCount(result[1] != null ? (Long) result[1] : 0L);
		summary.setTotalGlassBottleCount(result[2] != null ? (Long) result[2] : 0L);
		summary.setTotalPatBottleValue(result[3] != null ? (BigDecimal) result[3] : BigDecimal.ZERO);
		summary.setTotalAluBottleValue(result[4] != null ? (BigDecimal) result[4] : BigDecimal.ZERO);
		summary.setTotalGlassBottleValue(result[5] != null ? (BigDecimal) result[5] : BigDecimal.ZERO);
		summary.setTotalValue(result[6] != null ? (BigDecimal) result[6] : BigDecimal.ZERO);
		summary.setTotalBottle(summary.getTotalPatBottleCount() + summary.getTotalAluBottleCount()
				+ summary.getTotalGlassBottleCount());
		summary.setTotalVoucher(result[7] != null ? (Long) result[7] : 0L);
		return summary;
	}

	@Override
	public List<TransactionModel> fetchTransaction() {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.add(Restrictions.eq("fetch", false));
		criteria.setMaxResults(100);
		criteria.addOrder(Order.desc("id"));
		return (List<TransactionModel>) criteria.list();
	}

	@Override
	public Map<String, Long> getTotalBottleCount(Long id, Long resetDate, Long locationId) {
		Criteria commonCriteria = setCommonCriteria(TRANSACTION_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
//		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.ge("dateEnd", resetDate));

		// Create a projection list to include the sum of three columns
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("patBottleCount"));
		projectionList.add(Projections.sum("aluBottleCount"));
		projectionList.add(Projections.sum("glassBottleCount"));

		commonCriteria.setProjection(projectionList);

		// Execute the query and retrieve the sums
		Object[] result = (Object[]) commonCriteria.uniqueResult();

		// Handle the case when no result is found
		if (result == null || result.length != 3) {
			return getDefaultCounts(); // Return a map with default values
		}

		// Retrieve individual sums from the result array
		Long patBottleCount = result[0] != null ? (Long) result[0] : 0L;
		Long aluBottleCount = result[1] != null ? (Long) result[1] : 0L;
		Long glassBottleCount = result[2] != null ? (Long) result[2] : 0L;

		// Reset the projection to get the actual results if needed
		commonCriteria.setProjection(null);

		// Create and return a map
		Map<String, Long> countsMap = new HashMap<String, Long>();
		countsMap.put("patBottleCount", patBottleCount);
		countsMap.put("aluBottleCount", aluBottleCount);
		countsMap.put("glassBottleCount", glassBottleCount);

		return countsMap;
	}

	private Map<String, Long> getDefaultCounts() {
		Map<String, Long> defaultCounts = new HashMap<>();
		defaultCounts.put("patBottleCount", 0L);
		defaultCounts.put("aluBottleCount", 0L);
		defaultCounts.put("glassBottleCount", 0L);
		return defaultCounts;
	}

	@Override
	public Long getTotalVoucher(Long id, Long resetDate, Long locationId) {
		Criteria commonCriteria = setCommonCriteria(TRANSACTION_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
//		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.ge("dateEnd", resetDate));
		// Set the projection to count the results
		commonCriteria.setProjection(Projections.rowCount());

		// Execute the query and retrieve the count
		Long totalCount = (Long) commonCriteria.uniqueResult();

		// Reset the projection to get the actual results if needed
		commonCriteria.setProjection(null);
		return totalCount;
	}

	@Override
	public BigDecimal getTotalWeight(Long id, Long resetDate, Long locationId) {
		Criteria commonCriteria = setCommonCriteria(TRANSACTION_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
//		commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.ge("dateEnd", resetDate));

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("weight"));
		commonCriteria.setProjection(projectionList);

		// Execute the query and retrieve the sum
		BigDecimal weight = (BigDecimal) commonCriteria.uniqueResult();

		// Reset the projection to get the actual results if needed
		commonCriteria.setProjection(null);

		return weight != null ? weight : BigDecimal.ZERO;
	}

	public Map<String, Long> getTotalBottleCountByMachineIdAndLocationIdAndLastDateOfTransaction(Long id, Long resetDate, Long locationId, Long endDate) {
		Criteria commonCriteria = setCommonCriteria(TRANSACTION_MODEL);
		commonCriteria.createAlias("machineModel", "machineModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("machineModel.id", id));
		//commonCriteria.createAlias("locationModel", "locationModel", JoinType.LEFT_OUTER_JOIN);
		commonCriteria.add(Restrictions.eq("locationModel.id", locationId));
		commonCriteria.add(Restrictions.ge("dateEnd", resetDate));
		commonCriteria.add(Restrictions.le("dateEnd", endDate)); // This ensures we only include data until the current date

		// Create a projection list to include the sum of three columns
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("patBottleCount"));
		projectionList.add(Projections.sum("aluBottleCount"));
		projectionList.add(Projections.sum("glassBottleCount"));

		commonCriteria.setProjection(projectionList);

		// Execute the query and retrieve the sums
		Object[] result = (Object[]) commonCriteria.uniqueResult();

		// Handle the case when no result is found
		if (result == null || result.length != 3) {
			return getDefaultCounts(); // Return a map with default values
		}

		// Retrieve individual sums from the result array
		Long patBottleCount = result[0] != null ? ((Number) result[0]).longValue() : 0L;
		Long aluBottleCount = result[1] != null ? ((Number) result[1]).longValue() : 0L;
		Long glassBottleCount = result[2] != null ? ((Number) result[2]).longValue() : 0L;

		// Reset the projection to get the actual results if needed
		commonCriteria.setProjection(null);

		// Create and return a map
		Map<String, Long> countsMap = new HashMap<>();
		countsMap.put("patBottleCount", patBottleCount);
		countsMap.put("aluBottleCount", aluBottleCount);
		countsMap.put("glassBottleCount", glassBottleCount);

		return countsMap;
	}

	@Override
	public Long getLastTransactionByMachineIdForAllMachineState(Long machineId) {
		Criteria criteria = setCommonCriteria(TRANSACTION_MODEL_FOR_ALL_MACHINE_STATE);
		criteria.createAlias("machineModel", "machineModel");
		criteria.add(Restrictions.eq("machineModel.id", machineId));
		criteria.setProjection(Projections.property("dateEnd"));
		criteria.addOrder(Order.desc("dateEnd"));
		criteria.setMaxResults(1);

		return (Long) criteria.uniqueResult();
	}

}