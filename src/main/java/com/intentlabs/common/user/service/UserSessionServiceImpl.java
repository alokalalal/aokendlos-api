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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.user.model.UserSessionModel;

/**
 * This class used to implement all database related operation that will be
 * performed on user session table.
 * 
 * @author Nirav.Shah
 * @since 22/06/2018
 */
@Service(value = "userSessionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserSessionServiceImpl extends AbstractService<UserSessionModel> implements UserSessionService {

	@Override
	public String getEntityName() {
		return USER_SESSION_MODEL;
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
	public UserSessionModel get(String session) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.add(Restrictions.eq("session", session));
		return (UserSessionModel) criteria.uniqueResult();
	}

	@Override
	public UserSessionModel getByDeviceCookie(String deviceCookie, Long userId) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.add(Restrictions.eq("deviceCookie", deviceCookie));
		criteria.createAlias("userModel", "userModel");
		criteria.add(Restrictions.eq("userModel.id", userId));
		return (UserSessionModel) criteria.uniqueResult();
	}

	@Override
	public Long deviceUsed(Long userId) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.createAlias("userModel", "userModel");
		criteria.add(Restrictions.eq("userModel.id", userId));
		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void deleteLeastUnused(Long userId) {
		Query query = getSession().getNamedQuery("deleteLeastUnusedDevice");
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

	@Override
	public UserSessionModel getResetPassword(String session) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.add(Restrictions.eq("session", session));
		return (UserSessionModel) criteria.uniqueResult();
	}

	@Override
	public UserSessionModel getByDeviceAndSessionCookie(String sessionCookie, String deviceCookie, Long userId) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.add(Restrictions.eq("deviceCookie", deviceCookie));
		criteria.add(Restrictions.eq("session", sessionCookie));
		criteria.createAlias("userModel", "userModel");
		criteria.add(Restrictions.eq("userModel.id", userId));
		return (UserSessionModel) criteria.uniqueResult();
	}

	@Override
	public List<UserSessionModel> getByUser(Long userId) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.createAlias("userModel", "userModel");
		criteria.add(Restrictions.eq("userModel.id", userId));
		return (List<UserSessionModel>) criteria.list();
	}

	@Override
	public boolean isDeviceRegistered(String deviceCookie) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.add(Restrictions.eq("deviceCookie", deviceCookie));
		return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()) > 0 ? true : false;
	}

	@Override
	public List<UserSessionModel> getUserByDomainAndDevice(String deviceCookie, String domain) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		/*
		 * if (!"admin".equals(domain)) { criteria.createAlias("userModel",
		 * "userModel"); criteria.createAlias("userModel.hotelPartnerModel", "hotelPartnerModel");
		 * criteria.add(Restrictions.eq("hotelPartnerModel.domain", domain)); }
		 */
		criteria.add(Restrictions.eq("deviceCookie", deviceCookie));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<UserSessionModel>) criteria.list();
	}

	@Override
	public List<UserSessionModel> findByRole(Long id) {
		Criteria criteria = getSession().createCriteria(USER_SESSION_MODEL);
		criteria.createAlias("userModel", "userModel");
		criteria.createAlias("userModel.roleModels", "roleModel");
		criteria.add(Restrictions.eq("roleModel.id", id));
		return (List<UserSessionModel>) criteria.list();
	}
}