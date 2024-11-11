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

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.lang3.StringUtils;
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
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.location.model.CountryModel;
import com.intentlabs.common.location.service.CountryService;
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
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.model.UserPasswordModel;
import com.intentlabs.common.user.service.RoleService;
import com.intentlabs.common.user.service.UserPasswordService;
import com.intentlabs.common.user.service.UserService;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.HashUtil;
import com.intentlabs.common.util.Utility;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.service.CustomerService;
import com.intentlabs.endlos.customer.service.LocationService;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;

/**
 * This class used to perform all business operation on customer model.
 * 
 * @author Hemil.Shah
 * @since 18/11/2021
 */
@Component(value = "customerOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class CustomerOperationImpl extends AbstractOperation<CustomerModel, CustomerView> implements CustomerOperation {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FileService fileService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private FileOperation fileOperation;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserPasswordService userPasswordService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService getService() {
		return customerService;
	}

	@Override
	protected CustomerModel getNewModel() {
		return new CustomerModel();
	}

	@Override
	protected CustomerModel loadModel(CustomerView customerView) {
		return customerService.get(customerView.getId());
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(CustomerView customerView) throws EndlosAPIException {
		CustomerModel customerModel = customerService.getByName(customerView.getName());
		if (customerModel != null && customerModel.getName().equals(customerView.getName())) {
			throw new EndlosAPIException(ResponseCode.CUSTOMER_ALREADY_EXIST.getCode(),
					ResponseCode.CUSTOMER_ALREADY_EXIST.getMessage());
		}
		customerModel = toModel(new CustomerModel(), customerView);
		customerModel.setActive(true);
		customerService.create(customerModel);
		UserModel userModel = createSpocAccount(customerView, customerModel);
		sendEmailNotification(userModel, customerModel);
		createCustomerBucket(customerModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	private void createCustomerBucket(CustomerModel customerModel) {
		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Bucket bucket = storage.get(customerModel.getName().toLowerCase() + "-" + activeProfile);
		if (bucket == null) {
			storage.create(BucketInfo.of(customerModel.getName().toLowerCase() + "-" + activeProfile));
		}
	}

	/**
	 * This method is used to create a spoc person's email account.
	 * 
	 * @param customerView
	 * @throws EndlosAPIException
	 */
	private UserModel createSpocAccount(CustomerView customerView, CustomerModel customerModel)
			throws EndlosAPIException {
		if (!StringUtils.isBlank(customerView.getUserView().getEmail())) {
			UserModel userModel = userService.findByEmail(customerView.getUserView().getEmail());
			if (userModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_EMAIL_USER.getCode(),
						ResponseCode.DUPLICATE_EMAIL_USER.getMessage());
			}
		}
		if (!StringUtils.isBlank(customerView.getUserView().getMobile())) {
			UserModel userModel = userService.findByMobile(customerView.getUserView().getMobile());
			if (userModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_MOBILE_USER.getCode(),
						ResponseCode.DUPLICATE_MOBILE_USER.getMessage());
			}
		}
		UserModel userModel = new UserModel();
		userModel.setName(customerView.getUserView().getName());
		userModel.setEmail(customerView.getUserView().getEmail());
		userModel.setActive(true);
		userModel.setArchive(false);
		userModel.setResetPasswordTokenUsed(false);
		userModel.setTwofactorTokenUsed(false);
		userModel.setVerifyTokenUsed(true);
		userModel.setVerifyToken(Utility.generateUuid());
		userModel.setVerificationOtpUsed(true);
		userModel.setVerificationOtp(Utility.generateOTP(6));
		if (customerView.getUserView().getMobile() != null) {
			userModel.setMobile(customerView.getUserView().getMobile());
		}
		userModel.setCustomerAdmin(true);

		Set<RoleModel> roleModels = new HashSet<>();
		roleModels.add(roleService.get(3));
		userModel.setRoleModels(roleModels);
		userModel.getCustomerModels().add(customerModel);
		// userModel.getIndustryModels().add(industryModel);
		String tempPassword = Utility.generateToken(8);
		userModel.setTempPassword(tempPassword);
		userModel.setIsTempPassword(false);
		CountryModel countryModel = countryService.get(87l);
		userModel.setCountryModel(countryModel);
		String token = Utility.generateToken(8);
		userModel.setUniqueToken(token);
		userService.create(userModel);
		userService.insertSearchParam(userModel.getId());

		UserPasswordModel userPasswordModel = new UserPasswordModel();
		userPasswordModel.setUserModel(userModel);
		userPasswordModel.setPassword(HashUtil.hash(userModel.getTempPassword()));
		userPasswordModel.setCreate(Instant.now().getEpochSecond());
		userPasswordService.create(userPasswordModel);

		return userModel;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		CustomerModel customerModel = customerService.get(id);
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		CustomerView customerView = fromModel(customerModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				customerView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(CustomerView customerView) throws EndlosAPIException {
		CustomerModel customerModel = customerService.get(customerView.getId());
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		checkInactive(customerModel);
		customerModel = toModel(customerModel, customerView);
		customerService.update(customerModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(CustomerView customerView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = customerService.search(customerView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		List<CustomerView> customerViews = fromModelList((List<CustomerModel>) pageModel.getList());
		for (CustomerView view : customerViews) {
			List<LocationModel> locationModels = (List<LocationModel>) locationService.getByCustomer(view.getId());
			if (!locationModels.isEmpty()) {
				List<LocationView> locationViews = new ArrayList<>();
				for (LocationModel locationModel : locationModels) {
					LocationView locationView = new LocationView();
					setLocationView(locationView, locationModel);
					locationViews.add(locationView);
				}
				view.setLocationViews(locationViews);
			}
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), customerViews);
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		CustomerModel customerModel = customerService.get(id);
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		customerService.hardDelete(customerModel);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		CustomerModel customerModel = customerService.get(id);
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		Auditor.activationAudit(customerModel, !customerModel.isActive());
		customerService.update(customerModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	protected void checkInactive(CustomerModel customerModel) throws EndlosAPIException {
		if (!customerModel.isActive())
			throw new EndlosAPIException(ResponseCode.UPDATE_INACTIVE_CUSTOMER_RECORD.getCode(),
					ResponseCode.UPDATE_INACTIVE_CUSTOMER_RECORD.getMessage());
	}

	@Override
	public CustomerModel toModel(CustomerModel customerModel, CustomerView customerView) throws EndlosAPIException {
		customerModel.setName(customerView.getName());

		if (customerView.getLogo() == null || StringUtils.isBlank(customerView.getLogo().getFileId())) {
			customerModel.setLogo(null);
			return customerModel;
		}
		if (customerModel.getLogo() == null
				|| !customerView.getLogo().getFileId().equals(customerModel.getLogo().getFileId())) {
			FileModel fileModel = fileService.getByFileId(customerView.getLogo().getFileId());
			if (fileModel == null) {
				throw new EndlosAPIException(ResponseCode.lOGO_IS_INVALID.getCode(),
						ResponseCode.lOGO_IS_INVALID.getMessage());
			}
			List<MachineModel> machineModelList = machineService.getByCustomer(customerModel.getId());
			if (machineModelList != null && !machineModelList.isEmpty()) {
				for (MachineModel machineModel : machineModelList) {
					machineModel.setLogoChanged(true);
					machineService.update(machineModel);
				}
			}
			customerModel.setLogo(fileModel);
		}
		return customerModel;
	}

	@Override
	public CustomerView fromModel(CustomerModel customerModel) {
		CustomerView customerView = new CustomerView();
		customerView.setId(customerModel.getId());
		customerView.setName(customerModel.getName());
		customerView.setActive(customerModel.isActive());
		if (customerModel.getLogo() != null) {
			FileView fileView = new FileView(customerModel.getLogo().getFileId(), customerModel.getLogo().getName(),
					customerModel.getLogo().isPublicfile(), customerModel.getLogo().getCompressName(),
					customerModel.getLogo().getPath());
			customerView.setLogo(fileView);
		}
		setSpocView(customerView, customerModel);
		return customerView;
	}

	/**
	 * This method is used to create industry spoc person view.
	 * 
	 * @param industryView
	 * @param industryModel
	 */
	private void setSpocView(CustomerView customerView, CustomerModel customerModel) {
		UserModel userModel = userService.getCustomerAdmin(customerModel);
		if (userModel != null) {
			UserView userView = new UserView();
			userView.setUserView(userModel);
			customerView.setUserView(userView);
		}

	}

	/**
	 * To send a industry account email
	 * 
	 * @param userModel
	 * @param industryModel
	 */
	private void sendEmailNotification(UserModel userModel, CustomerModel customerModel) {
		Map<String, String> dynamicFields = new TreeMap<>();
		dynamicFields.put(CommunicationFields.EMAIL.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.USER_NAME.getName(), userModel.getName());
		dynamicFields.put(CommunicationFields.PASSWORD.getName(), userModel.getTempPassword());
		dynamicFields.put(CommunicationFields.CUSTOMER.getName(), customerModel.getName());

		NotificationEnum.MASTER_ADMIN_USER_CREATE.sendNotification(
				NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.MASTER_ADMIN_USER_CREATE.getId())),
				dynamicFields);
	}

	@Override
	public Response doDropdown() {
		List<CustomerModel> customertModels = customerService.findAll();
		if (customertModels == null || customertModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		// Sort the list based on the ID in descending order
		Collections.sort(customertModels, Comparator.comparingLong(CustomerModel::getId).reversed());

		List<CustomerView> customerViews = new ArrayList<>();
		for (CustomerModel customerModel : customertModels) {
			CustomerView customerView = new CustomerView();
			customerView.setId(customerModel.getId());
			customerView.setName(customerModel.getName());
			customerViews.add(customerView);
		}

		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				customerViews.size(), customerViews);
	}

	private void setLocationView(LocationView locationView, LocationModel locationModel) {
		locationView.setId(locationModel.getId());
		locationView.setName(locationModel.getName());
		locationView.setBranchNumber(locationModel.getBranchNumber());
	}

	@Override
	public Response doExport(CustomerView customerView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<CustomerModel> customerModels = customerService.doExport(customerView, orderType, orderParam);
		if (customerModels == null || customerModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Customer";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Customer Name");
			rowhead.createCell((short) 2).setCellValue("List Of Branches");
			rowhead.createCell((short) 3).setCellValue("Spoc Person Name");
			rowhead.createCell((short) 4).setCellValue("Email");

			int i = 0;
			for (CustomerModel customerModel : customerModels) {
				List<LocationModel> locationModels = locationService.getByCustomer(customerModel.getId());
				List<String> locationName = new ArrayList<String>();
				for (LocationModel locationModel : locationModels) {
					locationName.add(locationModel.getName());
				}
				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (customerModel.getName() != null) {
					row.createCell((short) 1).setCellValue(customerModel.getName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (!locationName.isEmpty() && locationName != null) {
					String commaseparatedlist = locationName.toString();
					commaseparatedlist = commaseparatedlist.replace("[", "").replace("]", "").replace(" ", "");
					row.createCell((short) 2).setCellValue(commaseparatedlist);
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				UserModel userModel = userService.getCustomerAdmin(customerModel);
				if (userModel != null) {
					row.createCell((short) 3).setCellValue(userModel.getName());
					row.createCell((short) 4).setCellValue(userModel.getEmail());
				} else {
					row.createCell((short) 3).setCellValue("");
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