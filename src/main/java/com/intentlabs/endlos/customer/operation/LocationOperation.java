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
import com.intentlabs.endlos.customer.view.LocationView;

/**
 * @author Hemil.Shah
 * @since 04/10/2021
 */
public interface LocationOperation extends BaseOperation<LocationView> {

	/**
	 * This method is used get list of location by customer.
	 * 
	 * @return
	 */
	Response doDropDownBycustomer(LocationView locationView);

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(LocationView locationView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	/**
	 * This method is used get list of customer.
	 * 
	 * @return
	 */
	Response doDropdown();
}