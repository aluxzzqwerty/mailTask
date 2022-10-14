package com.epam.pages;

import com.epam.base.BasePage;
import com.epam.models.User;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProtonMailLoginPage extends BasePage {
    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://account.proton.me/login";

    public ProtonMailLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"username\"]")
    WebElement inputUsername;

    @FindBy(xpath = "//input[@id='password']")
    WebElement inputPassword;

    @Step("opening proton.com page")
    public ProtonMailLoginPage openLoginPage() {
        driver.navigate().to(BASE_URL);
        logger.info("Proton login page is opened");
        return this;
    }

    @Step("logging in protonmail service")
    public ProtonMailMainPage loggingProtonmail(User user) {
        this.inputUsername.sendKeys(user.getName());
        this.inputPassword.sendKeys(user.getPassword(), Keys.ENTER);
        logger.info("Login in proton.mail performed");
        return new ProtonMailMainPage(getDriver());
    }
}
