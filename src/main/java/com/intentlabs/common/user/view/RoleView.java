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
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.view.ArchiveView;

/**
 * This class is used to represent role object in json/in customer response.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@JsonInclude(Include.NON_NULL)
public class RoleView extends ArchiveView {

	private static final long serialVersionUID = -4444717308537621033L;
	private String name;
	private String description;
	private List<RoleModuleRightsView> roleModuleRightsViews;
	private boolean customerRole;

	public RoleView() {
		super();
	}

	public RoleView(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RoleModuleRightsView> getRoleModuleRightsViews() {
		return roleModuleRightsViews;
	}

	public void setRoleModuleRightsViews(List<RoleModuleRightsView> roleModuleRightsViews) {
		this.roleModuleRightsViews = roleModuleRightsViews;
	}

	public void setView(RoleModel roleModel) {
		this.setId(roleModel.getId());
		this.setName(roleModel.getName());
	}

	public boolean isCustomerRole() {
		return customerRole;
	}

	public void setCustomerRole(boolean customerRole) {
		this.customerRole = customerRole;
	}

	public void setViewList(Set<RoleModel> roleModels, List<RoleView> roleViews) {
		for (RoleModel roleModel : roleModels) {
			RoleView roleView = new RoleView();
			roleView.setView(roleModel);
			roleView.setCustomerRole(roleModel.isCustomerRole());
			roleViews.add(roleView);
		}
	}
}
