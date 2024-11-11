package com.intentlabs.endlos.machine.enums;

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
public enum MachineOrderParameterEnum implements OrderParamEnumType {

	CREATE_DATE(1,"createDate","createDate"),MACHINE_ID(2, "machineId", "machineId"), MACHINE_TYPE(3, "machineType", "machineType"),
	MACHINE_ACTIVITY_STATUS(4, "status", "machineActivityStatus"),
	MACHINE_PHYSICAL_STATUS(5, "physicalStatus", "machineDevelopmentStatus"),
	CUSTOMER_NAME(6, "customerName", "customerModel.name"), //CITY(7, "city", "cityModel.name"),
	CITY(7, "city", "locationModel.cityModel"),
	BRANCH_NAME(8, "branchName", "locationModel.name"), BRANCH_NUMBER(9, "branchNumber", "locationModel.branchNumber"),
	BRANCH_WISE_MACHINE_NUMBER(10, "branchWiseMachineNumber", "branchMachineNumber"),
	BARCODE_TEMPLATE_NAME(11, "barcodeTemplateName", "barcodeTemplateModel.name");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, MachineOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (MachineOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	MachineOrderParameterEnum(int id, String name, String parameter) {
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
	public static MachineOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
