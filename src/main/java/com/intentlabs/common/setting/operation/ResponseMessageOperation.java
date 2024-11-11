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
package com.intentlabs.common.setting.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.setting.view.ResponseMessageView;

/**
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
public interface ResponseMessageOperation extends BaseOperation<ResponseMessageView> {

	/**
	 * This method is used to get data for view.
	 * 
	 * @param code
	 * @param locale
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doViewEdit(Integer code, String locale) throws EndlosAPIException;
}