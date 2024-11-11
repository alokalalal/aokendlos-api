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
 * This is Machine Type Enum which represent machine type status. This is
 * Machine Type Enum which represent machine type status.
 * 
 * @author Hemil.SHah
 * @since 06/10/2022
 */
public enum MachineTypeEnum implements EnumType {

	THREEE_SHREDDER(1, "Three Shredder"), ONE_SHREDDER(2, "One Shredder"), ONE_COMPACTOR(3, "One Compactor"),
	TWO_SHREDDER(4, "Two Shredder");

	private final int id;
	private final String name;
	private static final Map<Integer, MachineTypeEnum> MAP = new HashMap<>();

	static {
		for (MachineTypeEnum machineActivityStatusEnum : values()) {
			MAP.put(machineActivityStatusEnum.getId(), machineActivityStatusEnum);
		}
	}

	MachineTypeEnum(int id, String name) {
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
	public static MachineTypeEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
