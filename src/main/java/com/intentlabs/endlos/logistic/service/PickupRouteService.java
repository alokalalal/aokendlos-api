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
package com.intentlabs.endlos.logistic.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.logistic.model.PickupRouteModel;
import com.intentlabs.endlos.logistic.view.PickupRouteView;

import java.util.List;

/**
 * 
 * @author Milan.Gohil
 * @since 11/12/2023
 */
public interface PickupRouteService extends BaseService<PickupRouteModel> {

	String PICKUPROUTE_MODEL = "pickupRouteModel";

	/**
	 * It is used to get single record base on given id using light class.
	 * 
	 * @param id
	 * @return model
	 */
	PickupRouteModel getLight(long id);

	/**
	 * It is used to get single record base on given id using extra light class.
	 * 
	 * @param id
	 * @return model
	 */
	PickupRouteModel getExtraLight(long id);

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	List<PickupRouteModel> findAll();

	/**
	 * This method is used to delete customer model.
	 * 
	 * @param pickupRouteModel
	 */
	void hardDelete(PickupRouteModel pickupRouteModel);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param pickupRouteView
	 */
	List<PickupRouteModel> doExport(PickupRouteView pickupRouteView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to get Pickup Route by name.
	 * 
	 * @param name
	 */
	PickupRouteModel getByName(String name, String currentPickupRouteName);
	PickupRouteModel getByNumber(Integer pickupRouteNo, Integer routeNo);
}