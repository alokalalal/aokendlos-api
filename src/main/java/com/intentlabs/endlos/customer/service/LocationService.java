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
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;

import java.util.List;

/**
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
public interface LocationService extends BaseService<LocationModel> {

	String LOCATION_MODEL = "locationModel";
	String LIGHT_LOCATION_MODEL = "lightLocationModel";
	String SUPER_LIGHT_LOCATION_MODEL = "superLightMachineModel";

	/**
	 * It is used to get single record base on given id using light class.
	 * 
	 * @param id
	 * @return model
	 */
	LocationModel getLight(long id);

	/**
	 * This method is used to get non activated location
	 * 
	 * @param id
	 * @return
	 */
	LocationModel nonActivatedCustomer(Long id);

	/**
	 * This method is used to get location by customer
	 * 
	 * @param id
	 * @return
	 */
	List<LocationModel> getByCustomer(Long id);

	/**
	 * This is used to search model data on given criteria using light class.
	 * 
	 * @param locationView
	 * @param start        starting row from where to fetch record
	 * @param recordSize   end row of record
	 * @param orderParam
	 * @param orderType
	 * @return {@link PageModel}
	 */
	PageModel searchLight(LocationView locationView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param transactionView
	 */
	List<LocationModel> doExport(LocationView locationView, Integer orderType, Integer orderParam);

	void hardDelete(LocationModel locationModel);

	List<LocationModel> getByPickedupRoute(DailyPickupAssigneeView dailyPickupAssigneeView, Integer start, Integer recordSize);

	List<LocationModel> getByLocationIds(List<LocationView> locationViews);
}