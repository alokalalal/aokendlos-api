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
package com.intentlabs.common.user.operation;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.JWTExceptionEnum;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.location.service.CityService;
import com.intentlabs.common.location.service.CountryService;
import com.intentlabs.common.location.service.StateService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.JwtTokenModel;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.modelenums.CommonStatusEnum;
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
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.RoleModuleRightsModel;
import com.intentlabs.common.user.model.TokenBlackListModel;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.model.UserPasswordModel;
import com.intentlabs.common.user.model.UserSearchModel;
import com.intentlabs.common.user.model.UserSessionModel;
import com.intentlabs.common.user.service.RoleService;
import com.intentlabs.common.user.service.TokenBlackListService;
import com.intentlabs.common.user.service.UserPasswordService;
import com.intentlabs.common.user.service.UserService;
import com.intentlabs.common.user.service.UserSessionService;
import com.intentlabs.common.user.view.ModuleView;
import com.intentlabs.common.user.view.RightsView;
import com.intentlabs.common.user.view.RoleView;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.util.Constant;
import com.intentlabs.common.util.CookieUtility;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.FileUtility;
import com.intentlabs.common.util.HashUtil;
import com.intentlabs.common.util.HttpUtil;
import com.intentlabs.common.util.JsonUtil;
import com.intentlabs.common.util.JwtUtil;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.util.WebUtil;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.service.CustomerService;
import com.intentlabs.endlos.customer.view.CustomerView;

import io.jsonwebtoken.Claims;

/**
 * This class used to perform all business operation on user model.
 * 
 * @author Jalpa.Gandhi
 * @since 14/12/2018
 */

@Component(value = "userOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserOperationImpl extends AbstractOperation<UserModel, UserView> implements UserOperation {

	@Autowired
	private UserService userService;

	@Autowired
	private UserPasswordService userPasswordService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FileService fileService;

	@Autowired
	private TokenBlackListService tokenBlackListService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private FileOperation fileOperation;

	@Value("${email.url}")
	private String emailUrl;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		UserModel userModel = userService.getLight(id);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!Auditor.getAuditor().equals(userModel)) {
			checkAccess("doView", RightsEnum.VIEW, ModuleEnum.USER);
		}
		UserView userView = fromModel(userModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), userView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		UserModel userModel = userService.getLight(id);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!Auditor.getAuditor().equals(userModel)) {
			checkAccess("doEdit", RightsEnum.UPDATE, ModuleEnum.USER);
		}
		UserView userView = fromModel(userModel);
		if (userModel.getProfilepic() != null) {
			String filePath = userModel.getProfilepic().getPath();
			userView.setFile(FileUtility.convertToString(filePath));
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), userView);
	}

	@Override
	protected UserModel loadModel(UserView userView) {
		return userService.get(userView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		UserModel userModel = userService.get(id);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (userModel.isCustomerAdmin()) {
			throw new EndlosAPIException(ResponseCode.CANT_DELETE_CUSTOMER_ADMIN.getCode(),
					ResponseCode.CANT_DELETE_CUSTOMER_ADMIN.getMessage());
		}
		if (!Auditor.getAuditor().equals(userModel)) {
			checkAccess("doDelete", RightsEnum.DELETE, ModuleEnum.USER);
		} else {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getCode(),
					ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getMessage());
		}
		userModel.setArchive(true);
		userService.delete(userModel);
		return CommonResponse.create(ResponseCode.USER_DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.USER_DELETE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		UserModel userModel = userService.nonVerifiedUser(id);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		if (!Auditor.getAuditor().equals(userModel)) {
			checkAccess("doActivate", RightsEnum.UPDATE, ModuleEnum.USER);
		} else {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getCode(),
					ResponseCode.CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS.getMessage());
		}

		if (userModel.isActive()) {
			Auditor.activationAudit(userModel, false);
		} else {
			Auditor.activationAudit(userModel, true);
		}
		userService.update(userModel);
		if (userModel.isActive())
			return CommonResponse.create(ResponseCode.ACTIVATED_USER_SUCCESSFULLY.getCode(),
					ResponseCode.ACTIVATED_USER_SUCCESSFULLY.getMessage());
		else
			return CommonResponse.create(ResponseCode.DEACTIVATED_USER_SUCCESSFULLY.getCode(),
					ResponseCode.DEACTIVATED_USER_SUCCESSFULLY.getMessage());
	}

	@Override
	public UserModel toModel(UserModel userModel, UserView userView) throws EndlosAPIException {
		userModel.setName(userView.getName());
		userModel.setEmail(userView.getEmail());
		if (userModel.getId() == null || (userModel.getId() != null && userModel.getId().equals(0l))) {
			userModel.setActive(false);
			userModel.setArchive(false);
			userModel.setResetPasswordTokenUsed(false);
			userModel.setTwofactorTokenUsed(false);
			userModel.setVerifyTokenUsed(false);
			userModel.setVerifyToken(Utility.generateUuid());
			userModel.setVerificationOtpUsed(false);
			userModel.setVerificationOtp(Utility.generateOTP(6));
		}
		userModel.setMobile(userView.getMobile());
		if (userView.getCountryView() != null && userView.getCountryView().getKey() != null) {
			userModel.setCountryModel(countryService.load(userView.getCountryView().getKey()));
		}
		if (userView.getStateView() != null && userView.getStateView().getKey() != null) {
			userModel.setStateModel(stateService.load(userView.getStateView().getKey()));
		} else {
			userModel.setStateName(userView.getStateName());
		}
		if (userView.getCityView() != null && userView.getCityView().getKey() != null) {
			userModel.setCityModel(cityService.load(userView.getCityView().getKey()));
		} else {
			userModel.setCityName(userView.getCityName());
		}
		if (!StringUtils.isBlank(userView.getAddress())) {
			userModel.setAddress(userView.getAddress());
		}
		if (!StringUtils.isBlank(userView.getLandmark())) {
			userModel.setLandmark(userView.getLandmark());
		}
		if (!StringUtils.isBlank(userView.getPincode())) {
			userModel.setPincode(userView.getPincode());
		}
		return userModel;
	}

	@Override
	public UserView fromModel(UserModel userModel) {
		UserView userView = new UserView();
		userView.setId(userModel.getId());
		userView.setName(userModel.getName());
		userView.setEmail(userModel.getEmail());
		if (userModel.getMobile() != null) {
			userView.setMobile(userModel.getMobile());
		}
		userView.setHasLoggedIn(userModel.isHasLoggedIn());
		if (userModel.getRoleModels() != null && !userModel.getRoleModels().isEmpty()) {
			List<RoleView> roleViews = new ArrayList<>();
			RoleView roleView = new RoleView();
			roleView.setViewList(userModel.getRoleModels(), roleViews);
			userView.setRoleViews(roleViews);
		}
		if (userModel.getCustomerModels() != null && !userModel.getCustomerModels().isEmpty()) {
			CustomerView customerView = new CustomerView();
			customerView.setId(userModel.getCustomerModels().iterator().next().getId());
			customerView.setName(userModel.getCustomerModels().iterator().next().getName());
			userView.setCustomerView(customerView);
		}
		if (userModel.getProfilepic() != null) {
			FileView fileView = new FileView(userModel.getProfilepic().getFileId(), userModel.getProfilepic().getName(),
					userModel.getProfilepic().isPublicfile(), userModel.getProfilepic().getCompressName(),
					userModel.getProfilepic().getPath());
			userView.setProfilepic(fileView);
		}
		userView.setAddress(userModel.getAddress());
		userView.setPincode(userModel.getPincode());
		userView.setLandmark(userModel.getLandmark());
		if (userModel.getCountryModel() != null) {
			userView.setCountryView(
					KeyValueView.create(userModel.getCountryModel().getId(), userModel.getCountryModel().getName()));
		}
		userView.setStateName(userModel.getStateName());
		if (userModel.getCountryModel() != null) {
			userView.setCountryView(
					KeyValueView.create(userModel.getCountryModel().getId(), userModel.getCountryModel().getName()));
		}
		if (userModel.getStateModel() != null) {
			userView.setStateView(
					KeyValueView.create(userModel.getStateModel().getId(), userModel.getStateModel().getName()));
		}
		userView.setCityName(userModel.getCityName());
		if (userModel.getCityModel() != null) {
			userView.setCityView(
					KeyValueView.create(userModel.getCityModel().getId(), userModel.getCityModel().getName()));
		}
		userView.setActive(userModel.isActive());
		if (userModel.getRequestedCustomerModel() != null) {
			CustomerView customerView = new CustomerView();
			customerView.setName(userModel.getCustomerModels().iterator().next().getName());
			customerView.setId(userModel.getCustomerModels().iterator().next().getId());
			userView.setCustomerView(customerView);
		}
		return userView;
	}

	@Override
	protected UserModel getNewModel() {
		return new UserModel();
	}

	@Override
	public Response doSave(UserView userView) throws EndlosAPIException {
		if (!StringUtils.isBlank(userView.getEmail())) {
			UserModel userModel = userService.findByEmail(userView.getEmail());
			if (userModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_EMAIL_USER.getCode(),
						ResponseCode.DUPLICATE_EMAIL_USER.getMessage());
			}
		}
		if (!StringUtils.isBlank(userView.getMobile())) {
			UserModel userModel = userService.findByMobile(userView.getMobile());
			if (userModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_MOBILE_USER.getCode(),
						ResponseCode.DUPLICATE_MOBILE_USER.getMessage());
			}
		}
		UserModel userModel = toModel(new UserModel(), userView);
		if (userView.getProfilepic() != null) {
			if (userModel.getProfilepic() == null || (userModel.getProfilepic() != null
					&& !userModel.getProfilepic().getFileId().equals(userView.getProfilepic().getFileId()))) {
				FileModel fileModel = fileService.getByFileId(userView.getProfilepic().getFileId());
				if (fileModel == null) {
					throw new EndlosAPIException(ResponseCode.PROFILE_IS_INVALID.getCode(),
							ResponseCode.PROFILE_IS_INVALID.getMessage());
				}
				userModel.setProfilepic(fileModel);
			}
		}
		if (Auditor.getAuditor().getRequestedCustomerModel() != null) {
			setCustomerModel(userModel, null);
		}
		if (userView.getCustomerView() != null && userView.getCustomerView().getId() != null) {
			setCustomerModel(userModel, userView);
		}
		setRoleModel(userModel, userView);
		userModel.setActive(true);
		userModel.setVerifyTokenUsed(true);
		userModel.setVerificationOtpUsed(true);
		String token = Utility.generateToken(8);
		userModel.setUniqueToken(token);
		String tempPassword = Utility.generateToken(8);
		userView.setPassword(tempPassword);
		userModel.setIsTempPassword(false);
		userService.create(userModel);
		userService.insertSearchParam(userModel.getId());
		setPassword(userModel, userView);
		sendUserCreateEmail(userModel, userView);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doUpdate(UserView userView) throws EndlosAPIException {
		UserModel userModel = userService.get(userView.getId());
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (!Auditor.getAuditor().equals(userModel)) {
			checkAccess("doUpdate", RightsEnum.UPDATE, ModuleEnum.USER);
		}
		if (!userModel.getEmail().equals(userView.getEmail())) {
			UserModel emailCheckModel = userService.findByEmail(userView.getEmail());
			if (emailCheckModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_EMAIL_USER.getCode(),
						ResponseCode.DUPLICATE_EMAIL_USER.getMessage());
			}
		}
		if (userModel.getMobile() == null || !userModel.getMobile().equals(userView.getMobile())) {
			UserModel mobileCheckModel = userService.findByMobile(userView.getMobile());
			if (mobileCheckModel != null) {
				throw new EndlosAPIException(ResponseCode.DUPLICATE_MOBILE_USER.getCode(),
						ResponseCode.DUPLICATE_MOBILE_USER.getMessage());
			}
		}
		userModel = toModel(userModel, userView);
		if (!Auditor.getAuditor().equals(userModel)) {
			setRoleModel(userModel, userView);
		}
		if (userView.getProfilepic() != null) {
			if (userModel.getProfilepic() == null || (userModel.getProfilepic() != null
					&& !userModel.getProfilepic().getFileId().equals(userView.getProfilepic().getFileId()))) {
				FileModel fileModel = fileService.getByFileId(userView.getProfilepic().getFileId());
				if (fileModel == null) {
					throw new EndlosAPIException(ResponseCode.PROFILE_IS_INVALID.getCode(),
							ResponseCode.PROFILE_IS_INVALID.getMessage());
				}
				userModel.setProfilepic(fileModel);
			}
		} else {
			userModel.setProfilepic(null);
		}
		// if (!Auditor.getAuditor().equals(userModel)) {
		// if (userView.getRoleViews() != null &&
		// !userView.getRoleViews().isEmpty()) {
		// setRoleModel(userModel, userView);
		// }
		// }
		userService.update(userModel);
		userService.updateSearchParam(userModel.getId());
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public BaseService<UserModel> getService() {
		return userService;
	}

	@Override
	protected void checkInactive(UserModel model) throws EndlosAPIException {
	}

	@Override
	public Response doSearch(UserView userView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		List<UserSearchModel> userSearchModels = new ArrayList<UserSearchModel>();
		if (!StringUtils.isBlank(userView.getFullTextSearch())) {
			userSearchModels = userService.fullTextSearch(userView.getFullTextSearch());
			if (userSearchModels == null || (userSearchModels != null && userSearchModels.isEmpty())) {
				return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
						ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.emptyList());
			}
		}
		PageModel pageModel = userService.searchLight(userView, userSearchModels, start, recordSize, orderType,
				orderParam);
		if (pageModel == null || (pageModel.getList() != null && pageModel.getList().isEmpty())) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.emptyList());
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<UserModel>) pageModel.getList()));
	}

	@Override
	public Response doLogin(UserView userView, boolean isLoginThroughEmail) throws EndlosAPIException {
		UserModel userModel = validateUser(userView, isLoginThroughEmail);
		UserPasswordModel userPasswordModel = userPasswordService.getCurrent(userModel.getId());
		if (userPasswordModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_LOGINID_OR_PASSWORD.getCode(),
					ResponseCode.INVALID_LOGINID_OR_PASSWORD.getMessage());
		}
		if (!HashUtil.matchHash(userView.getPassword(), userPasswordModel.getPassword())) {
			throw new EndlosAPIException(ResponseCode.INVALID_LOGINID_OR_PASSWORD.getCode(),
					ResponseCode.INVALID_LOGINID_OR_PASSWORD.getMessage());
		}
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getDefaultPasswordChangeRequired())) {
			if (userModel.getIsTempPassword()) {
				JwtTokenModel jwtTokenModel = JwtTokenModel.createFirstLoginToken();
				jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
				return CommonResponse.create(ResponseCode.TEMP_PASSWORD_SESSION.getCode(),
						ResponseCode.TEMP_PASSWORD_SESSION.getMessage(), JwtUtil.generateAccessToken(
								userModel.getEmail(), JsonUtil.toJson(jwtTokenModel), jwtTokenModel),
						null);
			}
		}
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getVerificationThroughOTP())) {
			if (userModel.isVerificationOtpUsed()) {
				if (!userModel.isActive()) {
					throw new EndlosAPIException(ResponseCode.INACTIVE_USER.getCode(),
							ResponseCode.INACTIVE_USER.getMessage());
				}
			} else {
				JwtTokenModel jwtTokenModel = JwtTokenModel.createActivationToken();
				jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
				return CommonResponse.create(ResponseCode.EMAIL_VERIFICATION.getCode(),
						ResponseCode.EMAIL_VERIFICATION.getMessage(), JwtUtil.generateAccessToken(userModel.getEmail(),
								JsonUtil.toJson(jwtTokenModel), jwtTokenModel),
						null);
			}
		}
		boolean isDeviceAuthenticationRequired = setDeviceToken(userModel);
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getTwoFactorAuthenticationEnable())
				&& isDeviceAuthenticationRequired) {
			userModel.setTwofactorTokenUsed(false);
			userModel.setTwofactorToken(Utility.generateOTP(6));
			userModel.setTwofactorDate(DateUtility.getCurrentEpoch());
			userService.update(userModel);
			sendVerificationCode(userModel);
			JwtTokenModel jwtTokenModel = JwtTokenModel.createTwoFactorToken();
			jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
			userService.update(userModel);
			return CommonResponse.create(ResponseCode.VALIDATE_NEW_DEVICE.getCode(),
					ResponseCode.VALIDATE_NEW_DEVICE.getMessage(),
					JwtUtil.generateAccessToken(userModel.getEmail(), JsonUtil.toJson(jwtTokenModel), jwtTokenModel),
					null);
		}
		boolean isFirstLoggedIn = false;
		if (!userModel.isHasLoggedIn()) {
			isFirstLoggedIn = true;
			userModel.setHasLoggedIn(true);
			userService.update(userModel);
		}
		UserView responseView = fromModel(userModel);
		responseView.setModuleViews(setModules(userModel));
		ViewResponse viewResponse = ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(),
				ResponseCode.SUCCESSFUL.getMessage(), responseView);

		JwtTokenModel jwtTokenModel = JwtTokenModel.createLoginToken();
		jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
		viewResponse.setAccessToken(
				JwtUtil.generateAccessToken(userModel.getEmail(), JsonUtil.toJson(jwtTokenModel), jwtTokenModel));
		viewResponse
				.setRefreshToken(JwtUtil.generateRefreshToken(userModel.getEmail(), JsonUtil.toJson(jwtTokenModel)));
		return viewResponse;
	}

	@Override
	public Response doLogout(String jwtAccessToken) throws EndlosAPIException {
		Claims claims = null;
		try {
			claims = JwtUtil.extractAllClaims(jwtAccessToken);
		} catch (EndlosAPIException e) {
			LoggerService.exception(e);
		}
		String userEmail = JwtUtil.extractData(jwtAccessToken, claims);
		UserModel userModel = userService.findByEmail(userEmail);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		TokenBlackListModel tokenBlackListModel = new TokenBlackListModel();
		tokenBlackListModel.setUserModel(userModel);
		tokenBlackListModel.setJwtToken(jwtAccessToken);
		tokenBlackListService.create(tokenBlackListModel);
		return CommonResponse.create(ResponseCode.LOGGED_OUT_SUCCESSFUL.getCode(),
				ResponseCode.LOGGED_OUT_SUCCESSFUL.getMessage());
	}

	private void setPassword(UserModel userModel, UserView userView) throws EndlosAPIException {
		UserPasswordModel userPasswordModel = new UserPasswordModel();
		userPasswordModel.setUserModel(userModel);
		userPasswordModel.setPassword(HashUtil.hash(userView.getPassword()));
		userPasswordModel.setCreate(Instant.now().getEpochSecond());
		userPasswordService.create(userPasswordModel);
	}

	@Override
	public Response doActivate(String token) throws EndlosAPIException {
		UserModel userModel = userService.findByToken(token);
		if (userModel == null) {
			return CommonResponse.create(ResponseCode.INVALID_TOKEN.getCode(), ResponseCode.INVALID_TOKEN.getMessage());
		}
		if (userModel.isArchive()) {
			return CommonResponse.create(ResponseCode.INVALID_TOKEN.getCode(), ResponseCode.INVALID_TOKEN.getMessage());
		}
		if (userModel.isVerifyTokenUsed()) {
			return CommonResponse.create(ResponseCode.LINK_EXPIRED.getCode(), ResponseCode.LINK_EXPIRED.getMessage());
		}
		userModel.setVerifyTokenUsed(true);
		userModel.setVerificationOtpUsed(true);
		userModel.setActive(true);
		userModel.setActivationDate(DateUtility.getCurrentEpoch());
		userService.update(userModel);
		return CommonResponse.create(ResponseCode.ACTIVATION_SUCCESSFUL.getCode(),
				ResponseCode.ACTIVATION_SUCCESSFUL.getMessage());
	}

	@Override
	public Response doSendResetLink(UserView userView, boolean isLoginThroughEmail) throws EndlosAPIException {
		UserModel userModel = validateUser(userView, isLoginThroughEmail);
		if (!userModel.isActive()) {
			if (!userModel.isVerificationOtpUsed()) {
				throw new EndlosAPIException(ResponseCode.EMAIL_VERIFICATION.getCode(),
						ResponseCode.EMAIL_VERIFICATION.getMessage());
			} else {
				throw new EndlosAPIException(ResponseCode.INACTIVE_USER.getCode(),
						ResponseCode.INACTIVE_USER.getMessage());
			}
		}
		userModel.setResetPasswordToken(Utility.generateUuid());
		userModel.setResetPasswordTokenUsed(false);
		userModel.setResetPasswordDate(DateUtility.getCurrentEpoch());
		userService.update(userModel);

		Map<String, String> dynamicFields = new TreeMap<>();
		dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.USER_NAME.getName(), userModel.getName());
		dynamicFields.put(CommunicationFields.RESET_PASSWORD_TOKEN.getName(), userModel.getResetPasswordToken());
		//dynamicFields.put(CommunicationFields.RESET_PASSWORD_LINK.getName(),SystemSettingModel.getUrl() + File.separator + "user" + File.separator + "reset-password"+ File.separator + userModel.getResetPasswordToken());

		dynamicFields.put(CommunicationFields.RESET_PASSWORD_LINK.getName(), emailUrl + "/user/reset-password/" + userModel.getResetPasswordToken());


		dynamicFields.put(CommunicationFields.YEAR.getName(), String.valueOf(DateUtility.getCurrentYear()));
		NotificationEnum.USER_RESET_PASSWORD.sendNotification(NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.USER_RESET_PASSWORD.getId())), dynamicFields);
		return CommonResponse.create(ResponseCode.FORGET_PASSWORD_SUCCESSFUL.getCode(),
				ResponseCode.FORGET_PASSWORD_SUCCESSFUL.getMessage());
	}

	private String getResetPasswordJWTToken(UserModel userModel) throws EndlosAPIException {
		JwtTokenModel jwtTokenModel = JwtTokenModel.createResetPasswordToken();
		jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
		String jwtToken = JwtUtil.generateAccessToken(userModel.getEmail(), JsonUtil.toJson(jwtTokenModel),
				jwtTokenModel);
		return jwtToken;
	}

	@Override
	public Response doResetPasswordVerification(String token) throws EndlosAPIException {
		UserModel userModel = userService.findByResetPasswordToken(token);
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_TOKEN.getCode(), ResponseCode.INVALID_TOKEN.getMessage());
		}
		if (userModel.isArchive() || !userModel.isActive() || userModel.isResetPasswordTokenUsed()) {
			throw new EndlosAPIException(ResponseCode.EXPIRED_TOKEN.getCode(), ResponseCode.EXPIRED_TOKEN.getMessage());
		}
		if (DateUtility.isResetPasswordValidMinutes(userModel.getResetPasswordDate(),
				Long.valueOf(SystemSettingModel.getResetPasswordTokenValidMinutes()))) {
			throw new EndlosAPIException(ResponseCode.EXPIRED_TOKEN.getCode(), ResponseCode.EXPIRED_TOKEN.getMessage());
		}
		userModel.setResetPasswordTokenUsed(true);
		String uniqueToken = Utility.generateToken(8);
		userModel.setUniqueToken(uniqueToken);
		userService.update(userModel);
		CommonResponse commonResponse = CommonResponse.create(
				ResponseCode.FORGET_PASSWORD_VERIFICATION_SUCCESSFUL.getCode(),
				ResponseCode.FORGET_PASSWORD_VERIFICATION_SUCCESSFUL.getMessage());
		commonResponse.setAccessToken(getResetPasswordJWTToken(userModel));
		return commonResponse;
	}

	@Override
	public Response doResetPassword(UserView userView) throws EndlosAPIException {
		// UserSessionModel userSessionModel =
		// validateResetPasswordSession(true);
		String jwtAccesssTokenHeader = WebUtil.getCurrentRequest().getHeader("Authorization");
		String accessToken[] = jwtAccesssTokenHeader.split(" ");
		String userEmail = JwtUtil.extractData(accessToken[1], JwtUtil.extractAllClaims(accessToken[1]));
		UserModel userModel = userService.findByEmail(userEmail);

		String newPassword = HashUtil.hash(userView.getPassword());
		List<UserPasswordModel> userPasswordModels = validateLastUsedPasswords(userModel, userView, false);
		updateUserPassword(userModel, userView, userPasswordModels, newPassword);
		TokenBlackListModel tokenBlackListModel = new TokenBlackListModel();
		tokenBlackListModel.setUserModel(userModel);
		tokenBlackListModel.setJwtToken(accessToken[1]);
		tokenBlackListService.create(tokenBlackListModel);
		return CommonResponse.create(ResponseCode.RESET_PASSWORD_SUCCESSFUL.getCode(),
				ResponseCode.RESET_PASSWORD_SUCCESSFUL.getMessage());
	}

	@Override
	public Response doChangePassword(UserView userView) throws EndlosAPIException {
		UserModel userModel = Auditor.getAuditor();
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		String jwtAccesssTokenHeader = WebUtil.getCurrentRequest().getHeader("Authorization");
		String accessToken[] = jwtAccesssTokenHeader.split(" ");
		String userEmail = JwtUtil.extractData(accessToken[1], JwtUtil.extractAllClaims(accessToken[1]));
		UserModel existUserModel = userService.findByEmail(userEmail);

		if (!existUserModel.equals(userModel)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		String newPassword = HashUtil.hash(userView.getPassword());
		List<UserPasswordModel> userPasswordModels = validateLastUsedPasswords(existUserModel, userView, true);
		updateUserPassword(existUserModel, userView, userPasswordModels, newPassword);
		if (userModel.getIsTempPassword()) {
			userModel = userService.load(userModel.getId());
			userModel.setIsTempPassword(false);
//			userService.update(userModel);
		}
		String token = Utility.generateToken(8);
		existUserModel.setUniqueToken(token);
		userService.update(existUserModel);
		TokenBlackListModel tokenBlackListModel = new TokenBlackListModel();
		tokenBlackListModel.setUserModel(userModel);
		tokenBlackListModel.setJwtToken(accessToken[1]);
		tokenBlackListService.create(tokenBlackListModel);
		return CommonResponse.create(ResponseCode.CHANGE_PASSWORD_SUCCESSFUL.getCode(),
				ResponseCode.CHANGE_PASSWORD_SUCCESSFUL.getMessage());
	}

	/**
	 * this method will set registration JWT token in header.
	 * 
	 * @param userModel
	 * @throws EndlosAPIException
	 */
	private String getRegisterJWTToken(UserModel userModel) throws EndlosAPIException {
		JwtTokenModel jwtTokenModel = JwtTokenModel.createRegistrationToken();
		jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
		String jwtToken = JwtUtil.generateAccessToken(userModel.getEmail(), JsonUtil.toJson(jwtTokenModel),
				jwtTokenModel);
		return jwtToken;
	}

	private void updateUserPassword(UserModel userModel, UserView userView, List<UserPasswordModel> userPasswordModels,
			String newPassword) throws EndlosAPIException {
		if (!userPasswordModels.isEmpty()
				&& userPasswordModels.size() >= Integer.valueOf(SystemSettingModel.getMaxPasswordStoreCountPerUser())) {
			UserPasswordModel userPasswordModel = userPasswordModels.get(userPasswordModels.size() - 1);
			userPasswordModel.setCreate(Instant.now().getEpochSecond());
			userPasswordModel.setPassword(newPassword);
			userPasswordService.update(userPasswordModel);
			return;
		}
		UserPasswordModel userPasswordModel = new UserPasswordModel();
		userPasswordModel.setUserModel(userModel);
		userPasswordModel.setPassword(newPassword);
		userPasswordModel.setCreate(Instant.now().getEpochSecond());
		userPasswordService.create(userPasswordModel);
	}

	/**
	 * This method is used to validate a user password.
	 * 
	 * @param userView
	 * @param userModel
	 * @throws EndlosAPIException
	 */
	private UserModel validateUser(UserView userView, boolean isLoginThroughEmail) throws EndlosAPIException {
		UserModel userModel = null;
		if (isLoginThroughEmail) {
			userModel = userService.findByEmail(userView.getLoginId());
		} else {
			userModel = userService.findByMobile(userView.getLoginId());
		}
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_LOGINID_OR_PASSWORD.getCode(),
					ResponseCode.INVALID_LOGINID_OR_PASSWORD.getMessage());
		}
		if (userModel.isArchive()) {
			throw new EndlosAPIException(ResponseCode.DELETED_USER.getCode(), ResponseCode.DELETED_USER.getMessage());
		}
		return userModel;
	}

	/**
	 * This method is used to set device token against user id to verify the device
	 * base login.
	 * 
	 * @param userModel
	 * @return
	 * @throws EndlosAPIException
	 */
	private boolean setDeviceToken(UserModel userModel) throws EndlosAPIException {
		Long currentDate = DateUtility.getCurrentEpoch();
		String deviceCookie = null;
		Long maxAllowDevice = Long.valueOf(SystemSettingModel.getMaxAllowedDevice());
		Integer deviceCookieTime = Integer.valueOf(SystemSettingModel.getDeviceCookieTimeInSeconds());

		Cookie cookies[] = WebUtil.getCurrentRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constant.DEVICE_TOKEN.equals(cookie.getName())) {
					deviceCookie = cookie.getValue();
				}
			}
		}
		Long deviceCount = userSessionService.deviceUsed(userModel.getId());
		boolean isDeviceRegistered = userSessionService.isDeviceRegistered(deviceCookie);
		if (isDeviceRegistered) {
			UserSessionModel deviceUserSessionModel = userSessionService.getByDeviceCookie(deviceCookie,
					userModel.getId());
			if (deviceUserSessionModel == null) {
				deviceUserSessionModel = new UserSessionModel(HttpUtil.getUserBrowser(), HttpUtil.getUserOs(),
						HttpUtil.getHotelPartnerIpAddress(), deviceCookie, currentDate, currentDate, userModel);
				userSessionService.create(deviceUserSessionModel);
				CookieUtility.setCookie(WebUtil.getCurrentResponse(), Constant.DEVICE_TOKEN,
						deviceUserSessionModel.getDeviceCookie(), deviceCookieTime,
						WebUtil.getCurrentRequest().getContextPath());
				if (deviceCount.equals(new Long(0))) {
					return false;
				} else {
					return true;
				}
			} else {
				deviceUserSessionModel.setLastLoginDate(currentDate);
				userSessionService.update(deviceUserSessionModel);
				return false;
			}
		} else {
			if (maxAllowDevice <= deviceCount) {
				userSessionService.deleteLeastUnused(userModel.getId());
			}
			UserSessionModel userSessionModel = new UserSessionModel(HttpUtil.getUserBrowser(), HttpUtil.getUserOs(),
					HttpUtil.getHotelPartnerIpAddress(), HashUtil.generateDeviceToken(), currentDate, currentDate,
					userModel);
			userSessionService.create(userSessionModel);
			CookieUtility.setCookie(WebUtil.getCurrentResponse(), Constant.DEVICE_TOKEN,
					userSessionModel.getDeviceCookie(), deviceCookieTime, WebUtil.getCurrentRequest().getContextPath());
			if (deviceCount.equals(new Long(0))) {
				return false;
			} else {
				return true;
			}
		}
	}

	private List<UserPasswordModel> validateLastUsedPasswords(UserModel userModel, UserView userView,
			boolean isChangePwd) throws EndlosAPIException {
		Integer count = Integer.valueOf(SystemSettingModel.getMaxPasswordStoreCountPerUser());
		List<UserPasswordModel> userPasswordModels = userPasswordService.getByUser(userModel.getId(), count);
		UserPasswordModel userPasswordModelTemp = userPasswordModels.get(0);
		if (isChangePwd && userPasswordModelTemp != null
				&& !HashUtil.matchHash(userView.getOldPassword(), userPasswordModelTemp.getPassword())) {
			throw new EndlosAPIException(ResponseCode.CURRENT_PASSWORD_IS_INVALID.getCode(),
					ResponseCode.CURRENT_PASSWORD_IS_INVALID.getMessage());
		}
		for (UserPasswordModel userPasswordModel : userPasswordModels) {
			if (CommonStatusEnum.YES.equals(SystemSettingModel.getPasswordUsedValidationEnabled())) {
				if (HashUtil.matchHash(userView.getPassword(), userPasswordModel.getPassword())) {
					throw new EndlosAPIException(ResponseCode.DUPLICATE_PASSWORD_USER.getCode(),
							ResponseCode.DUPLICATE_PASSWORD_USER.getMessage());
				}
			}
		}
		return userPasswordModels;
	}

	@Override
	public Response doDisplayGrid(Integer start, Integer recordSize) {
		return null;
	}

	@Override
	public Response doIsLoggedIn() throws EndlosAPIException {
		UserView userView = fromModel(Auditor.getAuditor());
		userView.setModuleViews(setModules(Auditor.getAuditor()));
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), userView);
	}

	private List<ModuleView> setModules(UserModel userModel) {
		List<Long> ids = new ArrayList<>();
		for (RoleModel roleModel : userModel.getRoleModels()) {
			ids.add(roleModel.getId());
		}
		List<RoleModel> roleModels = roleService.getRights(ids);
		Map<ModuleEnum, List<RightsEnum>> map = new HashMap<>();
		for (RoleModel roleModel : roleModels) {
			for (RoleModuleRightsModel roleModuleRightsModel : roleModel.getRoleModuleRightsModels()) {
				if (map.get(ModuleEnum.fromId(roleModuleRightsModel.getModuleId().intValue())) != null) {
					if (!map.get(ModuleEnum.fromId(roleModuleRightsModel.getModuleId().intValue()))
							.contains(RightsEnum.fromId(roleModuleRightsModel.getRightsId().intValue()))) {
						map.get(ModuleEnum.fromId(roleModuleRightsModel.getModuleId().intValue()))
								.add(RightsEnum.fromId(roleModuleRightsModel.getRightsId().intValue()));
					}
				} else {
					List<RightsEnum> rightsEnums = new ArrayList<>();
					rightsEnums.add(RightsEnum.fromId(roleModuleRightsModel.getRightsId().intValue()));
					map.put(ModuleEnum.fromId(roleModuleRightsModel.getModuleId().intValue()), rightsEnums);
				}
			}
		}
		List<ModuleView> moduleViews = new ArrayList<>();
		for (ModuleEnum moduleEnum : map.keySet()) {
			ModuleView moduleView = new ModuleView();
			moduleView.setId(moduleEnum.getId());
			moduleView.setName(moduleEnum.getName());
			moduleView.setRightsViews(new ArrayList<>());
			for (RightsEnum rightsEnum : map.get(moduleEnum)) {
				RightsView rightsView = new RightsView();
				rightsView.setId(rightsEnum.getId());
				rightsView.setName(rightsEnum.getName());
				moduleView.getRightsViews().add(rightsView);
			}
			moduleViews.add(moduleView);
		}
		return moduleViews;
	}

	@Override
	public Response doActivateThroughOTP(UserView userView) throws EndlosAPIException {
		UserModel userModel = userService.getLight(Auditor.getAuditor().getId());
		if (userModel.isArchive()) {
			return CommonResponse.create(ResponseCode.DELETED_USER.getCode(), ResponseCode.DELETED_USER.getMessage());
		}
		if (userModel.isVerificationOtpUsed()) {
			return CommonResponse.create(ResponseCode.ACCOUNT_ALREADY_VERIFIED_THROUGH_OTP.getCode(),
					ResponseCode.ACCOUNT_ALREADY_VERIFIED_THROUGH_OTP.getMessage());
		}
		if (!userModel.getVerificationOtp().equals(userView.getVerificaitionOtp())) {
			return CommonResponse.create(ResponseCode.INVALID_OTP.getCode(), ResponseCode.INVALID_OTP.getMessage());
		}
		userModel.setVerifyTokenUsed(true);
		userModel.setVerificationOtpUsed(true);
		userModel.setActive(true);
		userModel.setActivationDate(DateUtility.getCurrentEpoch());
		userModel.setHasLoggedIn(true);
		userService.update(userModel);
		return CommonResponse.create(ResponseCode.ACTIVATION_SUCCESSFUL.getCode(),
				ResponseCode.ACTIVATION_SUCCESSFUL.getMessage());
	}

	@Override
	public Response doResentActivationOTP() throws EndlosAPIException {
		UserModel userModel = userService.load(Auditor.getAuditor().getId());
		if (userModel.isArchive()) {
			return CommonResponse.create(ResponseCode.DELETED_USER.getCode(), ResponseCode.DELETED_USER.getMessage());
		}
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getTwoFactorAuthenticationEnable())) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_REQUIRED.getCode(),
					ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
		}
		userModel.setVerifyTokenUsed(false);
		userModel.setVerifyToken(Utility.generateUuid());
		userModel.setVerificationOtpUsed(false);
		userModel.setVerificationOtp(Utility.generateOTP(6));
		userService.update(userModel);
		sendVerificationCode(userModel);
		return CommonResponse.create(ResponseCode.OTP_MAIL_SENT_SUCCESSFULLY.getCode(),
				ResponseCode.OTP_MAIL_SENT_SUCCESSFULLY.getMessage());
	}

	private void sendVerificationCode(UserModel userModel) {
		Map<String, String> dynamicFields = new TreeMap<>();
		dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.USER_NAME.getName(), userModel.getName());
		dynamicFields.put(CommunicationFields.VERIFICATION_CODE.getName(), userModel.getVerificationOtp());
		dynamicFields.put(CommunicationFields.YEAR.getName(), String.valueOf(DateUtility.getCurrentYear()));
		NotificationEnum.RESEND_VERIFICATION_CODE.sendNotification(
				NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.RESEND_VERIFICATION_CODE.getId())),
				dynamicFields);
	}

	@Override
	public Response doAccessedByDevice(String deviceCookie, String domain) {
		List<UserView> userViews = new ArrayList<>();
		List<UserSessionModel> userSessionModels = userSessionService.getUserByDomainAndDevice(deviceCookie, null);
		for (UserSessionModel userSessionModel : userSessionModels) {
			UserModel userModel = userSessionModel.getUserModel();
			if (userModel != null) {
				UserView userView = fromModel(userModel);
				userViews.add(userView);
			}
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				userViews.size(), userViews);
	}

	@Override
	public Response doGetAccessToken(UserView userView) throws EndlosAPIException {
		JWTExceptionEnum jwtRefreshPair = JwtUtil.isValidJWTToken(userView.getRefreshToken());
		if (jwtRefreshPair == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (jwtRefreshPair.equals(JWTExceptionEnum.SIGNAUTURE_EXCEPTION)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REFRESH_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_REFRESH_JSON_TOKEN.getMessage());
		}
		if (jwtRefreshPair.equals(JWTExceptionEnum.EXPIRED_JWT_EXCEPTION)) {
			throw new EndlosAPIException(ResponseCode.AUTHENTICATION_REQUIRED.getCode(),
					ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
		}
		Claims claims = JwtUtil.extractAllClaims(userView.getRefreshToken());
		String userEmail = JwtUtil.extractData(userView.getRefreshToken(), claims);
		UserModel userModel = userService.findByEmail(userEmail);
		JwtTokenModel jwtTokenModel = JwtTokenModel.createLoginToken();
		jwtTokenModel.setUniqueToken(userModel.getUniqueToken());
		CommonResponse commonResponse = CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(),
				ResponseCode.SUCCESSFUL.getMessage());
		commonResponse.setAccessToken(JwtUtil.generateAccessToken(userEmail, JsonUtil.toJson(jwtTokenModel), null));
		setNewRefreshToken(userView, commonResponse, userEmail, jwtTokenModel);
		return commonResponse;
	}

	private void setNewRefreshToken(UserView userView, CommonResponse commonResponse, String userEmail,
			JwtTokenModel jwtTokenModel) throws EndlosAPIException {
		Date refreshExpiredDate = JwtUtil.extractExpiration(userView.getRefreshToken(), null);
		Duration duration = Duration.between(LocalDateTime.now(),
				refreshExpiredDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		if (duration.toMinutes() < SystemSettingModel.getGenerateRefreshTokenTimeInMinutes()) {
			commonResponse.setRefreshToken(JwtUtil.generateRefreshToken(userEmail, JsonUtil.toJson(jwtTokenModel)));
		}
	}

	private void setCustomerModel(UserModel userModel, UserView userView) {
		Set<CustomerModel> existCustomerModels = new HashSet<>();
		Set<CustomerModel> toDeleteCustomerModels = new HashSet<>();
		Set<CustomerModel> toAddCustomerModels = new HashSet<>();
		CustomerModel tempCustomerModel;

		List<CustomerView> customerViews = new ArrayList<>();
		CustomerView tempCustomerView = new CustomerView();
		if (userView != null) {
			tempCustomerView.setId(userView.getCustomerView().getId());
		} else {
			tempCustomerView.setId(Auditor.getAuditor().getRequestedCustomerModel().getId());
		}
		customerViews.add(tempCustomerView);

		for (CustomerView customerView : customerViews) {
			tempCustomerModel = customerService.get(customerView.getId());

			if (userModel.getCustomerModels().contains(tempCustomerModel) == false) {
				toAddCustomerModels.add(tempCustomerModel);
			} else {
				existCustomerModels.add(tempCustomerModel);
			}
		}

		for (CustomerModel customerModel : userModel.getCustomerModels()) {
			if (existCustomerModels.contains(customerModel) == false) {
				toDeleteCustomerModels.add(customerModel);
			}
		}

		for (CustomerModel customerModel : toDeleteCustomerModels) {
			userModel.removeCustomerModel(customerModel);
		}
		for (CustomerModel customerModel : toAddCustomerModels) {
			userModel.addCustomerModel(customerModel);
		}
	}

	/**
	 * This method is used to prepare master user create email template and sent it
	 * to.
	 * 
	 * @param userModel
	 */
	private void sendUserCreateEmail(UserModel userModel, UserView userView) {
		Map<String, String> dynamicFields = new TreeMap<>();
		dynamicFields.put(CommunicationFields.EMAIL_TO.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.EMAIL.getName(), userModel.getEmail());
		dynamicFields.put(CommunicationFields.USER_NAME.getName(), userModel.getName());
		dynamicFields.put(CommunicationFields.PASSWORD.getName(), userView.getPassword());
		dynamicFields.put(CommunicationFields.ADMIN_URL.getName(), emailUrl);

		if (userModel.getRoleModels() != null && !userModel.getRoleModels().isEmpty()) {
			List<RoleView> roleViews = new ArrayList<>();
			RoleView roleView = new RoleView();
			roleView.setViewList(userModel.getRoleModels(), roleViews);
			userView.setRoleViews(roleViews);
			if(userView.getRoleViews().size() < 0) {
				dynamicFields.put(CommunicationFields.USER_ROLE.getName(), userView.getRoleViews().get(0).getName());
			}
			else {
				String roles = "";
				for (RoleView view : userView.getRoleViews()) {
					roles  += view.getName() + ", ";
				}
				dynamicFields.put(CommunicationFields.USER_ROLE.getName(), roles);
			}

		}

		NotificationEnum.MASTER_ADMIN_USER_CREATE.sendNotification(
				NotificationModel.getMAP().get(Long.valueOf(NotificationEnum.MASTER_ADMIN_USER_CREATE.getId())),
				dynamicFields);
	}

	@Override
	public Response doFirstTimePasswordChange(UserView userView) throws EndlosAPIException {
		UserModel userModel = Auditor.getAuditor();
		if (userModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		String jwtAccesssTokenHeader = WebUtil.getCurrentRequest().getHeader("Authorization");
		String accessToken[] = jwtAccesssTokenHeader.split(" ");
		String userEmail = JwtUtil.extractData(accessToken[1], JwtUtil.extractAllClaims(accessToken[1]));
		UserModel existUserModel = userService.findByEmail(userEmail);

		if (!existUserModel.equals(userModel)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		String newPassword = HashUtil.hash(userView.getPassword());
		List<UserPasswordModel> userPasswordModels = validateLastUsedPasswords(existUserModel, userView, true);
		updateUserPassword(existUserModel, userView, userPasswordModels, newPassword);
		if (userModel.getIsTempPassword()) {
			userModel = userService.load(userModel.getId());
			userModel.setIsTempPassword(false);
		}
		String token = Utility.generateToken(8);
		userModel.setUniqueToken(token);
		userService.update(userModel);
		TokenBlackListModel tokenBlackListModel = new TokenBlackListModel();
		tokenBlackListModel.setUserModel(userModel);
		tokenBlackListModel.setJwtToken(accessToken[1]);
		tokenBlackListService.create(tokenBlackListModel);
		return CommonResponse.create(ResponseCode.CHANGE_PASSWORD_SUCCESSFUL.getCode(),
				ResponseCode.CHANGE_PASSWORD_SUCCESSFUL.getMessage());
	}

	private void setRoleModel(UserModel userModel, UserView userView) throws EndlosAPIException {
		Set<RoleModel> existRoleModels = new HashSet<>();
		Set<RoleModel> toDeleteRoleModels = new HashSet<>();
		Set<RoleModel> toAddRoleModels = new HashSet<>();
		for (RoleView roleView : userView.getRoleViews()) {
			RoleModel tempRoleModel = roleService.getExtraLight(roleView.getId());
			if (tempRoleModel == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_ROLE.getCode(),
						ResponseCode.INVALID_ROLE.getMessage());
			}
			if (userModel.getRoleModels().contains(tempRoleModel) == false) {
				toAddRoleModels.add(tempRoleModel);
			} else {
				existRoleModels.add(tempRoleModel);
			}
		}
		for (RoleModel roleModel : userModel.getRoleModels()) {
			if (existRoleModels.contains(roleModel) == false) {
				toDeleteRoleModels.add(roleModel);
			}
		}
		for (RoleModel roleModel : toDeleteRoleModels) {
			userModel.removeRoleModel(roleModel);
		}
		for (RoleModel roleModel : toAddRoleModels) {
			userModel.addRoleModel(roleModel);
		}
	}

	@Override
	public Response doExport(UserView userView, Integer orderType, Integer orderParam) throws EndlosAPIException {
		List<UserModel> userModels = userService.doExport(userView, orderType, orderParam);
		if (userModels == null || userModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "User";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Name");
			rowhead.createCell((short) 2).setCellValue("User Role");
			rowhead.createCell((short) 3).setCellValue("Email");
			rowhead.createCell((short) 4).setCellValue("Mobile Number");

			int i = 0;
			for (UserModel userModel : userModels) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (userModel.getName() != null) {
					row.createCell((short) 1).setCellValue(userModel.getName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (userModel.getRoleModels() != null && !userModel.getRoleModels().isEmpty()) {
					row.createCell((short) 2).setCellValue(userModel.getRoleModels().iterator().next().getName());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (userModel.getEmail() != null) {
					row.createCell((short) 3).setCellValue(userModel.getEmail());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (userModel.getMobile() != null) {
					row.createCell((short) 4).setCellValue(userModel.getMobile());
				} else {
					row.createCell((short) 4).setCellValue(userModel.getMobile());
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
		fileModel.setModule(Long.valueOf(ModuleEnum.USER.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	public static void main(String[] args) {
		LocalDate currentdate = LocalDate.now();
		int currentMonth = currentdate.getMonthValue();
		String currentYear = String.valueOf(currentdate.getYear())
				.substring(String.valueOf(currentdate.getYear()).length() - 2);
		Long ss = Long.parseLong("ASC1-0822-0001".substring("ASC1-0822-0001".length() - 4)) + 1;
		System.out.println(ss);
		String lastFourDigits = String.format("%04d", ss);
		String machineId = "AS" + "C1-" + currentMonth + currentYear + "-" + lastFourDigits;
		System.out.println(machineId);
	}
}