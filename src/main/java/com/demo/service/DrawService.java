package com.demo.service;

import java.util.List;

import com.demo.dto.DrawResponse;

public interface DrawService {

	/**
	 * Get draw by drawId
	 * 
	 * @param drawId The drawId from request
	 * @return {@link DrawResponse}
	 */
	DrawResponse getDraw(String drawId);

	/**
	 * Get draws by status
	 * 
	 * @param status The status from request
	 * @return List of {@link DrawResponse}
	 */
	List<DrawResponse> getDraws(String status);

}
