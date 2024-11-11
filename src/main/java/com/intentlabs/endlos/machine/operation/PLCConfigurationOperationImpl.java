package com.intentlabs.endlos.machine.operation;

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
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.model.PLCConfigurationModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.PLCConfigurationService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.PLCConfigurationView;

@Component(value = "plcConfigurationOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class PLCConfigurationOperationImpl extends AbstractOperation<PLCConfigurationModel, PLCConfigurationView>
		implements PLCConfigurationOperation {
	@Autowired
	private PLCConfigurationService plcConfigurationService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService getService() {
		return plcConfigurationService;
	}

	@Override
	protected PLCConfigurationModel loadModel(PLCConfigurationView plcConfigurationView) {
		return plcConfigurationService.get(plcConfigurationView.getId());
	}

	@Override
	protected PLCConfigurationModel getNewModel() {
		return new PLCConfigurationModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		PLCConfigurationModel plcConfigurationModel = plcConfigurationService.get(id);
		if (plcConfigurationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				fromModel(plcConfigurationModel));
	}

	@Override
	public Response doSearch(PLCConfigurationView plcConfigurationView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) throws EndlosAPIException {
		PageModel pageModel = plcConfigurationService.search(plcConfigurationView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<PLCConfigurationModel>) pageModel.getList()));
	}

	@Override
	public PLCConfigurationModel toModel(PLCConfigurationModel plcConfigurationModel,
			PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		return plcConfigurationModel;
	}

	@Override
	public PLCConfigurationView fromModel(PLCConfigurationModel plcConfigurationModel) {
		PLCConfigurationView plcConfigurationView = new PLCConfigurationView();
		plcConfigurationView.setId(plcConfigurationModel.getId());
		plcConfigurationView.setBarcodeScannerResponseWait(plcConfigurationModel.getBarcodeScannerResponseWait());
		plcConfigurationView.setObjectDetectFirstResponseWait(plcConfigurationModel.getObjectDetectFirstResponseWait());
		plcConfigurationView.setPlcAddressGlassShredderCurrent(plcConfigurationModel.getPlcAddressGlassShredderCurrent());
		plcConfigurationView.setPlcAddressAluminiumShredderCurrent(plcConfigurationModel.getPlcAddressAluminiumShredderCurrent());
		plcConfigurationView.setPlcAddressBcFrequency(plcConfigurationModel.getPlcAddressBcFrequency());
		plcConfigurationView.setPlcAddressCDoorFrequency(plcConfigurationModel.getPlcAddressCDoorFrequency());
		plcConfigurationView.setPlcAddressFcFrequency(plcConfigurationModel.getPlcAddressFcFrequency());
		plcConfigurationView.setPlcAddressPlasticShredderCurrent(plcConfigurationModel.getPlcAddressPlasticShredderCurrent());
		plcConfigurationView.setPlcAddressSlidingConveyorFrequency(plcConfigurationModel.getPlcAddressSlidingConveyorFrequency());
		plcConfigurationView.setPlcIpAddress(plcConfigurationModel.getPlcIpAddress());
		MachineView machineView = new MachineView();
		machineView.setId(plcConfigurationModel.getMachineModel().getId());
		machineView.setMachineId(plcConfigurationModel.getMachineModel().getMachineId());
		plcConfigurationView.setMachineView(machineView);
		plcConfigurationView.setCreateDate(plcConfigurationModel.getCreateDate());
		return plcConfigurationView;

	}

	@Override
	protected void checkInactive(PLCConfigurationModel plcConfigurationModel) throws EndlosAPIException {
	}

	@Override
	public Response doUpdatePlcConfiguration(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		MachineModel machineModel = machineService.get(plcConfigurationView.getMachineView().getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		PLCConfigurationModel plcConfigurationModel = new PLCConfigurationModel();
		plcConfigurationModel.setMachineModel(machineModel);
		plcConfigurationModel.setBarcodeScannerResponseWait(plcConfigurationView.getBarcodeScannerResponseWait());
		plcConfigurationModel.setObjectDetectFirstResponseWait(plcConfigurationView.getObjectDetectFirstResponseWait());
		plcConfigurationModel
				.setPlcAddressGlassShredderCurrent(plcConfigurationView.getPlcAddressGlassShredderCurrent());
		plcConfigurationModel
				.setPlcAddressAluminiumShredderCurrent(plcConfigurationView.getPlcAddressAluminiumShredderCurrent());
		plcConfigurationModel.setPlcAddressBcFrequency(plcConfigurationView.getPlcAddressBcFrequency());
		plcConfigurationModel.setPlcAddressCDoorFrequency(plcConfigurationView.getPlcAddressCDoorFrequency());
		plcConfigurationModel.setPlcAddressFcFrequency(plcConfigurationView.getPlcAddressFcFrequency());
		plcConfigurationModel
				.setPlcAddressPlasticShredderCurrent(plcConfigurationView.getPlcAddressPlasticShredderCurrent());
		plcConfigurationModel
				.setPlcAddressSlidingConveyorFrequency(plcConfigurationView.getPlcAddressSlidingConveyorFrequency());
		plcConfigurationModel.setPlcIpAddress(plcConfigurationView.getPlcIpAddress());
		plcConfigurationModel.setCreateDate(DateUtility.getCurrentEpoch());
		plcConfigurationService.create(plcConfigurationModel);
		machineModel.setPlcConfigurationModel(plcConfigurationModel);
		machineService.update(machineModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}
}
