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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;

/**
 * This is Machine Log view which maps machine log table to class.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@JsonInclude(Include.NON_NULL)
public class MQTTConfigurationView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;

	private MachineView machineView;
	private String mqttBridgeHostName;
	private String mqttBridgePort;
	private String projectId;
	private String cloudRegion;
	private String registryId;
	private String gatewayId;
	private String publicKeyFilePath;
	private String privateKeyFilePath;
	private String derKeyFilePath;
	private String algorithm;
	private String deviceId;
	private String messageType;
	private Long createDate;
	private Long startDate;
	private Long endDate;

	public MachineView getMachineView() {
		return machineView;
	}

	public String getMqttBridgeHostName() {
		return mqttBridgeHostName;
	}

	public String getMqttBridgePort() {
		return mqttBridgePort;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getCloudRegion() {
		return cloudRegion;
	}

	public String getRegistryId() {
		return registryId;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}

	public String getPublicKeyFilePath() {
		return publicKeyFilePath;
	}

	public void setPublicKeyFilePath(String publicKeyFilePath) {
		this.publicKeyFilePath = publicKeyFilePath;
	}

	public String getDerKeyFilePath() {
		return derKeyFilePath;
	}

	public void setDerKeyFilePath(String derKeyFilePath) {
		this.derKeyFilePath = derKeyFilePath;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public void setMqttBridgeHostName(String mqttBridgeHostName) {
		this.mqttBridgeHostName = mqttBridgeHostName;
	}

	public void setMqttBridgePort(String mqttBridgePort) {
		this.mqttBridgePort = mqttBridgePort;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setCloudRegion(String cloudRegion) {
		this.cloudRegion = cloudRegion;
	}

	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public void setPrivateKeyFilePath(String privateKeyFilePath) {
		this.privateKeyFilePath = privateKeyFilePath;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getStartDate() {
		return startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
}