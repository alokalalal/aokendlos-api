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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.UserOrderParameterEnum;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.model.UserSearchModel;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.endlos.customer.model.CustomerModel;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService<UserModel> implements UserService {

	@Override
	public String getEntityName() {
		return USER_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		criteria.add(Restrictions.eq("archive", false));
		criteria.add(Restrictions.eq("active", true));
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		UserView userView = (UserView) searchObject;
		if (userView.getArchive() != null && userView.getArchive()) {
			commonCriteria.add(Restrictions.eq("archive", true));
		} else {
			commonCriteria.add(Restrictions.eq("archive", false));
		}
		if (userView.getActive() != null) {
			commonCriteria.add(Restrictions.eq("active", userView.getActive()));
		}
		if (userView.getHasLoggedIn() != null) {
			commonCriteria.add(Restrictions.eq("hasLoggedIn", userView.getHasLoggedIn()));
		}
		if (userView.getCustomerView() != null) {
			commonCriteria.createAlias("customerModels", "customerModels");
			if (userView.getCustomerView().getId() != null) {
				commonCriteria.add(Restrictions.eq("customerModels.id", userView.getCustomerView().getId()));
			}
		}
		if (userView.getRoleView() != null) {
			commonCriteria.createAlias("roleModels", "roleModels");
			if (userView.getRoleView().getId() != null) {
				commonCriteria.add(Restrictions.eq("roleModels.id", userView.getRoleView().getId()));
			}
		}
		return commonCriteria;
	}

	@Override
	public UserModel findByEmail(String email) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("archive", false));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel findByMobile(String mobile) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("mobile", mobile));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel findByToken(String token) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("verifyToken", token));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel findByResetPasswordToken(String token) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("resetPasswordToken", token));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel getLight(Long id) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("archive", false));
		criteria.add(Restrictions.eq("id", id));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel getExtraLight(Long id) {
		Criteria criteria = getSession().createCriteria(EXTRA_LIGHT_USER_MODEL);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("archive", false));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public void insertSearchParam(Long userId) {
		Query query = getSession().getNamedQuery(INSERT_USER_SEARCH_PARAM);
		query.setParameter("id", userId);
		query.executeUpdate();
	}

	@Override
	public void updateSearchParam(Long userId) {
		Query query = getSession().getNamedQuery(UPDATE_USER_SEARCH_PARAM);
		query.setParameter("id", userId);
		query.executeUpdate();
	}

	@Override
	public List<UserSearchModel> fullTextSearch(String searchParam) {
		searchParam = searchParam.trim();
		if (searchParam.contains(" ")) {
			searchParam = searchParam.replaceAll(" +", " | ");
		}
		String querystring = "select fkuserId as userId from tblusersearch where ts_rank_cd(to_tsvector('simple',txtsearchparam) ,to_tsquery('simple','"
				+ searchParam
				+ ":*')) > 0 order by ts_rank_cd(to_tsvector('simple',txtsearchparam) ,to_tsquery('simple','"
				+ searchParam + ":*')) desc";
		Query query = getSession().createSQLQuery(querystring).addScalar("userId", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(UserSearchModel.class));
		return (List<UserSearchModel>) query.getResultList();
	}

	@Override
	public PageModel searchLight(UserView userView, List<UserSearchModel> userSearchModels, Integer start,
			Integer recordSize, Integer orderType, Integer orderParam) {
		Criteria criteria = getSession().createCriteria(LIGHT_USER_MODEL);
		if (Auditor.getAuditor().getRequestedCustomerModel() != null) {
			criteria.createAlias("customerModels", "customerModels");
			criteria.add(
					Restrictions.eq("customerModels.id", Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		setSearchCriteria(userView, criteria);
		if (userSearchModels != null && !userSearchModels.isEmpty()) {
			List<Long> ids = new ArrayList<>();
			for (UserSearchModel userSearchModel : userSearchModels) {
				ids.add(userSearchModel.getUserId());
			}
			criteria.add(Restrictions.in("id", ids));
		}

		long records = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		if (start != null && recordSize != null) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(recordSize);
		}
		setOrder(criteria, orderType, orderParam);
		List<UserModel> results = criteria.list();

		return PageModel.create(results, records);
	}

	@Override
	public UserModel nonVerifiedUser(Long id) {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("archive", false));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public UserModel getCustomerAdmin(CustomerModel customerModel) {
		Criteria criteria = setCommonCriteria(LIGHT_USER_MODEL);
		criteria.createAlias("customerModels", "customerModels");
		criteria.add(Restrictions.eq("customerModels.id", customerModel.getId()));
		criteria.add(Restrictions.eq("customerAdmin", true));
		return (UserModel) criteria.uniqueResult();
	}

	@Override
	public List<UserModel> doExport(UserView userView, Integer orderType, Integer orderParam) {
		Criteria commonCriteria = getSession().createCriteria(USER_MODEL);
		commonCriteria = setSearchCriteria(userView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<UserModel>) commonCriteria.list();
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return UserOrderParameterEnum.class;
	}
}