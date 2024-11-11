package com.intentlabs.endlos.machine.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;

@JsonInclude(Include.NON_NULL)
public class PLCConfigurationView extends IdentifierView {

	private static final long serialVersionUID = 5110380148972483293L;
	private MachineView machineView;
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
	private Long startDate;
	private Long endDate;

	public MachineView getMachineView() {
		return machineView;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
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

	public Long getStartDate() {
		return startDate;
	}

	public Long getEndDate() {
		return endDate;
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

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
}
