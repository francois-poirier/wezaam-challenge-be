package com.wezaam.withdrawal.model;

public enum WithdrawalStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    OUT_OF_ACCOUNT("OUT_OF_ACCOUNT"),
    SUCCESS("SUCCESS"),
    CANCELED("CANCELED");

    WithdrawalStatus (final String name) {
        this.name=name;
    }
    private final String name;

    public String toString() {
        return this.name;
    }
}
