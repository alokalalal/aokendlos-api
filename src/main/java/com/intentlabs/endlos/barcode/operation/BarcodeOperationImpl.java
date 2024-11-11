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
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.google.cloud.firestore.CollectionReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.intentlabs.common.enums.ResponseCode;
//import com.intentlabs.common.exception.EndlosAPIException;
//import com.intentlabs.common.response.CommonResponse;
//import com.intentlabs.common.response.PageResultResponse;
//import com.intentlabs.common.response.Response;
//import com.intentlabs.common.response.ViewResponse;
//import com.intentlabs.common.util.DateUtility;
//import com.intentlabs.endlos.barcode.view.BarcodeView;
//import com.intentlabs.endlos.firestore.service.FirestoreService;
//import com.intentlabs.endlos.machine.model.MachineModel;
//import com.intentlabs.endlos.machine.service.MachineService;
//import com.intentlabs.endlos.machine.view.MachineView;
//
///**
// * @author Hemil Shah.
// * @version 1.0ka
// * @since 18/11/2021
// */
//@Component(value = "barcodeOperation")
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
//public class BarcodeOperationImpl implements BarcodeOperation {
//
//	@Autowired
//	FirestoreService firestoreService;
//
//	@Autowired
//	MachineService machineService;
//
//	@Override
//	public Response doAdd() throws EndlosAPIException {
//		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
//	}
//
//	@Override
//	public Response doView(Long machineId, Long barcode) throws EndlosAPIException {
//		MachineModel machineModel = machineService.get(machineId);
//		if (machineModel == null) {
//			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
//		}
//		DocumentSnapshot document = firestoreService.getByBarcode(machineModel.getMachineId(), barcode.toString());
//		if (!document.exists()) {
//			throw new EndlosAPIException(ResponseCode.DOES_NOT_EXIST.getCode(),
//					"Data" + ResponseCode.DOES_NOT_EXIST.getMessage());
//		}
//		BarcodeView barcodeView = fromModel(document, machineModel);
//		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
//				barcodeView);
//	}
//
//	private BarcodeView fromModel(DocumentSnapshot document, MachineModel machineModel) {
//		BarcodeView barcodeView = new BarcodeView();
//		barcodeView.setBarcode(document.getString("txtbarcode"));
//		barcodeView.setDescription(document.getString("txtdescription"));
//		barcodeView.setDataAcquisition(document.getString("txtdataacquisition"));
//		barcodeView.setIteamRedeemValue(document.getString("txtitemredeemvalue"));
//		barcodeView.setVolumn(document.getString("txtvolumn"));
//		barcodeView.setDateCreate(document.getLong("datecreate"));
//		barcodeView.setDateCreate(document.getLong("dateupdate"));
//		MachineView machineView = new MachineView();
//		machineView.setId(machineModel.getId());
//		machineView.setMachineId(machineModel.getMachineId());
//		barcodeView.setMachineView(machineView);
//		return barcodeView;
//	}
//
//	@Override
//	public Response doEdit(Long machineId, Long barcode) throws EndlosAPIException {
//		return doView(machineId, barcode);
//	}
//
//	@Override
//	public Response doUpdate(BarcodeView barcodeView) throws EndlosAPIException {
//		MachineModel machineModel = machineService.get(barcodeView.getMachineView().getId());
//		if (machineModel == null) {
//			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
//		}
//		DocumentSnapshot document = firestoreService.getByBarcode(machineModel.getMachineId(),
//				barcodeView.getBarcode());
//		if (!document.exists()) {
//			throw new EndlosAPIException(ResponseCode.DOES_NOT_EXIST.getCode(),
//					"Data" + ResponseCode.DOES_NOT_EXIST.getMessage());
//		}
//		Map<String, Object> update = toModel(barcodeView);
//		firestoreService.updateRecord(update, machineModel.getMachineId(), barcodeView.getBarcode());
//		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
//				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
//	}
//
//	private Map<String, Object> toModel(BarcodeView barcodeView) {
//		Map<String, Object> update = new HashMap<>();
//		update.put("txtbarcode", barcodeView.getBarcode());
//		update.put("txtdescription", barcodeView.getDescription());
//		update.put("txtdataacquisition", barcodeView.getDataAcquisition());
//		update.put("txtitemredeemvalue", barcodeView.getIteamRedeemValue());
//		update.put("txtvolumn", barcodeView.getVolumn());
//		update.put("datecreate", barcodeView.getDateCreate());
//		update.put("dateupdate", DateUtility.getCurrentEpoch());
//		return update;
//	}
//
//	@Override
//	public Response doSearch(BarcodeView barcodeView, Integer start, Integer recordSize) throws EndlosAPIException {
//		List<CollectionReference> collectionReferences = firestoreService.getAllCollection();
//		List<BarcodeView> barcodeViewss = new ArrayList<>();
//
//		for (CollectionReference cr : collectionReferences) {
//			List<DocumentSnapshot> documentSnapshots = firestoreService.search(cr.getId(), recordSize);
//			if (documentSnapshots != null || !documentSnapshots.isEmpty()) {
//				List<BarcodeView> barcodeViews = new ArrayList<>();
//				for (DocumentSnapshot document : documentSnapshots) {
//					MachineModel machineModel = machineService.getByMachineId(cr.getId());
//					if (machineModel != null) {
//						BarcodeView resView = fromModel(document, machineModel);
//						barcodeViews.add(resView);
//					}
//				}
//				barcodeViewss.addAll(barcodeViews);
//			}
//
//		}
//		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
//				barcodeViewss.size(), barcodeViewss);
//	}
//}