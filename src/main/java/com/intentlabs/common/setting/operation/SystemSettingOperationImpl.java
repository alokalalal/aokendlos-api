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
package com.intentlabs.common.setting.operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.modelenums.CommonStatusEnum;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.setting.service.SystemSettingService;
import com.intentlabs.common.setting.view.SystemSettingView;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.validation.DataType;

/**
 * This class used to perform all business operation on city model.
 * 
 * @author Nirav
 * @since 14/11/2018
 */
@Component(value = "systemSettingOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class SystemSettingOperationImpl implements SystemSettingOperation {

	@Autowired
	SystemSettingService systemSettingService;

	@Override
	public SystemSettingService getService() {
		return systemSettingService;
	}

	@Override
	public Response doView(String key) throws EndlosAPIException {
		SystemSettingModel systemSettingModel = systemSettingService.get(key);
		if (systemSettingModel == null) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		SystemSettingView systemSettingView = fromModel(systemSettingModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				systemSettingView);
	}

	@Override
	public Response doEdit(String key) throws EndlosAPIException {
		return doView(key);
	}

	@Override
	public Response doUpdate(SystemSettingView systemSettingView) throws EndlosAPIException {
		SystemSettingModel systemSettingModel = systemSettingService.get(systemSettingView.getKey());
		if (systemSettingModel == null) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		systemSettingService.update(toModel(systemSettingModel, systemSettingView));
		SystemSettingModel.getMAP().put(systemSettingView.getKey(), systemSettingView.getValue());
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doUpdateBulk(List<SystemSettingView> systemSettingViews) throws EndlosAPIException {
		Map<String, String> viewMap = new HashMap<>();
		for (SystemSettingView systemSettingView : systemSettingViews) {
			viewMap.put(systemSettingView.getKey(), systemSettingView.getValue());
		}

		for (Map.Entry<String, String> map : SystemSettingModel.getMAP().entrySet()) {
			if (viewMap.containsKey(map.getKey())) {
				SystemSettingModel systemSettingModel = systemSettingService.get(map.getKey());
				SystemSettingView systemSettingView = new SystemSettingView();
				systemSettingView.setValue(viewMap.get(map.getKey()));
				validateSettings(systemSettingModel, systemSettingView);
				systemSettingService.update(toModel(systemSettingModel, systemSettingView));
				map.setValue(systemSettingModel.getValue());
			}
		}
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	private void validateSettings(SystemSettingModel systemSettingModel, SystemSettingView systemSettingView)
			throws EndlosAPIException {
		if (systemSettingModel.getDataType().equals(DataType.INT)) {
			try {
				new Integer(systemSettingView.getValue());
			} catch (Exception e) {
				validateMessage(systemSettingModel.getKey());
			}
			if (systemSettingModel.getKey().equals("PASSWORD_GENERATION_MIN_LENGTH")) {
				if (Integer.valueOf(systemSettingView.getValue()) < 6) {
					throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MIN_LENGTH_MIN_LENGTH.getCode(),
							ResponseCode.PASSWORD_GENERATION_MIN_LENGTH_MIN_LENGTH.getMessage());
				}
			}
			if (systemSettingModel.getKey().equals("PASSWORD_GENERATION_MAX_LENGTH")) {
				if (Integer.valueOf(systemSettingView.getValue()) > 16) {
					throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MAX_LENGTH_MAX_LENGTH.getCode(),
							ResponseCode.PASSWORD_GENERATION_MAX_LENGTH_MAX_LENGTH.getMessage());
				}
			}
		}
		if (systemSettingModel.getDataType().equals(DataType.FLOAT)) {
			try {
				new Float(systemSettingView.getValue());
			} catch (Exception e) {
				validateMessage(systemSettingModel.getKey());
			}
		}
		if (systemSettingModel.getDataType().equals(DataType.LONG)) {
			try {
				new Long(systemSettingView.getValue());
			} catch (Exception e) {
				validateMessage(systemSettingModel.getKey());
			}
		}
		if (systemSettingModel.getDataType().equals(DataType.DATE)) {
			try {
				LocalDateTime value = DateUtility.stringToDateTime(systemSettingView.getValue(), "dd/MM/yyyy HH:mm:ss");
				if (value == null) {
					validateMessage(systemSettingModel.getKey());
				}
			} catch (Exception e) {
				validateMessage(systemSettingModel.getKey());
			}
		}
		if (systemSettingModel.getDataType().equals(DataType.BOOLEAN)) {
			if (CommonStatusEnum.fromId(Integer.valueOf(systemSettingView.getValue())) == null) {
				validateMessage(systemSettingModel.getKey());
			}
		}
		if (systemSettingModel.getKey().equals("SUCCESS_FILE_NAME")
				|| systemSettingModel.getKey().equals("CANCEL_FILE_NAME")) {
			String fileName = systemSettingView.getValue();
			if (fileName.contains(".")) {
				fileName = fileName.substring(0, systemSettingView.getValue().lastIndexOf("."));
			}
			fileName = fileName + ".html";
			systemSettingView.setValue(fileName);
		}
	}

	private void validateMessage(String key) throws EndlosAPIException {
		if (key.equals("DEFAULT_PASSWORD_CHANGE_REQUIRED")) {
			throw new EndlosAPIException(ResponseCode.DEFAULT_PASSWORD_CHANGE_REQUIRED_IS_INVALID.getCode(),
					ResponseCode.DEFAULT_PASSWORD_CHANGE_REQUIRED_IS_INVALID.getMessage());
		}
		if (key.equals("RESET_PASSWORD_TOKEN_VALID_MINUTES")) {
			throw new EndlosAPIException(ResponseCode.RESET_PASSWORD_TOKEN_VALID_MINUTES_IS_INVALID.getCode(),
					ResponseCode.RESET_PASSWORD_TOKEN_VALID_MINUTES_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_USED_VALIDATION_ENABLED")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_USED_VALIDATION_ENABLED_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_USED_VALIDATION_ENABLED_IS_INVALID.getMessage());
		}
		if (key.equals("MAX_PASSWORD_STORE_COUNT_PER_USER")) {
			throw new EndlosAPIException(ResponseCode.MAX_PASSWORD_STORE_COUNT_PER_USER_IS_INVALID.getCode(),
					ResponseCode.MAX_PASSWORD_STORE_COUNT_PER_USER_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_EXPIRATION_MAX_AGE_NEEDED")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_EXPIRATION_MAX_AGE_NEEDED_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_EXPIRATION_MAX_AGE_NEEDED_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_EXPIRATION_MAX_AGE_DAYS")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_EXPIRATION_MAX_AGE_DAYS_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_EXPIRATION_MAX_AGE_DAYS_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MIN_LENGTH")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MIN_LENGTH_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MIN_LENGTH_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MAX_LENGTH")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MAX_LENGTH_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MAX_LENGTH_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS")) {
			throw new EndlosAPIException(
					ResponseCode.PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS")) {
			throw new EndlosAPIException(
					ResponseCode.PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS_IS_INVALID.getMessage());
		}
		if (key.equals("PASSWORD_GENERATION_MIN_NUMERICS")) {
			throw new EndlosAPIException(ResponseCode.PASSWORD_GENERATION_MIN_NUMERICS_IS_INVALID.getCode(),
					ResponseCode.PASSWORD_GENERATION_MIN_NUMERICS_IS_INVALID.getMessage());
		}
		if (key.equals("TWO_FACTOR_AUTHENTICATION_ENABLED")) {
			throw new EndlosAPIException(ResponseCode.TWO_FACTOR_AUTHENTICATION_ENABLED_IS_INVALID.getCode(),
					ResponseCode.TWO_FACTOR_AUTHENTICATION_ENABLED_IS_INVALID.getMessage());
		}
		if (key.equals("DEVICE_COOKIE_TIME_IN_SECONDS")) {
			throw new EndlosAPIException(ResponseCode.DEVICE_COOKIE_TIME_IN_SECONDS_IS_INVALID.getCode(),
					ResponseCode.DEVICE_COOKIE_TIME_IN_SECONDS_IS_INVALID.getMessage());
		}
		if (key.equals("SESSION_INACTIVE_TIME_IN_MINUTES")) {
			throw new EndlosAPIException(ResponseCode.SESSION_INACTIVE_TIME_IN_MINUTES_IS_INVALID.getCode(),
					ResponseCode.SESSION_INACTIVE_TIME_IN_MINUTES_IS_INVALID.getMessage());
		}
		if (key.equals("MAX_ALLOWED_DEVICE")) {
			throw new EndlosAPIException(ResponseCode.MAX_ALLOWED_DEVICE_IS_INVALID.getCode(),
					ResponseCode.MAX_ALLOWED_DEVICE_IS_INVALID.getMessage());
		}
		if (key.equals("CAPTCHA_IMAGE_PATH")) {
			throw new EndlosAPIException(ResponseCode.CAPTCHA_IMAGE_PATH_IS_INVALID.getCode(),
					ResponseCode.CAPTCHA_IMAGE_PATH_IS_INVALID.getMessage());
		}
		if (key.equals("VERIFICATION_THROUGH_OTP")) {
			throw new EndlosAPIException(ResponseCode.VERIFICATION_THROUGH_OTP_IS_INVALID.getCode(),
					ResponseCode.VERIFICATION_THROUGH_OTP_IS_INVALID.getMessage());
		}
		if (key.equals("DEFAULT_TIME_ZONE_ID")) {
			throw new EndlosAPIException(ResponseCode.DEFAULT_TIME_ZONE_ID_IS_INVALID.getCode(),
					ResponseCode.DEFAULT_TIME_ZONE_ID_IS_INVALID.getMessage());
		}
		if (key.equals("DEFAULT_FILE_PATH")) {
			throw new EndlosAPIException(ResponseCode.DEFAULT_FILE_PATH_IS_INVALID.getCode(),
					ResponseCode.DEFAULT_FILE_PATH_IS_INVALID.getMessage());
		}
		if (key.equals("ADMIN_URL")) {
			throw new EndlosAPIException(ResponseCode.ADMIN_URL_IS_INVALID.getCode(),
					ResponseCode.ADMIN_URL_IS_INVALID.getMessage());
		}
		if (key.equals("LOCALE_SUPPORTED")) {
			throw new EndlosAPIException(ResponseCode.LOCALE_SUPPORTED_IS_INVALID.getCode(),
					ResponseCode.LOCALE_SUPPORTED_IS_INVALID.getMessage());
		}
	}

	@Override
	public Response doSearch(SystemSettingView systemSettingView, Integer start, Integer recordSize)
			throws EndlosAPIException {
		List<SystemSettingView> systemSettingViews = new ArrayList<>();

		List<SystemSettingModel> systemSettingModels = systemSettingService.findAll();
		for (SystemSettingModel systemSettingModel : systemSettingModels) {
			systemSettingViews.add(fromModel(systemSettingModel));
		}

		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				systemSettingViews.size(), systemSettingViews);
	}

	@Override
	public SystemSettingModel toModel(SystemSettingModel systemSettingModel, SystemSettingView systemSettingView) {
		if (systemSettingModel.getDataType() != null && DataType.DATE.equals(systemSettingModel.getDataType())) {
			// TimeZoneModel timeZoneModel = TimeZoneModel.getMAP()
			// .get(SystemSettingModel.getDefaultTimeZoneId());
			// Long value = DateUtility.getEpochFromLocalDateTime(
			// DateUtility.stringToDateTime(systemSettingView.getValue(),
			// "dd/MM/yyyy HH:mm:ss"), timeZoneModel);
			// systemSettingModel.setValue(String.valueOf(value));
			systemSettingModel.setValue(systemSettingView.getValue());
		} else {
			systemSettingModel.setValue(systemSettingView.getValue());
		}
		return systemSettingModel;
	}

	@Override
	public SystemSettingModel getModel(SystemSettingView systemSettingView) {
		return toModel(getNewModel(), systemSettingView);
	}

	@Override
	public SystemSettingModel getNewModel() {
		return new SystemSettingModel();
	}

	@Override
	public SystemSettingView fromModel(SystemSettingModel systemSettingModel) {
		SystemSettingView systemSettingView = new SystemSettingView();
		systemSettingView.setKey(systemSettingModel.getKey());
		systemSettingView.setValue(systemSettingModel.getValue());
		convertIntoDisplay(systemSettingView, systemSettingModel.getValue(), systemSettingModel.getDataType());
		return systemSettingView;
	}

	@Override
	public List<SystemSettingView> fromModelList(List<SystemSettingModel> systemSettingModels) {
		List<SystemSettingView> systemSettingViews = new ArrayList<>(systemSettingModels.size());
		for (SystemSettingModel systemSettingModel : systemSettingModels) {
			systemSettingViews.add(fromModel(systemSettingModel));
		}
		return systemSettingViews;
	}

	private void convertIntoDisplay(SystemSettingView systemSettingView, String value, DataType dataType) {
		if (DataType.INT.equals(dataType)) {
			systemSettingView.setDisplayValue(Integer.valueOf(value));
		}
		if (DataType.STRING.equals(dataType)) {
			systemSettingView.setDisplayValue(String.valueOf(value));
		}
		if (DataType.DATE.equals(dataType)) {
			// TimeZoneModel timeZoneModel = TimeZoneModel.getMAP()
			// .get(SystemSettingModel.getDefaultTimeZoneId());
			// systemSettingView.setDisplayValue(DateUtility.getLocalDateTime(Long.valueOf(value),
			// timeZoneModel));
			systemSettingView.setDisplayValue(value);
		}
		if (DataType.BOOLEAN.equals(dataType)) {
			if (CommonStatusEnum.YES.equals(CommonStatusEnum.fromId(Integer.valueOf(value)))) {
				systemSettingView.setDisplayValue(true);
			} else {
				systemSettingView.setDisplayValue(false);
			}
		}
	}
}