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
package com.intentlabs.endlos.barcodemachine.operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.lang.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeItemModel;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeItemService;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeService;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeItemView;

/**
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
@Component(value = "machineBarcodeOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MachineBarcodeOperationImpl extends AbstractOperation<MachineBarcodeFileModel, MachineBarcodeFileView>
		implements MachineBarcodeOperation {
	@Autowired
	MachineBarcodeService machineBarcodeService;

	@Autowired
	MachineBarcodeItemService machineBarcodeItemService;

	@Autowired
	FileOperation fileOperation;
	@Autowired
	private FileService fileService;
	@Autowired
	MachineBarcodeItemOperation machineBarcodeItemOperation;

	@Override
	public BaseService<MachineBarcodeFileModel> getService() {
		return machineBarcodeService;
	}

	@Override
	public Response doSave(MachineBarcodeFileView machineBarcodeFileView) throws EndlosAPIException {
		MachineBarcodeFileModel machineBarcodeFileModel = toModel(getNewModel(), machineBarcodeFileView);

		/*
		 * for (MachineBarcodeItemModel machineBarcodeItemModel :
		 * machineBarcodeFileModel.getMachineBarcodeItemModels()) {
		 * machineBarcodeItemModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
		 * }
		 */
		machineBarcodeService.create(machineBarcodeFileModel);

		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public MachineBarcodeFileModel toModel(MachineBarcodeFileModel machineBarcodeFileModel,
			MachineBarcodeFileView machineBarcodeFileView) throws EndlosAPIException {
		if (machineBarcodeFileView.getId() != null)
			machineBarcodeFileModel.setId(machineBarcodeFileView.getId());

		machineBarcodeFileModel.setNoOfMachineAssigned(machineBarcodeFileView.getNoOfMachineAssigned());
		machineBarcodeFileModel.setTotalNoOfBarcodes(machineBarcodeFileView.getTotalNoOfBarcodes());
		machineBarcodeFileModel.setPlastic(machineBarcodeFileView.getPlastic());
		machineBarcodeFileModel.setGlass(machineBarcodeFileView.getGlass());
		machineBarcodeFileModel.setAlluminium(machineBarcodeFileView.getAlluminium());
		machineBarcodeFileModel.setFileStatus(machineBarcodeFileView.getFileStatus());

		/*
		 * FileModel fileModel; if (machineBarcodeFileView.getFileView().getId() ==
		 * null) fileModel = fileOperation.toModel(new FileModel(),
		 * machineBarcodeFileView.getFileView().getName(),
		 * machineBarcodeFileView.getFileView().getOriginalName(),
		 * ModuleEnum.MACHINE_BARCODE.getId(), false, null, "", null); else { fileModel
		 * = new FileModel();
		 * fileModel.setId(machineBarcodeFileView.getFileView().getId()); fileModel =
		 * fileOperation.toModel(fileModel,
		 * machineBarcodeFileView.getFileView().getName(),
		 * machineBarcodeFileView.getFileView().getOriginalName(),
		 * ModuleEnum.MACHINE_BARCODE.getId(), false, null, "", null); }
		 */

		/*
		 * machineBarcodeFileModel.setFileModel(fileModel);
		 * 
		 * if (machineBarcodeFileView.getMachineBarcodeItemViews() != null) {
		 * List<MachineBarcodeItemModel> machineBarcodeItemModelSet = new ArrayList<>();
		 * for (MachineBarcodeItemView view :
		 * machineBarcodeFileView.getMachineBarcodeItemViews()) {
		 * MachineBarcodeItemModel modal = new MachineBarcodeItemModel();
		 * modal.setBarcodeName(view.getBarcodeName());
		 * modal.setMaterialType(view.getMaterialType());
		 * modal.setMaterial(view.getMaterial());
		 * modal.setItemVolume(view.getItemVolume());
		 * modal.setItemWeight(view.getItemWeight());
		 * modal.setItemValue(view.getItemValue());
		 * machineBarcodeItemModelSet.add(modal); }
		 * machineBarcodeFileModel.setMachineBarcodeItemModels(
		 * machineBarcodeItemModelSet); }
		 */
		return machineBarcodeFileModel;
	}

	@Override
	protected MachineBarcodeFileModel getNewModel() {
		return new MachineBarcodeFileModel();
	}

	@Override
	public Response doSearch(MachineBarcodeFileView machineBarcodeFileView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {

		PageModel pageModel = machineBarcodeService.search(machineBarcodeFileView, start, recordSize, orderType,
				orderParam);

		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MachineBarcodeFileModel>) pageModel.getList()));
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {

		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(id);
		if (machineBarcodeFileModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		MachineBarcodeFileView machineBarcodeFileView = fromModel(machineBarcodeFileModel);
		List<MachineBarcodeItemModel> machineBarcodeItemModels = machineBarcodeItemService
				.getByMachineBarcodeFileViewId(machineBarcodeFileModel.getId());

		if (machineBarcodeItemModels != null) {
			List<MachineBarcodeItemView> machineBarcodeItemViews = machineBarcodeItemModels.parallelStream()
					.map(machineBarcodeItemModel -> {
						MachineBarcodeItemView machineBarcodeItemView = new MachineBarcodeItemView();
						machineBarcodeItemView.setBarcodeName(machineBarcodeItemModel.getBarcodeName());
						machineBarcodeItemView.setMaterialType(machineBarcodeItemModel.getMaterialType());
						// machineBarcodeItemView.setMaterial(machineBarcodeItemModel.getMaterial());
						machineBarcodeItemView.setItemVolume(machineBarcodeItemModel.getItemVolume());
						machineBarcodeItemView.setItemWeight(machineBarcodeItemModel.getItemWeight());
						machineBarcodeItemView.setItemValue(machineBarcodeItemModel.getItemValue());
						return machineBarcodeItemView;
					}).collect(Collectors.toList());

			machineBarcodeFileView.setMachineBarcodeItemViews(machineBarcodeItemViews);
		}

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineBarcodeFileView);

	}

	@Override
	public Response doDropdown() {
		List<MachineBarcodeFileModel> machineBarcodeFileModels = machineBarcodeService.findAll();
		if (machineBarcodeFileModels == null || machineBarcodeFileModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineBarcodeFileView> machineBarcodeFileViews = new ArrayList<>();
		for (MachineBarcodeFileModel machineBarcodeFileModel : machineBarcodeFileModels) {
			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setId(machineBarcodeFileModel.getId());
			/*
			 * if (machineBarcodeFileModel.getFileModel() != null) {
			 * machineBarcodeFileView.setFileView(fileOperation.fromModel(
			 * machineBarcodeFileModel.getFileModel())); }
			 */
			if (machineBarcodeFileModel.getBarcodeFileName() != null) {
				machineBarcodeFileView.setBarcodeFileName(machineBarcodeFileModel.getBarcodeFileName());
			}
			machineBarcodeFileViews.add(machineBarcodeFileView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineBarcodeFileViews.size(), machineBarcodeFileViews);
	}

	/*
	 * @Override // Upload barcode using Asynchronous Processing public Response
	 * doFileUpload(MultipartFile multipartFile, String barcodeFileName) throws
	 * ExecutionException, InterruptedException, EndlosAPIException {
	 * 
	 * MachineBarcodeFileModel machineBarcodeFileModel =
	 * machineBarcodeService.getByMachineBarcodeName(barcodeFileName); if
	 * (machineBarcodeFileModel != null) { throw new
	 * EndlosAPIException(ResponseCode.MACHINE_BARCODE_ALREADY_EXIST.getCode(),
	 * ResponseCode.MACHINE_BARCODE_ALREADY_EXIST.getMessage()); } //Asynchronous
	 * Processing CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
	 * { try { processUploadFileData(multipartFile, barcodeFileName);
	 * 
	 * } catch (EndlosAPIException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } });
	 * 
	 * // Block and wait for the asynchronous processing to complete future.get();
	 * 
	 * return
	 * CommonResponse.create(ResponseCode.BARCODE_SAVE_SUCCESSFULLY.getCode(),
	 * ResponseCode.BARCODE_SAVE_SUCCESSFULLY.getMessage()); }
	 */

	/*
	 * private void processUploadFileData(MultipartFile multipartFile, String
	 * barcodeFileName) throws EndlosAPIException, IOException {
	 * 
	 * long totalPlastic = 0; long totalGlass = 0; long totalAlluminium = 0; long
	 * allSavedBottleCounter = 0L;
	 * 
	 * BufferedReader reader = null; try { Set<String> expectedHeaders = new
	 * HashSet<>(Arrays.asList("BARCODE", "MATERIAL_TYPE", "ITEM_VOLUME",
	 * "ITEM_WEIGHT", "ITEM_VALUE")); reader = new BufferedReader(new
	 * InputStreamReader(multipartFile.getInputStream())); String headerLine =
	 * reader.readLine(); if (headerLine == null || headerLine.isEmpty()) { throw
	 * new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
	 * ResponseCode.INVALID_FILE_FORMAT.getMessage()); } String[] headerNames =
	 * headerLine.split(","); for (String expectedHeader : expectedHeaders) { if
	 * (!Arrays.asList(headerNames).contains(expectedHeader)) { throw new
	 * EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
	 * ResponseCode.INVALID_FILE_FORMAT.getMessage()); } } } catch (IOException
	 * ioException) { throw new
	 * EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
	 * ResponseCode.INVALID_FILE_FORMAT.getMessage()); } try {
	 * MachineBarcodeFileModel machineBarcodeFileModel = new
	 * MachineBarcodeFileModel();
	 * machineBarcodeFileModel.setNoOfMachineAssigned(0L);
	 * machineBarcodeFileModel.setTotalNoOfBarcodes(0L);
	 * machineBarcodeFileModel.setPlastic(0L); machineBarcodeFileModel.setGlass(0L);
	 * machineBarcodeFileModel.setAlluminium(0L);
	 * machineBarcodeFileModel.setBarcodeFileName(barcodeFileName);
	 * machineBarcodeFileModel.setFileStatus("Not Uploaded");
	 * machineBarcodeService.create(machineBarcodeFileModel);
	 * 
	 * machineBarcodeFileModel = machineBarcodeService.getLastmachineBarcodeFile();
	 * 
	 * 
	 * String line; while ((line = reader.readLine()) != null) { String[] data =
	 * line.split(",");
	 * 
	 * if (data[0] != null || data[1] != null || !data[0].isEmpty() ||
	 * !data[1].isEmpty()) {
	 * 
	 * if (data[1].equals("1") || data[1].equals("2") || data[1].equals("3")) {
	 * 
	 * List<MachineBarcodeItemModel> machineBarcodeItemModels =
	 * machineBarcodeItemService.setbBarcodeNameAndBarcodeFileId(data[1],
	 * machineBarcodeFileModel.getId());
	 * 
	 * if (machineBarcodeItemModels.isEmpty()) {
	 * 
	 * if (NumberUtils.isNumber(data[2]) && NumberUtils.isNumber(data[3]) &&
	 * NumberUtils.isNumber(data[4])) {
	 * 
	 * if (data[1].equals("1"))//Glass totalGlass++; if
	 * (data[1].equals("2"))//Plastic totalPlastic++; if
	 * (data[1].equals("3"))//Aluminum totalAlluminium++;
	 * 
	 * MachineBarcodeItemModel machineBarcodeItemModel = new
	 * MachineBarcodeItemModel(); BigDecimal itemVolume = new
	 * BigDecimal(data[2].trim()); BigDecimal itemWeight = new
	 * BigDecimal(data[3].trim()); BigDecimal itemValue = new
	 * BigDecimal(data[4].trim());
	 * 
	 * machineBarcodeItemModel.setBarcodeName(data[0]);
	 * machineBarcodeItemModel.setMaterialType(Integer.parseInt(data[1]));
	 * machineBarcodeItemModel.setItemVolume(itemVolume);
	 * machineBarcodeItemModel.setItemWeight(itemWeight);
	 * machineBarcodeItemModel.setItemValue(itemValue);
	 * machineBarcodeItemModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
	 * machineBarcodeItemService.create(machineBarcodeItemModel);
	 * allSavedBottleCounter++; } } } } else { throw new
	 * EndlosAPIException(ResponseCode.INVALID_UPLOADED_DATA.getCode(),
	 * ResponseCode.INVALID_UPLOADED_DATA.getMessage()); } } reader.close(); if
	 * (allSavedBottleCounter > 0) {
	 * machineBarcodeFileModel.setNoOfMachineAssigned(0L);
	 * machineBarcodeFileModel.setTotalNoOfBarcodes(allSavedBottleCounter);
	 * machineBarcodeFileModel.setPlastic(totalPlastic);
	 * machineBarcodeFileModel.setGlass(totalGlass);
	 * machineBarcodeFileModel.setAlluminium(totalAlluminium);
	 * machineBarcodeFileModel.setBarcodeFileName(barcodeFileName);
	 * machineBarcodeFileModel.setFileStatus("Uploaded");
	 * machineBarcodeService.update(machineBarcodeFileModel); } } catch (Exception
	 * exception) { throw new
	 * EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
	 * ResponseCode.INTERNAL_SERVER_ERROR.getMessage()); } }
	 */

	@Override
	public Response doUpdateFile(Long id, MultipartFile multipartFile) throws ExecutionException, InterruptedException {

		// Asynchronous Processing
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			/*
			 * try { processUpdateFileData(id, multipartFile);
			 * 
			 * } catch (EndlosAPIException e) { e.printStackTrace(); }
			 */
		});

		// Block and wait for the asynchronous processing to complete
		future.get();

		return CommonResponse.create(ResponseCode.BARCODE_SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.BARCODE_SAVE_SUCCESSFULLY.getMessage());
	}

	private void processUpdateFileData(Long id, MultipartFile multipartFile) throws EndlosAPIException {

		FileModel fileModel = null;
		BufferedReader reader = null;
		try {
			// Set<String> expectedHeaders = new HashSet<>(Arrays.asList("ITEM_ID",
			// "BARCODE", "MATERIAL_TYPE", "MATERIAL", "ITEM_VOLUME", "ITEM_WEIGHT",
			// "ITEM_VALUE"));
			Set<String> expectedHeaders = new HashSet<>(
					Arrays.asList("BARCODE", "MATERIAL_TYPE", "ITEM_VOLUME", "ITEM_WEIGHT", "ITEM_VALUE"));
			reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
			String headerLine = reader.readLine();
			if (headerLine == null || headerLine.isEmpty()) {
				throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
						ResponseCode.INVALID_FILE_FORMAT.getMessage());
			}
			String[] headerNames = headerLine.split(",");
			for (String expectedHeader : expectedHeaders) {
				if (!Arrays.asList(headerNames).contains(expectedHeader)) {
					throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
							ResponseCode.INVALID_FILE_FORMAT.getMessage());
				}
			}
		} catch (IOException | EndlosAPIException ioException) {
			throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
					ResponseCode.INVALID_FILE_FORMAT.getMessage());
		}

		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(id);
		long totalNoOfbarcodes = machineBarcodeFileModel.getTotalNoOfBarcodes();
		long totalPlastic = machineBarcodeFileModel.getPlastic();
		long totalGlass = machineBarcodeFileModel.getGlass();
		long totalAlluminium = machineBarcodeFileModel.getAlluminium();
		long allSavedBottleCounter = machineBarcodeFileModel.getTotalNoOfBarcodes();

		Storage storage = StorageOptions.getDefaultInstance().getService();

		// FileWriter csvWriter = null; //local storage
		try {
			/*
			 * //local storage File csvFile = new
			 * File(machineBarcodeFileModel.getFileModel().getPath()); try { csvWriter = new
			 * FileWriter(csvFile, true); } catch (IOException e) { throw new
			 * RuntimeException(e); }
			 */
			String line;
			List<String> listData = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0] == null || data[1] == null || data[0].isEmpty() || data[1].isEmpty()) {
					machineBarcodeFileModel.setFileStatus("Partially Updated");
					machineBarcodeFileModel.setId(machineBarcodeFileModel.getId());
					machineBarcodeFileModel.setNoOfMachineAssigned(0L);
					machineBarcodeFileModel.setTotalNoOfBarcodes(allSavedBottleCounter);
					machineBarcodeFileModel.setPlastic(totalPlastic);
					machineBarcodeFileModel.setGlass(totalGlass);
					machineBarcodeFileModel.setAlluminium(totalAlluminium);
					machineBarcodeService.update(machineBarcodeFileModel);
					throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
							ResponseCode.INVALID_FILE_FORMAT.getMessage());
				} else {
					List<MachineBarcodeItemModel> machineBarcodeItemModels = machineBarcodeItemService
							.setbBarcodeNameAndBarcodeFileId(data[0], machineBarcodeFileModel.getId());
					if (machineBarcodeItemModels.isEmpty()) { // if check the duplicate entry in the database
						if (data[1].equals("1") || data[1].equals("2") || data[1].equals("3")) {
							if (data[2] != null && NumberUtils.isNumber(data[2]) && NumberUtils.isNumber(data[3])
									&& NumberUtils.isNumber(data[4])) { // if number is unique
								totalNoOfbarcodes++;
								if (data[1].equals("1")) // Glass
									totalGlass++;
								if (data[1].equals("2")) // Plastics
									totalPlastic++;
								if (data[1].equals("3")) // Aluminum
									totalAlluminium++;

								BigDecimal itemVolume = new BigDecimal(data[2].trim());
								BigDecimal itemWeight = new BigDecimal(data[3].trim());
								BigDecimal itemValue = new BigDecimal(data[4].trim());

								MachineBarcodeItemModel machineBarcodeItemModel = new MachineBarcodeItemModel();
								machineBarcodeItemModel.setBarcodeName(data[0]);
								machineBarcodeItemModel.setMaterialType(Integer.parseInt(data[1]));
								// machineBarcodeItemModel.setMaterial(data[3]);
								machineBarcodeItemModel.setItemVolume(itemVolume);
								machineBarcodeItemModel.setItemWeight(itemWeight);
								machineBarcodeItemModel.setItemValue(itemValue);
								machineBarcodeItemModel.setMachineBarcodeFileModel(machineBarcodeFileModel);

								// PrintWriter writer = new PrintWriter(csvWriter); //local storage
								// writer.println(String.format("%s,%d,%s,%s,%s",
								// machineBarcodeItemModel.getBarcodeName(),
								// machineBarcodeItemModel.getMaterialType(),
								// machineBarcodeItemModel.getItemVolume().toString(),
								// machineBarcodeItemModel.getItemWeight().toString(),
								// machineBarcodeItemModel.getItemValue().toString())); \\local storage
								listData.add(machineBarcodeItemModel.getBarcodeName() + ","
										+ machineBarcodeItemModel.getMaterialType() + ","
										+ machineBarcodeItemModel.getItemVolume().toString() + ","
										+ machineBarcodeItemModel.getItemWeight().toString() + ","
										+ machineBarcodeItemModel.getItemValue().toString());
								machineBarcodeItemService.create(machineBarcodeItemModel);
								allSavedBottleCounter++;
							} else {
								machineBarcodeFileModel.setFileStatus("Partially Uploaded");
								break;
							}
						}
					}
				}
			}
			reader.close();
			// appendDataToCsvFile(storage, "machine_barcode_",
			// machineBarcodeFileModel.getFileModel().getName(), listData);
			// csvWriter.close(); //local storage
			machineBarcodeFileModel.setFileStatus("Uploaded");
			machineBarcodeFileModel.setId(machineBarcodeFileModel.getId());
			machineBarcodeFileModel.setNoOfMachineAssigned(0L);
			machineBarcodeFileModel.setTotalNoOfBarcodes(allSavedBottleCounter);
			machineBarcodeFileModel.setPlastic(totalPlastic);
			machineBarcodeFileModel.setGlass(totalGlass);
			machineBarcodeFileModel.setAlluminium(totalAlluminium);
			machineBarcodeService.update(machineBarcodeFileModel);

		} catch (Exception ioException) {
			machineBarcodeFileModel.setFileStatus("Partially Uploaded");
			machineBarcodeFileModel.setId(machineBarcodeFileModel.getId());
			machineBarcodeFileModel.setNoOfMachineAssigned(0L);
			machineBarcodeFileModel.setTotalNoOfBarcodes(allSavedBottleCounter);
			machineBarcodeFileModel.setPlastic(totalPlastic);
			machineBarcodeFileModel.setGlass(totalGlass);
			machineBarcodeFileModel.setAlluminium(totalAlluminium);
			machineBarcodeService.update(machineBarcodeFileModel);

		} finally {
			// Local storage
			/*
			 * try { if (csvWriter != null) { csvWriter.close(); } } catch (IOException e) {
			 * }
			 */
		}

	}

	private static void appendDataToCsvFile(Storage storage, String bucketName, String fileName, List<String> data) {

		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}

		BlobId blobId = BlobId.of(bucketName + activeProfile, fileName);

		// Get the existing content of the CSV file
		Blob blob = storage.get(blobId);
		byte[] existingContent = blob.getContent();

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			byteArrayOutputStream.write(existingContent);

			// Use PrintWriter to append new data to the CSV file without including a header
			try (PrintWriter writer = new PrintWriter(byteArrayOutputStream, true)) {
				for (String line : data) {
					writer.println(line);
				}
			}

			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

			storage.create(blobInfo, byteArrayOutputStream.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeDataToCsvFile(Storage storage, String bucketName, String fileName, List<String> data) {

		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}

		BlobId blobId = BlobId.of(bucketName + activeProfile, fileName);

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				PrintWriter writer = new PrintWriter(byteArrayOutputStream, true)) {

			for (String line : data) {
				writer.println(line);
			}

			// Create the BlobInfo with the BlobId
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

			// Create the CSV file in the bucket
			storage.create(blobInfo, byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * // Read data from CSV file readCsvFile(storage, bucketName, fileName);
		 */
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	protected MachineBarcodeFileModel loadModel(MachineBarcodeFileView machineBarcodeFileView) {
		return machineBarcodeService.get(machineBarcodeFileView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(id);

		if (machineBarcodeFileModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		machineBarcodeItemService.delete(machineBarcodeFileModel.getId());

		/*
		 * FileModel fileModel =
		 * fileOperation.get(machineBarcodeFileModel.getFileModel().getFileId());
		 *//*
			 * //local storage File f = new File(fileModel.getPath()); f.delete();
			 *//*
				 * 
				 * String activeProfile = System.getProperty("spring.profiles.active"); if
				 * (activeProfile == null) { activeProfile = "dev"; }
				 * 
				 * Storage storage = StorageOptions.getDefaultInstance().getService(); BlobId
				 * blobId = BlobId.of("machine_barcode_" + activeProfile, fileModel.getName());
				 * 
				 * // Delete the blob (file) from the bucket boolean deleted =
				 * storage.delete(blobId);
				 * 
				 * if (deleted) { System.out.println("CSV file deleted successfully: " +
				 * blobId); } else { System.out.println("Failed to delete CSV file: " + blobId);
				 * }
				 * 
				 * fileService.delete(fileModel);
				 */
		machineBarcodeService.delete(machineBarcodeFileModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public MachineBarcodeFileView fromModel(MachineBarcodeFileModel machineBarcodeFileModel) {
		MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
		machineBarcodeFileView.setId(machineBarcodeFileModel.getId());
		machineBarcodeFileView.setNoOfMachineAssigned(machineBarcodeFileModel.getNoOfMachineAssigned());
		machineBarcodeFileView.setTotalNoOfBarcodes(machineBarcodeFileModel.getTotalNoOfBarcodes());
		machineBarcodeFileView.setPlastic(machineBarcodeFileModel.getPlastic());
		machineBarcodeFileView.setGlass(machineBarcodeFileModel.getGlass());
		machineBarcodeFileView.setAlluminium(machineBarcodeFileModel.getAlluminium());
		machineBarcodeFileView.setFileStatus(machineBarcodeFileModel.getFileStatus());
		machineBarcodeFileView.setBarcodeFileName(machineBarcodeFileModel.getBarcodeFileName());

		/*
		 * if (machineBarcodeFileModel.getFileModel() != null) { FileView fileView = new
		 * FileView(machineBarcodeFileModel.getFileModel().getFileId(),
		 * machineBarcodeFileModel.getFileModel().getName(),
		 * machineBarcodeFileModel.getFileModel().isPublicfile(),
		 * machineBarcodeFileModel.getFileModel().getCompressName(),
		 * machineBarcodeFileModel.getFileModel().getPath() );
		 * 
		 * machineBarcodeFileView.setFileView(fileView); }
		 */
		return machineBarcodeFileView;
	}

	public MachineBarcodeItemView itemFromModel(MachineBarcodeItemModel machineBarcodeItemModel) {
		MachineBarcodeItemView machineBarcodeItemView = new MachineBarcodeItemView();
		machineBarcodeItemView.setBarcodeName(machineBarcodeItemModel.getBarcodeName());
		machineBarcodeItemView.setMaterialType(machineBarcodeItemModel.getMaterialType());
		machineBarcodeItemView.setItemVolume(machineBarcodeItemModel.getItemVolume());
		machineBarcodeItemView.setItemWeight(machineBarcodeItemModel.getItemWeight());
		machineBarcodeItemView.setItemValue(machineBarcodeItemModel.getItemValue());
		// machineBarcodeItemView.setMachineBarcodeFileView(fromModel(machineBarcodeItemModel.getMachineBarcodeFileModel()));
		return machineBarcodeItemView;
	}

	@Override
	protected void checkInactive(MachineBarcodeFileModel model) throws EndlosAPIException {

	}

	@Override
	public Response doUpdate(MachineBarcodeFileView machineBarcodeFileView) throws EndlosAPIException {

		MachineBarcodeFileModel model = toModel(getNewModel(), machineBarcodeFileView);
		machineBarcodeService.update(model);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	/*
	 * @Override //Without-Batch-Process public Response doExport(Long id) throws
	 * EndlosAPIException { MachineBarcodeFileModel machineBarcodeFileModel =
	 * machineBarcodeService.get(id); if (machineBarcodeFileModel == null) { throw
	 * new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(),
	 * ResponseCode.NO_DATA_FOUND.getMessage()); }
	 * 
	 * List<MachineBarcodeItemModel> machineBarcodeItemModels =
	 * machineBarcodeItemService.getByMachineBarcodeFileViewId(
	 * machineBarcodeFileModel.getId());
	 * 
	 * String newFileName = machineBarcodeFileModel.getBarcodeFileName() + ".csv";
	 * String filepath = null;
	 * 
	 * try (StringWriter stringWriter = new StringWriter(); PrintWriter writer = new
	 * PrintWriter(stringWriter)) {
	 * 
	 * // Writing header
	 * writer.println("BARCODE,MATERIAL_TYPE,ITEM_VOLUME,ITEM_WEIGHT,ITEM_VALUE");
	 * 
	 * // Writing data for (MachineBarcodeItemModel machineBarcodeItemModel :
	 * machineBarcodeItemModels) {
	 * writer.println(machineBarcodeItemModel.getBarcodeName() + "," +
	 * machineBarcodeItemModel.getMaterialType() + "," +
	 * machineBarcodeItemModel.getItemVolume() + "," +
	 * machineBarcodeItemModel.getItemWeight() + "," +
	 * machineBarcodeItemModel.getItemValue()); }
	 * 
	 * String path = SystemSettingModel.getDefaultFilePath() + File.separator +
	 * "Excel" + File.separator;
	 * 
	 * File file = new File(path); if (!file.exists()) { file.mkdirs(); }
	 * 
	 * filepath = file.getPath() + File.separator + newFileName; try
	 * (FileOutputStream fileOut = new FileOutputStream(filepath)) {
	 * fileOut.write(stringWriter.toString().getBytes()); } } catch (Exception ex) {
	 * LoggerService.exception(ex); throw new
	 * EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
	 * ResponseCode.INTERNAL_SERVER_ERROR.getMessage()); } FileModel fileModel = new
	 * FileModel(); fileModel.setFileId(Utility.generateUuid());
	 * fileModel.setName(newFileName);
	 * fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE_BARCODE.getId()));
	 * fileModel.setPublicfile(false); fileModel.setPath(filepath);
	 * fileModel.setUpload(DateUtility.getCurrentEpoch());
	 * fileService.create(fileModel);
	 * 
	 * return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(),
	 * "Export successfully", fileOperation.fromModel(fileModel)); }
	 */
	@Override // With Batch process
	public Response doExport(Long id) throws EndlosAPIException {
		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(id);
		if (machineBarcodeFileModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		List<MachineBarcodeItemModel> machineBarcodeItemModels = machineBarcodeItemService
				.getByMachineBarcodeFileViewId(machineBarcodeFileModel.getId());

		String newFileName = machineBarcodeFileModel.getBarcodeFileName() + ".csv";
		String filepath = null;

		try {
			String path = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			filepath = file.getPath() + File.separator + newFileName;

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
				// Writing header
				writer.write("BARCODE,MATERIAL_TYPE,ITEM_VOLUME,ITEM_WEIGHT,ITEM_VALUE");
				writer.newLine();

				// Fetch and process data in batches
				int batchSize = 1000; // Adjust batch size as needed
				for (int i = 0; i < machineBarcodeItemModels.size(); i += batchSize) {
					List<MachineBarcodeItemModel> batch = machineBarcodeItemModels.subList(i,
							Math.min(i + batchSize, machineBarcodeItemModels.size()));
					for (MachineBarcodeItemModel machineBarcodeItemModel : batch) {
						writer.write(machineBarcodeItemModel.getBarcodeName() + ","
								+ machineBarcodeItemModel.getMaterialType() + ","
								+ machineBarcodeItemModel.getItemVolume() + ","
								+ machineBarcodeItemModel.getItemWeight() + ","
								+ machineBarcodeItemModel.getItemValue());
						writer.newLine();
					}
					writer.flush();
				}
			}
		} catch (IOException ex) {
			LoggerService.exception(ex);
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}

		FileModel fileModel = new FileModel();
		fileModel.setFileId(Utility.generateUuid());
		fileModel.setName(newFileName);
		fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE_BARCODE.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Barcode export completed successfully.",
				fileOperation.fromModel(fileModel));
	}

	@Override
	@Async
	public CompletableFuture<Response> doExportAsync(Long id) {
		try {
			Response response = doExport(id);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}

	@Override
	@Async
	public CompletableFuture<Response> doFileUpload(MultipartFile multipartFile, String barcodeFileName)
			throws ExecutionException, InterruptedException, EndlosAPIException {

		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService
				.getByMachineBarcodeName(barcodeFileName);
		if (machineBarcodeFileModel != null) {
			throw new EndlosAPIException(ResponseCode.MACHINE_BARCODE_ALREADY_EXIST.getCode(),
					ResponseCode.MACHINE_BARCODE_ALREADY_EXIST.getMessage());
		}
		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		Storage storage = StorageOptions.getDefaultInstance().getService();

		Bucket bucket = storage.get("barcode" + "-" + activeProfile);
		if (bucket == null) {
			bucket = storage.create(BucketInfo.of("barcode" + "-" + activeProfile));
		}
		// Upload the file
		try (InputStream inputStream = multipartFile.getInputStream()) {
			BlobId blobId = BlobId.of(bucket.getName(), barcodeFileName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			storage.create(blobInfo, inputStream);
		}catch (Exception e) {
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		try {
			Response response = processUploadFileData(multipartFile, barcodeFileName);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}

	public Response processUploadFileData(MultipartFile multipartFile, String barcodeFileName)
			throws EndlosAPIException {

		long totalPlastic = 0;
		long totalGlass = 0;
		long totalAlluminium = 0;
		long allSavedBottleCounter = 0L;

		BufferedReader reader = null;
		try {
			Set<String> expectedHeaders = new HashSet<>(
					Arrays.asList("BARCODE", "MATERIAL_TYPE", "ITEM_VOLUME", "ITEM_WEIGHT", "ITEM_VALUE"));
			reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
			String headerLine = reader.readLine();
			if (headerLine == null || headerLine.isEmpty()) {
				throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
						ResponseCode.INVALID_FILE_FORMAT.getMessage());
			}
			String[] headerNames = headerLine.split(",");
			for (String expectedHeader : expectedHeaders) {
				if (!Arrays.asList(headerNames).contains(expectedHeader)) {
					throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
							ResponseCode.INVALID_FILE_FORMAT.getMessage());
				}
			}
		} catch (IOException ioException) {
			throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
					ResponseCode.INVALID_FILE_FORMAT.getMessage());
		}
		try {
			MachineBarcodeFileModel machineBarcodeFileModel = new MachineBarcodeFileModel();
			machineBarcodeFileModel.setNoOfMachineAssigned(0L);
			machineBarcodeFileModel.setTotalNoOfBarcodes(0L);
			machineBarcodeFileModel.setPlastic(0L);
			machineBarcodeFileModel.setGlass(0L);
			machineBarcodeFileModel.setAlluminium(0L);
			machineBarcodeFileModel.setBarcodeFileName(barcodeFileName);
			machineBarcodeFileModel.setFileStatus("Not Uploaded");
			machineBarcodeService.create(machineBarcodeFileModel);

			machineBarcodeFileModel = machineBarcodeService.getLastmachineBarcodeFile();

			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");

				if (data[0] != null || data[1] != null || !data[0].isEmpty() || !data[1].isEmpty()) {

					if (data[1].equals("1") || data[1].equals("2") || data[1].equals("3")) {

						List<MachineBarcodeItemModel> machineBarcodeItemModels = machineBarcodeItemService
								.setbBarcodeNameAndBarcodeFileId(data[1], machineBarcodeFileModel.getId());

						if (machineBarcodeItemModels.isEmpty()) {

							if (NumberUtils.isNumber(data[2]) && NumberUtils.isNumber(data[3])
									&& NumberUtils.isNumber(data[4])) {

								if (data[1].equals("1"))// Glass
									totalGlass++;
								if (data[1].equals("2"))// Plastic
									totalPlastic++;
								if (data[1].equals("3"))// Aluminum
									totalAlluminium++;

								MachineBarcodeItemModel machineBarcodeItemModel = new MachineBarcodeItemModel();
								BigDecimal itemVolume = new BigDecimal(data[2].trim());
								BigDecimal itemWeight = new BigDecimal(data[3].trim());
								BigDecimal itemValue = new BigDecimal(data[4].trim());

								machineBarcodeItemModel.setBarcodeName(data[0]);
								machineBarcodeItemModel.setMaterialType(Integer.parseInt(data[1]));
								machineBarcodeItemModel.setItemVolume(itemVolume);
								machineBarcodeItemModel.setItemWeight(itemWeight);
								machineBarcodeItemModel.setItemValue(itemValue);
								machineBarcodeItemModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
								machineBarcodeItemService.create(machineBarcodeItemModel);
								allSavedBottleCounter++;
							}
						}
					}
				} else {
					throw new EndlosAPIException(ResponseCode.INVALID_UPLOADED_DATA.getCode(),
							ResponseCode.INVALID_UPLOADED_DATA.getMessage());
				}
			}
			reader.close();
			if (allSavedBottleCounter > 0) {
				machineBarcodeFileModel.setNoOfMachineAssigned(0L);
				machineBarcodeFileModel.setTotalNoOfBarcodes(allSavedBottleCounter);
				machineBarcodeFileModel.setPlastic(totalPlastic);
				machineBarcodeFileModel.setGlass(totalGlass);
				machineBarcodeFileModel.setAlluminium(totalAlluminium);
				machineBarcodeFileModel.setBarcodeFileName(barcodeFileName);
				machineBarcodeFileModel.setFileStatus("Uploaded");
				machineBarcodeService.update(machineBarcodeFileModel);

				return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(),
						"Barcode file has been successfully uploaded.");

			}
		} catch (Exception exception) {
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Barcode file has been successfully uploaded.");
	}
}