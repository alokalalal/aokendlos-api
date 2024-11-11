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
package com.intentlabs.endlos.barcodemachine.operation;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeItemModel;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeItemService;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeService;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
@Component(value = "MachineBarcodeItemOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MachineBarcodeItemOperationImpl extends AbstractOperation<MachineBarcodeItemModel, MachineBarcodeItemView> implements MachineBarcodeItemOperation {

	@Autowired
	MachineBarcodeItemService machineBarcodeItemService;
	@Autowired
	MachineBarcodeService machineBarcodeService;
	@Override
	public BaseService<MachineBarcodeItemModel> getService() {
		return machineBarcodeItemService;
	}
	@Override
	public Response doSave(MachineBarcodeItemView machineBarcodeItemView) throws EndlosAPIException {
		MachineBarcodeItemModel machineBarcodeItemModel = toModel(getNewModel(), machineBarcodeItemView);

		machineBarcodeItemService.create(machineBarcodeItemModel);

		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(), ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(MachineBarcodeItemView machineBarcodeFileView, Integer start, Integer recordSize, Integer orderType, Integer orderParam) throws EndlosAPIException {

		PageModel pageModel = machineBarcodeItemService.search(machineBarcodeFileView, start, recordSize, orderType, orderParam);

		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MachineBarcodeItemModel>) pageModel.getList()));
	}

	@Override
	public MachineBarcodeItemModel toModel(MachineBarcodeItemModel machineBarcodeItemModel, MachineBarcodeItemView machineBarcodeItemView) throws EndlosAPIException {
		machineBarcodeItemModel.setBarcodeName(machineBarcodeItemView.getBarcodeName());
		machineBarcodeItemModel.setMaterialType(machineBarcodeItemView.getMaterialType());
		//machineBarcodeItemModel.setMaterial(machineBarcodeItemView.getMaterial());
		machineBarcodeItemModel.setItemVolume(machineBarcodeItemView.getItemVolume());
		machineBarcodeItemModel.setItemWeight(machineBarcodeItemView.getItemWeight());
		machineBarcodeItemModel.setItemValue(machineBarcodeItemView.getItemValue());

		/*if(machineBarcodeItemView.getMachineBarcodeFileView().getId() != null) {
			//MachineBarcodeFileModel machineBarcodeFileModel = new MachineBarcodeFileModel();
			MachineBarcodeFileModel machineBarcodeFileModel = machineBarcodeService.get(machineBarcodeItemView.getMachineBarcodeFileView().getId());
			//machineBarcodeFileModel.setId(machineBarcodeItemView.getMachineBarcodeFileView().getId());
			machineBarcodeItemModel.setMachineBarcodeFileModel(machineBarcodeFileModel);
		}*/
		return machineBarcodeItemModel;
	}

	@Override
	protected MachineBarcodeItemModel getNewModel() {
		return new MachineBarcodeItemModel();
	}

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
		return null;
	}

	@Override
	protected MachineBarcodeItemModel loadModel(MachineBarcodeItemView view) {
		return null;
	}


	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		MachineBarcodeItemModel machineBarcodeItemModel = machineBarcodeItemService.get(id);


		if (machineBarcodeItemModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		machineBarcodeItemService.delete(machineBarcodeItemModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());


	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}


	@Override
	public MachineBarcodeItemView fromModel(MachineBarcodeItemModel model) {
		MachineBarcodeItemView  machineBarcodeItemView = new MachineBarcodeItemView();
		machineBarcodeItemView.setId(model.getId());
		machineBarcodeItemView.setBarcodeName(model.getBarcodeName());
		machineBarcodeItemView.setMaterialType(model.getMaterialType());
		machineBarcodeItemView.setItemVolume(model.getItemVolume());
		machineBarcodeItemView.setItemWeight(model.getItemWeight());
		machineBarcodeItemView.setItemValue(model.getItemValue());
		return machineBarcodeItemView;
	}


	@Override
	protected void checkInactive(MachineBarcodeItemModel model) throws EndlosAPIException {

	}
}