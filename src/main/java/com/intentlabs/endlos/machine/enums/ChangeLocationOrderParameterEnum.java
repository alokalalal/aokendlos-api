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
public enum ChangeLocationOrderParameterEnum implements OrderParamEnumType {
	REQUEST_DATE(1, "requestDate", "requestDate"), MACHINE_ID(2, "machineId", "machineModel.machineId"),
	CUSTOMER_NAME(3, "customerName", "oldCustomerModel.name"), BRANCH_NAME(4, "branchName", "oldLocationModel.name"),
	BARCODE_TEMPLATE_NAME(5, "barcodeTemplateName", "oldBarcodeTemplateModel.name"),
	BRANCH_WISE_MACHINE_NUMBER(6, "branchWiseMachineNumber", "oldBranchMachineNumber"), STATUS(7, "status", "status");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, ChangeLocationOrderParameterEnum> MAP = new HashMap<>();

	static {
		for (ChangeLocationOrderParameterEnum machineOrderParameter : values()) {
			MAP.put(machineOrderParameter.getId(), machineOrderParameter);
		}
	}

	ChangeLocationOrderParameterEnum(int id, String name, String parameter) {
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
	public static ChangeLocationOrderParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
