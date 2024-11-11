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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.intentlabs.common.model.Model;
import com.intentlabs.common.model.PageModel;

/**
 * It's definition of basic services used to perform database related
 * activities. It works on given model.
 * 
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public interface BaseService<M extends Model> extends Service {

	/**
	 * It is used to insert a single record into database using default class.
	 * 
	 * @param model
	 */
	void create(M model);

	/**
	 * It is used to insert bulk record into database using default class.
	 * 
	 * @param model list
	 */
	void createBulk(List<M> modelList);

	/**
	 * It is used to update single record into database using default class.
	 * 
	 * @param model
	 */
	void update(M model);

	/**
	 * It is used to update bulk record into database using default class.
	 * 
	 * @param models
	 */
	void updateBulk(List<M> modelList);

	/**
	 * It is used to get single record base on given id using default class.
	 * 
	 * @param id unique value to identify model
	 * @return model
	 */
	M get(long id);

	/**
	 * It is used to delete single record base on given id using default class.
	 * 
	 * @param deleteModel
	 */
	void delete(M model);

	/**
	 * It is used to delete bulk record base on given model using default class.
	 * 
	 * @param modelList
	 */
	void deleteBulk(List<M> modelList);

	/**
	 * This is used to search model data on given criteria using default class.
	 * 
	 * @param searchObject {@link Object}
	 * @param start        starting row from where to fetch record
	 * @param recordSize   end row of record
	 * @param orderParam
	 * @param orderType
	 * @return {@link PageModel}
	 */
	PageModel search(Object searchObject, Integer start, Integer recordSize, Integer orderType, Integer orderParam);

	/**
	 * This is used to fetch data that needs to be displayed on grid using default
	 * class.
	 * 
	 * @param start      starting row from where to fetch record
	 * @param recordSize end row of record
	 * @return {@link PageModel}
	 */
	PageModel getGridData(Integer start, Integer recordSize);

	/**
	 * This is used to fetch data that using hql query that needs to be displayed on
	 * grid.
	 * 
	 * @param query hibernate query object
	 * @param start starting row from where to fetch record
	 * @param end   recordSize row of record
	 * @return {@link PageModel}
	 */
	PageModel getResultUsingHQL(Query query, Integer startNo, Integer recordSize);

	/**
	 * This methods returns number of records base on given criteria.
	 * 
	 * @param criteria {@link Criteria}
	 * @return long number of records
	 */
	long getRawCount(Criteria criteria);

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	List<M> findAll();

	/**
	 * This method creates a proxy object as per hibernate's load method
	 * functionality which can be used during save & update method when just
	 * reference of entities are required.
	 * 
	 * @param id
	 * @param className
	 * @return
	 */
	M load(long id);

	/**
	 * This method creates a proxy objects as per hibernate's load method
	 * functionality which can be used during save & update method when just
	 * reference of entities are required.
	 * 
	 * @param id
	 * @param className
	 * @return
	 */
	List<M> loadBulk(List<Long> idList);
}
