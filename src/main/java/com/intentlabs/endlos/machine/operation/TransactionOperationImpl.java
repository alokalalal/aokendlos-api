/*******************************************************************************
\ * Copyright -2019 @intentlabs
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

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
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
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.enums.StatusEnum;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.model.TransactionLogModel;
import com.intentlabs.endlos.machine.model.TransactionModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.TransactionLogService;
import com.intentlabs.endlos.machine.service.TransactionService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.TransactionLogView;
import com.intentlabs.endlos.machine.view.TransactionView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@Component(value = "transactionOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class TransactionOperationImpl extends AbstractOperation<TransactionModel, TransactionView>
		implements TransactionOperation {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private FileService fileService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<TransactionModel> getService() {
		return transactionService;
	}

	@Override
	protected TransactionModel loadModel(TransactionView transactionView) {
		return transactionService.get(transactionView.getId());
	}

	@Override
	protected TransactionModel getNewModel() {
		return new TransactionModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(TransactionView transactionView) throws EndlosAPIException {

		/*for (int i=0; i<=10000;i++) {
			String id = "7ffac77e5c9d4e3ea5b0f1e6a70ae9d0" + i;
			TransactionModel transactionModel = new TransactionModel();
			transactionModel.setTransactionId(id);
			transactionModel.setPatBottleCount(10);
			transactionModel.setAluBottleCount(11);
			transactionModel.setGlassBottleCount(12);


			BigDecimal pb = new BigDecimal("5");
			transactionModel.setPatBottleValue(pb);

			BigDecimal ab = new BigDecimal("6");
			transactionModel.setAluBottleValue(ab);

			BigDecimal gb = new BigDecimal("7");
			transactionModel.setGlassBottleValue(gb);

			BigDecimal tv = new BigDecimal("18");

			transactionModel.setTotalValue(tv);
			transactionModel.setDateStart(1713425520L);
			transactionModel.setDateUpdate(1713425520L);
			transactionModel.setDateEnd(1713425520L);
			String barcode = "204201500000600060999979" + i;
			transactionModel.setBarcode(barcode);
			transactionModel.setMachineId("ASS3-0124-0016");
			MachineModel machineModel = new MachineModel();
			machineModel.setId(16L);
			transactionModel.setMachineModel(machineModel);
			transactionService.create(transactionModel);

		}*/
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(), ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
		//return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Saved successfully");
	}

	@Override
	public Response doUpdate(TransactionView transactionView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		TransactionModel transactionModel = transactionService.get(id);
		if (transactionModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		TransactionView transactionView = fromModel(transactionModel);
		List<TransactionLogModel> transactionLogModels = transactionLogService
				.getByTransactionId(transactionModel.getId());
		if (transactionLogModels != null) {
			List<TransactionLogView> transactionLogViews = new ArrayList<>();
			for (TransactionLogModel transactionLogModel : transactionLogModels) {
				TransactionLogView resView = new TransactionLogView();
				resView.setId(transactionLogModel.getId());
				resView.setBarcode(transactionLogModel.getBarcode());
				resView.setReason(transactionLogModel.getReason());
				if (transactionLogModel.getStatus() != null) {
					resView.setStatus(new KeyValueView(StatusEnum.fromId(transactionLogModel.getStatus()).getId(),
							StatusEnum.fromId(transactionLogModel.getStatus()).getName()));
				}
				if (transactionLogModel.getMaterial() != null) {
					resView.setMaterial(KeyValueView.create(transactionLogModel.getMaterial().getId(),
							transactionLogModel.getMaterial().getName()));
				}
				if (transactionLogModel.getVolumn() != null) {

					//resView.setVolumn(transactionLogModel.getVolumn().toString());
					BigDecimal vol = transactionLogModel.getVolumn();
					int intValue = vol.intValue();
					resView.setVolumn(String.valueOf(intValue));

				}
				if (transactionLogModel.getWeight() != null) {
					BigDecimal weight = transactionLogModel.getWeight();
					int intValue = weight.intValue();
					resView.setWeight(String.valueOf(intValue));

					//resView.setWeight(transactionLogModel.getWeight().toString());
				}
				if (transactionLogModel.getImageModel() != null) {
					FileView fileView = new FileView(transactionLogModel.getImageModel().getFileId(),
							transactionLogModel.getImageModel().getName(),
							transactionLogModel.getImageModel().isPublicfile(),
							transactionLogModel.getImageModel().getCompressName(),
							transactionLogModel.getImageModel().getPath());
					resView.setImageView(fileView);
				}
				transactionLogViews.add(resView);
			}
			transactionView.setTransactionLogViews(transactionLogViews);
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				transactionView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doSearch(TransactionView transactionView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = transactionService.search(transactionView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<TransactionModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public TransactionModel toModel(TransactionModel transactionModel, TransactionView transactionView)
			throws EndlosAPIException {
		return transactionModel;
	}

	@Override
	public TransactionView fromModel(TransactionModel transactionModel) {
		TransactionView transactionView = new TransactionView();
		transactionView.setId(transactionModel.getId());
		transactionView.setTransactionId(transactionModel.getTransactionId());
		transactionView.setPatBottleCount(transactionModel.getPatBottleCount());
		transactionView.setAluBottleCount(transactionModel.getAluBottleCount());
		transactionView.setGlassBottleCount(transactionModel.getGlassBottleCount());
		transactionView.setPatBottleValue(transactionModel.getPatBottleValue());
		transactionView.setAluBottleValue(transactionModel.getAluBottleValue());
		transactionView.setGlassBottleValue(transactionModel.getGlassBottleValue());
		transactionView.setTotalValue(transactionModel.getTotalValue());
		transactionView.setBarcode(transactionModel.getBarcode());

		transactionView.setDateStart(transactionModel.getDateStart());
		transactionView.setDateUpdate(transactionModel.getDateUpdate());
		transactionView.setDateEnd(transactionModel.getDateEnd());

		MachineView machineView = new MachineView();
		machineView.setId(transactionModel.getMachineModel().getId());
		machineView.setMachineId(transactionModel.getMachineModel().getMachineId());
		transactionView.setMachineView(machineView);

		if (transactionModel.getLocationModel() != null) {
			LocationView locationView = new LocationView();
			locationView.setId(transactionModel.getLocationModel().getId());
			locationView.setName(transactionModel.getLocationModel().getName());
			locationView.setBranchNumber(transactionModel.getLocationModel().getBranchNumber());
			CustomerView customerView = new CustomerView();
			customerView.setId(transactionModel.getLocationModel().getCustomerModel().getId());
			customerView.setName(transactionModel.getLocationModel().getCustomerModel().getName());
			locationView.setCustomerView(customerView);
			transactionView.setLocationView(locationView);
		}
		transactionView.setOffline(transactionModel.isOffline());
		return transactionView;
	}

	@Override
	protected void checkInactive(TransactionModel transactionModel) throws EndlosAPIException {
		// do nothing
	}

	/*@Override
	public Response doExport(TransactionView transactionView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<TransactionModel> transactionModels = transactionService.doExport(transactionView, orderType, orderParam);
		if (transactionModels == null || transactionModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineModel machineModel = null;
		if(transactionView.getMachineView() != null) {
			machineModel = machineService.get(transactionView.getMachineView().getId());
			if (machineModel == null) {
				return CommonResponse.create(ResponseCode.MACHINE_IS_INVALID.getCode(),
						ResponseCode.MACHINE_IS_INVALID.getMessage());
			}
		}
		String newFileName;
		if(machineModel != null)
			newFileName = machineModel.getMachineId() + transactionView.getStartDate() + "_"+ transactionView.getEndDate() + ".xlsx";
		else
			newFileName = "Transaction_" + transactionView.getStartDate() + "_"+ transactionView.getEndDate() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Transaction";

			Sheet sheet = workbook.createSheet(sheetname);

			int dataStartingFrom = 0;
			if(transactionView.getStartDate() !=null && transactionView.getEndDate() !=null) {

				Row rowFilterData1 = sheet.createRow((short) dataStartingFrom);
				rowFilterData1.createCell((short) 0).setCellValue("Date");
				rowFilterData1.createCell((short) 1).setCellValue(String.valueOf(format.format(transactionView.getStartDate() * 1000L)) + " - " + String.valueOf(format.format(transactionView.getEndDate() * 1000L)));

				dataStartingFrom++;

			}
			if(transactionView.getFullTextSearch() !=null && !transactionView.getFullTextSearch().isEmpty()) {

				Row rowFilterData2 = sheet.createRow((short) dataStartingFrom);
				rowFilterData2.createCell((short) 0).setCellValue("Voucher Barcode");
				rowFilterData2.createCell((short) 1).setCellValue(transactionView.getFullTextSearch());
				dataStartingFrom++;

			}
			if (transactionView.getCustomerView() !=null) {

				Row rowFilterData3 = sheet.createRow((short) dataStartingFrom);
				rowFilterData3.createCell((short) 0).setCellValue("Customer Name");
				rowFilterData3.createCell((short) 1).setCellValue(transactionModels.get(0).getLocationModel().getCustomerModel().getName());
				dataStartingFrom++;

			} if (transactionView.getCustomerView() !=null && transactionView.getLocationView() !=null) {

				Row rowFilterData4 = sheet.createRow((short) dataStartingFrom);
				rowFilterData4.createCell((short) 0).setCellValue("Branch Name");
				rowFilterData4.createCell((short) 1).setCellValue(transactionModels.get(0).getLocationModel().getName());
				dataStartingFrom++;
			}
			if (transactionView.getMachineView() !=null) {

				Row rowFilterData5 = sheet.createRow((short) dataStartingFrom);
				rowFilterData5.createCell((short) 0).setCellValue("Machine Id");
				rowFilterData5.createCell((short) 1).setCellValue(transactionModels.get(0).getMachineModel().getMachineId());
				dataStartingFrom++;
			}

			if(dataStartingFrom > 0) {
				dataStartingFrom++;
			}
			Row rowhead = sheet.createRow((short) dataStartingFrom);

			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Voucher Barcode");
			rowhead.createCell((short) 2).setCellValue("Date");
			rowhead.createCell((short) 3).setCellValue("Total Amount");
			rowhead.createCell((short) 4).setCellValue("Plastic");
			rowhead.createCell((short) 5).setCellValue("Glass");
			rowhead.createCell((short) 6).setCellValue("Aluminium");
			rowhead.createCell((short) 7).setCellValue("Machine Id");
			rowhead.createCell((short) 8).setCellValue("Customer Name");
			rowhead.createCell((short) 9).setCellValue("Branch Name");
			rowhead.createCell((short) 10).setCellValue("Transaction Type");

			AtomicInteger i = new AtomicInteger(dataStartingFrom+1);
			AtomicInteger j = new AtomicInteger(1);
			int batchSize = 1000;
			int totalRecords = transactionModels.size();
			for (int k = 0; k < totalRecords; k += batchSize) {
				List<TransactionModel> batch = transactionModels.subList(k, Math.min(k + batchSize, totalRecords));

				batch.parallelStream().forEach(transactionModel -> {

					int rowNum = i.getAndIncrement();
					int rowNum2 = j.getAndIncrement();

					synchronized (sheet) {

						Row row = sheet.createRow((short) rowNum);
						row.createCell((short) 0).setCellValue((Integer) rowNum2);
						if (transactionModel.getBarcode() != null) {
							row.createCell((short) 1).setCellValue(transactionModel.getBarcode());
						} else {
							row.createCell((short) 1).setCellValue("");
						}
						if (transactionModel.getDateEnd() != null) {
							row.createCell((short) 2)
									.setCellValue(String.valueOf(format.format(transactionModel.getDateEnd() * 1000L)));
						} else {
							row.createCell((short) 2).setCellValue("");
						}
						if (transactionModel.getTotalValue() != null
								&& transactionModel.getTotalValue().compareTo(BigDecimal.ZERO) > 0) {
							row.createCell((short) 3).setCellValue(transactionModel.getTotalValue().toString());
						} else {
							row.createCell((short) 3).setCellValue("");
						}

						row.createCell((short) 4).setCellValue(transactionModel.getPatBottleCount());
						row.createCell((short) 5).setCellValue(transactionModel.getGlassBottleCount());
						row.createCell((short) 6).setCellValue(transactionModel.getAluBottleCount());

						row.createCell((short) 7).setCellValue(transactionModel.getMachineModel().getMachineId());

						if (transactionModel.getMachineModel().getCustomerModel() != null) {
							//row.createCell((short) 8).setCellValue(transactionModel.getLocationModel().getCustomerModel().getName());
							row.createCell((short) 8).setCellValue(transactionModel.getMachineModel().getCustomerModel().getName());
						} else {
							row.createCell((short) 8).setCellValue("");
						}
						if (transactionModel.getMachineModel().getLocationModel() != null) {
							//row.createCell((short) 9).setCellValue(transactionModel.getLocationModel().getName());
							row.createCell((short) 9).setCellValue(transactionModel.getMachineModel().getLocationModel().getName());
						} else {
							row.createCell((short) 9).setCellValue("");
						}
						if (transactionModel.isOffline()) {
							row.createCell((short) 10).setCellValue("Offline");
						} else {
							row.createCell((short) 10).setCellValue("Online");
						}
					}
				});

			}

			String path = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator;

			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			filepath = file.getPath() + File.separator + newFileName;
			FileOutputStream fileOut = new FileOutputStream(filepath);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			LoggerService.exception(ex);
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		FileModel fileModel = new FileModel();
		fileModel.setFileId(Utility.generateUuid());
		fileModel.setName(newFileName);
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Transaction export completed successfully.", fileOperation.fromModel(fileModel));
	}*/

	@Override
	public Response doExport(TransactionView transactionView, Integer orderType, Integer orderParam) throws EndlosAPIException {

		List<TransactionModel> transactionModels = transactionService.doExport(transactionView, orderType, orderParam);

		if (transactionModels == null || transactionModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineModel machineModel = transactionView.getMachineView() != null ? machineService.get(transactionView.getMachineView().getId()) : null;

		String newFileName = (machineModel != null) ? machineModel.getMachineId() + "_" : "Transaction_";
		newFileName += transactionView.getStartDate() + "_" + transactionView.getEndDate() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Transaction");
			int rowNum  = 0;

			rowNum = writeFilterData(sheet, format, transactionView, transactionModels, rowNum);

			rowNum = writeHeaders(sheet, rowNum);


			int batchSize = 1000;
			int totalRecords = transactionModels.size();
			int sn = 0;

			for (int k = 0; k < totalRecords; k += batchSize) {
				List<TransactionModel> batch = transactionModels.subList(k, Math.min(k + batchSize, totalRecords));
				for (TransactionModel transactionModel : batch) {
					Row row = sheet.createRow(++rowNum);
					writeTransactionData(row, ++sn, transactionModel, format);
				}
			}

			// Write to file asynchronously
			filepath = writeWorkbookToFile(workbook, newFileName);

		} catch (Exception ex) {
			LoggerService.exception(ex);
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		// Create file model
		FileModel fileModel = createFileModel(newFileName, filepath);


		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Transaction export completed successfully.", fileOperation.fromModel(fileModel));
	}
	private int writeFilterData(Sheet sheet, DateFormat format, TransactionView transactionView, List<TransactionModel> transactionModels, int rowNum) {

		if (transactionView.getStartDate() != null && transactionView.getEndDate() != null) {
			Row rowFilterData1 = sheet.createRow(rowNum++);
			rowFilterData1.createCell(0).setCellValue("Date");
			rowFilterData1.createCell(1).setCellValue(format.format(transactionView.getStartDate() * 1000L) + " - " + format.format(transactionView.getEndDate() * 1000L));
		}
		if(transactionView.getFullTextSearch() !=null && !transactionView.getFullTextSearch().isEmpty()) {

			Row rowFilterData2 = sheet.createRow(rowNum++);
			rowFilterData2.createCell(0).setCellValue("Voucher Barcode");
			rowFilterData2.createCell(1).setCellValue(transactionView.getFullTextSearch());
		}
		if (transactionView.getCustomerView() !=null) {

			Row rowFilterData3 = sheet.createRow(rowNum++);
			rowFilterData3.createCell(0).setCellValue("Customer Name");
			rowFilterData3.createCell(1).setCellValue(transactionModels.get(0).getLocationModel().getCustomerModel().getName());

		} if (transactionView.getCustomerView() !=null && transactionView.getLocationView() !=null) {

			Row rowFilterData4 = sheet.createRow(rowNum++);
			rowFilterData4.createCell(0).setCellValue("Branch Name");
			rowFilterData4.createCell(1).setCellValue(transactionModels.get(0).getLocationModel().getName());
		}

		if (transactionView.getMachineView() !=null) {

			Row rowFilterData5 = sheet.createRow(rowNum++);
			rowFilterData5.createCell(0).setCellValue("Machine Id");
			rowFilterData5.createCell(1).setCellValue(transactionModels.get(0).getMachineModel().getMachineId());
		}
		return rowNum;
	}
	private int writeHeaders(Sheet sheet, int rowNum) {
		Row rowhead = sheet.createRow(++rowNum);
		String[] headers = {"S. No.", "Voucher Barcode", "Date", "Total Amount", "Plastic", "Glass", "Aluminium", "Machine Id", "Customer Name", "Branch Name", "Transaction Type"};
		for (int i = 0; i < headers.length; i++) {
			rowhead.createCell(i).setCellValue(headers[i]);
		}
		return rowNum;
	}
	private void writeTransactionData(Row row, int sn, TransactionModel transactionModel, DateFormat format) {
		row.createCell(0).setCellValue(sn);
		row.createCell(1).setCellValue(transactionModel.getBarcode() != null ? transactionModel.getBarcode() : "");
		row.createCell(2).setCellValue(transactionModel.getDateEnd() != null ? format.format(transactionModel.getDateEnd() * 1000L) : "");
		row.createCell(3).setCellValue(transactionModel.getTotalValue() != null && transactionModel.getTotalValue().compareTo(BigDecimal.ZERO) > 0 ? transactionModel.getTotalValue().toString() : "");
		row.createCell(4).setCellValue(transactionModel.getPatBottleCount());
		row.createCell(5).setCellValue(transactionModel.getGlassBottleCount());
		row.createCell(6).setCellValue(transactionModel.getAluBottleCount());
		row.createCell(7).setCellValue(transactionModel.getMachineModel().getMachineId());
		if(transactionModel.getLocationModel() != null) {
			row.createCell(8).setCellValue(transactionModel.getLocationModel().getCustomerModel() != null ? transactionModel.getLocationModel().getCustomerModel().getName() : "");
			row.createCell(9).setCellValue(transactionModel.getLocationModel() != null ? transactionModel.getLocationModel().getName() : "");
		}
		else {
			row.createCell(8).setCellValue("");
			row.createCell(9).setCellValue("");
		}
		row.createCell(10).setCellValue(transactionModel.isOffline() ? "Offline" : "Online");
	}
	private String writeWorkbookToFile(XSSFWorkbook workbook, String newFileName) throws IOException {
		String path = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filepath = file.getPath() + File.separator + newFileName;
		FileOutputStream fileOut = new FileOutputStream(filepath);
		workbook.write(fileOut);
		fileOut.close();
		return filepath;
	}

	private FileModel createFileModel(String newFileName, String filepath) {
		FileModel fileModel = new FileModel();
		fileModel.setFileId(Utility.generateUuid());
		fileModel.setName(newFileName);
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return fileModel;
	}

	/*@Override
	@Async
	public CompletableFuture<Response> doExportAsync(TransactionView transactionView, Integer orderType, Integer orderParam) {
		try {
			Response response = doExport(transactionView, orderType, orderParam);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}*/
	@Override
	public CompletableFuture<Response> doExportAsync(TransactionView transactionView, Integer orderType, Integer orderParam) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Response response = doExport(transactionView, orderType, orderParam);
				return response;
			} catch (EndlosAPIException e) {
				return CommonResponse.create(e.getCode(), e.getMessage());
			}
		});
	}
}