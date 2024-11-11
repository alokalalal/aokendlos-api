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
package com.intentlabs.endlos.machine.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.machine.model.DataTableModel;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil.shah
 * @version 1.0
 * @since 05/10/2021
 */
public interface DataTableService extends BaseService<DataTableModel> {

	String DATA_TABLE_MODEL = "dataTableModel";

	/**
	 * This method is used to get DataTableModel By barcode .
	 * 
	 * @param deviceId
	 */
	DataTableModel getByBarcode(String barcode);
}