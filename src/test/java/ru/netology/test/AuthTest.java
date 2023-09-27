package ru.netology.test;

import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

class AuthTest {

    @AfterAll
    static void stubTest() {
        cleanDataBase();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestWithDemoLoginAndPassword() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfoDemo1();
        var verificationPage = loginPage.validLogin(authInfo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var verificationCode = SQLHelper.getVerificationCode().getCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTestWithGeneratedNewUser() {
        var loginPage = new LoginPage();
        var authInfo = getRandomUser();
        var verificationPage = loginPage.validRandomLogin(authInfo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var verificationCode = SQLHelper.getVerificationCode().getCode();
        verificationPage.validVerify(verificationCode);
        cleanDataBaseAfterAddNewRandomUser();
    }

    @Test
    void shouldErrorWithIncorrectVerificationCode() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfoDemo1();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.VerifyErrorNotification();
    }

    @Test
    void shouldBlockUserWithIncorrectVerificationCode4Times() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfoDemo2();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        open("http://localhost:9999");
        loginPage.validLogin(authInfo);
        verificationPage.verify(verificationCode.getCode());
        open("http://localhost:9999");
        loginPage.validLogin(authInfo);
        verificationPage.verify(verificationCode.getCode());
        open("http://localhost:9999");
        loginPage.validLogin(authInfo);
        verificationPage.verify(verificationCode.getCode());
        verificationPage.VerifyErrorNotificationBlockUser();
    }

}
