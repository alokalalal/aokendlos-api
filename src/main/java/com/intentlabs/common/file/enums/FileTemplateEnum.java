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
package com.intentlabs.common.file.enums;

/**
 * This is file template enum which contains template name and columns headers.
 * @author Nirav.Shah
 * @since 29/11/2018
 */
public enum FileTemplateEnum {

	COHORT_TEMPLATE(1,new String[] {"Name", "Email", "User Type", "Company Name"});

	private final Integer id;
	private final String[] headers;

	FileTemplateEnum(Integer id, String[] headers) {
		this.id = id;
		this.headers = headers;
	}
	
	public Integer getId() {
		return id;
	}

	public String[] getHeaders() {
		return headers;
	}

	public static FileTemplateEnum fromId(Integer id) {
		for(FileTemplateEnum fileTemplateEnum : values()) {
			if(fileTemplateEnum.getId().equals(id)) {
				return fileTemplateEnum;
			}
		}
		return null;
	}
}
