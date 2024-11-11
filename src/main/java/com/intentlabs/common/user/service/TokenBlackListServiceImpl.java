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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.user.model.TokenBlackListModel;

@Service(value = "tokenBlackListService")
public class TokenBlackListServiceImpl extends AbstractService<TokenBlackListModel> implements TokenBlackListService {

	@Override
	public String getEntityName() {
		return TOKEN_BLACK_LIST_MODEL;
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
	public TokenBlackListModel findByUserAndToken(Long userId, String token) {
		Criteria criteria = getSession().createCriteria(TOKEN_BLACK_LIST_MODEL);
		criteria.createAlias("userModel", "userModel");
		criteria.add(Restrictions.eq("userModel.id", userId));
		criteria.add(Restrictions.eq("jwtToken", token));
		return (TokenBlackListModel) criteria.uniqueResult();
	}
}