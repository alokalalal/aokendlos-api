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
package com.intentlabs.common.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.user.view.ModuleView;
import com.intentlabs.common.user.view.RightsView;

/**
 * This controller maps all Role related APIs.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@Controller
@RequestMapping("/private/module")
public class ModulePrivateController {

	/**
	 * This method is used to get list of modules.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/all")
	@ResponseBody
	@AccessLog
	public Response all() {
		List<ModuleView> moduleViews = new ArrayList<>();
		for (Map.Entry<Integer, ModuleEnum> moduleEnum : ModuleEnum.MAP.entrySet()) {
			List<RightsView> rightsViews = new ArrayList<>();
			for (RightsEnum rightsEnum : moduleEnum.getValue().getAssignedRights()) {
				RightsView rightsView = new RightsView();
				rightsView.setRightView(rightsEnum);
				rightsViews.add(rightsView);
			}
			ModuleView moduleView = new ModuleView();
			moduleView.setModuleView(moduleEnum.getValue());
			moduleView.setRightsViews(rightsViews);
			moduleViews.add(moduleView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				moduleViews.size(), moduleViews);
	}
}