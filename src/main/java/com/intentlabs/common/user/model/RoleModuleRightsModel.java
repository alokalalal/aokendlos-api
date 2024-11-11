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

import com.intentlabs.common.model.Model;

/**
 * Role Module is used to map role module rights table.
 * @author Nirav.Shah
 * @since 05/08/2018
 *
 */
public class RoleModuleRightsModel implements Model  {

	private static final long serialVersionUID = 8817523517179583864L;
	private Long moduleId;
	private Long roleId;
	private Long rightsId;
	
	public RoleModuleRightsModel() {	
	}
	
	public RoleModuleRightsModel(Long roleId, Long moduleId, Long rightsId) {
		super();
		this.moduleId = moduleId;
		this.roleId = roleId;
		this.rightsId = rightsId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getRightsId() {
		return rightsId;
	}
	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((rightsId == null) ? 0 : rightsId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		RoleModuleRightsModel other = (RoleModuleRightsModel) obj;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (rightsId == null) {
			if (other.rightsId != null)
				return false;
		} else if (!rightsId.equals(other.rightsId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
}