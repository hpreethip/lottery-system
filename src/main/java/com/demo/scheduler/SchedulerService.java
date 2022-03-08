package com.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Scheduler Service class to run scheduled jobs.
 * </p>
 * 
 */
@Component
public class SchedulerService {

	/**
	 * Logger to log messages of {@link SchedulerService}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

	/**
	 * Autowired bean of {@link ThreadPoolTaskScheduler}
	 */
	@Autowired
	@Qualifier("scheduledTask")
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	/**
	 * Autowired bean of {@link ThreadPoolTaskExecutor}
	 */
	@Autowired
	@Qualifier("asyncTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * Autowired bean of {@link DrawJob}
	 */
	@Autowired
	private DrawJob drawJob;

	/**
	 * Autowired bean of {@link DrawWinnerJob}
	 */
	@Autowired
	private DrawWinnerJob drawWinnerJob;

	/**
	 * Draw Job Cron expression from property file
	 */
	@Value("${draw.job.cron.expression}")
	private String drawJobCronExpression;

	/**
	 * Draw Winner Job Cron expression from property file
	 */
	@Value("${draw.winner.job.cron.expression}")
	private String drawWinnerJobCronExpression;

	/**
	 * The Task Builder executes the master schedulers at given time based on the
	 * cron expression
	 */
	@Bean
	public void taskBuilder() {
		threadPoolTaskExecutor.execute(() -> {
			LOGGER.info("Inside the taskBuilder");
			/* Draw Job */
			threadPoolTaskScheduler.schedule(drawJob, new CronTrigger(drawJobCronExpression));
			/* Draw Winner Job */
			threadPoolTaskScheduler.schedule(drawWinnerJob, new CronTrigger(drawWinnerJobCronExpression));
		});
	}

}
