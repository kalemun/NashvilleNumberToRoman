package com.numberconverter.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.numberconverter.model.JsonResponse;
import com.numberconverter.model.NumberModel;
import com.numberconverter.services.NumberConverterService;
import com.numberconverter.validators.NumberFormatValidator;

@Controller
public class NumberConverterController {

	/*
	 * Get home page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}

	@RequestMapping(value = "/convert", method = RequestMethod.POST)
	public @ResponseBody JsonResponse submitNumberConvertForm(
			@Valid @ModelAttribute("numberModel") NumberModel numberModel, BindingResult bindingResult) {
		
		JsonResponse result = new JsonResponse();

		if (bindingResult.hasErrors()) {
			handleBindingError(bindingResult, result, numberModel);			
			return result;
		}
		
		NumberFormatValidator formValidation = new NumberFormatValidator();
		formValidation.validate(numberModel, bindingResult);

		if (!bindingResult.hasErrors()) {
			result.setStatus("SUCCESS");
			numberModel.setRomanNumeral(NumberConverterService.intToRoman(numberModel.getNashvilleNumber()));
			result.setResult(numberModel);
		} else {
			result.setStatus("FAIL");
			result.setResult(bindingResult.getFieldErrors());
		}

		return result;
	}
	
	/*
	 * This method for binding errors..
	 * Binding errors can be handle also with overriding messages in messages properties file 
	 */
	public void handleBindingError(BindingResult bindingResult, JsonResponse result, NumberModel numberModel) {
		List<FieldError> bindingErrors = (List<FieldError>) bindingResult.getFieldErrors();
		BindingResult fieldErrors = new BeanPropertyBindingResult(numberModel, "numberModel");
		for (FieldError error : bindingErrors) {
			String field = error.getField();
			fieldErrors.rejectValue(field, "typeMismatch.numberModel." + field, "Must be number :=)");
		}

		result.setStatus("FAIL");
		result.setResult(fieldErrors.getFieldErrors());		
	}

}
