package com.intentlabs.common.notification.batchjob;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.intentlabs.common.config.threadpool.AsyncConfiguration;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.notification.model.EmailAccountModel;
import com.intentlabs.common.notification.model.TransactionalEmailModel;
import com.intentlabs.common.notification.service.EmailAccountService;
import com.intentlabs.common.notification.service.TransactionalEmailService;
import com.intentlabs.common.notification.thread.EmailThread;
import com.intentlabs.common.util.Constant;

@DisallowConcurrentExecution
@Component
public class TransactionalEmailJob implements Job {

	@Autowired
	TransactionalEmailService transactionalEmailService;

	@Autowired
	EmailAccountService emailAccountService;

	@Autowired
	EmailThread emailThread;

	@Autowired
	ApplicationContext applicationContext;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		long startTime = System.currentTimeMillis();

		JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext
				.getBean("transactionEmailExecutor");
		if (taskExecutor.getActiveCount() == taskExecutor.getMaxPoolSize() && taskExecutor.getThreadPoolExecutor()
				.getQueue().size() == new AsyncConfiguration().getTransactionEmailQueuesize()) {
			LoggerService.error("TransactionEmailJob", "Quartz scheduler", "Queue and pool is full.");
			return;
		}
		List<TransactionalEmailModel> transactionalEmailModelList = transactionalEmailService.getEmailList(10);
		for (TransactionalEmailModel transactionalEmailModel : transactionalEmailModelList) {
			EmailAccountModel emailAccountModel = emailAccountService.get(transactionalEmailModel.getEmailAccountId());
			emailThread.sendTransactionEmail(transactionalEmailModel, emailAccountModel);
		}
		jobDataMap.put("Records", transactionalEmailModelList.size());
		jobDataMap.put(Constant.TOTAL_TIME_TAKEN_BY_JOB, System.currentTimeMillis() - startTime);
		return;
	}

}
