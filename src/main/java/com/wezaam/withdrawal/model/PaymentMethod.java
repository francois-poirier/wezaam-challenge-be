package com.wezaam.withdrawal.model;

public enum PaymentMethod {

    CREDIT_CARD("CREDIT_CARD"),
    PAYPAL("PAYPAL");

    PaymentMethod (final String name) {
        this.name=name;
    }
    private final String name;

    public String toString() {
        return this.name;
    }
}
