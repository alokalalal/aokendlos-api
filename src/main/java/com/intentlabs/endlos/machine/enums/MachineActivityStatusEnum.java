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
package com.intentlabs.endlos.machine.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This is Machine Activity Status Enum which represent machine activity status.
 * 
 * @author Hemil.Shah
 * @since 29/11/2021
 */
public enum MachineActivityStatusEnum implements EnumType {

	READY(1, "Ready"), WARNING(2, "Warning"), ERROR(3, "error");

	private final int id;
	private final String name;
	private static final Map<Integer, MachineActivityStatusEnum> MAP = new HashMap<>();

	static {
		for (MachineActivityStatusEnum machineActivityStatusEnum : values()) {
			MAP.put(machineActivityStatusEnum.getId(), machineActivityStatusEnum);
		}
	}

	MachineActivityStatusEnum(int id, String name) {
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
	public static MachineActivityStatusEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
