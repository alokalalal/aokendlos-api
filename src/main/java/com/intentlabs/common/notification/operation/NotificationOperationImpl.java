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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.service.NotificationService;
import com.intentlabs.common.notification.view.NotificationView;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.service.BaseService;

/**
 * This class used to perform all business operation on notification.
 * 
 * @author Nirav.Shah
 * @since 23/05/2020
 */

@Component(value = "notificationOperationImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class NotificationOperationImpl extends AbstractOperation<NotificationModel, NotificationView>
		implements NotificationOperation {
	@Autowired
	NotificationService notificationService;

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	protected NotificationModel loadModel(NotificationView notificationView) {
		return notificationService.get(notificationView.getId());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public NotificationModel toModel(NotificationModel notificationModel, NotificationView notificationView) {
		notificationModel.setEmail(notificationView.isEmail());
		notificationModel.setPush(notificationView.isPush());
		return notificationModel;
	}

	@Override
	protected NotificationModel getNewModel() {
		return new NotificationModel();
	}

	@Override
	public NotificationView fromModel(NotificationModel notificationModel) {
		NotificationView notificationView = new NotificationView();
		notificationView.setId(notificationModel.getId());
		notificationView.setEmail(notificationModel.isEmail());
		notificationView.setName(notificationModel.getName());
		notificationView.setPush(notificationModel.isPush());
		return notificationView;
	}

	@Override
	public BaseService<NotificationModel> getService() {
		return notificationService;
	}

	@Override
	protected void checkInactive(NotificationModel notificationModel) throws EndlosAPIException {

	}

	@Override
	public Response doSave(NotificationView notificationView) throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doUpdate(NotificationView notificationView) throws EndlosAPIException {
		NotificationModel notificationModel = notificationService.get(notificationView.getId());
		if (notificationModel == null) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		notificationService.update(toModel(notificationModel, notificationView));
		NotificationModel.getMAP().put(notificationModel.getId(), notificationModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());

	}

	@Override
	public Response doSearch(NotificationView notificationView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		List<NotificationView> notificationViews = new ArrayList<>();
		List<NotificationModel> notificationModels = notificationService.customFindAll();
		for (NotificationModel notificationModel : notificationModels) {
			notificationViews.add(fromModel(notificationModel));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				notificationViews.size(), notificationViews);
	}

	@Override
	public Response doDisplayGrid(Integer start, Integer recordSize) {
		return null;
	}
}