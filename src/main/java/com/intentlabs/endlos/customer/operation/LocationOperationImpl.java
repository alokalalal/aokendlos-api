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
package com.intentlabs.endlos.customer.operation;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.location.service.CityService;
import com.intentlabs.common.location.service.CountryService;
import com.intentlabs.common.location.service.StateService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.Model;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.service.CustomerService;
import com.intentlabs.endlos.customer.service.LocationService;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.logistic.service.PickupRouteService;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * This class used to perform all business operation on Location model.
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
@Component(value = "locationOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class LocationOperationImpl extends AbstractOperation<LocationModel, LocationView> implements LocationOperation {

	@Autowired
	private LocationService locationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FileService fileService;

	@Autowired
	private FileOperation fileOperation;

	@Autowired
	private MachineService machineService;
	@Autowired
	private PickupRouteService pickupRouteService;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(LocationView locationView) throws EndlosAPIException {
		CustomerModel customerModel = customerService.get(locationView.getCustomerView().getId());
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_CUSTOMER_ID.getCode(),
					ResponseCode.INVALID_CUSTOMER_ID.getMessage());
		}
		List<LocationModel> locationModels = locationService.getByCustomer(customerModel.getId());
		for (LocationModel model : locationModels) {
			if (model.getName().equals(locationView.getName())) {
				throw new EndlosAPIException(ResponseCode.LOCATION_ALREADY_EXIST.getCode(),
						ResponseCode.LOCATION_ALREADY_EXIST.getMessage());
			}
			if (model.getBranchNumber().equals(locationView.getBranchNumber())) {
				throw new EndlosAPIException(ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getCode(),
						ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getMessage());
			}
			if(model.getPositionRoute() !=null) {
				if (model.getPositionRoute().equals(locationView.getPositionRoute())) {
					throw new EndlosAPIException(ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getCode(),
							ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getMessage());
				}
			}
		}
		LocationModel locationModel = toModel(new LocationModel(), locationView);
		locationModel.setCustomerModel(customerModel);
		locationModel.setActive(true);
		locationService.create(locationModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		LocationModel locationModel = locationService.get(id);
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		LocationView locationView = fromModel(locationModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				locationView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		LocationModel locationModel = locationService.get(id);
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				fromModel(locationModel));
	}

	@Override
	protected LocationModel loadModel(LocationView locationView) {
		return locationService.get(locationView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		LocationModel locationModel = locationService.get(id);
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		locationService.hardDelete(locationModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY_BRANCH.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY_BRANCH.getMessage());

		// return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
		// ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		LocationModel locationModel = locationService.nonActivatedCustomer(id);
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		Auditor.activationAudit(locationModel, !locationModel.isActive());
		locationService.update(locationModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public LocationModel toModel(LocationModel locationModel, LocationView locationView) throws EndlosAPIException {
		locationModel.setName(locationView.getName());
		locationModel.setAddress(locationView.getAddress());
		locationModel.setArea(locationView.getArea());
		if (locationView.getCountryView() != null && locationView.getCountryView().getKey() != null) {
			locationModel.setCountryModel(countryService.load(locationView.getCountryView().getKey()));
		}
		if (locationView.getStateView() != null && locationView.getStateView().getKey() != null) {
			locationModel.setStateModel(stateService.load(locationView.getStateView().getKey()));
		}
		if (locationView.getCityView() != null && locationView.getCityView().getKey() != null) {
			locationModel.setCityModel(cityService.load(locationView.getCityView().getKey()));
		}
		locationModel.setPincode(locationView.getPincode());
		if (locationView.getLatitude() != null) {
			locationModel.setLatitude(locationView.getLatitude());
		}
		if (locationView.getLongitude() != null) {
			locationModel.setLongitude(locationView.getLongitude());
		}
		if (locationView.getAltitude() != null) {
			locationModel.setAltitude(locationView.getAltitude());
		}
		locationModel.setBranchNumber(locationView.getBranchNumber());

		//logistic
		if (locationView.getPickupRouteView() != null && locationView.getPickupRouteView().getKey() != null) {
			locationModel.setPickupRouteModel(pickupRouteService.load(locationView.getPickupRouteView().getKey()));
		}
		locationModel.setPositionRoute(locationView.getPositionRoute());

		if (false == locationView.getWorkDuringWeekends()) {
			locationModel.setWorkDuringWeekends(false);
		} else {
			locationModel.setWorkDuringWeekends(true);
		}
		if (false == locationView.getPickupEveryday()) {
			locationModel.setPickupEveryday(false);
		} else {
			locationModel.setPickupEveryday(true);
		}
		locationModel.setNumberOfGlassTanks(locationView.getNumberOfGlassTanks());
		locationModel.setGlassFullnessPercentageForPickup(locationView.getGlassFullnessPercentageForPickup());

		return locationModel;
	}

	@Override
	protected LocationModel getNewModel() {
		return new LocationModel();
	}

	@Override
	public LocationView fromModel(LocationModel locationModel) {
		LocationView locationView = new LocationView();
		locationView.setId(locationModel.getId());
		locationView.setName(locationModel.getName());
		locationView.setAddress(locationModel.getAddress());
		locationView.setArea(locationModel.getArea());
		if (locationModel.getCountryModel() != null) {
			locationView.setCountryView(KeyValueView.create(locationModel.getCountryModel().getId(),
					locationModel.getCountryModel().getName()));
		}
		if (locationModel.getStateModel() != null) {
			locationView.setStateView(KeyValueView.create(locationModel.getStateModel().getId(),
					locationModel.getStateModel().getName()));
		}
		if (locationModel.getCustomerModel() != null) {
			CustomerView customerView = new CustomerView();
			customerView.setId(locationModel.getCustomerModel().getId());
			customerView.setName(locationModel.getCustomerModel().getName());
			locationView.setCustomerView(customerView);
		}
		if (locationModel.getCityModel() != null) {
			locationView.setCityView(
					KeyValueView.create(locationModel.getCityModel().getId(), locationModel.getCityModel().getName()));
		}
		locationView.setPincode(locationModel.getPincode());
		if (locationModel.getLatitude() != null) {
			locationView.setLatitude(locationModel.getLatitude());
		}
		if (locationModel.getLongitude() != null) {
			locationView.setLongitude(locationModel.getLongitude());
		}
		if (locationModel.getAltitude() != null) {
			locationView.setAltitude(locationModel.getAltitude());
		}
		if (locationModel.getMachineModel() != null) {
			locationView.setMachineView(KeyValueView.create(locationModel.getMachineModel().getId(), locationModel.getMachineModel().getId().toString()));
		}
		locationView.setBranchNumber(locationModel.getBranchNumber());

		// logistic
		if (locationModel.getPickupRouteModel() != null) {
			locationView.setPickupRouteView(KeyValueView.create(locationModel.getPickupRouteModel().getId(),
					locationModel.getPickupRouteModel().getPickupRouteName().toString()));
		}
		locationView.setPositionRoute(locationModel.getPositionRoute());
		if (locationModel.getWorkDuringWeekends() != null && locationModel.getWorkDuringWeekends() == false) {
			locationView.setWorkDuringWeekends(false);
		} else if (locationModel.getWorkDuringWeekends() != null && locationModel.getWorkDuringWeekends() == true) {
			locationView.setWorkDuringWeekends(true);
		}
		if (locationModel.getPickupEveryday() != null && locationModel.getPickupEveryday() == false) {
			locationView.setPickupEveryday(false);
		} else if (locationModel.getPickupEveryday() != null && locationModel.getPickupEveryday() == true) {
			locationView.setPickupEveryday(true);
		}
		locationView.setNumberOfGlassTanks(locationModel.getNumberOfGlassTanks());
		locationView.setGlassFullnessPercentageForPickup(locationModel.getGlassFullnessPercentageForPickup());

		return locationView;
	}

	@Override
	public BaseService getService() {
		return locationService;
	}

	@Override
	protected void checkInactive(LocationModel locationModel) throws EndlosAPIException {
		// locatio Model Can not be active/inactive
	}

	@Override
	public Response doUpdate(LocationView locationView) throws EndlosAPIException {
		LocationModel locationModel = locationService.get(locationView.getId());
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (locationView.getCustomerView() != null) {
			CustomerModel customerModel = customerService.get(locationView.getCustomerView().getId());
			if (customerModel == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_CUSTOMER_ID.getCode(),
						ResponseCode.INVALID_CUSTOMER_ID.getMessage());
			}
			if (!locationModel.getName().equals(locationView.getName())) {
				List<LocationModel> locationModels = locationService.getByCustomer(customerModel.getId());
				for (LocationModel model : locationModels) {
					if (model.getName().equals(locationView.getName())) {
						throw new EndlosAPIException(ResponseCode.LOCATION_ALREADY_EXIST.getCode(),
								ResponseCode.LOCATION_ALREADY_EXIST.getMessage());
					}
					if (!locationModel.getBranchNumber().equals(locationView.getBranchNumber())) {
						if (model.getBranchNumber().equals(locationView.getBranchNumber())) {
							throw new EndlosAPIException(ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getCode(),
									ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getMessage());
						}
					}
					if (locationModel.getPositionRoute() != locationView.getPositionRoute()) {
						if (model.getPositionRoute() == locationView.getPositionRoute()) {
							throw new EndlosAPIException(ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getCode(),
									ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getMessage());
						}
					}
				}
			}
			else {
				if (!locationModel.getBranchNumber().equals(locationView.getBranchNumber()))  {
					List<LocationModel> locationModels = locationService.getByCustomer(customerModel.getId());
					for (LocationModel model : locationModels) {
						if (model.getBranchNumber().equals(locationView.getBranchNumber())) {
							throw new EndlosAPIException(ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getCode(),
									ResponseCode.BRANCH_NUMBER_IS_ALREADY_EXIST.getMessage());
						}
					}
				}
				if (!locationModel.getPositionRoute().equals(locationView.getPositionRoute()))  {
					List<LocationModel> locationModels = locationService.getByCustomer(customerModel.getId());
					for (LocationModel model : locationModels) {
						if (model.getPositionRoute() == locationView.getPositionRoute()) {
							throw new EndlosAPIException(ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getCode(),
									ResponseCode.POSITION_IN_ROUTE_ALREADY_EXIST.getMessage());
						}
					}
				}

			}
			locationModel = toModel(locationModel, locationView);
			locationModel.setCustomerModel(customerModel);
		}
		locationService.update(locationModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(LocationView locationView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		List<LocationModel> locationModels = new ArrayList<>();
		PageModel pageModel = locationService.search(locationView, start, recordSize, orderType, orderParam);

		for (Model locationModel : pageModel.getList()) {
			LocationModel locationModel1= (LocationModel) locationModel;
			List<MachineModel> byLocation = machineService.getByLocation(locationModel1.getId());

			if(byLocation.size() > 0)
				locationModel1.setMachineModel(byLocation.get(0));

			locationModels.add(locationModel1);
		}

		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		// return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(),
		// ResponseCode.SUCCESSFUL.getMessage(), pageModel.getRecords(),
		// fromModelList((List<LocationModel>) pageModel.getList()));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList(locationModels));
	}

	@Override
	public Response doDropDownBycustomer(LocationView locationView) {
		List<LocationModel> locationModels = locationService.getByCustomer(locationView.getCustomerView().getId());
		if (locationModels == null || locationModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<LocationView> locationViews = new ArrayList<>();
		for (LocationModel locationModel : locationModels) {
			LocationView resView = fromModel(locationModel);
			locationViews.add(resView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				locationViews.size(), locationViews);
	}

	@Override
	public Response doExport(LocationView locationView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<LocationModel> locationModels = locationService.doExport(locationView, orderType, orderParam);
		if (locationModels == null) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Branch List";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Name");
			rowhead.createCell((short) 2).setCellValue("Address");
			rowhead.createCell((short) 3).setCellValue("Area");
			rowhead.createCell((short) 4).setCellValue("Country");
			rowhead.createCell((short) 5).setCellValue("City");
			rowhead.createCell((short) 6).setCellValue("Branch Number");
			rowhead.createCell((short) 7).setCellValue("Pickup Route");
			rowhead.createCell((short) 8).setCellValue("Position in Route");
			rowhead.createCell((short) 9).setCellValue("Work During Weekends");
			rowhead.createCell((short) 10).setCellValue("Pickup Everyday");
			rowhead.createCell((short) 11).setCellValue("Number of Glass Tanks");
			rowhead.createCell((short) 12).setCellValue("Glass fullness Percentage for Pickup");

			int i = 0;
			for (LocationModel locationModel : locationModels) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (locationModel.getName() != null) {
					row.createCell((short) 1).setCellValue(locationModel.getName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (locationModel.getAddress() != null) {
					row.createCell((short) 2).setCellValue(locationModel.getAddress());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (locationModel.getArea() != null) {
					row.createCell((short) 3).setCellValue(locationModel.getArea());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (locationModel.getCountryModel() != null) {
					row.createCell((short) 4).setCellValue(locationModel.getCountryModel().getName());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (locationModel.getCityModel() != null) {
					row.createCell((short) 5).setCellValue(locationModel.getCityModel().getName());
				} else {
					row.createCell((short) 5).setCellValue("");
				}
				if (locationModel.getBranchNumber() != null) {
					row.createCell((short) 6).setCellValue(locationModel.getBranchNumber());
				} else {
					row.createCell((short) 6).setCellValue("");
				}

				if (locationModel.getPickupRouteModel() != null) {
					row.createCell((short) 7).setCellValue(locationModel.getPickupRouteModel().getPickupRouteName());
				} else {
					row.createCell((short) 7).setCellValue("");
				}
				if (locationModel.getPositionRoute() != null) {
					row.createCell((short) 8).setCellValue(locationModel.getPositionRoute());
				} else {
					row.createCell((short) 8).setCellValue("");
				}
				if (locationModel.getWorkDuringWeekends() != null) {
					if (locationModel.getWorkDuringWeekends() == true)
						row.createCell((short) 9).setCellValue("Yes");
					else
						row.createCell((short) 9).setCellValue("No");
				} else {
					row.createCell((short) 9).setCellValue("");
				}
				if (locationModel.getPickupEveryday() != null) {
					if (locationModel.getPickupEveryday() == true)
						row.createCell((short) 10).setCellValue("Yes");
					else
						row.createCell((short) 10).setCellValue("No");
				} else {
					row.createCell((short) 10).setCellValue("");
				}
				if (locationModel.getNumberOfGlassTanks() != null) {
					row.createCell((short) 11).setCellValue(locationModel.getNumberOfGlassTanks());
				} else {
					row.createCell((short) 11).setCellValue("");
				}
				if (locationModel.getGlassFullnessPercentageForPickup() != null) {
					row.createCell((short) 12).setCellValue(locationModel.getGlassFullnessPercentageForPickup());
				} else {
					row.createCell((short) 12).setCellValue("");
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
		fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	@Override
	public Response doDropdown() {
		List<LocationModel> locationModels = locationService.findAll();
		if (locationModels == null || locationModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<LocationView> locationViews = new ArrayList<>();
		for (LocationModel locationModel : locationModels) {
			LocationView locationView = new LocationView();
			locationView.setId(locationModel.getId());
			locationView.setName(locationModel.getName());
			locationView.setCityView(
					KeyValueView.create(locationModel.getCityModel().getId(), locationModel.getCityModel().getName()));
			locationViews.add(locationView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				locationViews.size(), locationViews);
	}
}