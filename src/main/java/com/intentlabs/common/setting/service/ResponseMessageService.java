/*******************************************************************************
 * Copyright -2018 @Intentlabs
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
import com.intentlabs.common.setting.model.ResponseMessageModel;

/**
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
public interface ResponseMessageService extends BaseService<ResponseMessageModel> {
	String RESPONSE_MESSAGE_MODEL = "responseMessageModel";

	/**
	 * This method is used to get language by code.
	 * 
	 * @param languageEnum
	 * @param responseCode
	 * @return
	 */
	ResponseMessageModel get(int code, String locale);
}