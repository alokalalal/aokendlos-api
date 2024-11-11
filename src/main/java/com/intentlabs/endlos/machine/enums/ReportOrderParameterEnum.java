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
public enum ReportOrderParameterEnum implements OrderParamEnumType {
	CREATE_DATE(1, "createDate", "createDate"),
	CUSTOMER_NAME(2, "customerName", "customerModel.name"),
	LOCATION_NAME(3, "branchName", "locationModel.name"),
	BRANCH_ID(4, "branchId", "locationModel.branchNumber"),
	MACHINE_ID(5, "machineId", "machineModel.machineId"), 
	CREATE_DATE_2(6, "createDate", "createDate"),
	SMALL_PAT_BOTTLE(7, "plastic", "smallPatBottleCount"),
	BIG_PAT_BOTTLE(8, "plastic1.5", "bigPatBottleCount"),
	TOTAL_PAT_BOTTLE(9, "totalPlastic", "patBottleCount"),
	SMALL_GLASS_BOTTLE(10, "glass", "smallGlassBottleCount"),
	BIG_GLASS_BOTTLE(11, "glass1.5", "bigGlassBottleCount"), 
	TOTAL_GLASS_BOTTLE(12, "totalGlass", "glassBottleCount"),
	TOTAL_ALUMINIUM_BOTTLE(13, "totalAluminium", "aluBottleCount"),
	TOTAL(14, "total", "totalBottleCount"),
	AMOUNT(15, "amount", "totalValue");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, ReportOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (ReportOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	ReportOrderParameterEnum(int id, String name, String parameter) {
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
	public static ReportOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
