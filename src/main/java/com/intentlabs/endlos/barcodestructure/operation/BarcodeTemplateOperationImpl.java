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
package com.intentlabs.endlos.barcodestructure.operation;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

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
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeService;
import com.intentlabs.endlos.barcodestructure.model.BarcodeStructureModel;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.barcodestructure.service.BarcodeStructureService;
import com.intentlabs.endlos.barcodestructure.service.BarcodeTemplateService;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
@Component(value = "barcodeStructureTemplateOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class BarcodeTemplateOperationImpl extends AbstractOperation<BarcodeTemplateModel, BarcodeTemplateView>
		implements BarcodeTemplateOperation {

	@Autowired
	private BarcodeTemplateService barcodeTemplateService;

	@Autowired
	private BarcodeStructureService barcodeStructureService;

	@Autowired
	private MachineService machineService;

	@Autowired
	private FileService fileService;

	@Autowired
	private FileOperation fileOperation;

	@Autowired
	MachineBarcodeService machineBarcodeService;

	@Override
	public BaseService<BarcodeTemplateModel> getService() {
		return barcodeTemplateService;
	}

	@Override
	protected BarcodeTemplateModel loadModel(BarcodeTemplateView barcodeTemplateView) {
		return barcodeTemplateService.get(barcodeTemplateView.getId());
	}

	@Override
	protected BarcodeTemplateModel getNewModel() {
		return new BarcodeTemplateModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doSave(BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService.getByName(barcodeTemplateView.getName());
		if (barcodeTemplateModel != null && barcodeTemplateModel.getName().equals(barcodeTemplateView.getName())) {
			throw new EndlosAPIException(ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getCode(),
					ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getMessage());
		}
		barcodeTemplateModel = toModel(getNewModel(), barcodeTemplateView);
		barcodeTemplateService.create(barcodeTemplateModel);
		BarcodeTemplateView barcodeTemplateModelView = fromModel(barcodeTemplateModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				barcodeTemplateModelView);
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService.get(id);
		if (barcodeTemplateModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		BarcodeTemplateView barcodeTemplateView = fromModel(barcodeTemplateModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				barcodeTemplateView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService.get(barcodeTemplateView.getId());
		if (barcodeTemplateModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!barcodeTemplateModel.getName().equals(barcodeTemplateView.getName())) {
			barcodeTemplateModel = barcodeTemplateService.getByName(barcodeTemplateModel.getName());
			if (barcodeTemplateModel != null && barcodeTemplateModel.getName().equals(barcodeTemplateView.getName())) {
				throw new EndlosAPIException(ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getCode(),
						ResponseCode.BARCODE_TEMPLATE_ALREADY_EXIST.getMessage());
			}
		}
		List<MachineModel> machineModels = machineService.findByBarcodeTemplateId(barcodeTemplateModel.getId());
		if (!machineModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_EDIT_BARCODE_TEMPLATE.getCode(),
					ResponseCode.CAN_NOT_EDIT_BARCODE_TEMPLATE.getMessage());
		}
		barcodeTemplateModel = toModel(barcodeTemplateModel, barcodeTemplateView);
		List<BarcodeStructureModel> barcodeStructureModels = barcodeStructureService
				.findByBarcodeTemplateId(barcodeTemplateModel.getId());
		for (BarcodeStructureModel barcodeStructureModel : barcodeStructureModels) {
			barcodeStructureService.hardDelete(barcodeStructureModel);
		}
		barcodeTemplateModel.setCurrentLength(0l);
		barcodeTemplateService.update(barcodeTemplateModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doExport(BarcodeTemplateView barcodeTemplateView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<BarcodeTemplateModel> barcodeTemplateModels = barcodeTemplateService.doExport(barcodeTemplateView,
				orderType, orderParam);
		if (barcodeTemplateModels == null || barcodeTemplateModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Barcode Template";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Barcode Template Name");
			rowhead.createCell((short) 2).setCellValue("Barcode Length");
			rowhead.createCell((short) 3).setCellValue("Number Of Machines Assigned");
			rowhead.createCell((short) 4).setCellValue("Status");

			int i = 0;
			for (BarcodeTemplateModel barcodeTemplateModel : barcodeTemplateModels) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (barcodeTemplateModel.getName() != null) {
					row.createCell((short) 1).setCellValue(barcodeTemplateModel.getName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (barcodeTemplateModel.getTotalLength() != null) {
					row.createCell((short) 2).setCellValue(barcodeTemplateModel.getTotalLength());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				List<MachineModel> machineModels = machineService.findByBarcodeTemplateId(barcodeTemplateModel.getId());
				if (machineModels != null && !machineModels.isEmpty()) {
					row.createCell((short) 3).setCellValue(machineModels.size());
				} else {
					row.createCell((short) 3).setCellValue("0");
				}
				if (barcodeTemplateModel.isCompleted()) {
					row.createCell((short) 4).setCellValue("Completed");
				} else {
					row.createCell((short) 4).setCellValue("Draft");
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
		fileModel.setModule(Long.valueOf(ModuleEnum.BARCODE_STRUCTURE.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	@Override
	public Response doSearch(BarcodeTemplateView barcodeTemplateView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = barcodeTemplateService.search(barcodeTemplateView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<BarcodeTemplateModel>) pageModel.getList()));
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService.get(id);
		if (barcodeTemplateModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineModel> machineModels = machineService.findByBarcodeTemplateId(barcodeTemplateModel.getId());
		if (!machineModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_DELETE_BARCODE_TEMPLATE.getCode(),
					ResponseCode.CAN_NOT_DELETE_BARCODE_TEMPLATE.getMessage());
		}
		List<BarcodeStructureModel> barcodeStructureModels = barcodeStructureService
				.findByBarcodeTemplateId(barcodeTemplateModel.getId());
		for (BarcodeStructureModel barcodeStructureModel : barcodeStructureModels) {
			barcodeStructureService.hardDelete(barcodeStructureModel);
		}
		barcodeTemplateService.hardDelete(barcodeTemplateModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());
	}

	@Override
	protected void checkInactive(BarcodeTemplateModel barcodeTemplateModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public BarcodeTemplateModel toModel(BarcodeTemplateModel barcodeTemplateModel,
			BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		barcodeTemplateModel.setTotalLength(Long.parseLong(barcodeTemplateView.getTotalLength()));
		barcodeTemplateModel.setName(barcodeTemplateView.getName());
		return barcodeTemplateModel;
	}

	@Override
	public BarcodeTemplateView fromModel(BarcodeTemplateModel barcodeTemplateModel) {
		BarcodeTemplateView barcodeTemplateView = new BarcodeTemplateView();
		barcodeTemplateView.setId(barcodeTemplateModel.getId());
		barcodeTemplateView.setTotalLength(barcodeTemplateModel.getTotalLength().toString());
		barcodeTemplateView.setName(barcodeTemplateModel.getName());
		List<MachineModel> machineModels = machineService.findByBarcodeTemplateId(barcodeTemplateModel.getId());
		barcodeTemplateView.setNumberOfMachineAssigned(String.valueOf(machineModels.size()));
		barcodeTemplateView.setCompleted(barcodeTemplateModel.isCompleted());
		return barcodeTemplateView;
	}

	@Override
	public Response doDropdown() {
		List<BarcodeTemplateModel> barcodeTemplateModels = barcodeTemplateService.findAll();
		if (barcodeTemplateModels == null || barcodeTemplateModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<BarcodeTemplateView> barcodeTemplateViews = new ArrayList<>();
		for (BarcodeTemplateModel barcodeTemplateModel : barcodeTemplateModels) {
			if (barcodeTemplateModel.isCompleted()) {
				BarcodeTemplateView barcodeTemplateView = new BarcodeTemplateView();
				barcodeTemplateView.setId(barcodeTemplateModel.getId());
				barcodeTemplateView.setName(barcodeTemplateModel.getName());
				barcodeTemplateViews.add(barcodeTemplateView);
			}
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				barcodeTemplateViews.size(), barcodeTemplateViews);
	}

	@Override
	public Response doAssignMachine(BarcodeTemplateView barcodeTemplateView) throws EndlosAPIException {
		if (barcodeTemplateView.getMachineViews() == null || barcodeTemplateView.getMachineViews().isEmpty()) {
			return CommonResponse.create(ResponseCode.PLEASE_SELECT_MACHINE.getCode(),
					ResponseCode.PLEASE_SELECT_MACHINE.getMessage());
		}
		BarcodeTemplateModel barcodeTemplateModel = null;
		if (barcodeTemplateView.getId() != null) {
			barcodeTemplateModel = barcodeTemplateService.get(barcodeTemplateView.getId());
			if (barcodeTemplateModel == null) {
				return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			if (!barcodeTemplateModel.isCompleted()) {
				return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
		}

		MachineBarcodeFileModel machineBarcodeFileModel = null;
		if (barcodeTemplateView.getBarcodeFileId() != null) {
			machineBarcodeFileModel = machineBarcodeService.get(barcodeTemplateView.getBarcodeFileId());
			if (machineBarcodeFileModel == null) {
				return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
		}
		long countNumberOfMachine = 0;
		for (MachineView machineView : barcodeTemplateView.getMachineViews()) {
			MachineModel machineModel = machineService.get(machineView.getId());
			if (machineModel == null) {
				return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
						ResponseCode.NO_DATA_FOUND.getMessage());
			}
			if (barcodeTemplateView.getId() != null) {
				machineModel.setBarcodeTemplateModel(barcodeTemplateModel);
				machineModel.setBarcodeTemplateChanged(true);
			}
			else {
				return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
			}
			if (barcodeTemplateView.getPassword() != null) {
				machineModel.setPassword(barcodeTemplateView.getPassword());
				machineModel.setPasswordChanged(true);
			}

			//Start Check condition for Same Barcode file assign then no need to increase counter
			if(machineModel.getMachineBarcodeFileModel() != null) {
				if(machineModel.getMachineBarcodeFileModel().getId() != machineBarcodeFileModel.getId()) {

					//start if barcode already assign to machine and again change Different then minus One to already assign and increase one to new machine barcode
					MachineBarcodeFileModel machineBarcodeFileModel2 = machineBarcodeService.get(machineModel.getMachineBarcodeFileModel().getId());
					if(machineBarcodeFileModel2.getNoOfMachineAssigned() !=0) {
						machineBarcodeFileModel2.setNoOfMachineAssigned(machineBarcodeFileModel2.getNoOfMachineAssigned() - 1);
						machineBarcodeService.update(machineBarcodeFileModel2);
						countNumberOfMachine++;
					}
				}
			}
			else {
				countNumberOfMachine++;
			}
			//End

			if (barcodeTemplateView.getBarcodeFileId() != null) {
				machineModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
				machineModel.setBarcodeChanged(true);
			}
			else {
				return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
			}
			machineService.update(machineModel);
		}

		// Set Number of Machines Assigned to Machine Barcode Files
		if (machineBarcodeFileModel != null) {
			machineBarcodeFileModel.setNoOfMachineAssigned(machineBarcodeFileModel.getNoOfMachineAssigned() + countNumberOfMachine);
			machineBarcodeService.update(machineBarcodeFileModel);
		}


		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage()
				+ " assign barcode template to '" + barcodeTemplateView.getMachineViews().get(0).getMachineId() + "'");
	}
}