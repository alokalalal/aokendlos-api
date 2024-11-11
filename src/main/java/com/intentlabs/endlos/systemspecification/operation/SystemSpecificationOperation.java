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
package com.intentlabs.endlos.systemspecification.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.systemspecification.view.SystemSpecificationView;

/**
 * @author Milan.Gohil
 * @since 1/2/2024
 */
public interface SystemSpecificationOperation extends BaseOperation<SystemSpecificationView> {

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(SystemSpecificationView systemSpecificationView, Integer orderType, Integer orderParam) throws EndlosAPIException;
}