package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void VerificationPage() {
        codeField.shouldBe(visible);
    }
    public void VerifyErrorNotification() {
        errorNotification
                .shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(Condition.visible);
    }

    public void VerifyErrorNotificationBlockUser() {
        errorNotification
                .shouldHave(Condition.text("Превышено количество попыток ввода кода"))
                .shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verify (String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }
}