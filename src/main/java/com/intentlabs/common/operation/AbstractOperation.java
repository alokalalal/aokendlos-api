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
package com.intentlabs.common.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.Model;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.view.IdentifierView;

/**
 * This class is provide transaction wrapper ,Actual transaction begin over here
 * contains common operation and list of abstract method
 * 
 * @author Nirav.Shah
 *
 * @param <M>
 * @param <V>
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public abstract class AbstractOperation<M extends Model, V extends IdentifierView> {

	/**
	 * This method is used to validate add request to add any entity into system.
	 * Once it is been validate. it allow users to add entity data.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	public abstract Response doAdd() throws EndlosAPIException;

	/**
	 * This method is used to save entity
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	public Response doSave(V view) throws EndlosAPIException {
		Model model = getModel(view);
		getService().create(model);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	/**
	 * This method is used to view entity.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	public abstract Response doView(Long id) throws EndlosAPIException;

	/**
	 * This method is used to edit entity.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	public abstract Response doEdit(Long id) throws EndlosAPIException;

	/**
	 * This method is used to update entity
	 * 
	 * @param view
	 * @return
	 * @throws EndlosAPIException
	 */
	public Response doUpdate(V view) throws EndlosAPIException {
		M model = loadModel(view);
		if (model == null) {
			return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		checkInactive(model);
		getService().update(toModel(model, view));
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	/**
	 * This method load original model from view
	 * 
	 * @param view view of model
	 * @return model
	 */
	protected abstract M loadModel(V view);

	/**
	 * This method is used delete existing data.
	 * 
	 * @param id
	 * @return
	 */
	public abstract Response doDelete(Long id) throws EndlosAPIException;

	/**
	 * This method is used active/inactive existing data.
	 * 
	 * @param id
	 * @return
	 */
	public abstract Response doActiveInActive(Long id) throws EndlosAPIException;

	/**
	 * This method used for grid display with pagination and Search Purpose
	 * 
	 * @param start      starting value of fetch record use for limit purpose
	 * @param recordSize end value of fetch record use for limit purposes
	 * @return Response
	 * @throws EndlosAPIException
	 */
	public Response doDisplayGrid(Integer start, Integer recordSize) throws EndlosAPIException {
		PageModel result = getService().getGridData(start, recordSize);
		if (result.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				result.getRecords(), fromModelList((List<M>) result.getList()));
	}

	/**
	 * This method used for search data base on queries.
	 * 
	 * @param view
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 * @throws EndlosAPIException
	 */
	public Response doSearch(V view, Integer start, Integer recordSize, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		PageModel result = getService().search(view, start, recordSize, orderType, orderParam);
		if (result.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				result.getRecords(), fromModelList((List<M>) result.getList()));
	}

	/**
	 * This method is used to prepare model from view.
	 * 
	 * @param model
	 * @param view
	 * @return
	 */
	public abstract M toModel(M model, V view) throws EndlosAPIException;

	/**
	 * This method is used to prepare model from view.
	 * 
	 * @param request
	 * @return
	 */
	protected M getModel(V view) throws EndlosAPIException {
		return toModel(getNewModel(), view);
	}

	/**
	 * This method used when require new model for view
	 * 
	 * @param view view of model
	 * @return model
	 */
	protected abstract M getNewModel();

	/**
	 * This method used when need to convert model to view
	 * 
	 * @param model
	 * @return view
	 */
	public abstract V fromModel(M model);

	/**
	 * This method convert list of model to list of view
	 * 
	 * @param modelList list of model
	 * @return list of view
	 */
	public List<V> fromModelList(List<M> modelList) {
		List<V> viewList = new ArrayList<>(modelList.size());
		for (M model : modelList) {
			viewList.add(fromModel(model));
		}
		return viewList;
	}

	/**
	 * This method use for get Service with respected operation
	 * 
	 * @return BaseService
	 */
	public abstract BaseService getService();

	/**
	 * This method is used to validate active or inactive state of model.
	 * 
	 * @param model
	 * @throws EndlosAPIException
	 */
	protected abstract void checkInactive(M model) throws EndlosAPIException;

	/**
	 * This method is used to check authorization access at operation layer if
	 * required.
	 * 
	 * @param methodName
	 * @param rightsEnum
	 * @param moduleEnum
	 * @throws EndlosAPIException
	 */
	public void checkAccess(String methodName, RightsEnum rightsEnum, ModuleEnum moduleEnum) throws EndlosAPIException {
		UserModel userModel = Auditor.getAuditor();
		if (userModel == null) {
			HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
			LoggerService.info(methodName, " Unauthorized access, " + httpServletRequest.getRequestURI(), "");
			throw new EndlosAPIException(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
					ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
		}
		boolean hasAccess = userModel.getRoleModels().stream()
				.anyMatch(roleModel -> userModel.hasAccess(roleModel.getId(),
						Integer.valueOf(moduleEnum.getId()).longValue(),
						Integer.valueOf(rightsEnum.getId()).longValue()));

		if (!hasAccess) {
			HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
			if (StringUtils.isBlank(userModel.getEmail())) {
				LoggerService.info(methodName, " Unauthorized access of, " + httpServletRequest.getRequestURI(),
						" by " + userModel.getMobile());
			} else {
				LoggerService.info(methodName, " Unauthorized access of, " + httpServletRequest.getRequestURI(),
						" by " + userModel.getEmail());
			}
			throw new EndlosAPIException(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
					ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
		}
	}
}
