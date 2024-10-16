package org.example.wallet.Enums;

import java.text.DecimalFormat;

public enum CurrencyType {
    INR(84.04),
    USD(1.0),
    EUR(0.94);

    private final double baseValue;

    CurrencyType(double baseValue) {
        this.baseValue = baseValue;
    }
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public double convertTo(double amount, CurrencyType targetCurrency) {
        if (this == targetCurrency) {
            return amount;
        }
        double amountInBaseCurrency = amount / this.baseValue;
        double convertedAmount = amountInBaseCurrency * targetCurrency.baseValue;
        return Double.parseDouble(df.format(convertedAmount));
    }
}
