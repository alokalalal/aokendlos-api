/*******************************************************************************
 * Copyright -2018 @intentlabs
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
package com.intentlabs.common.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.notification.operation.NotificationOperation;
import com.intentlabs.common.notification.view.NotificationView;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;

/**
 * This Controller maps all API related to notification.
 * 
 * @author Nirav.Shah
 * @since 17/07/2018
 *
 */
@Controller
@RequestMapping("/private/notification")
public class NotificationPrivateController extends AbstractController<NotificationView> {

	@Autowired
	NotificationOperation notificationOperation;

	@Override
	public BaseOperation<NotificationView> getOperation() {
		return notificationOperation;
	}

	@Override
	@AccessLog
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response save(@RequestBody NotificationView notificationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response update(@RequestBody NotificationView notificationView) throws EndlosAPIException {
		if (notificationView == null || notificationView.getId() == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return notificationOperation.doUpdate(notificationView);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.LIST)
	public Response search(@RequestBody NotificationView notificationView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return notificationOperation.doSearch(notificationView, start, recordSize, orderType, orderParam);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.VIEW)
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return notificationOperation.doView(id);
	}

	@Override
	@AccessLog
	@Authorization(modules = ModuleEnum.NOTIFICATION, rights = RightsEnum.UPDATE)
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return notificationOperation.doEdit(id);
	}

	@Override
	@AccessLog
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());

	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public void isValidSaveData(NotificationView notificationView) throws EndlosAPIException {
		// Do nothing
	}
}