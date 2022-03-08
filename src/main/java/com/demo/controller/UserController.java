package com.demo.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.constant.RequestUri;
import com.demo.dto.DrawResponse;
import com.demo.dto.UserRequest;
import com.demo.dto.UserResponse;
import com.demo.exception.BadRequestException;
import com.demo.service.UserService;

@RestController
@RequestMapping(RequestUri.USER)
public class UserController {

	/**
	 * Logger to log messages of {@link UserController}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * Autowired bean of {@link UserService}
	 */
	@Autowired
	private UserService userService;

	/**
	 * RestAPI to handle create User
	 * 
	 * @param userRequest The object of {@link UserRequest}
	 * @return The response of create User
	 * @throws Exception
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> handleCreateUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
		try {
			UserResponse response = null;
			if (userRequest instanceof UserRequest) {
				/* Call to Create User with the input request */
				response = userService.createUser(userRequest);
				LOGGER.info("The result of User response is : {}", response);
				if (Objects.nonNull(response))
					return new ResponseEntity<>(response, HttpStatus.CREATED);
				else
					throw new BadRequestException("Failed to create user");
			} else
				throw new BadRequestException("Input request is invalid");
		} catch (BadRequestException ex) {
			LOGGER.error("BadRequestException in create due to {}", ex.getMessage());
			throw new BadRequestException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Exception in create user due to {}", ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * RestAPI to handle get list of Draws
	 * 
	 * @param drawRequest The object of {@link DrawRequest}
	 * @return The response of get Draw
	 * @throws Exception
	 */
	@GetMapping(value = RequestUri.DRAWS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DrawResponse>> handleUserDraws(@RequestParam String userId) throws Exception {
		try {
			List<DrawResponse> response = null;
			/* Call to get Draws */
			if (userId instanceof String) {
				response = userService.getUserDraws(userId);
				LOGGER.info("The result of Draw response is : {}", response);
				if (Objects.nonNull(response))
					return new ResponseEntity<>(response, HttpStatus.OK);
				else
					throw new BadRequestException("Failed to get draws details");
			} else {
				throw new BadRequestException("Input request is invalid");
			}
		} catch (BadRequestException ex) {
			LOGGER.error("BadRequestException in get draws due to {}", ex.getMessage());
			throw new BadRequestException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Exception in get draw due to {}", ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

}
