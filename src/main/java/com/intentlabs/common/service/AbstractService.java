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
package com.intentlabs.common.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.enums.OrderTypeEnum;
import com.intentlabs.common.model.Model;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.threadlocal.Auditor;

/**
 * This is abstract service class used to provide implementation or definition
 * of basic services which are used to perform database related activities.
 * 
 * @author Nirav.Shah
 * @since 25/12/2019
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public abstract class AbstractService<M extends Model> {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Setter method to set session factory object of hibernate.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * it returns current session bound with transaction boundary.
	 * 
	 * @return Session hibernate session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * This methods returns entity name against which all database related operation
	 * will performed through hibernate.
	 * 
	 * @return String entityname
	 */
	public abstract String getEntityName();

	/**
	 * It is used to set common search criteria. This criteria will be set base on
	 * logged in user.
	 * 
	 * @return {@link Criteria}
	 */
	public abstract Criteria setCommonCriteria(String entityName);

	/**
	 * It is used to set search criteria base on given search parameters.
	 * 
	 * @param model          model on which search criteria should be applied.
	 * @param commonCriteria return common criteria set by setCommonCriteria method
	 * @return {@link Criteria}
	 */
	public abstract Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria);

	/**
	 * It is used to insert a single record into database using model.
	 * 
	 * @param model
	 */
	public void create(M model) {
		saveModel(model, getEntityName());
	}

	/**
	 * This method is used to save model by given entityname.
	 * 
	 * @param model
	 * @param entityName
	 */
	protected void create(M model, String entityName) {
		saveModel(model, entityName);
	}

	/**
	 * It is used to insert bulk record into database using default entity.
	 * 
	 * @param models list of models
	 */
	public void createBulk(List<M> modelList) {
		for (M model : modelList) {
			saveModel(model, getEntityName());
		}
	}

	/**
	 * It is used to insert bulk record into database using given entity.
	 * 
	 * @param models list of models
	 */
	protected void createBulk(List<M> modelList, String entityName) {
		for (M model : modelList) {
			saveModel(model, entityName);
		}
	}

	private void saveModel(Model model, String entityName) {
		Auditor.createAudit(model);
		getSession().save(entityName, model);
	}

	/**
	 * It is used to update single record into database using default entity.
	 * 
	 * @param model
	 */
	public void update(M model) {
		updateModel(model, getEntityName());
	}

	/**
	 * It is used to update bulk record into database using default entity.
	 * 
	 * @param models
	 */
	public void updateBulk(List<M> modelList) {
		for (M model : modelList) {
			updateModel(model, getEntityName());
		}
	}

	/**
	 * It is used to update single record into database using given entity.
	 * 
	 * @param model
	 */
	protected void update(M model, String entityName) {
		updateModel(model, entityName);
	}

	/**
	 * It is used to update bulk record into database using given entity.
	 * 
	 * @param models
	 */
	protected void updateBulk(List<M> modelList, String entityName) {
		for (M model : modelList) {
			updateModel(model, entityName);
		}
	}

	private void updateModel(Model model, String entityName) {
		Auditor.updateAudit(model);
		getSession().update(entityName, model);
	}

	/**
	 * It is used to get single record base on given id using default entity.
	 * 
	 * @param id unique value to identify model
	 * @return model
	 */
	public M get(long id) {
		return get(id, getEntityName());
	}

	/**
	 * It is used to get single record base on given id using default entity.
	 * 
	 * @param id unique value to identify model
	 * @return model
	 */
	protected M get(long id, String entityName) {
		Criteria criteria = setCommonCriteria(entityName);
		criteria.add(Restrictions.eq("id", id));
		return (M) criteria.uniqueResult();
	}

	/**
	 * It is used to delete single record base on given id using default entity.
	 * 
	 * @param model
	 */
	public void delete(M model) {
		delete(model, getEntityName());
	}

	/**
	 * This method is used to delete a model with given entity.
	 * 
	 * @param model
	 * @param entityName
	 */
	protected void delete(M model, String entityName) {
		deleteModel(model, entityName);
	}

	/**
	 * It is used to delete bulk record base on given model using default entity.
	 * 
	 * @param models list of models
	 */
	public void deleteBulk(List<M> modelList) {
		for (M model : modelList) {
			deleteModel(model, getEntityName());
		}
	}

	/**
	 * It is used to delete bulk record base on given model using default entity.
	 * 
	 * @param models list of models
	 */
	public void deleteBulk(List<M> modelList, String entityName) {
		for (M model : modelList) {
			deleteModel(model, entityName);
		}
	}

	private void deleteModel(Model model, String entityName) {
		Auditor.archiveAudit(model);
		getSession().update(entityName, model);
	}

	/**
	 * This is used to fetch data that needs to be displayed on grid using default
	 * entity.
	 * 
	 * @param start      starting row from where to fetch record
	 * @param recordSize end row of record
	 * @return {@link PageModel}
	 */
	public PageModel getGridData(Integer start, Integer recordSize) {
		return getResults(setCommonCriteria(getEntityName()), start, recordSize, null, null);
	}

	/**
	 * This is used to fetch data that needs to be displayed on grid using given
	 * entity.
	 * 
	 * @param start      starting row from where to fetch record
	 * @param recordSize end row of record
	 * @param entityName name of entity define inside hbm file which needs to mapped
	 *                   to model.
	 * @return {@link PageModel}
	 */
	protected PageModel getGridData(Integer start, Integer recordSize, String entityName) {
		return getResults(setCommonCriteria(entityName), start, recordSize, null, null);
	}

	/**
	 * This is used to search model data on given criteria using default entity.
	 * 
	 * @param searchObject
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return {@link PageModel}
	 */
	public PageModel search(Object searchObject, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		return search(searchObject, start, recordSize, getEntityName(), orderType, orderParam);
	}

	/**
	 * This is used to search model data on given criteria using given entity.
	 * 
	 * @param searchObject
	 * @param start
	 * @param recordSize
	 * @param entityName
	 * @param orderType
	 * @param orderParam
	 * @return {@link PageModel}
	 */
	protected PageModel search(Object searchObject, Integer start, Integer recordSize, String entityName,
			Integer orderType, Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(entityName);
		setSearchCriteria(searchObject, commonCriteria);
		return getResults(commonCriteria, start, recordSize, orderType, orderParam);
	}

	protected PageModel getResults(Criteria criteria, Integer startNo, Integer recordSize, Integer orderType,
			Integer orderParam) {
		long records = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		if (startNo != null && recordSize != null) {
			criteria.setFirstResult(startNo);
			criteria.setMaxResults(recordSize);
		}
		setOrder(criteria, orderType, orderParam);
		List<? extends M> results = criteria.list();
		return PageModel.create(results, records);
	}

	/**
	 * This is used to fetch data that using hql query that needs to be displayed on
	 * grid.
	 * 
	 * @param query      hibernate query object
	 * @param startNo    starting row from where to fetch record
	 * @param recordSize end row of record
	 * @return {@link PageModel}
	 */
	public PageModel getResultUsingHQL(Query query, Integer startNo, Integer recordSize) {
		long records = query.list().size();
		if (startNo != null && recordSize != null) {
			query.setFirstResult(startNo);
			query.setMaxResults((1 + recordSize) - startNo);
		}
		List<? extends M> results = query.list();
		return PageModel.create(results, records);
	}

	/**
	 * This methods returns number of records base on given criteria.
	 * 
	 * @param criteria {@link Criteria}
	 * @return long number of records
	 */
	public long getRawCount(Criteria criteria) {
		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	public List<M> findAll() {
		return findAll(getEntityName());
	}

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	protected List<M> findAll(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return (List<M>) criteria.list();
	}

	/**
	 * This method will be used to add default order in criteria
	 * 
	 * @param criteria
	 * @param orderType
	 * @param orderParam
	 */
	protected void setOrder(Criteria criteria, Integer orderType, Integer orderParam) {
		if (getOrderParameterEnum() == null || orderType == null || orderParam == null) {
			criteria.addOrder(Order.desc("id"));
			return;
		}
		OrderTypeEnum orderTypeEnum = OrderTypeEnum.fromId(orderType);
		OrderParamEnumType orderParamEnumType = null;
		for (OrderParamEnumType tempOrderParamEnumType : getOrderParameterEnum().getEnumConstants()) {
			if (tempOrderParamEnumType.getId() == orderParam.intValue()) {
				orderParamEnumType = tempOrderParamEnumType;
				break;
			}
		}
		if (orderTypeEnum == null || orderParamEnumType == null) {
			criteria.addOrder(Order.desc("id"));
			return;
		}
		if (OrderTypeEnum.ASC.equals(orderTypeEnum)) {
//			if (orderParamEnumType.getParameter().contains(".")) {
//				String model = orderParamEnumType.getParameter().split("\\.")[0];
//				criteria.createAlias(model, model);
//			}
			criteria.addOrder(Order.asc(orderParamEnumType.getParameter()));
		}
		if (OrderTypeEnum.DESC.equals(orderTypeEnum)) {
//			if (orderParamEnumType.getParameter().contains(".")) {
//				String model = orderParamEnumType.getParameter().split("\\.")[0];
//				criteria.createAlias(model, model);
//			}
			criteria.addOrder(Order.desc(orderParamEnumType.getParameter()));
		}
	}

	/**
	 * This methods returns Order Param Enum against module which Module Required
	 * custom list ordering.
	 * 
	 * @return Enum which is implements to OrderParamEnumType interface.
	 */
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return null;
	}

	/**
	 * This method is used to load entity without firing a query to database like
	 * proxy object.
	 * 
	 * @param id
	 * @return
	 */
	public M load(long id) {
		return load(id, getEntityName());
	}

	/**
	 * This method is used to load entity without firing a query to database like
	 * proxy object.
	 * 
	 * @param id
	 * @return
	 */
	protected M load(long id, String entityName) {
		return (M) getSession().load(entityName, new Long(id));
	}

	public List<M> loadBulk(List<Long> idList) {
		return loadBulk(idList, getEntityName());
	}

	protected List<M> loadBulk(List<Long> idList, String entityName) {
		List<M> modelList = new ArrayList<>();
		for (Long id : idList) {
			modelList.add((M) getSession().load(entityName, new Long(id)));
		}
		return modelList;
	}
}
