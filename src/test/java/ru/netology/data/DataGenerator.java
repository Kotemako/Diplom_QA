package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final Faker fakerWithCyrillic = new Faker(new Locale("ru", "RU"));

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";

    private DataGenerator() {
    }

    @Value
    public static class CardData {
        String number;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static CardData getValidApprovedCard() {
        return new CardData(approvedCard, getMonth(3), getYear(1), getOwner(), getCVC());
    }

    public static CardData getValidDeclinedCard() {
        return new CardData(declinedCard, getMonth(2), getYear(1), getOwner(), getCVC());
    }

    public static String getCardNumberWith13Digits() {
        return faker.numerify("4444 4444 4444 4");
    }

    public static String getCardNumberWith16Zero() {
        return faker.numerify("0000 0000 0000 0000");
    }

    public static String getEmptyCardNumber() {
        return faker.numerify("");
    }

    public static String getMonth(int shiftMonth) {
        return LocalDate.now().plusMonths(shiftMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getTwoDigitsGreaterThan12() {
        var greaterThan12 = new String[]{"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38",
                "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68",
                "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83",
                "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"
    };
        return greaterThan12[new Random().nextInt(greaterThan12.length)];
    }

    public static String getZeroDigit() {
        return faker.numerify("0");
    }

    public static String getTwoZeroDigits() {
        return faker.numerify("00");
    }

    public static String getOneDigit() {
        var oneDigit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };
        return oneDigit[new Random().nextInt(oneDigit.length)];
    }

    public static String getEmptyMonth() {
        return faker.regexify("");
    }

    public static String getYear(int shiftYear) {
        return LocalDate.now().plusYears(shiftYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getEmptyYear() {
        return faker.numerify("");
    }

    public static String getTwoDigitsGreaterThan28() {
        var greaterThan28 = new String[]{"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
                "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84",
                "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"
        };
        return greaterThan28[new Random().nextInt(greaterThan28.length)];
    }

    public static String getOwner() {
        return faker.name().fullName().toUpperCase();
    }

    public static String getOwnerWithFigures() {
        return faker.numerify("################# ################");
    }

    public static String getOwnerWithSymbols() {
        return faker.regexify("[!@#$%^&*()_+-={}|?><]{6} [!@#$%^&*()_+-={}|?><]{5}");
    }

    public static String getOwnerWithCyrillic() {
        return fakerWithCyrillic.name().firstName().toUpperCase() + " " + fakerWithCyrillic.name().lastName().toUpperCase();
    }

    public static String getOwnerWithLatinLowerCase() {
        return faker.name().firstName().toLowerCase() + " " + faker.name().lastName().toLowerCase();
    }

    public static String getOwnerWithCapitalLetters() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOwnerWithLatinUpperCaseMoreThan85Symbols() {
        return faker.letterify("??????????????????????????????????????????? ???????????????????????????????????????????");
    }

    public static String getEmptyOwner() {
        return faker.letterify("");
    }

    public static String getCVC() {
        return faker.numerify("###");
    }

    public static String getLessThan3Cvc() {
        var cvcLess = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68",
                "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82",
                "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96",
                "97", "98", "99", "00"
        };
        return cvcLess[new Random().nextInt(cvcLess.length)];
        }

        public static String getEmptyCVC() {
            return faker.bothify("");
    }
}