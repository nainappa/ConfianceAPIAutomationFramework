package com.confiance.framework.api.report;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class Assertions extends Assertion {

	@Override
	public void onBeforeAssert(IAssert a) {
	}

	@Override
	public void onAfterAssert(IAssert a) {
	}

	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		ExtentReportUtil.logger.pass("Assertion Passed. Expected: "+ assertCommand.getExpected() +", Actual: "+ assertCommand.getActual());
	}

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		ExtentReportUtil.logger.fail("Assertion Failed. Expected: "+ assertCommand.getExpected() +", Actual: "+ assertCommand.getActual());
	}
}
