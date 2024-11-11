package com.intentlabs.endlos.customer.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

/**
 * 
 * This is used in search api for order data of location model.
 * 
 * @author Tushar.Patel
 * @since 17/12/2021
 */
public enum LocationOrderParameterEnum implements OrderParamEnumType {

	CREATE_DATE(1, "Create Date", "id"), NAME(2, "Name", "name");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, LocationOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (LocationOrderParameterEnum customerOrderParameterEnum : values()) {
			MAP.put(customerOrderParameterEnum.getId(), customerOrderParameterEnum);
		}
	}

	LocationOrderParameterEnum(int id, String name, String parameter) {
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
	 * @return customer order parameter enum
	 */
	public static LocationOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
