package com.intentlabs.endlos.machine.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.enums.OrderParamEnumType;

public enum PLCConfigurationParameterEnum implements OrderParamEnumType {

	CREATE_DATE(1, "createDate", "createDate"),PLC_ADDRESS_C_DOOR_FREQUENCY(2, "plcAddressCDoorFrequency", "plcAddressCDoorFrequency"),
	PLC_ADDRESS_FC_FREQUENCY(3, "plcAddressFcFrequency", "plcAddressFcFrequency"),PLC_ADDRESS_PLASTIC_SHREDDER_CURRENT(4, "plcAddressPlasticShredderCurrent", "plcAddressPlasticShredderCurrent"),
	PLC_ADDRESS_GLASS_SHREDDER_CURRENT(5, "plAaddressGlassShredderCurrent", "plAaddressGlassShredderCurrent"),PLC_ADDRESS_ALUMINIUM_SHREDDER_CURRENT(6, "plcAddressAluminiumShredderCurrent","plcAddressAluminiumShredderCurrent"),
	PLC_ADDRESS_BC_FREQUENCY(7, "plcAddressBcFrequency", "plcAddressBcFrequency"),PLC_ADDRESS_SLIDING_CONVEYOR_FREQUENCY(8, "plcAddressSlidingConveyorFrequency",	"plcAddressSlidingConveyorFrequency"),
	BARCODE_SCANNER_RESPONSE_WAIT(9, "barcodeScannerResponseWait", "barcodeScannerResponseWait"),OBJECT_DETECT_FIRST_RESPONSE_WAIT(10, "objectDetectFirstResponseWait", "objectDetectFirstResponseWait"),
	MACHINE_ID(11, "machineId", "machineModel.machineId"), PLC_IP_ADDRESS(12, "plcIpAddress", "plcIpAddress");

	private final int id;
	private final String name;
	private final String parameter;
	private static final Map<Integer, PLCConfigurationParameterEnum> MAP = new HashMap<>();

	static {
		for (PLCConfigurationParameterEnum plcConfigurationParameter : values()) {
			MAP.put(plcConfigurationParameter.getId(), plcConfigurationParameter);
		}
	}

	PLCConfigurationParameterEnum(int id, String name, String parameter) {
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
	public static PLCConfigurationParameterEnum fromId(Integer id) {
		return MAP.get(id);
	}
}
