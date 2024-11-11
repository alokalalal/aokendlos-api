/*******************************************************************************
\\ * Copyright -2019 @intentlabs
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

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.model.ReportModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.ReportService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.ReportView;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 08/07/2022
 */
@Component(value = "reportOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ReportOperationImpl extends AbstractOperation<ReportModel, ReportView> implements ReportOperation {

	@Autowired
	private ReportService reportService;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
	private FileService fileService;

	@Autowired
	private MachineService machineService;

	@Override
	public BaseService<ReportModel> getService() {
		return reportService;
	}

	@Override
	protected ReportModel loadModel(ReportView reportView) {
		return reportService.get(reportView.getId());
	}

	@Override
	protected ReportModel getNewModel() {
		return new ReportModel();
	}

	@Override
	public Response doAdd() throws EndlosAPIException {
		return null;
	}

	@Override
	public Response doSave(ReportView reportView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doUpdate(ReportView reportView) throws EndlosAPIException {
		return CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
				ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
	}

	@Override
	public Response doView(Long id) throws EndlosAPIException {
		ReportModel reportModel = reportService.get(id);
		if (reportModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		ReportView reportView = fromModel(reportModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), reportView);
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
	public Response doSearch(ReportView reportView, Integer start, Integer recordSize, Integer orderType,
			Integer orderParam) {
		PageModel pageModel = reportService.search(reportView, start, recordSize, orderType, orderParam);
		if (pageModel.getRecords() == 0) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				pageModel.getRecords(), fromModelList((List<ReportModel>) pageModel.getList()));
	}

	@Override
	public Response doActiveInActive(Long id) throws EndlosAPIException {
		return null;
	}

	@Override
	public ReportModel toModel(ReportModel reportModel, ReportView reportView) throws EndlosAPIException {
		return reportModel;
	}

	@Override
	public ReportView fromModel(ReportModel reportModel) {
		ReportView reportView = new ReportView();
		reportView.setId(reportModel.getId());

		MachineView machineView = new MachineView();
		machineView.setId(reportModel.getMachineModel().getId());
		machineView.setMachineId(reportModel.getMachineModel().getMachineId());

		LocationView locationView = new LocationView();
		if (reportModel.getLocationModel() != null) {
			locationView.setId(reportModel.getLocationModel().getId());
			locationView.setName(reportModel.getLocationModel().getName());
			locationView.setBranchNumber(reportModel.getLocationModel().getBranchNumber());
			CustomerView customerView = new CustomerView();
			customerView.setId(reportModel.getLocationModel().getCustomerModel().getId());
			customerView.setName(reportModel.getLocationModel().getCustomerModel().getName());
			locationView.setCustomerView(customerView);
		}
		reportView.setLocationView(locationView);
		reportView.setMachineView(machineView);
		reportView.setCreateDate(reportModel.getCreateDate());
		reportView.setAluBottleCount(reportModel.getAluBottleCount());
		reportView.setSmallPatBottleCount(reportModel.getSmallPatBottleCount());
		reportView.setPatBottleCount(reportModel.getPatBottleCount());
		reportView.setBigPatBottleCount(reportModel.getBigPatBottleCount());
		reportView.setSmallGlassBottleCount(reportModel.getSmallGlassBottleCount());
		reportView.setGlassBottleCount(reportModel.getGlassBottleCount());
		reportView.setBigGlassBottleCount(reportModel.getBigGlassBottleCount());
		reportView.setTotalBottleCount(reportModel.getTotalBottleCount());
		reportView.setTotalValue(reportModel.getTotalValue());
		return reportView;
	}

	@Override
	protected void checkInactive(ReportModel reportModel) throws EndlosAPIException {
		// do nothing
	}

	@Override
	public Response doExport(ReportView reportView, Integer orderType, Integer orderParam) throws EndlosAPIException {
		List<ReportModel> reportModels = reportService.doExport(reportView, orderType, orderParam);
		if (reportModels == null || reportModels.isEmpty()) {
			return CommonResponse.create(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
//		MachineModel machineModel = machineService.get(reportView.getMachineView().getId());
//		if (machineModel == null) {
//			return CommonResponse.create(ResponseCode.MACHINE_IS_INVALID.getCode(),
//					ResponseCode.MACHINE_IS_INVALID.getMessage());
//		}
		String newFileName = "Report_" + DateUtility.getCurrentEpoch() + ".xlsx";

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getDefault());
		String filepath = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			String sheetname = "Report";

			int dataStartingFrom = 0;

			Sheet sheet = workbook.createSheet(sheetname);
			if(reportView.getStartDate() !=null && reportView.getEndDate() !=null) {

				Row rowFilterData1 = sheet.createRow((short) dataStartingFrom);
				rowFilterData1.createCell((short) 0).setCellValue("Date");
				rowFilterData1.createCell((short) 1).setCellValue(String.valueOf(format.format(reportView.getStartDate() * 1000L)) + " - " + String.valueOf(format.format(reportView.getEndDate() * 1000L)));
				dataStartingFrom++;
			}
			if (reportView.getCustomerView() !=null) {

				Row rowFilterData2 = sheet.createRow((short) dataStartingFrom);
				rowFilterData2.createCell((short) 0).setCellValue("Customer Name");
				rowFilterData2.createCell((short) 1).setCellValue(reportModels.get(0).getLocationModel().getCustomerModel().getName());
				dataStartingFrom++;

			} if (reportView.getCustomerView() !=null && reportView.getLocationView() !=null) {

				Row rowFilterData3 = sheet.createRow((short) dataStartingFrom);
				rowFilterData3.createCell((short) 0).setCellValue("Branch Name");
				rowFilterData3.createCell((short) 1).setCellValue(reportModels.get(0).getLocationModel().getName());
				dataStartingFrom++;
			}
			if (reportView.getMachineView() !=null) {

				Row rowFilterData4 = sheet.createRow((short) dataStartingFrom);
				rowFilterData4.createCell((short) 0).setCellValue("Machine Id");
				rowFilterData4.createCell((short) 1).setCellValue(reportModels.get(0).getMachineModel().getMachineId());
				dataStartingFrom++;
			}

			if(dataStartingFrom > 0) {
				dataStartingFrom++;
			}
			Row rowhead = sheet.createRow((short) dataStartingFrom);
			rowhead.createCell((short) 0).setCellValue("S. No.");
			rowhead.createCell((short) 1).setCellValue("Customer Name");
			rowhead.createCell((short) 2).setCellValue("Branch Name");
			rowhead.createCell((short) 3).setCellValue("Branch Number");
			rowhead.createCell((short) 4).setCellValue("Machine Id");
			rowhead.createCell((short) 5).setCellValue("Date");

			rowhead.createCell((short) 6).setCellValue("Plastic Upto 1.5 L");
			rowhead.createCell((short) 7).setCellValue("Plastic 1.5 L& Above");
			rowhead.createCell((short) 8).setCellValue("Total Plastic");
			rowhead.createCell((short) 9).setCellValue("Glass Upto 1.5 L");
			rowhead.createCell((short) 10).setCellValue("Glass 1.5 L& Above");
			rowhead.createCell((short) 11).setCellValue("Total Glass");
			rowhead.createCell((short) 12).setCellValue("Total Aluminium");
			rowhead.createCell((short) 13).setCellValue("Total");
			rowhead.createCell((short) 14).setCellValue("Amount");

			Long totalPatBottleCount = 0l;
			Long totalSmallPatBottleCount = 0l;
			Long totalBigPatBottleCount = 0l;
			Long totalAluBottleCount = 0l;
			Long totalGlassBottleCount = 0l;
			Long totalSmallGlassBottleCount = 0l;
			Long totalBigGlassBottleCount = 0l;
			Long allTotalBottleCount = 0l;
			BigDecimal allTotalValue = new BigDecimal(0);

			for (ReportModel reportModel : reportModels) {
				totalPatBottleCount = totalPatBottleCount + reportModel.getPatBottleCount();
				totalSmallPatBottleCount = totalSmallPatBottleCount + reportModel.getSmallPatBottleCount();
				totalBigPatBottleCount = totalBigPatBottleCount + reportModel.getBigPatBottleCount();
				totalAluBottleCount = totalAluBottleCount + reportModel.getAluBottleCount();
				totalGlassBottleCount = totalGlassBottleCount + reportModel.getGlassBottleCount();
				totalSmallGlassBottleCount = totalSmallGlassBottleCount + reportModel.getSmallGlassBottleCount();
				totalBigGlassBottleCount = totalBigGlassBottleCount + reportModel.getBigGlassBottleCount();
				allTotalBottleCount = allTotalBottleCount + reportModel.getTotalBottleCount();
				allTotalValue = allTotalValue.add(reportModel.getTotalValue());
			}

			Row rowCount = sheet.createRow((short) dataStartingFrom+1);
			rowCount.createCell((short) 6).setCellValue(totalSmallPatBottleCount);
			rowCount.createCell((short) 7).setCellValue(totalBigPatBottleCount);
			rowCount.createCell((short) 8).setCellValue(totalPatBottleCount);
			rowCount.createCell((short) 9).setCellValue(totalSmallGlassBottleCount);
			rowCount.createCell((short) 10).setCellValue(totalBigGlassBottleCount);
			rowCount.createCell((short) 11).setCellValue(totalGlassBottleCount);
			rowCount.createCell((short) 12).setCellValue(totalAluBottleCount);
			rowCount.createCell((short) 13).setCellValue(allTotalBottleCount);
			rowCount.createCell((short) 14).setCellValue(allTotalValue.toString());

			int i = dataStartingFrom+1;
			int j=0;
			for (ReportModel reportModel : reportModels) {

				i++; j++;
				Row row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue((Integer) j);
				row.createCell((short) 1).setCellValue(reportModel.getLocationModel().getCustomerModel().getName());
				row.createCell((short) 2).setCellValue(reportModel.getLocationModel().getName());
				row.createCell((short) 3).setCellValue(reportModel.getLocationModel().getBranchNumber());
				row.createCell((short) 4).setCellValue(reportModel.getMachineModel().getMachineId());
				row.createCell((short) 4).setCellValue(reportModel.getMachineModel().getMachineId());
				row.createCell((short) 5)
						.setCellValue(String.valueOf(format.format(reportModel.getCreateDate() * 1000L)));
				row.createCell((short) 6).setCellValue(reportModel.getSmallPatBottleCount());
				row.createCell((short) 7).setCellValue(reportModel.getBigPatBottleCount());
				row.createCell((short) 8).setCellValue(reportModel.getPatBottleCount());
				row.createCell((short) 9).setCellValue(reportModel.getSmallGlassBottleCount());
				row.createCell((short) 10).setCellValue(reportModel.getBigGlassBottleCount());
				row.createCell((short) 11).setCellValue(reportModel.getGlassBottleCount());
				row.createCell((short) 12).setCellValue(reportModel.getAluBottleCount());
				row.createCell((short) 13).setCellValue(reportModel.getTotalBottleCount());
				row.createCell((short) 14).setCellValue(reportModel.getTotalValue().toString());

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
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), "Report export completed successfully.", fileOperation.fromModel(fileModel));
	}

	@Override
	public Response doCalculateBinCount(ReportView reportView) {
		List<ReportModel> reportModels = reportService.doCalculateBinCount(reportView);
		if (reportModels == null || reportModels.isEmpty()) {
			return PageResultResponse.create(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage(), 0, Collections.EMPTY_LIST);
		}
		Long totalPatBottleCount = 0l;
		Long totalSmallPatBottleCount = 0l;
		Long totalBigPatBottleCount = 0l;
		Long totalAluBottleCount = 0l;
		Long totalGlassBottleCount = 0l;
		Long totalSmallGlassBottleCount = 0l;
		Long totalBigGlassBottleCount = 0l;
		Long allTotalBottleCount = 0l;
		BigDecimal allTotalValue = new BigDecimal(0);

		for (ReportModel reportModel : reportModels) {
			totalPatBottleCount = totalPatBottleCount + reportModel.getPatBottleCount();
			totalSmallPatBottleCount = totalSmallPatBottleCount + reportModel.getSmallPatBottleCount();
			totalBigPatBottleCount = totalBigPatBottleCount + reportModel.getBigPatBottleCount();
			totalAluBottleCount = totalAluBottleCount + reportModel.getAluBottleCount();
			totalGlassBottleCount = totalGlassBottleCount + reportModel.getGlassBottleCount();
			totalSmallGlassBottleCount = totalSmallGlassBottleCount + reportModel.getSmallGlassBottleCount();
			totalBigGlassBottleCount = totalBigGlassBottleCount + reportModel.getBigGlassBottleCount();
			allTotalBottleCount = allTotalBottleCount + reportModel.getTotalBottleCount();
			allTotalValue = allTotalValue.add(reportModel.getTotalValue());
		}
		ReportView reportView2 = new ReportView();
		reportView2.setTotalPatBottleCount(totalPatBottleCount);
		reportView2.setTotalSmallPatBottleCount(totalSmallPatBottleCount);
		reportView2.setTotalBigPatBottleCount(totalBigPatBottleCount);
		reportView2.setTotalAluBottleCount(totalAluBottleCount);
		reportView2.setTotalGlassBottleCount(totalGlassBottleCount);
		reportView2.setTotalSmallGlassBottleCount(totalSmallGlassBottleCount);
		reportView2.setTotalBigGlassBottleCount(totalBigGlassBottleCount);
		reportView2.setAllTotalBottleCount(allTotalBottleCount);
		reportView2.setAllTotalValue(allTotalValue);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				reportView2);
	}

	@Override
	@Async
	public CompletableFuture<Response> doExportAsync(ReportView reportView, Integer orderType, Integer orderParam) {
		try {
			Response response = doExport(reportView, orderType, orderParam);
			return CompletableFuture.completedFuture(response);
		} catch (EndlosAPIException e) {
			return CompletableFuture.completedFuture(CommonResponse.create(e.getCode(), e.getMessage()));
		}
	}
}