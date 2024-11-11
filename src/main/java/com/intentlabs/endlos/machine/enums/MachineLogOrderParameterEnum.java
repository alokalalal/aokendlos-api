package com.intentlabs.endlos.machine.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

/**
 * 
 * This is used in search api for order data of machine log model.
 * 
 * @author Hemil.Shah
 * @since 13/06/2023
 */
public enum MachineLogOrderParameterEnum implements OrderParamEnumType {

	RESET_DATE(1, "createDate", "createDate"), MATERIAL_TYPE(2, "materialType", "materialType"),
	MATERIAL_COUNT(3, "materialCount", "materialCount"), CUSTOMER_NAME(4, "customerName", "customerModel.name"),
	BRANCH_NAME(5, "branchName", "locationModel.name");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, MachineLogOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (MachineLogOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	MachineLogOrderParameterEnum(int id, String name, String parameter) {
		this.id = id;
		this.name = name;
		this.parameter = parameter;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getParameter() {
		return parameter;
	}

	/**
	 * This methods is used to fetch Enum base on given id.
	 * 
	 * @param id enum key
	 * @return machine order parameter enum
	 */
	public static MachineLogOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
