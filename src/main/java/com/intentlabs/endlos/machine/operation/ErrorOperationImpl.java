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

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import com.intentlabs.endlos.machine.view.MaintenanceError;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.model.ErrorModel;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.ErrorService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.ErrorView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@Component(value = "errorOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ErrorOperationImpl extends AbstractOperation<ErrorModel, ErrorView> implements ErrorOperation {

	@Autowired
	private ErrorService errorService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
	private FileService fileService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<ErrorModel> getService() {
		return errorService;
	}

	@Override
	protected ErrorModel loadModel(ErrorView errorView) {
		return errorService.get(errorView.getId());
	}

	@Override
	protected ErrorModel getNewModel() {
		return new ErrorModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(ErrorView errorView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(ErrorView errorView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		ErrorModel errorModel = errorService.get(id);
		if (errorModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		ErrorView errorView = fromModel(errorModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), errorView);
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
	public Response doSearch(ErrorView errorView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = errorService.search(errorView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<ErrorModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public ErrorModel toModel(ErrorModel errorModel, ErrorView errorView) throws EndlosAPIException {
		return errorModel;
	}

	@Override
	public ErrorView fromModel(ErrorModel errorModel) {
		ErrorView errorView = new ErrorView();
		errorView.setId(errorModel.getId());

		errorView.setErrorName(errorModel.getErrorName());
		errorView.setCreateDate(errorModel.getCreateDate());
		errorView.setResolve(errorModel.isResolve());
		if (errorModel.getResolveDate() != null) {
			errorView.setResolveDate(errorModel.getResolveDate());
		}
		MachineView machineView = new MachineView();
		machineView.setId(errorModel.getMachineModel().getId());
		machineView.setMachineId(errorModel.getMachineModel().getMachineId());
		errorView.setMachineView(machineView);

		if (errorModel.getLocationModel() != null) {
			LocationView locationView = new LocationView();
			locationView.setId(errorModel.getLocationModel().getId());
			locationView.setName(errorModel.getLocationModel().getName());
			locationView.setBranchNumber(errorModel.getLocationModel().getBranchNumber());
			CustomerView customerView = new CustomerView();
			customerView.setId(errorModel.getLocationModel().getCustomerModel().getId());
			customerView.setName(errorModel.getLocationModel().getCustomerModel().getName());
			locationView.setCustomerView(customerView);
			errorView.setLocationView(locationView);
		}
		return errorView;
	}

	@Override
	protected void checkInactive(ErrorModel errorModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doExport(ErrorView errorView, Integer orderType, Integer orderParam) throws EndlosAPIException {
		List<ErrorModel> errorModels = errorService.doExport(errorView, orderType, orderParam);
		if (errorModels == null || errorModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineModel machineModel = null;
		if (errorView.getMachineView() != null) {
			machineModel = machineService.get(errorView.getMachineView().getId());
		}
		/*if (machineModel == null) {
			return CommonResponse.create(ResponseCode.MACHINE_IS_INVALID.getCode(),
					ResponseCode.MACHINE_IS_INVALID.getMessage());
		}*/
		String newFileName = "";
		if (machineModel != null) {
			newFileName = machineModel.getMachineId() + errorView.getStartDate() + "_" + errorView.getEndDate()
					+ ".xlsx";
		}
		else {
			newFileName = "Error_"+errorView.getStartDate() + "_" + errorView.getEndDate()
					+ ".xlsx";
		}

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Error";

			int dataStartingFrom = 0;
			Sheet sheet = workbook.createSheet(sheetname);
			if(errorView.getStartDate() !=null && errorView.getEndDate() !=null) {

				Row rowFilterData1 = sheet.createRow((short) dataStartingFrom);
				rowFilterData1.createCell((short) 0).setCellValue("Date");
				rowFilterData1.createCell((short) 1).setCellValue(String.valueOf(format.format(errorView.getStartDate() * 1000L)) + " - " + String.valueOf(format.format(errorView.getEndDate() * 1000L)));

				dataStartingFrom++;

			}
			if(errorView.getFullTextSearch() !=null && !errorView.getFullTextSearch().isEmpty()) {

				Row rowFilterData2 = sheet.createRow((short) dataStartingFrom);
				rowFilterData2.createCell((short) 0).setCellValue("Voucher Barcode");
				rowFilterData2.createCell((short) 1).setCellValue(errorView.getFullTextSearch());
				dataStartingFrom++;

			}
			if (errorView.getCustomerView() !=null) {

				Row rowFilterData3 = sheet.createRow((short) dataStartingFrom);
				rowFilterData3.createCell((short) 0).setCellValue("Customer Name");
				rowFilterData3.createCell((short) 1).setCellValue(errorModels.get(0).getLocationModel().getCustomerModel().getName());
				dataStartingFrom++;

			} if (errorView.getCustomerView() !=null && errorView.getLocationView() !=null) {

				Row rowFilterData4 = sheet.createRow((short) dataStartingFrom);
				rowFilterData4.createCell((short) 0).setCellValue("Branch Name");
				rowFilterData4.createCell((short) 1).setCellValue(errorModels.get(0).getLocationModel().getName());
				dataStartingFrom++;
			}
			if (errorView.getMachineView() !=null) {

				Row rowFilterData5 = sheet.createRow((short) dataStartingFrom);
				rowFilterData5.createCell((short) 0).setCellValue("Machine Id");
				rowFilterData5.createCell((short) 1).setCellValue(errorModels.get(0).getMachineModel().getMachineId());
				dataStartingFrom++;
			}


			if(dataStartingFrom > 0) {
				dataStartingFrom++;
			}
			Row rowhead = sheet.createRow((short) dataStartingFrom);

			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Error Name");
			rowhead.createCell((short) 2).setCellValue("Create Date");
			rowhead.createCell((short) 3).setCellValue("Resolve Date");
			rowhead.createCell((short) 4).setCellValue("Status");
			rowhead.createCell((short) 5).setCellValue("Machine Id");
			rowhead.createCell((short) 6).setCellValue("Customer Name");
			rowhead.createCell((short) 7).setCellValue("Branch Name");

			int i = dataStartingFrom;
			int j=0;
			for (ErrorModel errorModel : errorModels) {

				i++; j++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) j);
				if (errorModel.getErrorName() != null) {
					//row.createCell((short) 1).setCellValue(MaintenanceError.getMaintenanceErrorMessage(errorModel.getErrorName()));
					row.createCell((short) 1).setCellValue(errorModel.getErrorName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (errorModel.getCreateDate() != null) {
					row.createCell((short) 2)
							.setCellValue(String.valueOf(format.format(errorModel.getCreateDate() * 1000L)));
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (errorModel.getResolveDate() != null) {
					row.createCell((short) 3)
							.setCellValue(String.valueOf(format.format(errorModel.getResolveDate() * 1000L)));
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (errorModel.isResolve()) {
					row.createCell((short) 4).setCellValue("Resolve");
				} else {
					row.createCell((short) 4).setCellValue("Pending");
				}
				row.createCell((short) 5).setCellValue(errorModel.getMachineModel().getMachineId());

				if (errorModel.getLocationModel().getCustomerModel() != null) {
					row.createCell((short) 6).setCellValue(errorModel.getLocationModel().getCustomerModel().getName());
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (errorModel.getLocationModel() != null) {
					row.createCell((short) 7).setCellValue(errorModel.getLocationModel().getName());
				} else {
					row.createCell((short) 7).setCellValue("");
				}
			}
//			String path = SettingTemplateModel.getFilePath(Auditor.getAuditor().getRequestedClientModel().getId())
//					+ File.separator + ModuleEnum.EMPLOYEE.getName() + File.separator + "Excel" + File.separator;

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
		//return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully", fileOperation.fromModel(fileModel));
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Errors export completed successfully.", fileOperation.fromModel(fileModel));
	}

	@Override
	@Async
	public CompletableFuture<Response> doExportAsync(ErrorView errorView, Integer orderType, Integer orderParam) {
		try {
			Response response = doExport(errorView, orderType, orderParam);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}
}