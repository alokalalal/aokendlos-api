package com.intentlabs.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used in search api for order data by order type.
 * 
 * @author Tushar.Patel
 * @since 09/12/2021
 */
public enum OrderTypeEnum implements EnumType {

	DESC(1, "desc"), ASC(2, "asc");

	private final int id;
	private final String name;
	private static final Map<Integer, OrderTypeEnum> MAP = new HashMap<>();

	static {
		for (OrderTypeEnum orderTypeEnum : values()) {
			MAP.put(orderTypeEnum.getId(), orderTypeEnum);
		}
	}

	OrderTypeEnum(int id, String name) {
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
	 * @return order type enum
	 */
	public static OrderTypeEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
