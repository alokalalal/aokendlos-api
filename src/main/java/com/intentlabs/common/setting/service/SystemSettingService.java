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
package com.intentlabs.common.setting.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;

/**
 * 
 * @author Nirav
 * @since 24/11/2018
 */
public interface SystemSettingService extends BaseService<SystemSettingModel> {
	String SYSTEM_SETTING_MODEL = "systemSettingModel";

	/**
	 * This method used to fetch setting value base on key.
	 * 
	 * @param key
	 * @return
	 */
	SystemSettingModel get(String key);
}