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
package com.intentlabs.common.user.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.view.View;

/**
 * This class is used to represent module object in json/in customer response.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@JsonInclude(Include.NON_NULL)
public class ModuleView implements View {

	private static final long serialVersionUID = 4740087385931669057L;
	private Integer id;
	private String name;
	private List<RightsView> rightsViews;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RightsView> getRightsViews() {
		return rightsViews;
	}

	public void setRightsViews(List<RightsView> rightsViews) {
		this.rightsViews = rightsViews;
	}

	public void setModuleView(ModuleEnum moduleEnum) {
		this.setId(moduleEnum.getId());
		this.setName(moduleEnum.getName());
	}
}