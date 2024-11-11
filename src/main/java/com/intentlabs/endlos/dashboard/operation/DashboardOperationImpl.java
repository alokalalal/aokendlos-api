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
package com.intentlabs.endlos.dashboard.operation;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.endlos.dashboard.view.DashboardView;
import com.intentlabs.endlos.machine.enums.MachineActivityStatusEnum;
import com.intentlabs.endlos.machine.enums.MachineBinFullStatusEnum;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;
import com.intentlabs.endlos.machine.service.TransactionService;
import com.intentlabs.endlos.machine.view.MachineView;
import com.intentlabs.endlos.machine.view.TransactionSummary;
import com.intentlabs.endlos.machine.view.TransactionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 27/10/2021
 */
@Component(value = "dashboardOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DashboardOperationImpl implements DashboardOperation {

	@Autowired
	MachineService machineService;

	@Autowired
	TransactionService transactionService;

//	@Override
//	public Response doGetCounters(MachineView machineView) throws EndlosAPIException {
//		List<MachineModel> machineModels = machineService.findAllMachine(machineView);
//		BigDecimal totalValue = new BigDecimal(0);
//		Long totalBottle = 0l;
//		Long totalVoucherBarcode = 0l;
//		Long totalPatBottleCount = 0l;
//		BigDecimal totalPatBottleValue = new BigDecimal(0);
//		Long totalGlassBottleCount = 0l;
//		BigDecimal totalGlassBottleValue = new BigDecimal(0);
//		Long totalAluBottleCount = 0l;
//		BigDecimal totalAluBottleValue = new BigDecimal(0);
//		for (MachineModel machineModel : machineModels) {
//			TransactionView transactionView = new TransactionView();
//			if (machineView.getStartDate() != null) {
//				transactionView.setStartDate(machineView.getStartDate());
//				transactionView.setEndDate(machineView.getEndDate());
//			}
//			machineView.setId(machineModel.getId());
//			machineView.setMachineId(machineModel.getMachineId());
//			transactionView.setMachineView(machineView);
//
//			List<TransactionModel> transactionModels = transactionService.findAllTransaction(transactionView);
//			for (TransactionModel transactionModel : transactionModels) {
//				totalPatBottleCount = totalPatBottleCount + transactionModel.getPatBottleCount();
//				totalAluBottleCount = totalAluBottleCount + transactionModel.getAluBottleCount();
//				totalGlassBottleCount = totalGlassBottleCount + transactionModel.getGlassBottleCount();
//
//				totalPatBottleValue = totalPatBottleValue.add(transactionModel.getPatBottleValue());
//				totalAluBottleValue = totalAluBottleValue.add(transactionModel.getAluBottleValue());
//				totalGlassBottleValue = totalGlassBottleValue.add(transactionModel.getGlassBottleValue());
//
//				totalValue = totalValue.add(transactionModel.getTotalValue());
//				totalBottle = totalBottle + transactionModel.getAluBottleCount() + transactionModel.getPatBottleCount()
//						+ transactionModel.getGlassBottleCount();
//				if (transactionModel.getBarcode() != null
//						&& transactionModel.getTotalValue().compareTo(BigDecimal.ZERO) > 0) {
//					totalVoucherBarcode++;
//				}
//			}
//		}
//		DashboardView dashboardView = new DashboardView();
//		dashboardView.setTotalValue(totalValue.toString());
//		dashboardView.setTotalBottle(totalBottle.toString());
//		dashboardView.setVoucherBarcode(totalVoucherBarcode.toString());
//		dashboardView.setTotalPatBottleCount(totalPatBottleCount);
//		dashboardView.setTotalPatBottleValue(totalPatBottleValue);
//		dashboardView.setTotalGlassBottleCount(totalGlassBottleCount);
//		dashboardView.setTotalGlassBottleValue(totalGlassBottleValue);
//		dashboardView.setTotalAluBottleCount(totalAluBottleCount);
//		dashboardView.setTotalAluBottleValue(totalAluBottleValue);
//		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
//				dashboardView);
//	}
	@Override
	public Response doGetCounters(MachineView machineView) throws EndlosAPIException {
		List<MachineModel> machineModels = machineService.findAllMachine(machineView);
		BigDecimal totalValue = BigDecimal.ZERO;
		long totalBottle = 0;
		long totalVoucherBarcode = 0;
		long totalPatBottleCount = 0;
		BigDecimal totalPatBottleValue = BigDecimal.ZERO;
		long totalGlassBottleCount = 0;
		BigDecimal totalGlassBottleValue = BigDecimal.ZERO;
		long totalAluBottleCount = 0;
		BigDecimal totalAluBottleValue = BigDecimal.ZERO;

		for (MachineModel machineModel : machineModels) {
			TransactionView transactionView = new TransactionView();
			if (machineView.getStartDate() != null) {
				transactionView.setStartDate(machineView.getStartDate());
				transactionView.setEndDate(machineView.getEndDate());
			}
			machineView.setId(machineModel.getId());
			machineView.setMachineId(machineModel.getMachineId());
			transactionView.setMachineView(machineView);

			TransactionSummary transactionSummary = transactionService.findAllTransaction(transactionView);
			if (transactionSummary != null) {
				if (transactionSummary.getTotalPatBottleCount() != null) {
					totalPatBottleCount += transactionSummary.getTotalPatBottleCount();
				}
				if (transactionSummary.getTotalAluBottleCount() != null) {
					totalAluBottleCount += transactionSummary.getTotalAluBottleCount();
				}
				if (transactionSummary.getTotalGlassBottleCount() != null) {
					totalGlassBottleCount += transactionSummary.getTotalGlassBottleCount();
				}
				if (transactionSummary.getTotalPatBottleValue() != null) {
					totalPatBottleValue = totalPatBottleValue.add(transactionSummary.getTotalPatBottleValue());
				}
				if (transactionSummary.getTotalAluBottleValue() != null) {
					totalAluBottleValue = totalAluBottleValue.add(transactionSummary.getTotalAluBottleValue());
				}
				if (transactionSummary.getTotalGlassBottleValue() != null) {
					totalGlassBottleValue = totalGlassBottleValue.add(transactionSummary.getTotalGlassBottleValue());
				}
				if (transactionSummary.getTotalValue() != null) {
					totalValue = totalValue.add(transactionSummary.getTotalValue());
				}
				if (transactionSummary.getTotalBottle() != null) {
					totalBottle += transactionSummary.getTotalBottle();
				}
				if (transactionSummary.getTotalValue() != null
						&& transactionSummary.getTotalValue().compareTo(BigDecimal.ZERO) > 0) {
					totalVoucherBarcode += transactionSummary.getTotalVoucher();
					//totalVoucherBarcode++;
				}
			}
//			for (TransactionModel transactionModel : transactionModels) {
//				totalPatBottleCount += transactionModel.getPatBottleCount();
//				totalAluBottleCount += transactionModel.getAluBottleCount();
//				totalGlassBottleCount += transactionModel.getGlassBottleCount();
//
//				totalPatBottleValue = totalPatBottleValue.add(transactionModel.getPatBottleValue());
//				totalAluBottleValue = totalAluBottleValue.add(transactionModel.getAluBottleValue());
//				totalGlassBottleValue = totalGlassBottleValue.add(transactionModel.getGlassBottleValue());
//
//				totalValue = totalValue.add(transactionModel.getTotalValue());
//				totalBottle += transactionModel.getAluBottleCount() + transactionModel.getPatBottleCount()
//						+ transactionModel.getGlassBottleCount();
//				if (transactionModel.getBarcode() != null
//						&& transactionModel.getTotalValue().compareTo(BigDecimal.ZERO) > 0) {
//					totalVoucherBarcode++;
//				}
//			}

		}
		DashboardView dashboardView = new DashboardView();
		dashboardView.setTotalValue(totalValue.toString());
		dashboardView.setTotalBottle(Long.toString(totalBottle));
		dashboardView.setVoucherBarcode(Long.toString(totalVoucherBarcode));
		dashboardView.setTotalPatBottleCount(totalPatBottleCount);
		dashboardView.setTotalPatBottleValue(totalPatBottleValue);
		dashboardView.setTotalGlassBottleCount(totalGlassBottleCount);
		dashboardView.setTotalGlassBottleValue(totalGlassBottleValue);
		dashboardView.setTotalAluBottleCount(totalAluBottleCount);
		dashboardView.setTotalAluBottleValue(totalAluBottleValue);

		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				dashboardView);
	}

	@Override
	public Response doGetMachineStatus(MachineView machineView) throws EndlosAPIException {
		List<MachineModel> machineModels = machineService.findAllMachine(machineView);
//		Long totalGoodMachine = 0l;
//		Long totalWarningMachine = 0l;
//		Long totalErrorMachine = 0l;
//		int totalMachine = 0;
//
//		for (MachineModel machineModel : machineModels) {
//			if (machineModel.getMachineActivityStatus() != null) {
//				if (machineModel.getMachineActivityStatus().equals(MachineActivityStatusEnum.READY)) {
//					totalGoodMachine++;
//				}
//				if (machineModel.getMachineActivityStatus().equals(MachineActivityStatusEnum.WARNING)) {
//					totalWarningMachine++;
//				}
//				if (machineModel.getMachineActivityStatus().equals(MachineActivityStatusEnum.ERROR)) {
//					totalErrorMachine++;
//				}
//			}
//			totalMachine = machineModels.size();
//		}
		Map<MachineActivityStatusEnum, Long> statusCounts = machineModels.stream()
				.map(MachineModel::getMachineActivityStatus).filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		// Get counts for each status or default to 0
		Long totalGoodMachine = statusCounts.getOrDefault(MachineActivityStatusEnum.READY, 0L);
		Long totalWarningMachine = statusCounts.getOrDefault(MachineActivityStatusEnum.WARNING, 0L);
		Long totalErrorMachine = statusCounts.getOrDefault(MachineActivityStatusEnum.ERROR, 0L);

		// Total machine count
		int totalMachine = machineModels.size();
		DashboardView dashboardView = new DashboardView();
		dashboardView.setTotalMachine(String.valueOf(totalMachine));
		dashboardView.setErrorMachine(totalErrorMachine.toString());
		dashboardView.setWarningMachine(totalWarningMachine.toString());
		dashboardView.setReadyMachine(totalGoodMachine.toString());
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				dashboardView);
	}

	@Override
	public Response doGetFullnessStatus(MachineView machineView) {
		List<MachineModel> machineModels = machineService.findAllMachine(machineView);
//		Long morethen90machine = 0l;
//		Long between60to90machine = 0l;
//		Long lessthen90machine = 0l;
//		int totalMachine = 0;
//
//		for (MachineModel machineModel : machineModels) {
//			if (machineModel.getBinFullStatus() != null) {
//				if (machineModel.getBinFullStatus().equals(MachineBinFullStatusEnum.ERROR)) {
//					morethen90machine++;
//				}
//				if (machineModel.getBinFullStatus().equals(MachineBinFullStatusEnum.WARNING)) {
//					between60to90machine++;
//				}
//				if (machineModel.getBinFullStatus().equals(MachineBinFullStatusEnum.READY)) {
//					lessthen90machine++;
//				}
//				totalMachine = machineModels.size();
//			}
//		}
		Map<MachineBinFullStatusEnum, Long> statusCounts = machineModels.stream().map(MachineModel::getBinFullStatus)
				.filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		// Get counts for each status or default to 0
		Long morethen90machine = statusCounts.getOrDefault(MachineBinFullStatusEnum.ERROR, 0L);
		Long between60to90machine = statusCounts.getOrDefault(MachineBinFullStatusEnum.WARNING, 0L);
		Long lessthen90machine = statusCounts.getOrDefault(MachineBinFullStatusEnum.READY, 0L);

		// Total machine count
		int totalMachine = machineModels.size();
		DashboardView dashboardView = new DashboardView();
		dashboardView.setTotalMachine(String.valueOf(totalMachine));
		dashboardView.setMorethen90machine(morethen90machine.toString());
		dashboardView.setBetween60to90machine(between60to90machine.toString());
		dashboardView.setLessthen90machine(lessthen90machine.toString());
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				dashboardView);
	}
}