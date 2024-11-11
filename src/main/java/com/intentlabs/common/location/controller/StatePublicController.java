/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.location.operation.StateOperation;
import com.intentlabs.common.response.Response;

/**
 * This controller maps all client related apis.
 * 
 * @author Jaydip
 * @since 23/04/2019
 */
@Controller
@RequestMapping("/public/state")
public class StatePublicController {

	@Autowired
	private StateOperation stateOperation;

	/**
	 * Prepare a state dropdown from country id.
	 * 
	 * @return
	 * @throws EndlosAPIException`
	 */
	@GetMapping
	@ResponseBody
	@AccessLog
	public Response all(@RequestParam(name = "countryId", required = true) Long countryId) throws EndlosAPIException {
		if (countryId == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return stateOperation.doGetAll(countryId);
	}
}