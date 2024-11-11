/*******************************************************************************
 \ * Copyright -2019 @intentlabs
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

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.logistic.model.DailyPickupLogModel;
import com.intentlabs.endlos.logistic.service.DailyPickupLogService;
import com.intentlabs.endlos.logistic.service.PickupRouteService;
import com.intentlabs.endlos.logistic.view.LogisticCurrentFullnessLogsView;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import com.intentlabs.endlos.machine.model.MachineLogModel;
import com.intentlabs.endlos.machine.service.MachineLogService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.TransactionService;
import com.intentlabs.endlos.machine.view.MachineLogView;
import com.intentlabs.endlos.machine.view.MachineView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@Component(value = "machineLogOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MachineLogOperationImpl extends AbstractOperation<MachineLogModel, MachineLogView>
		implements MachineLogOperation {

	@Autowired
	private MachineLogService machineLogService;

	@Autowired
	FileOperation fileOperation;
	@Autowired
	private FileService fileService;

	@Autowired
	private MachineService machineService;
	@Autowired
	TransactionService transactionService;

	@Autowired
	DailyPickupLogService dailyPickupLogService;

	@Autowired
	private PickupRouteService pickupRouteService;

	@Override
	public BaseService<MachineLogModel> getService() {
		return machineLogService;
	}

	@Override
	protected MachineLogModel loadModel(MachineLogView machineLogView) {
		return machineLogService.get(machineLogView.getId());
	}

	@Override
	protected MachineLogModel getNewModel() {
		return new MachineLogModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(MachineLogView machineLogView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(MachineLogView machineLogView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		MachineLogModel machineLogModel = machineLogService.get(id);
		if (machineLogModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		MachineLogView machineLogView = fromModel(machineLogModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				machineLogView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doSearch(MachineLogView machineLogView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = machineLogService.search(machineLogView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<MachineLogModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public MachineLogModel toModel(MachineLogModel machineLogModel, MachineLogView machineLogView)
			throws EndlosAPIException {
		return machineLogModel;
	}

	@Override
	public MachineLogView fromModel(MachineLogModel machineLogModel) {
		MachineLogView machineLogView = new MachineLogView();
		machineLogView.setId(machineLogModel.getId());

		machineLogView.setResetDate(machineLogModel.getResetDate());
		machineLogView.setCreateDate(machineLogModel.getCreateDate());
		machineLogView.setMaterialType(MachineLogView.setMaterialType(machineLogModel.getMaterialType().getId()));
		machineLogView.setMaterialCount(machineLogModel.getMaterialCount());
		machineLogView.setHardReset(machineLogModel.isHardReset());
		if (machineLogModel.getHardResetDate() != null) {
			machineLogView.setHardResetDate(machineLogModel.getHardResetDate());
		}

		MachineView machineView = new MachineView();
		machineView.setId(machineLogModel.getMachineModel().getId());
		machineView.setMachineId(machineLogModel.getMachineModel().getMachineId());
		machineLogView.setMachineView(machineView);

		if (machineLogModel.getLocationModel() != null) {
			LocationView locationView = new LocationView();
			locationView.setId(machineLogModel.getLocationModel().getId());
			locationView.setName(machineLogModel.getLocationModel().getName());
			locationView.setBranchNumber(machineLogModel.getLocationModel().getBranchNumber());
			CustomerView customerView = new CustomerView();
			customerView.setId(machineLogModel.getLocationModel().getCustomerModel().getId());
			customerView.setName(machineLogModel.getLocationModel().getCustomerModel().getName());
			locationView.setCustomerView(customerView);
			machineLogView.setLocationView(locationView);
			if (machineLogModel.getLocationModel().getPickupRouteModel() != null) {
				PickupRouteView pickupRouteView = new PickupRouteView();
				pickupRouteView.setId(machineLogModel.getLocationModel().getPickupRouteModel().getId());
				pickupRouteView.setName(machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName());
			}
		}
		return machineLogView;
	}

	@Override
	protected void checkInactive(MachineLogModel machineLogModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doExport(MachineLogView machineLogView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<MachineLogModel> machineLogModels = machineLogService.doExport(machineLogView, orderType, orderParam);
		if (machineLogModels == null || machineLogModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
//		MachineModel machineModel = machineService.get(machineLogView.getMachineView().getId());
//		if (machineModel == null) {
//			return CommonResponse.create(ResponseCode.MACHINE_IS_INVALID.getCode(),
//					ResponseCode.MACHINE_IS_INVALID.getMessage());
//		}
		String newFileName = "Machine_Logs" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Bin Fullness Logs";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Reset Date");
			rowhead.createCell((short) 2).setCellValue("Type of Reset");
			rowhead.createCell((short) 3).setCellValue("Material Name");
			rowhead.createCell((short) 4).setCellValue("Material Count");
			rowhead.createCell((short) 5).setCellValue("Customer Name");
			rowhead.createCell((short) 6).setCellValue("Branch Name");

			int i = 0;
			for (MachineLogModel machineLogModel : machineLogModels) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);
				if (machineLogModel.isHardReset()) {

					if (machineLogModel.getHardResetDate() != null || machineLogModel.getHardResetDate() != 0) {
						row.createCell((short) 1).setCellValue(
								String.valueOf(format.format(machineLogModel.getHardResetDate() * 1000L)));
					} else {
						row.createCell((short) 1).setCellValue("");
					}
					row.createCell((short) 2).setCellValue("Hard");
				} else {
					if (machineLogModel.getResetDate() != null || machineLogModel.getResetDate() != 0) {
						row.createCell((short) 1)
								.setCellValue(String.valueOf(format.format(machineLogModel.getResetDate() * 1000L)));
					} else {
						row.createCell((short) 1).setCellValue("");
					}
					row.createCell((short) 2).setCellValue("Soft");
				}

				if (machineLogModel.getMaterialType() != null) {
					row.createCell((short) 3).setCellValue(machineLogModel.getMaterialType().getName());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (machineLogModel.getMaterialCount() != null) {
					row.createCell((short) 4).setCellValue(machineLogModel.getMaterialCount());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (machineLogModel.getLocationModel().getCustomerModel() != null) {
					row.createCell((short) 5)
							.setCellValue(machineLogModel.getLocationModel().getCustomerModel().getName());
				} else {
					row.createCell((short) 5).setCellValue("");
				}
				if (machineLogModel.getLocationModel() != null) {
					row.createCell((short) 6).setCellValue(machineLogModel.getLocationModel().getName());
				} else {
					row.createCell((short) 6).setCellValue("");
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
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	/*
	* Last HardReset based Data, Bin-Resets-Since-Full-Reset count base on last hard reset to till soft reset data, Bottle count based on last hard reset data
	* */
	@Override
	public Response getLogisticCurrentFullnessLogs(MachineView machineView1) throws EndlosAPIException {

		List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService
				.getHighestHardResetDateOfEachMachine(machineView1);

		List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine
				.stream().collect(Collectors.toMap(machineLog -> machineLog.getMachineModel().getId(),
						Function.identity(), (existing, replacement) -> existing))
				.values().stream().collect(Collectors.toList());

		List l = new ArrayList();
		for (MachineLogModel machineLogModel1 : uniqueList) {

			LogisticCurrentFullnessLogsView logisticCurrentFullnessLogsView = new LogisticCurrentFullnessLogsView();
			Integer numberOfPlasticBinResetsSinceFullReset = 0;
			Integer numberOfAluminumBinResetsSinceFullReset = 0;
			Integer numberOfGlassBinResetsSinceFullReset = 0;

			Integer numberOfPlasticBottlesSinceLastFullReset = 0;
			Integer numberOfAluminumBottlesSinceLastFullReset = 0;
			Integer numberOfGlassBottlesSinceLastFullReset = 0;

			logisticCurrentFullnessLogsView.setDate(machineLogModel1.getHardResetDate());

			long epochTimeSeconds = machineLogModel1.getHardResetDate();
			Instant instant = Instant.ofEpochSecond(epochTimeSeconds);
			Instant currentInstant = Instant.now();
			long totalHours = Duration.between(instant, currentInstant).toHours();

			logisticCurrentFullnessLogsView.setHoursFromLastTimeStempTillNow(totalHours);

			if (machineLogModel1.getMachineModel() != null) {
				MachineView machineView = new MachineView();
				machineView.setId(machineLogModel1.getMachineModel().getId());
				machineView.setMachineId(machineLogModel1.getMachineModel().getMachineId());
				logisticCurrentFullnessLogsView.setMachineView(machineView);
			}

			if (machineLogModel1.getLocationModel() != null) {
				LocationView locationView = new LocationView();
				locationView.setId(machineLogModel1.getLocationModel().getId());
				locationView.setName(machineLogModel1.getLocationModel().getName());
				logisticCurrentFullnessLogsView.setLocationView(locationView);

				if (machineLogModel1.getLocationModel().getCustomerModel() != null) {
					CustomerView customerView = new CustomerView();
					customerView.setId(machineLogModel1.getLocationModel().getCustomerModel().getId());
					customerView.setName(machineLogModel1.getLocationModel().getCustomerModel().getName());
					locationView.setCustomerView(customerView);
				}
				if (machineLogModel1.getLocationModel().getPickupRouteModel() != null) {
					PickupRouteView pickupRouteView = new PickupRouteView();
					pickupRouteView.setId(machineLogModel1.getLocationModel().getPickupRouteModel().getId());
					pickupRouteView
							.setName(machineLogModel1.getLocationModel().getPickupRouteModel().getPickupRouteName());
					locationView.setPickupRouteView(KeyValueView.create(
							machineLogModel1.getLocationModel().getPickupRouteModel().getId(),
							machineLogModel1.getLocationModel().getPickupRouteModel().getPickupRouteName().toString()));
				}
			}

			List<MachineLogModel> latestResetDateToTillDateSoftReset = machineLogService
					.getLatestResetDateToTillDateSoftReset(machineLogModel1.getMachineModel().getId());

			Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(
					machineLogModel1.getMachineModel().getId(), machineLogModel1.getHardResetDate(),
					machineLogModel1.getMachineModel().getLocationModel().getId());

			for (MachineLogModel machineLogModel : latestResetDateToTillDateSoftReset) {
				if (machineLogModel.getMaterialType().getId() == 1) {
					numberOfGlassBinResetsSinceFullReset++;
					// numberOfGlassBottlesSinceLastFullReset+=
					// machineLogModel.getMaterialCount().intValue();
				} else if (machineLogModel.getMaterialType().getId() == 2) {

					numberOfPlasticBinResetsSinceFullReset++;
					// numberOfPlasticBottlesSinceLastFullReset +=
					// machineLogModel.getMaterialCount().intValue();
				} else if (machineLogModel.getMaterialType().getId() == 3) {
					numberOfAluminumBinResetsSinceFullReset++;
					// numberOfAluminumBottlesSinceLastFullReset+=
					// machineLogModel.getMaterialCount().intValue();
				}

			}
			/*
			 * logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(
			 * numberOfGlassBottlesSinceLastFullReset);
			 * logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(
			 * numberOfPlasticBottlesSinceLastFullReset);
			 * logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(
			 * numberOfAluminumBottlesSinceLastFullReset);
			 */

			Long patBottleCount = bottleCounts.get("patBottleCount");
			Long aluBottleCount = bottleCounts.get("aluBottleCount");
			Long glassBottleCount = bottleCounts.get("glassBottleCount");

			logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(glassBottleCount.intValue());
			logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(patBottleCount.intValue());
			logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(aluBottleCount.intValue());

			logisticCurrentFullnessLogsView
					.setNumberOfPlasticBinResetsSinceFullReset(numberOfPlasticBinResetsSinceFullReset);
			logisticCurrentFullnessLogsView
					.setNumberOfGlassBinResetsSinceFullReset(numberOfGlassBinResetsSinceFullReset);
			logisticCurrentFullnessLogsView
					.setNumberOfAluminumBinResetsSinceFullReset(numberOfAluminumBinResetsSinceFullReset);
			l.add(logisticCurrentFullnessLogsView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				l.size(), l);
	}

	@Override
	public Response getLogisticPickupLogsPerMachine(MachineView machineView1) {
		List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService.getHighestHardResetDateOfEachMachine(machineView1);

		List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine.stream()
				.collect(Collectors.toMap(
						machineLog -> machineLog.getMachineModel().getId(),
						Function.identity(),
						(existing, replacement) -> existing))
				.values()
				.stream()
				.collect(Collectors.toList());

		List l = new ArrayList();
		for (MachineLogModel machineLogModel : uniqueList) {

			LogisticCurrentFullnessLogsView logisticCurrentFullnessLogsView = new LogisticCurrentFullnessLogsView();

			logisticCurrentFullnessLogsView.setDate(machineLogModel.getHardResetDate());

			long epochTimeSeconds = machineLogModel.getHardResetDate();
			Instant instant = Instant.ofEpochSecond(epochTimeSeconds);
			Instant currentInstant = Instant.now();
			long totalHours = Duration.between(instant, currentInstant).toHours();

			logisticCurrentFullnessLogsView.setHoursFromLastTimeStempTillNow(totalHours);

			if (machineLogModel.getMachineModel() != null) {
				MachineView machineView = new MachineView();
				machineView.setId(machineLogModel.getMachineModel().getId());
				machineView.setMachineId(machineLogModel.getMachineModel().getMachineId());
				logisticCurrentFullnessLogsView.setMachineView(machineView);
			}

			if (machineLogModel.getLocationModel() != null) {
				LocationView locationView = new LocationView();
				locationView.setId(machineLogModel.getLocationModel().getId());
				locationView.setName(machineLogModel.getLocationModel().getName());
				logisticCurrentFullnessLogsView.setLocationView(locationView);

				if (machineLogModel.getLocationModel().getCustomerModel() != null) {
					CustomerView customerView = new CustomerView();
					customerView.setId(machineLogModel.getLocationModel().getCustomerModel().getId());
					customerView.setName(machineLogModel.getLocationModel().getCustomerModel().getName());
					locationView.setCustomerView(customerView);
				}
				if (machineLogModel.getLocationModel().getPickupRouteModel() != null) {
					PickupRouteView pickupRouteView = new PickupRouteView();
					pickupRouteView.setId(machineLogModel.getLocationModel().getPickupRouteModel().getId());
					pickupRouteView.setName(machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName());
					locationView.setPickupRouteView(KeyValueView.create(machineLogModel.getLocationModel().getPickupRouteModel().getId(), machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName().toString()));
				}
			}

			/*Integer numberOfPlasticBottlesSinceLastFullReset = 0;
			Integer numberOfAluminumBottlesSinceLastFullReset = 0;
			Integer numberOfGlassBottlesSinceLastFullReset = 0;

			List<MachineLogModel> latestResetDateToTillDateSoftReset = machineLogService.getLatestResetDateToTillDateSoftReset(machineLogModel.getMachineModel().getId());

			for (MachineLogModel logModel : latestResetDateToTillDateSoftReset) {

				if (machineLogModel.getMaterialType().getId() == 1) {
					numberOfGlassBottlesSinceLastFullReset+= logModel.getMaterialCount().intValue();
					logisticCurrentFullnessLogsView.setTotalWeightOfPickup(machineLogModel.getMaterialCount()*700);
				}
				else if (machineLogModel.getMaterialType().getId() == 2) {
					numberOfPlasticBottlesSinceLastFullReset += logModel.getMaterialCount().intValue();
					logisticCurrentFullnessLogsView.setTotalWeightOfPickup(machineLogModel.getMaterialCount()*25);
				}
				else if (machineLogModel.getMaterialType().getId() == 3) {
					numberOfAluminumBottlesSinceLastFullReset+= logModel.getMaterialCount().intValue();
					logisticCurrentFullnessLogsView.setTotalWeightOfPickup(machineLogModel.getMaterialCount()*10);
				}
			}
			logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(numberOfGlassBottlesSinceLastFullReset);
			logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(numberOfPlasticBottlesSinceLastFullReset);
			logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(numberOfAluminumBottlesSinceLastFullReset);
			logisticCurrentFullnessLogsView.setTotalWeightOfPickup( Long.valueOf(numberOfGlassBottlesSinceLastFullReset*700) + (numberOfPlasticBottlesSinceLastFullReset*25) + (numberOfAluminumBottlesSinceLastFullReset*10));
			*/

			/*Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate());
			Long patBottleCount = bottleCounts.get("patBottleCount");
			Long aluBottleCount = bottleCounts.get("aluBottleCount");
			Long glassBottleCount = bottleCounts.get("glassBottleCount");*/

			Long patBottleCount = 0L;
			Long aluBottleCount = 0L;
			Long glassBottleCount = 0L;

			List<MachineLogModel> result = highestHardResetDateOfEachMachine.stream()
					.filter(obj -> obj.getMachineModel().getId() == machineLogModel.getMachineModel().getId())
					.collect(Collectors.toList());

			for (MachineLogModel logModel : result) {
				if (logModel.getMaterialType().getId() == 1) {
					glassBottleCount+= logModel.getMaterialCount().intValue();
				}
				else if (logModel.getMaterialType().getId() == 2) {
					patBottleCount += logModel.getMaterialCount().intValue();
				}
				else if (logModel.getMaterialType().getId() == 3) {
					aluBottleCount+= logModel.getMaterialCount().intValue();
				}
			}
			logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(patBottleCount.intValue());
			logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(aluBottleCount.intValue());
			logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(glassBottleCount.intValue());
			logisticCurrentFullnessLogsView.setTotalWeightOfPickup( Long.valueOf(glassBottleCount*700) + (patBottleCount*25) + (aluBottleCount*10));

			l.add(logisticCurrentFullnessLogsView);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), l.size(), l);
	}

	@Override
	public Response getLogisticPickupLogsPerRoute(MachineLogView machineLogView) {

		/*List<MachineLogModel> highestHardResetDateOfEachLocation = machineLogService
				.getHighestHardResetDateOfEachRoute(machineLogView.getFullTextSearch());

		List<MachineLogModel> uniqueList = highestHardResetDateOfEachLocation
				.stream().collect(Collectors.toMap(machineLog -> machineLog.getLocationModel().getId(),
						Function.identity(), (existing, replacement) -> existing))
				.values().stream().collect(Collectors.toList());


		List l = new ArrayList();

		//case 1 : HardReset wise Generate plan
		for (MachineLogModel machineLogModel : uniqueList) {

			DailyPickupLogModel dailyPickupLogModel = dailyPickupLogService.getByPickupRoute(machineLogModel.getLocationModel().getPickupRouteModel().getId(),machineLogModel.getHardResetDate(), machineLogModel.getHardResetDate());


			LogisticCurrentFullnessLogsView logisticCurrentFullnessLogsView = new LogisticCurrentFullnessLogsView();
			if (machineLogModel.getLocationModel() != null) {
				LocationView locationView = new LocationView();
				locationView.setId(machineLogModel.getLocationModel().getId());
				locationView.setName(machineLogModel.getLocationModel().getName());
				logisticCurrentFullnessLogsView.setLocationView(locationView);

				if (machineLogModel.getLocationModel().getPickupRouteModel() != null) {
					PickupRouteView pickupRouteView = new PickupRouteView();
					pickupRouteView.setId(machineLogModel.getLocationModel().getPickupRouteModel().getId());
					pickupRouteView
							.setName(machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName());
					locationView.setPickupRouteView(KeyValueView.create(
							machineLogModel.getLocationModel().getPickupRouteModel().getId(),
							machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName().toString()));
				}
			}
			logisticCurrentFullnessLogsView.setDate(machineLogModel.getHardResetDate());


			//Hard Reset With Generate Plan
			if(dailyPickupLogModel != null) {

				// Start GeneratePlanned Data
				logisticCurrentFullnessLogsView.setGeneratePlanDate(dailyPickupLogModel.getGeneratePlanDate());
				logisticCurrentFullnessLogsView.setPlannedNumberOfPatBottle(dailyPickupLogModel.getNumberOfPatBottle());
				logisticCurrentFullnessLogsView.setPlannedNumberOfAluBottle(dailyPickupLogModel.getNumberOfAluBottle());
				logisticCurrentFullnessLogsView.setPlannedNumberOfGlassBottle(dailyPickupLogModel.getNumberOfGlassBottle());
				logisticCurrentFullnessLogsView.setPlannedTotalWeight(dailyPickupLogModel.getTotalWeight());
				logisticCurrentFullnessLogsView.setPlannedTotalNumberOfPickup(dailyPickupLogModel.getTotalNumberOfPickup());
				// End GeneratePlanned Data


				Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(
						machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate(),
						machineLogModel.getMachineModel().getLocationModel().getId());
				Long patBottleCount = bottleCounts.get("patBottleCount");
				Long aluBottleCount = bottleCounts.get("aluBottleCount");
				Long glassBottleCount = bottleCounts.get("glassBottleCount");

				logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(patBottleCount.intValue());
				logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(aluBottleCount.intValue());
				logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(glassBottleCount.intValue());
				//logisticCurrentFullnessLogsView.setTotalWeightOfPickup(Long.valueOf(glassBottleCount * 700) + (patBottleCount * 25) + (aluBottleCount * 10));
				logisticCurrentFullnessLogsView.setTotalWeightOfPickup(Long.valueOf(glassBottleCount ) + (patBottleCount ) + (aluBottleCount ));
				logisticCurrentFullnessLogsView.setTotalNumberOfPickups(highestHardResetDateOfEachLocation.size());

				l.add(logisticCurrentFullnessLogsView);


			} else { //If Hard Reset Not Generate Plan

				logisticCurrentFullnessLogsView.setPlannedNumberOfPatBottle(0L);
				logisticCurrentFullnessLogsView.setPlannedNumberOfAluBottle(0L);
				logisticCurrentFullnessLogsView.setPlannedNumberOfGlassBottle(0L);
				logisticCurrentFullnessLogsView.setPlannedTotalWeight(BigDecimal.valueOf(0));
				logisticCurrentFullnessLogsView.setPlannedTotalNumberOfPickup(0L);


				Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(
						machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate(),
						machineLogModel.getMachineModel().getLocationModel().getId());
				Long patBottleCount = bottleCounts.get("patBottleCount");
				Long aluBottleCount = bottleCounts.get("aluBottleCount");
				Long glassBottleCount = bottleCounts.get("glassBottleCount");

				logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(patBottleCount.intValue());
				logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(aluBottleCount.intValue());
				logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(glassBottleCount.intValue());
				logisticCurrentFullnessLogsView.setTotalWeightOfPickup(Long.valueOf(glassBottleCount * 700) + (patBottleCount * 25) + (aluBottleCount * 10));
				logisticCurrentFullnessLogsView.setTotalNumberOfPickups(highestHardResetDateOfEachLocation.size());

				l.add(logisticCurrentFullnessLogsView);
			}
		}

		//case 2 : Generate plan with harResetCheck
		List<DailyPickupLogModel> allPickupRoute = dailyPickupLogService.getAllPickupRoute();

		for (DailyPickupLogModel dailyPickupLogModel : allPickupRoute) {

			for (MachineLogModel machineLogModel : uniqueList) {

				//Generate Plan and not repeat above data
				if(dailyPickupLogModel.getPickupRouteModel().getId() != machineLogModel.getLocationModel().getPickupRouteModel().getId()){

					LogisticCurrentFullnessLogsView logisticCurrentFullnessLogsView = new LogisticCurrentFullnessLogsView();

						LocationView locationView = new LocationView();
						logisticCurrentFullnessLogsView.setLocationView(locationView);

						if (machineLogModel.getLocationModel().getPickupRouteModel() != null) {
							PickupRouteView pickupRouteView = new PickupRouteView();
							pickupRouteView.setId(dailyPickupLogModel.getPickupRouteModel().getId());
							pickupRouteView.setName(dailyPickupLogModel.getPickupRouteModel().getPickupRouteName());
							locationView.setPickupRouteView(KeyValueView.create(dailyPickupLogModel.getPickupRouteModel().getId(), dailyPickupLogModel.getPickupRouteModel().getPickupRouteName().toString()));
						}

					logisticCurrentFullnessLogsView.setDate(machineLogModel.getHardResetDate());

					// Start GeneratePlanned Data
					logisticCurrentFullnessLogsView.setGeneratePlanDate(dailyPickupLogModel.getGeneratePlanDate());
					logisticCurrentFullnessLogsView.setPlannedNumberOfPatBottle(dailyPickupLogModel.getNumberOfPatBottle());
					logisticCurrentFullnessLogsView.setPlannedNumberOfAluBottle(dailyPickupLogModel.getNumberOfAluBottle());
					logisticCurrentFullnessLogsView.setPlannedNumberOfGlassBottle(dailyPickupLogModel.getNumberOfGlassBottle());
					logisticCurrentFullnessLogsView.setPlannedTotalWeight(dailyPickupLogModel.getTotalWeight());
					logisticCurrentFullnessLogsView.setPlannedTotalNumberOfPickup(dailyPickupLogModel.getTotalNumberOfPickup());
					// End GeneratePlanned Data


					logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(0);
					logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(0);
					logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(0);
					logisticCurrentFullnessLogsView.setTotalWeightOfPickup(0L);
					logisticCurrentFullnessLogsView.setTotalNumberOfPickups(0);

					l.add(logisticCurrentFullnessLogsView);
				}
			}

		}

		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				l.size(), l);*/

		// new logic for Pickup log per route
		List l = new ArrayList();
		MachineView machineView=new MachineView();
		machineView.setFullTextSearch(machineLogView.getFullTextSearch());

		LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		Long endEpoch = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toEpochSecond();

		List<DailyPickupLogModel> allPickupRoute = dailyPickupLogService.getAllPickupRoute(machineLogView.getFullTextSearch(), machineLogView.getStartDate(), machineLogView.getEndDate());

		for (DailyPickupLogModel dailyPickupLogModel : allPickupRoute) {

			LogisticCurrentFullnessLogsView logisticCurrentFullnessLogsView = new LogisticCurrentFullnessLogsView();

			LocationView view = new LocationView();
			if (dailyPickupLogModel.getPickupRouteModel() != null) {
				view.setPickupRouteView(KeyValueView.create(dailyPickupLogModel.getPickupRouteModel().getId(), dailyPickupLogModel.getPickupRouteModel().getPickupRouteName().toString()));
			}
			logisticCurrentFullnessLogsView.setLocationView(view);
			logisticCurrentFullnessLogsView.setDate(dailyPickupLogModel.getGeneratePlanDate());

			// Start GeneratePlanned Data
			logisticCurrentFullnessLogsView.setGeneratePlanDate(dailyPickupLogModel.getGeneratePlanDate());
			logisticCurrentFullnessLogsView.setPlannedNumberOfPatBottle(dailyPickupLogModel.getNumberOfPatBottle());
			logisticCurrentFullnessLogsView.setPlannedNumberOfAluBottle(dailyPickupLogModel.getNumberOfAluBottle());
			logisticCurrentFullnessLogsView.setPlannedNumberOfGlassBottle(dailyPickupLogModel.getNumberOfGlassBottle());
			logisticCurrentFullnessLogsView.setPlannedTotalWeight(dailyPickupLogModel.getTotalWeight());
			logisticCurrentFullnessLogsView.setPlannedTotalNumberOfPickup(dailyPickupLogModel.getTotalNumberOfPickup());
			// End GeneratePlanned Data


			//Start Actual Plan Data. HardResat to till date data of only HardRest machine, rest data are we can consider as zero
			List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService.getHighestHardResetDateOfEachMachine(machineView);

			List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine.stream()
					.collect(Collectors.toMap(
							machineLog -> machineLog.getMachineModel().getId(),
							Function.identity(),
							(existing, replacement) -> existing))
					.values()
					.stream()
					.collect(Collectors.toList());

			Long patBottleCount = null, aluBottleCount = null, glassBottleCount = null;
			int noOfPickup = 0;
			for (MachineLogModel machineLogModel : uniqueList) {

				if(machineLogModel.getLocationModel().getPickupRouteModel().getId() == dailyPickupLogModel.getPickupRouteModel().getId()) {
					noOfPickup ++;

					Map<String, Long> bottleCounts = transactionService.getTotalBottleCountByMachineIdAndLocationIdAndLastDateOfTransaction(machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate(), machineLogModel.getMachineModel().getLocationModel().getId(), endEpoch);
					Long patBottleCountFromMap = bottleCounts.get("patBottleCount");
					Long aluBottleCountFromMap = bottleCounts.get("aluBottleCount");
					Long glassBottleCountFromMap = bottleCounts.get("glassBottleCount");

					if (patBottleCountFromMap != null) {
						patBottleCount = (patBottleCount != null) ? patBottleCount + patBottleCountFromMap : patBottleCountFromMap;
					}
					else {
						patBottleCount = patBottleCount + 0L;
					}
					if (aluBottleCountFromMap != null) {
						aluBottleCount = (aluBottleCount != null) ? aluBottleCount + aluBottleCountFromMap : aluBottleCountFromMap;
					}
					else {
						aluBottleCount = aluBottleCount + 0L;
					}
					if (glassBottleCountFromMap != null) {
						glassBottleCount = (glassBottleCount != null) ? glassBottleCount + glassBottleCountFromMap : glassBottleCountFromMap;
					}
					else {
						glassBottleCount = glassBottleCount + 0L;
					}
				}
			}

			int plat = Optional.ofNullable(patBottleCount).map(Long::intValue).orElse(0);
			int alu = Optional.ofNullable(glassBottleCount).map(Long::intValue).orElse(0);
			int glass = Optional.ofNullable(glassBottleCount).map(Long::intValue).orElse(0);

			logisticCurrentFullnessLogsView.setNumberOfPlasticBottlesSinceLastFullReset(plat);
			logisticCurrentFullnessLogsView.setNumberOfAluminumBottlesSinceLastFullReset(alu);
			logisticCurrentFullnessLogsView.setNumberOfGlassBottlesSinceLastFullReset(glass);

			logisticCurrentFullnessLogsView.setTotalWeightOfPickup(Long.valueOf(glass * 700) + (plat * 25) + (alu * 10));
			logisticCurrentFullnessLogsView.setTotalNumberOfPickups(noOfPickup);
			//End Actual Plan Data

			l.add(logisticCurrentFullnessLogsView);

		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),l.size(), l);
	}

	@Override
	public Response doExportCurrentfullnessLog(MachineView machineView1, Integer orderType, Integer orderParam)
			throws EndlosAPIException {

		List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService
				.getHighestHardResetDateOfEachMachine(machineView1);

		List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine
				.stream().collect(Collectors.toMap(machineLog -> machineLog.getMachineModel().getId(),
						Function.identity(), (existing, replacement) -> existing))
				.values().stream().collect(Collectors.toList());

		if (uniqueList == null || uniqueList.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		String newFileName = "current_fullness_logs" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Current Fullness Logs";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Store Chain");
			rowhead.createCell((short) 1).setCellValue("Branch");
			rowhead.createCell((short) 2).setCellValue("Pickup Route Name");
			rowhead.createCell((short) 3).setCellValue("Machine ID");
			rowhead.createCell((short) 4).setCellValue("Date Stamp of Last Full Reset");
			//rowhead.createCell((short) 5).setCellValue("Time Stamp of Reset");
			rowhead.createCell((short) 5).setCellValue("Hours From Last Time Stemp Till Now");
			rowhead.createCell((short) 6).setCellValue("No.of Plastic Bin Resets Since Full Reset");
			rowhead.createCell((short) 7).setCellValue("No.of Aluminum Bin Resets Since Full Reset");
			rowhead.createCell((short) 8).setCellValue("No.of Glass Bin Resets Since Full Reset");
			rowhead.createCell((short) 9).setCellValue("No.of plastic bottles Since Last Full Reset");
			rowhead.createCell((short) 10).setCellValue("No.of Aluminum Bottles Since Last Full Reset");
			rowhead.createCell((short) 11).setCellValue("No.of Glass Bottles Since Last Full Reset");
			rowhead.createCell((short) 12).setCellValue("Total");

			int i = 0;
			for (MachineLogModel machineLogModel1 : uniqueList) {
				i++;
				Row row = sheet.createRow((short) i);

				Integer numberOfPlasticBinResetsSinceFullReset = 0;
				Integer numberOfAluminumBinResetsSinceFullReset = 0;
				Integer numberOfGlassBinResetsSinceFullReset = 0;

				if (machineLogModel1.getLocationModel() != null) {
					if (machineLogModel1.getLocationModel().getCustomerModel() != null) {
						row.createCell((short) 0).setCellValue(machineLogModel1.getLocationModel().getCustomerModel().getName());
					} else {
						row.createCell((short) 0).setCellValue("");
					}

					row.createCell((short) 1).setCellValue(machineLogModel1.getLocationModel().getName());

					if (machineLogModel1.getLocationModel().getPickupRouteModel() != null) {
						row.createCell((short) 2).setCellValue(machineLogModel1.getLocationModel().getPickupRouteModel().getPickupRouteName());
					}
					else {
						row.createCell((short) 2).setCellValue("");
					}

				} else {
					row.createCell((short) 1).setCellValue("");
					row.createCell((short) 2).setCellValue("");
				}
				if (machineLogModel1.getMachineModel() != null) {
					row.createCell((short) 3).setCellValue(machineLogModel1.getMachineModel().getMachineId());
				} else {
					row.createCell((short) 3).setCellValue("");
				}

				if (machineLogModel1.getHardResetDate() != null || machineLogModel1.getHardResetDate() != 0) {
					row.createCell((short) 4).setCellValue(String.valueOf(format.format(machineLogModel1.getHardResetDate() * 1000L)));
					//row.createCell((short) 5).setCellValue(String.valueOf(timeFormat.format(machineLogModel1.getHardResetDate() * 1000L)));

					long epochTimeSeconds = machineLogModel1.getHardResetDate();
					Instant instant = Instant.ofEpochSecond(epochTimeSeconds);
					Instant currentInstant = Instant.now();
					long totalHours = Duration.between(instant, currentInstant).toHours();
					row.createCell((short) 5).setCellValue(totalHours);

				} else {
					row.createCell((short) 4).setCellValue("");
					//row.createCell((short) 5).setCellValue("");
					row.createCell((short) 5).setCellValue("");
				}

				List<MachineLogModel> latestResetDateToTillDateSoftReset = machineLogService.getLatestResetDateToTillDateSoftReset(machineLogModel1.getMachineModel().getId());

				Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(machineLogModel1.getMachineModel().getId(), machineLogModel1.getHardResetDate(), machineLogModel1.getMachineModel().getLocationModel().getId());

				for (MachineLogModel machineLogModel : latestResetDateToTillDateSoftReset) {
					if (machineLogModel.getMaterialType().getId() == 1) {
						numberOfGlassBinResetsSinceFullReset++;
					} else if (machineLogModel.getMaterialType().getId() == 2) {

						numberOfPlasticBinResetsSinceFullReset++;
					} else if (machineLogModel.getMaterialType().getId() == 3) {
						numberOfAluminumBinResetsSinceFullReset++;
					}

				}
				Long patBottleCount = bottleCounts.get("patBottleCount");
				Long aluBottleCount = bottleCounts.get("aluBottleCount");
				Long glassBottleCount = bottleCounts.get("glassBottleCount");

				if (numberOfPlasticBinResetsSinceFullReset != null) {
					row.createCell((short) 6).setCellValue(numberOfPlasticBinResetsSinceFullReset);
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (numberOfAluminumBinResetsSinceFullReset != null) {
					row.createCell((short) 7).setCellValue(numberOfAluminumBinResetsSinceFullReset);
				} else {
					row.createCell((short) 7).setCellValue("");
				}
				if (numberOfGlassBinResetsSinceFullReset != null) {
					row.createCell((short) 8).setCellValue(numberOfGlassBinResetsSinceFullReset);
				} else {
					row.createCell((short) 8).setCellValue("");
				}
				if (patBottleCount != null) {
					row.createCell((short) 9).setCellValue(patBottleCount.intValue());
				} else {
					row.createCell((short) 9).setCellValue("");
				}
				if (aluBottleCount != null) {
					row.createCell((short) 10).setCellValue(aluBottleCount);
				} else {
					row.createCell((short) 10).setCellValue("");
				}
				if (glassBottleCount != null) {
					row.createCell((short) 11).setCellValue(glassBottleCount);
				} else {
					row.createCell((short) 11).setCellValue("");
				}
				Long total = patBottleCount + glassBottleCount + aluBottleCount;
				if (total != null) {
					row.createCell((short) 12).setCellValue(total);
				} else {
					row.createCell((short) 12).setCellValue("");
				}
			}

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
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	@Override
	public Response doExportPickupLogsperMachine(MachineView machineView1, Integer orderType, Integer orderParam)
			throws EndlosAPIException {

		List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService.getHighestHardResetDateOfEachMachine(machineView1);

		List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine.stream()
				.collect(Collectors.toMap(
						machineLog -> machineLog.getMachineModel().getId(),
						Function.identity(),
						(existing, replacement) -> existing))
				.values()
				.stream()
				.collect(Collectors.toList());
		if (uniqueList == null || uniqueList.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		String newFileName = "pickup_logs_per_machine" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Pickup Logs per Machine";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Store Chain");
			rowhead.createCell((short) 1).setCellValue("Branch");
			rowhead.createCell((short) 2).setCellValue("Machine ID");
			rowhead.createCell((short) 3).setCellValue("Pickup Route Name");
			rowhead.createCell((short) 4).setCellValue("Date of Full Reset");
			rowhead.createCell((short) 5).setCellValue("Time of Full Reset");
			rowhead.createCell((short) 6).setCellValue("Number of plastic bottles In Pickup");
			rowhead.createCell((short) 7).setCellValue("Number of Aluminum Bottles In Pickup");
			rowhead.createCell((short) 8).setCellValue("Number of Glass Bottles In Pickup");
			rowhead.createCell((short) 9).setCellValue("Total Weight of Pickup(kg)");
			rowhead.createCell((short) 10).setCellValue("Total");

			int i = 0;
			for (MachineLogModel machineLogModel : uniqueList) {

				i++;
				Row row = sheet.createRow((short) i);

				//List<MachineLogModel> latestResetDateToTillDateSoftReset = machineLogService.getLatestResetDateToTillDateSoftReset(machineLogModel.getMachineModel().getId());

				if (machineLogModel.getMachineModel() != null) {
					row.createCell((short) 2).setCellValue(machineLogModel.getMachineModel().getMachineId());
				}
				else {
					row.createCell((short) 2).setCellValue("");
				}
				if (machineLogModel.getLocationModel() != null) {
					if (machineLogModel.getLocationModel().getCustomerModel() != null) {
						row.createCell((short) 0).setCellValue(machineLogModel.getLocationModel().getCustomerModel().getName());
					}
					else {
						row.createCell((short) 0).setCellValue("");
					}
					row.createCell((short) 1).setCellValue(machineLogModel.getLocationModel().getName());

					if (machineLogModel.getLocationModel().getPickupRouteModel() != null) {
						row.createCell((short) 3).setCellValue(machineLogModel.getLocationModel().getPickupRouteModel().getPickupRouteName());
					}
					else {
						row.createCell((short) 3).setCellValue("");
					}
				} else {
					row.createCell((short) 1).setCellValue("");
				}

				if (machineLogModel.getHardResetDate() != null || machineLogModel.getHardResetDate()  != 0) {
					row.createCell((short) 4).setCellValue(String.valueOf(format.format(machineLogModel.getHardResetDate()  * 1000L)));
					row.createCell((short) 5).setCellValue(String.valueOf(timeFormat.format(machineLogModel.getHardResetDate()  * 1000L)));

				} else {
					row.createCell((short) 4).setCellValue("");
					row.createCell((short) 5).setCellValue("");
				}

				/*Integer numberOfPlasticBottlesSinceLastFullReset = 0;
				Integer numberOfAluminumBottlesSinceLastFullReset = 0;
				Integer numberOfGlassBottlesSinceLastFullReset = 0;
				for (MachineLogModel logModel : latestResetDateToTillDateSoftReset) {

					if (machineLogModel.getMaterialType().getId() == 1) {
						numberOfGlassBottlesSinceLastFullReset+= logModel.getMaterialCount().intValue();
					}
					else if (machineLogModel.getMaterialType().getId() == 2) {
						numberOfPlasticBottlesSinceLastFullReset += logModel.getMaterialCount().intValue();
					}
					else if (machineLogModel.getMaterialType().getId() == 3) {
						numberOfAluminumBottlesSinceLastFullReset+= logModel.getMaterialCount().intValue();
					}
				}
				Long totalWeight = Long.valueOf(numberOfGlassBottlesSinceLastFullReset*700) + (numberOfPlasticBottlesSinceLastFullReset*25) + (numberOfAluminumBottlesSinceLastFullReset*10);
*/

				/*Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate());
				Long patBottleCount = bottleCounts.get("patBottleCount");
				Long aluBottleCount = bottleCounts.get("aluBottleCount");
				Long glassBottleCount = bottleCounts.get("glassBottleCount");
				Long totalWeight = Long.valueOf(glassBottleCount*700) + (patBottleCount*25) + (aluBottleCount*10);*/

				Long patBottleCount = 0L;
				Long aluBottleCount = 0L;
				Long glassBottleCount = 0L;

				List<MachineLogModel> result = highestHardResetDateOfEachMachine.stream()
						.filter(obj -> obj.getMachineModel().getId() == machineLogModel.getMachineModel().getId())
						.collect(Collectors.toList());

				for (MachineLogModel logModel : result) {
					if (logModel.getMaterialType().getId() == 1) {
						glassBottleCount+= logModel.getMaterialCount().intValue();
					}
					else if (logModel.getMaterialType().getId() == 2) {
						patBottleCount += logModel.getMaterialCount().intValue();
					}
					else if (logModel.getMaterialType().getId() == 3) {
						aluBottleCount+= logModel.getMaterialCount().intValue();
					}
				}
				Long totalWeight = Long.valueOf(glassBottleCount*700) + (patBottleCount*25) + (aluBottleCount*10);

				if (patBottleCount != null) {
					row.createCell((short) 6).setCellValue(patBottleCount);
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (aluBottleCount != null) {
					row.createCell((short) 7).setCellValue(aluBottleCount);
				} else {
					row.createCell((short) 7).setCellValue("");
				}
				if (glassBottleCount != null) {
					row.createCell((short) 8).setCellValue(glassBottleCount);
				} else {
					row.createCell((short) 8).setCellValue("");
				}
				if (totalWeight != null) {
					double weight = (double) totalWeight / 1000.0;
					row.createCell((short) 9).setCellValue(weight);
				} else {
					row.createCell((short) 9).setCellValue("");
				}
				Long total = glassBottleCount + aluBottleCount + patBottleCount;
				if (total != null) {
					row.createCell((short) 10).setCellValue(total);
				} else {
					row.createCell((short) 10).setCellValue("");
				}

			}

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
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}

	@Override
	public Response doExportPickupLogsperRoute(MachineView machineView1, Integer orderType, Integer orderParam)
			throws EndlosAPIException {

		List l = new ArrayList();
		MachineView machineView=new MachineView();
		machineView.setFullTextSearch(machineView1.getFullTextSearch());

		LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		Long endEpoch = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toEpochSecond();

		List<DailyPickupLogModel> allPickupRoute = dailyPickupLogService.getAllPickupRoute(machineView1.getFullTextSearch(), machineView1.getStartDate(), machineView1.getEndDate());

		if (allPickupRoute == null || allPickupRoute.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = "pickup_logs_per_route" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Pickup Logs per Route";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S.No.");
			rowhead.createCell((short) 1).setCellValue("Pickup Route Name");
			rowhead.createCell((short) 2).setCellValue("Planned Date");
			rowhead.createCell((short) 3).setCellValue("Planned No.of Plastic Bottles in Route");
			rowhead.createCell((short) 4).setCellValue("Planned No.of Aluminum Bottles in Route");
			rowhead.createCell((short) 5).setCellValue("Planned No.of Glass Bottles in Route");
			rowhead.createCell((short) 6).setCellValue("Planned Total Weight of Route(kg)");
			rowhead.createCell((short) 7).setCellValue("Planned Total No.of Pickups");

			rowhead.createCell((short) 8).setCellValue("Actual No.of Plastic Bottles in Route");
			rowhead.createCell((short) 9).setCellValue("Actual No.of Aluminum Bottles in Route");
			rowhead.createCell((short) 10).setCellValue("Actual No.of Glass Bottles in Route");
			rowhead.createCell((short) 11).setCellValue("Actual Total Weight of Route(kg)");
			rowhead.createCell((short) 12).setCellValue("Actual Total No.of Pickups");

			int i = 0;

			for (DailyPickupLogModel dailyPickupLogModel : allPickupRoute) {

				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);

				if (dailyPickupLogModel.getPickupRouteModel() != null) {
					row.createCell((short) 1).setCellValue(dailyPickupLogModel.getPickupRouteModel().getPickupRouteName());
				} else {
					row.createCell((short) 1).setCellValue("");
				}

				if (dailyPickupLogModel.getGeneratePlanDate() != null || dailyPickupLogModel.getGeneratePlanDate() != 0) {

					row.createCell((short) 2).setCellValue(String.valueOf(format.format(dailyPickupLogModel.getGeneratePlanDate() * 1000L)));
				} else {
					row.createCell((short) 2).setCellValue("");
				}

				if (dailyPickupLogModel.getNumberOfPatBottle() != null) {
					row.createCell((short) 3).setCellValue(dailyPickupLogModel.getNumberOfPatBottle());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (dailyPickupLogModel.getNumberOfAluBottle() != null) {
					row.createCell((short) 4).setCellValue(dailyPickupLogModel.getNumberOfAluBottle());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (dailyPickupLogModel.getNumberOfGlassBottle() != null) {
					row.createCell((short) 5).setCellValue(dailyPickupLogModel.getNumberOfGlassBottle());
				} else {
					row.createCell((short) 5).setCellValue("");
				}
				if (dailyPickupLogModel.getTotalWeight() != null) {
					BigDecimal bigDecimal = new BigDecimal(dailyPickupLogModel.getTotalWeight()+"");
					String stringWeight = bigDecimal.toString();
					row.createCell((short) 6).setCellValue(stringWeight);
				} else {
					row.createCell((short) 6).setCellValue("");
				}
				if (dailyPickupLogModel.getTotalNumberOfPickup() != null) {
					row.createCell((short) 7).setCellValue(dailyPickupLogModel.getTotalNumberOfPickup());
				} else {
					row.createCell((short) 7).setCellValue("");
				}



				//Start Actual Plan Data. HardResat to till date data of only HardRest machine, rest data are we can consider as zero
				List<MachineLogModel> highestHardResetDateOfEachMachine = machineLogService.getHighestHardResetDateOfEachMachine(machineView);

				List<MachineLogModel> uniqueList = highestHardResetDateOfEachMachine.stream()
						.collect(Collectors.toMap(
								machineLog -> machineLog.getMachineModel().getId(),
								Function.identity(),
								(existing, replacement) -> existing))
						.values()
						.stream()
						.collect(Collectors.toList());

				Long patBottleCount = null, aluBottleCount = null, glassBottleCount = null;
				int noOfPickup = 0;
				for (MachineLogModel machineLogModel : uniqueList) {

					if(machineLogModel.getLocationModel().getPickupRouteModel().getId() == dailyPickupLogModel.getPickupRouteModel().getId()) {
						noOfPickup ++;

						Map<String, Long> bottleCounts = transactionService.getTotalBottleCountByMachineIdAndLocationIdAndLastDateOfTransaction(machineLogModel.getMachineModel().getId(), machineLogModel.getHardResetDate(), machineLogModel.getMachineModel().getLocationModel().getId(), endEpoch);
						Long patBottleCountFromMap = bottleCounts.get("patBottleCount");
						Long aluBottleCountFromMap = bottleCounts.get("aluBottleCount");
						Long glassBottleCountFromMap = bottleCounts.get("glassBottleCount");

						if (patBottleCountFromMap != null) {
							patBottleCount = (patBottleCount != null) ? patBottleCount + patBottleCountFromMap : patBottleCountFromMap;
						}
						else {
							patBottleCount = patBottleCount + 0L;
						}
						if (aluBottleCountFromMap != null) {
							aluBottleCount = (aluBottleCount != null) ? aluBottleCount + aluBottleCountFromMap : aluBottleCountFromMap;
						}
						else {
							aluBottleCount = aluBottleCount + 0L;
						}
						if (glassBottleCountFromMap != null) {
							glassBottleCount = (glassBottleCount != null) ? glassBottleCount + glassBottleCountFromMap : glassBottleCountFromMap;
						}
						else {
							glassBottleCount = glassBottleCount + 0L;
						}
					}
				}

				int plat = Optional.ofNullable(patBottleCount).map(Long::intValue).orElse(0);
				int alu = Optional.ofNullable(glassBottleCount).map(Long::intValue).orElse(0);
				int glass = Optional.ofNullable(glassBottleCount).map(Long::intValue).orElse(0);


				long totalWeight = (Long.valueOf(glass * 700) + (plat * 25) + (alu * 10));
				BigDecimal bigDecimal = BigDecimal.valueOf(totalWeight).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
				String stringWeight = bigDecimal.toString();

				row.createCell((short) 8).setCellValue(plat);
				row.createCell((short) 9).setCellValue(alu);
				row.createCell((short) 10).setCellValue(glass);
				row.createCell((short) 11).setCellValue(stringWeight);
				row.createCell((short) 12).setCellValue(noOfPickup);
				//End Actual Plan Data


			}

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
		fileModel.setModule(Long.valueOf(ModuleEnum.TRANSACTION.getId()));
		fileModel.setPublicfile(false);
		fileModel.setPath(filepath);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully",
				fileOperation.fromModel(fileModel));
	}
}