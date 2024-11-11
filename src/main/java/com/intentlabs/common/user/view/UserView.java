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
package com.intentlabs.common.user.view;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.modelenums.CommonStatusEnum;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.ArchiveView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;

/**
 * This class is used to represent user object in json/in user response.
 * 
 * @author Vishwa.Shah
 * @since 08/02/2018
 */
@JsonInclude(Include.NON_NULL)
public class UserView extends ArchiveView {

	private static final long serialVersionUID = -4444717308537621033L;
	private String name;
	private String email;
	private String mobile;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private String token;
	private List<RoleView> roleViews;
	private Boolean hasLoggedIn;
	private String loginId;
	private String shortName;
	private boolean verificationOtpUsed;
	private String verificaitionOtp;
	private Long searchRoleId;
	private FileView profilepic;
	private String address;
	private String landmark;
	private KeyValueView countryView;
	private KeyValueView stateView;
	private KeyValueView cityView;
	private String stateName;
	private String cityName;
	private String pincode;
	private String fullTextSearch;
	private RoleView roleView;
	private List<ModuleView> moduleViews;
	private String file;
	private String accessToken;
	private String refreshToken;
	private CustomerView customerView;

	public UserView() {

	}

	public UserView(Long id, String name) {
		this.setId(id);
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (!StringUtils.isBlank(email)) {
			this.email = email.trim().toLowerCase();
		} else {
			this.email = null;
		}
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if (!StringUtils.isBlank(mobile)) {
			this.mobile = mobile.trim();
		} else {
			this.mobile = null;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public List<RoleView> getRoleViews() {
		return roleViews;
	}

	public void setRoleViews(List<RoleView> roleViews) {
		this.roleViews = roleViews;
	}

	public List<ModuleView> getModuleViews() {
		return moduleViews;
	}

	public void setModuleViews(List<ModuleView> moduleViews) {
		this.moduleViews = moduleViews;
	}

	/*public String getShortFormOfName() {
		if (!StringUtils.isEmpty(this.name)) {
			String name = this.name.trim().replaceAll(" +", " ");
			if (name.contains(" ")) {
				String tempFirstWord = name.substring(0, name.lastIndexOf(' '));
				String tempSecondWord = name.substring(tempFirstWord.length() + 1, name.length());
				return tempFirstWord.substring(0, 1).toUpperCase() + tempSecondWord.substring(0, 1).toUpperCase();
			} else {
				return name.substring(0, 1).toUpperCase() + name.substring(1, 2).toUpperCase();
			}
		}
		return null;
	}*/

	public Boolean getHasLoggedIn() {
		return hasLoggedIn;
	}

	public void setHasLoggedIn(Boolean hasLoggedIn) {
		this.hasLoggedIn = hasLoggedIn;
	}

	/*public String getShortName() {
		return shortName;
	}*/

	public boolean isVerificationOtpUsed() {
		return verificationOtpUsed;
	}

	public void setVerificationOtpUsed(boolean verificationOtpUsed) {
		this.verificationOtpUsed = verificationOtpUsed;
	}

	public String getVerificaitionOtp() {
		return verificaitionOtp;
	}

	public void setVerificaitionOtp(String verificaitionOtp) {
		this.verificaitionOtp = verificaitionOtp;
	}

	/*public void setShortName(String shortName) {
		if (shortName != null) {
			String name = shortName.trim().replaceAll(" +", " ");
			if (name.contains(" ")) {
				String tempFirstWord = name.substring(0, name.lastIndexOf(' '));
				String tempSecondWord = name.substring(tempFirstWord.length() + 1, name.length());
				this.shortName = tempFirstWord.substring(0, 1).toUpperCase()
						+ tempSecondWord.substring(0, 1).toUpperCase();
			} else {
				this.shortName = name.substring(0, 1).toUpperCase() + name.substring(1, 2).toUpperCase();
			}
		} else {
			this.shortName = shortName;
		}
	}*/

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		if (!StringUtils.isBlank(loginId)) {
			this.loginId = loginId.trim().toLowerCase();
		} else {
			this.loginId = null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserView(UserModel userModel) {
		this.setId(userModel.getId());
		this.setName(userModel.getName());
		this.setEmail(userModel.getEmail());
		this.setMobile(userModel.getMobile());
	}

	public Long getSearchRoleId() {
		return searchRoleId;
	}

	public void setSearchRoleId(Long searchRoleId) {
		this.searchRoleId = searchRoleId;
	}

	public FileView getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(FileView profilepic) {
		this.profilepic = profilepic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public KeyValueView getCountryView() {
		return countryView;
	}

	public void setCountryView(KeyValueView countryView) {
		this.countryView = countryView;
	}

	public KeyValueView getStateView() {
		return stateView;
	}

	public void setStateView(KeyValueView stateView) {
		this.stateView = stateView;
	}

	public KeyValueView getCityView() {
		return cityView;
	}

	public void setCityView(KeyValueView cityView) {
		this.cityView = cityView;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public RoleView getRoleView() {
		return roleView;
	}

	public void setRoleView(RoleView roleView) {
		this.roleView = roleView;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public static UserView setView(UserModel userModel) {
		UserView userView = new UserView();
		userView.setId(userModel.getId());
		userView.setName(userModel.getName());
		userView.setEmail(userModel.getEmail());
		if (userModel.getMobile() != null) {
			userView.setMobile(userModel.getMobile());
		}
		return userView;
	}

	public static void isValid(UserView userView) throws EndlosAPIException {
		Validator.STRING
				.isValid(new InputField("NAME", userView.getName(), true, 0, 100, RegexEnum.ALPHABETS_WITH_SPACE_DOT));

		Validator.STRING.isValid(new InputField("EMAIL", userView.getEmail(), true, 100, RegexEnum.EMAIL));

		if (!StringUtils.isBlank(userView.getMobile())) {
			Validator.STRING
					.isValid(new InputField("MOBILE", userView.getMobile(), true, 15, RegexEnum.CONTACT_NUMBER));
		}
	}

	public static void isSpocPersonValid(UserView userView) throws EndlosAPIException {
		Validator.STRING
				.isValid(new InputField("SPOC_PERSON_NAME", userView.getName(), true, 0, 100, RegexEnum.ALPHABETS_WITH_SPACE_DOT));

		Validator.STRING.isValid(new InputField("EMAIL", userView.getEmail(), true, 100, RegexEnum.EMAIL));

		if (!StringUtils.isBlank(userView.getMobile())) {
			Validator.STRING
					.isValid(new InputField("MOBILE", userView.getMobile(), true, 15, RegexEnum.CONTACT_NUMBER));
		}
	}

	public static void validatePassword(String password) throws NumberFormatException, EndlosAPIException {
		Validator.STRING
				.isValid(new InputField("Password", password, true, SystemSettingModel.getPasswordGenerationMinLength(),
						SystemSettingModel.getPasswordGenerationMaxLength()));

		if (CommonStatusEnum.YES
				.equals(CommonStatusEnum.fromId(SystemSettingModel.getPasswordGenerationSyntaxCheckingEnabled()))) {
			Integer upperCase = 0, lowerCase = 0, specialChar = 0, numbers = 0;
			for (int index = 0; index < password.length(); index++) {
				upperCase += Character.isUpperCase(password.charAt(index)) ? 1 : 0;
				lowerCase += Character.isLowerCase(password.charAt(index)) ? 1 : 0;
				numbers += Character.isDigit(password.charAt(index)) ? 1 : 0;
				specialChar += "!@#$%^&*()_+=-".contains(String.valueOf(password.charAt(index))) ? 1 : 0;
			}
			if (SystemSettingModel.getPasswordGenerationMinUpperCaseAlphabets() > upperCase) {
				sendErrorMessage();
			}
			if (SystemSettingModel.getPasswordGenerationMinLowerCaseAlphabets() > lowerCase) {
				sendErrorMessage();
			}
			if (SystemSettingModel.getPasswordGenerationMinNumerics() > numbers) {
				sendErrorMessage();
			}
			if (SystemSettingModel.getPasswordGenerationMinSpecialCharacters() > specialChar) {
				sendErrorMessage();
			}
		}
	}

	private static void sendErrorMessage() throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.PASSOWRD_IS_INVALID.getCode(),
				ResponseCode.PASSOWRD_IS_INVALID.getMessage());
	}
}