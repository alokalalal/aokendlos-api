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
package com.intentlabs.endlos.systemspecification.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.systemspecification.model.SystemSpecificationModel;
import com.intentlabs.endlos.systemspecification.view.SystemSpecificationView;

import java.util.List;

/**
 * 
 * @author Milan.Gohil
 * @since 1/2/2024
 */
public interface SystemSpecificationService extends BaseService<SystemSpecificationModel> {

	String SYSTEM_SPECIFICATION_MODEL = "systemSpecificationModel";

	/**
	 * It is used to get single record base on given id using light class.
	 * 
	 * @param id
	 * @return model
	 */
	SystemSpecificationModel getLight(long id);

	/**
	 * It is used to get single record base on given id using extra light class.
	 * 
	 * @param id
	 * @return model
	 */
	SystemSpecificationModel getExtraLight(long id);

	/**
	 * This method will be used to load all data from table. This will be very
	 * useful to load small amount of data from table into map.
	 * 
	 * @return
	 */
	List<SystemSpecificationModel> findAll();

	/**
	 * This method is used to delete customer model.
	 * 
	 * @param systemSpecificationModel
	 */
	void hardDelete(SystemSpecificationModel systemSpecificationModel);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param systemSpecificationView
	 */
	List<SystemSpecificationModel> doExport(SystemSpecificationView systemSpecificationView, Integer orderType, Integer orderParam);
}