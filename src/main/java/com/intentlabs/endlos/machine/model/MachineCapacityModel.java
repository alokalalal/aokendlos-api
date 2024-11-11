package com.intentlabs.endlos.machine.model;

import com.intentlabs.common.model.IdentifierModel;

public class MachineCapacityModel extends IdentifierModel {

	private static final long serialVersionUID = 6905307878121352305L;
	private MachineModel machineModel;
	private Long plasticCapacity;
	private Long glassCapacity;
	private Long aluminiumnCapacity;
	private Long printCapacity;
	private Long maxTransaction;
	private Long maxAutoCleaning;
	private Long createDate;

	public MachineModel getMachineModel() {
		return machineModel;
	}

	public Long getPlasticCapacity() {
		return plasticCapacity;
	}

	public Long getGlassCapacity() {
		return glassCapacity;
	}

	public Long getAluminiumnCapacity() {
		return aluminiumnCapacity;
	}

	public Long getPrintCapacity() {
		return printCapacity;
	}

	public Long getMaxTransaction() {
		return maxTransaction;
	}

	public Long getMaxAutoCleaning() {
		return maxAutoCleaning;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
	}

	public void setPlasticCapacity(Long plasticCapacity) {
		this.plasticCapacity = plasticCapacity;
	}

	public void setGlassCapacity(Long glassCapacity) {
		this.glassCapacity = glassCapacity;
	}

	public void setAluminiumnCapacity(Long aluminiumnCapacity) {
		this.aluminiumnCapacity = aluminiumnCapacity;
	}

	public void setPrintCapacity(Long printCapacity) {
		this.printCapacity = printCapacity;
	}

	public void setMaxTransaction(Long maxTransaction) {
		this.maxTransaction = maxTransaction;
	}

	public void setMaxAutoCleaning(Long maxAutoCleaning) {
		this.maxAutoCleaning = maxAutoCleaning;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}