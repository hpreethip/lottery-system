package com.demo.service;

import com.demo.dto.TicketRequest;

public interface TicketService {

	/**
	 * Buy a ticket by the request payload
	 * 
	 * @param ticketRequest The {@link TicketRequest}
	 * @return String message
	 */
	String buyTicket(TicketRequest ticketRequest);

}
