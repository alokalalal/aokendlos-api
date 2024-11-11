package com.intentlabs.endlos.machine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.machine.enums.PLCConfigurationParameterEnum;
import com.intentlabs.endlos.machine.operation.PLCConfigurationOperation;
import com.intentlabs.endlos.machine.view.PLCConfigurationView;

@RestController
@RequestMapping("/private/plc-configuration")
public class PLCConfigurationPrivateController extends AbstractController<PLCConfigurationView> {
	@Autowired
	private PLCConfigurationOperation plcConfigurationOperation;

	@Override
	public BaseOperation<PLCConfigurationView> getOperation() {
		return plcConfigurationOperation;
	}

	@Override
	public Response add() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response save(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response update(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response view(Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return plcConfigurationOperation.doView(id);
	}

	@Override
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response edit(Long id) throws EndlosAPIException {
		throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
	}

	@Override
	public Response search(@RequestBody PLCConfigurationView plcConfigurationView,
			@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {
		return plcConfigurationOperation.doSearch(plcConfigurationView, start, recordSize, orderType, orderParam);
	}

	@Override
	public void isValidSaveData(PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		// do nothing
	}

	@GetMapping("/get-order-parameter")
	@AccessLog
	public Response getOrderParameter() {
		List<KeyValueView> keyValueViews = new ArrayList<>();
		for (PLCConfigurationParameterEnum plcConfigurationParameterEnum : PLCConfigurationParameterEnum.values()) {
			keyValueViews.add(KeyValueView.create(plcConfigurationParameterEnum.getId(),
					plcConfigurationParameterEnum.getName()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				keyValueViews.size(), keyValueViews);
	}

	/**
	 * This method is used to update plc configuration.
	 * 
	 * @param plcConfigurationView
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping("/update-plc-configuration")
	@AccessLog
	@Authorization(modules = ModuleEnum.MACHINE, rights = RightsEnum.UPDATE)
	Response export(@RequestBody PLCConfigurationView plcConfigurationView) throws EndlosAPIException {
		if (plcConfigurationView.getMachineView() == null || plcConfigurationView.getMachineView().getId() == null) {
			throw new EndlosAPIException(ResponseCode.MACHINE_NAME_IS_MISSING.getCode(),
					ResponseCode.MACHINE_NAME_IS_MISSING.getMessage());
		}
		return plcConfigurationOperation.doUpdatePlcConfiguration(plcConfigurationView);
	}
}
