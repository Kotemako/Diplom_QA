package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.pages.DebitPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.DbHelper.*;

public class TicketBuyingTest {

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
        @DisplayName("№1 Buying with a valid debit card")
        public void shouldBuyWithValidCard() {
            var startPage = new StartPage();
            var cardData = getValidApprovedCard();
            startPage.chooseDebitCard();
            var debitPage = new DebitPage();
            debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            debitPage.approved();
            var expected = "APPROVED";
            var paymentInfo = getPaymentInfo();
            var orderInfo = getOrderInfo();
            assertEquals(expected, paymentInfo.getStatus());
            assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
        }

    }

    @Nested
    public class DeclinedCard {

        @Test
        @DisplayName("№2 Buying with a declined debit card")
        public void buyWithDeclinedCard() {
            var startPage = new StartPage();
            var cardData = getValidDeclinedCard();
            startPage.chooseDebitCard();
            var debitPage = new DebitPage();
            debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                    cardData.getOwner(), cardData.getCvc());
            debitPage.declined();
            var expected = "DECLINED";
            var paymentInfo = getPaymentInfo();
            var orderInfo = getOrderInfo();
            assertEquals(expected, paymentInfo.getStatus());
            assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
        }

        @Nested
        public class CardNumberField {

            @Test
            @DisplayName("№3 13 digits in the card number field")
            public void thirteenDigitsCardNumber() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(getCardNumberWith13Digits(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.cardNumberErrorFormat();
            }

            @Test
            @DisplayName("№4 16 zero in the card number field")
            public void sixteenZeroCardNumber() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(getCardNumberWith16Zero(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.cardNumberErrorFormat();
            }


            @Test
            @DisplayName("№5 The card number field is empty")
            public void shouldAppearCardNumberError() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(getEmptyCardNumber(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.cardNumberErrorFilling();
            }
        }

        @Nested
        public class MonthField {

            @Test
            @DisplayName("№6 The figure more than 12 is in the month field")
            public void MoreThan12InMonth() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                var month = DataGenerator.getTwoDigitsGreaterThan12();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorTerm();
            }

            @Test
            @DisplayName("№7 Figure 0 is in the month field")
            public void zeroDigitInMonth() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                var month = getZeroDigit();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorFormat();
            }

            @Test
            @DisplayName("№8 Two 0 are in the month field")
            public void twoZeroInMonth() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                var month = getTwoZeroDigits();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), month, cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorTerm();
            }

            @Test
            @DisplayName("№9 A digit is in the month field")
            public void oneDigitInMonth() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), getOneDigit(), cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorFormat();
            }

            @Test
            @DisplayName("№10 Previous month and current year are in their fields")
            public void previousMonthAndCurrentYear() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), getMonth(-1), getYear(0),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorTerm();
            }

            @Test
            @DisplayName("№11 The month field is empty")
            public void shouldAppearMonthError() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), getEmptyMonth(), cardData.getYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.monthErrorFilling();
            }
        }

        @Nested
        public class YearField {

            @Test
            @DisplayName("№12 A digit is in year field")
            public void oneDigitYear() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getOneDigit(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.yearErrorFormat();
            }

            @Test
            @DisplayName("№13 Previous years are in year field")
            public void oldYear() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getYear(-1),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.yearErrorExpired();
            }

            @Test
            @DisplayName("№14 Two zero are in year field")
            public void twoZeroYear() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                var year = "00";
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), year,
                        cardData.getOwner(), cardData.getCvc());
                debitPage.yearErrorExpired();
            }

            @Test
            @DisplayName("№15 The Year greater than the current year on 6 is in year field")
            public void greatestYear() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getTwoDigitsGreaterThan28(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.yearErrorTerm();
            }

            @Test
            @DisplayName("№16 The year field is empty")
            public void shouldAppearYearError() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), getEmptyYear(),
                        cardData.getOwner(), cardData.getCvc());
                debitPage.yearErrorFilling();
            }
        }

        @Nested
        public class CardholdersNameField {

            @Test
            @DisplayName("№17 Figures are in cardholder's name")
            public void cardholderFigures() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithFigures(), cardData.getCvc());
                debitPage.ownerErrorFormat();
            }

            @Test
            @DisplayName("№18 Symbols are in cardholder's name")
            public void cardholderSymbols() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithSymbols(), cardData.getCvc());
                debitPage.ownerErrorFormat();
            }

            @Test
            @DisplayName("№19 Cardholder's name is on Cyrillic")
            public void cardholderOnCyrillic() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithCyrillic(), cardData.getCvc());
                debitPage.ownerErrorFormat();
            }

            @Test
            @DisplayName("№20 Cardholder's name is on lower case")
            public void cardholderLowerCase() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithLatinLowerCase(), cardData.getCvc());
                debitPage.ownerErrorFormat();
            }

            @Test
            @DisplayName("№21 Cardholder's name is on capital letters")
            public void cardholderCapitalLetters() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithCapitalLetters(), cardData.getCvc());
                debitPage.ownerErrorFormat();
            }

            @Test
            @DisplayName("№22 Cardholder's name is greater than 85 symbols")
            public void cardholderGreaterThan85() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        getOwnerWithLatinUpperCaseMoreThan85Symbols(), cardData.getCvc());
                debitPage.ownerErrorLimit();
            }

            @Test
            @DisplayName("№23 The cardholder's name field is empty")
            public void shouldAppearOwnerError() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(), getEmptyOwner(), cardData.getCvc());
                debitPage.ownerErrorFilling();
            }
        }

        @Nested
        public class CvcField {

            @Test
            @DisplayName("№24 Three zero are in CVC field")
            public void threeZeroCvc() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                var cvc = "000";
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), cvc);
                debitPage.cvcErrorFormat();
            }
        }
            @Test
            @DisplayName("№25 Less than 3 digits are in CVC field")
            public void lessThan3DigitsCvc() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), getLessThan3Cvc());
                debitPage.cvcErrorFormat();
            }

            @Test
            @DisplayName("№26 The cvc field is empty")
            public void shouldAppearCvcError() {
                var startPage = new StartPage();
                var cardData = getValidApprovedCard();
                startPage.chooseDebitCard();
                var debitPage = new DebitPage();
                debitPage.sendDataInForm(cardData.getNumber(), cardData.getMonth(), cardData.getYear(),
                        cardData.getOwner(), getEmptyCVC());
                debitPage.cvcErrorFilling();
            }
        }
    }