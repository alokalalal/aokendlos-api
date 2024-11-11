/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.notification.service;

import java.util.List;

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.model.EmailAccountModel;
import com.intentlabs.common.notification.view.EmailAccountView;
import com.intentlabs.common.service.BaseService;

/**
 * This is declaration of email account service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 12/08/2017
 */
public interface EmailAccountService extends BaseService<EmailAccountModel> {
	String EMAIL_ACCOUNT = "emailAccount";
	String LIGHT_EMAIL_ACCOUNT = "lightEmailAccount";

	/**
	 * This method is used to get email account details.
	 * 
	 * @param id
	 * @return
	 */
	EmailAccountModel getLight(Long id);

	/**
	 * This method is used to search email accounts.
	 * 
	 * @return
	 */
	List<EmailAccountModel> findAllByLight();

	/**
	 * This method is used to search email accounts based on light entity.
	 * 
	 * @param emailAccountView
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 */
	PageModel searchByLight(EmailAccountView emailAccountView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam);

	/**
	 * 
	 * @param name
	 * @return
	 */
	EmailAccountModel getByName(String name);

	/**
	 * To delete an account.
	 * 
	 * @param emailAccountModel
	 */
	void hardDelete(EmailAccountModel emailAccountModel);

}
