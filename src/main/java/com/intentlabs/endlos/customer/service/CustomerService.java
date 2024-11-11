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
package com.intentlabs.endlos.customer.service;

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.view.CustomerView;

import java.util.List;

/**
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
public interface CustomerService extends BaseService<CustomerModel> {

	String CUSTOMER_MODEL = "customerModel";
	String SUPER_LIGHT_CUSTOMER_MODEL = "superLightCustomerModel";

	/**
	 * It is used to get single record base on given id using light class.
	 * 
	 * @param id
	 * @return model
	 */
	CustomerModel getLight(long id);

	/**
	 * It is used to get single record base on given id using extra light class.
	 * 
	 * @param id
	 * @return model
	 */
	CustomerModel getExtraLight(long id);

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	List<CustomerModel> findAll();

	/**
	 * This method is used to delete customer model.
	 * 
	 * @param customerModel
	 */
	void hardDelete(CustomerModel customerModel);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param transactionView
	 */
	List<CustomerModel> doExport(CustomerView customerView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to get customer by name.
	 * 
	 * @param transactionView
	 */
	CustomerModel getByName(String name);

	PageModel searchLight(CustomerView customerView, Integer start, Integer recordSize, Integer orderType,
						  Integer orderParam);
}