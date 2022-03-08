package com.demo.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Thread Pool Task Executor is required for concurrency in Async Thread
 * running.
 * 
 * @author Hema Preethi
 *
 */
@Component
@Configuration
public class ThreadPoolTaskBeans {

	/**
	 * Async TaskExecutor Bean
	 * 
	 * @return ThreadPoolTaskExecutor
	 */
	@Bean(name = { "asyncTaskExecutor" })
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(40);
		executor.setQueueCapacity(500);
		executor.setKeepAliveSeconds(60);
		executor.setAllowCoreThreadTimeOut(false);
		executor.setThreadNamePrefix("EXECUTOR-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

	/**
	 * Scheduled Task Bean
	 * 
	 * @return ThreadPoolTaskScheduler
	 */
	@Bean(name = { "scheduledTask" })
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
		threadPoolTaskScheduler.setThreadNamePrefix("SCHEDULER-");
		threadPoolTaskScheduler.setAwaitTerminationSeconds(20);
		threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskScheduler.setThreadPriority(5);
		threadPoolTaskScheduler.initialize();
		return threadPoolTaskScheduler;
	}

}
