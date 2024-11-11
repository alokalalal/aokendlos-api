///*******************************************************************************
// * Copyright -2019 @intentlabs
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License.  You may obtain a copy
// * of the License at
// * 
// *   http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
// * License for the specific language governing permissions and limitations under
// * the License.
// ******************************************************************************/
//package com.intentlabs.endlos.barcode.operation;
//
//import java.util.concurrent.ExecutionException;
//
//import com.intentlabs.common.exception.EndlosAPIException;
//import com.intentlabs.common.response.Response;
//import com.intentlabs.endlos.barcode.view.BarcodeView;
//
///**
// * @author Hemil Shah.
// * @version 1.0
// * @since 18/11/2021
// */
//public interface BarcodeOperation {
//	/**
//	 * This method is used to validate add request to add any entity into
//	 * system. Once it is been validate. it allow users to add entity data.
//	 *
//	 * @return
//	 * @throws EndlosAPIException
//	 */
//	Response doAdd() throws EndlosAPIException;
//
//	/**
//	 * This method is used to view entity.
//	 *
//	 * @param id
//	 * @return
//	 * @throws EndlosAPIException
//	 */
//	Response doView(Long machineId, Long barcode) throws EndlosAPIException;
//
//	/**
//	 * This method is used to edit entity.
//	 *
//	 * @param id
//	 * @return
//	 * @throws EndlosAPIException
//	 */
//	Response doEdit(Long machineId, Long barcode) throws EndlosAPIException;
//
//	/**
//	 * This method is used to update entity.
//	 *
//	 * @param id
//	 * @return
//	 * @throws ExecutionException
//	 * @throws InterruptedException
//	 * @throws EndlosAPIException
//	 */
//	Response doUpdate(BarcodeView barcodeView) throws EndlosAPIException;
//
//	/**
//	 * This method is used to search entity.
//	 *
//	 * @param id
//	 * @return
//	 * @throws ExecutionException
//	 * @throws InterruptedException
//	 * @throws EndlosAPIException
//	 */
//	Response doSearch(BarcodeView barcodeView, Integer start, Integer recordSize) throws EndlosAPIException;
//}