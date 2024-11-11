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
package com.intentlabs.endlos.customer.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.customer.view.CustomerView;

/**
 * @author Hemil.Shah
 * @since 18/11/2021
 */
public interface CustomerOperation extends BaseOperation<CustomerView> {

	/**
	 * This method is used get list of customer.
	 * 
	 * @return
	 */
	Response doDropdown();

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(CustomerView customerView, Integer orderType, Integer orderParam) throws EndlosAPIException;
}