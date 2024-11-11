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

import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.model.ModuleModel;
import com.intentlabs.common.user.model.RoleModuleRightsModel;

/**
 * This class used to implement all database related operation that will be
 * performed on module table.
 * 
 * @author Nirav.Shah
 * @since 14/02/2018
 */
@Service(value = "moduleService")
public class ModuleServiceImpl extends AbstractService<ModuleModel> implements ModuleService, CustomInitializationBean {

	@Autowired
	RoleService roleService;

	@Override
	public String getEntityName() {
		return MODULE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void onStartUp() throws EndlosAPIException {
		List<ModuleModel> moduleModels = findAll();
		for (Entry<Integer, ModuleEnum> modulemap : ModuleEnum.MAP.entrySet()) {
			ModuleModel moduleModel = null;
			if (moduleModels != null && !moduleModels.isEmpty()) {
				moduleModel = moduleModels.stream()
						.filter(model -> model.getId().equals(Long.valueOf(modulemap.getKey()))).findAny().orElse(null);
			}
			if (moduleModel == null) {
				moduleModel = new ModuleModel();
				moduleModel.setId(modulemap.getKey().longValue());
				moduleModel.setName(modulemap.getValue().getName());
				create(moduleModel);
				setRoleModuleRights(moduleModel);
			}
		}
	}

	/**
	 * This method is used to insert a new created modules and related role at time
	 * of server startup. So no need to write migration query to assign module and
	 * rights to each role.
	 * 
	 * @param moduleModel
	 */
	private void setRoleModuleRights(ModuleModel moduleModel) {
		ModuleEnum moduleEnum = ModuleEnum.fromId(moduleModel.getId().intValue());
		switch (moduleEnum) {
		case USER:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case ROLE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case SETTING:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case NOTIFICATION:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case EMAIL_ACCOUNT:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case RESPONSE_MESSAGE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case MACHINE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case CUSTOMER:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case LOCATION:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case TRANSACTION:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case ERROR:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case DASHBOARD:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case BARCODE_STRUCTURE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case CHANGE_LOCATION:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case REPORT:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case MACHINE_LOG:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			break;
		case MACHINE_BARCODE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		case PICKUP_ROUTE:
			setGlobalAdminRoleRight(moduleEnum, moduleModel);
			setCustomerAdminRoleRight(moduleEnum, moduleModel);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * This method is used to setup module wise rights for global admin role.
	 * 
	 * @param moduleEnum
	 * @param moduleModel
	 */
	private void setGlobalAdminRoleRight(ModuleEnum moduleEnum, ModuleModel moduleModel) {
		for (RightsEnum rightsEnum : moduleEnum.getAssignedRights()) {
			RoleModuleRightsModel roleModuleRightsModel = new RoleModuleRightsModel();
			roleModuleRightsModel.setModuleId(moduleModel.getId());
			roleModuleRightsModel.setRightsId(Long.valueOf(rightsEnum.getId()));
			roleModuleRightsModel.setRoleId(1l);
			getSession().save("roleModuleRightsModelEntity", roleModuleRightsModel);
		}
	}

	/**
	 * 
	 * This method is used to setup module wise rights for Customer admin role.
	 * 
	 * @param moduleEnum
	 * @param moduleModel
	 */
	private void setCustomerAdminRoleRight(ModuleEnum moduleEnum, ModuleModel moduleModel) {
		for (RightsEnum rightsEnum : moduleEnum.getAssignedRights()) {
			RoleModuleRightsModel roleModuleRightsModel = new RoleModuleRightsModel();
			roleModuleRightsModel.setModuleId(moduleModel.getId());
			roleModuleRightsModel.setRightsId(Long.valueOf(rightsEnum.getId()));
			roleModuleRightsModel.setRoleId(2l);
			getSession().save("roleModuleRightsModelEntity", roleModuleRightsModel);
		}
	}
}