package model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public interface Validatable {

	public default Set<ConstraintViolation<Validatable>> validate() {

		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();

		Set<ConstraintViolation<Validatable>> constraintViolations = validator
				.validate(this);

		return constraintViolations;
	}
}
