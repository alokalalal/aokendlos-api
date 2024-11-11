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
package com.intentlabs.endlos.machine.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.machine.view.ReportView;
import com.intentlabs.endlos.machine.view.TransactionView;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Hemil Shah.
 * @version 1.0
 * @since 08/07/2022
 */

public interface ReportOperation extends BaseOperation<ReportView> {

	/**
	 * This method is used export transactions.
	 * 
	 * @return
	 */
	Response doExport(ReportView reportView, Integer orderType, Integer orderParam) throws EndlosAPIException;

	/**
	 * This method is used to calculate all bin count.
	 * 
	 * @return
	 */
	Response doCalculateBinCount(ReportView reportView);

	CompletableFuture<Response> doExportAsync(ReportView reportView, Integer orderType, Integer orderParam);

}