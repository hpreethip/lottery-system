package com.demo.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.constant.RequestUri;
import com.demo.dto.DrawResponse;
import com.demo.exception.BadRequestException;
import com.demo.service.DrawService;

@RestController
public class DrawController {

	/**
	 * Logger to log messages of {@link DrawController}
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DrawController.class);

	/**
	 * Autowired bean of {@link DrawService}
	 */
	@Autowired
	private DrawService drawService;

	/**
	 * RestAPI to handle get Draw by Id
	 * 
	 * @param drawRequest The object of {@link DrawRequest}
	 * @return The response of get Draw
	 * @throws Exception
	 */
	@GetMapping(value = RequestUri.DRAW, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DrawResponse> handleGetDrawById(@RequestParam String drawId) throws Exception {
		try {
			DrawResponse response = null;
			if (drawId instanceof String) {
				/* Call to get Draw with the input request */
				response = drawService.getDraw(drawId);
				LOGGER.info("The result of Draw response is : {}", response);
				if (Objects.nonNull(response))
					return new ResponseEntity<>(response, HttpStatus.OK);
				else
					throw new BadRequestException("Failed to get draw details");
			} else
				throw new BadRequestException("Input request is invalid");
		} catch (BadRequestException ex) {
			LOGGER.error("BadRequestException in get draw due to {}", ex.getMessage());
			throw new BadRequestException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Exception in get draw due to {}", ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * RestAPI to handle get list of Draws by status
	 * 
	 * @param status The value of status
	 * @return The response of Draws
	 * @throws Exception
	 */
	@GetMapping(value = RequestUri.DRAWS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DrawResponse>> handleGetDraws(@RequestParam String status) throws Exception {
		try {
			List<DrawResponse> response = null;
			if (status instanceof String) {
				/* Call to get Draws */
				response = drawService.getDraws(status);
				LOGGER.info("The result of Draw response is : {}", response);
				if (Objects.nonNull(response))
					return new ResponseEntity<>(response, HttpStatus.OK);
				else
					throw new BadRequestException("Failed to get draws details");
			}
			else
				throw new BadRequestException("Input request is invalid");
		} catch (BadRequestException ex) {
			LOGGER.error("BadRequestException in get draws due to {}", ex.getMessage());
			throw new BadRequestException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("Exception in get draw due to {}", ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

}
