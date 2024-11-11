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

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.intentlabs.endlos.machine.model.PrintLogModel;
import com.intentlabs.endlos.machine.service.PrintLogService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.PrintLogView;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Component(value = "printLogOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class PrintLogOperationImpl extends AbstractOperation<PrintLogModel, PrintLogView> implements PrintLogOperation {

	@Autowired
	private PrintLogService printLogService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
	private FileService fileService;

	@Override
	public BaseService<PrintLogModel> getService() {
		return printLogService;
	}

	@Override
	protected PrintLogModel loadModel(PrintLogView printLogView) {
		return printLogService.get(printLogView.getId());
	}

	@Override
	protected PrintLogModel getNewModel() {
		return new PrintLogModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(PrintLogView printLogView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(PrintLogView printLogView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		PrintLogModel printLogModel = printLogService.get(id);
		if (printLogModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		PrintLogView printLogView = fromModel(printLogModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				printLogView);
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
	public Response doSearch(PrintLogView printLogView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = printLogService.search(printLogView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<PrintLogModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public PrintLogModel toModel(PrintLogModel printLogModel, PrintLogView printLogView) throws EndlosAPIException {
		return printLogModel;
	}

	@Override
	public PrintLogView fromModel(PrintLogModel printLogModel) {
		PrintLogView printLogView = new PrintLogView();
		printLogView.setId(printLogModel.getId());

		printLogView.setResetDate(printLogModel.getResetDate());
		printLogView.setCreateDate(printLogModel.getCreateDate());
		printLogView.setPrintCount(printLogModel.getPrintCount());

		MachineView machineView = new MachineView();
		machineView.setId(printLogModel.getMachineModel().getId());
		machineView.setMachineId(printLogModel.getMachineModel().getMachineId());
		printLogView.setMachineView(machineView);

		if (printLogModel.getLocationModel() != null) {
			LocationView locationView = new LocationView();
			locationView.setId(printLogModel.getLocationModel().getId());
			locationView.setName(printLogModel.getLocationModel().getName());
			locationView.setBranchNumber(printLogModel.getLocationModel().getBranchNumber());
			CustomerView customerView = new CustomerView();
			customerView.setId(printLogModel.getLocationModel().getCustomerModel().getId());
			customerView.setName(printLogModel.getLocationModel().getCustomerModel().getName());
			locationView.setCustomerView(customerView);
			printLogView.setLocationView(locationView);
		}
		return printLogView;
	}

	@Override
	protected void checkInactive(PrintLogModel printLogModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doExport(PrintLogView printLogView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<PrintLogModel> printLogModels = printLogService.doExport(printLogView, orderType, orderParam);
		if (printLogModels == null || printLogModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
//		MachineModel machineModel = machineService.get(printLogView.getMachineView().getId());
//		if (machineModel == null) {
//			return CommonResponse.create(ResponseCode.MACHINE_IS_INVALID.getCode(),
//					ResponseCode.MACHINE_IS_INVALID.getMessage());
//		}
		String newFileName = "Machine_Logs" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Error";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Reset Date");
			rowhead.createCell((short) 2).setCellValue("Print Count");
			rowhead.createCell((short) 3).setCellValue("Customer Name");
			rowhead.createCell((short) 4).setCellValue("Branch Name");

			int i = 0;
			for (PrintLogModel printLogModel : printLogModels) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (printLogModel.getResetDate() != null) {
					row.createCell((short) 1)
							.setCellValue(String.valueOf(format.format(printLogModel.getResetDate() * 1000L)));
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (printLogModel.getPrintCount() != null) {
					row.createCell((short) 2).setCellValue(printLogModel.getPrintCount());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (printLogModel.getLocationModel().getCustomerModel() != null) {
					row.createCell((short) 3)
							.setCellValue(printLogModel.getLocationModel().getCustomerModel().getName());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (printLogModel.getLocationModel() != null) {
					row.createCell((short) 4).setCellValue(printLogModel.getLocationModel().getName());
				} else {
					row.createCell((short) 4).setCellValue("");
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
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}
}