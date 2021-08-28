package com.example.myquiz.ultis;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormats {
    public static String double2Decimal(double num) {
        NumberFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(num);
    }
}
