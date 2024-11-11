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
package com.intentlabs.endlos.customer.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.ActivationView;

/**
 * This is Customer view which maps Customer table to class.
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class CustomerView extends ActivationView {

	private static final long serialVersionUID = -70823349628419359L;
	private String name;
	private FileView logo;
	private List<LocationView> locationViews;
	private UserView userView;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileView getLogo() {
		return logo;
	}

	public void setLogo(FileView logo) {
		this.logo = logo;
	}

	public List<LocationView> getLocationViews() {
		return locationViews;
	}

	public void setLocationViews(List<LocationView> locationViews) {
		this.locationViews = locationViews;
	}

	public UserView getUserView() {
		return userView;
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
	}

	public static void isValid(CustomerView customerView) throws EndlosAPIException {
		Validator.STRING.isValid(new InputField("CUSTOMER_NAME", customerView.getName(), true, 100,
				RegexEnum.ALPHANUMERIC_WITH_SPACE_HYPEN_DOT_AND));
	}
}