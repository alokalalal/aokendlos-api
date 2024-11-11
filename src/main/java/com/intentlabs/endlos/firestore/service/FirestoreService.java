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
//package com.intentlabs.endlos.firestore.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.google.cloud.firestore.CollectionReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//
///**
// * This is fire store service for storing all attachment.
// * 
// * @author Hemil.shah
// * @version 1.0
// * @since 26/04/2022
// */
//public interface FirestoreService {
//
//	/**
//	 * This method is used to save machine on firestore.
//	 * 
//	 * @param machineId
//	 * @param data
//	 * 
//	 */
//	void doSaveMachine(Map<String, Object> data, String machineId);
//
//	/**
//	 * This method is used to update machine on firestore.
//	 * 
//	 * @param machineId
//	 * @param data
//	 * 
//	 */
//	void doUpdateMachine(Map<String, Object> data, String machineId);
//
//	/**
//	 * This method is used to get barcode document by machineId.
//	 * 
//	 * @param machineId
//	 * @param barcode
//	 * 
//	 */
//	DocumentSnapshot getByBarcode(String machineId, String barcode);
//
//	/**
//	 * This method is used to update barcode record.
//	 * 
//	 * @param machineId
//	 * @param barcode
//	 */
//	void updateRecord(Map<String, Object> update, String machineId, String barcode);
//
//	/**
//	 * This method is used to search barcode records by machineId.
//	 * 
//	 * @param machineId
//	 * @param barcode
//	 */
//	List<DocumentSnapshot> search(String machineId, Integer recordSize);
//
//	List<DocumentSnapshot> search(Integer recordSize);
//
//	List<CollectionReference> getAllCollection();
//}