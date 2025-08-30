package com.budgetpartner.APP._suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.budgetpartner.APP.dto",
        "com.budgetpartner.APP.repository",
        "com.budgetpartner.APP.service"
})
public class UnitaryTestSuite {
}
