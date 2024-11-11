/*******************************************************************************
 * Copyright -2019 @Intentlbas
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
package com.intentlabs.common.setting.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.setting.enums.LocaleEnum;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.setting.view.LocaleView;

/**
 * This controller maps all locale Key related apis
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@Controller
@RequestMapping("/public/response-message")
public class ResponseMessagePublicController {

	/**
	 * This method is used to get supported locale.
	 * 
	 * @Param userView
	 * @return
	 * @throws EndlosAPIException
	 */
	@GetMapping(value = "/dropdown-supported-locale")
	@ResponseBody
	@AccessLog
	public Response dropdownSupportedLocale() {
		List<LocaleView> languageViews = new ArrayList<>();
		for (String language : SystemSettingModel.getLocaleSupported().split(",\\s")) {
			LocaleEnum localeEnum = LocaleEnum.fromLanguageTag(language.trim());
			languageViews.add(
					new LocaleView(localeEnum.getLanguageTag(), localeEnum.getLanguage(), localeEnum.getCountry()));
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				languageViews.size(), languageViews);
	}
}
