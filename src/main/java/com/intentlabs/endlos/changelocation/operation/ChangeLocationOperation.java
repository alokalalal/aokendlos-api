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
package com.intentlabs.endlos.changelocation.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.changelocation.view.ChangeLocationView;

/**
 *
 * @author Hemil Shah.
 * @version 1.0
 * @since 07/09/2022
 */

public interface ChangeLocationOperation extends BaseOperation<ChangeLocationView> {

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(ChangeLocationView changeLocationView, Integer orderType, Integer orderParam)
			throws EndlosAPIException;
	/**
	 * This method is used Approve and reject change-location request.
	 * 
	 * @return
	 */
	Response doApproveOrReject(Long id, Boolean isApprove) throws EndlosAPIException;

	/**
	 * This method is used Assign change-location request.
	 * 
	 * @return
	 */
	Response doAssignChangLocation(ChangeLocationView changeLocationView) throws EndlosAPIException;
}