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
package com.intentlabs.common.user.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.view.RoleView;

/**
 * @author Nirav.Shah
 * @since 08/02/2018
 */
public interface RoleOperation extends BaseOperation<RoleView> {

	/**
	 * This method is used get list of role by user type.
	 * 
	 * @return
	 * @throws AxisIotAPIException
	 */
	Response doDropdown(boolean isCustomer) throws EndlosAPIException;
}