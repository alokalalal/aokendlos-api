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
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.endlos.machine.model.MachineCapacityModel;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineCapacityService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.view.MachineCapacityView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Component(value = "machineCapacityOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MachineCapacityOperationImpl extends AbstractOperation<MachineCapacityModel, MachineCapacityView>
		implements MachineCapacityOperation {

	@Autowired
	private MachineCapacityService machineCapacityService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<MachineCapacityModel> getService() {
		return machineCapacityService;
	}

	@Override
	protected MachineCapacityModel loadModel(MachineCapacityView machineCapacityView) {
		return machineCapacityService.get(machineCapacityView.getId());
	}

	@Override
	protected MachineCapacityModel getNewModel() {
		return new MachineCapacityModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(MachineCapacityView machineCapacityView) throws EndlosAPIException {
		MachineCapacityModel machineCapacityModel = toModel(getNewModel(), machineCapacityView);
		machineCapacityService.create(machineCapacityModel);
		MachineModel machineModel = machineService.get(machineCapacityView.getMachineView().getId());
		machineModel.setMachineCapacityModel(machineCapacityModel);
		machineModel.setCapacityChanged(true);
		machineService.update(machineModel);
		machineCapacityView = fromModel(machineCapacityModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineCapacityView);
	}

	@Override
	public Response doUpdate(MachineCapacityView machineCapacityView) throws EndlosAPIException {
		MachineCapacityModel machineCapacityModel = machineCapacityService.get(machineCapacityView.getId());
		if (machineCapacityModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		machineCapacityModel = toModel(machineCapacityModel, machineCapacityView);
		machineCapacityService.update(machineCapacityModel);

		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		MachineCapacityModel machineCapacityModel = machineCapacityService.get(id);
		if (machineCapacityModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineCapacityView machineCapacityView = fromModel(machineCapacityModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineCapacityView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doSearch(MachineCapacityView machineCapacityView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = machineCapacityService.search(machineCapacityView, start, recordSize, orderType,
				orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MachineCapacityModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public MachineCapacityModel toModel(MachineCapacityModel machineCapacityModel,
			MachineCapacityView machineCapacityView) throws EndlosAPIException {
		machineCapacityModel.setId(machineCapacityView.getId());
		machineCapacityModel.setPlasticCapacity(machineCapacityView.getPlasticCapacity());
		machineCapacityModel.setGlassCapacity(machineCapacityView.getGlassCapacity());
		machineCapacityModel.setAluminiumnCapacity(machineCapacityView.getAluminiumnCapacity());
		machineCapacityModel.setPrintCapacity(machineCapacityView.getPrintCapacity());
		machineCapacityModel.setMaxTransaction(machineCapacityView.getMaxTransaction());
		machineCapacityModel.setMaxAutoCleaning(machineCapacityView.getMaxAutoCleaning());
		//machineCapacityModel.setCreateDate(machineCapacityView.getCreateDate());
		machineCapacityModel.setCreateDate(DateUtility.getCurrentEpoch());
		return machineCapacityModel;
	}

	@Override
	public MachineCapacityView fromModel(MachineCapacityModel machineCapacityModel) {
		MachineCapacityView machineCapacityView = new MachineCapacityView();
		machineCapacityView.setId(machineCapacityModel.getId());
		machineCapacityView.setPlasticCapacity(machineCapacityModel.getPlasticCapacity());
		machineCapacityView.setGlassCapacity(machineCapacityModel.getGlassCapacity());
		machineCapacityView.setAluminiumnCapacity(machineCapacityModel.getAluminiumnCapacity());
		machineCapacityView.setPrintCapacity(machineCapacityModel.getPrintCapacity());
		machineCapacityView.setMaxAutoCleaning(machineCapacityModel.getMaxAutoCleaning());
		machineCapacityView.setMaxTransaction(machineCapacityModel.getMaxTransaction());

		MachineView machineView = new MachineView();
		machineView.setId(machineCapacityModel.getMachineModel().getId());
		machineView.setMachineId(machineCapacityModel.getMachineModel().getMachineId());
		machineCapacityView.setMachineView(machineView);
		machineCapacityView.setCreateDate(machineCapacityModel.getCreateDate());
		return machineCapacityView;
	}

	@Override
	protected void checkInactive(MachineCapacityModel machineCapacityModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doUpdateMachineCapacity(MachineCapacityView machineCapacityView) throws EndlosAPIException {
		MachineModel machineModel = machineService.get(machineCapacityView.getMachineView().getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineCapacityModel machineCapacityModel = new MachineCapacityModel();
		machineCapacityModel.setMachineModel(machineModel);
		machineCapacityModel.setPlasticCapacity(machineCapacityView.getPlasticCapacity());
		machineCapacityModel.setGlassCapacity(machineCapacityView.getGlassCapacity());
		machineCapacityModel.setAluminiumnCapacity(machineCapacityView.getAluminiumnCapacity());
		machineCapacityModel.setPrintCapacity(machineCapacityView.getPrintCapacity());
		machineCapacityModel.setMaxAutoCleaning(machineCapacityView.getMaxAutoCleaning());
		machineCapacityModel.setMaxTransaction(machineCapacityView.getMaxTransaction());
		machineCapacityModel.setCreateDate(DateUtility.getCurrentEpoch());
		machineCapacityService.create(machineCapacityModel);
		machineModel.setMachineCapacityModel(machineCapacityModel);
		machineService.update(machineModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

}