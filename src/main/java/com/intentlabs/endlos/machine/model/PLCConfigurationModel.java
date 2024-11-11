package com.intentlabs.endlos.machine.model;

import com.intentlabs.common.model.IdentifierModel;

public class PLCConfigurationModel extends IdentifierModel {

	private static final long serialVersionUID = 2315447401434369563L;

	private MachineModel machineModel;
	private Long plcAddressCDoorFrequency;
	private Long plcAddressFcFrequency;
	private Long barcodeScannerResponseWait;
	private Long plcAddressPlasticShredderCurrent;
	private Long plcAddressGlassShredderCurrent;
	private Long plcAddressAluminiumShredderCurrent;
	private Long plcAddressBcFrequency;
	private Long objectDetectFirstResponseWait;
	private Long plcAddressSlidingConveyorFrequency;
	private String plcIpAddress;
	private Long createDate;

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public Long getPlcAddressCDoorFrequency() {
		return plcAddressCDoorFrequency;
	}

	public Long getPlcAddressFcFrequency() {
		return plcAddressFcFrequency;
	}

	public Long getBarcodeScannerResponseWait() {
		return barcodeScannerResponseWait;
	}

	public Long getPlcAddressPlasticShredderCurrent() {
		return plcAddressPlasticShredderCurrent;
	}

	public Long getPlcAddressGlassShredderCurrent() {
		return plcAddressGlassShredderCurrent;
	}

	public Long getPlcAddressAluminiumShredderCurrent() {
		return plcAddressAluminiumShredderCurrent;
	}

	public Long getPlcAddressBcFrequency() {
		return plcAddressBcFrequency;
	}

	public Long getObjectDetectFirstResponseWait() {
		return objectDetectFirstResponseWait;
	}

	public Long getPlcAddressSlidingConveyorFrequency() {
		return plcAddressSlidingConveyorFrequency;
	}

	public String getPlcIpAddress() {
		return plcIpAddress;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setPlcAddressCDoorFrequency(Long plcAddressCDoorFrequency) {
		this.plcAddressCDoorFrequency = plcAddressCDoorFrequency;
	}

	public void setPlcAddressFcFrequency(Long plcAddressFcFrequency) {
		this.plcAddressFcFrequency = plcAddressFcFrequency;
	}

	public void setBarcodeScannerResponseWait(Long barcodeScannerResponseWait) {
		this.barcodeScannerResponseWait = barcodeScannerResponseWait;
	}

	public void setPlcAddressPlasticShredderCurrent(Long plcAddressPlasticShredderCurrent) {
		this.plcAddressPlasticShredderCurrent = plcAddressPlasticShredderCurrent;
	}

	public void setPlcAddressGlassShredderCurrent(Long plcAddressGlassShredderCurrent) {
		this.plcAddressGlassShredderCurrent = plcAddressGlassShredderCurrent;
	}

	public void setPlcAddressAluminiumShredderCurrent(Long plcAddressAluminiumShredderCurrent) {
		this.plcAddressAluminiumShredderCurrent = plcAddressAluminiumShredderCurrent;
	}

	public void setPlcAddressBcFrequency(Long plcAddressBcFrequency) {
		this.plcAddressBcFrequency = plcAddressBcFrequency;
	}

	public void setObjectDetectFirstResponseWait(Long objectDetectFirstResponseWait) {
		this.objectDetectFirstResponseWait = objectDetectFirstResponseWait;
	}

	public void setPlcAddressSlidingConveyorFrequency(Long plcAddressSlidingConveyorFrequency) {
		this.plcAddressSlidingConveyorFrequency = plcAddressSlidingConveyorFrequency;
	}

	public void setPlcIpAddress(String plcIpAddress) {
		this.plcIpAddress = plcIpAddress;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}
