package com.epam.tests;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.MailRuLoginPage;
import com.epam.pages.MailRuMainPage;
import com.epam.pages.ProtonMailLoginPage;
import com.epam.service.UserCreator;
import io.qameta.allure.Description;
import java.lang.reflect.Method;
import org.openqa.selenium.support.PageFactory;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MessageTest extends BaseTest {

    private final String CONTENT = "some content";
    private final String recipient = "firstTAAlua@protonmail.com";
    private final String sender = "fakeusername00@mail.ru";
    private MailRuMainPage mailRuMainPage;

    @Test()
    @Description("Logging into mail.ru and sending letter to protonmail user")
    public void sentLetterValidation() {
        User user = UserCreator.withCredentialsFromProperty("mail");
        mailRuMainPage = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingUpIntoAccount(user)
                .sendLetter(recipient, CONTENT);
        boolean actualResult = mailRuMainPage.isLetterSent(recipient, CONTENT);
        assertTrue(actualResult);
    }

    @Test(dependsOnMethods = { "sentLetterValidation" })
    @Description("Logging into protonmail.com and checking whether letter is arrived, unread and has correct sender and content")
    public void letterValidation() {
        User user = UserCreator.withCredentialsFromProperty("proton");
        boolean actualResult = PageFactory.initElements(driver, ProtonMailLoginPage.class)
                .openLoginPage()
                .loggingProtonmail(user)
                .isLetterUnreadAndHasCorrectSenderAndContent(sender, CONTENT);
        assertTrue(actualResult);
    }

    @AfterMethod(inheritGroups = false, alwaysRun = true)
    public void acceptAlert(Method method) {
        if (method.getName().equals("sentLetterValidation"))
            mailRuMainPage.acceptAlert();
    }
}
