package com.demo.service;

import java.util.List;

import com.demo.dto.DrawResponse;
import com.demo.dto.UserRequest;
import com.demo.dto.UserResponse;

public interface UserService {

	/**
	 * Create a user
	 * 
	 * @param userRequest The {@link UserRequest}
	 * @return {@link UserResponse}
	 */
	UserResponse createUser(UserRequest userRequest);

	/**
	 * Get draws by userId
	 * 
	 * @param userId The userId
	 * @return List of {@link DrawResponse}
	 */
	List<DrawResponse> getUserDraws(String userId);

}
