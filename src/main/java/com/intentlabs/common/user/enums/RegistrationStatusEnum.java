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
package com.intentlabs.common.user.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This is Registration status enum for user registration flow.
 * 
 * @author Jaydip
 * @since 04/02/2021
 */
public enum RegistrationStatusEnum implements EnumType {
	REGISTER(1,"Register"),VERIFICATION_REGISTRATION_OTP(2, "Verification Registration Otp"), PASSWORD_SETUP(3, "Password Setup"),
	ROLE_SELECTION(4, "Role Selection"),REGISTRATION_SUCCESS(5,"Registration Success");

	private final int id;
	private final String name;
	public static final Map<Integer, RegistrationStatusEnum> MAP = new HashMap<>();

	static {
		for (RegistrationStatusEnum registrationStatusEnum : values()) {
			MAP.put(registrationStatusEnum.getId(), registrationStatusEnum);
		}
	}

	RegistrationStatusEnum(int id, String name) {
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
	 * @return registrationStatusEnum enum
	 */
	public static RegistrationStatusEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
