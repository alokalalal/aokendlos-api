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
package com.intentlabs.common.setting.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.setting.enums.LocaleEnum;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.IdentifierView;

/**
 * This class is used to represent response message object in json/in customer
 * response.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@JsonInclude(Include.NON_NULL)
public class ResponseMessageView extends IdentifierView {

	private static final long serialVersionUID = -4444717308537621033L;
	private Integer code;
	private String locale;
	private String message;

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

	public static void isValid(ResponseMessageView responseMessageView) throws EndlosAPIException {
		if (responseMessageView.getCode() == null || ResponseCode.fromId(responseMessageView.getCode()) == null) {
			throw new EndlosAPIException(ResponseCode.CODE_IS_INVALID.getCode(),
					ResponseCode.CODE_IS_INVALID.getMessage());
		}
		if (responseMessageView.getLocale() == null
				|| LocaleEnum.fromLanguageTag(responseMessageView.getLocale()) == null) {
			throw new EndlosAPIException(ResponseCode.LANGUAGE_IS_INVALID.getCode(),
					ResponseCode.LANGUAGE_IS_INVALID.getMessage());
		}
		if (!SystemSettingModel.getLocaleSupported().contains(responseMessageView.getLocale())) {
			throw new EndlosAPIException(ResponseCode.LANGUAGE_IS_INVALID.getCode(),
					ResponseCode.LANGUAGE_IS_INVALID.getMessage());
		}
		Validator.STRING.isValid(new InputField("MESSAGE", responseMessageView.getMessage(), true, 1000));
	}
}