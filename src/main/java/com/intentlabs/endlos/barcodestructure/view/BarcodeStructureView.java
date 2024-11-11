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
package com.intentlabs.endlos.barcodestructure.view;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.AuditableView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodestructure.enums.BarcodeTypeEnum;
import com.intentlabs.endlos.barcodestructure.enums.DynamicValueEnum;

/**
 * This class is used to represent Barcode Structure object in json/in customer
 * response.
 * 
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
public class BarcodeStructureView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private BarcodeTemplateView barcodeTemplateView;
	private String fieldName;
	private KeyValueView barcodeType;
	private String length;
	private String value;
	private KeyValueView dynamicValue;
	private String index;
	private String endValue;

	public BarcodeTemplateView getBarcodeTemplateView() {
		return barcodeTemplateView;
	}

	public void setBarcodeTemplateView(BarcodeTemplateView barcodeTemplateView) {
		this.barcodeTemplateView = barcodeTemplateView;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public KeyValueView getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(KeyValueView barcodeType) {
		this.barcodeType = barcodeType;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public KeyValueView getDynamicValue() {
		return dynamicValue;
	}

	public void setDynamicValue(KeyValueView dynamicValue) {
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

	public static void isValid(BarcodeStructureView barcodeStructureView) throws EndlosAPIException {
		if (barcodeStructureView.getBarcodeType() == null || barcodeStructureView.getBarcodeType().getKey() == null) {
			throw new EndlosAPIException(ResponseCode.BARCODE_TYPE_IS_MISSING.getCode(),
					ResponseCode.BARCODE_TYPE_IS_MISSING.getMessage());
		}
		if (barcodeStructureView.getLength() == null || Integer.parseInt(barcodeStructureView.getLength()) == 0) {
			throw new EndlosAPIException(ResponseCode.LENGTH_IS_INVALID.getCode(),
					ResponseCode.LENGTH_IS_INVALID.getMessage());
		}
//		if (barcodeStructureView.getDynamicValue() != null && Integer.parseInt(barcodeStructureView.length) < 2) {
//			throw new EndlosAPIException(ResponseCode.LENGTH_IS_INVALID.getCode(),
//					ResponseCode.LENGTH_IS_INVALID.getMessage());
//		}
		Validator.STRING.isValid(new InputField("FIELD_NAME", barcodeStructureView.getFieldName(), true));

		Validator.STRING.isValid(new InputField("LENGTH", barcodeStructureView.getLength(), true, RegexEnum.NUMERIC));
		if (barcodeStructureView.getBarcodeType().getKey() == BarcodeTypeEnum.MACHINE_DATA.getId()) {
			if (barcodeStructureView.getDynamicValue() == null) {
				throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
						ResponseCode.INVALID_REQUEST.getMessage());
			}
			if (barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.VOUCHER_VALUE.getId()
					&& Integer.valueOf(barcodeStructureView.getLength()) < DynamicValueEnum.VOUCHER_VALUE.getLength()) {
				throw new EndlosAPIException(ResponseCode.VOUCHER_VALUE_LENGTH_IS_INVALID.getCode(),
						ResponseCode.VOUCHER_VALUE_LENGTH_IS_INVALID.getMessage());
			}
			if (barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.BRANCH_ID.getId()
					&& Integer.valueOf(barcodeStructureView.getLength()) < 1
					&& Integer.valueOf(barcodeStructureView.getLength()) > DynamicValueEnum.BRANCH_ID.getLength()) {
				throw new EndlosAPIException(ResponseCode.BRANCH_ID_LENGTH_IS_INVALID.getCode(),
						ResponseCode.BRANCH_ID_LENGTH_IS_INVALID.getMessage());
			}
			if (barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.MACHINE_NUMBER.getId() && Integer
					.valueOf(barcodeStructureView.getLength()) != DynamicValueEnum.MACHINE_NUMBER.getLength()) {
				throw new EndlosAPIException(ResponseCode.MACHINE_NUMBER_LENGTH_IS_INVALID.getCode(),
						ResponseCode.MACHINE_NUMBER_LENGTH_IS_INVALID.getMessage());
			}
			if (barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.CHECKEDSUM.getId()
					&& Integer.valueOf(barcodeStructureView.getLength()) != DynamicValueEnum.CHECKEDSUM.getLength()) {
				throw new EndlosAPIException(ResponseCode.CHECKED_SUM_LENGTH_IS_INVALID.getCode(),
						ResponseCode.CHECKED_SUM_LENGTH_IS_INVALID.getMessage());
			}
			if (barcodeStructureView.getDynamicValue().getKey() == DynamicValueEnum.MACHINE_DATA_DATE.getId()
					&& Integer.valueOf(barcodeStructureView.getLength()) != DynamicValueEnum.MACHINE_DATA_DATE.getLength()) {
				throw new EndlosAPIException(ResponseCode.MACHINE_DATA_DATE_LENGTH_IS_INVALID.getCode(),
						ResponseCode.MACHINE_DATA_DATE_LENGTH_IS_INVALID.getMessage());
			}
		}
		if (barcodeStructureView.getBarcodeType().getKey() == BarcodeTypeEnum.STATIC.getId()) {
			Validator.STRING.isValid(new InputField("VALUE", barcodeStructureView.getValue(), true,
					Integer.parseInt(barcodeStructureView.getLength()),
					Integer.parseInt(barcodeStructureView.getLength()), RegexEnum.NUMERIC));
		}
		if (barcodeStructureView.getBarcodeType().getKey() == BarcodeTypeEnum.NUMERATOR.getId()) {
			Validator.STRING.isValid(new InputField("VALUE", barcodeStructureView.getValue(), true,
					Integer.parseInt(barcodeStructureView.getLength()),
					Integer.parseInt(barcodeStructureView.getLength()), RegexEnum.NUMERIC));
			Validator.STRING.isValid(new InputField("END_VALUE", barcodeStructureView.getEndValue(), true,
					Integer.parseInt(barcodeStructureView.getLength()),
					Integer.parseInt(barcodeStructureView.getLength()), RegexEnum.NUMERIC));
		}
	}
}