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
 * This is Machine Development Status Enum which represent machine development
 * status.
 * 
 * @author Tushar.Patel
 * @since 06/12/2021
 */
public enum MachineDevelopmentStatusEnum implements EnumType {

	MANUFACTURING(1, "Manufacturing"), TRANSIT(2, "Transit"), POC_UAT(3, "POC/UAT"),
	READY_TO_DEPLOY(4, "Ready To Deploy"), DEPLOYED(5, "Deployed"), ALLOCATED(6, "Allocated");

	private final int id;
	private final String name;
	private static final Map<Integer, MachineDevelopmentStatusEnum> MAP = new HashMap<>();

	static {
		for (MachineDevelopmentStatusEnum machineActivityStatusEnum : values()) {
			MAP.put(machineActivityStatusEnum.getId(), machineActivityStatusEnum);
		}
	}

	MachineDevelopmentStatusEnum(int id, String name) {
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
	public static MachineDevelopmentStatusEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
