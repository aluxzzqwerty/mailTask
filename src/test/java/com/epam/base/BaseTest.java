package com.epam.base;

import com.epam.utilities.DriverSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeSuite
    protected void setUp() {
        driver = DriverSingleton.getSingletonDriver();
    }

    @AfterSuite
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void takeScreenshot() {
        Date currentDate = new Date();
        String screenName = currentDate.toString().replace(" ", "-").replace(":", "-");
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(".//target/screenshots//" + screenName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    protected void trackTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot();
        }
    }
}
