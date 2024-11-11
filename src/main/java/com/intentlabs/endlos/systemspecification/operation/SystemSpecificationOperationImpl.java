/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.systemspecification.operation;

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
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.systemspecification.model.SystemSpecificationModel;
import com.intentlabs.endlos.systemspecification.service.SystemSpecificationService;
import com.intentlabs.endlos.systemspecification.view.SystemSpecificationView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * This class used to perform all business operation on PickupRote model.
 * 
 * @author Milan.Gohil
 * @since 1/2/2024
 */
@Component(value = "SystemSpecificationOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class SystemSpecificationOperationImpl extends AbstractOperation<SystemSpecificationModel, SystemSpecificationView>
		implements SystemSpecificationOperation {

	@Autowired
	private SystemSpecificationService systemSpecificationService;
	@Autowired
	private MachineService machineService;
	@Autowired
	private FileOperation fileOperation;
	@Autowired
	private FileService fileService;

	@Override
	public BaseService getService() {
		return systemSpecificationService;
	}

	@Override
	protected SystemSpecificationModel getNewModel() {
		return new SystemSpecificationModel();
	}

	@Override
	protected SystemSpecificationModel loadModel(SystemSpecificationView systemSpecificationView) {
		return systemSpecificationService.get(systemSpecificationView.getId());
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(SystemSpecificationView systemSpecificationView) throws EndlosAPIException {
		SystemSpecificationModel systemSpecificationModel = new SystemSpecificationModel();

		systemSpecificationModel.setDateOfCreated(DateUtility.getCurrentEpoch());
		systemSpecificationModel.setDateOfUpdated(DateUtility.getCurrentEpoch());
		systemSpecificationModel = toModel(systemSpecificationModel, systemSpecificationView);
		systemSpecificationService.create(systemSpecificationModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		SystemSpecificationModel systemSpecificationModel = systemSpecificationService.get(id);
		if (systemSpecificationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		SystemSpecificationView systemSpecificationView = fromModel(systemSpecificationModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				systemSpecificationView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(SystemSpecificationView systemSpecificationView) throws EndlosAPIException {
		SystemSpecificationModel systemSpecificationModel = systemSpecificationService.get(systemSpecificationView.getId());
		if (systemSpecificationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		systemSpecificationModel.setDateOfUpdated(DateUtility.getCurrentEpoch());
		systemSpecificationModel = toModel(systemSpecificationModel, systemSpecificationView);
		systemSpecificationService.update(systemSpecificationModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(SystemSpecificationView systemSpecificationView, Integer start, Integer recordSize, Integer orderType,
                             Integer orderParam) {
		PageModel pageModel = systemSpecificationService.search(systemSpecificationView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		List<SystemSpecificationView> systemSpecificationViews = fromModelList((List<SystemSpecificationModel>) pageModel.getList());
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), systemSpecificationViews);
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		SystemSpecificationModel systemSpecificationModel = systemSpecificationService.get(id);
		if (systemSpecificationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		systemSpecificationService.hardDelete(systemSpecificationModel);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}


	@Override
	public SystemSpecificationModel toModel(SystemSpecificationModel systemSpecificationModel, SystemSpecificationView systemSpecificationView)
			throws EndlosAPIException {

		systemSpecificationModel.setAnydeskId(systemSpecificationView.getAnydeskId());
		systemSpecificationModel.setAnydeskPassword(systemSpecificationView.getAnydeskPassword());
		systemSpecificationModel.setWindowsActivationKey(systemSpecificationView.getWindowsActivationKey());
		systemSpecificationModel.setWindowsProductionKey(systemSpecificationView.getWindowsProductionKey());
		systemSpecificationModel.setWindowsPassword(systemSpecificationView.getWindowsPassword());
		MachineModel machineModel = machineService.get(systemSpecificationView.getMachineView().getId());
		systemSpecificationModel.setMachineModel(machineModel);

		return systemSpecificationModel;
	}

	@Override
	public SystemSpecificationView fromModel(SystemSpecificationModel systemSpecificationModel) {
		SystemSpecificationView systemSpecificationView = new SystemSpecificationView();
		systemSpecificationView.setId(systemSpecificationModel.getId());
		systemSpecificationView.setAnydeskId(systemSpecificationModel.getAnydeskId());
		systemSpecificationView.setAnydeskPassword(systemSpecificationModel.getAnydeskPassword());
		systemSpecificationView.setWindowsActivationKey(systemSpecificationModel.getWindowsActivationKey());
		systemSpecificationView.setWindowsProductionKey(systemSpecificationModel.getWindowsProductionKey());
		systemSpecificationView.setWindowsPassword(systemSpecificationModel.getWindowsPassword());
		if(systemSpecificationModel.getDateOfCreated() != null) {
			systemSpecificationView.setDateOfCreated(systemSpecificationModel.getDateOfCreated());
		}
		if(systemSpecificationModel.getDateOfUpdated() != null) {
			systemSpecificationView.setDateOfUpdated(systemSpecificationModel.getDateOfUpdated());
		}

		MachineView machineView = new MachineView();
		machineView.setId(systemSpecificationModel.getMachineModel().getId());
		machineView.setMachineId(systemSpecificationModel.getMachineModel().getMachineId());
		systemSpecificationView.setMachineView(machineView);

		return systemSpecificationView;
	}

	@Override
	public Response doExport(SystemSpecificationView systemSpecificationView, Integer orderType, Integer orderParam) throws EndlosAPIException {

		List<SystemSpecificationModel> systemSpecificationModels = systemSpecificationService.doExport(systemSpecificationView, orderType, orderParam);
		if (systemSpecificationModels == null || systemSpecificationModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = "System_Specification_Detail" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "System Specification Detail";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Machine Id");
			rowhead.createCell((short) 2).setCellValue("Anydesk Id");
			rowhead.createCell((short) 3).setCellValue("Anydesk Password");
			rowhead.createCell((short) 4).setCellValue("Windows Activation Key");
			rowhead.createCell((short) 5).setCellValue("Windows Production Key");
			rowhead.createCell((short) 6).setCellValue("Windows Password");
			rowhead.createCell((short) 7).setCellValue("Created On");
			rowhead.createCell((short) 8).setCellValue("Last Updated");

			int i = 0;
			for (SystemSpecificationModel systemSpecificationModel : systemSpecificationModels) {
				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);

				if (systemSpecificationModel.getMachineModel() != null) {
					row.createCell((short) 1).setCellValue(systemSpecificationModel.getMachineModel().getMachineId());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (systemSpecificationModel.getAnydeskId() != null) {
					row.createCell((short) 2).setCellValue(systemSpecificationModel.getAnydeskId());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (systemSpecificationModel.getAnydeskPassword() != null) {
					row.createCell((short) 3).setCellValue(systemSpecificationModel.getAnydeskPassword());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (systemSpecificationModel.getWindowsActivationKey() != null) {
					row.createCell((short) 4).setCellValue(systemSpecificationModel.getWindowsActivationKey());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (systemSpecificationModel.getWindowsProductionKey() != null) {
					row.createCell((short) 5).setCellValue(systemSpecificationModel.getWindowsProductionKey());
				} else {
					row.createCell((short) 5).setCellValue("");
				}
				if (systemSpecificationModel.getWindowsPassword() != null) {
					row.createCell((short) 6).setCellValue(systemSpecificationModel.getWindowsPassword());
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (systemSpecificationModel.getDateOfCreated() != null || systemSpecificationModel.getDateOfCreated() != 0) {
					row.createCell((short) 7).setCellValue(String.valueOf(format.format(systemSpecificationModel.getDateOfCreated() * 1000L)));
				} else {
					row.createCell((short) 7).setCellValue("");
				}

				if (systemSpecificationModel.getDateOfUpdated() != null || systemSpecificationModel.getDateOfUpdated() != 0) {
					row.createCell((short) 8).setCellValue(String.valueOf(format.format(systemSpecificationModel.getDateOfUpdated() * 1000L)));
				} else {
					row.createCell((short) 8).setCellValue("");
				}
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
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	@Override
	protected void checkInactive(SystemSpecificationModel model) throws EndlosAPIException {

	}

}