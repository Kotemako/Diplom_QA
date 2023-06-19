package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class DebitPage {

    private final SelenideElement continueButton = $x("//span[text()='Продолжить']");

    private final SelenideElement cardNumberInput = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthInput = $("input[placeholder='08'");
    private final SelenideElement yearInput = $("input[placeholder='22'");
    private final SelenideElement ownerInput = $x("//span[text()='Владелец']/../../..//input");
    private final SelenideElement cvcInput = $("input[placeholder='999'");

    private final SelenideElement cardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement ownerError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement cvcError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    private final SelenideElement notificationError = $x("//div[contains(@class, 'notification_status_error')]");

    private final SelenideElement notificationSuccess = $(".notification_status_ok");

    public void sendDataInForm(String number, String month, String year, String owner, String cvc) {
        cardNumberInput.setValue(number);
        monthInput.setValue(month);
        yearInput.setValue(year);
        ownerInput.setValue(owner);
        cvcInput.setValue(cvc);
        continueButton.click();
    }

    public void approved() {
        notificationSuccess
                .shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    public void declined() {
        notificationError
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    public void monthErrorFormat() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError
                .shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void monthErrorTerm() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError
                .shouldHave(Condition.text("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void monthErrorFilling() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void cardNumberErrorFormat() {
        cardNumberError
                .shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void cardNumberErrorFilling() {
        cardNumberError
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void yearErrorFormat() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError
                .shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void yearErrorExpired() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError
                .shouldHave(Condition.text("Истёк срок действия карты"))
                .shouldBe(Condition.visible);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void yearErrorTerm() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError
                .shouldHave(Condition.text("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void yearErrorFilling() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
        ownerError.shouldBe(Condition.hidden);
        cvcError.shouldBe(Condition.hidden);
    }

    public void ownerErrorFormat() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError
                .shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
        cvcError.shouldBe(Condition.hidden);
    }

    public void ownerErrorLimit() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError
                .shouldHave(Condition.text("Превышен лимит вводимых символов"))
                .shouldBe(Condition.visible);
        cvcError.shouldBe(Condition.hidden);
    }

    public void ownerErrorFilling() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
        cvcError.shouldBe(Condition.hidden);
    }

    public void cvcErrorFormat() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError
                .shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
    }

    public void cvcErrorFilling() {
        cardNumberError.shouldBe(Condition.hidden);
        monthError.shouldBe(Condition.hidden);
        yearError.shouldBe(Condition.hidden);
        ownerError.shouldBe(Condition.hidden);
        cvcError
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }
}