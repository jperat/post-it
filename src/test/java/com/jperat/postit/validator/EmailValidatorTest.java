package com.jperat.postit.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class EmailValidatorTest {

	@Test
	public void isValid() {
		EmailValidator emailValidator = new EmailValidator();
		assertTrue(emailValidator.isValid("test@test.test", null));
		assertFalse(emailValidator.isValid("tes.t.@test", null));
	}
}
