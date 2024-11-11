/*******************************************************************************

 * Copyright -2018 @Intentlabs
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
package com.intentlabs.common.setting.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.intentlabs.common.model.Model;

/**
 * This is Response Message Model which maps Response Message table.
 * 
 * @author Author Team
 * @since 12/08/2021
 */
public class ResponseMessageModel implements Model {

	private static final long serialVersionUID = 4299801242035355223L;

	private Integer code;
	private String locale;
	private String message;

	private static Map<String, String> reponseMessages = new ConcurrentHashMap<>();

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void add(ResponseMessageModel responseMessageModel) {
		reponseMessages.put(responseMessageModel.getCode() + responseMessageModel.getLocale(),
				responseMessageModel.getMessage());
	}

	public static String get(String code, String locale) {
		return reponseMessages.get(code + locale);
	}

	public static Map<String, String> getReponseMessages() {
		return reponseMessages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		ResponseMessageModel other = (ResponseMessageModel) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}
}