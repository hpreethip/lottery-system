package com.demo.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.DrawResponse;
import com.demo.dto.UserRequest;
import com.demo.dto.UserResponse;
import com.demo.exception.BadRequestException;
import com.demo.model.Draw;
import com.demo.model.Ticket;
import com.demo.model.User;
import com.demo.repository.DrawRepository;
import com.demo.repository.TicketRepository;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

	/**
	 * Logger to log messages of {@link UserServiceImpl}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * Autowired bean of {@link ObjectMapper}
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Autowired bean of {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepository;

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

	@Override
	public UserResponse createUser(UserRequest userRequest) {
		/* Converts the DTO to Entity of the User */
		User user = objectMapper.convertValue(userRequest, User.class);
		user.setUserId(UUID.randomUUID().toString());
		/* Saves the User */
		userRepository.save(user);
		/* Converts the Entity to DTO of the User */
		return objectMapper.convertValue(user, UserResponse.class);
	}

	@Override
	public List<DrawResponse> getUserDraws(String userId) {
		List<DrawResponse> drawResponse = null;
		/* Finds the tickets by userId */
		List<Ticket> tickets = ticketRepository.findAllByUserId(userId);
		if (!tickets.isEmpty()) {
			/* Gets the draws from the Tickets */
			List<String> drawIds = tickets.stream().map(Ticket::getDrawId).collect(Collectors.toList());
			LOGGER.info("The drawIds found for the user is: {}", drawIds);
			/* Finds the draws participated by the user from the drawIds */
			List<Draw> draws = drawRepository.findAllByDrawIdInOrderByStartDateDesc(drawIds);
			/* Converts the entities to DTOs of the Draws */
			drawResponse = objectMapper.convertValue(draws, new TypeReference<List<DrawResponse>>() {});
			return drawResponse;
		} else {
			throw new BadRequestException("User is not participated in any of the draws");
		}
	}

}
