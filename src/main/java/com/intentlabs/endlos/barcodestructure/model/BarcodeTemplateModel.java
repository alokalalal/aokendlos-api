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
package com.intentlabs.endlos.barcodestructure.model;

import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;

/**
 * This is Barcode Template model which maps Barcode Template Template table to
 * class.
 * 
 * @author hemil.Shah
 * @since 19/07/2022
 */
public class BarcodeTemplateModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private String name;
	private Long totalLength;
	private Long currentLength;
	private boolean completed;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Long totalLength) {
		this.totalLength = totalLength;
	}

	public Long getCurrentLength() {
		return currentLength;
	}

	public void setCurrentLength(Long currentLength) {
		this.currentLength = currentLength;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
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