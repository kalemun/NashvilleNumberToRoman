package com.numberconverter.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.numberconverter.model.NumberModel;

public class NumberFormatValidator implements Validator {
	private Pattern pattern;
	private Matcher matcher;

	final String NUMBER_PATTERN = "[0-9]+";

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 * This method to validate number field. Number must be numeric and bigger than zero
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		NumberModel numberModel = (NumberModel) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nashvilleNumber", "required.nashvilleNumber",
				"Can not be empty");

		// input string conatains numeric values only and number must be bigger than zero
		if (numberModel.getNashvilleNumber() != null) {
			pattern = Pattern.compile(NUMBER_PATTERN);
			matcher = pattern.matcher(numberModel.getNashvilleNumber().toString());
			if (!matcher.matches()) {
				errors.rejectValue("nashvilleNumber", "nashvilleNumber.incorrect", "Number must be bigger than zero");
			}
		}

	}
}
