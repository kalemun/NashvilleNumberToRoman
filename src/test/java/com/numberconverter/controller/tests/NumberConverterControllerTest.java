package com.numberconverter.controller.tests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.numberconverter.controllers.NumberConverterController;

public class NumberConverterControllerTest {

	@InjectMocks
	private NumberConverterController numberConverterController;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		// Process mock annotations
		MockitoAnnotations.initMocks(this);

		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(numberConverterController).build();
	}

	/*
	 * Home page test
	 */
	@Test
	public void showHomePage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	/*
	 * Test for string input
	 */
	@Test
	public void testInvalidNumber() throws Exception {

		this.mockMvc
				.perform(post("/convert").param("nashvilleNumber", "h"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$result[0].defaultMessage", is("Must be number :=)")));

	}

	/*
	 * Test for empty input
	 */
	@Test
	public void testEmptyNumber() throws Exception {

		this.mockMvc
				.perform(post("/convert").param("nashvilleNumber", ""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$result[0].defaultMessage", is("Can not be empty")));
	}
	
	/*
	 * Test for negative input
	 */
	@Test
	public void testNegativeNumber() throws Exception {

		this.mockMvc
				.perform(post("/convert").param("nashvilleNumber", "-212"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$result[0].defaultMessage", is("Number must be bigger than zero")));

	}
	
	/*
	 * Test for 0
	 */
	@Test
	public void testZeroNumber() throws Exception {

		this.mockMvc
				.perform(post("/convert").param("nashvilleNumber", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$result.romanNumeral", is("nulla")));

	}

	/*
	 * Test for correct input
	 */
	@Test
	public void testConverter() throws Exception {

		this.mockMvc
				.perform(post("/convert").param("nashvilleNumber", "234"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$result.romanNumeral", is("CCXXXIV")));

	}	
}
