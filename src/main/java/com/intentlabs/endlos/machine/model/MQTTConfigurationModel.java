package com.intentlabs.endlos.machine.model;

import com.intentlabs.common.model.IdentifierModel;

public class MQTTConfigurationModel extends IdentifierModel {

	private static final long serialVersionUID = 6905307878121352305L;
	private MachineModel machineModel;
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

	public MachineModel getMachineModel() {
		return machineModel;
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

	public String getAlgorithm() {
		return algorithm;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMachineModel(MachineModel machineModel) {
		this.machineModel = machineModel;
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
}