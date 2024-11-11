package com.intentlabs.endlos.machine.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

/**
 * 
 * This is used in search api for order data of error model.
 * 
 * @author Hemil.Shah
 * @since 06/07/2022
 */
public enum ErrorOrderParameterEnum implements OrderParamEnumType {

	ERROR_NAME(1, "createDate", "createDate"), CREATE_DATE(2, "errorName", "errorName"),
	RESOLVE_DATE(3, "resolveDate", "resolveDate"), STATUS(4, "status", "resolve"),
	MACHINE_ID(5, "machineId", "machineModel.machineId"), CUSTOMER_NAME(6, "customerName", "customerModel.name"),
	BRANCH_NAME(7, "branchName", "locationModel.name");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, ErrorOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (ErrorOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	ErrorOrderParameterEnum(int id, String name, String parameter) {
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
	public static ErrorOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
