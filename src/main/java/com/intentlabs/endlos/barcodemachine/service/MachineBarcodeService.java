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
package com.intentlabs.endlos.barcodemachine.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;

/**
 * This is service for storing all attachment.
 * 
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
public interface MachineBarcodeService extends BaseService<MachineBarcodeFileModel> {

	String MACHINE_BARCODE_FILE_MODEL = "machineBarcodeFileModel";

	MachineBarcodeFileModel getLastmachineBarcodeFile();

	void delete(MachineBarcodeFileModel machineBarcodeFileModel);

	MachineBarcodeFileModel getByMachineBarcodeName(String barcodeFileName);

}