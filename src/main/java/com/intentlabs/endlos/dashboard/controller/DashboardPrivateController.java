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
package com.intentlabs.endlos.dashboard.controller;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.endlos.dashboard.operation.DashboardOperation;
import com.intentlabs.endlos.machine.view.MachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller maps all Dashboard related apis.
 * 
 * @author hemil.Shah
 * @since 27/10/2021
 */
@Controller
@RequestMapping("/private/dashboard")
public class DashboardPrivateController {
	@Autowired
	private DashboardOperation dashboardOperation;

	/**
	 * This method is used to get counters data.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/get-counters")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getCounters(@RequestBody MachineView machineView) throws EndlosAPIException {
		return dashboardOperation.doGetCounters(machineView);
	}

	/**
	 * This method is used to get machine status data.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/get-machine-status")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getMachineStatus(@RequestBody MachineView machineView) throws EndlosAPIException {
		return dashboardOperation.doGetMachineStatus(machineView);
	}

	/**
	 * This method is used to get machine fullness Status.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/get-fullness-status")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.DASHBOARD, rights = RightsEnum.VIEW)
	public Response getFullnessStatus(@RequestBody MachineView machineView) throws EndlosAPIException {
		return dashboardOperation.doGetFullnessStatus(machineView);
	}
}