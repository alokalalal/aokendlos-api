
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
package com.intentlabs.common.user.service;

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.RoleModuleRightsModel;
import com.intentlabs.common.user.view.RoleView;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
public interface RoleService extends BaseService<RoleModel> {
	String ROLE_MODEL = "roleModel";
	String LIGHT_ROLE_MODEL = "lightRoleModel";
	String EXTRA_LIGHT_ROLE_MODEL = "extralightRoleModel";

	/**
	 * This method is used to get role model using different entities.
	 * 
	 * @param id
	 * @return
	 */
	RoleModel getLight(long id);

	/**
	 * This method is used to get role model using different entities.
	 * 
	 * @param id
	 * @return
	 */
	RoleModel getExtraLight(long id);

	/**
	 * This method is used to fetch role base rights.
	 * 
	 * @param id
	 * @param RoleModuleRightsModel
	 * @return
	 */
	Set<RoleModuleRightsModel> getRights(long id);

	/**
	 * This method is used to delete a role roleModel.getId()
	 * 
	 * @param roleModel
	 */
	void hardDelete(RoleModel roleModel);

	/**
	 * This method is used to search role based on light entity.
	 * 
	 * @param roleView
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 */
	PageModel searchByLight(RoleView roleView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam);

	/**
	 * This method is used to fetch role base rights.
	 * 
	 * @param id
	 * @param RoleModel
	 * @return
	 */
	List<RoleModel> getRights(List<Long> id);

	/**
	 * This method is used to fetch role by user type.
	 * 
	 * @param isIndustry
	 * @return
	 */
	List<RoleModel> getByUserType(boolean isCustomer);

	List<RoleModel> getByName(String name);
	List<RoleModel> getByNameExcludingId(String name, Long currentId);
}