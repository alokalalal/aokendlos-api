/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.logistic.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.PageModel;
import com.intentlabs.common.operation.AbstractOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.PageResultListRespone;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.service.BaseService;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;
import com.intentlabs.endlos.customer.model.LocationModel;
import com.intentlabs.endlos.customer.service.LocationService;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.logistic.model.DailyPickupLogModel;
import com.intentlabs.endlos.logistic.model.PickupRouteModel;
import com.intentlabs.endlos.logistic.service.DailyPickupLogService;
import com.intentlabs.endlos.logistic.service.PickupRouteService;
import com.intentlabs.endlos.logistic.view.DailyPickupAssigneeView;
import com.intentlabs.endlos.logistic.view.PickupRouteView;
import com.intentlabs.endlos.machine.enums.MaterialEnum;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineLogService;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.TransactionService;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This class used to perform all business operation on PickupRote model.
 * 
 * @author Milan.Gohil
 * @since 11/12/2023
 */
@Component(value = "PickupRouteOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class PickupRouteOperationImpl extends AbstractOperation<PickupRouteModel, PickupRouteView>
		implements PickupRouteOperation {

	@Autowired
	private PickupRouteService pickupRouteService;

	@Autowired
	private FileService fileService;

	@Autowired
	private FileOperation fileOperation;

	@Override
	public BaseService getService() {
		return pickupRouteService;
	}

	@Override
	protected PickupRouteModel getNewModel() {
		return new PickupRouteModel();
	}

	@Override
	protected PickupRouteModel loadModel(PickupRouteView pickupRouteView) {
		return pickupRouteService.get(pickupRouteView.getId());
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(PickupRouteView pickupRouteView) throws EndlosAPIException {
		PickupRouteModel pickupRouteModel;

		pickupRouteModel = pickupRouteService.getByNumber(pickupRouteView.getPickupRouteNo(), null);
		if (pickupRouteModel != null) {
			throw new EndlosAPIException(ResponseCode.ROUTE_NUMBER_ALREADY_EXIST.getCode(),
					ResponseCode.ROUTE_NUMBER_ALREADY_EXIST.getMessage());
		}
		pickupRouteModel = pickupRouteService.getByName(pickupRouteView.getName(), null);
		if (pickupRouteModel != null && pickupRouteModel.getPickupRouteName().equals(pickupRouteView.getName())) {
			throw new EndlosAPIException(ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getCode(),
					ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getMessage());
		}
		pickupRouteModel = toModel(new PickupRouteModel(), pickupRouteView);
		pickupRouteService.create(pickupRouteModel);
		return CommonResponse.create(ResponseCode.SAVE_SUCCESSFULLY.getCode(),
				ResponseCode.SAVE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		PickupRouteModel pickupRouteModel = pickupRouteService.get(id);
		if (pickupRouteModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		PickupRouteView pickupRouteView = fromModel(pickupRouteModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pickupRouteView);
	}

	@Override
	public Response doEdit(Long id) throws EndlosAPIException {
		return doView(id);
	}

	@Override
	public Response doUpdate(PickupRouteView pickupRouteView) throws EndlosAPIException {
		PickupRouteModel pickupRouteModel = pickupRouteService.get(pickupRouteView.getId());
		if (pickupRouteModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		PickupRouteModel pickupRouteNumberExist = pickupRouteService.getByNumber(pickupRouteView.getPickupRouteNo(),
				pickupRouteModel.getPickupRouteNo());
		if (pickupRouteNumberExist != null) {
			throw new EndlosAPIException(ResponseCode.ROUTE_NUMBER_ALREADY_EXIST.getCode(),
					ResponseCode.ROUTE_NUMBER_ALREADY_EXIST.getMessage());
		}
		PickupRouteModel pickupRouteNameExist = pickupRouteService.getByName(pickupRouteView.getName(),
				pickupRouteModel.getPickupRouteName());
		if (pickupRouteNameExist != null) {
			throw new EndlosAPIException(ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getCode(),
					ResponseCode.PICKUP_ROUTE_ALREADY_EXIST.getMessage());
		}

		// checkInactive(pickupRouteModel);
		pickupRouteModel = toModel(pickupRouteModel, pickupRouteView);
		pickupRouteService.update(pickupRouteModel);
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	public Response doSearch(PickupRouteView pickupRouteView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = pickupRouteService.search(pickupRouteView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		List<PickupRouteView> pickupRouteViews = fromModelList((List<PickupRouteModel>) pageModel.getList());
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), pickupRouteViews);
	}

	@Override
	public Response doDelete(Long id) throws EndlosAPIException {
		PickupRouteModel pickupRouteModel = pickupRouteService.get(id);
		if (pickupRouteModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		pickupRouteService.hardDelete(pickupRouteModel);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UPDATE_SUCCESSFULLY.getCode(),
				ResponseCode.UPDATE_SUCCESSFULLY.getMessage());
	}

	@Override
	protected void checkInactive(PickupRouteModel pickupRouteModel) throws EndlosAPIException {

	}

	@Override
	public PickupRouteModel toModel(PickupRouteModel pickupRouteModel, PickupRouteView pickupRouteView)
			throws EndlosAPIException {
		pickupRouteModel.setPickupRouteNo(pickupRouteView.getPickupRouteNo());
		pickupRouteModel.setPickupRouteName(pickupRouteView.getName());
		pickupRouteModel.setArea(pickupRouteView.getArea());
		pickupRouteModel.setComment(pickupRouteView.getComment());
		pickupRouteModel.setPickupRoutecreateDate(Instant.now().getEpochSecond());

		return pickupRouteModel;
	}

	@Override
	public PickupRouteView fromModel(PickupRouteModel pickupRouteModel) {
		PickupRouteView pickupRouteView = new PickupRouteView();
		pickupRouteView.setId(pickupRouteModel.getId());
		pickupRouteView.setPickupRouteNo(pickupRouteModel.getPickupRouteNo());
		pickupRouteView.setArea(pickupRouteModel.getArea());
		pickupRouteView.setName(pickupRouteModel.getPickupRouteName());
		pickupRouteView.setComment(pickupRouteModel.getComment());
		pickupRouteView.setPickupRoutecreateDate(pickupRouteModel.getPickupRoutecreateDate());
		return pickupRouteView;
	}

	@Override
	public Response doDropdown() {

		/*
		 * List<KeyValueView> listPickupRoute = new ArrayList<>(); for (Map.Entry<Long,
		 * PickupRouteModel> pickupRouteMap :
		 * PickupRouteModel.getPickupRoutes().entrySet()) {
		 * listPickupRoute.add(KeyValueView.create(pickupRouteMap.getValue().getId(),
		 * pickupRouteMap.getValue().getPickupRouteName()));
		 * 
		 * } Collections.sort(listPickupRoute, (o1, o2) ->
		 * (o1).getValue().compareTo((o2).getValue())); return
		 * PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(),
		 * ResponseCode.SUCCESSFUL.getMessage(), listPickupRoute.size(),
		 * listPickupRoute);
		 */

		List<PickupRouteModel> pickupRouteModels = pickupRouteService.findAll();
		if (pickupRouteModels == null || pickupRouteModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		// Sort the list based on the ID in descending order
		Collections.sort(pickupRouteModels, Comparator.comparingLong(PickupRouteModel::getId).reversed());

		List<PickupRouteView> pickupRouteViews = new ArrayList<>();
		for (PickupRouteModel pickupRouteModel : pickupRouteModels) {
			PickupRouteView pickupRouteView = new PickupRouteView();
			pickupRouteView.setId(pickupRouteModel.getId());
			pickupRouteView.setName(pickupRouteModel.getPickupRouteName());
			pickupRouteViews.add(pickupRouteView);
		}

		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pickupRouteViews.size(), pickupRouteViews);
	}

	private void setLocationView(LocationView locationView, LocationModel locationModel) {
		locationView.setId(locationModel.getId());
		locationView.setName(locationModel.getName());
		locationView.setBranchNumber(locationModel.getBranchNumber());
	}

	@Override
	public Response doExport(PickupRouteView pickupRouteView, Integer orderType, Integer orderParam)
			throws EndlosAPIException {
		List<PickupRouteModel> pickupRouteModels = pickupRouteService.doExport(pickupRouteView, orderType, orderParam);
		if (pickupRouteModels == null || pickupRouteModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		String newFileName = "pickup_route" + "_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Pickup route";
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Timestamp");
			rowhead.createCell((short) 2).setCellValue("Pickup Route Number");
			rowhead.createCell((short) 3).setCellValue("Area");
			rowhead.createCell((short) 4).setCellValue("Pickup Route Name");
			rowhead.createCell((short) 5).setCellValue("Comment");

			int i = 0;
			for (PickupRouteModel pickupRouteModel : pickupRouteModels) {
				i++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) i);

				if (pickupRouteModel.getPickupRoutecreateDate() != null
						|| pickupRouteModel.getPickupRoutecreateDate() != 0) {
					row.createCell((short) 1).setCellValue(
							String.valueOf(format.format(pickupRouteModel.getPickupRoutecreateDate() * 1000L)));
				} else {
					row.createCell((short) 1).setCellValue("");
				}

				if (pickupRouteModel.getPickupRouteNo() != null) {
					row.createCell((short) 2).setCellValue(pickupRouteModel.getPickupRouteNo());
				} else {
					row.createCell((short) 2).setCellValue("");
				}
				if (pickupRouteModel.getArea() != null) {
					row.createCell((short) 3).setCellValue(pickupRouteModel.getArea());
				} else {
					row.createCell((short) 3).setCellValue("");
				}
				if (pickupRouteModel.getPickupRouteName() != null) {
					row.createCell((short) 4).setCellValue(pickupRouteModel.getPickupRouteName());
				} else {
					row.createCell((short) 4).setCellValue("");
				}
				if (pickupRouteModel.getComment() != null) {
					row.createCell((short) 5).setCellValue(pickupRouteModel.getComment());
				} else {
					row.createCell((short) 5).setCellValue("");
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

	@Autowired
	LocationService locationService;

	@Autowired
	MachineService machineService;

	@Autowired
	MachineLogService machineLogService;

	@Autowired
	TransactionService transactionService;

	@Override
	public Response doGetDailyPickupAssignee(DailyPickupAssigneeView dailyPickupAssigneeView, Integer start,
			Integer recordSize) {
		PageModel pageModel = machineService.getByPickedupRoute(dailyPickupAssigneeView, start, recordSize);
//		List<DailyPickupAssigneeView> assigneeViews = new ArrayList<>();
		List<MachineView> machineViews = new ArrayList<>();
//		for (LocationModel locationModel : locationModels) {
//			List<MachineModel> machineModels = machineService.getByLocation(locationModel.getId());
		if (pageModel.getRecords() != 0) {
//				DailyPickupAssigneeView assigneeView = new DailyPickupAssigneeView();
//				CustomerView customerView = new CustomerView();
//				customerView.setId(locationModel.getCustomerModel().getId());
//				customerView.setName(locationModel.getCustomerModel().getName());
//				assigneeView.setCustomerView(customerView);
//				LocationView locationView = new LocationView();
//				locationView.setId(locationModel.getId());
//				locationView.setName(locationModel.getName());
//				PickupRouteView pickupRouteView = new PickupRouteView();
//				if (locationModel.getPickupRouteModel() != null) {
//					pickupRouteView.setId(locationModel.getPickupRouteModel().getId());
//					pickupRouteView.setName(locationModel.getPickupRouteModel().getPickupRouteName());
//					locationView.setPickupRouteView1(pickupRouteView);
//				}
//				locationView.setPickupEveryday(locationModel.getPickupEveryday());
//				locationView.setNumberOfGlassTanks(locationModel.getNumberOfGlassTanks());
//				locationView.setPositionRoute(locationModel.getPositionRoute());
//
//				assigneeView.setLocationView(locationView);
//				List<MachineView> machineViews = new ArrayList<MachineView>();
//				Long totalGlassResetCount = 0l;
//				Long totalAluminiumnResetCount = 0l;
//				Long totalPlasticResetCount = 0l;
//				Long totalBottleCountOfAllMachine = 0l;
//				Long totalVoucherCountOfAllMachine = 0l;
//				BigDecimal totalBottleWeight = new BigDecimal(0);
			for (MachineModel machineModel : (List<MachineModel>) pageModel.getList()) {
				if (machineModel.getLocationModel() != null) {
					MachineView machineView = new MachineView();
					CustomerView customerView = new CustomerView();
					customerView.setId(machineModel.getLocationModel().getCustomerModel().getId());
					customerView.setName(machineModel.getLocationModel().getCustomerModel().getName());
					machineView.setCustomerView(customerView);
					LocationView locationView = new LocationView();
					locationView.setId(machineModel.getLocationModel().getId());
					locationView.setName(machineModel.getLocationModel().getName());
					PickupRouteView pickupRouteView = new PickupRouteView();
					if (machineModel.getLocationModel().getPickupRouteModel() != null) {
						pickupRouteView.setId(machineModel.getLocationModel().getPickupRouteModel().getId());
						pickupRouteView
								.setName(machineModel.getLocationModel().getPickupRouteModel().getPickupRouteName());
						locationView.setPickupRouteView1(pickupRouteView);
					}
					locationView.setPickupEveryday(machineModel.getLocationModel().getPickupEveryday());
					if (machineModel.getLocationModel().getPickupEveryday() != null)
						machineView.setPickupEveryday(machineModel.getLocationModel().getPickupEveryday());
					locationView.setNumberOfGlassTanks(machineModel.getLocationModel().getNumberOfGlassTanks());
					locationView.setPositionRoute(machineModel.getLocationModel().getPositionRoute());
					machineView.setLocationView(locationView);
					machineView.setId(machineModel.getId());
					machineView.setMachineId(machineModel.getMachineId());
					Long hardResetDateOfMachine = machineLogService.getLastHardResetDateOfMachine(machineModel.getId(),
							machineModel.getLocationModel().getId());

					/*Long resetDate = machineLogService.getLastSoftResetDateOfMachine(machineModel.getId(), machineModel.getLocationModel().getId());
					if (resetDate != null) {
						machineView.setLastHardResetDate(resetDate);
					}*/

					if (hardResetDateOfMachine == null) {
						hardResetDateOfMachine = machineModel.getCreateDate();
					}
					machineView.setLastHardResetDate(hardResetDateOfMachine);
					Duration duration = Duration.between(Instant.ofEpochSecond(hardResetDateOfMachine), Instant.now());
					long hours = duration.toHours();

					/*long minutes = duration.toMinutes() % 60;
					machineView.setHoursFromPickup(hours + "Hours" + "" + minutes + " minutes");*/

					machineView.setHoursFromPickup(hours + " Hr");

					Long glassResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
							MaterialEnum.GLASS.getId(), hardResetDateOfMachine,
							machineModel.getLocationModel().getId());
					Long plasticResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
							MaterialEnum.PLASTIC.getId(), hardResetDateOfMachine,
							machineModel.getLocationModel().getId());
					Long aluminiumnResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
							MaterialEnum.ALUMIUMN.getId(), hardResetDateOfMachine,
							machineModel.getLocationModel().getId());

//						totalGlassResetCount = totalGlassResetCount + glassResetCount;
//						totalPlasticResetCount = totalPlasticResetCount + plasticResetCount;
//						totalAluminiumnResetCount = totalAluminiumnResetCount + aluminiumnResetCount;

					machineView.setGlassResetCount(glassResetCount);
					machineView.setPlasticResetCount(plasticResetCount);
					machineView.setAluminiumnResetCount(aluminiumnResetCount);
					Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(machineModel.getId(),
							hardResetDateOfMachine, machineModel.getLocationModel().getId());
					Long patBottleCount = bottleCounts.get("patBottleCount");
					Long aluBottleCount = bottleCounts.get("aluBottleCount");
					Long glassBottleCount = bottleCounts.get("glassBottleCount");
					Long totalCount = patBottleCount + aluBottleCount + glassBottleCount;
					Long totalVoucher = transactionService.getTotalVoucher(machineModel.getId(), hardResetDateOfMachine,
							machineModel.getLocationModel().getId());
					if (totalVoucher == null) {
						totalVoucher = 0l;
					}
					BigDecimal totalWeight = transactionService.getTotalWeight(machineModel.getId(),
							hardResetDateOfMachine, machineModel.getLocationModel().getId());
//						totalBottleCountOfAllMachine = totalBottleCountOfAllMachine + totalCount;
//						totalVoucherCountOfAllMachine = totalVoucherCountOfAllMachine + totalVoucher;
//						totalBottleWeight = totalBottleWeight.add(totalWeight);
					machineView.setGlassBottleCount(glassBottleCount);
					machineView.setAluBottleCount(aluBottleCount);
					machineView.setPatBottleCount(patBottleCount);
					machineView.setTotalBottleCount(totalCount);
					machineView.setTotalVoucher(totalVoucher);

					machineView.setNoGlassBinPickup(machineView.getGlassResetCount() + 1);
					machineView.setMaterialWeight(totalWeight.divide(new BigDecimal(1000)));
					machineView.setBinWeight(new BigDecimal(machineView.getNoGlassBinPickup() * 120));
					machineView.setTotalWight(machineView.getMaterialWeight().add(machineView.getBinWeight()));
					machineView.setMachineActivityStatus(
							MachineView.setMachineActivityStatus(machineModel.getMachineActivityStatus().getId()));
					// machineViews.add(machineView);
//						assigneeView.setMachineViews(machineViews);
//						assigneeView.setNumbersOfGlassReset(totalGlassResetCount);
//						assigneeView.setNumbersOfPatReset(totalPlasticResetCount);
//						assigneeView.setNumbersOfAluReset(totalAluminiumnResetCount);
//						assigneeView.setTotalBottles(totalBottleCountOfAllMachine);
//						assigneeView.setTotalVouchers(totalVoucherCountOfAllMachine);
//						assigneeView.setTotalWeight(totalBottleWeight);
					if (dailyPickupAssigneeView.getStartDate() != null && dailyPickupAssigneeView.getEndDate() != null) {
						if (machineView.getLastHardResetDate() >= dailyPickupAssigneeView.getStartDate() && machineView.getLastHardResetDate() <= dailyPickupAssigneeView.getEndDate()) {
							machineViews.add(machineView);
						}
					}
					else {
						machineViews.add(machineView);
					}
				}
			}
//				Long hardResetDate = machineLogService.getLastHardResetDateOfLocation(locationModel.getId());
//				if (hardResetDate == null) {
//					hardResetDate = machineModels.get(0).getCreateDate();
//				}
//				assigneeView.setLastPickupDate(hardResetDate);
//				if (hardResetDate != null) {
//					Duration duration = Duration.between(Instant.ofEpochSecond(hardResetDate), Instant.now());
//					long hours = duration.toHours();
//					long minutes = duration.toMinutes() % 60;
//					assigneeView.setHoursFromPickup(hours + "Hours" + "" + minutes + " minutes");
//				}
//				assigneeViews.add(assigneeView);
		}
//		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), machineViews);
	}

	@Override
	public Response doGeneratePlan(DailyPickupAssigneeView dailyPickupAssigneeView) throws EndlosAPIException {
//		List<LocationModel> locationModels = locationService
//				.getByLocationIds(dailyPickupAssigneeView.getLocationViews());
		List<MachineModel> machineModels = machineService.getByMachineIds(dailyPickupAssigneeView.getMachineViews());
		Map<PickupRouteModel, List<MachineModel>> modelMap = new HashMap<>();
		for (MachineModel machineModel : machineModels) {
			PickupRouteModel pickupRouteModel = machineModel.getLocationModel().getPickupRouteModel();
			if (modelMap.containsKey(pickupRouteModel)) {
				List<MachineModel> customerMachineModels = modelMap.get(pickupRouteModel);
				customerMachineModels.add(machineModel);
			} else {
				List<MachineModel> customerMachineModels = new ArrayList<>();
				customerMachineModels.add(machineModel);
				modelMap.put(pickupRouteModel, customerMachineModels);
			}
		}
		List<FileView> fileViews = new ArrayList<FileView>();

		for (Map.Entry<PickupRouteModel, List<MachineModel>> entry : modelMap.entrySet()) {
			Long totalNumberOfAluBottle = 0l;
			Long totalNumberOfGlassBottle = 0l;
			Long totalNumberOfPatBottle = 0l;
			Long totalNumberOfPickup = 0l;
			BigDecimal totalWeightPickup = new BigDecimal(0);

			PickupRouteModel pickupRouteModel = entry.getKey();
			List<MachineModel> newModels = entry.getValue();
			String newFileName = DateUtility.getCurrentEpoch() + "_" + pickupRouteModel.getId() + ".xlsx";

			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			format.setTimeZone(TimeZone.getDefault());
			String filepath = null;
			try {
				XSSFWorkbook workbook = new XSSFWorkbook();
				String sheetname = pickupRouteModel.getPickupRouteName();
				Sheet sheet = workbook.createSheet(sheetname);
				Row pickupRouteName = sheet.createRow((short) 0);
				pickupRouteName.createCell((short) 0).setCellValue("Pickup Route Name");
				pickupRouteName.createCell((short) 1).setCellValue(pickupRouteModel.getPickupRouteName());

				Row rowhead = sheet.createRow((short) 1);
				rowhead.createCell((short) 0).setCellValue("S. No.");
				rowhead.createCell((short) 1).setCellValue("Order In Pickup");
				rowhead.createCell((short) 2).setCellValue("Branch Name");
				rowhead.createCell((short) 3).setCellValue("Machine Id");

				//rowhead.createCell((short) 4).setCellValue("Technical Status");

				rowhead.createCell((short) 4).setCellValue("No Of Glass Reset");
				rowhead.createCell((short) 5).setCellValue("No Of Plastic Reset");
				rowhead.createCell((short) 6).setCellValue("No Of Aluminiumn Reset");
				rowhead.createCell((short) 7).setCellValue("No Of Total Glass Bottle");
				rowhead.createCell((short) 8).setCellValue("No Of Total Plastic Bottle");
				rowhead.createCell((short) 9).setCellValue("No Of Total Aluminiumn Bottle");
				rowhead.createCell((short) 10).setCellValue("Total Bottle");

				rowhead.createCell((short) 11).setCellValue("Number Of Glass Bin Pickup");
				rowhead.createCell((short) 12).setCellValue("Material Weight");
				rowhead.createCell((short) 13).setCellValue("Bin Weight");
				rowhead.createCell((short) 14).setCellValue("Total Weight");

				rowhead.createCell((short) 15).setCellValue("Total Voucher");
				rowhead.createCell((short) 16).setCellValue("Comments");

				int i = 1;
				int srNo =0;
				Long totalBottles = 0l;
				Long totalVouchers = 0l;

//				for (LocationModel locationModel : locationModels) {
//					List<MachineModel> machineModels = machineService.getByLocation(locationModel.getId());
				if (!newModels.isEmpty() && newModels != null) {
					for (MachineModel machineModel : newModels) {
						Long hardResetDateOfMachine = machineLogService.getLastHardResetDateOfMachine(
								machineModel.getId(), machineModel.getLocationModel().getId());
						if (hardResetDateOfMachine == null) {
							hardResetDateOfMachine = machineModel.getCreateDate();
						}
						Long glassResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
								MaterialEnum.GLASS.getId(), hardResetDateOfMachine,
								machineModel.getLocationModel().getId());
						Long plasticResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
								MaterialEnum.PLASTIC.getId(), hardResetDateOfMachine,
								machineModel.getLocationModel().getId());
						Long aluminiumnResetCount = machineLogService.getMaterialBinCount(machineModel.getId(),
								MaterialEnum.ALUMIUMN.getId(), hardResetDateOfMachine,
								machineModel.getLocationModel().getId());
						Long totalVoucher = transactionService.getTotalVoucher(machineModel.getId(),
								hardResetDateOfMachine, machineModel.getLocationModel().getId());
						BigDecimal materialWeight = transactionService.getTotalWeight(machineModel.getId(),
								hardResetDateOfMachine, machineModel.getLocationModel().getId());

						Long noGlassBinBinPickup = glassResetCount + 1;
						BigDecimal binWeight = new BigDecimal(noGlassBinBinPickup * 120);
						BigDecimal totalWeight = materialWeight.add(binWeight);
						totalWeightPickup = totalWeightPickup.add(totalWeight);

						Map<String, Long> bottleCounts = transactionService.getTotalBottleCount(machineModel.getId(),
								hardResetDateOfMachine, machineModel.getLocationModel().getId());
						Long patBottleCount = bottleCounts.get("patBottleCount");
						Long aluBottleCount = bottleCounts.get("aluBottleCount");
						Long glassBottleCount = bottleCounts.get("glassBottleCount");
						Long totalCount = patBottleCount + aluBottleCount + glassBottleCount;

						totalNumberOfAluBottle = patBottleCount + totalNumberOfAluBottle;
						totalNumberOfGlassBottle = totalNumberOfGlassBottle + glassBottleCount;
						totalNumberOfPatBottle = totalNumberOfPatBottle + patBottleCount;
						//totalNumberOfPickup = totalNumberOfPickup + totalCount;
						totalNumberOfPickup ++;

						totalBottles = totalBottles + totalCount;
						if (totalVoucher == null) {
							totalVoucher = 0l;
						}
						totalVouchers = totalVouchers + totalVoucher;
						i++;
						srNo++;
						Row row = sheet.createRow((short) i);
						row.createCell((short) 0).setCellValue((Integer) srNo);
						if (machineModel.getLocationModel().getPositionRoute() != null) {
							row.createCell((short) 1).setCellValue(machineModel.getLocationModel().getPositionRoute());
						} else {
							row.createCell((short) 1).setCellValue("");
						}
						if (machineModel.getLocationModel().getName() != null) {
							row.createCell((short) 2).setCellValue(machineModel.getLocationModel().getName());
						} else {
							row.createCell((short) 2).setCellValue("");
						}
						if (machineModel.getMachineId() != null) {
							row.createCell((short) 3).setCellValue(machineModel.getMachineId());
						} else {
							row.createCell((short) 3).setCellValue("");
						}
						/*if (machineModel.getMachineActivityStatus() != null) {
							row.createCell((short) 4).setCellValue(machineModel.getMachineActivityStatus().getName());
						} else {
							row.createCell((short) 4).setCellValue("");
						}*/
						if (glassResetCount != null) {
							row.createCell((short) 4).setCellValue(glassResetCount);
						}
						if (plasticResetCount != null) {
							row.createCell((short) 5).setCellValue(plasticResetCount);
						} else {
							row.createCell((short) 5).setCellValue("");
						}
						if (aluminiumnResetCount != null) {
							row.createCell((short) 6).setCellValue(aluminiumnResetCount);
						} else {
							row.createCell((short) 6).setCellValue("");
						}
						if (glassBottleCount != null) {
							row.createCell((short) 7).setCellValue(glassBottleCount);
						}
						if (patBottleCount != null) {
							row.createCell((short) 8).setCellValue(patBottleCount);
						}
						if (aluBottleCount != null) {
							row.createCell((short) 9).setCellValue(aluBottleCount);
						}
						if (totalCount != null) {
							row.createCell((short) 10).setCellValue(totalCount);
						}
						if (noGlassBinBinPickup != null) {
							row.createCell((short) 11).setCellValue(noGlassBinBinPickup.toString());
						}
						if (materialWeight != null) {
							row.createCell((short) 12).setCellValue(materialWeight.toString());
						}
						if (binWeight != null) {
							row.createCell((short) 13).setCellValue(binWeight.toString());
						}
						if (totalWeight != null) {
							row.createCell((short) 14).setCellValue(totalWeight.toString());
						} else {
							row.createCell((short) 14).setCellValue("");
						}
						if (totalVoucher != null) {
							row.createCell((short) 15).setCellValue(totalVoucher);
						}
						row.createCell((short) 16).setCellValue("No Comments");
					}
				}
//				}
				i++;
				Row weightRow = sheet.createRow((short) i);
				CellRangeAddress mergedRegion = new CellRangeAddress(i, i, 0, 11);
				sheet.addMergedRegion(mergedRegion);

				// Set values for the merged cells
				Cell mergedCell = weightRow.createCell((short) 0);
				mergedCell.setCellValue(totalBottles);

				// Set values for the remaining cells in weightRow
//				weightRow.createCell((short) 10).setCellValue(totalWeightPickup.toString());
//				weightRow.createCell((short) 10).setCellValue(totalBottles);
				weightRow.createCell((short) 14).setCellValue(totalWeightPickup.intValue());
				weightRow.createCell((short) 15).setCellValue(totalVouchers);

				String path = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator;

				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				filepath = file.getPath() + File.separator + newFileName;
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close();

//				PDDocument document = new PDDocument();
//				PDPage page = new PDPage();
//				document.addPage(page);
//
//				// Create content stream for PDF
//				PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//				// Write Excel data to PDF
//				int y = 700; // Initial Y coordinate
//				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
//					String text = sheet.getRow(j).getCell(0).getStringCellValue(); // Assuming data is in first column
//					contentStream.beginText();
//					contentStream.setFont(PDType1Font.HELVETICA, 12);
//					contentStream.newLineAtOffset(50, y);
//					contentStream.showText(text);
//					contentStream.endText();
//					y -= 20; // Adjust Y coordinate for the next row
//				}
//
//				contentStream.close();
//
//				// Save PDF
//				document.save(filepath);
//				document.close();
//
//				System.out.println("PDF created successfully.");

//				try {
//					PdfWriter writer = new PdfWriter(filepath);
//					PdfDocument pdf = new PdfDocument(writer);
//					Document document = new Document(pdf);
				//
//					// Assuming only one sheet for simplicity
//					int rows = sheet.getPhysicalNumberOfRows();
//					for (int i1 = 0; i1 < rows; i1++) {
//					    Row row = sheet.getRow(i1);
//					    if (row != null) {
//					        int cols = row.getPhysicalNumberOfCells();
//					        for (int j = 0; j < cols; j++) {
//					            Cell cell = row.getCell(j);
//					            if (cell != null) {
//					                String cellValue = cell.toString();
//					                // Add cell value to PDF
//					                document.add(new Paragraph(cellValue));
//					            }
//					        }
//					    }
//					}
				//
//					document.close();
//					pdf.close();
//					writer.close();
				//
//					System.out.println("Excel converted to PDF successfully!");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

			} catch (Exception ex) {
				LoggerService.exception(ex);
				return null;
			}
			FileModel fileModel = new FileModel();
			fileModel.setFileId(Utility.generateUuid());
			fileModel.setName(newFileName);
			fileModel.setModule(Long.valueOf(ModuleEnum.PICKUP_ROUTE.getId()));
			fileModel.setPublicfile(false);
			fileModel.setPath(filepath);
			fileModel.setUpload(DateUtility.getCurrentEpoch());
			fileService.create(fileModel);
			fileViews.add(fileOperation.fromModel(fileModel));

			LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
			Long startEpoch = LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.systemDefault()).toEpochSecond();
			Long endEpoch = LocalDateTime.of(localDate, LocalTime.MAX).atZone(ZoneId.systemDefault()).toEpochSecond();

			DailyPickupLogModel dailyPickupLogModel = dailyPickupLogService.getByPickupRoute(pickupRouteModel.getId(),
					startEpoch, endEpoch);
			if (dailyPickupLogModel == null) {
				dailyPickupLogModel = new DailyPickupLogModel();
				dailyPickupLogModel.setNumberOfAluBottle(totalNumberOfAluBottle);
				dailyPickupLogModel.setNumberOfGlassBottle(totalNumberOfGlassBottle);
				dailyPickupLogModel.setNumberOfPatBottle(totalNumberOfPatBottle);
				dailyPickupLogModel.setPickupRouteModel(pickupRouteModel);
				dailyPickupLogModel.setTotalNumberOfPickup(totalNumberOfPickup);
				dailyPickupLogModel.setTotalWeight(totalWeightPickup);
				dailyPickupLogModel.setGeneratePlanDate(DateUtility.getCurrentEpoch());
				dailyPickupLogService.create(dailyPickupLogModel);
			} else {
				dailyPickupLogModel.setNumberOfAluBottle(totalNumberOfAluBottle);
				dailyPickupLogModel.setNumberOfGlassBottle(totalNumberOfGlassBottle);
				dailyPickupLogModel.setNumberOfPatBottle(totalNumberOfPatBottle);
				dailyPickupLogModel.setPickupRouteModel(pickupRouteModel);
				dailyPickupLogModel.setTotalNumberOfPickup(totalNumberOfPickup);
				dailyPickupLogModel.setTotalWeight(totalWeightPickup);
				dailyPickupLogModel.setGeneratePlanDate(DateUtility.getCurrentEpoch());
				dailyPickupLogService.update(dailyPickupLogModel);
			}
		}
		return PageResultListRespone.create(ResponseCode.SUCCESSFUL.getCode(), "Export successfully", fileViews.size(),
				fileViews);
	}

	@Autowired
	DailyPickupLogService dailyPickupLogService;

	public static void main(String[] args) {
		String excelFilePath = "/endlos-api/Excel/input.xlsx";
		String pdfFilePath = "/endlos-api/Excel/output.pdf";

		try {
			// Load XLSX file
			FileInputStream excelFile = new FileInputStream(excelFilePath);
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0); // Assuming you have only one sheet

			// Create PDF document
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);

			// Create content stream for PDF
			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			// Write Excel data to PDF
			int y = 700; // Initial Y coordinate
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				String text = sheet.getRow(i).getCell(0).getStringCellValue(); // Assuming data is in first column
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 12);
				contentStream.newLineAtOffset(50, y);
				contentStream.showText(text);
				contentStream.endText();
				y -= 20; // Adjust Y coordinate for the next row
			}

			contentStream.close();

			// Save PDF
			document.save(pdfFilePath);
			document.close();

			System.out.println("PDF created successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}