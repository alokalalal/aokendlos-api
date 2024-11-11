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
public enum MachineCapacityParameterEnum implements OrderParamEnumType {

	CREATE_DATE(1, "createDate", "createDate"), PLASTIC_CAPACITY(2, "plasticCapacity", "plasticCapacity"),
	GLASS_CAPACITY(3, "glassCapacity", "glassCapacity"), ALU_CAPACITY(4, "aluminiumnCapacity", "aluminiumnCapacity"),
	MACHINE_ID(5, "machineId", "machineModel.machineId"), PRINT_CAPACITY(6, "printCapacity", "printCapacity"),
	MAX_TRANSACTION(7, "maxTransaction", "maxTransaction"),MAX_AUTOCLEANING(7, "maxAutoCleaning", "maxAutoCleaning");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, MachineCapacityParameterEnum> MAP = new HashMap<>();

	static {
		for (MachineCapacityParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	MachineCapacityParameterEnum(int id, String name, String parameter) {
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
	public static MachineCapacityParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
