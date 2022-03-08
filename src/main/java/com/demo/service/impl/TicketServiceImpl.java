package com.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.constant.DrawStatus;
import com.demo.constant.TicketStatus;
import com.demo.dto.TicketRequest;
import com.demo.exception.BadRequestException;
import com.demo.model.Draw;
import com.demo.model.Ticket;
import com.demo.repository.DrawRepository;
import com.demo.repository.TicketRepository;
import com.demo.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TicketServiceImpl implements TicketService {

	/**
	 * Autowired bean of {@link TicketRepository}
	 */
	@Autowired
	private TicketRepository ticketRepository;
	
	/**
	 * Autowired bean of {@link DrawRepository}
	 */
	@Autowired
	private DrawRepository drawRepository;

	/**
	 * Autowired bean of {@link ObjectMapper}
	 */
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String buyTicket(TicketRequest ticketRequest) {
		/* Finds the draw by drawId from the request */
		Optional<Draw> drawOtional = drawRepository.findById(ticketRequest.getDrawId());
		if(drawOtional.isPresent()) {
			/* Checks if the user is participated in the draw */
			if(!drawOtional.get().getStatus().equals(DrawStatus.ACTIVE.name())) {
				throw new BadRequestException("Cannot participate as the draw is completed");
			}
		} else {
			throw new BadRequestException("Cannot participate in the draw as it's invalid");
		}
		/* Finds the tickets bu userId and status */
		Optional<Ticket> ticketOptional = ticketRepository.findByUserIdAndStatus(ticketRequest.getUserId(), TicketStatus.ACTIVE.name());
		if (!ticketOptional.isPresent()) {
			/* Generates the ticket with ACTIVE status */
			Ticket ticket = objectMapper.convertValue(ticketRequest, Ticket.class);
			ticket.setTicketId(UUID.randomUUID().toString());
			ticket.setStatus(TicketStatus.ACTIVE.name());
			ticket.setDate(LocalDateTime.now());
			ticketRepository.save(ticket);
			return "Participated successfully in the draw";
		} else {
			throw new BadRequestException("The user has already participated in another draw");
		}
	}

}
