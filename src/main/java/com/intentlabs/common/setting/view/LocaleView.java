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
import com.intentlabs.common.view.IdentifierView;

/**
 * This class is used to represent language object in json/in customer response.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
@JsonInclude(Include.NON_NULL)
public class LocaleView extends IdentifierView {

	private static final long serialVersionUID = 7317434108875697545L;

	private String languageTag;
	private String language;
	private String country;

	LocaleView() {
	}

	public LocaleView(String languageTag, String language, String country) {
		this.languageTag = languageTag;
		this.language = language;
		this.country = country;
	}

	public String getLanguageTag() {
		return languageTag;
	}

	public void setLanguageTag(String languageTag) {
		this.languageTag = languageTag;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}