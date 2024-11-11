/*******************************************************************************

 * Copyright -2019 @intentlabs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.intentlabs.endlos.machine.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.intentlabs.common.model.ActivationModel;
import com.intentlabs.common.model.IdentifierModel;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.machine.enums.*;

/**
 * This is Machine model which maps device table to class.
 * 
 * @author Hemil.Shah
 * @since 18/11/2021
 */
public class MachineModel extends ActivationModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private String machineId;
	private String uniqueToken;
	private CustomerModel customerModel;
	private LocationModel locationModel;
	private Integer machineActivityStatus;
	private Integer machineDevelopmentStatus;
	private String mpin;
	private BigDecimal patBottlePercentage;
	private BigDecimal aluBottlePercentage;
	private BigDecimal glassBottlePercentage;
	private String branchMachineNumber;
	private BarcodeTemplateModel barcodeTemplateModel;
	private Integer binFullStatus;
	private boolean barcodeTemplateChanged;
	private Boolean rejected;
	private Integer machineType;
	private Long patBottleCount;
	private Long glassBottleCount;
	private Long aluBottleCount;
	private MachineCapacityModel machineCapacityModel;
	private MQTTConfigurationModel mqttConfigurationModel;
	private PLCConfigurationModel plcConfigurationModel;
	private MachineBarcodeFileModel machineBarcodeFileModel;
	private boolean passwordChanged;
	private String password;
	private boolean barcodeChanged;
	private boolean logoChanged;
	private boolean capacityChanged;
	private Set<Integer> acceptedMaterials = new HashSet<>();

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	public CustomerModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}

	public MachineActivityStatusEnum getMachineActivityStatus() {
		return MachineActivityStatusEnum.fromId(machineActivityStatus);
	}

	public void setMachineActivityStatus(Integer machineActivityStatus) {
		this.machineActivityStatus = machineActivityStatus;
	}

	public MachineDevelopmentStatusEnum getMachineDevelopmentStatus() {
		return MachineDevelopmentStatusEnum.fromId(machineDevelopmentStatus);
	}

	public void setMachineDevelopmentStatus(Integer machineDevelopmentStatus) {
		this.machineDevelopmentStatus = machineDevelopmentStatus;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public BigDecimal getPatBottlePercentage() {
		return patBottlePercentage;
	}

	public void setPatBottlePercentage(BigDecimal patBottlePercentage) {
		this.patBottlePercentage = patBottlePercentage;
	}

	public BigDecimal getAluBottlePercentage() {
		return aluBottlePercentage;
	}

	public void setAluBottlePercentage(BigDecimal aluBottlePercentage) {
		this.aluBottlePercentage = aluBottlePercentage;
	}

	public BigDecimal getGlassBottlePercentage() {
		return glassBottlePercentage;
	}

	public void setGlassBottlePercentage(BigDecimal glassBottlePercentage) {
		this.glassBottlePercentage = glassBottlePercentage;
	}

	public String getBranchMachineNumber() {
		return branchMachineNumber;
	}

	public void setBranchMachineNumber(String branchMachineNumber) {
		this.branchMachineNumber = branchMachineNumber;
	}

	public BarcodeTemplateModel getBarcodeTemplateModel() {
		return barcodeTemplateModel;
	}

	public void setBarcodeTemplateModel(BarcodeTemplateModel barcodeTemplateModel) {
		this.barcodeTemplateModel = barcodeTemplateModel;
	}

	public MachineBinFullStatusEnum getBinFullStatus() {
		return MachineBinFullStatusEnum.fromId(binFullStatus);
	}

	public void setBinFullStatus(Integer binFullStatus) {
		this.binFullStatus = binFullStatus;
	}

	public boolean isBarcodeTemplateChanged() {
		return barcodeTemplateChanged;
	}

	public void setBarcodeTemplateChanged(boolean barcodeTemplateChanged) {
		this.barcodeTemplateChanged = barcodeTemplateChanged;
	}

	public Boolean getRejected() {
		return rejected;
	}

	public void setRejected(Boolean rejected) {
		this.rejected = rejected;
	}

	public MachineTypeEnum getMachineType() {
		return MachineTypeEnum.fromId(machineType);
	}

	public void setMachineType(Integer machineType) {
		this.machineType = machineType;
	}

	public Long getPatBottleCount() {
		return patBottleCount;
	}

	public Long getGlassBottleCount() {
		return glassBottleCount;
	}

	public Long getAluBottleCount() {
		return aluBottleCount;
	}

	public void setPatBottleCount(Long patBottleCount) {
		this.patBottleCount = patBottleCount;
	}

	public void setGlassBottleCount(Long glassBottleCount) {
		this.glassBottleCount = glassBottleCount;
	}

	public void setAluBottleCount(Long aluBottleCount) {
		this.aluBottleCount = aluBottleCount;
	}

	public MachineCapacityModel getMachineCapacityModel() {
		return machineCapacityModel;
	}

	public void setMachineCapacityModel(MachineCapacityModel machineCapacityModel) {
		this.machineCapacityModel = machineCapacityModel;
	}

	public MQTTConfigurationModel getMqttConfigurationModel() {
		return mqttConfigurationModel;
	}

	public void setMqttConfigurationModel(MQTTConfigurationModel mqttConfigurationModel) {
		this.mqttConfigurationModel = mqttConfigurationModel;
	}

	public PLCConfigurationModel getPlcConfigurationModel() {
		return plcConfigurationModel;
	}

	public void setPlcConfigurationModel(PLCConfigurationModel plcConfigurationModel) {
		this.plcConfigurationModel = plcConfigurationModel;
	}

	public MachineBarcodeFileModel getMachineBarcodeFileModel() {
		return machineBarcodeFileModel;
	}

	public void setMachineBarcodeFileModel(MachineBarcodeFileModel machineBarcodeFileModel) {
		this.machineBarcodeFileModel = machineBarcodeFileModel;
	}

	public boolean isBarcodeChanged() {
		return barcodeChanged;
	}

	public void setBarcodeChanged(boolean barcodeChanged) {
		this.barcodeChanged = barcodeChanged;
	}

	public boolean isLogoChanged() {
		return logoChanged;
	}

	public void setLogoChanged(boolean logoChanged) {
		this.logoChanged = logoChanged;
	}

	public boolean isCapacityChanged() {
		return capacityChanged;
	}

	public void setCapacityChanged(boolean capacityChanged) {
		this.capacityChanged = capacityChanged;
	}

	public Set<Integer> getAcceptedMaterials() {
		return acceptedMaterials;
	}

	public void setAcceptedMaterials(Set<Integer> acceptedMaterials) {
		this.acceptedMaterials = acceptedMaterials;
	}

	public void addAcceptedMaterials(Integer acceptedMaterials) {
		this.acceptedMaterials.add(acceptedMaterials);
	}

	public void removeAcceptedMaterials(Integer acceptedMaterials) {
		this.acceptedMaterials.remove(acceptedMaterials);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}