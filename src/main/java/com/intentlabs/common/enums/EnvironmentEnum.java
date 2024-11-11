package com.intentlabs.common.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.modelenums.ModelEnum;

/**
 * This enum is used to maintain environment values.
 * 
 * @author Nirav.Shah
 * @since 24/05/2019
 */
public enum EnvironmentEnum implements ModelEnum {

	DEVELOPMENT(1, "Development"), UAT(2, "Uat"), PRODUCTION(3, "Production"), TESTING(3, "Testing");

	private final Integer id;
	private final String name;

	public static final Map<Integer, EnvironmentEnum> MAP = new HashMap<>();

	static {
		for (EnvironmentEnum status : values()) {
			MAP.put(status.getId(), status);
		}
	}

	EnvironmentEnum(Integer id, String name) {
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

	public static EnvironmentEnum fromId(Integer id) {
		return MAP.get(id);
	}

}
