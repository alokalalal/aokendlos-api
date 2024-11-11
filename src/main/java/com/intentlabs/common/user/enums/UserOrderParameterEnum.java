package com.intentlabs.common.user.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

/**
 * 
 * This is used in search api for order data of machine model.
 * 
 * @author Tushar.Patel
 * @since 16/12/2021
 */
public enum UserOrderParameterEnum implements OrderParamEnumType {

	USER_NAME(1, "name", "name"), USER_EMAIL(2, "email", "email"), USER_MOBILE(3, "mobile", "mobile");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, UserOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (UserOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	UserOrderParameterEnum(int id, String name, String parameter) {
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
	public static UserOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
