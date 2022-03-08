package com.demo.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.constant.DrawStatus;
import com.demo.constant.TicketStatus;
import com.demo.model.Draw;
import com.demo.model.Ticket;
import com.demo.repository.DrawRepository;
import com.demo.repository.TicketRepository;

@Component
public class DrawWinnerJob implements Runnable {

	/**
	 * Logger to log messages of {@link DrawWinnerJob}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DrawWinnerJob.class);

	/**
	 * Autowired bean of {@link DrawRepository}
	 */
	@Autowired
	private DrawRepository drawRepository;

	/**
	 * Autowired bean of {@link TicketRepository}
	 */
	@Autowired
	private TicketRepository ticketRepository;

	/**
	 * Picks 1 winner randomly from the active draw and the end date less than equal to
	 * current time runs every 20 seconds cron expression defined in properties file
	 */
	@Override
	public void run() {
		LOGGER.info("Running DrawWinnerJob...");
		/* Finds the Draws with less than equal to current time and Active status */
		List<Draw> draws = drawRepository.findAllByEndDateLessThanEqualAndStatus(LocalDateTime.now(),
				DrawStatus.ACTIVE.name());
		for (Draw draw : draws) {
			LOGGER.info("The draw is ready to find the winner for {}", draw);
			/* Finds the distinct tickets by drawId */
			List<Ticket> tickets = ticketRepository.findDistinctByDrawId(draw.getDrawId());
			if (!tickets.isEmpty()) {
				/* Contains unique ticketIds from the list */
				List<String> ticketIds = tickets.stream().map(Ticket::getTicketId).collect(Collectors.toList());
				LOGGER.info("The ticketIds found for the draw is: {}", ticketIds);
				/* Picks random ticketId from the list */
				String ticketId = ticketIds.get(new Random().nextInt(ticketIds.size()));
				/* Gets the userId in the Ticket entity */
				Ticket winnerTicket = tickets.stream().filter(t -> t.getTicketId().equals(ticketId)).findFirst().get();
				LOGGER.info("The winner ticketId for the draw is: {}", winnerTicket.getUserId());
				draw.setWinnerUserId(winnerTicket.getUserId());

				/* Discarding the tickets by setting the status as INACTIVE */
				for (Ticket ticket : tickets) {
					ticket.setStatus(TicketStatus.INACTIVE.name());
					ticketRepository.save(ticket);
				}
			} else {
				LOGGER.info("No tickets found for the draw");
			}
			/* The draws are completed by setting the status */
			draw.setStatus(DrawStatus.COMPLETED.name());
			drawRepository.save(draw);
		}
	}

}
