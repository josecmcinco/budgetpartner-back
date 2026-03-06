package com.budgetpartner.APP._suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.budgetpartner.APP.controller"
})
public class IntegrationTestSuite {
}
