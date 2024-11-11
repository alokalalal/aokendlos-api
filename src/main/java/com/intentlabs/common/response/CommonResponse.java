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
package com.intentlabs.common.response;

/**
 * This is common response which will be send for every request..
 * 
 * @author Nirav.Shah
 * @since 03/08/2018
 */
public class CommonResponse implements Response {

	private static final long serialVersionUID = 3217452268355902474L;
	private int code;
	private String message;
	private String accessToken;
	private String refreshToken;

	protected CommonResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	protected CommonResponse(int code, String message, String accessToken, String refreshToken) {
		this.code = code;
		this.message = message;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static CommonResponse create(int code, String message) {
		return new CommonResponse(code, message);
	}

	public static CommonResponse create(int code, String message, String accessToken, String refreshToken) {
		return new CommonResponse(code, message, accessToken, refreshToken);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
