package com.demo.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.constant.DrawStatus;
import com.demo.model.Draw;
import com.demo.repository.DrawRepository;

@Component
public class DrawJob implements Runnable {

	/**
	 * Logger to log messages of {@link DrawJob}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DrawJob.class);

	/**
	 * Autowired bean of {@link DrawRepository}
	 */
	@Autowired
	private DrawRepository drawRepository;

	/**
	 * Creating draws continuously and periodically every 60 seconds cron expression
	 * defined in properties file.
	 */
	@Override
	public void run() {
		LOGGER.info("Running DrawJob...");
		Draw draw = new Draw();
		draw.setDrawId(UUID.randomUUID().toString());
		draw.setName("Draw-" + draw.getDrawId());
		draw.setStatus(DrawStatus.ACTIVE.name());
		draw.setStartDate(LocalDateTime.now());
		draw.setEndDate(draw.getStartDate().plus(60, ChronoUnit.SECONDS));
		drawRepository.save(draw);
	}

}
