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
import com.intentlabs.endlos.logistic.model.DailyPickupLogModel;

import java.util.List;

/**
 * 
 * @author Milan.Gohil
 * @since 11/12/2023
 */
public interface DailyPickupLogService extends BaseService<DailyPickupLogModel> {

	String DAILYPICKUPLOGMODEL = "dailyPickupLogModel";

	DailyPickupLogModel getByPickupRoute(Long id, Long startEpoch, Long endEpoch);
	List<DailyPickupLogModel> getAllPickupRoute(String fullTextSearch, Long startEpoch, Long endEpoch);
}