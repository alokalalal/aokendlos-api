/*******************************************************************************
 * Copyright -2018 @intentlabs
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
package com.intentlabs.common.exception;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;

/**
 * This is a common exception handler which wrapped over controller. This will
 * be used to handle custom & other common exception. This handler will return
 * response to customer in json format.
 * 
 * @author Nirav.Shah
 * @since 24/12/2019
 */
@ControllerAdvice
public class EndlosAPIExceptionHanlder {

	/**
	 * This method all custom generated exception.
	 * 
	 * @param endlosAPIException
	 * @returnAPIException
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(EndlosAPIException.class)
	@ResponseBody
	public Response handleProgramException(EndlosAPIException endlosAPIException) throws IOException {
		switch (endlosAPIException.getCode()) {
		case 2001:
			return CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		default:
			break;
		}
		LoggerService.error(endlosAPIException.getMessage());
		return CommonResponse.create(endlosAPIException.getCode(), endlosAPIException.getMessage());
	}

	/**
	 * Exception handler
	 * 
	 * @param exception
	 * @return {@link WebResponseView}
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response handleException(Exception exception) throws IOException {
		LoggerService.exception(exception);
		return CommonResponse.create(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
				ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
	}
}
