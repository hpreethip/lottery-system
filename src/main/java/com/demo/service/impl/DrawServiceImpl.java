package com.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.constant.DrawStatus;
import com.demo.dto.DrawResponse;
import com.demo.exception.BadRequestException;
import com.demo.model.Draw;
import com.demo.repository.DrawRepository;
import com.demo.service.DrawService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DrawServiceImpl implements DrawService {

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
	public DrawResponse getDraw(String drawId) {
		Draw draw = null;
		/* Finds the draw by drawId */
		Optional<Draw> optional = drawRepository.findById(drawId);
		if (optional.isPresent())
			/* Gets the draw entity */
			draw = optional.get();
		else
			throw new BadRequestException("The drawId in the request is not valid");
		/* Converts the draw entity to DTO */
		return objectMapper.convertValue(draw, DrawResponse.class);
	}

	@Override
	public List<DrawResponse> getDraws(String status) {
		/* Checks the valid status is contains in the enum */
		if (!DrawStatus.contains(status)) {
			throw new BadRequestException("The draw status in the request is not valid");
		}
		List<DrawResponse> drawResponse = null;
		/* Finds the draws by Status that sorts based the startDate in descending order */
		List<Draw> draws = drawRepository.findAllByStatusOrderByStartDateDesc(status);
		/* Converts the draw entities to DTOs */
		drawResponse = objectMapper.convertValue(draws, new TypeReference<List<DrawResponse>>() {});
		if (Objects.nonNull(drawResponse) && !drawResponse.isEmpty())
			return drawResponse;
		else
			throw new BadRequestException("Could not find any " + status + " draws");
	}

}
