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
package com.intentlabs.endlos.barcodestructure.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This is Barcode Type Enum which represent barcode type status.
 * 
 * @author Hemil.Shah
 * @since 12/08/2022
 */
public enum BarcodeTypeEnum implements EnumType {

	STATIC(1, "Static"), MACHINE_DATA(2, "Machine Data"), NUMERATOR(3, "Numerator");

	private final int id;
	private final String name;
	private static final Map<Integer, BarcodeTypeEnum> MAP = new HashMap<>();

	static {
		for (BarcodeTypeEnum machineActivityStatusEnum : values()) {
			MAP.put(machineActivityStatusEnum.getId(), machineActivityStatusEnum);
		}
	}

	BarcodeTypeEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * This methods is used to fetch Enum base on given id.
	 * 
	 * @param id enum key
	 * @return machineStatusEnum
	 */
	public static BarcodeTypeEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
