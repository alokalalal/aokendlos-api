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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This is Module enum which represent module of system.
 * 
 * @author Nirav.Shah
 * @since 08/02/2018
 */
public enum ModuleEnum implements EnumType {

	USER(1, "User") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			rightsEnums.add(RightsEnum.FILE_UPLOAD);
			rightsEnums.add(RightsEnum.ACTIVATION);
			return rightsEnums;
		}
	},
	ROLE(2, "Role") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	SETTING(3, "Setting") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	NOTIFICATION(4, "Notification") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	EMAIL_ACCOUNT(5, "Email Account") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	RESPONSE_MESSAGE(6, "Response Message") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	MACHINE(7, "Machine") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			return rightsEnums;
		}
	},
	CUSTOMER(8, "Customer") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			rightsEnums.add(RightsEnum.FILE_UPLOAD);
			rightsEnums.add(RightsEnum.ACTIVATION);
			return rightsEnums;
		}
	},
	LOCATION(9, "Location") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.ACTIVATION);
			return rightsEnums;
		}
	},
	TRANSACTION(10, "Transaction") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	ERROR(11, "Error") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	DASHBOARD(12, "Dashboard") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	BARCODE_STRUCTURE(13, "Barcode Structure") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	CHANGE_LOCATION(14, "Change Location") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	REPORT(15, "Report") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	MACHINE_LOG(16, "Machine Log") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	},
	MACHINE_BARCODE(17, "Machine Barcode") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			rightsEnums.add(RightsEnum.FILE_UPLOAD);
			rightsEnums.add(RightsEnum.ACTIVATION);
			return rightsEnums;
		}
	},
	PICKUP_ROUTE(18, "Pickup Route") {
		@Override
		public List<RightsEnum> getAssignedRights() {
			List<RightsEnum> rightsEnums = new ArrayList<>();
			rightsEnums.add(RightsEnum.ADD);
			rightsEnums.add(RightsEnum.UPDATE);
			rightsEnums.add(RightsEnum.VIEW);
			rightsEnums.add(RightsEnum.DELETE);
			rightsEnums.add(RightsEnum.LIST);
			rightsEnums.add(RightsEnum.DOWNLOAD);
			return rightsEnums;
		}
	};

	private final int id;
	private final String name;

	public static final Map<Integer, ModuleEnum> MAP = new HashMap<>();

	static {
		for (ModuleEnum moduleEnums : values()) {
			MAP.put(moduleEnums.getId(), moduleEnums);
		}
	}

	ModuleEnum(int id, String name) {
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
	 * @return rightsEnums enum
	 */
	public static ModuleEnum fromId(Integer id) {
		return MAP.get(id);
	}

	/**
	 * Return the list of rights.
	 * 
	 * @return
	 */
	public abstract List<RightsEnum> getAssignedRights();
}
