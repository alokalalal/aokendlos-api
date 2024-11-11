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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.View;

/**
 * This class is used to represent rolemodulerights object in json/in customer response.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
@JsonInclude(Include.NON_NULL)
public class RoleModuleRightsView implements View  {

	private static final long serialVersionUID = -6639006849279463177L;
	private RightsView rightsView;
	private ModuleView moduleView;
	
	public RightsView getRightsView() {
		return rightsView;
	}
	public void setRightsView(RightsView rightsView) {
		this.rightsView = rightsView;
	}
	public ModuleView getModuleView() {
		return moduleView;
	}
	public void setModuleView(ModuleView moduleView) {
		this.moduleView = moduleView;
	}
}
