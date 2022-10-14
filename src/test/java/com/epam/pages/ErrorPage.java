package com.epam.pages;

import com.epam.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage extends BasePage {
    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Incorrect password. Try again']")
    WebElement incorrectPasswordError;

    @FindBy(xpath = "//small[text()='The \"Account name\" field is required']")
    WebElement emptyUsernameFieldError;

    public String getIncorrectPasswordError() {
        return incorrectPasswordError.getText();
    }

    public String getEmptyUsernameFieldError() {
        return emptyUsernameFieldError.getText();
    }
}
