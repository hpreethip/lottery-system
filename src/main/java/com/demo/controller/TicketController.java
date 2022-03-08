package com.demo.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.constant.RequestUri;
import com.demo.dto.TicketRequest;
import com.demo.exception.BadRequestException;
import com.demo.service.TicketService;

@RestController
@RequestMapping(RequestUri.TICKET)
public class TicketController {
	
	/**
	 * Logger to log messages of {@link TicketController}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

	/**
	 * Autowired bean of {@link TicketService}
	 */
	@Autowired
	private TicketService ticketService;
	
	/**
	 * RestAPI to handle buy Ticket
	 * 
	 * @param ticketRequest The object of {@link TicketRequest}
	 * @return The response of buy Ticket
	 * @throws Exception
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> handleBuyTicket(@RequestBody @Valid TicketRequest ticketRequest) throws Exception {
		try {
			String response = null;
			if (ticketRequest instanceof TicketRequest) {
				/* Call to Buy Ticket with the input request */
				response = ticketService.buyTicket(ticketRequest);
				LOGGER.info("The result of Ticket response is : {}", response);
				if (Objects.nonNull(response))
					return new ResponseEntity<>(response, HttpStatus.CREATED);
				else
					throw new BadRequestException("Failed to buy ticket");
			} else
				throw new BadRequestException("Input request is invalid");
		} catch (BadRequestException ex) {
			LOGGER.error("BadRequestException in buy ticket due to {}", ex.getMessage());
			throw new BadRequestException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Exception in buy ticket due to {}", ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

}
