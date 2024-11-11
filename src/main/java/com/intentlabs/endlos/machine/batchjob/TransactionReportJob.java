package com.intentlabs.endlos.machine.batchjob;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intentlabs.common.notification.thread.EmailThread;
import com.intentlabs.common.util.Constant;
import com.intentlabs.endlos.machine.enums.MaterialEnum;
import com.intentlabs.endlos.machine.model.ReportModel;
import com.intentlabs.endlos.machine.model.TransactionLogModel;
import com.intentlabs.endlos.machine.model.TransactionModel;
import com.intentlabs.endlos.machine.service.ReportService;
import com.intentlabs.endlos.machine.service.TransactionLogService;
import com.intentlabs.endlos.machine.service.TransactionService;

@DisallowConcurrentExecution
@Component
public class TransactionReportJob implements Job {

	@Autowired
	ReportService reportService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	TransactionLogService transactionLogService;

	@Autowired
	EmailThread emailThread;

	@Autowired
	ApplicationContext applicationContext;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		long startTime = System.currentTimeMillis();

		JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

		List<TransactionModel> transactionModels = transactionService.fetchTransaction();
		for (TransactionModel transactionModel : transactionModels) {
			if (transactionModel.getDateEnd() != null) {
				LocalDate date = Instant.ofEpochMilli(transactionModel.getDateEnd() * 1000)
						.atZone(ZoneId.systemDefault()).toLocalDate();
				Long startEpoch = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toEpochSecond();
				ReportModel reportModel = reportService.getRecords(startEpoch,
						transactionModel.getLocationModel().getId(), transactionModel.getMachineModel().getId());
				if (reportModel == null) {
					reportModel = new ReportModel();
					reportModel.setLocationModel(transactionModel.getLocationModel());
					reportModel.setMachineModel(transactionModel.getMachineModel());
					reportModel.setAluBottleCount(transactionModel.getAluBottleCount());
					reportModel.setPatBottleCount(transactionModel.getPatBottleCount());
					reportModel.setGlassBottleCount(transactionModel.getGlassBottleCount());
					Long total = transactionModel.getPatBottleCount() + transactionModel.getAluBottleCount()
							+ transactionModel.getGlassBottleCount();

					List<TransactionLogModel> transactionLogModels = transactionLogService
							.getByTransactionId(transactionModel.getId());
					int bigPatBottle = 0;
					int smallPatBottle = 0;

					int bigGlassBottle = 0;
					int smallGlassBottle = 0;
					if (transactionLogModels != null) {
						for (TransactionLogModel transactionLogModel : transactionLogModels) {
							if (transactionLogModel.getMaterial() != null && (transactionLogModel.getVolumn() != null
									&& transactionLogModel.getVolumn().compareTo(BigDecimal.ZERO) != 0)) {
								if (transactionLogModel.getStatus().equals(1)) {
									if (transactionLogModel.getMaterial().equals(MaterialEnum.PLASTIC)) {
										if (transactionLogModel.getVolumn().compareTo(new BigDecimal(1500)) >= 0) {
											bigPatBottle++;
										} else if (transactionLogModel.getVolumn()
												.compareTo(new BigDecimal(1500)) < 0) {
											smallPatBottle++;
										}
									}
									if (transactionLogModel.getMaterial().equals(MaterialEnum.GLASS)) {
										if (transactionLogModel.getVolumn().compareTo(new BigDecimal(1500)) >= 0) {
											bigGlassBottle++;
										} else if (transactionLogModel.getVolumn()
												.compareTo(new BigDecimal(1500)) < 0) {
											smallGlassBottle++;
										}
									}
								}
							}
						}
						reportModel.setSmallPatBottleCount(smallPatBottle);
						reportModel.setBigPatBottleCount(bigPatBottle);
						reportModel.setSmallGlassBottleCount(smallGlassBottle);
						reportModel.setBigGlassBottleCount(bigGlassBottle);
					}
					reportModel.setTotalBottleCount(total);
					reportModel.setTotalValue(transactionModel.getTotalValue());
					reportModel.setCreateDate(startEpoch);
					reportService.create(reportModel);
				} else {
					reportModel
							.setAluBottleCount(reportModel.getAluBottleCount() + transactionModel.getAluBottleCount());
					reportModel
							.setPatBottleCount(reportModel.getPatBottleCount() + transactionModel.getPatBottleCount());
					reportModel.setGlassBottleCount(
							reportModel.getGlassBottleCount() + transactionModel.getGlassBottleCount());
					Long total = transactionModel.getPatBottleCount() + transactionModel.getAluBottleCount()
							+ transactionModel.getGlassBottleCount();
					reportModel.setTotalBottleCount(reportModel.getTotalBottleCount() + total);
					reportModel.setTotalValue(reportModel.getTotalValue().add(transactionModel.getTotalValue()));

					List<TransactionLogModel> transactionLogModels = transactionLogService
							.getByTransactionId(transactionModel.getId());
					int bigPatBottle = 0;
					int smallPatBottle = 0;

					int bigGlassBottle = 0;
					int smallGlassBottle = 0;
					if (transactionLogModels != null) {
						for (TransactionLogModel transactionLogModel : transactionLogModels) {
							if (transactionLogModel.getMaterial() != null && (transactionLogModel.getVolumn() != null
									&& transactionLogModel.getVolumn().compareTo(BigDecimal.ZERO) != 0)) {
								if (transactionLogModel.getStatus().equals(1)) {
									if (transactionLogModel.getMaterial().equals(MaterialEnum.PLASTIC)) {
										if (transactionLogModel.getVolumn().compareTo(new BigDecimal(1500)) >= 0) {
											bigPatBottle++;
										} else if (transactionLogModel.getVolumn()
												.compareTo(new BigDecimal(1500)) < 0) {
											smallPatBottle++;
										}
									}
									if (transactionLogModel.getMaterial().equals(MaterialEnum.GLASS)) {
										if (transactionLogModel.getVolumn().compareTo(new BigDecimal(1500)) >= 0) {
											bigGlassBottle++;
										} else if (transactionLogModel.getVolumn()
												.compareTo(new BigDecimal(1500)) < 0) {
											smallGlassBottle++;
										}
									}
								}
							}
						}
						reportModel.setSmallPatBottleCount(reportModel.getSmallPatBottleCount() + smallPatBottle);
						reportModel.setBigPatBottleCount(reportModel.getBigPatBottleCount() + bigPatBottle);
						reportModel.setSmallGlassBottleCount(reportModel.getSmallGlassBottleCount() + smallGlassBottle);
						reportModel.setBigGlassBottleCount(reportModel.getBigGlassBottleCount() + bigGlassBottle);
					}
					reportService.update(reportModel);
				}
			}
			transactionModel.setFetch(true);
			transactionService.update(transactionModel);
		}
		jobDataMap.put(Constant.TOTAL_TIME_TAKEN_BY_JOB, System.currentTimeMillis() - startTime);
		return;
	}
}
