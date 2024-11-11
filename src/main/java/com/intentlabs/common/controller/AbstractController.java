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
package com.intentlabs.common.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.view.View;

/**
 * Its abstract class of controller. It provides abstract methods &
 * implementation of basic methods used by any controller.
 * 
 * @author Nirav.Shah
 * @since 08/08/2018
 * @param <V>
 */
public abstract class AbstractController<V extends View> {
	static final String ABSTRACT_CONTROLLER = "AbstractController";

	/**
	 * It is used to get operation which will be used inside controller.
	 * 
	 * @return
	 */
	public abstract BaseOperation<V> getOperation();

	/**
	 * This method is used to validate add request to add any entity into
	 * system. Once it is been validate. it allow users to add entity data.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */

	@GetMapping(value = "/add")
	@ResponseBody
	@AccessLog
	public abstract Response add() throws EndlosAPIException;

	/**
	 * This method is used to handle save request coming from client for any
	 * module.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/save")
	@ResponseBody
	@AccessLog
	public Response save(@RequestBody V view) throws EndlosAPIException {
		if (view == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(view);
		return getOperation().doSave(view);
	}

	/**
	 * This method is used to handle view request coming from client for any
	 * module.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/view")
	@ResponseBody
	@AccessLog
	public Response view(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return getOperation().doView(id);
	}

	/**
	 * This method is used to handle edit request coming from client for any
	 * module.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/edit")
	@ResponseBody
	@AccessLog
	public Response edit(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return getOperation().doEdit(id);
	}

	/**
	 * This method is used to handle update request coming from client for any
	 * module.
	 * 
	 * @param view
	 * @return
	 * @throws EndlosAPIException
	 */
	@PutMapping(value = "/update")
	@ResponseBody
	@AccessLog
	public Response update(@RequestBody V view) throws EndlosAPIException {
		if (view == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		isValidSaveData(view);
		return getOperation().doUpdate(view);
	}

	/**
	 * This method is used to handle delete request coming from client for any
	 * module.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	@DeleteMapping(value = "/delete")
	@ResponseBody
	@AccessLog
	public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return getOperation().doDelete(id);
	}

	/**
	 * This method is used to handle active/inactive request.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	@PutMapping(value = "/activation")
	@ResponseBody
	@AccessLog
	public Response activeInActive(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
		if (id == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		return getOperation().doActiveInActive(id);
	}

	/**
	 * This method is used to handle grid request coming from client for any
	 * module.
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/display")
	@ResponseBody
	@AccessLog
	public Response displayGrid(@RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
		return getOperation().doDisplayGrid(start, recordSize);
	}

	/**
	 * This method is used to handle search request coming from client for any
	 * module.
	 * 
	 * @param view
	 * @param start
	 * @param recordSize
	 * @param orderType
	 * @param orderParam
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/search")
	@ResponseBody
	@AccessLog
	public abstract Response search(@RequestBody V view, @RequestParam(name = "start", required = true) Integer start,
			@RequestParam(name = "recordSize", required = true) Integer recordSize,
			@RequestParam(name = "orderType", required = false) Integer orderType,
			@RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException;

	/**
	 * This methods is used to validate data received from client side before
	 * saving or updating a module.
	 * 
	 * @param view
	 * @throws EndlosAPIException
	 */
	public abstract void isValidSaveData(V view) throws EndlosAPIException;
}