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

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.model.UserSearchModel;
import com.intentlabs.common.user.view.UserView;
import com.intentlabs.endlos.customer.model.CustomerModel;

/**
 * 
 * @author Nirav
 * @since 11/09/2018
 */
public interface UserService extends BaseService<UserModel> {

	String USER_MODEL = "userModel";
	String LIGHT_USER_MODEL = "lightUserModel";
	String EXTRA_LIGHT_USER_MODEL = "extraLightUserModel";
	String EXTRA_EXTRA_LIGHT_USER_MODEL = "extraExtraLightUserModel";
	String INSERT_USER_SEARCH_PARAM = "insertUserSearchParam";
	String UPDATE_USER_SEARCH_PARAM = "updateUserSearchParam";

	/**
	 * This method is used to find user through their email id.
	 * 
	 * @param email
	 * @return
	 */
	UserModel findByEmail(String email);

	/**
	 * This method is used to find user through their mobile.
	 * 
	 * @param mobile
	 * @return
	 */
	UserModel findByMobile(String mobile);

	/**
	 * This method is used to find user based on verification token.
	 * 
	 * @param token
	 * @return
	 */
	UserModel findByToken(String token);

	/**
	 * This method is used to find user based on reset password token.
	 * 
	 * @param token
	 * @return
	 */
	UserModel findByResetPasswordToken(String token);

	/**
	 * This method is used to get light entity.
	 * 
	 * @param id
	 * @return
	 */
	UserModel getLight(Long id);

	/**
	 * This method is used to get extra light user id.
	 * 
	 * @param id
	 * @return
	 */
	UserModel getExtraLight(Long id);

	/**
	 * This method is used to insert search param of user.
	 * 
	 * @param userId
	 */
	void insertSearchParam(Long userId);

	/**
	 * This method is used to update search param of user.
	 * 
	 * @param userId
	 */
	void updateSearchParam(Long userId);

	/**
	 * This method is used to search user id based on given search parameter using
	 * postgresql's full text search.
	 * 
	 * @param searchParam
	 * @return
	 */
	List<UserSearchModel> fullTextSearch(String searchParam);

	/**
	 * This method is used to search user list using multiple filter parameters.
	 * 
	 * @param userView
	 * @param userSearchModels
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 */
	PageModel searchLight(UserView userView, List<UserSearchModel> userSearchModels, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam);

	/**
	 * This method is used to get non verified user
	 * 
	 * @param id
	 * @return
	 */
	UserModel nonVerifiedUser(Long id);

	/**
	 * This method is used to get customer admin.
	 * 
	 * @param id
	 * @return
	 */
	UserModel getCustomerAdmin(CustomerModel customerModel);

	/**
	 * This method is used to export users .
	 * 
	 * @param userView
	 */
	List<UserModel> doExport(UserView userView, Integer orderType, Integer orderParam);
}