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
package com.intentlabs.endlos.barcodestructure.operation;

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
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodestructure.enums.BarcodeTypeEnum;
import com.intentlabs.endlos.barcodestructure.enums.DynamicValueEnum;
import com.intentlabs.endlos.barcodestructure.model.BarcodeStructureModel;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.barcodestructure.service.BarcodeStructureService;
import com.intentlabs.endlos.barcodestructure.service.BarcodeTemplateService;
import com.intentlabs.endlos.barcodestructure.view.BarcodeStructureView;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;

/**
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
@Component(value = "barcodeStructureOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class BarcodeStructureOperationImpl extends AbstractOperation<BarcodeStructureModel, BarcodeStructureView>
		implements BarcodeStructureOperation {

	@Autowired
	private BarcodeStructureService barcodeStructureService;

	@Autowired
	private BarcodeTemplateService barcodeTemplateService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<BarcodeStructureModel> getService() {
		return barcodeStructureService;
	}

	@Override
	protected BarcodeStructureModel loadModel(BarcodeStructureView barcodeStructureView) {
		return barcodeStructureService.get(barcodeStructureView.getId());
	}

	@Override
	protected BarcodeStructureModel getNewModel() {
		return new BarcodeStructureModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doSave(BarcodeStructureView barcodeStructureView) throws EndlosAPIException {

		BarcodeStructureModel barcodeStructureModel = toModel(getNewModel(), barcodeStructureView);

		barcodeStructureService.create(barcodeStructureModel);
		BarcodeStructureView barcodeStructureModelView = fromModel(barcodeStructureModel);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				barcodeStructureModelView);
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		BarcodeStructureModel barcodeStructureModel = barcodeStructureService.get(id);
		if (barcodeStructureModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		BarcodeStructureView barcodeStructureView = fromModel(barcodeStructureModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				barcodeStructureView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		BarcodeStructureModel barcodeStructureModel = barcodeStructureService.get(barcodeStructureView.getId());
		if (barcodeStructureModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineModel> machineModels = machineService
				.findByBarcodeTemplateId(barcodeStructureModel.getBarcodeTemplateModel().getId());
		if (!machineModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_EDIT_BARCODE_TEMPLATE.getCode(),
					ResponseCode.CAN_NOT_EDIT_BARCODE_TEMPLATE.getMessage());
		}
		barcodeStructureModel = toModel(barcodeStructureModel, barcodeStructureView);
		barcodeStructureService.update(barcodeStructureModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(BarcodeStructureView barcodeStructureView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = barcodeStructureService.search(barcodeStructureView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<BarcodeStructureModel>) pageModel.getList()));
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		BarcodeStructureModel barcodeStructureModel = barcodeStructureService.get(id);
		if (barcodeStructureModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineModel> machineModels = machineService
				.findByBarcodeTemplateId(barcodeStructureModel.getBarcodeTemplateModel().getId());
		if (!machineModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.CAN_NOT_DELETE_BARCODE_TEMPLATE.getCode(),
					ResponseCode.CAN_NOT_DELETE_BARCODE_TEMPLATE.getMessage());
		}
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService
				.get(barcodeStructureModel.getBarcodeTemplateModel().getId());
		barcodeTemplateModel
				.setCurrentLength(barcodeTemplateModel.getCurrentLength() - barcodeStructureModel.getLength());
		barcodeTemplateModel.setCompleted(false);
		barcodeTemplateService.update(barcodeTemplateModel);
		barcodeStructureService.hardDelete(barcodeStructureModel);
		return CommonResponse.create(ResponseCode.DELETE_SUCCESSFULLY.getCode(),
				ResponseCode.DELETE_SUCCESSFULLY.getMessage());
	}

	@Override
	protected void checkInactive(BarcodeStructureModel barcodeStructureModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public BarcodeStructureModel toModel(BarcodeStructureModel barcodeStructureModel,
			BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService
				.get(barcodeStructureView.getBarcodeTemplateView().getId());
		if (barcodeTemplateModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (barcodeStructureView.getDynamicValue() != null
				&& barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.CHECKEDSUM.getId()
				&& Long.valueOf(barcodeStructureView.getIndex()) != barcodeTemplateModel.getTotalLength()) {
			throw new EndlosAPIException(ResponseCode.LAST_INDEX_FOR_CHECKED_SUM.getCode(),
					ResponseCode.LAST_INDEX_FOR_CHECKED_SUM.getMessage());
		}
		barcodeStructureModel.setBarcodeTemplateModel(barcodeTemplateModel);
		Long length = barcodeTemplateModel.getTotalLength() - barcodeTemplateModel.getCurrentLength();
//		if (Long.parseLong(barcodeStructureView.getLength()) > length) {
//			throw new EndlosAPIException(ResponseCode.LENGTH_IS_INVALID.getCode(),
//					ResponseCode.LENGTH_IS_INVALID.getMessage());
//		}
		barcodeStructureModel.setFieldName(barcodeStructureView.getFieldName());
		barcodeStructureModel.setLength(Long.parseLong(barcodeStructureView.getLength()));
		barcodeStructureModel.setBarcodeType(
				BarcodeTypeEnum.fromId(barcodeStructureView.getBarcodeType().getKey().intValue()).getId());
		if (barcodeStructureView.getValue() != null) {
			barcodeStructureModel.setValue(barcodeStructureView.getValue());
		}
		if (barcodeStructureView.getEndValue() != null) {
			barcodeStructureModel.setEndValue(barcodeStructureView.getEndValue());
		}
		if (barcodeStructureView.getDynamicValue() != null && barcodeStructureView.getDynamicValue().getKey() != null) {
			barcodeStructureModel.setDynamicValue(
					DynamicValueEnum.fromId(barcodeStructureView.getDynamicValue().getKey().intValue()).getId());
		}
		barcodeTemplateModel
				.setCurrentLength(barcodeTemplateModel.getCurrentLength() + barcodeStructureModel.getLength());
		if (barcodeTemplateModel.getCurrentLength() == barcodeTemplateModel.getTotalLength()) {
			barcodeTemplateModel.setCompleted(true);
		}
		barcodeStructureModel.setIndex(barcodeStructureView.getIndex());
		barcodeTemplateService.update(barcodeTemplateModel);
		return barcodeStructureModel;
	}

	@Override
	public BarcodeStructureView fromModel(BarcodeStructureModel barcodeStructureModel) {
		BarcodeStructureView barcodeStructureView = new BarcodeStructureView();
		barcodeStructureView.setId(barcodeStructureModel.getId());
		barcodeStructureView.setFieldName(barcodeStructureModel.getFieldName());
		barcodeStructureView.setBarcodeType(KeyValueView.create(barcodeStructureModel.getBarcodeType().getId(),
				barcodeStructureModel.getBarcodeType().getName()));
		if (barcodeStructureModel.getDynamicValue() != null) {
			barcodeStructureView.setDynamicValue(KeyValueView.create(barcodeStructureModel.getDynamicValue().getId(),
					barcodeStructureModel.getDynamicValue().getName()));
		}
		if (barcodeStructureModel.getLength() != null) {
			barcodeStructureView.setLength(barcodeStructureModel.getLength().toString());
		}
		if (barcodeStructureModel.getValue() != null) {
			barcodeStructureView.setValue(barcodeStructureModel.getValue().toString());
		}
		BarcodeTemplateModel barcodeTemplateModel = barcodeTemplateService
				.get(barcodeStructureModel.getBarcodeTemplateModel().getId());
		BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
		barcodeStructureTemplateView.setName(barcodeTemplateModel.getName());
		barcodeStructureTemplateView.setId(barcodeTemplateModel.getId());
		barcodeStructureTemplateView.setTotalLength(barcodeTemplateModel.getTotalLength().toString());
		barcodeStructureView.setBarcodeTemplateView(barcodeStructureTemplateView);
		if (barcodeStructureModel.getIndex() != null) {
			barcodeStructureView.setIndex(barcodeStructureModel.getIndex());
		}
		if (barcodeStructureModel.getEndValue() != null) {
			barcodeStructureView.setEndValue(barcodeStructureModel.getEndValue().toString());
		}
		return barcodeStructureView;
	}
}