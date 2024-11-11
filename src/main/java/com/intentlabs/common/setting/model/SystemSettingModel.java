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
package com.intentlabs.common.setting.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.intentlabs.common.model.Model;
import com.intentlabs.common.modelenums.CommonStatusEnum;
import com.intentlabs.common.validation.DataType;

/**
 * This is System Setting model which maps system setting list table to
 * class.This model contains common configuration and map their values. This is
 * applicable to all users of the system.This configuration is not specific to
 * any customer.
 * 
 * @author Nirav
 * @since 20/12/2019
 *
 */
public class SystemSettingModel implements Model {

	private static final long serialVersionUID = 6653648434546572167L;
	private String key;
	private String value;
	private Integer dataType;

	private static Map<String, String> MAP = new ConcurrentHashMap<>();

	private static final String DEFAULT_PASSWORD_CHANGE_REQUIRED = "DEFAULT_PASSWORD_CHANGE_REQUIRED";
	private static final String RESET_PASSWORD_TOKEN_VALID_MINUTES = "RESET_PASSWORD_TOKEN_VALID_MINUTES";
	private static final String PASSWORD_USED_VALIDATION_ENABLED = "PASSWORD_USED_VALIDATION_ENABLED";
	private static final String MAX_PASSWORD_STORE_COUNT_PER_USER = "MAX_PASSWORD_STORE_COUNT_PER_USER";
	private static final String PASSWORD_EXPIRATION_MAX_AGE_NEEDED = "PASSWORD_EXPIRATION_MAX_AGE_NEEDED";
	private static final String PASSWORD_EXPIRATION_MAX_AGE_DAYS = "PASSWORD_EXPIRATION_MAX_AGE_DAYS";
	private static final String PASSWORD_GENERATION_MIN_LENGTH = "PASSWORD_GENERATION_MIN_LENGTH";
	private static final String PASSWORD_GENERATION_MAX_LENGTH = "PASSWORD_GENERATION_MAX_LENGTH";
	private static final String PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS = "PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS";
	private static final String PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS = "PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS";
	private static final String PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS = "PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS";
	private static final String PASSWORD_GENERATION_MIN_NUMERICS = "PASSWORD_GENERATION_MIN_NUMERICS";

	private static final String TWO_FACTOR_AUTHENTICATION_ENABLED = "TWO_FACTOR_AUTHENTICATION_ENABLED";
	private static final String DEVICE_COOKIE_TIME_IN_SECONDS = "DEVICE_COOKIE_TIME_IN_SECONDS";
	private static final String SESSION_INACTIVE_TIME_IN_MINUTES = "SESSION_INACTIVE_TIME_IN_MINUTES";
	private static final String MAX_ALLOWED_DEVICE = "MAX_ALLOWED_DEVICE";
	private static final String CAPTCHA_IMAGE_PATH = "CAPTCHA_IMAGE_PATH";
	private static final String VERIFICATION_THROUGH_OTP = "VERIFICATION_THROUGH_OTP";
	private static final String DEFAULT_FILE_PATH = "DEFAULT_FILE_PATH";
	private static final String URL = "URL";

	private static final String LOCALE_SUPPORTED = "LOCALE_SUPPORTED";

	private static final String SECRET_KEY_FOR_GENERATE_JWT_TOKEN = "SECRET_KEY_FOR_GENERATE_JWT_TOKEN";
	private static final String GENERATE_REFRESH_TOKEN_TIME_IN_MINUTES = "GENERATE_REFRESH_TOKEN_TIME_IN_MINUTES";
	private static final String REGISTRATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES = "REGISTRATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String RESET_PASSWORD_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES = "RESET_PASSWORD_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String CAPTCHA_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES = "CAPTCHA_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String ACTIVATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES = "ACTIVATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String ACCESS_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES = "ACCESS_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String REFRESH_JWT_TOKEN_EXPIRY_TIME_IN_DAY = "REFRESH_JWT_TOKEN_EXPIRY_TIME_IN_DAY";
	private static final String TWO_FACTOR_TOKEN_EXPIRY_TIME_IN_MINUTES = "TWO_FACTOR_TOKEN_EXPIRY_TIME_IN_MINUTES";
	private static final String FIRST_LOGIN_TOKEN_EXPIRY_TIME_IN_MINUTES = "FIRST_LOGIN_TOKEN_EXPIRY_TIME_IN_MINUTES";

	private static final String PASSWORD_GENERATION_SYNTAX_CHECKING_ENABLED = "PASSWORD_GENERATION_SYNTAX_CHECKING_ENABLED";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DataType getDataType() {
		return DataType.getFromId(this.dataType);
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType.getId();
	}

	public static Map<String, String> getMAP() {
		return MAP;
	}

	public static void setMAP(Map<String, String> map) {
		MAP = map;
	}

	public static CommonStatusEnum getTwoFactorAuthenticationEnable() {
		return CommonStatusEnum.fromId(Integer.valueOf(MAP.get(TWO_FACTOR_AUTHENTICATION_ENABLED)));
	}

	public static Integer getDeviceCookieTimeInSeconds() {
		return Integer.valueOf(MAP.get(DEVICE_COOKIE_TIME_IN_SECONDS));
	}

	public static Long getSessionInactiveTimeInMinutes() {
		return Long.valueOf(MAP.get(SESSION_INACTIVE_TIME_IN_MINUTES));
	}

	public static Long getMaxAllowedDevice() {
		return Long.valueOf(MAP.get(MAX_ALLOWED_DEVICE));
	}

	public static Long getResetPasswordTokenValidMinutes() {
		return Long.valueOf(MAP.get(RESET_PASSWORD_TOKEN_VALID_MINUTES));
	}

	public static CommonStatusEnum getPasswordUsedValidationEnabled() {
		return CommonStatusEnum.fromId(Integer.valueOf(MAP.get(PASSWORD_USED_VALIDATION_ENABLED)));
	}

	public static Integer getMaxPasswordStoreCountPerUser() {
		return Integer.valueOf(MAP.get(MAX_PASSWORD_STORE_COUNT_PER_USER));
	}

	public static String getCaptchaImagePath() {
		return MAP.get(CAPTCHA_IMAGE_PATH);
	}

	public static CommonStatusEnum getVerificationThroughOTP() {
		return CommonStatusEnum.fromId(Integer.valueOf(MAP.get(VERIFICATION_THROUGH_OTP)));
	}

	public static String getDefaultFilePath() {
		return MAP.get(DEFAULT_FILE_PATH);
	}

	public static CommonStatusEnum getDefaultPasswordChangeRequired() {
		return CommonStatusEnum.fromId(Integer.valueOf(MAP.get(DEFAULT_PASSWORD_CHANGE_REQUIRED)));
	}

	public static CommonStatusEnum getPasswordExpirationMaxAgeNeeded() {
		return CommonStatusEnum.fromId(Integer.valueOf(MAP.get(PASSWORD_EXPIRATION_MAX_AGE_NEEDED)));
	}

	public static Long getPasswordExpirationMaxAgeDays() {
		return Long.valueOf(MAP.get(PASSWORD_EXPIRATION_MAX_AGE_DAYS));
	}

	public static Integer getPasswordGenerationMinLength() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MIN_LENGTH));
	}

	public static Integer getPasswordGenerationMaxLength() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MAX_LENGTH));
	}

	public static Integer getPasswordGenerationMinLowerCaseAlphabets() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS));
	}

	public static Integer getPasswordGenerationMinUpperCaseAlphabets() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS));
	}

	public static Integer getPasswordGenerationMinSpecialCharacters() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS));
	}

	public static Integer getPasswordGenerationMinNumerics() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_MIN_NUMERICS));
	}

	public static String getLocaleSupported() {
		return MAP.get(LOCALE_SUPPORTED);
	}

	public static String getUrl() {
		return MAP.get(URL);
	}

	public static String getSecretKeyForGenerateJWTToken() {
		return MAP.get(SECRET_KEY_FOR_GENERATE_JWT_TOKEN);
	}

	public static Integer getGenerateRefreshTokenTimeInMinutes() {
		return Integer.valueOf(MAP.get(GENERATE_REFRESH_TOKEN_TIME_IN_MINUTES));
	}

	public static Integer getRegistrationJwtTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(REGISTRATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getResetPasswordJwtTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(RESET_PASSWORD_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getCaptchaJwtTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(CAPTCHA_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getActivationJwtTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(ACTIVATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getAccessJwtTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(ACCESS_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getRefreshJwtTokenExpiryTimeInDay() {
		return Integer.valueOf(MAP.get(REFRESH_JWT_TOKEN_EXPIRY_TIME_IN_DAY));
	}

	public static Integer getTwoFactorTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(TWO_FACTOR_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getFirstLoginTokenExpiryTimeInMinutes() {
		return Integer.valueOf(MAP.get(FIRST_LOGIN_TOKEN_EXPIRY_TIME_IN_MINUTES));
	}

	public static Integer getPasswordGenerationSyntaxCheckingEnabled() {
		return Integer.valueOf(MAP.get(PASSWORD_GENERATION_SYNTAX_CHECKING_ENABLED));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemSettingModel other = (SystemSettingModel) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}