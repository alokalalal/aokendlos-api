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
package com.intentlabs.endlos.barcodestructure.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;

/**
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
public interface BarcodeTemplateOperation extends BaseOperation<BarcodeTemplateView> {

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
	Response doExport(BarcodeTemplateView barcodeTemplateView, Integer orderType, Integer orderParam)
			throws EndlosAPIException;

	/**
	 * This method is used to assign barcode template to machine.
	 * 
	 * @param barcodeTemplateView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doAssignMachine(BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException;
}