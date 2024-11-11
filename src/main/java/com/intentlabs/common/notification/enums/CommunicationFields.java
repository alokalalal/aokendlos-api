/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.notification.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.modelenums.ModelEnum;

/**
 * This is enum type of all email common fields.
 * 
 * @author Vishwa.Shah
 * @since 10/08/2018
 */
public enum CommunicationFields implements ModelEnum {

	USER_NAME(1, "name"), RESET_PASSWORD_LINK(2, "resetpasswordlink"), EMAIL_TO(3, "email"), PASSWORD(4, "password"),
	URL(5, "url"), VERIFICATION_CODE(6, "verification-code"), RESET_PASSWORD_TOKEN(7, "RESET_PASSWORD_TOKEN"),
	PATH(8, "Path"), WEBSITE(9, "website"), OTP(10, "otp"), ATTACHMENT_PATH(11, "attachment_path"), YEAR(12, "year"),
	CONNECTION_STATUS(15, "connection_status"), CUSTOMER(16, "Customer"), EMAIL(17, "Email"), ADMIN_URL(18, "adminurl"), USER_ROLE(19, "userrole"),
	OLD_BRANCH_NAME(20, "oldbranchname"), OLD_BRANCH_NUMBER(21, "oldbranchnumber"), OLD_BRANCH_WISE_MACHINE_NO(22, "oldbranchwisemachineno"), NEW_BRANCH_NAME(23, "newbranchname"), NEW_BRANCH_NUMBER(24, "newbranchnumber"), NEW_BRANCH_WISE_MACHINE_NO(25, "newbranchwisemachineno"), MACHINE_NO(26, "machineno"), OLD_CUSTOMER_NAME(27, "oldcustomername"), NEW_CUSTOMER_NAME(28, "newcustomername");

	private final Integer id;
	private final String name;

	public static final Map<Integer, CommunicationFields> MAP = new HashMap<>();

	static {
		for (CommunicationFields communicationFields : values()) {
			MAP.put(communicationFields.getId(), communicationFields);
		}
	}

	CommunicationFields(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public static CommunicationFields fromId(Integer id) {
		return MAP.get(id);
	}
}