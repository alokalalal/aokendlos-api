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
package com.intentlabs.common.enums;

import java.io.Serializable;
import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intentlabs.common.config.locale.LocaleConfiguration;
import com.intentlabs.common.setting.enums.LocaleEnum;
import com.intentlabs.common.setting.model.ResponseMessageModel;
import com.intentlabs.common.util.WebUtil;

/**
 * This is used to give response code & message to request.
 * 
 * @author Core team.
 * @version 1.0
 * @since 21/09/2016
 */
public enum ResponseCode implements Serializable {

	SUCCESSFUL(1000, "Successful"), SAVE_SUCCESSFULLY(1001, "Data saved successfully."),
	UPDATE_SUCCESSFULLY(1002, "Data updated successfully."),
	REGISTRATION_SUCCESSFUL(1003, "Registration was successful. Please verify your email address."),
	ACTIVATION_SUCCESSFUL(1004, "Your account has been activated successfully."),
	DELETE_SUCCESSFULLY(1005, "Data deleted successfully."),
	CHANGE_PASSWORD_SUCCESSFUL(1006, "You have changed your password successfully. Please login into system using it."),
	FORGET_PASSWORD_SUCCESSFUL(1007,
			"Your request to change the password has been initiated. Please check your registered email id for the same."),
	RESET_PASSWORD_SUCCESSFUL(1008, "You have successfully reset your password. Please login into system using it."),
	FORGET_PASSWORD_VERIFICATION_SUCCESSFUL(1009,
			"Your token for forget password verified successfully, You can now change your password."),
	LOGGED_OUT_SUCCESSFUL(1010, "You have successfully logged out."),
	OTP_MAIL_SENT_SUCCESSFULLY(1011, "OTP was resent successfully."),
	// Any code above 2000 is an error code.
	INTERNAL_SERVER_ERROR(2000, "Oops, Something went wrong! Please try again."),
	INVALID_REQUEST(2001, "Invalid request."), NO_DATA_FOUND(2002, "No data found."),
	UNAUTHORIZED_ACCESS(2003, "You don't have a permission to access it."),

	// common
	TEMP_PASSWORD_SESSION(2004, "For Access the portal you need to change password."),
	PASSWORD_EXPIRED(2005, "Your password has been expired, Please change your password."),
	VALIDATE_NEW_DEVICE(2006, " Login detected from a new device."),
	EMAIL_VERIFICATION(2007, "Please verify your email account to continue."),
	AUTHENTICATION_REQUIRED(2008, "Authentication is required to access requested resource."),
	INVALID_JSON(2009, "Invalid json format."),

	// captcha
	UNABLE_TO_LOAD_CAPTCHA(2010, "Captcha is not loaded."), INVALID_CAPTCHA(2011, "Captcha is invalid."),

	// setting
	PASSWORD_GENERATION_MIN_LENGTH_MIN_LENGTH(2012, "Minimum length 6 allowed for PASSWORD_GENERATION_MIN_LENGTH"),
	PASSWORD_GENERATION_MAX_LENGTH_MAX_LENGTH(2013, "Maximum length 16 allowed for PASSWORD_GENERATION_MAX_LENGTH"),
	PROPERTY_MAX_LENGTH_EXCEED(2014, "Max 100 characters are allowed in Property"),
	PROPERTY_IS_MISSING(2015, "Property is missing"),
	PROPERTY_VALUE_MAX_LENGTH_EXCEED(2016, "Max 100 characters are allowed in Property value"),
	PROPERTY_VALUE_IS_MISSING(2017, "Property value is missing"),
	DEFAULT_PASSWORD_CHANGE_REQUIRED_IS_INVALID(2018,
			"Only numeric value allowed for DEFAULT_PASSWORD_CHANGE_REQUIRED"),
	RESET_PASSWORD_TOKEN_VALID_MINUTES_IS_INVALID(2019,
			"Only numeric value allowed for RESET_PASSWORD_TOKEN_VALID_MINUTES"),
	PASSWORD_USED_VALIDATION_ENABLED_IS_INVALID(2020,
			"Only boolean value allowed for PASSWORD_USED_VALIDATION_ENABLED"),
	MAX_PASSWORD_STORE_COUNT_PER_USER_IS_INVALID(2021,
			"Only numeric value allowed for MAX_PASSWORD_STORE_COUNT_PER_USER"),
	PASSWORD_EXPIRATION_MAX_AGE_NEEDED_IS_INVALID(2022,
			"Only numeric value allowed for PASSWORD_EXPIRATION_MAX_AGE_NEEDED"),
	PASSWORD_EXPIRATION_MAX_AGE_DAYS_IS_INVALID(2023,
			"Only numeric value allowed for PASSWORD_EXPIRATION_MAX_AGE_DAYS"),
	PASSWORD_GENERATION_MIN_LENGTH_IS_INVALID(2024, "Only numeric value allowed for PASSWORD_GENERATION_MIN_LENGTH"),
	PASSWORD_GENERATION_MAX_LENGTH_IS_INVALID(2025, "Only numeric value allowed for PASSWORD_GENERATION_MAX_LENGTH"),
	PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS_IS_INVALID(2026,
			"Only numeric value allowed for PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS"),
	PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS_IS_INVALID(2027,
			"Only numeric value allowed for PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS"),
	PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS_IS_INVALID(2028,
			"Only numeric value allowed for PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS"),
	PASSWORD_GENERATION_MIN_NUMERICS_IS_INVALID(2029,
			"Only numeric value allowed for PASSWORD_GENERATION_MIN_NUMERICS"),
	TWO_FACTOR_AUTHENTICATION_ENABLED_IS_INVALID(2030,
			"Only boolean value allowed for TWO_FACTOR_AUTHENTICATION_ENABLED"),
	DEVICE_COOKIE_TIME_IN_SECONDS_IS_INVALID(2031, "Only numeric value allowed for DEVICE_COOKIE_TIME_IN_SECONDS"),
	SESSION_INACTIVE_TIME_IN_MINUTES_IS_INVALID(2032,
			"Only numeric value allowed for SESSION_INACTIVE_TIME_IN_MINUTES"),
	MAX_ALLOWED_DEVICE_IS_INVALID(2033, "Only numeric value allowed for MAX_ALLOWED_DEVICE"),
	CAPTCHA_IMAGE_PATH_IS_INVALID(2034, "Only string value allowed for CAPTCHA_IMAGE_PATH"),
	VERIFICATION_THROUGH_OTP_IS_INVALID(2035, "Only boolean value allowed for VERIFICATION_THROUGH_OTP"),
	DEFAULT_TIME_ZONE_ID_IS_INVALID(2036, "Only numeric value allowed for DEFAULT_TIME_ZONE_ID"),
	DEFAULT_FILE_PATH_IS_INVALID(2037, "Only string value allowed for DEFAULT_FILE_PATH"),
	ADMIN_URL_IS_INVALID(2038, "Only string value allowed for ADMIN_URL"),
	LOCALE_SUPPORTED_IS_INVALID(2039, "Only string value allowed for LOCALE_SUPPORTED"),

	// file
	UPLOAD_IMAGE_ONLY(2040, "Please upload image file(s), Other file types are not allowed."),
	FILE_ID_IS_INVALID(2041, "FileId is invalid."), UNABLE_TO_UPLOAD_FILE(2042, "Unable to upload a file."),
	UNABLE_TO_DOWNLOAD_FILE(2043, "Unable to download a file."),
	UNABLE_TO_CREATE_THUMBNAIL(2044, "Unable to create thumbnail."),
	UNABLE_TO_CONVERT_INTO_BASE64(2045, "Unable to convert image into bytes"),
	FILE_ID_IS_MISSING(2046, "FileId is mandatory."), FILE_IS_MISSING(2047, "File is mandatory."),
	INVALID_FILE_FORMAT(2048, "Uploaded file doesn't match with the given template."),

	// notification
	WEBSITE_MAX_LENGTH_EXCEED(2049, "Max 2048 characters are allowed in Website"),
	WEBSITE_IS_INVALID(2050, "Website is invalid"), SUBJECT_IS_MISSING(2051, "Subject is missing"),
	SUBJECT_MAX_LENGTH_EXCEED(2052, "Max 1000 characters are allowed in Subject"),
	UNABLE_TO_DELETE_EMAIL_ACCOUNT(2053, "Unable to delete it as it's already binded with email content(s)."),
	RATE_PER_HOUR_INVALID(2054, "Rate per hour is invalid"), RATE_PER_DAY_INVALID(2055, "Rate per day is invalid"),
	AUTHENTICATION_METHOD_IS_MISSING(2056, "Authentication method is missing"),
	AUTHENTICATION_METHOD_IS_INVALID(2057, "Authentication method is invalid"),
	AUTHENTICATION_SECURITY_IS_MISSING(2058, "Authentication security is missing"),
	AUTHENTICATION_SECURITY_IS_INVALID(2059, "Authentication security is invalid"),
	TIMEOUT_IS_INVALID(2060, "Timeout is invalid"), EMAIL_CONTENT_ALREADY_EXIST(2061, "Email content already exist"),
	EMAIL_ACCOUNT_ALREADY_EXIST(2062, "Email account already exist"),
	EMAIL_ACOUNT_IS_INVALID(2063, "Email account is invalid"),
	EMAIL_ACOUNT_IS_MISSING(2064, "Email account is missing"),
	EMAIL_CONTENT_IS_MISSING(2065, "Email content is missing"),
	NOTIFICATION_IS_MISSING(2066, "Notification is missing"), NOTIFICATION_IS_INVALID(2067, "Notification is invalid"),
	HOST_IS_MISSING(2068, "Host is missing"), HOST_MAX_LENGTH_EXCEED(2069, "Max 500 characters are allowed in Host"),
	EMAIL_ACCOUNT_NAME_IS_MISSING(2070, "Email Account Name is missing"),
	EMAIL_ACCOUNT_NAME_MAX_LENGTH_EXCEED(2071, "Max 100 characters are allowed in Email Account Name"),
	EMAIL_FROM_IS_MISSING(2072, "Email From is missing"),
	EMAIL_FROM_MAX_LENGTH_EXCEED(2073, "Max 500 characters are allowed in Email From"),
	REPLY_TO_IS_MISSING(2074, "Reply to is missing"),
	REPLY_TO_MAX_LENGTH_EXCEED(2075, "Max 100 characters are allowed in Reply to"),
	REPLY_TO_IS_INVALID(2076, "Reply to is invalid"), PORT_IS_MISSING(2077, "Port is missing"),
	PORT_MAX_LENGTH_EXCEED(2078, "Max 65555 characters are allowed in Port"),
	PORT_MIN_LENGTH_EXCEED(2079, "Min 0 characters are allowed in Port"), PORT_IS_INVALID(2080, "Port is invalid"),
	EMAIL_USER_NAME_IS_MISSING(2081, "User name is missing"),
	EMAIL_USER_NAME_MAX_LENGTH_EXCEED(2082, "Max 100 characters are allowed in user name"),
	WEBSITE_IS_MISSING(2083, "Website is missing"),

	// response message
	RESPONSE_MESSAGE_ALREADY_EXIST(2084, "Response message already exist"), CODE_IS_INVALID(2085, "Code is invalid"),
	LANGUAGE_IS_INVALID(2086, "Language is invalid"), CODE_IS_MISSING(2087, "Code is missing"),
	LANGUAGE_IS_MISSING(2088, "Language is missing"), MESSAGE_IS_MISSING(2089, "Message is missing"),
	MESSAGE_MAX_LENGTH_EXCEED(2090, "Max 1000 characters are allowed in Message"),

	// User
	PASSOWRD_IS_INVALID(2098,
			"Minimum 1 upper case alphabets," + " minimum 1 lower case alphabets," + " minimum 1 numerics,"
					+ " minium 1 special characters(!@#$%^&*()_+=-) numbers needed for your password."),
	ROLE_ALREADY_EXIST(2099, "Role already exist"), ROLE_TYPE_IS_INVALID(2100, "Role type is invalid"),
	MODULE_IS_INVALID(2101, "Module is invalid"), MODULE_IS_MISSING(2102, "Module is missing"),
	RIGHT_IS_INVALID(2103, "Right is invalid"), RIGHT_IS_MISSING(2104, "Right is missing"),
	MODULE_RIGHT_IS_MISSING(2105, "Module & Rights is missing"),
	CANT_DELETE_ROLE(2106, "You can't delete a role as its binded with user(s)."),
	NOT_ALLOWED_DELETE_OWN_ACCOUNT(2107, "You can't delete your own account."),
	PASSWORD_NOT_MATCH(2108, "New and Confirm password do not match."),
	CAN_NOT_CHANGE_OWN_ACTIVATION_STATUS(2109, "You can't change own activation status."),
	OTP_IS_MISSING(2110, "OTP is missing"), ROLE_IS_MISSING(2111, "Role is missing"),
	PROFILE_IS_INVALID(2112, "Profile image is invalid"), GENDER_IS_INVALID(2113, "Gender is invalid"),
	BIRTHDATE_IS_INVALID(2114, "Birthdate is invalid"), COUNTRY_IS_MISSING(2115, "Country is missing"),
	STATE_IS_MISSING(2116, "State is missing"), CITY_IS_MISSING(2117, "City is missing"),
	INVALID_EMAIL_OR_MOBILE_NUMBER(2119, "You haven't created your account with given email-id/mobile number."),
	DEVICE_NOT_USED(2120, "This device is not used to login/signup"),
	PASSWORD_AND_CONFIRM_NOT_MATCH(2122, "Password and confirm password does not match"),
	DUPLICATE_EMAIL_USER(2124, "Email already exists or is registered in the system."),
	DUPLICATE_MOBILE_USER(2125,
			"It looks like given mobile number is already registered. Please try to login instead?"),
	INVALID_ROLE(2126, "The role is invalid"), INVALID_TOKEN(2127, "Invalid Token."),
	LINK_EXPIRED(2128, "Activation link is expired or have already been used"),
	EXPIRED_TOKEN(2129, "Link is expired or have already been used"),
	INVALID_LOGINID_OR_PASSWORD(2130, "Invalid login-id or password."),
	DELETED_USER(2131, "Your account had been deleted."), INACTIVE_USER(2132, "Your account has been inactivated."),
	CURRENT_PASSWORD_IS_INVALID(2133, "Incorrect Password."),
	DUPLICATE_PASSWORD_USER(2134,
			"The password entered is one of your last used password.Please provide a new password."),
	ACCOUNT_ALREADY_VERIFIED_THROUGH_OTP(2135, "Account is already verified through OTP."),
	INVALID_OTP(2136, "Invalid OTP."),
	REGISTRATION_SUCCESSFUL_WITH_EMAIL_VERIFICATION(2137,
			"Registration was successful. We have sent a verification link on registered email address."),
	CITY_MAX_LENGTH_EXCEED(2140, "Max 100 characters are allowed in city"),
	STATE_MAX_LENGTH_EXCEED(2141, "Max 100 characters are allowed in state"),
	ROLE_MAX_LENGTH_EXCEED(2142, "Max 30 characters are allowed in role"),
	DESCRIPTION_MAX_LENGTH_EXCEED(2143, "Max 30 characters are allowed in description"),
	EMAIL_IS_MISSING(2144, "Email id is missing"),
	EMAIL_MAX_LENGTH_EXCEED(2145, "Max 100 characters are allowed in email"),
	EMAIL_IS_INVALID(2146, "Email id is invalid"), PASSWORD_IS_MISSING(2147, "Password is missing"),
	PASSWORD_MAX_LENGTH_EXCEED(2148, "Max 16 characters are allowed in Password"),
	PASSWORD_MIN_LENGTH_EXCEED(2149, "Min 8 characters are allowed in Password"),
	NAME_IS_MISSING(2150, "Name is missing"), NAME_MAX_LENGTH_EXCEED(2151, "Max 30 characters are allowed in name"),
	NAME_IS_INVALID(2152, "Name is invalid"), PINCODE_IS_MISSING(2153, "Pincode is missing"),
	PINCODE_MAX_LENGTH_EXCEED(2154, "Max 6 characters are allowed in pincode"),
	PINCODE_IS_INVALID(2155, "Pincode is invalid"), MOBILE_IS_MISSING(2156, "Mobile is missing"),
	MOBILE_MAX_LENGTH_EXCEED(2157, "Max 15 characters are allowed in mobile"),
	MOBILE_IS_INVALID(2158, "Mobile is invalid"), ADDRESS_IS_MISSING(2159, "Address is missing"),
	ADDRESS_MAX_LENGTH_EXCEED(2160, "Max 100 characters are allowed in address"),
	LANDMARK_IS_MISSING(2161, "Landmark is missing"),
	LANDMARK_MAX_LENGTH_EXCEED(2162, "Max 1000 characters are allowed in landmark"),

	TAX_NAME_IS_MISSING(2163, "Tax name is missing"),
	TAX_NAME_MAX_LENGTH_EXCEED(2164, "Max 30 characters are allowed in tax name"),

	// User
	FIRST_NAME_IS_MISSING(2165, "First Name is missing"),
	FIRST_NAME_MAX_LENGTH_EXCEED(2166, "Max 30 characters are allowed in first name"),
	FIRST_NAME_IS_INVALID(2167, "First Name is invalid"), LAST_NAME_IS_MISSING(2168, "Last Name is missing"),
	LAST_NAME_MAX_LENGTH_EXCEED(2169, "Max 30 characters are allowed in last name"),
	LAST_NAME_IS_INVALID(2170, "Last Name is invalid"), USER_ALREADY_EXIST(2171, "User Already Exist"),
	INVALID_JSON_TOKEN(2172, "Session Expired"), EXPIRED_JSON_TOKEN(2173, "Expired Session"),
	INVALID_REFRESH_JSON_TOKEN(2174, "Invalid Refresh Json Token"), DATA_IS_MISSING(2175, "Data Is Missing"),
	ALREADY_EXIST(2176, "already exist."), lOGO_IS_INVALID(2177, "Logo is invalid"),

	// Customer
	CUSTOMER_ALREADY_EXIST(2178, "Customer is Already Exist"), CUSTOMER_IS_INVALID(2179, "Customer is invalid"),
	CUSTOMER_NAME_IS_MISSING(2180, "Customer Name is missing"),
	CUSTOMER_NAME_MAX_LENGTH_EXCEED(2181, "Max 500 characters are allowed in Customer Name"),
	CUSTOMER_NAME_IS_INVALID(2182, "Customer Name is invalid"),
	CUSTOMER_NAME_ALREADY_EXIST(2183, "Customer Name is Already Exist"),
	DELETE_DEPENDENCY(2184, "Delete other dependency."), CUSTOMER_IS_MISSING(2185, "Customer is missing"),

	// location
	LOCATION_NAME_IS_MISSING(2186, "Branch Name is missing"),
	LOCATION_NAME_MAX_LENGTH_EXCEED(2187, "Max 500 characters are allowed in Client Branch Name"),
	LOCATION_NAME_IS_INVALID(2188, "Branch Name is invalid"),
	LOCATION_NAME_ALREADY_EXIST(2189, "Branch Name is Already Exist"),
	LATITUDE_IS_MISSING(2190, "Latitude is missing"),
	LATITUDE_MAX_LENGTH_EXCEED(2191, "Max 20 characters are allowed in Latitude"),
	LATITUDE_IS_INVALID(2192, "Latitude is invalid"), LONGITUDE_IS_MISSING(2193, "Longitude is missing"),
	LONGITUDE_MAX_LENGTH_EXCEED(2294, "Max 20 characters are allowed in Longitude"),
	LONGITUDE_IS_INVALID(2295, "Longitude is invalid"), ALTITUDE_IS_MISSING(2296, "Altitude is missing"),
	ALTITUDE_MAX_LENGTH_EXCEED(2297, "Max 20 characters are allowed in Altitude"),
	ALTITUDE_IS_INVALID(2298, "Altitude is invalid"), LOCATION_ALREADY_EXIST(2299, "Branch Name is Already Exist"),
	MAX_LENGTH_EXCEED(2300, "characters are allowed"), DATA_IS_INVALID(2301, "is invalid."),
	CHECK_IS_INVALID(2302, "is invalid."),

	// customer
	INVALID_CUSTOMER_ID(2303, "Customer Id is invalid."),
	CUSTOMER_ACCOUNT_IS_BEEN_DEACTIVATED(2304, "Customer Account is been deactivated"),

	// machine
	MACHINE_NAME_IS_MISSING(2305, "Machine Name is missing"),
	MACHINE_NAME_MAX_LENGTH_EXCEED(2306, "Max 100 characters are allowed in Machine Name"),
	MACHINE_NAME_IS_INVALID(2307, "Machine Name is invalid"),
	MACHINE_ID_ALREADY_EXIST(2308, "Machine Id is Already Exist"),
	MACHINE_IS_INVALID(2309, "Machine is invalid"), IMPORT_SUCCESSFULLY(2310, "Import successfully."),
	NO_IMPORT_PERFORM(2311, "There are no information uploaded"),
	REASON_MAX_LENGTH_EXCEED(2312, "Max 256 characters are allowed in reason"),
	MATERIAL_MISMATCHED(2313, "Material Is not Matched"), TRANSACTION_ID_IS_MISSING(2314, "Transaction Id is missing"),

	// customer
	UPDATE_INACTIVE_CUSTOMER_RECORD(2315, "Can not update inactive customer details."),

	// machine
	MACHINE_SERIAL_NUMBER_IS_MISSING(2316, "Machine serial number is missing"),

	// customer
	CANT_DELETE_CUSTOMER(2317, "You can't delete a customer as its binded with location(s) or machine(s)."),
	IS_APPLICABLE_FOR_ALL(2318, "Is applicable for all missing"), MACHINE_ID_IS_MISSING(2319, "Machine ID is missing"),
	DOES_NOT_EXIST(2320, "Document Does Not Exist"),

	BARCODE_IS_MISSING(2321, "Barcode is missing"),
	BARCODE_MAX_LENGTH_EXCEED(2322, "Max 100 characters are allowed in Barcode"),
	DISCRIPTION_IS_MISSING(2323, "Discription is missing"),
	DISCRIPTION_MAX_LENGTH_EXCEED(2324, "Max 100 characters are allowed in Discription"),
	DATA_ACQUISITION_IS_MISSING(2325, "Data Acquisition is missing"),
	DATA_ACQUISITION_MAX_LENGTH_EXCEED(2326, "Max 20 characters are allowed in Data Acquisition"),
	ITEAM_REDEEM_VALUE_IS_MISSING(2327, "Iteam Redeem Value is missing"),
	ITEAM_REDEEM_VALUE_MAX_LENGTH_EXCEED(2328, "Max 20 characters are allowed in Iteam redeem value"),
	VOLUMN_IS_MISSING(2329, "Volumn is missing"),
	VOLUMN_MAX_LENGTH_EXCEED(2330, "Max 20 characters are allowed in Volumn"),
	NOT_ALLOWED_TO_CHANGE_MACHINE_ID(2331, "You are not allowed to change Machine Id"),
	DATE_IS_MANDATORY(2332, "Please select Date Range to export the data"), BRANCH_NUMBER_IS_MISSING(2333, "branch number is missing"),
	BRANCH_NUMBER_MAX_LENGTH_EXCEED(2334, "Exact 4 characters are allowed in branch number"),
	BRANCH_NUMBER_IS_INVALID(2335, "branch number is invalid"),
	BRANCH_MACHINE_NUMBER_IS_MISSING(2336, "branch machine number is missing"),
	BRANCH_MACHINE_NUMBER_MAX_LENGTH_EXCEED(2337, "Exact 1 characters are allowed in machine branch number"),
	BRANCH_MACHINE_NUMBER_IS_INVALID(2338, "machine branch number is invalid"),
	BARCODE_TYPE_IS_MISSING(2339, "barcode type is missing"), VALUE_IS_MISSING(2340, "value is missing"),
	VALUE_MIN_LENGTH_EXCEED(2341, "please put exact length of the value"),
	VALUE_MAX_LENGTH_EXCEED(2342, "please put exact length of the value"),
	BARCODE_TEMPLATE_ALREADY_EXIST(2343, "Barcode Template is Already Exist"),
	LENGTH_IS_MISSING(2344, "Barcode Length Is Invalid"), LENGTH_IS_INVALID(2345, "Barcode Length Is Invalid"),
	END_VALUE_IS_MISSING(2346, "end value is missing"),
	END_VALUE_MIN_LENGTH_EXCEED(2347, "please put exact length of the end value"),
	END_VALUE_MAX_LENGTH_EXCEED(2348, "please put exact length of the end value"),
	FIELD_NAME_IS_MISSING(2349, "field name is missing"),
	VOUCHER_VALUE_LENGTH_IS_INVALID(2350, "Please Choose Length Of Atleast 4 Digits"),
	BRANCH_ID_LENGTH_IS_INVALID(2351, "Please Choose Length of 4 digits."),
	MACHINE_NUMBER_LENGTH_IS_INVALID(2352, "Please Choose Length Of 1 Digit Only"),
	CHECKED_SUM_LENGTH_IS_INVALID(2353, "Please Choose Length of 1 digit only."), VALUE_IS_INVALID(2354, "Value is invalid"),
	PLEASE_SELECT_MACHINE(2355, "Please Select Atleast One Machine"),
	CAN_NOT_EDIT_BARCODE_TEMPLATE(2356, "You are not allowed to Edit this barcode template as it is already assigned to machine(s)"),
	CAN_NOT_DELETE_BARCODE_TEMPLATE(2357, "You are not allowed to Delete this barcode template as it is already assigned to machine(s)"),
	LAST_INDEX_FOR_CHECKED_SUM(2358, "Please select last index for checked sum"),
	BRANCH_NUMBER_IS_ALREADY_EXIST(2359, "Branch number is already exist"),
	CANT_DELETE_CUSTOMER_ADMIN(2360,"You can't delete a customer admin"),
	BARCODE_TEMPLATE_MIN_LENGTH_EXCEED(2361,"Min 8 characters are allowed in barcode template length"),
	BARCODE_TEMPLATE_MAX_LENGTH_EXCEED(2362,"Max 30 characters are allowed in barcode template length"),
	//Customer
	SPOC_PERSON_NAME_IS_INVALID(2363, "Spoc Person Name is invalid"),
	//User Role
	DELETE_SUCCESSFULLY_ROLE(2364, "User role deleted successfully."),
	//User
	ACTIVATED_USER_SUCCESSFULLY(2365, "The user has been successfully Activated."),
	DEACTIVATED_USER_SUCCESSFULLY(2366, "The user has been successfully deactivated."),
	//User
	USER_DELETE_SUCCESSFULLY(2367, "The user has been successfully deleted."),
	UPLOAD_CSV_ONLY(2368, "Please upload CSV, Other file types are not allowed."),
	File_Size_Exceed(2369, "You are uploading Exceed limit of Data."),
	CANT_DELETE_BARCODE_FILE(2370, "You can't delete a Barcodes as its bind with barcode item(s)."),
	ROLE_NAME_ALREADY_EXIST(2371, "Role Name is Already Exist"),
	CANT_DELETE_BRANCH(2372, "You can't delete a Branch as its binded with machine."),
	DELETE_SUCCESSFULLY_BRANCH(2373, "Branch deleted successfully."),
	//Pickup Route
	PICKUP_ROUTE_ALREADY_EXIST(2374, "Pickup Route is Already Exist"),
	CANT_DELETE_PICKUP_ROUTE(2375, "You can't delete a Pickup Route as its binded with Branch(s)."),
	BRANCH_WISE_MACHINE_NUMBER_MAX_LENGTH(2376, "Machine number greater than 9 are not allowed"),
	ROUTE_NUMBER_ALREADY_EXIST(2377, "Pickup Route Number is Already Exist"),
	POSITION_IN_ROUTE_ALREADY_EXIST(2378, "Position in Route is Already Exist"),
	MACHINE_DATA_DATE_LENGTH_IS_INVALID(2379, "Please Choose Length of 6 digit only."),
	BARCODE_SAVE_SUCCESSFULLY(2400, "All Barcodes saved successfully."),
	INVALID_UPLOADED_DATA(2401, "Invalid data."),
	MACHINE_BARCODE_ALREADY_EXIST(2402, "Barcode File Name Already Exist");



	private final int code;
	private final String message;
	private LocaleConfiguration localeConfiguration;

	ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		LocaleEnum localeEnum = WebUtil.getCurrentLanguage();
		if (localeEnum == null) {
			localeEnum = LocaleEnum.fromLanguageTag(localeConfiguration.getDefaultLocale());
		}
		String languageMessage = ResponseMessageModel.get(String.valueOf(this.code), localeEnum.getLanguageTag());
		return StringUtils.isBlank(languageMessage) ? this.message : languageMessage;
	}

	private void setLocaleConfiguration(LocaleConfiguration injetLocaleConfiguration) {
		this.localeConfiguration = injetLocaleConfiguration;
	}

	/**
	 * This methods is used to fetch Enum base on given id.
	 * 
	 * @param code enum key
	 * @return ResponseCode enum
	 */
	public static ResponseCode fromId(int code) {
		for (ResponseCode responseCode : values()) {
			if (responseCode.code == code) {
				return responseCode;
			}
		}
		return null;
	}

	@Component
	public static class LocaleConfigurationInjector {
		@Autowired
		private LocaleConfiguration localeConfiguration;

		@PostConstruct
		public void postConstruct() {
			for (ResponseCode responseCode : EnumSet.allOf(ResponseCode.class)) {
				responseCode.setLocaleConfiguration(localeConfiguration);
			}
		}
	}
}