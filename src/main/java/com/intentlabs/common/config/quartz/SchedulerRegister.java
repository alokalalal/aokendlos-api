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
package com.intentlabs.common.config.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.intentlabs.common.notification.batchjob.TransactionalEmailJob;
import com.intentlabs.endlos.machine.batchjob.TransactionReportJob;

/**
 * This class configures quartz scheduler properties.
 * 
 * @author nirav
 *
 */
@Component
@Configuration
public class SchedulerRegister implements ApplicationRunner {

	@Value("${send.communication}")
	private boolean sendCommunication;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	public JobDetail transactionEmailJob() {
		JobKey jobKey = new JobKey("TransactionEmail", "endlos-api");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "TransactionEmail");
		return JobBuilder.newJob(TransactionalEmailJob.class).withIdentity(jobKey).setJobData(jobDataMap).build();
	}

	public Trigger transactionEmailCronTrigger() {
		return TriggerBuilder.newTrigger().withIdentity("TransactionEmail", "endlos-api")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
	}

	public JobDetail transactionReportJob() {
		JobKey jobKey = new JobKey("TransactionReport", "endlos-api");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "TransactionReport");
		return JobBuilder.newJob(TransactionReportJob.class).withIdentity(jobKey).setJobData(jobDataMap).build();
	}

	public Trigger transactionReportCronTrigger() {
		return TriggerBuilder.newTrigger().withIdentity("TransactionReport", "endlos-api")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.getListenerManager().addJobListener(new SchedulerJobListener());
		if (sendCommunication) {
			JobDetail transactionEmailJobDetail = transactionEmailJob();
			Trigger transactionEmailTrigger = transactionEmailCronTrigger();
			scheduler.scheduleJob(transactionEmailJobDetail, transactionEmailTrigger);
		}
		JobDetail transactionReportJobDetail = transactionReportJob();
		Trigger transactionReportTrigger = transactionReportCronTrigger();
		scheduler.scheduleJob(transactionReportJobDetail, transactionReportTrigger);
	}
}