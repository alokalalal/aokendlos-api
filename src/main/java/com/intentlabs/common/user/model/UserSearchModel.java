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
 * This is user full text search model which is used to search user based on
 * name, email & mobile number. This is very specific to postgres so when you
 * try to implement any other database please remove this class related code.
 * 
 * @author Nirav.Shah
 * @since 28/12/2019
 */
public class UserSearchModel implements Model {

	private static final long serialVersionUID = -5764068071467332650L;

	private Long userId;
	private String searchParam;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

}