package org.github.listeners;

import org.github.utils.ExtentReportsUtility;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestEventListenersUtility implements ITestListener {
    private static ExtentReportsUtility extentUtilityObject;

    @Override
    public void onTestStart(ITestResult result) {
        if (extentUtilityObject != null) {
            extentUtilityObject.startSingleTestReport(result.getMethod().getMethodName());
        } else {
            System.err.println("Error: ExtentReportsUtility object is not initialized.");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (extentUtilityObject != null) {
            extentUtilityObject.logTestpassed(result.getMethod().getMethodName() + " is passed");
        } else {
            System.err.println("Error: ExtentReportsUtility object is not initialized.");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (extentUtilityObject != null) {
            extentUtilityObject.logTestFailed(result.getMethod().getMethodName() + " is failed");
            extentUtilityObject.logTestFailedWithException(result.getThrowable());
        } else {
            System.err.println("Error: ExtentReportsUtility object is not initialized.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Optionally implement behavior for skipped tests
    }

    @Override
    public void onStart(ITestContext context) {
        extentUtilityObject = ExtentReportsUtility.getInstance();
        if (extentUtilityObject != null) {
            System.out.println("Report utility object created=" + extentUtilityObject);
            extentUtilityObject.startExtentReport();
        } else {
            System.err.println("Error: Failed to create ExtentReportsUtility instance.");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentUtilityObject != null) {
            extentUtilityObject.endReport();
        } else {
            System.err.println("Error: ExtentReportsUtility object is not initialized.");
        }
    }
}
