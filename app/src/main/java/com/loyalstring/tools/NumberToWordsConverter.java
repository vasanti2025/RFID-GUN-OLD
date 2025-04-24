package com.loyalstring.tools;

import java.text.DecimalFormat;

public class NumberToWordsConverter {
    private static final String[] tensNames = {
            "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
    };

    private static final String[] numNames = {
            "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine",
            " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"
    };

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " Hundred" + current;
    }

    public static String convert(long number) {
        if (number == 0) return "zero";

        String snumber = Long.toString(number);

        // Pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        int billions = Integer.parseInt(snumber.substring(0, 3));
        int millions  = Integer.parseInt(snumber.substring(3, 6));
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0: tradBillions = ""; break;
            case 1: tradBillions = convertLessThanOneThousand(billions) + " Billion "; break;
            default: tradBillions = convertLessThanOneThousand(billions) + " Billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0: tradMillions = ""; break;
            case 1: tradMillions = convertLessThanOneThousand(millions) + " Million "; break;
            default: tradMillions = convertLessThanOneThousand(millions) + " Million ";
        }
        result =  result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0: tradHundredThousands = ""; break;
            case 1: tradHundredThousands = "One Thousand "; break;
            default: tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " Thousand ";
        }
        result =  result + tradHundredThousands;

        String tradThousands;
        tradThousands = convertLessThanOneThousand(thousands);
        result =  result + tradThousands;

        // remove extra spaces
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    public static String convertDoubleToWords(double number) {
        long rupees = (long) number;
        long paise = Math.round((number - rupees) * 100);
        return "Rs. " + convert(rupees) + " and " + convert(paise) + " Paise Only";
    }
}
