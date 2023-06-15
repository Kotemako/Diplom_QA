package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.pages.CreditPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.DbHelper.*;

public class TicketCreditTest {

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.headless = true;
    }

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    public class PositiveScenarios {

        @Test
        @DisplayName("№1 Buying in credit with a valid card")
        public void shouldBuyWithCreditValidCard() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.approved();
            var expected = "APPROVED";
            var creditInfo = getCreditRequestInfo();
            var orderInfo = getOrderInfo();
            assertEquals(expected, creditInfo.getStatus());
            assertEquals(creditInfo.getId(), orderInfo.getCredit_id());
        }
    }

    @Nested
    public class DeclinedCard {

        @Test
        @DisplayName("№2 Buying with a declined credit card")
        public void buyWithDeclinedCard() {
            var startPage = new StartPage();
            var cardData = getValidDeclinedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.declined();
            var expected = "DECLINED";
            var paymentInfo = getCreditRequestInfo();
            var orderInfo = getOrderInfo();
            assertEquals(expected, paymentInfo.getStatus());
            assertEquals(paymentInfo.getBank_id(), orderInfo.getPayment_id());
        }

        @Test
        @DisplayName("№3 Buying in credit with a declined card")
        public void buyCreditDeclinedCard() {
            var startPage = new StartPage();
            var cardData = getValidDeclinedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.declined();
        }
    }

    @Nested
    public class CardNumberField {

        @Test
        @DisplayName("№4 13 digits in the card number field")
        public void thirteenDigitsCardNumber() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(getCardNumberWith13Digits(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.cardNumberErrorFormat();
        }

        @Test
        @DisplayName("№5 16 zero in the card number field")
        public void sixteenZeroCardNumber() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(getCardNumberWith16Zero(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.cardNumberErrorFormat();
        }


        @Test
        @DisplayName("№6 The card number field is empty")
        public void shouldAppearCardNumberError() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(getEmptyCardNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.cardNumberErrorFilling();
        }
    }

    @Nested
    public class MonthField {

        @Test
        @DisplayName("№7 The figure more than 12 is in the month field")
        public void MoreThan12InMonth() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            var month = DataGenerator.getTwoDigitsGreaterThan12();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorTerm();
        }

        @Test
        @DisplayName("№8 Figure 0 is in the month field")
        public void zeroDigitInMonth() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            var month = getZeroDigit();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorFormat();
        }

        @Test
        @DisplayName("№9 Two 0 are in the month field")
        public void twoZeroInMonth() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            var month = getTwoZeroDigits();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorTerm();
        }

        @Test
        @DisplayName("№10 A digit is in the month field")
        public void oneDigitInMonth() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), getOneDigit(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorFormat();
        }

        @Test
        @DisplayName("№11 Previous month and current year are in their fields")
        public void previousMonthAndCurrentYear() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), getMonth(-1), getYear(0),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorTerm();
        }

        @Test
        @DisplayName("№12 The month field is empty")
        public void shouldAppearMonthError() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), getEmptyMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.monthErrorFilling();
        }
    }

    @Nested
    public class YearField {

        @Test
        @DisplayName("№13 A digit is in year field")
        public void oneDigitYear() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getOneDigit(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.yearErrorFormat();
        }

        @Test
        @DisplayName("№14 Previous years are in year field")
        public void oldYear() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getYear(-1),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.yearErrorExpired();
        }

        @Test
        @DisplayName("№15 Two zero are in year field")
        public void twoZeroYear() {
            var startPage = new StartPage();var cardData = getValidApprovedCard();
            var year = "00";
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), year,
                    cardData.getOwner(), cardData.getCvc());
            creditPage.yearErrorExpired();
        }

        @Test
        @DisplayName("№16 The Year greater than the current year on 6 is in year field")
        public void greatestYear() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getTwoDigitsGreaterThan28(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.yearErrorTerm();
        }

        @Test
        @DisplayName("№17 The year field is empty")
        public void shouldAppearYearError() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getEmptyYear(),
                    cardData.getOwner(), cardData.getCvc());
            creditPage.yearErrorFilling();
        }
    }

    @Nested
    public class CardholdersNameField {

        @Test
        @DisplayName("№18 Figures are in cardholder's name")
        public void cardholderFigures() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithFigures(), cardData.getCvc());
            creditPage.ownerErrorFormat();
        }

        @Test
        @DisplayName("№19 Symbols are in cardholder's name")
        public void cardholderSymbols() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithSymbols(), cardData.getCvc());
            creditPage.ownerErrorFormat();
        }

        @Test
        @DisplayName("№20 Cardholder's name is on Cyrillic")
        public void cardholderOnCyrillic() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithCyrillic(), cardData.getCvc());
            creditPage.ownerErrorFormat();
        }

        @Test
        @DisplayName("№21 Cardholder's name is on lower case")
        public void cardholderLowerCase() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithLatinLowerCase(), cardData.getCvc());
            creditPage.ownerErrorFormat();
        }

        @Test
        @DisplayName("№22 Cardholder's name is on capital letters")
        public void cardholderCapitalLetters() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithCapitalLetters(), cardData.getCvc());
            creditPage.ownerErrorFormat();
        }

        @Test
        @DisplayName("№23 Cardholder's name is greater than 85 symbols")
        public void cardholderGreaterThan85() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    getOwnerWithLatinUpperCaseMoreThan85Symbols(), cardData.getCvc());
            creditPage.ownerErrorLimit();
        }

        @Test
        @DisplayName("№24 The cardholder's name field is empty")
        public void shouldAppearOwnerError() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(), getEmptyOwner(), cardData.getCvc());
            creditPage.ownerErrorFilling();
        }
    }

    @Nested
    public class CvcField {

        @Test
        @DisplayName("№25 Three zero are in CVC field")
        public void threeZeroCvc() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            var cvc = "000";
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cvc);
            creditPage.cvcErrorFormat();
        }

        @Test
        @DisplayName("№26 Less than 3 digits are in CVC field")
        public void lessThan3DigitsCvc() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), getLessThan3Cvc());
            creditPage.cvcErrorFormat();
        }

        @Test
        @DisplayName("№27 The cvc field is empty")
        public void shouldAppearCvcError() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseCreditCard();
            var creditPage = new CreditPage();
            creditPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), getEmptyCVC());
            creditPage.cvcErrorFilling();
        }
    }
}