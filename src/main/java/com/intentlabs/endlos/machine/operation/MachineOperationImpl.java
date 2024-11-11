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
package com.intentlabs.endlos.machine.operation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.enums.DataSheetFileExtensionEnum;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.threadlocal.MachineAuditor;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.ExcelRow;
import com.intentlabs.common.util.FileUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.util.WebUtil;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeItemService;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodestructure.model.BarcodeStructureModel;
import com.intentlabs.endlos.barcodestructure.service.BarcodeStructureService;
import com.intentlabs.endlos.barcodestructure.view.BarcodeStructureView;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.service.CustomerService;
import com.intentlabs.endlos.customer.service.LocationService;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.firestore.config.FirestoreConfiguration;
import com.intentlabs.endlos.machine.enums.MachineActivityStatusEnum;
import com.intentlabs.endlos.machine.enums.MachineDevelopmentStatusEnum;
import com.intentlabs.endlos.machine.enums.MachineTypeEnum;
import com.intentlabs.endlos.machine.enums.MaterialEnum;
import com.intentlabs.endlos.machine.enums.StatusEnum;
import com.intentlabs.endlos.machine.enums.TransactionFields;
import com.intentlabs.endlos.machine.model.DataTableModel;
import com.intentlabs.endlos.machine.model.MQTTConfigurationModel;
import com.intentlabs.endlos.machine.model.MachineCapacityModel;
import com.intentlabs.endlos.machine.model.MachineLogModel;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.model.PLCConfigurationModel;
import com.intentlabs.endlos.machine.model.TransactionLogModel;
import com.intentlabs.endlos.machine.model.TransactionModel;
import com.intentlabs.endlos.machine.service.DataTableService;
import com.intentlabs.endlos.machine.service.MQTTConfigurationService;
import com.intentlabs.endlos.machine.service.MachineCapacityService;
import com.intentlabs.endlos.machine.service.MachineLogService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.PLCConfigurationService;
import com.intentlabs.endlos.machine.service.TransactionLogService;
import com.intentlabs.endlos.machine.service.TransactionService;
import com.intentlabs.endlos.machine.view.DataTableView;
import com.intentlabs.endlos.machine.view.MQTTConfigurationView;
import com.intentlabs.endlos.machine.view.MachineCapacityView;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.MachineViewForLogistic;
import com.intentlabs.endlos.machine.view.PLCConfigurationView;
import com.intentlabs.endlos.machine.view.TransactionView;
import com.itextpdf.html2pdf.HtmlConverter;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Component(value = "machineOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MachineOperationImpl extends AbstractOperation<MachineModel, MachineView> implements MachineOperation {

	@Autowired
	private MachineService machineService;

	@Autowired
	private FileService fileService;

	@Autowired
	private DataTableService dataTableService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private FirestoreConfiguration firestoreConfiguration;

	@Autowired
	private BarcodeStructureService barcodeStructureService;

	@Autowired
	private MachineLogService machineLogService;

	@Autowired
	private MQTTConfigurationService mqttConfigurationService;

	@Autowired
	private PLCConfigurationService plcConfigurationService;

	@Autowired
	private MachineCapacityService machineCapacityService;

	@Autowired
	private MachineBarcodeItemService machineBarcodeItemService;

	@Override
	public BaseService<MachineModel> getService() {
		return machineService;
	}

	@Override
	protected MachineModel loadModel(MachineView machineView) {
		return machineService.get(machineView.getId());
	}

	@Override
	protected MachineModel getNewModel() {
		return new MachineModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doSave(MachineView machineView) throws EndlosAPIException {
		MachineModel machineModel = machineService.getByMachineId(machineView.getMachineId());
		if (machineModel != null) {
			throw new EndlosAPIException(ResponseCode.MACHINE_ID_ALREADY_EXIST.getCode(),
					ResponseCode.MACHINE_ID_ALREADY_EXIST.getMessage());
		}
		machineModel = toModel(getNewModel(), machineView);
		machineModel.setMachineDevelopmentStatus(MachineDevelopmentStatusEnum.MANUFACTURING.getId());
		machineModel.setMachineActivityStatus(MachineActivityStatusEnum.ERROR.getId());
		String token = Utility.generateToken(8);
		machineModel.setUniqueToken(token);

		setAcceptedMaterials(machineModel, machineView);

		machineService.create(machineModel);

		setPlcConfiguration(machineModel);
		setMachineCapacity(machineModel);
//		createDeviceInClearBladeAndSetMqttDetails(machineModel);

//		Map<String, Object> data = toMachine(machineModel);
//		firestoreService.doSaveMachine(data, machineModel.getMachineId());

		MachineView machineModelView = fromModel(machineModel);
		if (machineModel.getMqttConfigurationModel() != null) {
			MQTTConfigurationView mqttConfigurationView = new MQTTConfigurationView();
			mqttConfigurationView.setId(machineModel.getMqttConfigurationModel().getId());
			mqttConfigurationView
					.setMqttBridgeHostName(machineModel.getMqttConfigurationModel().getMqttBridgeHostName());
			mqttConfigurationView.setMqttBridgePort(machineModel.getMqttConfigurationModel().getMqttBridgePort());
			mqttConfigurationView.setProjectId(machineModel.getMqttConfigurationModel().getProjectId());
			mqttConfigurationView.setCloudRegion(machineModel.getMqttConfigurationModel().getCloudRegion());
			mqttConfigurationView.setRegistryId(machineModel.getMqttConfigurationModel().getRegistryId());
			mqttConfigurationView.setGatewayId(machineModel.getMqttConfigurationModel().getGatewayId());
			mqttConfigurationView
					.setPrivateKeyFilePath(machineModel.getMqttConfigurationModel().getPrivateKeyFilePath());
			mqttConfigurationView.setPublicKeyFilePath(machineModel.getMqttConfigurationModel().getPublicKeyFilePath());
			mqttConfigurationView.setDerKeyFilePath(machineModel.getMqttConfigurationModel().getDerKeyFilePath());
			mqttConfigurationView.setAlgorithm(machineModel.getMqttConfigurationModel().getAlgorithm());
			mqttConfigurationView.setDeviceId(machineModel.getMqttConfigurationModel().getDeviceId());
			mqttConfigurationView.setMessageType(machineModel.getMqttConfigurationModel().getMessageType());
			mqttConfigurationView.setCreateDate(machineModel.getMqttConfigurationModel().getCreateDate());
			machineModelView.setMqttConfigurationView(mqttConfigurationView);
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineModelView);
	}

	private void setAcceptedMaterials(MachineModel machineModel, MachineView machineView) throws EndlosAPIException {
			Set<Integer> existMaterialEnums = new HashSet<>();
			Set<Integer> toDeleteMaterialEnums = new HashSet<>();
			Set<Integer> toAddMaterialEnums = new HashSet<>();
			MaterialEnum tempMaterialEnum;

			for (KeyValueView KeyValueView : machineView.getAcceptedMaterials()) {
//				machineView.setMachineType(MachineView.setMachineType(machineModel.getMachineType().getId()));
//
//				public static KeyValueView setMachineType(Integer machineType) {
//					MachineTypeEnum machineTypeEnum = MachineTypeEnum.fromId(machineType);
//					return KeyValueView.create(machineTypeEnum.getId(), machineTypeEnum.getName());
//				}
				MaterialEnum materialEnum = MaterialEnum.fromId(KeyValueView.getKey().intValue());
				tempMaterialEnum = materialEnum;
				if (materialEnum == null) {
					throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
							ResponseCode.INVALID_REQUEST.getMessage());
				}
				if (!machineModel.getAcceptedMaterials().contains(tempMaterialEnum.getId())) {
					toAddMaterialEnums.add(tempMaterialEnum.getId());
				} else {
					existMaterialEnums.add(tempMaterialEnum.getId());
				}
			}
			for (Integer materialEnum : machineModel.getAcceptedMaterials()) {
				if (!existMaterialEnums.contains(materialEnum)) {
					toDeleteMaterialEnums.add(materialEnum);
				}
			}
			for (Integer materialEnum : toDeleteMaterialEnums) {
				machineModel.removeAcceptedMaterials(materialEnum);
			}
			for (Integer materialEnum : toAddMaterialEnums) {
				machineModel.addAcceptedMaterials(materialEnum);
			}
	}
//
//	private Map<String, Object> toMachine(MachineModel machineModel) {
//		Map<String, Object> data = new HashMap<>();
//		data.put("pkid", machineModel.getId());
//		data.put("txtmachineid", machineModel.getMachineId());
//		data.put("txtuniquetoken", machineModel.getUniqueToken());
//		data.put("datecreate", machineModel.getCreateDate());
//		data.put("dateupdate", machineModel.getUpdateDate());
//		return data;
//	}

	private void setMachineCapacity(MachineModel machineModel) {
		MachineCapacityModel machineCapacityModel = new MachineCapacityModel();
		machineCapacityModel.setMachineModel(machineModel);
		machineCapacityModel.setPlasticCapacity(1000l);
		machineCapacityModel.setGlassCapacity(1000l);
		machineCapacityModel.setAluminiumnCapacity(1000l);
		machineCapacityModel.setPrintCapacity(1000l);
		machineCapacityModel.setMaxAutoCleaning(1000l);
		machineCapacityModel.setMaxTransaction(1000l);
		machineCapacityModel.setCreateDate(DateUtility.getCurrentEpoch());
		machineCapacityService.create(machineCapacityModel);
		machineModel.setMachineCapacityModel(machineCapacityModel);
		machineService.update(machineModel);
	}

	private void setPlcConfiguration(MachineModel machineModel) {
		PLCConfigurationModel plcConfigurationModel = new PLCConfigurationModel();
		plcConfigurationModel.setMachineModel(machineModel);
		plcConfigurationModel.setBarcodeScannerResponseWait(60l);
		plcConfigurationModel.setObjectDetectFirstResponseWait(60l);
		plcConfigurationModel.setPlcAddressGlassShredderCurrent(60l);
		plcConfigurationModel.setPlcAddressAluminiumShredderCurrent(60l);
		plcConfigurationModel.setPlcAddressBcFrequency(60l);
		plcConfigurationModel.setPlcAddressCDoorFrequency(60l);
		plcConfigurationModel.setPlcAddressFcFrequency(60l);
		plcConfigurationModel.setPlcAddressPlasticShredderCurrent(60l);
		plcConfigurationModel.setPlcAddressSlidingConveyorFrequency(60l);
		plcConfigurationModel.setPlcIpAddress("192.168.1.200");
		plcConfigurationModel.setCreateDate(DateUtility.getCurrentEpoch());
		plcConfigurationService.create(plcConfigurationModel);
		machineModel.setPlcConfigurationModel(plcConfigurationModel);
		machineService.update(machineModel);
	}

	private void createDeviceInClearBladeAndSetMqttDetails(MachineModel machineModel) {
		String privateKeyPath = null;
		String derFilePath = null;
		String publicKeyPath = null;

		String activeProfile = System.getProperty("spring.profiles.active");
		String url = null;
		String userToken = null;
//		if (activeProfile == null) {
//			activeProfile = "test";
//		}
		LoggerService.info(activeProfile, url, userToken);
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		if (activeProfile.equals("test")) {
			url = "https://asia-east1.clearblade.com/api/v/4/webhook/execute/aad7d0d40cd0bffbe782c6fe9dbe01/cloudiot_devices";
			userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOi0xLCJpYXQiOjE2OTkzNTQwNzcsInNpZCI6IjI2ZDFkMGU4LTY0ODItNGUwZS05MzEyLTE3MWE1NDQ5YmZjYSIsInR0IjoxLCJ1aWQiOiJiYWQ3ZDBkNDBjZGE5YWRhYzk5MGZmYjFiNzAyIiwidXQiOjJ9.MFG1zngt4HIvCU8Rb0Z8UxLiIBaDkbl3GE3Ej_v8yq4";
		} else if (activeProfile.equals("prod")) {
			url = "https://asia-east1.clearblade.com/api/v/4/webhook/execute/8c97e9d90cbe81e281dbf2dfc52f/cloudiot_devices";
			userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOi0xLCJpYXQiOjE3MDQ3OTc2NDksInNpZCI6IjhjZDBhNTgzLTZkNWEtNDhlZC1hZmQ1LTMxMzlmYjg5YjU4OSIsInR0IjoxLCJ1aWQiOiJhMjk3ZTlkOTBjYzQ4NGU4ZjZlNWMxYWZkYTYxIiwidXQiOjJ9.FP-5UH6saEKL50_ZBOwahNqKcedgXqYV15K5qI1cN6w";
		} else {
			url = "https://asia-east1.clearblade.com/api/v/4/webhook/execute/dc879bcc0cda948cff9dd49cbf64/cloudiot_devices";
			userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOi0xLCJpYXQiOjE2OTA1MjYxOTcsInNpZCI6IjkxYjRkYzI4LTQ3ZGYtNDkwZS05ZWJhLWVhMzE1Y2U0MGQwOCIsInR0IjoxLCJ1aWQiOiJlYTg3OWJjYzBjYWNkZWQwODljYjkzZjlhNTNkIiwidXQiOjJ9.IvOQYmC4sJUPtBNFS-Xnph7w60x7fJeFwqoagWbDsaw";
		}
		LoggerService.info(activeProfile, url, userToken);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", machineModel.getMachineId());
		JSONArray jsonArray = new JSONArray();
		JSONObject publicKey = new JSONObject();
		JSONObject key = new JSONObject();

		try {
			// Generate an RSA key pair
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048); // Key size in bits
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			// Get the private and public keys from the key pair
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey secerteKey = keyPair.getPublic();

			StringWriter publicWriter = new StringWriter();
			try (PemWriter pemWriter = new PemWriter(publicWriter)) {
				PemObject pemObject = new PemObject("PUBLIC KEY", secerteKey.getEncoded());
				pemWriter.writeObject(pemObject);
			} catch (IOException e) {
				e.printStackTrace();
				LoggerService.exception(e);
			}

//			StringWriter privateWriter publicWriter.toString()= new StringWriter();
//			try (PemWriter pemWriter = new PemWriter(privateWriter)) {
//				PemObject pemObject = new PemObject("PRIVATE KEY", privateKey.getEncoded());
//				pemWriter.writeObject(pemObject);
//			} catch (IOException e) {
//				e.printStackTrace();
//				LoggerService.exception(e);
//			}

			String privateKeyPEM = convertPrivateKeyToPEM(privateKey);
			byte[] privateKeyBytes = privateKeyPEM.getBytes();

			String publicKeyPEM = convertPublicKeyToPEM(secerteKey);
			byte[] publicKeyBytes = publicKeyPEM.getBytes();

			PrivateKeyInfo pkcs8PrivateKeyInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
			byte[] pkcs8PrivateKeyBytes = pkcs8PrivateKeyInfo.getEncoded();

			Storage storage = StorageOptions.getDefaultInstance().getService();
			String machineId = machineModel.getMachineId().toLowerCase();

			String publicFileName = machineId + "_" + "rsa_public.pem";
			String privateFileName = machineId + "_" + "rsa_private.pem";
			String derFileName = machineId + "_" + "rsa_private.der";

			publicKeyPath = machineId + File.separator + "PUBLIC_FILE" + File.separator + publicFileName;
			privateKeyPath = machineId + File.separator + "PRIVATE_FILE" + File.separator + privateFileName;
			derFilePath = machineId + File.separator + "DER_FILE" + File.separator + derFileName;

			Bucket bucket = storage.get(machineId + "-" + activeProfile);
			if (bucket == null) {
//				bucket = storage.create(BucketInfo.of(machineId + "-" + activeProfile));
			}
			bucket.create(publicKeyPath, new ByteArrayInputStream(publicKeyBytes));
			bucket.create(privateKeyPath, new ByteArrayInputStream(privateKeyBytes));
			bucket.create(derFilePath, new ByteArrayInputStream(pkcs8PrivateKeyBytes));

			key.put("key", publicWriter.toString());

		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			LoggerService.exception(e);
		}

//				"-----BEGIN CERTIFICATE-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApfm8ystgWayOQbzubKjsM186L5gG2jkkyyiv7IiiQ5COqqM4QiIUwi2DQMcsd9NmHlksgAWSHGoswT6hUGYc2WjRcqqAfhMwKvFBWP0uUs6UxXFEZjbQ5pN+FYdTKSGYBBc1lUSTEgPc2r6YbjbwRXpHOJdi9PfxDXXBRW9K3f59v9y6TgxxCSM0V8ptn7ckkUor8wQW3MOI5cyaZj6Lomms4Pdz/UuGQnaCwYY2+n8OH8cDd6n//G2hCGF7JvxKnkmg9QzienTxiwlfJGQunF6Ezc9rg2AAOkmfglLy2zwMx2dh6XEYmiHVfNbWqnDE6AtTvruYZBrZnMuCeeK+ZQIDAQAB\n-----END CERTIFICATE-----\n");
		key.put("format", "RSA_PEM");
		publicKey.put("publicKey", key);
		jsonArray.add(publicKey);
		jsonObject.put("credentials", jsonArray);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);

			// Set headers
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("ClearBlade-UserToken", userToken);

			// Set payload
			StringEntity entity = new StringEntity(jsonObject.toJSONString());
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);

			// Get the response code
			int responseCode = response.getStatusLine().getStatusCode();

			// Read the response
			HttpEntity responseEntity = response.getEntity();
			String responseBody = EntityUtils.toString(responseEntity);
			LoggerService.info("responseBody", "responseBody", responseBody);

		} catch (Exception e) {
			e.printStackTrace();
			LoggerService.exception(e);
		}
		MQTTConfigurationModel mqttConfigurationModel = new MQTTConfigurationModel();
		mqttConfigurationModel.setMqttBridgeHostName("asia-east1-mqtt.clearblade.com");
		mqttConfigurationModel.setMqttBridgePort("8883");
		mqttConfigurationModel.setProjectId("asofta-" + activeProfile);
		mqttConfigurationModel.setCloudRegion("asia-east1");
		mqttConfigurationModel.setRegistryId("asofta-" + activeProfile + "-registry");
		mqttConfigurationModel.setGatewayId("");
		mqttConfigurationModel.setPublicKeyFilePath(publicKeyPath);
		mqttConfigurationModel.setPrivateKeyFilePath(privateKeyPath);
		mqttConfigurationModel.setDerKeyFilePath(derFilePath);
		mqttConfigurationModel.setAlgorithm("RS256");
		mqttConfigurationModel.setDeviceId(machineModel.getMachineId());
		mqttConfigurationModel.setMessageType("event");
		mqttConfigurationModel.setCreateDate(DateUtility.getCurrentEpoch());
		mqttConfigurationModel.setMachineModel(machineModel);
		mqttConfigurationService.create(mqttConfigurationModel);
		machineModel.setMqttConfigurationModel(mqttConfigurationModel);
		machineService.update(machineModel);
	}

	private String convertPrivateKeyToPEM(PrivateKey privateKey) throws IOException {
		StringWriter stringWriter = new StringWriter();
		try (JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
			pemWriter.writeObject(privateKey);
		}
		return stringWriter.toString();
	}

	private String convertPublicKeyToPEM(PublicKey privateKey) throws IOException {
		StringWriter stringWriter = new StringWriter();
		try (JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
			pemWriter.writeObject(privateKey);
		}
		return stringWriter.toString();
	}

//	public static void main(String[] args) {
//
//		String imagePath = null;
//		String activeProfile = System.getProperty("spring.profiles.active");
//		String url = null;
//		String userToken = null;
//		System.out.println(activeProfile);
//		if (activeProfile == null) {
//			activeProfile = "test";
//		}
//		if (activeProfile == "test") {
//			url = "https://asia-east1.clearblade.com/api/v/4/webhook/execute/aad7d0d40cd0bffbe782c6fe9dbe01/cloudiot_devices";
//			userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOi0xLCJpYXQiOjE2OTkzNTQwNzcsInNpZCI6IjI2ZDFkMGU4LTY0ODItNGUwZS05MzEyLTE3MWE1NDQ5YmZjYSIsInR0IjoxLCJ1aWQiOiJiYWQ3ZDBkNDBjZGE5YWRhYzk5MGZmYjFiNzAyIiwidXQiOjJ9.MFG1zngt4HIvCU8Rb0Z8UxLiIBaDkbl3GE3Ej_v8yq4";
//		} else {
//			System.out.println("gew");
//			url = "https://asia-east1.clearblade.com/api/v/4/webhook/execute/dc879bcc0cda948cff9dd49cbf64/cloudiot_devices";
//			userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOi0xLCJpYXQiOjE2OTkzNTQwNzcsInNpZCI6IjI2ZDFkMGU4LTY0ODItNGUwZS05MzEyLTE3MWE1NDQ5YmZjYSIsInR0IjoxLCJ1aWQiOiJiYWQ3ZDBkNDBjZGE5YWRhYzk5MGZmYjFiNzAyIiwidXQiOjJ9.MFG1zngt4HIvCU8Rb0Z8UxLiIBaDkbl3GE3Ej_v8yq4";
//		}
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("id", "test1");
//		JSONArray jsonArray = new JSONArray();
//		JSONObject publicKey = new JSONObject();
//		JSONObject key = new JSONObject();
//
//		try {
//			// Generate an RSA key pair
//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(2048); // Key size in bits
//			KeyPair keyPair = keyPairGenerator.generateKeyPair();
//
//			// Get the private and public keys from the key pair
//			PrivateKey privateKey = keyPair.getPrivate();
//			PublicKey secerteKey = keyPair.getPublic();
//
//			StringWriter publicWriter = new StringWriter();
//			try (PemWriter pemWriter = new PemWriter(publicWriter)) {
//				PemObject pemObject = new PemObject("PUBLIC KEY", secerteKey.getEncoded());
//				pemWriter.writeObject(pemObject);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			PrivateKeyInfo pkcs8PrivateKeyInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
//			byte[] pkcs8PrivateKeyBytes = pkcs8PrivateKeyInfo.getEncoded();
//			key.put("key", publicWriter.toString());
//
//		} catch (NoSuchAlgorithmException | IOException e) {
//			e.printStackTrace();
//		}
//
////				"-----BEGIN CERTIFICATE-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApfm8ystgWayOQbzubKjsM186L5gG2jkkyyiv7IiiQ5COqqM4QiIUwi2DQMcsd9NmHlksgAWSHGoswT6hUGYc2WjRcqqAfhMwKvFBWP0uUs6UxXFEZjbQ5pN+FYdTKSGYBBc1lUSTEgPc2r6YbjbwRXpHOJdi9PfxDXXBRW9K3f59v9y6TgxxCSM0V8ptn7ckkUor8wQW3MOI5cyaZj6Lomms4Pdz/UuGQnaCwYY2+n8OH8cDd6n//G2hCGF7JvxKnkmg9QzienTxiwlfJGQunF6Ezc9rg2AAOkmfglLy2zwMx2dh6XEYmiHVfNbWqnDE6AtTvruYZBrZnMuCeeK+ZQIDAQAB\n-----END CERTIFICATE-----\n");
//		key.put("format", "RSA_PEM");
//		publicKey.put("publicKey", key);
//		jsonArray.add(publicKey);
//		jsonObject.put("credentials", jsonArray);
//
//		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//			HttpPost httpPost = new HttpPost(url);
//
//			// Set headers
//			httpPost.setHeader("Content-Type", "application/json");
//			httpPost.setHeader("ClearBlade-UserToken", userToken);
//
//			// Set payload
//			StringEntity entity = new StringEntity(jsonObject.toJSONString());
//			httpPost.setEntity(entity);
//
//			HttpResponse response = httpClient.execute(httpPost);
//
//			// Get the response code
//			int responseCode = response.getStatusLine().getStatusCode();
////			System.out.println(responseCode);
//			// Read the response
//			HttpEntity responseEntity = response.getEntity();
//			String responseBody = EntityUtils.toString(responseEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	private void setMqttConfiguration(MachineModel machineModel, String keyfile) {
		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		MQTTConfigurationModel mqttConfigurationModel = new MQTTConfigurationModel();
		mqttConfigurationModel.setMqttBridgeHostName("asia-east1-mqtt.clearblade.com");
		mqttConfigurationModel.setMqttBridgePort("8883");
		mqttConfigurationModel.setProjectId("asofta-" + activeProfile);
		mqttConfigurationModel.setCloudRegion("asia-east1");
		mqttConfigurationModel.setRegistryId("asofta-" + activeProfile + "-registry");
		mqttConfigurationModel.setGatewayId("");
		mqttConfigurationModel.setPrivateKeyFilePath(keyfile);
		mqttConfigurationModel.setPrivateKeyFilePath(keyfile);
		mqttConfigurationModel.setPrivateKeyFilePath(keyfile);
		mqttConfigurationModel.setAlgorithm("RS256");
		mqttConfigurationModel.setDeviceId(machineModel.getMachineId());
		mqttConfigurationModel.setMessageType("event");
		mqttConfigurationModel.setCreateDate(DateUtility.getCurrentEpoch());
		mqttConfigurationModel.setMachineModel(machineModel);
		mqttConfigurationService.create(mqttConfigurationModel);
		machineModel.setMqttConfigurationModel(mqttConfigurationModel);
		machineService.update(machineModel);
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		MachineModel machineModel = machineService.get(id);
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineView machineView = fromModel(machineModel);
		if (machineModel.getMqttConfigurationModel() != null) {
			MQTTConfigurationView mqttConfigurationView = new MQTTConfigurationView();
			mqttConfigurationView.setId(machineModel.getMqttConfigurationModel().getId());
			mqttConfigurationView
					.setMqttBridgeHostName(machineModel.getMqttConfigurationModel().getMqttBridgeHostName());
			mqttConfigurationView.setMqttBridgePort(machineModel.getMqttConfigurationModel().getMqttBridgePort());
			mqttConfigurationView.setProjectId(machineModel.getMqttConfigurationModel().getProjectId());
			mqttConfigurationView.setCloudRegion(machineModel.getMqttConfigurationModel().getCloudRegion());
			mqttConfigurationView.setRegistryId(machineModel.getMqttConfigurationModel().getRegistryId());
			mqttConfigurationView.setGatewayId(machineModel.getMqttConfigurationModel().getGatewayId());
			mqttConfigurationView
					.setPrivateKeyFilePath(machineModel.getMqttConfigurationModel().getPrivateKeyFilePath());
			mqttConfigurationView.setPublicKeyFilePath(machineModel.getMqttConfigurationModel().getPublicKeyFilePath());
			mqttConfigurationView.setDerKeyFilePath(machineModel.getMqttConfigurationModel().getDerKeyFilePath());
			mqttConfigurationView.setAlgorithm(machineModel.getMqttConfigurationModel().getAlgorithm());
			mqttConfigurationView.setDeviceId(machineModel.getMqttConfigurationModel().getDeviceId());
			mqttConfigurationView.setMessageType(machineModel.getMqttConfigurationModel().getMessageType());
			mqttConfigurationView.setCreateDate(machineModel.getMqttConfigurationModel().getCreateDate());
			machineView.setMqttConfigurationView(mqttConfigurationView);
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(MachineView machineView) throws EndlosAPIException {
		MachineModel machineModel = machineService.get(machineView.getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!machineModel.getMachineDevelopmentStatus().equals(MachineDevelopmentStatusEnum.MANUFACTURING)) {
			throw new EndlosAPIException(ResponseCode.NOT_ALLOWED_TO_CHANGE_MACHINE_ID.getCode(),
					ResponseCode.NOT_ALLOWED_TO_CHANGE_MACHINE_ID.getMessage());
		}
		machineModel = toModel(machineModel, machineView);
		if (machineView.getAcceptedMaterials() != null) {
			setAcceptedMaterials(machineModel,machineView);
		}
		machineService.update(machineModel);

//		Map<String, Object> data = toMachine(machineModel);
//		firestoreService.doUpdateMachine(data, machineModel.getMachineId());

		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(MachineView machineView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = machineService.search(machineView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MachineModel>) pageModel.getList()));
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	protected void checkInactive(MachineModel machineModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		MachineModel machineModel = machineService.get(id);
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (machineModel.isActive()) {
			machineModel.setActive(false);
			machineModel.setActivationChangeBy(Auditor.getAuditor());
			machineModel.setActivationDate(Instant.now().getEpochSecond());
		} else {
			machineModel.setActive(true);
			machineModel.setActivationChangeBy(Auditor.getAuditor());
			machineModel.setActivationDate(Instant.now().getEpochSecond());
		}
		machineService.update(machineModel);
		if (machineModel.isActive()) {
			return CommonResponse.create(ResponseCode.ACTIVATED_USER_SUCCESSFULLY.getCode(),
					ResponseCode.ACTIVATED_USER_SUCCESSFULLY.getMessage());
		} else {
			return CommonResponse.create(ResponseCode.DEACTIVATED_USER_SUCCESSFULLY.getCode(),
					ResponseCode.DEACTIVATED_USER_SUCCESSFULLY.getMessage());
		}
	}

	@Override
	public MachineModel toModel(MachineModel machineModel, MachineView machineView) throws EndlosAPIException {
		String asofta = "AS";
		String machineType = null;
		machineModel.setMachineType(machineView.getMachineType().getKey().intValue());
		MachineModel model = machineService.getLastMachine();
		Integer i;
		if (machineView.getMachineId() == null) {
			i = 1;
			if (model != null) {
				String count = model.getMachineId().substring(model.getMachineId().length() - 4);
				if (count.matches("-?\\d+(\\.\\d+)?")) {
					i = Integer.valueOf(count) + 1;
				}
			}
		} else {
			String newMachineId = "";
			int lastIndex = machineView.getMachineId().lastIndexOf("-");
			if (lastIndex >= 4) {
				newMachineId = machineView.getMachineId().substring(lastIndex + 1);
			}
			i = Integer.parseInt(newMachineId);
		}

		if (machineView.getMachineType().getKey() == MachineTypeEnum.ONE_COMPACTOR.getId()) {
			machineType = "C1";
		} else if (machineView.getMachineType().getKey() == MachineTypeEnum.ONE_SHREDDER.getId()) {
			machineType = "S1";
		} else if (machineView.getMachineType().getKey() == MachineTypeEnum.TWO_SHREDDER.getId()) {
			machineType = "S2";
		} else if (machineView.getMachineType().getKey() == MachineTypeEnum.THREEE_SHREDDER.getId()) {
			machineType = "S3";
		}
		LocalDate currentdate = LocalDate.now();
		String machineId = asofta + machineType + "-" + String.format("%tm", currentdate)
				+ String.format("%ty", currentdate) + "-" + String.format("%04d", i);

		if (machineView.getMachineId() == null) {
			machineModel.setMachineId(machineId);
		} else {
			machineModel.setMachineId(machineId);
		}
		return machineModel;
	}

	@Override
	public MachineView fromModel(MachineModel machineModel) {
		MachineView machineView = new MachineView();
		machineView.setId(machineModel.getId());
		machineView.setMachineId(machineModel.getMachineId());
		TransactionModel transactionModel = transactionService.getLastTransactionByMachineId(machineModel.getId());
		if (transactionModel != null) {
			TransactionView transactionView = new TransactionView();
			transactionView.setDateStart(transactionModel.getDateEnd());
			machineView.setTransactionView(transactionView);
		}
		if (machineModel.getCustomerModel() != null) {
			CustomerView customerView = new CustomerView();
			customerView.setId(machineModel.getCustomerModel().getId());
			customerView.setName(machineModel.getCustomerModel().getName());
			if (machineModel.getCustomerModel().getLogo() != null) {
				FileView fileView = new FileView(machineModel.getCustomerModel().getLogo().getFileId(),
						machineModel.getCustomerModel().getLogo().getName(),
						machineModel.getCustomerModel().getLogo().isPublicfile(),
						machineModel.getCustomerModel().getLogo().getCompressName(),
						machineModel.getCustomerModel().getLogo().getPath());
				customerView.setLogo(fileView);
			}
			machineView.setCustomerView(customerView);
		}
		if (machineModel.getLocationModel() != null) {
			LocationView locationView = new LocationView();
			locationView.setId(machineModel.getLocationModel().getId());
			locationView.setName(machineModel.getLocationModel().getName());
			locationView.setAddress(machineModel.getLocationModel().getAddress());
			locationView.setArea(machineModel.getLocationModel().getArea());
			locationView.setBranchNumber(machineModel.getLocationModel().getBranchNumber());
			if (machineModel.getLocationModel().getStateModel() != null) {
				locationView.setStateView(KeyValueView.create(machineModel.getLocationModel().getStateModel().getId(),
						machineModel.getLocationModel().getStateModel().getName()));
			}
			if (machineModel.getLocationModel().getCustomerModel() != null) {
				CustomerView customerView = new CustomerView();
				customerView.setId(machineModel.getLocationModel().getCustomerModel().getId());
				customerView.setName(machineModel.getLocationModel().getCustomerModel().getName());
				locationView.setCustomerView(customerView);
			}
			if (machineModel.getLocationModel().getCityModel() != null) {
				locationView.setCityView(KeyValueView.create(machineModel.getLocationModel().getCityModel().getId(),
						machineModel.getLocationModel().getCityModel().getName()));
			}
			if (machineModel.getLocationModel().getCountryModel() != null) {
				locationView
						.setCountryView(KeyValueView.create(machineModel.getLocationModel().getCountryModel().getId(),
								machineModel.getLocationModel().getCountryModel().getName()));
			}
			locationView.setPincode(machineModel.getLocationModel().getPincode());
			if (machineModel.getLocationModel().getLatitude() != null) {
				locationView.setLatitude(machineModel.getLocationModel().getLatitude());
			}
			if (machineModel.getLocationModel().getLongitude() != null) {
				locationView.setLongitude(machineModel.getLocationModel().getLongitude());
			}
			if (machineModel.getLocationModel().getAltitude() != null) {
				locationView.setAltitude(machineModel.getLocationModel().getAltitude());
			}
			machineView.setLocationView(locationView);
		}
		machineView.setMachineActivityStatus(
				MachineView.setMachineActivityStatus(machineModel.getMachineActivityStatus().getId()));
		machineView.setMachineDevelopmentStatus(
				MachineView.setMachineDevelopmentStatus(machineModel.getMachineDevelopmentStatus().getId()));
		if (machineModel.getPatBottlePercentage() != null) {
			machineView.setPatBottlePercentage(machineModel.getPatBottlePercentage().toString());
		}
		if (machineModel.getGlassBottlePercentage() != null) {
			machineView.setGlassBottlePercentage(machineModel.getGlassBottlePercentage().toString());
		}
		if (machineModel.getAluBottlePercentage() != null) {
			machineView.setAluBottlePercentage(machineModel.getAluBottlePercentage().toString());
		}
		if (machineModel.getBranchMachineNumber() != null) {
			machineView.setBranchMachineNumber(machineModel.getBranchMachineNumber());
		}
		if (machineModel.getBarcodeTemplateModel() != null) {
			BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
			barcodeStructureTemplateView.setId(machineModel.getBarcodeTemplateModel().getId());
			barcodeStructureTemplateView.setName(machineModel.getBarcodeTemplateModel().getName());
			barcodeStructureTemplateView
					.setTotalLength(machineModel.getBarcodeTemplateModel().getTotalLength().toString());
			machineView.setBarcodeTemplateView(barcodeStructureTemplateView);
			List<BarcodeStructureModel> barcodeStructureModels = barcodeStructureService
					.findByBarcodeTemplateId(machineModel.getBarcodeTemplateModel().getId());
			List<BarcodeStructureView> barcodeStructureViews = new ArrayList<BarcodeStructureView>();
			for (BarcodeStructureModel barcodeStructureModel : barcodeStructureModels) {
				BarcodeStructureView barcodeStructureView = new BarcodeStructureView();
				barcodeStructureView.setId(barcodeStructureModel.getId());
				barcodeStructureView.setFieldName(barcodeStructureModel.getFieldName());
				barcodeStructureView.setBarcodeType(KeyValueView.create(barcodeStructureModel.getBarcodeType().getId(),
						barcodeStructureModel.getBarcodeType().getName()));
				if (barcodeStructureModel.getDynamicValue() != null) {
					barcodeStructureView
							.setDynamicValue(KeyValueView.create(barcodeStructureModel.getDynamicValue().getId(),
									barcodeStructureModel.getDynamicValue().getName()));
				}
				if (barcodeStructureModel.getLength() != null) {
					barcodeStructureView.setLength(barcodeStructureModel.getLength().toString());
				}
				if (barcodeStructureModel.getValue() != null) {
					barcodeStructureView.setValue(barcodeStructureModel.getValue());
				}
				if (barcodeStructureModel.getEndValue() != null) {
					barcodeStructureView.setEndValue(barcodeStructureModel.getEndValue());
				}
				barcodeStructureViews.add(barcodeStructureView);
			}
			machineView.setBarcodeStructureViews(barcodeStructureViews);
		}
		if (machineModel.getMachineBarcodeFileModel() != null) {
			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setId(machineModel.getMachineBarcodeFileModel().getId());
			if (machineModel.getMachineBarcodeFileModel() != null) {
				machineBarcodeFileView
						.setBarcodeFileName(machineModel.getMachineBarcodeFileModel().getBarcodeFileName());
			}
			machineView.setMachineBarcodeFileView(machineBarcodeFileView);
		}
		machineView.setMachineType(MachineView.setMachineType(machineModel.getMachineType().getId()));
		machineView.setCreateBy(machineModel.getCreateBy().getName());
		machineView.setCreateDate(machineModel.getCreateDate().toString());

		MachineLogModel plasticBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
				MaterialEnum.PLASTIC.getId());
		if (plasticBinResetDate != null) {
			machineView.setPlasticBinResetDate(plasticBinResetDate.getHardResetDate());
		}

		MachineLogModel aluBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
				MaterialEnum.ALUMIUMN.getId());
		if (aluBinResetDate != null) {
			machineView.setAluminiumnBinResetDate(aluBinResetDate.getHardResetDate());
		}

		MachineLogModel glassBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
				MaterialEnum.GLASS.getId());
		if (glassBinResetDate != null) {
			machineView.setGlassBinResetDate(glassBinResetDate.getHardResetDate());
		}
		machineView.setPatBottleCount(machineModel.getPatBottleCount());
		machineView.setGlassBottleCount(machineModel.getGlassBottleCount());
		machineView.setAluBottleCount(machineModel.getAluBottleCount());

		MachineCapacityView machineCapacityView = new MachineCapacityView();
		if (machineModel.getMachineCapacityModel() != null) {
			machineCapacityView.setAluminiumnCapacity(machineModel.getMachineCapacityModel().getAluminiumnCapacity());
			machineCapacityView.setPlasticCapacity(machineModel.getMachineCapacityModel().getPlasticCapacity());
			machineCapacityView.setPrintCapacity(machineModel.getMachineCapacityModel().getPrintCapacity());
			machineCapacityView.setGlassCapacity(machineModel.getMachineCapacityModel().getGlassCapacity());
			machineCapacityView.setMaxTransaction(machineModel.getMachineCapacityModel().getMaxTransaction());
			machineCapacityView.setMaxAutoCleaning(machineModel.getMachineCapacityModel().getMaxAutoCleaning());
			machineView.setMachineCapacityView(machineCapacityView);
		}
		if (machineModel.getPlcConfigurationModel() != null) {
			PLCConfigurationView plcConfigurationView = new PLCConfigurationView();
			plcConfigurationView.setId(machineModel.getPlcConfigurationModel().getId());
			plcConfigurationView.setPlcIpAddress(machineModel.getPlcConfigurationModel().getPlcIpAddress());
			plcConfigurationView
					.setPlcAddressCDoorFrequency(machineModel.getPlcConfigurationModel().getPlcAddressCDoorFrequency());
			plcConfigurationView
					.setPlcAddressFcFrequency(machineModel.getPlcConfigurationModel().getPlcAddressFcFrequency());
			plcConfigurationView.setBarcodeScannerResponseWait(
					machineModel.getPlcConfigurationModel().getBarcodeScannerResponseWait());
			plcConfigurationView.setPlcAddressPlasticShredderCurrent(
					machineModel.getPlcConfigurationModel().getPlcAddressPlasticShredderCurrent());
			plcConfigurationView.setPlcAddressGlassShredderCurrent(
					machineModel.getPlcConfigurationModel().getPlcAddressGlassShredderCurrent());
			plcConfigurationView.setPlcAddressAluminiumShredderCurrent(
					machineModel.getPlcConfigurationModel().getPlcAddressAluminiumShredderCurrent());
			plcConfigurationView
					.setPlcAddressBcFrequency(machineModel.getPlcConfigurationModel().getPlcAddressBcFrequency());
			plcConfigurationView.setObjectDetectFirstResponseWait(
					machineModel.getPlcConfigurationModel().getObjectDetectFirstResponseWait());
			plcConfigurationView.setPlcAddressSlidingConveyorFrequency(
					machineModel.getPlcConfigurationModel().getPlcAddressSlidingConveyorFrequency());
			machineView.setPlcConfigurationView(plcConfigurationView);
		}
		if (machineModel.getPassword() != null) {
			machineView.setPassword(machineModel.getPassword());
		}
		machineView.setActive(machineModel.isActive());

		machineView.setMachineType(MachineView.setMachineType(machineModel.getMachineType().getId()));

		if(machineModel.getAcceptedMaterials()!=null && !machineModel.getAcceptedMaterials().isEmpty()){
			List<KeyValueView> keyValueViews = new ArrayList<>();
			for (Integer i : machineModel.getAcceptedMaterials()) {
				MaterialEnum materialEnum = MaterialEnum.fromId(i);
				keyValueViews.add(KeyValueView.create(materialEnum.getId(), materialEnum.getName()));
			}
			machineView.setAcceptedMaterials(keyValueViews);
		}
		return machineView;
	}

	@Override
	public Response doImportDataTable(MachineView machineView) throws EndlosAPIException {
		FileModel fileModel = fileService.getByFileId(machineView.getFileId());

		String filePath = SystemSettingModel.getDefaultFilePath() + File.separator + "bottle_barcode_sheet"
				+ File.separator + fileModel.getName();

		File uploadedFile = new File(filePath);

		String[] headers = new String[9];
		headers[0] = "ITEM_ID";
		headers[1] = "BARCODE";
		headers[2] = "MATERIAL_TYPE";
		headers[3] = "MATERIAL";
		headers[4] = "ITEM_DESCRIPTION";
		headers[5] = "ITEM_VOLUME";
		headers[6] = "ITEM_WEIGHT";
		headers[7] = "ITEM _VALUE";
		headers[8] = "DATA_ACQUISITION";

		if (!Utility.validateExcelTemplate(uploadedFile, headers)) {
			throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
					ResponseCode.INVALID_FILE_FORMAT.getMessage());
		}

		List<MachineModel> machineModels = new ArrayList<>();
		if (machineView.getIsApplicableForAll().equals(true)) {
			machineModels = machineService.findAll();
		} else {
			for (MachineView machineView1 : machineView.getMachineViews()) {
				MachineModel machineModel = machineService.get(machineView1.getId());
				if (machineModel != null) {
					machineModels.add(machineModel);
				} else {
					throw new EndlosAPIException(ResponseCode.MACHINE_IS_INVALID.getCode(),
							ResponseCode.MACHINE_IS_INVALID.getMessage());
				}
			}
		}

		List<ExcelRow> excelRows = null;
		excelRows = Utility.readExcelFormat(uploadedFile, headers);
		Integer rowCount = 1;
		Integer updationRowCount = 0;
		Firestore db = firestoreConfiguration.getFireStore();

		for (MachineModel machineModel : machineModels) {
			for (ExcelRow excelRow : excelRows) {
				if (StringUtils.isBlank(excelRow.getSixCell()) || excelRow.getFourCell().equals("0")) {
					updationRowCount++;
					continue;
				}
				validateExcelFile(excelRow, rowCount, machineModel, db);
				rowCount++;
			}
		}
		if (updationRowCount.equals(excelRows.size())) {
			return CommonResponse.create(ResponseCode.NO_IMPORT_PERFORM.getCode(),
					ResponseCode.NO_IMPORT_PERFORM.getMessage());
		}
		return CommonResponse.create(ResponseCode.IMPORT_SUCCESSFULLY.getCode(),
				ResponseCode.IMPORT_SUCCESSFULLY.getMessage());
	}

	private void validateExcelFile(ExcelRow excelRow, Integer rowCount, MachineModel machineModel, Firestore db)
			throws EndlosAPIException {
		LoggerService.error("Material at position " + rowCount);
		if (StringUtils.isBlank(excelRow.getSecondCell()) || excelRow.getSecondCell().equals("0")) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"Barcode " + ResponseCode.DATA_IS_MISSING.getMessage() + " on in row " + rowCount);
		}
		MaterialEnum materialEnum = null;
		for (MaterialEnum material : MaterialEnum.MAP.values()) {
			if (material.getName().equalsIgnoreCase(excelRow.getFourCell())) {
				materialEnum = material;
			}
		}
		if (materialEnum != null) {

			DocumentReference docRef = db.collection(machineModel.getMachineId()).document(machineModel.getMachineId());

			ApiFuture<DocumentSnapshot> future = docRef.get();
			try {
				DocumentSnapshot document = future.get();
				if (!document.exists()) {
					throw new EndlosAPIException(ResponseCode.DOES_NOT_EXIST.getCode(),
							"Machine" + ResponseCode.DOES_NOT_EXIST.getMessage());
				}
			} catch (InterruptedException | ExecutionException e) {
				LoggerService.info("", "", "");
			}
			DocumentReference docRef1 = docRef.collection("BARCODE").document(excelRow.getSecondCell());

			Map<String, Object> data = new HashMap<>();
			data.put("txtbarcode", excelRow.getSecondCell());
			data.put("enummaterial", materialEnum.getId());
			data.put("txtdescription", excelRow.getFiveCell());
			if (excelRow.getSixCell() != null && !StringUtils.isBlank(excelRow.getSixCell())) {
				data.put("txtvolumn", new BigDecimal(excelRow.getSixCell()));
			}
			if (materialEnum.equals(MaterialEnum.GLASS)) {
				data.put("txtitemweight", new BigDecimal(500));
			} else if (materialEnum.equals(MaterialEnum.PLASTIC)) {
				data.put("txtitemweight", new BigDecimal(40));
			} else if (materialEnum.equals(MaterialEnum.ALUMIUMN)) {
				data.put("txtitemweight", new BigDecimal(20));
			}

			if (excelRow.getEightCell() != null && !StringUtils.isBlank(excelRow.getEightCell())) {
				data.put("txtitemredeemvalue", new BigDecimal(excelRow.getEightCell()));
			}
			data.put("txtdataacquisition", excelRow.getNineCell());
			data.put("datecreate", DateUtility.getCurrentEpoch());
			data.put("dateupdate", 0);
			docRef1.set(data);

			// DataTableModel dataTableModel = new DataTableModel();
			// dataTableModel.setBarcode(excelRow.getFirstCell());
			// dataTableModel.setMaterial(materialEnum.getId());
			// dataTableModel.setDescription(excelRow.getThirdCell());
			// if (excelRow.getFourCell() != null &&
			// !StringUtils.isBlank(excelRow.getFourCell())) {
			// dataTableModel.setVolumn(new BigDecimal(excelRow.getFourCell()));
			// }
			// if (excelRow.getFiveCell() != null &&
			// !StringUtils.isBlank(excelRow.getFiveCell())) {
			// dataTableModel.setWeight(new BigDecimal(excelRow.getFiveCell()));
			// }
			// if (excelRow.getSixCell() != null &&
			// !StringUtils.isBlank(excelRow.getSixCell())) {
			// dataTableModel.setRedeemvalue(new BigDecimal(excelRow.getSixCell()));
			// }
			// dataTableModel.setDataacquisition(excelRow.getSevenCell());
			// dataTableService.create(dataTableModel);
		} else {
			LoggerService.error("Material at position " + rowCount);
		}
	}

	@Override
	public Response doValidateBarcode(String barcode) throws EndlosAPIException {
		DataTableModel dataTableModel = dataTableService.getByBarcode(barcode);
		if (dataTableModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		DataTableView dataTableView = new DataTableView();
		dataTableView.setId(dataTableModel.getId());
		dataTableView.setBarcode(dataTableModel.getBarcode());
		dataTableView.setMaterial(
				KeyValueView.create(dataTableModel.getMaterial().getId(), dataTableModel.getMaterial().getName()));
		dataTableView.setDescription(dataTableModel.getDescription());
		dataTableView.setVolumn(dataTableModel.getVolumn());
		dataTableView.setWeight(dataTableModel.getWeight());
		dataTableView.setRedeemvalue(dataTableModel.getRedeemvalue());
		dataTableView.setDataacquisition(dataTableModel.getDataacquisition());
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				dataTableView);
	}

	@Override
	public Response doTransactionStart() throws EndlosAPIException {
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setTransactionId(Utility.generateUuid());
		transactionModel.setDateStart(DateUtility.getCurrentEpoch());
		transactionModel.setMachineModel(MachineAuditor.getMachineAuditor());
		transactionService.create(transactionModel);

		TransactionView transactionView = fromModel(transactionModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				transactionView);
	}

	@Override
	public Response doTransactionSave(TransactionView transactionView) throws EndlosAPIException {
		TransactionModel transactionModel = transactionService.getByTransactionId(transactionView.getTransactionId());
		if (transactionModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (!StringUtils.isEmpty(transactionView.getBarcode()) && transactionView.getMaterial() != null
				&& transactionView.getMaterial().getKey() != null) {
			DataTableModel dataTableModel = dataTableService.getByBarcode(transactionView.getBarcode());
			if (dataTableModel != null
					&& dataTableModel.getMaterial().getId() != transactionView.getMaterial().getKey().intValue()) {
				throw new EndlosAPIException(ResponseCode.MATERIAL_MISMATCHED.getCode(),
						ResponseCode.MATERIAL_MISMATCHED.getMessage());
			}
		}
		TransactionLogModel transactionLogModel = new TransactionLogModel();
		transactionLogModel.setTransactionModel(transactionModel);
		transactionLogModel.setBarcode(transactionView.getBarcode());
		if (transactionView.getMaterial() != null && transactionView.getMaterial().getKey() != null) {
			transactionLogModel.setMaterial(transactionView.getMaterial().getKey().intValue());
		}
		if (!StringUtils.isEmpty(transactionView.getReason())) {
			transactionLogModel.setReason(transactionView.getReason());
		}
		transactionLogModel.setStatus(StatusEnum.fromId(transactionView.getStatus().getKey().intValue()).getId());
		transactionLogService.create(transactionLogModel);
		transactionModel.setDateUpdate(DateUtility.getCurrentEpoch());
		if (transactionView.getStatus().getValue().equals(StatusEnum.ACCEPTED.getName())) {
			DataTableModel dataTableModels = dataTableService.getByBarcode(transactionView.getBarcode());
			if (dataTableModels.getMaterial().getName().equals(MaterialEnum.GLASS.getName())) {
				transactionModel.setGlassBottleCount(transactionModel.getGlassBottleCount() + 1);
				transactionModel.setGlassBottleValue(
						transactionModel.getGlassBottleValue().add(dataTableModels.getRedeemvalue()));
			}
			if (dataTableModels.getMaterial().getName().equals(MaterialEnum.PLASTIC.getName())) {
				transactionModel.setPatBottleCount(transactionModel.getPatBottleCount() + 1);
				transactionModel
						.setPatBottleValue(transactionModel.getPatBottleValue().add(dataTableModels.getRedeemvalue()));
			}
			if (dataTableModels.getMaterial().getName().equals(MaterialEnum.ALUMIUMN.getName())) {
				transactionModel.setAluBottleCount(transactionModel.getAluBottleCount() + 1);
				transactionModel
						.setAluBottleValue(transactionModel.getAluBottleValue().add(dataTableModels.getRedeemvalue()));
			}
			transactionModel.setTotalValue(transactionModel.getTotalValue().add(dataTableModels.getRedeemvalue()));
		}
		transactionService.update(transactionModel);

		TransactionView responseTransactionView = fromModel(transactionModel);
		responseTransactionView.setStatus(new KeyValueView(StatusEnum.fromId(transactionLogModel.getStatus()).getId(),
				StatusEnum.fromId(transactionLogModel.getStatus()).getName()));
		responseTransactionView.setReason(transactionLogModel.getReason());
		responseTransactionView.setTotalInsertedBottleCount(transactionModel.getGlassBottleCount()
				+ transactionModel.getPatBottleCount() + transactionModel.getAluBottleCount());

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				responseTransactionView);
	}

	@Override
	public Response doTransactionClose(String transactionId) throws EndlosAPIException {
		TransactionModel transactionModel = transactionService.getByTransactionId(transactionId);
		if (transactionModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		transactionModel.setDateEnd(DateUtility.getCurrentEpoch());

		String path = SystemSettingModel.getDefaultFilePath();

		transactionService.update(transactionModel);
		String barcode = Utility.generateOTP(23);

		File barcodeFile = createBarcode(transactionModel, path, barcode);

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat format1 = new SimpleDateFormat("HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		format1.setTimeZone(TimeZone.getDefault());

		if (transactionModel.getTotalValue().compareTo(BigDecimal.ZERO) > 0) {
			Map<String, String> dynamicFields = new HashMap<String, String>();
			dynamicFields.put(TransactionFields.MACHINE_ID.getName(),
					String.valueOf(transactionModel.getMachineModel().getMachineId()));
			dynamicFields.put(TransactionFields.DATE.getName(), String.valueOf(transactionModel.getDateEnd()));
			dynamicFields.put(TransactionFields.PAT_BOTTLE_COUNT.getName(),
					String.valueOf(transactionModel.getPatBottleCount()));
			dynamicFields.put(TransactionFields.PAT_BOTTLE_VALUE.getName(),
					String.valueOf(transactionModel.getPatBottleValue()));
			dynamicFields.put(TransactionFields.ALU_BOTTLE_COUNT.getName(),
					String.valueOf(transactionModel.getAluBottleCount()));
			dynamicFields.put(TransactionFields.ALU_BOTTLE_VALUE.getName(),
					String.valueOf(transactionModel.getAluBottleValue()));
			dynamicFields.put(TransactionFields.GLASS_BOTTLE_COUNT.getName(),
					String.valueOf(transactionModel.getGlassBottleCount()));
			dynamicFields.put(TransactionFields.GLASS_BOTTLE_VALUE.getName(),
					String.valueOf(transactionModel.getGlassBottleValue()));
			dynamicFields.put(TransactionFields.TOTAL_VALUE.getName(),
					String.valueOf(transactionModel.getTotalValue()));
			dynamicFields.put(TransactionFields.DATE.getName(),
					String.valueOf(format.format(transactionModel.getDateUpdate() * 1000L)));
			dynamicFields.put(TransactionFields.TIME.getName(),
					String.valueOf(format1.format(transactionModel.getDateUpdate() * 1000L)));
			dynamicFields.put(TransactionFields.PATH.getName(), String.valueOf(barcodeFile));
			dynamicFields.put(TransactionFields.BARCODE.getName(), String.valueOf(barcode));

			File file = new File(WebUtil.getCurrentRequest().getServletContext().getRealPath(File.separator)
					+ File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "/html/receipt.html");
			StringBuilder contentBuilder = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				String str;
				while ((str = in.readLine()) != null) {
					contentBuilder.append(str);
				}
				in.close();
			} catch (IOException e) {
				LoggerService.exception(e);
			}
			String content = contentBuilder.toString();
			StrSubstitutor sub = new StrSubstitutor(dynamicFields);
			String pdfContent = sub.replace(content);

			String finalPDFFilePath = path + File.separator + transactionId + ".pdf";
			try {
				HtmlConverter.convertToPdf(pdfContent, new FileOutputStream(finalPDFFilePath));
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
			FileModel fileModel = new FileModel();
			fileModel.setFileId(Utility.generateUuid());
			String newFileName = transactionId + ".pdf";
			fileModel.setName(newFileName);
			fileModel.setOriginalName(newFileName);
			fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE.getId()));
			fileModel.setPublicfile(true);
			fileModel.setUpload(DateUtility.getCurrentEpoch());
			fileModel.setCompressName(newFileName);
			fileModel.setPath(finalPDFFilePath);
			fileModel.setCompressPath(finalPDFFilePath);
			fileService.create(fileModel);
			String filePath = SystemSettingModel.getDefaultFilePath();
			String fileExtension = fileModel.getName().substring(fileModel.getName().lastIndexOf(".") + 1,
					fileModel.getName().length());

			if (DataSheetFileExtensionEnum.pdf.getName().equals(fileExtension)) {
				httpServletResponse.setContentType("application/pdf");
			}
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=\"" + fileModel.getName() + "\"");
			httpServletResponse.setHeader("Cache-control", "max-age=31536000");
			FileUtility.download(filePath, fileModel.getName(), httpServletResponse);
		}
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	private File createBarcode(TransactionModel transactionModel, String path, String barcode) {

		transactionModel.setBarcode(barcode);
		String filePath = path + File.separator + barcode + ".png";
		File barcodeFile = new File(filePath);

		Barcode barcode3;
		try {
			barcode3 = BarcodeFactory.createCode128(transactionModel.getBarcode());
			barcode3.setResolution(300);
			BarcodeImageHandler.savePNG(barcode3, barcodeFile);
		} catch (BarcodeException e1) {
			e1.printStackTrace();
		} catch (OutputException e) {
			e.printStackTrace();
		}

		FileModel fileModel = new FileModel();
		fileModel.setFileId(Utility.generateUuid());
		fileModel.setName(barcode);
		fileModel.setOriginalName(barcode);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE.getId()));
		fileModel.setPublicfile(true);
		fileModel.setCompressName(barcode);
		fileModel.setPath(filePath);
		fileModel.setCompressPath(filePath);
		fileService.create(fileModel);

		transactionModel.setBarcodeFileModel(fileModel);
		transactionService.update(transactionModel);

		return barcodeFile;
	}

	@Override
	public Response doGetData(String transactionId) throws EndlosAPIException {
		TransactionModel transactionModel = transactionService.getByTransactionId(transactionId);
		if (transactionModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		TransactionView transactionView = fromModel(transactionModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				transactionView);
	}

	public TransactionView fromModel(TransactionModel transactionModel) {
		TransactionView transactionView = new TransactionView();
		transactionView.setTransactionId(transactionModel.getTransactionId());
		transactionView.setPatBottleCount(transactionModel.getPatBottleCount());
		transactionView.setGlassBottleCount(transactionModel.getGlassBottleCount());
		transactionView.setAluBottleCount(transactionModel.getAluBottleCount());
		transactionView.setAluBottleValue(transactionModel.getAluBottleValue());
		transactionView.setGlassBottleValue(transactionModel.getGlassBottleValue());
		transactionView.setPatBottleValue(transactionModel.getPatBottleValue());
		transactionView.setTotalValue(transactionModel.getTotalValue());
		transactionView.setDateStart(transactionModel.getDateStart());
		transactionView.setDateUpdate(transactionModel.getDateUpdate());
		transactionView.setDateEnd(transactionModel.getDateEnd());

		MachineView machineView = new MachineView();
		machineView.setId(transactionModel.getMachineModel().getId());
		machineView.setMachineId(transactionModel.getMachineModel().getMachineId());
		transactionView.setMachineView(machineView);

		return transactionView;
	}

	@Override
	public Response doAssignMachine(MachineView machineview) throws EndlosAPIException {
		CustomerModel customerModel = customerService.getLight(machineview.getCustomerView().getId());
		if (customerModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		LocationModel locationModel = locationService.get(machineview.getLocationView().getId());
		if (locationModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineModel machineModel = machineService.get(machineview.getId());
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		if (machineview.getBranchMachineNumber() != null && !machineview.getBranchMachineNumber().trim().isEmpty()) {
			machineModel.setBranchMachineNumber(machineview.getBranchMachineNumber());
		} else {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		machineModel.setCustomerModel(customerModel);
		machineModel.setLocationModel(locationModel);
		machineService.update(machineModel);

		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doValidateMachineId(String machineId) throws EndlosAPIException {
		MachineModel machineModel = machineService.getByMachineId(machineId);
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		if (!machineModel.getMachineDevelopmentStatus().equals(MachineDevelopmentStatusEnum.MANUFACTURING)) {
			throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(),
					ResponseCode.INVALID_REQUEST.getMessage());
		}
		machineModel.setMachineDevelopmentStatus(MachineDevelopmentStatusEnum.ALLOCATED.getId());
		machineService.update(machineModel);
//		JwtTokenModel jwtTokenModel = JwtTokenModel.createMachineToken();
//		jwtTokenModel.setUniqueToken(machineModel.getUniqueToken());

		MachineView machineView = new MachineView();
		machineView.setId(machineModel.getId());
		machineView.setMachineId(machineModel.getMachineId());
		machineView.setMachineDevelopmentStatus(
				MachineView.setMachineDevelopmentStatus(machineModel.getMachineDevelopmentStatus().getId()));
		machineView.setCustomerName(machineModel.getCustomerModel().getName());
		machineView.setBranchName(machineModel.getLocationModel().getName());
		machineView.setBranchNumber(machineModel.getLocationModel().getBranchNumber());
		machineView.setBranchMachineNumber(machineModel.getBranchMachineNumber());
		if (machineModel.getBarcodeTemplateModel() != null) {
			BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
			barcodeStructureTemplateView.setId(machineModel.getBarcodeTemplateModel().getId());
			barcodeStructureTemplateView.setName(machineModel.getBarcodeTemplateModel().getName());
			barcodeStructureTemplateView
					.setTotalLength(machineModel.getBarcodeTemplateModel().getTotalLength().toString());
			machineView.setBarcodeTemplateView(barcodeStructureTemplateView);
			List<BarcodeStructureModel> barcodeStructureModels = barcodeStructureService
					.findByBarcodeTemplateId(machineModel.getBarcodeTemplateModel().getId());
			List<BarcodeStructureView> barcodeStructureViews = new ArrayList<BarcodeStructureView>();
			for (BarcodeStructureModel barcodeStructureModel : barcodeStructureModels) {
				BarcodeStructureView barcodeStructureView = new BarcodeStructureView();
				barcodeStructureView.setId(barcodeStructureModel.getId());
				barcodeStructureView.setFieldName(barcodeStructureModel.getFieldName());
				barcodeStructureView.setBarcodeType(KeyValueView.create(barcodeStructureModel.getBarcodeType().getId(),
						barcodeStructureModel.getBarcodeType().getName()));
				if (barcodeStructureModel.getDynamicValue() != null) {
					barcodeStructureView
							.setDynamicValue(KeyValueView.create(barcodeStructureModel.getDynamicValue().getId(),
									barcodeStructureModel.getDynamicValue().getName()));
				}
				if (barcodeStructureModel.getLength() != null) {
					barcodeStructureView.setLength(barcodeStructureModel.getLength().toString());
				}
				if (barcodeStructureModel.getValue() != null) {
					barcodeStructureView.setValue(barcodeStructureModel.getValue());
				}
				if (barcodeStructureModel.getEndValue() != null) {
					barcodeStructureView.setEndValue(barcodeStructureModel.getEndValue());
				}
				barcodeStructureViews.add(barcodeStructureView);
			}
			machineView.setBarcodeStructureViews(barcodeStructureViews);
		}
//		machineView.setUniqueToken(machineModel.getUniqueToken());
//		machineView.setAccessToken(JwtUtil.generateAccessToken(machineModel.getMachineId(),
//				JsonUtil.toJson(jwtTokenModel), jwtTokenModel));
//		machineView.setRefreshToken(
//				JwtUtil.generateRefreshToken(machineModel.getMachineId(), JsonUtil.toJson(jwtTokenModel)));
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineView);
	}

	@Override
	public Response doDropdown() {
		List<MachineModel> machineModels = machineService.dropDown();
		if (machineModels == null || machineModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineView> machineViews = new ArrayList<>();
		for (MachineModel machineModel : machineModels) {
			MachineView machineView = new MachineView();
			machineView.setId(machineModel.getId());
			machineView.setMachineId(machineModel.getMachineId());
			machineViews.add(machineView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineViews.size(), machineViews);
	}

//	@Override
//	public byte[] doBarcode() throws EndlosAPIException {
//		byte[] fileContent = null;
//		try {
//			fileContent = FileUtils.readFileToByteArray(new File(path));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return fileContent;
//	}

	@Override
	public Response doGetByMachineId(String machineID) throws EndlosAPIException {
		MachineModel machineModel = machineService.getByMachineId(machineID);
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineView machineView = fromModel(machineModel);
		if (machineModel.getMachineBarcodeFileModel() != null) {
			String activeProfile = System.getProperty("spring.profiles.active");
			Storage storage = StorageOptions.getDefaultInstance().getService();

			BlobInfo csvFile = BlobInfo.newBuilder("barcode" + "-" + activeProfile,
					machineModel.getMachineBarcodeFileModel().getBarcodeFileName()).build();
			if (csvFile != null) {
				long duration = 1; // 1 hour
				TimeUnit timeUnit = TimeUnit.HOURS;
				URL signedUrl = storage.signUrl(csvFile, duration, timeUnit, Storage.SignUrlOption.withV4Signature());
				if (signedUrl != null) {
					machineView.setBarcodeFilePath(signedUrl.toString());
				}
			}
		}
		if (machineModel.getMqttConfigurationModel() != null) {
			MQTTConfigurationView mqttConfigurationView = new MQTTConfigurationView();
			mqttConfigurationView.setId(machineModel.getMqttConfigurationModel().getId());
			mqttConfigurationView
					.setMqttBridgeHostName(machineModel.getMqttConfigurationModel().getMqttBridgeHostName());
			mqttConfigurationView.setMqttBridgePort(machineModel.getMqttConfigurationModel().getMqttBridgePort());
			mqttConfigurationView.setProjectId(machineModel.getMqttConfigurationModel().getProjectId());
			mqttConfigurationView.setCloudRegion(machineModel.getMqttConfigurationModel().getCloudRegion());
			mqttConfigurationView.setRegistryId(machineModel.getMqttConfigurationModel().getRegistryId());
			mqttConfigurationView.setGatewayId(machineModel.getMqttConfigurationModel().getGatewayId());
			Storage storage = StorageOptions.getDefaultInstance().getService();

			// Specify your blob and duration for the signed URL.
			String activeProfile = System.getProperty("spring.profiles.active");
			if (activeProfile == null) {
				activeProfile = "dev";
			}
			BlobInfo privateKey = BlobInfo.newBuilder(machineModel.getMachineId().toLowerCase() + "-" + activeProfile,
					machineModel.getMqttConfigurationModel().getPrivateKeyFilePath()).build();
			if (machineModel.getMqttConfigurationModel().getPublicKeyFilePath() != null) {
				BlobInfo publicKey = BlobInfo
						.newBuilder(machineModel.getMachineId().toLowerCase() + "-" + activeProfile,
								machineModel.getMqttConfigurationModel().getPublicKeyFilePath())
						.build();
				if (publicKey != null) {
					long duration = 1; // 1 hour
					TimeUnit timeUnit = TimeUnit.HOURS;
					URL signedUrl = storage.signUrl(publicKey, duration, timeUnit,
							Storage.SignUrlOption.withV4Signature());
					if (signedUrl != null) {
						mqttConfigurationView.setPublicKeyFilePath(signedUrl.toString());
					}
				}
			}
			if (machineModel.getMqttConfigurationModel().getDerKeyFilePath() != null) {
				BlobInfo derKey = BlobInfo.newBuilder(machineModel.getMachineId().toLowerCase() + "-" + activeProfile,
						machineModel.getMqttConfigurationModel().getDerKeyFilePath()).build();
				if (derKey != null) {
					long duration = 1; // 1 hour
					TimeUnit timeUnit = TimeUnit.HOURS;
					URL signedUrl = storage.signUrl(derKey, duration, timeUnit,
							Storage.SignUrlOption.withV4Signature());
					if (signedUrl != null) {
						mqttConfigurationView.setDerKeyFilePath(signedUrl.toString());
					}
				}
			}
			if (privateKey != null) {
				long duration = 1; // 1 hour
				TimeUnit timeUnit = TimeUnit.HOURS;
				URL signedUrl = storage.signUrl(privateKey, duration, timeUnit,
						Storage.SignUrlOption.withV4Signature());
				if (signedUrl != null) {
					mqttConfigurationView.setPrivateKeyFilePath(signedUrl.toString());
				}

			}

			mqttConfigurationView.setAlgorithm(machineModel.getMqttConfigurationModel().getAlgorithm());
			mqttConfigurationView.setDeviceId(machineModel.getMqttConfigurationModel().getDeviceId());
			mqttConfigurationView.setMessageType(machineModel.getMqttConfigurationModel().getMessageType());
			mqttConfigurationView.setCreateDate(machineModel.getMqttConfigurationModel().getCreateDate());
			machineView.setMqttConfigurationView(mqttConfigurationView);
		}

		if (machineModel.getPassword() != null) {
			machineView.setPassword(machineModel.getPassword());
		}
		machineView.setAluminiumnPrice("0.3");
		machineView.setGlassPrice("0.3");
		machineView.setPlasticPrice("0.3");
		machineView.setSetupPassword("9999");
		machineView.setTechnicianPassword("8888");

//		if (machineModel.getMachineBarcodeFileModel() != null) {
//
//			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
//			machineBarcodeFileView.setId(machineModel.getMachineBarcodeFileModel().getId());
//
//			List<MachineBarcodeItemModel> machineBarcodeItemModels = machineBarcodeItemService
//					.getByMachineBarcodeFileViewId(machineModel.getMachineBarcodeFileModel().getId());
//
//			if (machineBarcodeItemModels != null) {
//				List<MachineBarcodeItemView> machineBarcodeItemViews = new ArrayList<>();
//
//				for (MachineBarcodeItemModel machineBarcodeItemModel : machineBarcodeItemModels) {
//					MachineBarcodeItemView machineBarcodeItemView = new MachineBarcodeItemView();
//
//					machineBarcodeItemView.setBarcodeName(machineBarcodeItemModel.getBarcodeName());
//					machineBarcodeItemView.setMaterialType(machineBarcodeItemModel.getMaterialType());
//					machineBarcodeItemView.setItemVolume(machineBarcodeItemModel.getItemVolume());
//					machineBarcodeItemView.setItemWeight(machineBarcodeItemModel.getItemWeight());
//					machineBarcodeItemView.setItemValue(machineBarcodeItemModel.getItemValue());
//
//					machineBarcodeItemViews.add(machineBarcodeItemView);
//				}
//				machineBarcodeFileView.setMachineBarcodeItemViews(machineBarcodeItemViews);
//			}
//			machineView.setMachineBarcodeFileView(machineBarcodeFileView);
//		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineView);
	}

	@Override
	public Response doExport(MachineView machineView, Integer orderType, Integer orderParam) throws EndlosAPIException {
		List<MachineModel> machineModels = machineService.doExport(machineView, orderType, orderParam);
		if (machineModels == null || machineModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Machine";
			if (machineView.getExportFileName() != null) {
				if (machineView.getExportFileName().equals("TechnicalStatus")) {
					sheetname = "Technical Status";
				} else if (machineView.getExportFileName().equals("FullnessStatus")) {
					sheetname = "Fullness Status";
				}
			} else {
				sheetname = "Machine";
			}

			int dataStartingFrom = 0;

			Sheet sheet = workbook.createSheet(sheetname);

			if (machineView.getFullTextSearch() != null && !machineView.getFullTextSearch().isEmpty()) {

				Row rowFilterData1 = sheet.createRow((short) dataStartingFrom);
				rowFilterData1.createCell((short) 0).setCellValue("Machine Id");
				rowFilterData1.createCell((short) 1).setCellValue(machineView.getFullTextSearch());
				dataStartingFrom++;

			}
			if (machineView.getCustomerView() != null) {

				Row rowFilterData2 = sheet.createRow((short) dataStartingFrom);
				rowFilterData2.createCell((short) 0).setCellValue("Customer Name");
				rowFilterData2.createCell((short) 1).setCellValue(machineModels.get(0).getCustomerModel().getName());
				dataStartingFrom++;

			}
			if (machineView.getCustomerView() != null && machineView.getLocationView() != null) {

				Row rowFilterData3 = sheet.createRow((short) dataStartingFrom);
				rowFilterData3.createCell((short) 0).setCellValue("Branch Name");
				rowFilterData3.createCell((short) 1).setCellValue(machineModels.get(0).getLocationModel().getName());
				dataStartingFrom++;
			}

			if (dataStartingFrom > 0) {
				dataStartingFrom++;
			}
			Row rowhead = sheet.createRow((short) dataStartingFrom);

			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Machine Id");
			rowhead.createCell((short) 2).setCellValue("Machine Type");
			if (machineView.getExportFileName() != null) {
				if (machineView.getExportFileName().equals("TechnicalStatus")) {
					// rowhead.createCell((short) 3).setCellValue("Technical Status");
					rowhead.createCell((short) 3).setCellValue("City");
					rowhead.createCell((short) 4).setCellValue("Branch Name");
					rowhead.createCell((short) 5).setCellValue("Branch Number");
				} else if (machineView.getExportFileName().equals("FullnessStatus")) {
					// rowhead.createCell((short) 3).setCellValue("Fullness Status");
					rowhead.createCell((short) 3).setCellValue("Plastic");
					rowhead.createCell((short) 4).setCellValue("Plastic Last Reset Date");
					rowhead.createCell((short) 5).setCellValue("Glass");
					rowhead.createCell((short) 6).setCellValue("Glass Last Reset Date");
					rowhead.createCell((short) 7).setCellValue("Aluminium");
					rowhead.createCell((short) 8).setCellValue("Aluminium Last Reset Date");
					// rowhead.createCell((short) 6).setCellValue("Last Reset Date");
					rowhead.createCell((short) 9).setCellValue("City");
					rowhead.createCell((short) 10).setCellValue("Branch Name");
					rowhead.createCell((short) 11).setCellValue("Branch Number");
				}
			} else {
				// rowhead.createCell((short) 3).setCellValue("Technical Status");
				// rowhead.createCell((short) 4).setCellValue("Physical Status");
				rowhead.createCell((short) 3).setCellValue("Customer Name");
				rowhead.createCell((short) 4).setCellValue("City");
				rowhead.createCell((short) 5).setCellValue("Branch Name");
				rowhead.createCell((short) 6).setCellValue("Branch Number");
				rowhead.createCell((short) 7).setCellValue("Branchwise Machine Number");
				rowhead.createCell((short) 8).setCellValue("Barcode Template Name");
			}

			int i = dataStartingFrom;
			int j = 0;
			for (MachineModel machineModel : machineModels) {
				i++;
				j++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) j);
				if (machineModel.getMachineId() != null) {
					row.createCell((short) 1).setCellValue(machineModel.getMachineId());
				} else {
					row.createCell((short) 1).setCellValue("");
				}
				if (machineModel.getMachineType() != null) {
					row.createCell((short) 2).setCellValue(machineModel.getMachineType().getName());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (machineView.getExportFileName() != null) {
					if (machineView.getExportFileName().equals("TechnicalStatus")) {
						/*
						 * if (machineModel.getMachineActivityStatus() != null) { row.createCell((short)
						 * 3).setCellValue(machineModel.getMachineActivityStatus().getName()); } else {
						 * row.createCell((short) 3).setCellValue(""); }
						 */
						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 3)
									.setCellValue(machineModel.getLocationModel().getCityModel().getName());
						} else {
							row.createCell((short) 3).setCellValue("");
						}
						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 4).setCellValue(machineModel.getLocationModel().getName());
						} else {
							row.createCell((short) 4).setCellValue("");
						}
						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 5).setCellValue(machineModel.getLocationModel().getBranchNumber());
						} else {
							row.createCell((short) 5).setCellValue("");
						}
					} else if (machineView.getExportFileName().equals("FullnessStatus")) {

						if (machineModel.getPatBottlePercentage() != null) {
							row.createCell((short) 3)
									.setCellValue(machineModel.getPatBottlePercentage().toString() + "%");
						} else {
							row.createCell((short) 3).setCellValue("");
						}
						MachineLogModel plasticBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
								MaterialEnum.PLASTIC.getId());
						if (plasticBinResetDate != null) {
							row.createCell((short) 4)
									.setCellValue(format.format(plasticBinResetDate.getHardResetDate() * 1000L));
						} else {
							row.createCell((short) 4).setCellValue("");
						}

						if (machineModel.getGlassBottlePercentage() != null) {
							row.createCell((short) 5)
									.setCellValue(machineModel.getGlassBottlePercentage().toString() + "%");
						} else {
							row.createCell((short) 5).setCellValue("");
						}
						MachineLogModel glassBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
								MaterialEnum.GLASS.getId());
						if (glassBinResetDate != null && glassBinResetDate.getHardResetDate() != 0) {
							row.createCell((short) 6)
									.setCellValue(format.format(glassBinResetDate.getHardResetDate() * 1000L));
						} else {
							row.createCell((short) 6).setCellValue("");
						}

						if (machineModel.getAluBottlePercentage() != null) {
							row.createCell((short) 7)
									.setCellValue(machineModel.getAluBottlePercentage().toString() + "%");
						} else {
							row.createCell((short) 7).setCellValue("");
						}

						MachineLogModel aluBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
								MaterialEnum.ALUMIUMN.getId());
						if (aluBinResetDate != null) {
							row.createCell((short) 8)
									.setCellValue(format.format(aluBinResetDate.getHardResetDate() * 1000L));
						} else {
							row.createCell((short) 8).setCellValue("");
						}

						/*
						 * if(machineModel.getPatBottlePercentage() != null &&
						 * machineModel.getGlassBottlePercentage() != null) { row.createCell((short) 3)
						 * .setCellValue("Plastic:" + machineModel.getPatBottlePercentage().toString() +
						 * "%\n" + "Aluminium:" + machineModel.getAluBottlePercentage().toString() +
						 * "%\n" + "Glass:" + machineModel.getGlassBottlePercentage().toString() + "%");
						 * } else { row.createCell((short) 3).setCellValue(""); }
						 *
						 * MachineLogModel aluBinResetDate =
						 * machineLogService.getLatestResetDate(machineModel.getId(),
						 * MaterialEnum.ALUMIUMN.getId()); String aluResetDate = "-"; String
						 * glassResetDate = "-"; String patResetDate = "-";
						 *
						 * if (aluBinResetDate != null) { aluResetDate =
						 * String.valueOf(format.format(aluBinResetDate.getResetDate() * 1000L)); }
						 * MachineLogModel glassBinResetDate =
						 * machineLogService.getLatestResetDate(machineModel.getId(),
						 * MaterialEnum.GLASS.getId()); if (glassBinResetDate != null) { glassResetDate
						 * = String.valueOf(format.format(glassBinResetDate.getResetDate() * 1000L)); }
						 * MachineLogModel plasticBinResetDate =
						 * machineLogService.getLatestResetDate(machineModel.getId(),
						 * MaterialEnum.PLASTIC.getId()); if (plasticBinResetDate != null) {
						 * patResetDate =
						 * String.valueOf(format.format(plasticBinResetDate.getResetDate() * 1000L)); }
						 * row.createCell((short) 9) .setCellValue(patResetDate + "\n" + glassResetDate
						 * + "\n" + aluResetDate);
						 */

						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 9)
									.setCellValue(machineModel.getLocationModel().getCityModel().getName());
						} else {
							row.createCell((short) 9).setCellValue("");
						}
						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 10).setCellValue(machineModel.getLocationModel().getName());
						} else {
							row.createCell((short) 10).setCellValue("");
						}
						if (machineModel.getLocationModel() != null) {
							row.createCell((short) 11).setCellValue(machineModel.getLocationModel().getBranchNumber());
						} else {
							row.createCell((short) 11).setCellValue("");
						}
					}
				} else {
					/*
					 * if (machineModel.getMachineActivityStatus() != null) { row.createCell((short)
					 * 3).setCellValue(machineModel.getMachineActivityStatus().getName()); } else {
					 * row.createCell((short) 3).setCellValue(""); }
					 */
					/*
					 * if (machineModel.getMachineDevelopmentStatus() != null) {
					 * row.createCell((short)
					 * 4).setCellValue(machineModel.getMachineDevelopmentStatus().getName()); } else
					 * { row.createCell((short) 4).setCellValue(""); }
					 */
					if (machineModel.getCustomerModel() != null) {
						row.createCell((short) 3).setCellValue(machineModel.getCustomerModel().getName());
					} else {
						row.createCell((short) 3).setCellValue("");
					}
					if (machineModel.getLocationModel() != null) {
						row.createCell((short) 4)
								.setCellValue(machineModel.getLocationModel().getCityModel().getName());
					} else {
						row.createCell((short) 4).setCellValue("");
					}
					if (machineModel.getLocationModel() != null) {
						row.createCell((short) 5).setCellValue(machineModel.getLocationModel().getName());
					} else {
						row.createCell((short) 5).setCellValue("");
					}
					if (machineModel.getLocationModel() != null) {
						row.createCell((short) 6).setCellValue(machineModel.getLocationModel().getBranchNumber());
					} else {
						row.createCell((short) 6).setCellValue("");
					}
					if (machineModel.getBranchMachineNumber() != null) {
						row.createCell((short) 7).setCellValue(machineModel.getBranchMachineNumber());
					} else {
						row.createCell((short) 7).setCellValue("");
					}
					if (machineModel.getBarcodeTemplateModel() != null) {
						row.createCell((short) 8).setCellValue(machineModel.getBarcodeTemplateModel().getName());
					} else {
						row.createCell((short) 8).setCellValue("");
					}
				}
			}
//			String path = SettingTemplateModel.getFilePath(Auditor.getAuditor().getRequestedClientModel().getId())
//					+ File.separator + ModuleEnum.EMPLOYEE.getName() + File.separator + "Excel" + File.separator;

			String path = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator;

			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			filepath = file.getPath() + File.separator + newFileName;
			FileOutputStream fileOut = new FileOutputStream(filepath);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			LoggerService.exception(ex);
			throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
					ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
		}
		FileModel fileModel = new FileModel();
		fileModel.setFileId(Utility.generateUuid());
		fileModel.setName(newFileName);
		fileModel.setModule(Long.valueOf(ModuleEnum.MACHINE.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		// return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export
		// successfully", fileOperation.fromModel(fileModel));
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Machine export completed successfully.",
				fileOperation.fromModel(fileModel));

	}

	@Override
	public Response doDropDownByLocation(MachineView machineView) {

		List<MachineModel> machineModels = null;

		if (machineView.getCustomerView() != null) {
			machineModels = machineService.getByCustomer(machineView.getCustomerView().getId());
		}
		if (machineView.getLocationView() != null) {
			machineModels = machineService.getByLocation(machineView.getLocationView().getId());
		}
		if (machineModels == null || machineModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineView> machineViews = new ArrayList<>();
		for (MachineModel machineModel : machineModels) {
			MachineView resView = fromModel(machineModel);
			machineViews.add(resView);
		}

		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineViews.size(), machineViews);
	}

	@Override
	public Response getLastBranchwiseMachineNo(MachineView machineView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(),
				machineService.machineLastNumberByLocation(machineView));
	}

	@Override
	public Response usedMachineNumberByLocation(MachineView machineView) throws EndlosAPIException {

		List<MachineModel> machineModels = null;

		if (machineView.getCustomerView() != null || machineView.getMachineViews() != null) {
			machineModels = machineService.usedMachineNumberByLocation(machineView);
		}

		if (machineModels == null || machineModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		List<MachineView> machineViews = new ArrayList<>();
		for (MachineModel machineModel : machineModels) {
			MachineView resView = fromModel(machineModel);
			machineViews.add(resView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineViews.size(), machineViews);

	}

	@Override
	public Response machineListForAssignBarcodeTemplate(MachineView machineView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {

		PageModel pageModel = machineService.machineListForAssignBarcodeTemplate(machineView, start, recordSize,
				orderType, orderParam);

		List<MachineView> viewList = new ArrayList<>(pageModel.getList().size());
		for (MachineModel machineModel : (List<MachineModel>) pageModel.getList()) {

			MachineView machineViewNew = new MachineView();

			machineViewNew.setId(machineModel.getId());
			machineViewNew.setMachineId(machineModel.getMachineId());

			if (machineModel.getCustomerModel() != null) {
				CustomerView customerView = new CustomerView();
				customerView.setId(machineModel.getCustomerModel().getId());
				customerView.setName(machineModel.getCustomerModel().getName());
				machineViewNew.setCustomerView(customerView);
			}

			if (machineModel.getLocationModel() != null) {

				LocationView locationView = new LocationView();

				locationView.setId(machineModel.getLocationModel().getId());
				locationView.setName(machineModel.getLocationModel().getName());

				if (machineModel.getLocationModel().getCityModel() != null) {
					locationView.setCityView(KeyValueView.create(machineModel.getLocationModel().getCityModel().getId(),
							machineModel.getLocationModel().getCityModel().getName()));
				}
				machineViewNew.setLocationView(locationView);
			}
			if (machineModel.getBranchMachineNumber() != null) {
				machineViewNew.setBranchMachineNumber(machineModel.getBranchMachineNumber());
			}

			if (machineModel.getBarcodeTemplateModel() != null) {
				BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
				barcodeStructureTemplateView.setId(machineModel.getBarcodeTemplateModel().getId());
				barcodeStructureTemplateView.setName(machineModel.getBarcodeTemplateModel().getName());
				machineViewNew.setBarcodeTemplateView(barcodeStructureTemplateView);
			}
			viewList.add(machineViewNew);
		}

		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), viewList);
	}

	@Override
	public Response doViewAssignBarcodeTemplate(Long id) throws EndlosAPIException {
		MachineModel machineModel = machineService.getByMachineIdFromSmallModel(id);

		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineView machineView = new MachineView();

		machineView.setId(machineModel.getId());
		machineView.setMachineId(machineModel.getMachineId());

		if (machineModel.getCustomerModel() != null) {
			CustomerView customerView = new CustomerView();
			customerView.setId(machineModel.getCustomerModel().getId());
			customerView.setName(machineModel.getCustomerModel().getName());
			machineView.setCustomerView(customerView);
		}

		if (machineModel.getLocationModel() != null) {

			LocationView locationView = new LocationView();

			locationView.setId(machineModel.getLocationModel().getId());
			locationView.setName(machineModel.getLocationModel().getName());

			if (machineModel.getLocationModel().getCityModel() != null) {
				locationView.setCityView(KeyValueView.create(machineModel.getLocationModel().getCityModel().getId(),
						machineModel.getLocationModel().getCityModel().getName()));
			}
			machineView.setLocationView(locationView);
		}
		if (machineModel.getBranchMachineNumber() != null) {
			machineView.setBranchMachineNumber(machineModel.getBranchMachineNumber());
		}

		if (machineModel.getBarcodeTemplateModel() != null) {
			BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
			barcodeStructureTemplateView.setId(machineModel.getBarcodeTemplateModel().getId());
			barcodeStructureTemplateView.setName(machineModel.getBarcodeTemplateModel().getName());
			machineView.setBarcodeTemplateView(barcodeStructureTemplateView);
		}
		if (machineModel.getMachineBarcodeFileModel() != null) {
			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setId(machineModel.getMachineBarcodeFileModel().getId());
			if (machineModel.getMachineBarcodeFileModel() != null) {
				machineBarcodeFileView
						.setBarcodeFileName(machineModel.getMachineBarcodeFileModel().getBarcodeFileName());
			}
			machineView.setMachineBarcodeFileView(machineBarcodeFileView);
		}
		if (machineModel.getPassword() != null) {
			machineView.setPassword(machineModel.getPassword());
		}
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineView);
	}

	@Override
	public Response machineList(MachineView machineView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = machineService.machineList(machineView, start, recordSize, orderType, orderParam);

		List<MachineView> viewList = new ArrayList<>(pageModel.getList().size());
		for (MachineModel machineModel : (List<MachineModel>) pageModel.getList()) {
			viewList.add(fromLightModel(machineModel));
		}
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), viewList);
	}

	public MachineView fromLightModel(MachineModel machineModel) {
		MachineView machineView = new MachineView();

		machineView.setId(machineModel.getId());
		machineView.setMachineId(machineModel.getMachineId());

		if (machineModel.getCustomerModel() != null) {
			CustomerView customerView = new CustomerView();
			customerView.setId(machineModel.getCustomerModel().getId());
			customerView.setName(machineModel.getCustomerModel().getName());
			machineView.setCustomerView(customerView);
		}

		if (machineModel.getLocationModel() != null) {

			LocationView locationView = new LocationView();

			locationView.setId(machineModel.getLocationModel().getId());
			locationView.setName(machineModel.getLocationModel().getName());
			locationView.setAddress(machineModel.getLocationModel().getAddress());
			locationView.setArea(machineModel.getLocationModel().getArea());

			if (machineModel.getLocationModel().getStateModel() != null) {
				locationView.setStateView(KeyValueView.create(machineModel.getLocationModel().getStateModel().getId(),
						machineModel.getLocationModel().getStateModel().getName()));
			}
			if (machineModel.getLocationModel().getCityModel() != null) {
				locationView.setCityView(KeyValueView.create(machineModel.getLocationModel().getCityModel().getId(),
						machineModel.getLocationModel().getCityModel().getName()));
			}
			if (machineModel.getLocationModel().getCountryModel() != null) {
				locationView
						.setCountryView(KeyValueView.create(machineModel.getLocationModel().getCountryModel().getId(),
								machineModel.getLocationModel().getCountryModel().getName()));
			}
			locationView.setPincode(machineModel.getLocationModel().getPincode());
			locationView.setBranchNumber(machineModel.getLocationModel().getBranchNumber());

			machineView.setLocationView(locationView);
		}
		if (machineModel.getPatBottlePercentage() != null) {
			machineView.setPatBottlePercentage(machineModel.getPatBottlePercentage().toString());
		}
		if (machineModel.getGlassBottlePercentage() != null) {
			machineView.setGlassBottlePercentage(machineModel.getGlassBottlePercentage().toString());
		}
		if (machineModel.getAluBottlePercentage() != null) {
			machineView.setAluBottlePercentage(machineModel.getAluBottlePercentage().toString());
		}
		if (machineModel.getBranchMachineNumber() != null) {
			machineView.setBranchMachineNumber(machineModel.getBranchMachineNumber());
		}
		if (machineModel.getMachineBarcodeFileModel() != null) {
			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();

			if (machineModel.getMachineBarcodeFileModel() != null) {
				machineBarcodeFileView.setId(machineModel.getMachineBarcodeFileModel().getId());
				machineBarcodeFileView
						.setBarcodeFileName(machineModel.getMachineBarcodeFileModel().getBarcodeFileName());
			}
			machineView.setMachineBarcodeFileView(machineBarcodeFileView);
		}

		if (machineModel.getBarcodeTemplateModel() != null) {
			BarcodeTemplateView barcodeStructureTemplateView = new BarcodeTemplateView();
			barcodeStructureTemplateView.setId(machineModel.getBarcodeTemplateModel().getId());
			barcodeStructureTemplateView.setName(machineModel.getBarcodeTemplateModel().getName());
			machineView.setBarcodeTemplateView(barcodeStructureTemplateView);
		}

		machineView.setMachineType(MachineView.setMachineType(machineModel.getMachineType().getId()));
		machineView.setCreateDate(machineModel.getCreateDate().toString());
		machineView.setActive(machineModel.isActive());
		return machineView;
	}

	@Override
	public Response machineListForDashboard(MachineView machineView, Integer start, Integer recordSize,
			Integer orderType, Integer orderParam) {
		PageModel pageModel = machineService.machineList(machineView, start, recordSize, orderType, orderParam);

		List<MachineView> viewList = new ArrayList<>(pageModel.getList().size());
		for (MachineModel machineModel : (List<MachineModel>) pageModel.getList()) {
			MachineView machineViewNew = fromLightModel(machineModel);

			MachineLogModel plasticBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
					MaterialEnum.PLASTIC.getId());
			if (plasticBinResetDate != null) {
				machineViewNew.setPlasticBinResetDate(plasticBinResetDate.getHardResetDate());
			}

			MachineLogModel aluBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
					MaterialEnum.ALUMIUMN.getId());
			if (aluBinResetDate != null) {
				machineViewNew.setAluminiumnBinResetDate(aluBinResetDate.getHardResetDate());
			}

			MachineLogModel glassBinResetDate = machineLogService.getLatestResetDate(machineModel.getId(),
					MaterialEnum.GLASS.getId());
			if (glassBinResetDate != null) {
				machineViewNew.setGlassBinResetDate(glassBinResetDate.getHardResetDate());
			}
			if(machineModel.getAcceptedMaterials()!=null && !machineModel.getAcceptedMaterials().isEmpty()){
				List<KeyValueView> keyValueViews = new ArrayList<>();
				for (Integer i : machineModel.getAcceptedMaterials()) {
					MaterialEnum materialEnum = MaterialEnum.fromId(i);
					keyValueViews.add(KeyValueView.create(materialEnum.getId(), materialEnum.getName()));
				}
				machineViewNew.setAcceptedMaterials(keyValueViews);
			}
			viewList.add(machineViewNew);

		}

		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), viewList);
	}

	@Override
	@Async
	public CompletableFuture<Response> doExportAsync(MachineView machineView, Integer orderType, Integer orderParam) {
		try {
			Response response = doExport(machineView, orderType, orderParam);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}

	@Override
	public Response getAllMachineState() {

		List<MachineModel> machineModels = machineService.findAllMachineState();

		if (machineModels == null || machineModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		List<MachineViewForLogistic> viewList = new ArrayList<>();
		machineModels.stream().forEach(machineModel -> {

			MachineViewForLogistic machineView = new MachineViewForLogistic();
			machineView.setMachineId(machineModel.getMachineId());
			machineView.setMachineType(MachineView.setMachineType(machineModel.getMachineType().getId()).getValue());

			if (machineModel.getLocationModel() != null) {

				machineView.setBranchName(machineModel.getLocationModel().getName());
				machineView.setBranchNumber(machineModel.getLocationModel().getBranchNumber());

				if (machineModel.getLocationModel().getCityModel() != null) {
					machineView.setCity(machineModel.getLocationModel().getCityModel().getName());
				}
				else {
					machineView.setCity(null);
				}

				if (machineModel.getLocationModel().getPickupEveryday() != null) {
					machineView.setIsPickupTomorrow(machineModel.getLocationModel().getPickupEveryday());
				}
				else {
					machineView.setIsPickupTomorrow(null);
				}
				machineView.setNumberOfGlassTanks(machineModel.getLocationModel().getNumberOfGlassTanks());
			}
			else {
				machineView.setBranchName(null);
				machineView.setBranchNumber(null);
				machineView.setCity(null);
				machineView.setIsPickupTomorrow(null);
				machineView.setNumberOfGlassTanks(null);
			}

			if (machineModel.getPatBottlePercentage() != null) {
				machineView.setPlasticCurrentPercentage(machineModel.getPatBottlePercentage().toString());

				try {
					Double plasticPercentage = Double.valueOf(machineView.getPlasticCurrentPercentage());

					Double plasticPercentageDouble = plasticPercentage / 100.0;

					if (plasticPercentageDouble == 0 || plasticPercentageDouble == 0.0 || plasticPercentageDouble == 0.00) {
						machineView.setPlasticCapacityUnitsWrapper(null);
					} else {
						Long plasticCapacityUnits = (long) (machineModel.getPatBottleCount() / plasticPercentageDouble);
						machineView.setPlasticCapacityUnits(plasticCapacityUnits);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input for Plastic percentage: " + e.getMessage());
				}
			}
			else {
				machineView.setPlasticCurrentPercentage(null);
				machineView.setPlasticCapacityUnitsWrapper(null);
			}

			// Get HardReset Date of All 3 types os material in one Query

			// Glass : 1, Plastic : 2, Aluminium : 3
			Map<Integer, Long> latestHardResetDatesInSingleQuery = machineLogService.getLatestHardResetDatesInSingleQuery(machineModel.getId());

			//MachineLogModel plasticHardResetDate = machineLogService.getLatestResetDate(machineModel.getId(), MaterialEnum.PLASTIC.getId());
			if (latestHardResetDatesInSingleQuery.get(2) != null) {
				machineView.setHardResetDatePlastic(latestHardResetDatesInSingleQuery.get(2));
			}
			else {
				machineView.setHardResetDatePlastic(null);
			}

			if (machineModel.getGlassBottlePercentage() != null) {
				machineView.setGlassCurrentPercentage(machineModel.getGlassBottlePercentage().toString());

				try {
					Double glassPercentage = Double.valueOf(machineView.getGlassCurrentPercentage());

					Double glassPercentageFraction = glassPercentage / 100.0;

					if (glassPercentageFraction == 0 || glassPercentageFraction == 0.0 || glassPercentageFraction == 0.00) {
						machineView.setGlassCapacityUnitsWrapper(null);
					} else {
						Long glassCapacityUnits = (long) (machineModel.getGlassBottleCount() / glassPercentageFraction);
						machineView.setGlassCapacityUnits(glassCapacityUnits);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input for Glass percentage: " + e.getMessage());
				}

			}
			else {
				machineView.setGlassCurrentPercentage(null);
				machineView.setGlassCapacityUnitsWrapper(null);
			}

			//MachineLogModel glassHardResetDate = machineLogService.getLatestResetDate(machineModel.getId(), MaterialEnum.GLASS.getId());
			if (latestHardResetDatesInSingleQuery.get(1) != null) {
				machineView.setHardResetDateGlass(latestHardResetDatesInSingleQuery.get(1));
			}
			else {
				machineView.setHardResetDateGlass(null);
			}

			if (machineModel.getAluBottlePercentage() != null) {
				machineView.setAluminiumnCurrentPercentage(machineModel.getAluBottlePercentage().toString());

				try {
					Double aluPercentage = Double.valueOf(machineView.getAluminiumnCurrentPercentage());

					Double aluPercentageFraction = aluPercentage / 100.0;

					if (aluPercentageFraction == 0 || aluPercentageFraction == 0.0 || aluPercentageFraction == 0.00) {
						machineView.setAluminiumCapacityUnitsWrapper(null);
					} else {
						Long aluCapacityUnits = (long) (machineModel.getAluBottleCount() / aluPercentageFraction);
						machineView.setAluminiumCapacityUnits(aluCapacityUnits);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input for Alu percentage: " + e.getMessage());
				}
			}
			else {
				machineView.setAluminiumnCurrentPercentage(null);
				machineView.setAluminiumCapacityUnitsWrapper(null);
			}

			//MachineLogModel aluHardResetDate = machineLogService.getLatestResetDate(machineModel.getId(), MaterialEnum.ALUMIUMN.getId());
			if (latestHardResetDatesInSingleQuery.get(3) != null) {
				machineView.setHardResetDateAluminiumn(latestHardResetDatesInSingleQuery.get(3));
			}
			else {
				machineView.setHardResetDateAluminiumn(null);
			}

			machineView.setPlasticCurrentUnits(machineModel.getPatBottleCount());
			machineView.setGlassCurrentUnits(machineModel.getGlassBottleCount());
			machineView.setAluminiumCurrentUnits(machineModel.getAluBottleCount());

			// Set Last Transaction Pushed Date as Last Status Update
			//TransactionModel transactionModel = transactionService.getLastTransactionByMachineId(machineModel.getId());
			Long lastTransactionDate = transactionService.getLastTransactionByMachineIdForAllMachineState(machineModel.getId());
			if (lastTransactionDate != null) {
				machineView.setLastStatusUpdate(lastTransactionDate);
			}
			else {
				machineView.setLastStatusUpdate(null);
			}

			// get Soft Reset Date
			// Glass : 1, Plastic : 2, Aluminium : 3
			Map<Integer, Long> latestSoftResetDatesInSingleQuery = machineLogService.getLatestSoftResetDatesInSingleQuery(machineModel.getId());

			//MachineLogModel plasticSoftResetDate = machineLogService.getLatestSoftResetDate(machineModel.getId(), MaterialEnum.PLASTIC.getId());
			if (latestSoftResetDatesInSingleQuery.get(2) != null) {
				machineView.setSoftResetDatePlastic(latestSoftResetDatesInSingleQuery.get(2));
			}
			else {
				machineView.setSoftResetDatePlastic(null);
			}

			//MachineLogModel glassSoftResetDate = machineLogService.getLatestSoftResetDate(machineModel.getId(), MaterialEnum.GLASS.getId());
			if (latestSoftResetDatesInSingleQuery.get(1) != null) {
				machineView.setSoftResetDateGlass(latestSoftResetDatesInSingleQuery.get(1));
			}
			else {
				machineView.setSoftResetDateGlass(null);
			}

			//MachineLogModel aluSoftResetDate = machineLogService.getLatestSoftResetDate(machineModel.getId(), MaterialEnum.ALUMIUMN.getId());
			if (latestSoftResetDatesInSingleQuery.get(3) != null) {
				machineView.setSoftResetDateAluminiumn(latestSoftResetDatesInSingleQuery.get(3));
			}
			else {
				machineView.setSoftResetDateAluminiumn(null);
			}
			// End get Soft Reset Date

			/*
			 * if(machineModel.getMachineActivityStatus() !=null) {
			 * machineView.setMachineActivityStatus(MachineView.setMachineActivityStatus(
			 * machineModel.getMachineActivityStatus().getId())); }
			 */

			viewList.add(machineView);

		});
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), viewList.size(), viewList);
	}
}