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
package com.intentlabs.endlos.changelocation.operation;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.enums.CommunicationFields;
import com.intentlabs.common.notification.enums.NotificationEnum;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.service.UserService;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeService;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.barcodestructure.service.BarcodeTemplateService;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.changelocation.enums.ChangeLocationStatus;
import com.intentlabs.endlos.changelocation.model.ChangeLocationModel;
import com.intentlabs.endlos.changelocation.service.ChangeLocationService;
import com.intentlabs.endlos.changelocation.view.ChangeLocationView;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.service.CustomerService;
import com.intentlabs.endlos.customer.service.LocationService;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.MachineView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 07/09/2022
 */
@Component(value = "changeLocationOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ChangeLocationOperationImpl extends AbstractOperation<ChangeLocationModel, ChangeLocationView>
		implements ChangeLocationOperation {

	@Autowired
	private ChangeLocationService changeLocationService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	private FileService fileService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private BarcodeTemplateService barcodeTemplateService;

	@Autowired
	private MachineService machineService;

	@Autowired
	MachineBarcodeService machineBarcodeService;

	@Autowired
	private UserService userService;

	@Value("${email.url}")
	private String emailUrl;

	@Override
	public BaseService<ChangeLocationModel> getService() {
		return changeLocationService;
	}

	@Override
	protected ChangeLocationModel loadModel(ChangeLocationView ChangeLocationView) {
		return changeLocationService.get(ChangeLocationView.getId());
	}

	@Override
	protected ChangeLocationModel getNewModel() {
		return new ChangeLocationModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(ChangeLocationView ChangeLocationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(ChangeLocationView ChangeLocationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		ChangeLocationModel ChangeLocationModel = changeLocationService.get(id);
		if (ChangeLocationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		ChangeLocationView ChangeLocationView = fromModel(ChangeLocationModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				ChangeLocationView);
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
	public Response doSearch(ChangeLocationView ChangeLocationView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = changeLocationService.search(ChangeLocationView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<ChangeLocationModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public ChangeLocationModel toModel(ChangeLocationModel ChangeLocationModel, ChangeLocationView ChangeLocationView)
			throws EndlosAPIException {
		return ChangeLocationModel;
	}

	@Override
	public ChangeLocationView fromModel(ChangeLocationModel changeLocationModel) {
		ChangeLocationView ChangeLocationView = new ChangeLocationView();
		ChangeLocationView.setId(changeLocationModel.getId());

		setOldChangeLocationView(ChangeLocationView, changeLocationModel);
		if (changeLocationModel.getCustomerModel() != null) {
			setNewChangeLocationView(ChangeLocationView, changeLocationModel);
		}
		if (changeLocationModel.getStatus() != null) {
			ChangeLocationView.setStatus(KeyValueView.create(changeLocationModel.getStatus().getId(),
					changeLocationModel.getStatus().getName()));
		}
		ChangeLocationView.setRequestDate(changeLocationModel.getRequestDate().toString());
		if (changeLocationModel.getResponseDate() != null) {
			ChangeLocationView.setResponseDate(changeLocationModel.getResponseDate().toString());
		}
		return ChangeLocationView;
	}

	private void setNewChangeLocationView(ChangeLocationView changeLocationView,
			ChangeLocationModel changeLocationModel) {
		CustomerView customerView = new CustomerView();
		customerView.setId(changeLocationModel.getCustomerModel().getId());
		customerView.setName(changeLocationModel.getCustomerModel().getName());
		changeLocationView.setCustomerView(customerView);

		LocationView locationView = new LocationView();

		locationView.setId(changeLocationModel.getLocationModel().getId());
		locationView.setName(changeLocationModel.getLocationModel().getName());
		locationView.setAddress(changeLocationModel.getLocationModel().getAddress());
		locationView.setArea(changeLocationModel.getLocationModel().getArea());
		if (changeLocationModel.getLocationModel().getCountryModel() != null) {
			locationView.setCountryView(
					KeyValueView.create(changeLocationModel.getLocationModel().getCountryModel().getId(),
							changeLocationModel.getLocationModel().getCountryModel().getName()));
		}
		if (changeLocationModel.getLocationModel().getStateModel() != null) {
			locationView
					.setStateView(KeyValueView.create(changeLocationModel.getLocationModel().getStateModel().getId(),
							changeLocationModel.getLocationModel().getStateModel().getName()));
		}
		if (changeLocationModel.getLocationModel().getCityModel() != null) {
			locationView.setCityView(KeyValueView.create(changeLocationModel.getLocationModel().getCityModel().getId(),
					changeLocationModel.getLocationModel().getCityModel().getName()));
		}
		locationView.setPincode(changeLocationModel.getLocationModel().getPincode());
		if (changeLocationModel.getLocationModel().getLatitude() != null) {
			locationView.setLatitude(changeLocationModel.getLocationModel().getLatitude());
		}
		if (changeLocationModel.getLocationModel().getLongitude() != null) {
			locationView.setLongitude(changeLocationModel.getLocationModel().getLongitude());
		}
		if (changeLocationModel.getLocationModel().getAltitude() != null) {
			locationView.setAltitude(changeLocationModel.getLocationModel().getAltitude());
		}
		locationView.setBranchNumber(changeLocationModel.getLocationModel().getBranchNumber());

		changeLocationView.setLocationView(locationView);

		BarcodeTemplateView barcodeTemplateView = new BarcodeTemplateView();
		barcodeTemplateView.setId(changeLocationModel.getBarcodeTemplateModel().getId());
		barcodeTemplateView.setName(changeLocationModel.getBarcodeTemplateModel().getName());

		changeLocationView.setBarcodeTemplateView(barcodeTemplateView);
		if (changeLocationModel.getNewBranchMachineNumber() != null) {
			changeLocationView.setNewBranchMachineNumber(changeLocationModel.getNewBranchMachineNumber());
		}
	}

	private void setOldChangeLocationView(ChangeLocationView changeLocationView,
			ChangeLocationModel changeLocationModel) {
		MachineView machineView = new MachineView();
		machineView.setId(changeLocationModel.getMachineModel().getId());
		machineView.setMachineId(changeLocationModel.getMachineModel().getMachineId());
		changeLocationView.setMachineView(machineView);

		CustomerView customerView = new CustomerView();
		customerView.setId(changeLocationModel.getOldCustomerModel().getId());
		customerView.setName(changeLocationModel.getOldCustomerModel().getName());
		changeLocationView.setOldCustomerView(customerView);

		LocationView locationView = new LocationView();

		locationView.setId(changeLocationModel.getOldLocationModel().getId());
		locationView.setName(changeLocationModel.getOldLocationModel().getName());
		locationView.setAddress(changeLocationModel.getOldLocationModel().getAddress());
		locationView.setArea(changeLocationModel.getOldLocationModel().getArea());
		if (changeLocationModel.getOldLocationModel().getCountryModel() != null) {
			locationView.setCountryView(
					KeyValueView.create(changeLocationModel.getOldLocationModel().getCountryModel().getId(),
							changeLocationModel.getOldLocationModel().getCountryModel().getName()));
		}
		if (changeLocationModel.getOldLocationModel().getStateModel() != null) {
			locationView
					.setStateView(KeyValueView.create(changeLocationModel.getOldLocationModel().getStateModel().getId(),
							changeLocationModel.getOldLocationModel().getStateModel().getName()));
		}
		if (changeLocationModel.getOldLocationModel().getCityModel() != null) {
			locationView
					.setCityView(KeyValueView.create(changeLocationModel.getOldLocationModel().getCityModel().getId(),
							changeLocationModel.getOldLocationModel().getCityModel().getName()));
		}
		locationView.setPincode(changeLocationModel.getOldLocationModel().getPincode());
		if (changeLocationModel.getOldLocationModel().getLatitude() != null) {
			locationView.setLatitude(changeLocationModel.getOldLocationModel().getLatitude());
		}
		if (changeLocationModel.getOldLocationModel().getLongitude() != null) {
			locationView.setLongitude(changeLocationModel.getOldLocationModel().getLongitude());
		}
		if (changeLocationModel.getOldLocationModel().getAltitude() != null) {
			locationView.setAltitude(changeLocationModel.getOldLocationModel().getAltitude());
		}
		locationView.setBranchNumber(changeLocationModel.getOldLocationModel().getBranchNumber());

		changeLocationView.setOldLocationView(locationView);

		BarcodeTemplateView barcodeTemplateView = new BarcodeTemplateView();
		barcodeTemplateView.setId(changeLocationModel.getOldBarcodeTemplateModel().getId());
		barcodeTemplateView.setName(changeLocationModel.getOldBarcodeTemplateModel().getName());

		changeLocationView.setOldBarcodeTemplateView(barcodeTemplateView);

		if (changeLocationModel.getOldBranchMachineNumber() != null) {
			changeLocationView.setOldBranchMachineNumber(changeLocationModel.getOldBranchMachineNumber());
		}
		if (changeLocationModel.getOldMachineBarcodeFileModel() != null) {
			/*MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			FileView fileView = new FileView();
			fileView.setName(changeLocationModel.getOldMachineBarcodeFileModel().getFileModel().getName());
			machineBarcodeFileView.setFileView(fileView);
			changeLocationView.setOldMachineBarcodeFileView(machineBarcodeFileView);*/

			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setBarcodeFileName(changeLocationModel.getOldMachineBarcodeFileModel().getBarcodeFileName());
			changeLocationView.setOldMachineBarcodeFileView(machineBarcodeFileView);

		}
		if (changeLocationModel.getMachineBarcodeFileModel() != null) {
		/*	MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			FileView fileView = new FileView();
			fileView.setName(changeLocationModel.getMachineBarcodeFileModel().getFileModel().getName());
			machineBarcodeFileView.setFileView(fileView);
			changeLocationView.setBarcodeMachineView(machineBarcodeFileView);*/

			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setBarcodeFileName(changeLocationModel.getMachineBarcodeFileModel().getBarcodeFileName());
			changeLocationView.setBarcodeMachineView(machineBarcodeFileView);
		}
	}

	@Override
	protected void checkInactive(ChangeLocationModel ChangeLocationModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doExport(ChangeLocationView ChangeLocationView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<ChangeLocationModel> ChangeLocationModels = changeLocationService.doExport(ChangeLocationView, orderType,
				orderParam);
		if (ChangeLocationModels == null) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = "Change_Location" + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Change Location";

			int dataStartingFrom = 0;

			Sheet sheet = workbook.createSheet(sheetname);

			if (ChangeLocationView.getStartDate() != null && ChangeLocationView.getEndDate() != null) {

				Row rowFilterData1 = sheet.createRow((short) dataStartingFrom);
				rowFilterData1.createCell((short) 0).setCellValue("Date");
				rowFilterData1.createCell((short) 1)
						.setCellValue(String.valueOf(format.format(ChangeLocationView.getStartDate() * 1000L)) + " - "
								+ String.valueOf(format.format(ChangeLocationView.getEndDate() * 1000L)));
				dataStartingFrom++;
			}
			if (ChangeLocationView.getCustomerView() != null) {

				Row rowFilterData2 = sheet.createRow((short) dataStartingFrom);
				rowFilterData2.createCell((short) 0).setCellValue("Customer Name");
				rowFilterData2.createCell((short) 1)
						.setCellValue(ChangeLocationModels.get(0).getCustomerModel().getName());
				dataStartingFrom++;

			}
			if (ChangeLocationView.getCustomerView() != null && ChangeLocationView.getLocationView() != null) {

				Row rowFilterData3 = sheet.createRow((short) dataStartingFrom);
				rowFilterData3.createCell((short) 0).setCellValue("Branch Name");
				rowFilterData3.createCell((short) 1)
						.setCellValue(ChangeLocationModels.get(0).getLocationModel().getName());
				dataStartingFrom++;
			}
			if (ChangeLocationView.getMachineView() != null) {

				Row rowFilterData5 = sheet.createRow((short) dataStartingFrom);
				rowFilterData5.createCell((short) 0).setCellValue("Machine Id");
				rowFilterData5.createCell((short) 1)
						.setCellValue(ChangeLocationModels.get(0).getMachineModel().getMachineId());
				dataStartingFrom++;
			}

			if (dataStartingFrom > 0) {
				dataStartingFrom++;
			}
			Row rowhead = sheet.createRow((short) dataStartingFrom);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Machine Id");
			rowhead.createCell((short) 2).setCellValue("Old Customer Name");
			rowhead.createCell((short) 3).setCellValue("Old Branch Name");
			rowhead.createCell((short) 4).setCellValue("Old Barcode Template Name");
			rowhead.createCell((short) 5).setCellValue("Branch Wise Machine NUmber");
			rowhead.createCell((short) 6).setCellValue("Requested Timestamp");
			rowhead.createCell((short) 7).setCellValue("Status");
			rowhead.createCell((short) 8).setCellValue("New Customer Name");
			rowhead.createCell((short) 9).setCellValue("New Branch Name");
			rowhead.createCell((short) 10).setCellValue("New Barcode Template Name");

			int i = dataStartingFrom;
			int j = 0;
			for (ChangeLocationModel changeLocationModel : ChangeLocationModels) {

				i++;
				j++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) j);
				if (changeLocationModel.getMachineModel() != null) {
					row.createCell((short) 1).setCellValue(changeLocationModel.getMachineModel().getMachineId());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (changeLocationModel.getOldCustomerModel() != null) {
					row.createCell((short) 2).setCellValue(changeLocationModel.getOldCustomerModel().getName());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (changeLocationModel.getOldLocationModel() != null) {
					row.createCell((short) 3).setCellValue(changeLocationModel.getOldLocationModel().getName());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (changeLocationModel.getOldBarcodeTemplateModel() != null) {
					row.createCell((short) 4).setCellValue(changeLocationModel.getOldBarcodeTemplateModel().getName());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (changeLocationModel.getOldBranchMachineNumber() != null) {
					row.createCell((short) 5).setCellValue(changeLocationModel.getOldBranchMachineNumber());
				} else {
					row.createCell((short) 5).setCellValue("");
				}
				if (changeLocationModel.getRequestDate() != null) {
					row.createCell((short) 6)
							.setCellValue(String.valueOf(format.format(changeLocationModel.getRequestDate() * 1000L)));
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (changeLocationModel.getStatus() != null) {
					row.createCell((short) 7).setCellValue(changeLocationModel.getStatus().getName());
				} else {
					row.createCell((short) 7).setCellValue("");
				}
				if (changeLocationModel.getCustomerModel() != null) {
					row.createCell((short) 8).setCellValue(changeLocationModel.getCustomerModel().getName());
				} else {
					row.createCell((short) 8).setCellValue("");
				}
				if (changeLocationModel.getLocationModel() != null) {
					row.createCell((short) 9).setCellValue(changeLocationModel.getLocationModel().getName());
				} else {
					row.createCell((short) 9).setCellValue("");
				}

				if (changeLocationModel.getBarcodeTemplateModel() != null) {
					row.createCell((short) 10).setCellValue(changeLocationModel.getBarcodeTemplateModel().getName());
				} else {
					row.createCell((short) 10).setCellValue("");
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

	@Override
	public Response doApproveOrReject(Long id, Boolean approve) throws EndlosAPIException {
		ChangeLocationModel changeLocationModel = changeLocationService.get(id);
		if (changeLocationModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		MachineModel machineModel = machineService.get(changeLocationModel.getMachineModel().getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (approve) {
			changeLocationModel.setStatus(ChangeLocationStatus.ALLOCATION_PENDING.getId());
		} else {
			changeLocationModel.setStatus(ChangeLocationStatus.REJECT.getId());
			machineModel.setRejected(true);
		}
		changeLocationModel.setResponseDate(DateUtility.getCurrentEpoch());
		machineService.update(machineModel);
		changeLocationService.update(changeLocationModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doAssignChangLocation(ChangeLocationView changeLocationView) throws EndlosAPIException {
		ChangeLocationModel changeLocationModel = changeLocationService.get(changeLocationView.getId());
		if (changeLocationModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		MachineModel machineModel = machineService.get(changeLocationModel.getMachineModel().getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		CustomerModel customerModel = customerService.get(changeLocationView.getCustomerView().getId());
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.CUSTOMER_IS_INVALID.getCode(),
					ResponseCode.CUSTOMER_IS_INVALID.getMessage());
		}
		LocationModel locationModel = locationService.get(changeLocationView.getLocationView().getId());
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.LOCATION_NAME_IS_INVALID.getCode(),
					ResponseCode.LOCATION_NAME_IS_INVALID.getMessage());
		}
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService
				.get(changeLocationView.getBarcodeTemplateView().getId());
		if (barcodeTemplateModel == null) {
			throw new EndlosAPIException(ResponseCode.BARCODE_IS_MISSING.getCode(),
					ResponseCode.BARCODE_IS_MISSING.getMessage());
		}
		MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(changeLocationView.getBarcodeMachineView().getId());
		if (machineBarcodeFileModel == null) {
			throw new EndlosAPIException(ResponseCode.BARCODE_IS_MISSING.getCode(),
					ResponseCode.BARCODE_IS_MISSING.getMessage());
		}
		else {
			//Start Check condition for Same Barcode file assign then no need to increase counter
			if (changeLocationModel.getOldMachineBarcodeFileModel().getId() != machineBarcodeFileModel.getId()) {
				if(changeLocationModel.getMachineBarcodeFileModel() ==null) {

					machineBarcodeFileModel.setNoOfMachineAssigned(machineBarcodeFileModel.getNoOfMachineAssigned() + 1);
					machineBarcodeService.update(machineBarcodeFileModel);

					MachineBarcodeFileModel machineBarcodeFileModel2 = machineBarcodeService.get(changeLocationModel.getOldMachineBarcodeFileModel().getId());
					if(machineBarcodeFileModel2.getNoOfMachineAssigned() !=0) {
						machineBarcodeFileModel2.setNoOfMachineAssigned(machineBarcodeFileModel2.getNoOfMachineAssigned() - 1);
						machineBarcodeService.update(machineBarcodeFileModel2);
					}
				}
			}
			//End
		}
		changeLocationModel.setCustomerModel(customerModel);
		changeLocationModel.setLocationModel(locationModel);
		changeLocationModel.setBarcodeTemplateModel(barcodeTemplateModel);
		changeLocationModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
		changeLocationModel.setStatus(ChangeLocationStatus.ACCEPTED.getId());
		changeLocationModel.setNewBranchMachineNumber(changeLocationView.getNewBranchMachineNumber());

		machineModel.setCustomerModel(customerModel);
		machineModel.setLocationModel(locationModel);
		machineModel.setBarcodeTemplateModel(barcodeTemplateModel);
		machineModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
		machineModel.setRejected(false);
		machineModel.setBranchMachineNumber(changeLocationView.getNewBranchMachineNumber());
		machineService.update(machineModel);
		changeLocationService.update(changeLocationModel);

		// Code for change location send mail
		sendChangelocationEmail(changeLocationModel);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	private void sendChangelocationEmail(ChangeLocationModel changeLocationModel) {

		String oldBranchName = changeLocationModel.getOldLocationModel().getName();
		String oldBranchNumber = changeLocationModel.getOldLocationModel().getBranchNumber();
		String OldBranchWiseMachineNo = changeLocationModel.getOldBranchMachineNumber();
		String oldCustomerName = changeLocationModel.getOldLocationModel().getCustomerModel().getName() == "" || changeLocationModel.getOldLocationModel().getCustomerModel().getName().isEmpty() ? "" : changeLocationModel.getOldLocationModel().getCustomerModel().getName();

		String newBranchName = changeLocationModel.getLocationModel().getName();
		String newBranchNumber = changeLocationModel.getLocationModel().getBranchNumber();
		String newBranchWiseMachineNo = changeLocationModel.getNewBranchMachineNumber();
		String newCustomerName = changeLocationModel.getCustomerModel().getName() == "" || changeLocationModel.getCustomerModel().getName().isEmpty() ? "" : changeLocationModel.getCustomerModel().getName();

		String machineNo = changeLocationModel.getMachineModel().getMachineId();

		Map<String, String> dynamicFields = new TreeMap<>();
		dynamicFields.put(CommunicationFields.OLD_BRANCH_NAME.getName(), oldBranchName);
		dynamicFields.put(CommunicationFields.OLD_BRANCH_NUMBER.getName(), oldBranchNumber);
		dynamicFields.put(CommunicationFields.OLD_BRANCH_WISE_MACHINE_NO.getName(), OldBranchWiseMachineNo);
		dynamicFields.put(CommunicationFields.OLD_CUSTOMER_NAME.getName(), oldCustomerName);

		dynamicFields.put(CommunicationFields.NEW_BRANCH_NAME.getName(), newBranchName);
		dynamicFields.put(CommunicationFields.NEW_BRANCH_NUMBER.getName(), newBranchNumber);
		dynamicFields.put(CommunicationFields.NEW_BRANCH_WISE_MACHINE_NO.getName(), newBranchWiseMachineNo);
		dynamicFields.put(CommunicationFields.NEW_CUSTOMER_NAME.getName(), newCustomerName);

		dynamicFields.put(CommunicationFields.MACHINE_NO.getName(), machineNo);

		dynamicFields.put(CommunicationFields.ADMIN_URL.getName(), emailUrl);



		//If New and Old customer both are same then send email to Only One
		if(changeLocationModel.getCustomerModel().getId() == changeLocationModel.getOldCustomerModel().getId()) {

			//Email for New Customer
			UserModel userModel = userService.getCustomerAdmin(changeLocationModel.getCustomerModel());
			dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), userModel.getEmail());

			NotificationEnum.CHANGE_LOCATION.sendNotification(NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.CHANGE_LOCATION.getId())), dynamicFields);
		}
		//If New and Old customer both are Not same then send email to Both Customer(Old and New)
		else if(changeLocationModel.getCustomerModel().getId() != changeLocationModel.getOldCustomerModel().getId()) {

			//Email for New Customer
			UserModel newUserModel = userService.getCustomerAdmin(changeLocationModel.getCustomerModel());
			dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), newUserModel.getEmail());
			NotificationEnum.CHANGE_LOCATION.sendNotification(NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.CHANGE_LOCATION.getId())), dynamicFields);


			//Email for Old Customer
			UserModel oldUserModel = userService.getCustomerAdmin(changeLocationModel.getOldCustomerModel());
			dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), oldUserModel.getEmail());

			NotificationEnum.CHANGE_LOCATION.sendNotification(NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.CHANGE_LOCATION.getId())), dynamicFields);
		}

	}
}