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

import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.service.BaseService;

/**
 * This is declaration of notification service which defines database operation
 * which can be performed on this table.
 * 
 * @author Nirav.Shah
 * @since 23/05/2020
 */
public interface NotificationService extends BaseService<NotificationModel> {
	String NOTIFICATION_MODEL = "notificationModel";

	/**
	 * This method is used to find all list of notification order by id.
	 * 
	 * @return
	 */
	List<NotificationModel> customFindAll();
}
