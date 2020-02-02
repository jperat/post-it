package com.jperat.postit.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class ToolsTest {

	@Test
	public void isInteger() {
		assertTrue(Tools.isInteger("12"));
		assertFalse(Tools.isInteger("twelve"));
	}

}
