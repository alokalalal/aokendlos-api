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
package com.intentlabs.common.config.locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * This is a transaction email configuration which specify when to run this
 * scheduler and and how many record to be processed at a time.
 * 
 * @author Nirav.Shah
 * @since 15/05/2019
 */

@Configuration
@ComponentScan
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LocaleConfiguration {

	@Value("${system.default.locale.country}")
	private String country;

	@Value("${system.default.locale.lang}")
	private String lang;

	private String defaultLocale;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	@PostConstruct
	public void setDefaultLocale() {
		this.defaultLocale = this.lang + "-" + this.country;
	}
}