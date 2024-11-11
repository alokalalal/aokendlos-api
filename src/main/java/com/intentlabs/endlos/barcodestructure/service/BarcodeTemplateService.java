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
package com.intentlabs.endlos.barcodestructure.service;

import java.util.List;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
public interface BarcodeTemplateService extends BaseService<BarcodeTemplateModel> {

	String BARCODE_TEMPLATE_MODEL = "barcodeTemplateModel";

	/**
	 * This method is used to delete pump house from session.
	 * 
	 * @param pumpHouseModel
	 */
	void hardDelete(BarcodeTemplateModel barcodeTemplateModel);

	/**
	 * This method is used to export transactions .
	 * 
	 * @param transactionView
	 */
	List<BarcodeTemplateModel> doExport(BarcodeTemplateView barcodeTemplateView, Integer orderType, Integer orderParam);

	/**
	 * This method is used to get barcode template by name .
	 * 
	 * @param name
	 */
	BarcodeTemplateModel getByName(String name);
}