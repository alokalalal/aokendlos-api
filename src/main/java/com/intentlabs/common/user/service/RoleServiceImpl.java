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

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.kernal.CustomInitializationBean;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.RoleModuleRightsModel;
import com.intentlabs.common.user.view.RoleView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * This class used to implement all database related operation that will be
 * performed on role table.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@Service(value = "roleService")
public class RoleServiceImpl extends AbstractService<RoleModel> implements RoleService, CustomInitializationBean {

	@Override
	public String getEntityName() {
		return ROLE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		criteria.add(Restrictions.eq("active", true));
		criteria.add(Restrictions.eq("archive", false));
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		RoleView roleView = (RoleView) searchObject;

		if (!StringUtils.isEmpty(roleView.getName())) {
			commonCriteria.add(Restrictions.ilike("name", roleView.getName(), MatchMode.START));
		}

		return commonCriteria;
	}

	@Override
	public void onStartUp() throws EndlosAPIException {
		List<RoleModel> roleModels = (List<RoleModel>) findAll(EXTRA_LIGHT_ROLE_MODEL);
		for (RoleModel roleModel : roleModels) {
			RoleModel.getMAP().put(roleModel.getId(), roleModel);
		}
	}

	@Override
	public RoleModel getLight(long id) {
		return get(id, LIGHT_ROLE_MODEL);
	}

	@Override
	public RoleModel getExtraLight(long id) {
		return get(id, EXTRA_LIGHT_ROLE_MODEL);
	}

	public Set<RoleModuleRightsModel> getRights(long id) {
		Criteria criteria = setCommonCriteria(LIGHT_ROLE_MODEL);
		criteria.add(Restrictions.eq("id", id));
		RoleModel roleModel = (RoleModel) criteria.uniqueResult();
		return roleModel.getRoleModuleRightsModels();
	}

	@Override
	public void hardDelete(RoleModel roleModel) {
		getSession().delete(roleModel);
	}

	@Override
	public PageModel searchByLight(RoleView roleView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		return search(roleView, start, recordSize, EXTRA_LIGHT_ROLE_MODEL, orderType, orderParam);
	}

	@Override
	public List<RoleModel> getRights(List<Long> ids) {
		Criteria criteria = setCommonCriteria(LIGHT_ROLE_MODEL);
		criteria.add(Restrictions.in("id", ids));
		return (List<RoleModel>) criteria.list();
	}

	@Override
	public List<RoleModel> getByUserType(boolean isCustomer) {
		Criteria criteria = getSession().createCriteria(EXTRA_LIGHT_ROLE_MODEL);
		criteria.add(Restrictions.eq("customerRole", isCustomer));
		return (List<RoleModel>) criteria.list();
	}
	@Override
	public List<RoleModel> getByName(String name) {
		Criteria criteria = getSession().createCriteria(LIGHT_ROLE_MODEL);
		criteria.add(Restrictions.ilike("name", name));
		return (List<RoleModel>) criteria.list();
	}
	@Override
	public List<RoleModel> getByNameExcludingId(String name, Long currentId) {
		Criteria criteria = getSession().createCriteria(LIGHT_ROLE_MODEL);
		criteria.add(Restrictions.ilike("name", name));
		criteria.add(Restrictions.not(Restrictions.idEq(currentId))); // Exclude the current ID
		return (List<RoleModel>) criteria.list();
	}
}