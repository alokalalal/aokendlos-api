/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.customer.model;

import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.model.ActivationModel;
import com.intentlabs.common.model.IdentifierModel;

/**
 * This is customer model which maps customer table to class.
 * 
 * @author Hemil Shah
 * @since 18/11/2021
 */
public class CustomerModel extends ActivationModel {

	private static final long serialVersionUID = 6981951404561215786L;
	private String name;
	private FileModel logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileModel getLogo() {
		return logo;
	}

	public void setLogo(FileModel logo) {
		this.logo = logo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}