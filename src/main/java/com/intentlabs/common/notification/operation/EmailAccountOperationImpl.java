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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.notification.model.EmailAccountModel;
import com.intentlabs.common.notification.service.EmailAccountService;
import com.intentlabs.common.notification.view.EmailAccountView;
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
 * @author Nirav.Shah
 * @since 17/07/2018
 *
 */
@Component(value = "emailAccountOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class EmailAccountOperationImpl extends AbstractOperation<EmailAccountModel, EmailAccountView>
		implements EmailAccountOperation {

	@Autowired
	EmailAccountService emailAccountService;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		EmailAccountModel emailAccountModel = emailAccountService.get(id);
		if (emailAccountModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		EmailAccountView emailAccountView = fromModel(emailAccountModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				emailAccountView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	protected EmailAccountModel loadModel(EmailAccountView emailAccountView) {
		return emailAccountService.get(emailAccountView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		EmailAccountModel emailAccountModel = emailAccountService.get(id);
		if (emailAccountModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		emailAccountService.hardDelete(emailAccountModel);
		EmailAccountModel.removeEmailAccount(emailAccountModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());

	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public EmailAccountModel toModel(EmailAccountModel emailAccountModel, EmailAccountView emailAccountView)
			throws EndlosAPIException {
		emailAccountModel.setName(emailAccountView.getName());
		emailAccountModel.setHost(emailAccountView.getHost());
		emailAccountModel.setPort(Long.valueOf(emailAccountView.getPort()));
		emailAccountModel.setUsername(emailAccountView.getUserName());
		emailAccountModel.setPassword(emailAccountView.getPassword());
		emailAccountModel.setReplyToEmail(emailAccountView.getReplyToEmail());
		emailAccountModel.setEmailFrom(emailAccountView.getEmailFrom());
		emailAccountModel.setRatePerHour(emailAccountView.getRatePerHour());
		emailAccountModel.setUpdateRatePerHour(emailAccountView.getUpdateRatePerHour());
		emailAccountModel.setRatePerDay(emailAccountView.getRatePerDay());
		emailAccountModel.setUpdateRatePerDay(emailAccountView.getUpdateRatePerDay());
		emailAccountModel.setAuthenticationMethod(
				Integer.parseInt(String.valueOf(emailAccountView.getAuthenticationMethod().getKey())));
		emailAccountModel.setAuthenticationSecurity(
				Integer.parseInt(String.valueOf(emailAccountView.getAuthenticationSecurity().getKey())));
		if (emailAccountView.getTimeOut() == null) {
			emailAccountModel.setTimeOut(60000l);
		} else {
			emailAccountModel.setTimeOut(emailAccountView.getTimeOut());
		}

		return emailAccountModel;
	}

	@Override
	protected EmailAccountModel getNewModel() {
		return new EmailAccountModel();
	}

	@Override
	public EmailAccountView fromModel(EmailAccountModel emailAccountModel) {
		EmailAccountView emailAccountView = new EmailAccountView();
		emailAccountView.setId(emailAccountModel.getId());
		emailAccountView.setName(emailAccountModel.getName());
		emailAccountView.setHost(emailAccountModel.getHost());
		if (emailAccountModel.getPort() != null) {
			emailAccountView.setPort(emailAccountModel.getPort().toString());
		}
		emailAccountView.setUserName(emailAccountModel.getUsername());
		emailAccountView.setPassword(emailAccountModel.getPassword());
		emailAccountView.setReplyToEmail(emailAccountModel.getReplyToEmail());
		emailAccountView.setEmailFrom(emailAccountModel.getEmailFrom());
		if (emailAccountModel.getRatePerHour() != null) {
			emailAccountView.setRatePerHour(emailAccountModel.getRatePerHour());
		}
		if (emailAccountModel.getUpdateRatePerHour() != null) {
			emailAccountView.setUpdateRatePerHour(emailAccountModel.getUpdateRatePerHour());
		}
		if (emailAccountModel.getRatePerDay() != null) {
			emailAccountView.setRatePerDay(emailAccountModel.getRatePerDay());
		}
		if (emailAccountModel.getUpdateRatePerDay() != null) {
			emailAccountView.setUpdateRatePerDay(emailAccountModel.getUpdateRatePerDay());
		}
		if (emailAccountModel.getAuthenticationMethod() != null) {
			emailAccountView.setAuthenticationMethod(
					KeyValueView.create(Long.valueOf(emailAccountModel.getAuthenticationMethod().getId()),
							emailAccountModel.getAuthenticationMethod().getName()));
		}
		if (emailAccountModel.getAuthenticationSecurity() != null) {
			emailAccountView.setAuthenticationSecurity(
					KeyValueView.create(Long.valueOf(emailAccountModel.getAuthenticationSecurity().getId()),
							emailAccountModel.getAuthenticationSecurity().getName()));
		}
		if (emailAccountModel.getTimeOut() != null) {
			emailAccountView.setTimeOut(emailAccountModel.getTimeOut());
		}
		return emailAccountView;
	}

	@Override
	public BaseService<EmailAccountModel> getService() {
		return emailAccountService;
	}

	@Override
	protected void checkInactive(EmailAccountModel emailAccountModel) throws EndlosAPIException {

	}

	@Override
	public Response doSave(EmailAccountView emailAccountView) throws EndlosAPIException {
		EmailAccountModel emailAccountModelExist = emailAccountService.getByName(emailAccountView.getName());
		if (emailAccountModelExist != null) {
			throw new EndlosAPIException(ResponseCode.EMAIL_ACCOUNT_ALREADY_EXIST.getCode(),
					ResponseCode.EMAIL_ACCOUNT_ALREADY_EXIST.getMessage());
		}
		EmailAccountModel emailAccountModel = getModel(emailAccountView);
		emailAccountModel.setActive(true);
		emailAccountService.create(emailAccountModel);
		EmailAccountModel.addEmailAccount(emailAccountModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doUpdate(EmailAccountView emailAccountView) throws EndlosAPIException {
		EmailAccountModel emailAccountModel = emailAccountService.get(emailAccountView.getId());
		if (emailAccountModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!emailAccountModel.getName().equals(emailAccountView.getName())) {
			EmailAccountModel emailAccountModelExist = emailAccountService.getByName(emailAccountView.getName());
			if (emailAccountModelExist != null) {
				throw new EndlosAPIException(ResponseCode.EMAIL_ACCOUNT_ALREADY_EXIST.getCode(),
						ResponseCode.EMAIL_ACCOUNT_ALREADY_EXIST.getMessage());
			}
		}
		emailAccountService.update(toModel(emailAccountModel, emailAccountView));
		EmailAccountModel.updateEmailAccount(emailAccountModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(EmailAccountView emailAccountView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = emailAccountService.searchByLight(emailAccountView, start, recordSize, orderType,
				orderParam);
		if (pageModel == null || (pageModel.getList() != null && pageModel.getList().isEmpty())) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.emptyList());
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<EmailAccountModel>) pageModel.getList()));
	}

	@Override
	public Response doDropdown() {
		EmailAccountView emailAccountView = new EmailAccountView();
		PageModel pageModel = emailAccountService.searchByLight(emailAccountView, null, null, null, null);
		List<KeyValueView> keyValueViews = new ArrayList<>();
		if (pageModel == null || (pageModel.getList() != null && pageModel.getList().isEmpty())) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.emptyList());
		}
		for (EmailAccountModel emailAccountModel : (List<EmailAccountModel>) pageModel.getList()) {
			keyValueViews.add(KeyValueView.create(emailAccountModel.getId(), emailAccountModel.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}
}