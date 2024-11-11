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
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.CollectionReference;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.Query;
//import com.google.cloud.firestore.QuerySnapshot;
//import com.google.cloud.firestore.SetOptions;
//import com.intentlabs.endlos.firestore.config.FirestoreConfiguration;
//
///**
// * This is fire store service for storing all attachment.
// * 
// * @author Hemil.shah
// * @version 1.0
// * @since 26/04/2022
// */
//@Service(value = "firestoreService")
//public class FirestoreServiceImpl implements FirestoreService {
//	@Autowired
//	FirestoreConfiguration fireStoreConfiguration;
//
//	Firestore db;
//
//	@Override
//	public void doUpdateMachine(Map<String, Object> data, String machineId) {
//		this.db = fireStoreConfiguration.getFireStore();
//		this.db.collection(machineId).document(machineId).set(data, SetOptions.merge());
//	}
//
//	@Override
//	public void doSaveMachine(Map<String, Object> data, String machineId) {
//		this.db = fireStoreConfiguration.getFireStore();
//		this.db.collection(machineId).document(machineId).set(data);
//	}
//
//	@Override
//	public DocumentSnapshot getByBarcode(String machineId, String barcode) {
//		this.db = fireStoreConfiguration.getFireStore();
//		DocumentReference docRef = this.db.collection(machineId).document(machineId).collection("BARCODE")
//				.document(barcode);
//		ApiFuture<DocumentSnapshot> future = docRef.get();
//		DocumentSnapshot document = null;
//		try {
//			document = future.get();
//			return document;
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
//
//	@Override
//	public void updateRecord(Map<String, Object> update, String machineId, String barcode) {
//		this.db = fireStoreConfiguration.getFireStore();
//		db.collection(machineId).document(machineId).collection("BARCODE").document(barcode).set(update,
//				SetOptions.merge());
//	}
//
//	@Override
//	public List<DocumentSnapshot> search(String machineId, Integer recordSize) {
//		this.db = fireStoreConfiguration.getFireStore();
//		CollectionReference collectionReference = this.db.collection(machineId).document(machineId)
//				.collection("BARCODE");
//		Query query = collectionReference.limit(recordSize);
//		ApiFuture<QuerySnapshot> querySnapshot = query.get();
//		List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
//		try {
//			for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//				documentSnapshots.add(document);
//			}
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
//		return documentSnapshots;
//	}
//
//	@Override
//	public List<DocumentSnapshot> search(Integer recordSize) {
//		this.db = fireStoreConfiguration.getFireStore();
//		List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
//		for (CollectionReference cr : this.db.listCollections()) {
//			CollectionReference collectionReference = this.db.collection(cr.getId()).document(cr.getId())
//					.collection("BARCODE");
//			Query query = collectionReference.limit(recordSize);
//			ApiFuture<QuerySnapshot> querySnapshot = query.get();
//			try {
//				for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//					documentSnapshots.add(document);
//				}
//			} catch (InterruptedException | ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
//		return documentSnapshots;
//	}
//
//	@Override
//	public List<CollectionReference> getAllCollection() {
//		this.db = fireStoreConfiguration.getFireStore();
//		List<CollectionReference> collectionReferences = new ArrayList<>();
//		for (CollectionReference cr : this.db.listCollections()) {
//			collectionReferences.add(cr);
//		}
//		return collectionReferences;
//	}
//}