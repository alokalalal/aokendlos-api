package com.intentlabs.common.notification.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.EnumType;

/**
 * This enum is used to maintain email/sms/push notification status.
 * 
 * @author Dhruvang.Joshi
 * @since 26/07/2017
 */
public enum EmailStatusEnum implements EnumType {

	NEW(0, "NEW"), INPROCESS(1, "INPROCESS"), FAILED(2, "FAILED"), SENT(3, "SENT");

	private final int id;
	private final String name;

	public static final Map<Integer, EmailStatusEnum> MAP = new HashMap<>();

	static {
		for (EmailStatusEnum status : values()) {
			MAP.put(status.getId(), status);
		}
	}

	EmailStatusEnum(Integer id, String name) {
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

	public static EmailStatusEnum fromId(Integer status) {
		return MAP.get(status);
	}

}
