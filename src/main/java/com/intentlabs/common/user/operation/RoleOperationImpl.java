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

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.RoleModuleRightsModel;
import com.intentlabs.common.user.service.RoleService;
import com.intentlabs.common.user.view.ModuleView;
import com.intentlabs.common.user.view.RightsView;
import com.intentlabs.common.user.view.RoleModuleRightsView;
import com.intentlabs.common.user.view.RoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * This class used to perform all business operation on Announcement model.
 * 
 * @author Dhruvang.Joshi
 * @since 26/12/2018
 */
@Component(value = "roleOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class RoleOperationImpl extends AbstractOperation<RoleModel, RoleView> implements RoleOperation {

	@Autowired
	private RoleService roleService;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		RoleModel roleModel = roleService.get(id);
		if (roleModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		RoleView roleView = fromModel(roleModel);
		if (roleModel.getRoleModuleRightsModels() != null) {
			setRoleModuleRightsView(roleView, roleModel);
		}

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), roleView);
	}

	@Override
	public Response doSave(RoleView roleView) throws EndlosAPIException {

		List<RoleModel> userRoleExist = roleService.getByName(roleView.getName());
		if (!userRoleExist.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.ROLE_NAME_ALREADY_EXIST.getCode(),
					ResponseCode.ROLE_NAME_ALREADY_EXIST.getMessage());
		}

		RoleModel roleModel = toModel(getNewModel(), roleView);
//		if (roleView.getType() != null) {
//			RoleModel existingRoleModel = roleService
//					.getByRoleType(RoleTypeEnum.fromId(roleView.getType().getKey().intValue()));
//			if (existingRoleModel != null) {
//				throw new EndlosAPIException(ResponseCode.ROLE_ALREADY_EXIST.getCode(),
//						ResponseCode.ROLE_ALREADY_EXIST.getMessage());
//			}
//		}
		roleService.create(roleModel);
		RoleModel.getMAP().put(roleModel.getId(), roleModel);

		setRoleModuleRights(roleModel, roleView);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(RoleView roleView) throws EndlosAPIException {

		List<RoleModel> userRoleExist = roleService.getByNameExcludingId(roleView.getName(), roleView.getId());
		if (userRoleExist != null && !userRoleExist.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.ROLE_NAME_ALREADY_EXIST.getCode(),
					ResponseCode.ROLE_NAME_ALREADY_EXIST.getMessage());
		}

		RoleModel roleModel = roleService.get(roleView.getId());
		if (roleModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		roleModel = toModel(roleModel, roleView);
		roleService.update(roleModel);
		RoleModel.getMAP().put(roleModel.getId(), roleModel);

		setRoleModuleRights(roleModel, roleView);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		RoleModel roleModel = roleService.get(id);
		if (roleModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		roleService.hardDelete(roleModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY_ROLE.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY_ROLE.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSearch(RoleView roleView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = roleService.searchByLight(roleView, start, recordSize, orderType, orderParam);
		if (pageModel == null || (pageModel.getList() != null && pageModel.getList().isEmpty())) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.emptyList());
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<RoleModel>) pageModel.getList()));
	}

	@Override
	public Response doDisplayGrid(Integer start, Integer recordSize) {
		return null;
	}

	private void setRoleModuleRightsView(RoleView roleView, RoleModel roleModel) {
		List<RoleModuleRightsView> roleModuleRightsViews = new ArrayList<>();
		for (RoleModuleRightsModel roleModuleRightsModel : roleModel.getRoleModuleRightsModels()) {
			RoleModuleRightsView roleModuleRightsView = new RoleModuleRightsView();

			RightsView rightsView = new RightsView();
			rightsView.setRightView(RightsEnum.fromId(roleModuleRightsModel.getRightsId().intValue()));
			roleModuleRightsView.setRightsView(rightsView);

			ModuleView moduleView = new ModuleView();
			moduleView.setModuleView(ModuleEnum.fromId(roleModuleRightsModel.getModuleId().intValue()));
			roleModuleRightsView.setModuleView(moduleView);

			roleModuleRightsViews.add(roleModuleRightsView);
		}
		roleView.setRoleModuleRightsViews(roleModuleRightsViews);
	}

	@Override
	protected RoleModel loadModel(RoleView roleView) {
		return roleService.get(roleView.getId());
	}

	@Override
	public RoleModel toModel(RoleModel roleModel, RoleView roleView) throws EndlosAPIException {
		roleModel.setDescription(roleView.getDescription());
		roleModel.setName(roleView.getName());
		roleModel.setActive(true);
		roleModel.setCustomerRole(roleView.isCustomerRole());
		return roleModel;
	}

	@Override
	protected RoleModel getNewModel() {
		return new RoleModel();
	}

	@Override
	public RoleView fromModel(RoleModel roleModel) {
		RoleView roleView = new RoleView();
		roleView.setId(roleModel.getId());
		roleView.setName(roleModel.getName());
		roleView.setDescription(roleModel.getDescription());
		roleView.setCustomerRole(roleModel.isCustomerRole());
		return roleView;
	}

	@Override
	public BaseService<RoleModel> getService() {
		return roleService;
	}

	@Override
	protected void checkInactive(RoleModel roleModel) throws EndlosAPIException {
	}

	private void setRoleModuleRights(RoleModel roleModel, RoleView roleView) throws EndlosAPIException {

		RoleModuleRightsModel tempRoleModuleRightsModel;

		Set<RoleModuleRightsModel> existRoleModuleRightsModels = new HashSet<>();
		Set<RoleModuleRightsModel> toDeleteRoleModuleRightsModels = new HashSet<>();
		Set<RoleModuleRightsModel> toAddRoleModuleRightsModels = new HashSet<>();

		for (RoleModuleRightsView roleModuleRightsView : roleView.getRoleModuleRightsViews()) {
			tempRoleModuleRightsModel = new RoleModuleRightsModel();
			ModuleEnum moduleEnum = ModuleEnum.fromId(roleModuleRightsView.getModuleView().getId());
			if (moduleEnum == null) {
				throw new EndlosAPIException(ResponseCode.MODULE_IS_INVALID.getCode(),
						ResponseCode.MODULE_IS_INVALID.getMessage());
			}
			RightsEnum rightsEnum = RightsEnum.fromId(roleModuleRightsView.getRightsView().getId());
			if (rightsEnum == null) {
				throw new EndlosAPIException(ResponseCode.RIGHT_IS_INVALID.getCode(),
						ResponseCode.RIGHT_IS_INVALID.getMessage());
			}
			tempRoleModuleRightsModel.setRoleId(roleModel.getId());
			tempRoleModuleRightsModel.setRightsId((long) rightsEnum.getId());
			tempRoleModuleRightsModel.setModuleId((long) moduleEnum.getId());
			if (roleModel.getRoleModuleRightsModels().contains(tempRoleModuleRightsModel) == false) {
				toAddRoleModuleRightsModels.add(tempRoleModuleRightsModel);
			} else {
				existRoleModuleRightsModels.add(tempRoleModuleRightsModel);
			}
		}

		for (RoleModuleRightsModel roleModuleRightsModel : roleModel.getRoleModuleRightsModels()) {
			if (existRoleModuleRightsModels.contains(roleModuleRightsModel) == false) {
				toDeleteRoleModuleRightsModels.add(roleModuleRightsModel);
			}
		}

		for (RoleModuleRightsModel roleModuleRightsModel : toDeleteRoleModuleRightsModels) {
			roleModel.removeRoleModuleRightsModels(roleModuleRightsModel);
		}
		for (RoleModuleRightsModel roleModuleRightsModel : toAddRoleModuleRightsModels) {
			roleModel.addRoleModuleRightsModels(roleModuleRightsModel);
		}
	}

	@Override
	public Response doDropdown(boolean isCustomer) throws EndlosAPIException {
		List<RoleModel> roleModels = roleService.getByUserType(isCustomer);
		if (roleModels == null || roleModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<RoleView> roleViews = new ArrayList<>();
		for (RoleModel roleModel : roleModels) {
			RoleView roleView = fromModel(roleModel);
			roleViews.add(roleView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				roleViews.size(), roleViews);
	}
}