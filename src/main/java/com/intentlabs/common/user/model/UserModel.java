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
package com.intentlabs.common.user.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.location.model.CityModel;
import com.intentlabs.common.location.model.CountryModel;
import com.intentlabs.common.location.model.StateModel;
import com.intentlabs.common.model.ArchiveModel;
import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.common.model.JwtTokenModel;
import com.intentlabs.endlos.customer.model.CustomerModel;

/**
 * This is User model which maps user table to class.
 * 
 * @author Nirav.Shah
 * @since 24/07/2018
 */
public class UserModel extends ArchiveModel {

	private static final long serialVersionUID = -5764068071467332650L;

	private String name;
	private String email;
	private String mobile;
	private String verifyToken;
	private boolean verifyTokenUsed;
	private String resetPasswordToken;
	private boolean resetPasswordTokenUsed;
	private Long resetPasswordDate;
	private String twofactorToken;
	private boolean twofactorTokenUsed;
	private Long twofactorDate;
	private String tempPassword;
	private boolean hasLoggedIn;
	private String verificationOtp;
	private boolean verificationOtpUsed;
	private Set<RoleModel> roleModels = new HashSet<>();
	private FileModel profilepic;
	private String address;
	private String landmark;
	private CityModel cityModel;
	private StateModel stateModel;
	private CountryModel countryModel;
	private String stateName;
	private String cityName;
	private String pincode;
	private Boolean isTempPassword;
	private String accessJWTToken;
	private JwtTokenModel jwtTokenModel;
	private String refreshJWTToken;
	private String uniqueToken;
	private Set<CustomerModel> customerModels = new HashSet<>();
	private boolean customerAdmin;
	private CustomerModel requestedCustomerModel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!StringUtils.isEmpty(name)) {
			this.name = WordUtils.capitalizeFully(name);
		} else {
			this.name = name;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Set<RoleModel> getRoleModels() {
		return roleModels;
	}

	public void setRoleModels(Set<RoleModel> roleModels) {
		this.roleModels = roleModels;
	}

	public String getVerifyToken() {
		return verifyToken;
	}

	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
	}

	public boolean isVerifyTokenUsed() {
		return verifyTokenUsed;
	}

	public void setVerifyTokenUsed(boolean verifyTokenUsed) {
		this.verifyTokenUsed = verifyTokenUsed;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public boolean isResetPasswordTokenUsed() {
		return resetPasswordTokenUsed;
	}

	public void setResetPasswordTokenUsed(boolean resetPasswordTokenUsed) {
		this.resetPasswordTokenUsed = resetPasswordTokenUsed;
	}

	public Long getResetPasswordDate() {
		return resetPasswordDate;
	}

	public void setResetPasswordDate(Long resetPasswordDate) {
		this.resetPasswordDate = resetPasswordDate;
	}

	public String getTwofactorToken() {
		return twofactorToken;
	}

	public void setTwofactorToken(String twofactorToken) {
		this.twofactorToken = twofactorToken;
	}

	public boolean isTwofactorTokenUsed() {
		return twofactorTokenUsed;
	}

	public void setTwofactorTokenUsed(boolean twofactorTokenUsed) {
		this.twofactorTokenUsed = twofactorTokenUsed;
	}

	public Long getTwofactorDate() {
		return twofactorDate;
	}

	public void setTwofactorDate(Long twofactorDate) {
		this.twofactorDate = twofactorDate;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public boolean isHasLoggedIn() {
		return hasLoggedIn;
	}

	public void setHasLoggedIn(boolean hasLoggedIn) {
		this.hasLoggedIn = hasLoggedIn;
	}

	public String getVerificationOtp() {
		return verificationOtp;
	}

	public void setVerificationOtp(String verificationOtp) {
		this.verificationOtp = verificationOtp;
	}

	public boolean isVerificationOtpUsed() {
		return verificationOtpUsed;
	}

	public void setVerificationOtpUsed(boolean verificationOtpUsed) {
		this.verificationOtpUsed = verificationOtpUsed;
	}

	public void addRoleModel(RoleModel roleModel) {
		this.roleModels.add(roleModel);
	}

	public void removeRoleModel(RoleModel roleModel) {
		this.roleModels.remove(roleModel);
	}

	public boolean hasAccess(Long roleId, Long moduleId, Long rightsId) {
		RoleModuleRightsModel roleModuleRightsModel = new RoleModuleRightsModel(roleId, moduleId, rightsId);
		for (RoleModel roleModel : this.getRoleModels()) {
			if (roleModel.getRoleModuleRightsModels().contains(roleModuleRightsModel)) {
				return true;
			}
		}
		return false;
	}

	public FileModel getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(FileModel profilepic) {
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

	public CityModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CityModel cityModel) {
		this.cityModel = cityModel;
	}

	public StateModel getStateModel() {
		return stateModel;
	}

	public void setStateModel(StateModel stateModel) {
		this.stateModel = stateModel;
	}

	public CountryModel getCountryModel() {
		return countryModel;
	}

	public void setCountryModel(CountryModel countryModel) {
		this.countryModel = countryModel;
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

	public Boolean getIsTempPassword() {
		return isTempPassword;
	}

	public void setIsTempPassword(Boolean isTempPassword) {
		this.isTempPassword = isTempPassword;
	}

	public String getAccessJWTToken() {
		return accessJWTToken;
	}

	public void setAccessJWTToken(String accessJWTToken) {
		this.accessJWTToken = accessJWTToken;
	}

	public JwtTokenModel getJwtTokenModel() {
		return jwtTokenModel;
	}

	public void setJwtTokenModel(JwtTokenModel jwtTokenModel) {
		this.jwtTokenModel = jwtTokenModel;
	}

	public String getRefreshJWTToken() {
		return refreshJWTToken;
	}

	public void setRefreshJWTToken(String refreshJWTToken) {
		this.refreshJWTToken = refreshJWTToken;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	public Set<CustomerModel> getCustomerModels() {
		return customerModels;
	}

	public void setCustomerModels(Set<CustomerModel> customerModels) {
		this.customerModels = customerModels;
	}

	public void addCustomerModel(CustomerModel customerModel) {
		this.customerModels.add(customerModel);
	}

	public void removeCustomerModel(CustomerModel customerModel) {
		this.customerModels.remove(customerModel);
	}

	public boolean isCustomerAdmin() {
		return customerAdmin;
	}

	public void setCustomerAdmin(boolean customerAdmin) {
		this.customerAdmin = customerAdmin;
	}

	public CustomerModel getRequestedCustomerModel() {
		return requestedCustomerModel;
	}

	public void setRequestedCustomerModel(CustomerModel requestedCustomerModel) {
		this.requestedCustomerModel = requestedCustomerModel;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}