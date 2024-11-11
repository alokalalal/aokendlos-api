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
import com.intentlabs.endlos.barcodestructure.enums.BarcodeTypeEnum;
import com.intentlabs.endlos.barcodestructure.enums.DynamicValueEnum;

/**
 * This is Barcode Structure model which maps Barcode Structure table to class.
 * 
 * @author hemil.Shah
 * @since 19/07/2022
 */
public class BarcodeStructureModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private BarcodeTemplateModel barcodeTemplateModel;
	private String fieldName;
	private Integer barcodeType;
	private Long length;
	private String value;
	private Integer dynamicValue;
	private String index;
	private String endValue;

	public BarcodeTemplateModel getBarcodeTemplateModel() {
		return barcodeTemplateModel;
	}

	public void setBarcodeTemplateModel(BarcodeTemplateModel barcodeTemplateModel) {
		this.barcodeTemplateModel = barcodeTemplateModel;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BarcodeTypeEnum getBarcodeType() {
		return BarcodeTypeEnum.fromId(barcodeType);
	}

	public void setBarcodeType(Integer barcodeType) {
		this.barcodeType = barcodeType;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DynamicValueEnum getDynamicValue() {
		return DynamicValueEnum.fromId(dynamicValue);
	}

	public void setDynamicValue(Integer dynamicValue) {
		this.dynamicValue = dynamicValue;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getEndValue() {
		return endValue;
	}

	public void setEndValue(String endValue) {
		this.endValue = endValue;
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