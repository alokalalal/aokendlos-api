package com.intentlabs.common.config.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.intentlabs.common.logger.LoggerService;

public class SchedulerJobListener implements JobListener {

	public static final String SCHEDULER_JOB_LISTENER = "SchedulerJobListener";

	@Override
	public String getName() {
		return SCHEDULER_JOB_LISTENER;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
		String jobName = jobExecutionContext.getJobDetail().getKey().toString();
		LoggerService.debug(SCHEDULER_JOB_LISTENER, "Job To Be Executed", jobName);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException jobException) {
		if (jobException != null) {
			LoggerService.exception(jobException);
			return;
		}
		String jobName = jobExecutionContext.getJobDetail().getKey().toString();

		LoggerService.debug(SCHEDULER_JOB_LISTENER, "Job Completed", jobName);
	}
}
