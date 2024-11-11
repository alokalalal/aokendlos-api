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
package com.intentlabs.common.notification.operation;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.enums.NotificationEnum;
import com.intentlabs.common.notification.model.EmailAccountModel;
import com.intentlabs.common.notification.model.EmailContentModel;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.service.EmailAccountService;
import com.intentlabs.common.notification.service.EmailContentService;
import com.intentlabs.common.notification.view.EmailContentView;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.view.KeyValueView;

/**
 * This class used to perform all business operation on program model.
 * 
 * @author Nisha.Panchal
 * @since 23/07/2018
 */

@Component(value = "emailContentOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class EmailContentOperationImpl extends AbstractOperation<EmailContentModel, EmailContentView>
		implements EmailContentOperation {
	@Autowired
	EmailContentService emailContentService;

	@Autowired
	EmailAccountService emailAccountService;

	@Autowired
	EmailAccountOperationImpl emailAccountOperation;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		EmailContentModel emailContentModel = emailContentService.get(id);
		if (emailContentModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		EmailContentView emailContentView = fromModel(emailContentModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				emailContentView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	protected EmailContentModel loadModel(EmailContentView emailContentView) {
		return emailContentService.get(emailContentView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		EmailContentModel emailContentModel = emailContentService.get(id);
		if (emailContentModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		emailContentService.hardDelete(emailContentModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public EmailContentModel toModel(EmailContentModel emailContentModel, EmailContentView emailContentView) {
		emailContentModel.setSubject(emailContentView.getSubject());
		emailContentModel.setContent(emailContentView.getContent());
		emailContentModel.setEmailBcc(emailContentView.getEmailBcc());
		emailContentModel.setEmailCc(emailContentView.getEmailCc());
		return emailContentModel;
	}

	@Override
	protected EmailContentModel getNewModel() {
		return new EmailContentModel();
	}

	@Override
	public EmailContentView fromModel(EmailContentModel emailContentModel) {
		EmailContentView emailContentView = new EmailContentView();
		emailContentView.setId(emailContentModel.getId());
		emailContentView.setSubject(emailContentModel.getSubject());
		emailContentView.setContent(emailContentModel.getContent());
		emailContentView.setEmailBcc(emailContentModel.getEmailBcc());
		emailContentView.setEmailCc(emailContentModel.getEmailCc());
		emailContentView.setNotificationView(KeyValueView.create(emailContentModel.getNotificationModel().getId(),
				emailContentModel.getNotificationModel().getName()));
		if (emailContentModel.getEmailAccountId() != null) {
			EmailAccountModel emailAccountModel = EmailAccountModel.getMAP().get(emailContentModel.getEmailAccountId());
			if (emailAccountModel == null) {
				emailAccountModel = emailAccountService.load(emailContentModel.getEmailAccountId());
			}
			emailContentView
					.setEmailAccountView(KeyValueView.create(emailAccountModel.getId(), emailAccountModel.getName()));
		}
		return emailContentView;
	}

	@Override
	public BaseService<EmailContentModel> getService() {
		return emailContentService;
	}

	@Override
	protected void checkInactive(EmailContentModel model) throws EndlosAPIException {

	}

	@Override
	public Response doSave(EmailContentView emailContentView) throws EndlosAPIException {
		EmailContentModel emailContentModel = emailContentService
				.findByNotification(NotificationModel.getMAP().get(emailContentView.getNotificationView().getKey()));
		if (emailContentModel != null) {
			throw new EndlosAPIException(ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getCode(),
					ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getMessage());
		}
		emailContentModel = getModel(emailContentView);
		EmailAccountModel emailAccountModel = emailAccountService
				.getLight(emailContentView.getEmailAccountView().getKey());
		if (emailAccountModel == null) {
			throw new EndlosAPIException(ResponseCode.EMAIL_ACOUNT_IS_INVALID.getCode(),
					ResponseCode.EMAIL_ACOUNT_IS_INVALID.getMessage());
		}
		emailContentModel.setEmailAccountId(emailAccountModel.getId());
		emailContentModel
				.setNotificationModel(NotificationModel.getMAP().get(emailContentView.getNotificationView().getKey()));
		emailContentService.create(emailContentModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doUpdate(EmailContentView emailContentView) throws EndlosAPIException {
		EmailContentModel emailContentModel = emailContentService.get(emailContentView.getId());
		if (emailContentModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		if (!emailContentModel.getNotificationModel().getId().equals(Long.valueOf(
				NotificationEnum.fromId(emailContentView.getNotificationView().getKey().intValue()).getId()))) {
			EmailContentModel tempEmailContentModel = emailContentService.findByNotification(
					NotificationModel.getMAP().get(emailContentView.getNotificationView().getKey()));
			if (tempEmailContentModel != null) {
				throw new EndlosAPIException(ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getCode(),
						ResponseCode.EMAIL_CONTENT_ALREADY_EXIST.getMessage());
			}
		}

		EmailAccountModel emailAccountModel = emailAccountService
				.getLight(emailContentView.getEmailAccountView().getKey());
		if (emailAccountModel == null) {
			throw new EndlosAPIException(ResponseCode.EMAIL_ACOUNT_IS_INVALID.getCode(),
					ResponseCode.EMAIL_ACOUNT_IS_INVALID.getMessage());
		}
		emailContentModel.setEmailAccountId(emailAccountModel.getId());
		emailContentModel
				.setNotificationModel(NotificationModel.getMAP().get(emailContentView.getNotificationView().getKey()));
		emailContentService.update(toModel(emailContentModel, emailContentView));
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());

	}

	@Override
	public Response doSearch(EmailContentView emailContentView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel result = emailContentService.searchLight(emailContentView, start, recordSize, orderType, orderParam);
		if (result.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				result.getRecords(), fromModelList((List<EmailContentModel>) result.getList()));
	}

	@Override
	public Response doDisplayGrid(Integer start, Integer recordSize) {
		return null;
	}
}