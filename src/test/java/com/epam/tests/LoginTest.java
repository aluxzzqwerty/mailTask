package com.epam.tests;

import static org.testng.Assert.assertEquals;

import com.epam.base.BaseTest;
import com.epam.models.User;
import com.epam.pages.MailRuLoginPage;
import com.epam.pages.MailRuMainPage;
import com.epam.service.UserCreator;
import io.qameta.allure.Description;
import java.lang.reflect.Method;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private MailRuMainPage mailRuMainPage;

    @Test(priority = 2)
    @Description("Logging into mail.ru with the correct username / password")
    public void loggingToMailRuPositive() {
        User user = UserCreator.withCredentialsFromProperty("mail");
        mailRuMainPage = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingUpIntoAccount(user);
        String actualResult = mailRuMainPage.getLoggedInUser();
        assertEquals(actualResult, user.getName());
    }

    @Test(priority = 1)
    @Description("Logging into mail.ru with the incorrect username / password pair")
    public void loggingToMailRuNegative() {
        String actualResult = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingWithIncorrectData(UserCreator.withIncorrectPassword())
                .getIncorrectPasswordError();
        assertEquals(actualResult, "Incorrect password. Try again");
    }

    @Test(priority = 0)
    @Description("Logging into mail.ru with an empty username / password")
    public void loggingToMailWithNoData() {
        String actualResult = PageFactory.initElements(driver, MailRuLoginPage.class)
                .openLoginPage()
                .loggingWithIncorrectData(UserCreator.withEmptyCredentials())
                .getEmptyUsernameFieldError();
        assertEquals(actualResult, "The \"Account name\" field is required");
    }

    @AfterMethod(inheritGroups = false, alwaysRun = true)
    public void acceptAlert(Method method) {
        if (method.getName().equals("loggingToMailRuPositive"))
            mailRuMainPage.logOut();
    }
}