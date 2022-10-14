package com.epam.pages;

import com.epam.base.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MailRuMainPage extends BasePage {
    private final Logger logger = LogManager.getRootLogger();

    public MailRuMainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()='Написать письмо']")
    WebElement writeLetterButton;

    @FindBy(xpath = "//label[@class='container--zU301']")
    WebElement inputLetterRecipient;

    @FindBy(xpath = "//div[@role='textbox']")
    WebElement inputContent;

    @FindBy(xpath = "//span[text()='Отправить']")
    WebElement sendButton;

    @FindBy(xpath = "//div[text()='Отправленные']")
    WebElement sentMessagesButton;

    @FindBy(xpath = "//span[@class='ll-crpt']")
    List<WebElement> listOfRecipients;

    @FindBy(xpath = "//span[@class='ll-sp__normal']")
    List<WebElement> listOfContentsThatSent;

    @FindBy(xpath = "//span[@class='ph-dropdown-icon svelte-14x1gy5']")
    WebElement dropdownOfUserOptions;

    @FindBy(xpath = "//span[@class='ph-project__user-name svelte-1hiqrvn']")
    WebElement linkLoggedUser;

    @FindBy(xpath = "//div[text()='Выйти']")
    WebElement exitButton;

    @Step("sending letter in mailru")
    public MailRuMainPage sendLetter(String recipient, String content) {
        waitForElementToBeClickable(this.sentMessagesButton);
        this.sentMessagesButton.click();
        waitForElementToBeClickable(this.writeLetterButton);
        this.writeLetterButton.click();
        this.inputLetterRecipient.sendKeys(recipient, Keys.ENTER);
        this.inputContent.sendKeys(content);
        waitForElementToBeClickable(this.sendButton);
        this.sendButton.click();
        logger.info("Letter sent to " + recipient + " with this content " + content);
        return this;
    }

    @Step("finding particular recipient by its name in mailru")
    public boolean findRecipientByName(String name) {
        for (WebElement listOfRecipient : this.listOfRecipients) {
            if (listOfRecipient.getText().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    @Step("finding particular content by its text in mailru")
    public boolean findContentByText(String text) {
        for (WebElement element : this.listOfContentsThatSent) {
            if (element.getText().contains(text))
                return true;
        }
        return false;
    }

    @Step("getting link of user after logging")
    public String getLoggedInUser() {
        return this.linkLoggedUser.getText();
    }

    @Step("validating whether the letter is sent or not")
    public boolean isLetterSent(String recipient, String textOfContent) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", this.sentMessagesButton);

        return findRecipientByName(recipient) && findContentByText(textOfContent);
    }

    @Step("accepting \"leave site\" alert")
    public MailRuMainPage acceptAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step("logging out")
    public MailRuLoginPage logOut() {
        this.dropdownOfUserOptions.click();
        this.exitButton.click();
        logger.info("User logged out");
        return new MailRuLoginPage(driver);
    }

}
