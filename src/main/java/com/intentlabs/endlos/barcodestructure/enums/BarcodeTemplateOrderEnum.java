package com.intentlabs.endlos.barcodestructure.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

/**
 * 
 * This is used in search api for order data of barcodeTemplate model.
 * 
 * @author Hemil.Shah
 * @since 13/08/2022
 */
public enum BarcodeTemplateOrderEnum implements OrderParamEnumType {

	BARCODE_TEMPLATE_NAME(1, "barcodeTemplateName", "name"), BARCODE_TEMPLATE_LENGTH(2, "totalLength", "totalLength"),
	STATUS(3, "status", "completed");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, BarcodeTemplateOrderEnum> MAP = new HashMap<>();

	static {
		for (BarcodeTemplateOrderEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	BarcodeTemplateOrderEnum(int id, String name, String parameter) {
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
	public static BarcodeTemplateOrderEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
