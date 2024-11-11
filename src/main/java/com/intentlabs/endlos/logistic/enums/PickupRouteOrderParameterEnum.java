package com.intentlabs.endlos.logistic.enums;

import com.intentlabs.common.enums.OrderParamEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This is used in search api for order data of Pickup Route model.
 * 
 * @author Milan.Gohil
 * @since 12/12/2023
 */
public enum PickupRouteOrderParameterEnum implements OrderParamEnumType {

	NAME(1, "pickupRouteName", "name"), CREATE_DATE(2, "pickupRouteNo", "no");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, PickupRouteOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (PickupRouteOrderParameterEnum customerOrderParameterEnum : values()) {
			MAP.put(customerOrderParameterEnum.getId(), customerOrderParameterEnum);
		}
	}

	PickupRouteOrderParameterEnum(int id, String name, String parameter) {
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
	public static PickupRouteOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
