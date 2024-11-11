/*******************************************************************************
 * Copyright -2019 @Intentlabs
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
package com.intentlabs.common.setting.operation;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.ResponseMessageModel;
import com.intentlabs.common.setting.service.ResponseMessageService;
import com.intentlabs.common.setting.view.ResponseMessageView;

/**
 * This class used to perform all business operation on locale key.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@Component(value = "reponseMessageOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ResponseMessageOperationImpl extends AbstractOperation<ResponseMessageModel, ResponseMessageView>
		implements ResponseMessageOperation {

	@Autowired
	private ResponseMessageService responseMessageService;

	@Override
	public BaseService getService() {
		return responseMessageService;
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doSave(ResponseMessageView responseMessageView) throws EndlosAPIException {
		ResponseMessageModel responseMessageModel = toModel(getNewModel(), responseMessageView);
		responseMessageService.create(responseMessageModel);
		ResponseMessageModel.add(responseMessageModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response doUpdate(ResponseMessageView responseMessageView) throws EndlosAPIException {
		ResponseMessageModel responseMessageModel = responseMessageService.get(responseMessageView.getCode(),
				responseMessageView.getLocale());
		if (responseMessageModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		responseMessageModel = toModel(responseMessageModel, responseMessageView);
		responseMessageService.update(responseMessageModel);
		ResponseMessageModel.add(responseMessageModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	protected ResponseMessageModel loadModel(ResponseMessageView responseMessageView) {
		return responseMessageService.load(responseMessageView.getCode());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public ResponseMessageModel toModel(ResponseMessageModel responseMessageModel,
			ResponseMessageView responseMessageView) throws EndlosAPIException {
		responseMessageModel.setLocale(responseMessageView.getLocale());
		responseMessageModel.setCode(responseMessageView.getCode());
		responseMessageModel.setMessage(responseMessageView.getMessage());
		return responseMessageModel;
	}

	@Override
	protected ResponseMessageModel getNewModel() {
		return new ResponseMessageModel();
	}

	@Override
	public ResponseMessageView fromModel(ResponseMessageModel responseMessageModel) {
		ResponseMessageView responseMessageView = new ResponseMessageView();
		responseMessageView.setCode(responseMessageModel.getCode());
		responseMessageView.setLocale(responseMessageModel.getLocale());
		responseMessageView.setMessage(responseMessageModel.getMessage());
		return responseMessageView;
	}

	@Override
	protected void checkInactive(ResponseMessageModel model) throws EndlosAPIException {
		// Do nothing
	}

	@Override
	public Response doSearch(ResponseMessageView responseMessageView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel result = responseMessageService.search(responseMessageView, start, recordSize, orderType, orderParam);
		if (result.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				result.getRecords(), fromModelList((List<ResponseMessageModel>) result.getList()));
	}

	@Override
	public Response doViewEdit(Integer code, String locale) throws EndlosAPIException {
		ResponseMessageModel responseMessageModel = responseMessageService.get(code, locale);
		if (responseMessageModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		ResponseMessageView responseMessageView = fromModel(responseMessageModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				responseMessageView);
	}
}