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

import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.model.EmailContentModel;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.view.EmailContentView;
import com.intentlabs.common.service.BaseService;

/**
 * This is declaration of email content service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 12/08/2017
 */
public interface EmailContentService extends BaseService<EmailContentModel> {
	String EMAIL_CONTENT = "emailContent";
	String LIGHT_EMAIL_CONTENT = "lightEmailContent";

	/**
	 * Used to search light email content
	 * @param emailContentView
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 */
	PageModel searchLight(EmailContentView emailContentView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam);

	/**
	 * This method is used to delete.
	 * 
	 * @param emailContentModel
	 */
	void hardDelete(EmailContentModel emailContentModel);

	/**
	 * Used to find email content by notification id.
	 * 
	 * @param notificationModel
	 * @return
	 */
	EmailContentModel findByNotification(NotificationModel notificationModel);
}