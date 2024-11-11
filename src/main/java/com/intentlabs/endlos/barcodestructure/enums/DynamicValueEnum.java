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
 * This is Dynamic Value Enum which represent dynamic status.
 * 
 * @author Hemil.Shah
 * @since 10/08/2022
 */
public enum DynamicValueEnum implements EnumType {

	BRANCH_ID(1, "Branch Id", 4), VOUCHER_VALUE(2, "Voucher Value", 4),
	MACHINE_NUMBER(3, "Branchwise Machine Number", 1), CHECKEDSUM(4, "Checkedsum", 1), MACHINE_DATA_DATE(5, "Date", 6);

	private final int id;
	private final String name;
	private final int length;
	private static final Map<Integer, DynamicValueEnum> MAP = new HashMap<>();

	static {
		for (DynamicValueEnum machineActivityStatusEnum : values()) {
			MAP.put(machineActivityStatusEnum.getId(), machineActivityStatusEnum);
		}
	}

	DynamicValueEnum(int id, String name, int length) {
		this.id = id;
		this.name = name;
		this.length = length;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	/**
	 * This methods is used to fetch Enum base on given id.
	 * 
	 * @param id enum key
	 * @return machineStatusEnum
	 */
	public static DynamicValueEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
