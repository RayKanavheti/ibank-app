package com.equals.transactionservice.domain;

public enum TransactionType {

    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL"),
    INTERNAL_FUND_TRANSFER("INTERNAL_FUND_TRANSFER"),
    EXTERNAL_FUND_TRANSFER("EXTERNAL_FUND_TRANSFER"),
    BILL_PAYMENT("BILL_PAYMENT"),

    FEE("FEE");

    private String label;

    TransactionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
