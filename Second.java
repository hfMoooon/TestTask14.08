package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Second {

    private static final String[] THOUSANDS = {
            "", "тысяча", "тысячи", "тысяч"
    };

    private static final String[] HUNDREDS = {
            "", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"
    };

    private static final String[] TENS = {
            "", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
    };

    private static final String[] NUMBERS_BELOW_TWENTY = {
            "", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять",
            "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
            "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    };

    public static String convert(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("99999.99")) > 0 || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 0 до 99 999.99");
        }

        BigInteger rubles = amount.setScale(0, RoundingMode.DOWN).toBigInteger();
        int kopecks = amount.remainder(BigDecimal.ONE).multiply(new BigDecimal("100")).intValue();

        return convertPart(rubles) + " целых, " + convertPart(BigInteger.valueOf(kopecks)) + " сотых.";
    }

    private static String convertPart(BigInteger number) {
        if (number.equals(BigInteger.ZERO)) {
            return "ноль";
        }

        StringBuilder words = new StringBuilder();

        int thousands = number.divide(BigInteger.valueOf(1000)).intValue();
        int remainder = number.mod(BigInteger.valueOf(1000)).intValue();

        if (thousands > 0) {
            words.append(convertHundreds(thousands)).append(" ");
            words.append(getThousandsWordForm(thousands));
            words.append(" ");
        }

        words.append(convertHundreds(remainder));

        return words.toString().trim();
    }

    private static String convertHundreds(int number) {
        StringBuilder words = new StringBuilder();

        words.append(HUNDREDS[number / 100]).append(" ");

        int remainder = number % 100;

        if (remainder < 20) {
            words.append(NUMBERS_BELOW_TWENTY[remainder]);
        } else {
            words.append(TENS[remainder / 10]).append(" ");
            words.append(NUMBERS_BELOW_TWENTY[remainder % 10]);
        }

        return words.toString().trim();
    }

    private static String getThousandsWordForm(int thousands) {
        if (thousands == 1) {
            return THOUSANDS[1];
        } else if (thousands >= 2 && thousands <= 4) {
            return THOUSANDS[2];
        } else {
            return THOUSANDS[3];
        }
    }
}

