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
package com.intentlabs.endlos.changelocation.service;

import java.util.List;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.changelocation.model.ChangeLocationModel;
import com.intentlabs.endlos.changelocation.view.ChangeLocationView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 07/09/2022
 */
public interface ChangeLocationService extends BaseService<ChangeLocationModel> {

	String CHANGE_LOCATION_MODEL = "changeLocationModel";

	/**
	 * This method is used to export changeLocationList.
	 * 
	 * @param changeLocationView
	 */
	List<ChangeLocationModel> doExport(ChangeLocationView changeLocationView, Integer orderType, Integer orderParam);
}