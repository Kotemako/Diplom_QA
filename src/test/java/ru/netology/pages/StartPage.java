package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StartPage {

    private final SelenideElement debitTitle = $(byText("Оплата по карте"));
    private final SelenideElement debitButton = $(byText("Купить"));
    private final SelenideElement creditTitle = $(byText("Кредит по данным карты"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));
    private final SelenideElement continueButton = $x("//span[text()='Продолжить']");

    public void chooseDebitCard() {
        debitButton.click();
        debitTitle.shouldBe(Condition.visible);
        continueButton.shouldBe(Condition.visible);
    }

    public void chooseCreditCard() {
        creditButton.click();
        creditTitle.shouldBe(Condition.visible);
        continueButton.shouldBe(Condition.visible);
    }
}