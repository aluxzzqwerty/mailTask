package com.epam.pages;

import com.epam.base.BasePage;
import com.epam.exceptions.NoSuchNameInListException;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProtonMailMainPage extends BasePage {
    private final Logger logger = LogManager.getRootLogger();

    public ProtonMailMainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@class,'item-container unread')]//span[@data-testid=\"message-column:sender-address\"]")
    List<WebElement> unreadSendersLetters;

    @FindBy(xpath = "//*[@data-signature-widget='container']/preceding-sibling::div[2]")
    List<WebElement> contentsOfSenders;

    @FindBy(xpath = "//iframe[@title='Email content']")
    WebElement contentFrame;

    @Step("finding a particular sender by its name in protonmail")
    public int findSenderIndexByName(String name) {
        int indexOfSender = -1;
        for (int i = 0; i < this.unreadSendersLetters.size(); i++) {
            if (this.unreadSendersLetters.get(i).getAttribute("title").equals(name)) {
                indexOfSender = i;
            }
        }
        if (indexOfSender == -1)
            throw new NoSuchNameInListException("Senders name not found");
        return indexOfSender;
    }

    @Step("finding a particular sender`s content by its text in protonmail")
    public int findIndexOfContentBySender(String content) {
        int indexOfContent = -1;
        for (int i = 0; i < this.contentsOfSenders.size(); i++) {
            if (this.contentsOfSenders.get(i).getText().equals(content)) {
                indexOfContent = i;
            }
        }
        if (indexOfContent == -1)
            throw new NoSuchNameInListException("Content not found");
        return indexOfContent;
    }

    public boolean isLetterUnreadAndHasCorrectSenderAndContent(String sender, String sentContent) {
        try {
            int indexOfSender = findSenderIndexByName(sender);

            this.unreadSendersLetters.get(indexOfSender).click();
            driver.switchTo().frame(this.contentFrame);
            findIndexOfContentBySender(sentContent);
            driver.switchTo().defaultContent();
        }
        catch (Exception e) {
            logger.error("Letter was not delivered");
            return false;
        }
        return true;
    }

}
