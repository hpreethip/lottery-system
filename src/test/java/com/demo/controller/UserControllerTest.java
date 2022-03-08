package com.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.constant.RequestUri;
import com.demo.dto.UserRequest;
import com.demo.dto.UserResponse;
import com.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Mock bean of {@link UserService}
	 */
	@MockBean
	private UserService userService;

	@Test
	void createPizzaPositiveCase() throws Exception {
		System.out.println("Create Pizza order Positive test case");
		UserRequest userRequest = new UserRequest();
		userRequest.setUserName("panda");
		userRequest.setEmail("panda@gmail.com");
		String requestBody = objectMapper.writeValueAsString(userRequest);

		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(UUID.randomUUID().toString());
		userResponse.setUserName("panda");
		
		when(userService.createUser(ArgumentMatchers.any(UserRequest.class)))
				.thenReturn(userResponse);
		
    	mockMvc.perform(post(RequestUri.USER)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
        		.andReturn();
	}
	
	@Test
	void createPizzaNegativeCase() throws Exception {
		System.out.println("Create Pizza order Negative test case");
		UserRequest userRequest = null;
		String requestBody = objectMapper.writeValueAsString(userRequest);

		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(UUID.randomUUID().toString());
		userResponse.setUserName("panda");
		
		when(userService.createUser(ArgumentMatchers.any(UserRequest.class)))
				.thenReturn(userResponse);
		
    	mockMvc.perform(post(RequestUri.USER)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
        		.andReturn();
	}

}
