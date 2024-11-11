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
package com.intentlabs.endlos.machine.view;

import java.math.BigDecimal;
import java.util.List;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.location.view.CityView;
import com.intentlabs.common.view.ActivationView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodestructure.view.BarcodeStructureView;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.enums.MachineActivityStatusEnum;
import com.intentlabs.endlos.machine.enums.MachineDevelopmentStatusEnum;
import com.intentlabs.endlos.machine.enums.MachineTypeEnum;
import com.intentlabs.endlos.machine.enums.MaterialEnum;

/**
 * This class is used to represent Machine object in json/in customer response.
 * 
 * @author Hemil.Shah
 * @since 18/11/2021
 */
public class MachineView extends ActivationView {

	private static final long serialVersionUID = -1942730193814291940L;
	private String machineId;
	private CustomerView customerView;
	private LocationView locationView;
	private TransactionView transactionView;
	private KeyValueView machineActivityStatus;
	private KeyValueView machineDevelopmentStatus;
	private String mpin;
	private String fullTextSearch;
	private String accessToken;
	private String refreshToken;
	private String uniqueToken;
	private String fileId;
	private Boolean isApplicableForAll;
	private List<MachineView> machineViews;
	private String patBottlePercentage;
	private String aluBottlePercentage;
	private String glassBottlePercentage;
	private String branchMachineNumber;
	private BarcodeTemplateView barcodeTemplateView;
	private Long startDate;
	private Long endDate;
	private KeyValueView binFullStatus;
	private CityView cityView;

	private String customerName;
	private String branchName;
	private String branchNumber;
	private List<BarcodeStructureView> barcodeStructureViews;
	private KeyValueView machineType;
	private Long plasticBinResetDate;
	private Long glassBinResetDate;
	private Long aluminiumnBinResetDate;
	private Long patBottleCount;
	private Long glassBottleCount;
	private Long aluBottleCount;
	private String exportFileName;
	private MachineCapacityView machineCapacityView;
	private MQTTConfigurationView mqttConfigurationView;
	private PLCConfigurationView plcConfigurationView;
	private MachineBarcodeFileView machineBarcodeFileView;

	private Long lastHardResetDate;
	private Long glassResetCount;
	private Long plasticResetCount;
	private Long aluminiumnResetCount;
	private Long totalBottleCount;
	private Long totalVoucher;
	private BigDecimal weight;
	private String password;
	private String hoursFromPickup;
	private boolean pickupEveryday;
	private Long noGlassBinPickup;
	private BigDecimal binWeight;
	private BigDecimal materialWeight;
	private BigDecimal totalWight;
	private String barcodeFilePath;
	private String setupPassword;
	private String technicianPassword;
	private String aluminiumnPrice;
	private String plasticPrice;
	private String glassPrice;

	private List<KeyValueView> acceptedMaterials;

	public boolean isPickupEveryday() {
		return pickupEveryday;
	}

	public void setPickupEveryday(boolean pickupEveryday) {
		this.pickupEveryday = pickupEveryday;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public TransactionView getTransactionView() {
		return transactionView;
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
	}

	public KeyValueView getMachineActivityStatus() {
		return machineActivityStatus;
	}

	public void setMachineActivityStatus(KeyValueView machineActivityStatus) {
		this.machineActivityStatus = machineActivityStatus;
	}

	public KeyValueView getMachineDevelopmentStatus() {
		return machineDevelopmentStatus;
	}

	public void setMachineDevelopmentStatus(KeyValueView machineDevelopmentStatus) {
		this.machineDevelopmentStatus = machineDevelopmentStatus;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Boolean getIsApplicableForAll() {
		return isApplicableForAll;
	}

	public void setIsApplicableForAll(Boolean isApplicableForAll) {
		this.isApplicableForAll = isApplicableForAll;
	}

	public List<MachineView> getMachineViews() {
		return machineViews;
	}

	public void setMachineViews(List<MachineView> machineViews) {
		this.machineViews = machineViews;
	}

	public String getPatBottlePercentage() {
		return patBottlePercentage;
	}

	public void setPatBottlePercentage(String patBottlePercentage) {
		this.patBottlePercentage = patBottlePercentage;
	}

	public String getAluBottlePercentage() {
		return aluBottlePercentage;
	}

	public void setAluBottlePercentage(String aluBottlePercentage) {
		this.aluBottlePercentage = aluBottlePercentage;
	}

	public String getGlassBottlePercentage() {
		return glassBottlePercentage;
	}

	public void setGlassBottlePercentage(String glassBottlePercentage) {
		this.glassBottlePercentage = glassBottlePercentage;
	}

	public BarcodeTemplateView getBarcodeTemplateView() {
		return barcodeTemplateView;
	}

	public void setBarcodeTemplateView(BarcodeTemplateView barcodeTemplateView) {
		this.barcodeTemplateView = barcodeTemplateView;
	}

	public static KeyValueView setMachineActivityStatus(Integer machineActivityStatus) {
		MachineActivityStatusEnum machineActivityStatusEnum = MachineActivityStatusEnum.fromId(machineActivityStatus);
		return KeyValueView.create(machineActivityStatusEnum.getId(), machineActivityStatusEnum.getName());
	}

	public static KeyValueView setMachineDevelopmentStatus(Integer machineDevelopmentStatus) {
		MachineDevelopmentStatusEnum machineDevelopmentStatusEnum = MachineDevelopmentStatusEnum
				.fromId(machineDevelopmentStatus);
		return KeyValueView.create(machineDevelopmentStatusEnum.getId(), machineDevelopmentStatusEnum.getName());
	}

	public static KeyValueView setMachineType(Integer machineType) {
		MachineTypeEnum machineTypeEnum = MachineTypeEnum.fromId(machineType);
		return KeyValueView.create(machineTypeEnum.getId(), machineTypeEnum.getName());
	}

	public String getBranchMachineNumber() {
		return branchMachineNumber;
	}

	public void setBranchMachineNumber(String branchMachineNumber) {
		this.branchMachineNumber = branchMachineNumber;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public KeyValueView getBinFullStatus() {
		return binFullStatus;
	}

	public void setBinFullStatus(KeyValueView binFullStatus) {
		this.binFullStatus = binFullStatus;
	}

	public CityView getCityView() {
		return cityView;
	}

	public void setCityView(CityView cityView) {
		this.cityView = cityView;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public List<BarcodeStructureView> getBarcodeStructureViews() {
		return barcodeStructureViews;
	}

	public void setBarcodeStructureViews(List<BarcodeStructureView> barcodeStructureViews) {
		this.barcodeStructureViews = barcodeStructureViews;
	}

	public KeyValueView getMachineType() {
		return machineType;
	}

	public void setMachineType(KeyValueView machineType) {
		this.machineType = machineType;
	}

	public static void isValid(MachineView machineView) throws EndlosAPIException {
//		Validator.STRING.isValid(new InputField("MACHINE_SERIAL_NUMBER", machineView.getMachineId(), true, 20));
	}

	public Long getPlasticBinResetDate() {
		return plasticBinResetDate;
	}

	public Long getGlassBinResetDate() {
		return glassBinResetDate;
	}

	public Long getAluminiumnBinResetDate() {
		return aluminiumnBinResetDate;
	}

	public void setPlasticBinResetDate(Long plasticBinResetDate) {
		this.plasticBinResetDate = plasticBinResetDate;
	}

	public void setGlassBinResetDate(Long glassBinResetDate) {
		this.glassBinResetDate = glassBinResetDate;
	}

	public void setAluminiumnBinResetDate(Long aluminiumnBinResetDate) {
		this.aluminiumnBinResetDate = aluminiumnBinResetDate;
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

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public MachineCapacityView getMachineCapacityView() {
		return machineCapacityView;
	}

	public void setMachineCapacityView(MachineCapacityView machineCapacityView) {
		this.machineCapacityView = machineCapacityView;
	}

	public MQTTConfigurationView getMqttConfigurationView() {
		return mqttConfigurationView;
	}

	public void setMqttConfigurationView(MQTTConfigurationView mqttConfigurationView) {
		this.mqttConfigurationView = mqttConfigurationView;
	}

	public PLCConfigurationView getPlcConfigurationView() {
		return plcConfigurationView;
	}

	public void setPlcConfigurationView(PLCConfigurationView plcConfigurationView) {
		this.plcConfigurationView = plcConfigurationView;
	}

	public MachineBarcodeFileView getMachineBarcodeFileView() {
		return machineBarcodeFileView;
	}

	public void setMachineBarcodeFileView(MachineBarcodeFileView machineBarcodeFileView) {
		this.machineBarcodeFileView = machineBarcodeFileView;
	}

	public Long getLastHardResetDate() {
		return lastHardResetDate;
	}

	public void setLastHardResetDate(Long lastHardResetDate) {
		this.lastHardResetDate = lastHardResetDate;
	}

	public Long getGlassResetCount() {
		return glassResetCount;
	}

	public void setGlassResetCount(Long glassResetCount) {
		this.glassResetCount = glassResetCount;
	}

	public Long getPlasticResetCount() {
		return plasticResetCount;
	}

	public void setPlasticResetCount(Long plasticResetCount) {
		this.plasticResetCount = plasticResetCount;
	}

	public Long getAluminiumnResetCount() {
		return aluminiumnResetCount;
	}

	public void setAluminiumnResetCount(Long aluminiumnResetCount) {
		this.aluminiumnResetCount = aluminiumnResetCount;
	}

	public Long getTotalBottleCount() {
		return totalBottleCount;
	}

	public void setTotalBottleCount(Long totalBottleCount) {
		this.totalBottleCount = totalBottleCount;
	}

	public Long getTotalVoucher() {
		return totalVoucher;
	}

	public void setTotalVoucher(Long totalVoucher) {
		this.totalVoucher = totalVoucher;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHoursFromPickup() {
		return hoursFromPickup;
	}

	public void setHoursFromPickup(String hoursFromPickup) {
		this.hoursFromPickup = hoursFromPickup;
	}

	public Long getNoGlassBinPickup() {
		return noGlassBinPickup;
	}

	public void setNoGlassBinPickup(Long noGlassBinPickup) {
		this.noGlassBinPickup = noGlassBinPickup;
	}

	public BigDecimal getBinWeight() {
		return binWeight;
	}

	public void setBinWeight(BigDecimal binWeight) {
		this.binWeight = binWeight;
	}

	public BigDecimal getMaterialWeight() {
		return materialWeight;
	}

	public void setMaterialWeight(BigDecimal materialWeight) {
		this.materialWeight = materialWeight;
	}

	public BigDecimal getTotalWight() {
		return totalWight;
	}

	public void setTotalWight(BigDecimal totalWight) {
		this.totalWight = totalWight;
	}

	public String getBarcodeFilePath() {
		return barcodeFilePath;
	}

	public void setBarcodeFilePath(String barcodeFilePath) {
		this.barcodeFilePath = barcodeFilePath;
	}

	public String getSetupPassword() {
		return setupPassword;
	}

	public void setSetupPassword(String setupPassword) {
		this.setupPassword = setupPassword;
	}

	public String getTechnicianPassword() {
		return technicianPassword;
	}

	public void setTechnicianPassword(String technicianPassword) {
		this.technicianPassword = technicianPassword;
	}

	public String getAluminiumnPrice() {
		return aluminiumnPrice;
	}

	public void setAluminiumnPrice(String aluminiumnPrice) {
		this.aluminiumnPrice = aluminiumnPrice;
	}

	public String getPlasticPrice() {
		return plasticPrice;
	}

	public void setPlasticPrice(String plasticPrice) {
		this.plasticPrice = plasticPrice;
	}

	public String getGlassPrice() {
		return glassPrice;
	}

	public void setGlassPrice(String glassPrice) {
		this.glassPrice = glassPrice;
	}

	public List<KeyValueView> getAcceptedMaterials() {
		return acceptedMaterials;
	}

	public void setAcceptedMaterials(List<KeyValueView> acceptedMaterials) {
		this.acceptedMaterials = acceptedMaterials;
	}
}