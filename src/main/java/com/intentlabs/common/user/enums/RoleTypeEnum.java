/*******************************************************************************
 * Copyright -2018 @intentlabs
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
package com.intentlabs.common.user.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This is Role type enum which is used to idenfity basic roles into system. If
 * role with one role type is already created then system should not allow to
 * create the same role again.
 * 
 * @author Nirav.Shah
 * @since 28/03/2019
 */
public enum RoleTypeEnum implements EnumType {

	MASTER_ADMIN(1, "Master Admin"), CUSTOMER_ADMIN(2, "Customer Admin"), CUSTOMER(3, "Customer");
	private final int id;
	private final String name;
	public static final Map<Integer, RoleTypeEnum> MAP = new HashMap<>();

	static {
		for (RoleTypeEnum groupEnum : values()) {
			MAP.put(groupEnum.getId(), groupEnum);
		}
	}

	RoleTypeEnum(int id, String name) {
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
	 * @param id
	 *            enum key
	 * @return rightsEnums enum
	 */
	public static RoleTypeEnum fromId(Integer id) {
		return MAP.get(id);
	}

	public static RoleTypeEnum fromType(String type) {
		return RoleTypeEnum.valueOf(type.toUpperCase());
	}
}
