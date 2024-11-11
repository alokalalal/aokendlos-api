/*******************************************************************************
\ * Copyright -2019 @intentlabs
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
import com.intentlabs.endlos.machine.model.MQTTConfigurationModel;
import com.intentlabs.endlos.machine.service.MQTTConfigurationService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.MQTTConfigurationView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Component(value = "mqttConfigurationOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MQTTConfigurationOperationImpl extends AbstractOperation<MQTTConfigurationModel, MQTTConfigurationView>
		implements MQTTConfigurationOperation {

	@Autowired
	private MQTTConfigurationService mqttConfigurationService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<MQTTConfigurationModel> getService() {
		return mqttConfigurationService;
	}

	@Override
	protected MQTTConfigurationModel loadModel(MQTTConfigurationView mqttConfigurationView) {
		return mqttConfigurationService.get(mqttConfigurationView.getId());
	}

	@Override
	protected MQTTConfigurationModel getNewModel() {
		return new MQTTConfigurationModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(MQTTConfigurationView mqttConfigurationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(MQTTConfigurationView mqttConfigurationView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		MQTTConfigurationModel mqttConfigurationModel = mqttConfigurationService.get(id);
		if (mqttConfigurationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MQTTConfigurationView mqttConfigurationView = fromModel(mqttConfigurationModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				mqttConfigurationView);
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
	public Response doSearch(MQTTConfigurationView mqttConfigurationView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = mqttConfigurationService.search(mqttConfigurationView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MQTTConfigurationModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public MQTTConfigurationModel toModel(MQTTConfigurationModel mqttConfigurationModel,
			MQTTConfigurationView mqttConfigurationView) throws EndlosAPIException {
		return mqttConfigurationModel;
	}

	@Override
	public MQTTConfigurationView fromModel(MQTTConfigurationModel mqttConfigurationModel) {
		MQTTConfigurationView mqttConfigurationView = new MQTTConfigurationView();
		mqttConfigurationView.setId(mqttConfigurationModel.getId());
		mqttConfigurationView.setMqttBridgeHostName(mqttConfigurationModel.getMqttBridgeHostName());
		mqttConfigurationView.setMqttBridgePort(mqttConfigurationModel.getMqttBridgePort());
		mqttConfigurationView.setProjectId(mqttConfigurationModel.getProjectId());
		mqttConfigurationView.setCloudRegion(mqttConfigurationModel.getCloudRegion());
		mqttConfigurationView.setRegistryId(mqttConfigurationModel.getRegistryId());
		mqttConfigurationView.setGatewayId(mqttConfigurationModel.getGatewayId());
		mqttConfigurationView.setPrivateKeyFilePath(mqttConfigurationModel.getPrivateKeyFilePath());
		mqttConfigurationView.setAlgorithm(mqttConfigurationModel.getAlgorithm());
		mqttConfigurationView.setDeviceId(mqttConfigurationModel.getDeviceId());
		mqttConfigurationView.setMessageType(mqttConfigurationModel.getMessageType());
		mqttConfigurationView.setCreateDate(mqttConfigurationModel.getCreateDate());

		MachineView machineView = new MachineView();
		machineView.setId(mqttConfigurationModel.getMachineModel().getId());
		machineView.setMachineId(mqttConfigurationModel.getMachineModel().getMachineId());
		mqttConfigurationView.setMachineView(machineView);
		return mqttConfigurationView;
	}

	@Override
	protected void checkInactive(MQTTConfigurationModel mqttConfigurationModel) throws EndlosAPIException {
		// do nothing
	}
}