package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class DebitPaymentVO extends Payment {

    private PaymentType type;
    private String description;
    private int reasonId;

    public DebitPaymentVO() {
    }

    public DebitPaymentVO(long id, Timestamp date, BigDecimal amount, String comment, PaymentType type, int reasonId, String description, String accountNumber, String buildingAddress) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.type = type;
        this.reasonId = reasonId;
        this.description = description;
        this.accountNumber = accountNumber;
        this.buildingAddress = buildingAddress;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebitPaymentVO)) return false;
        if (!super.equals(o)) return false;

        DebitPaymentVO that = (DebitPaymentVO) o;

        if (getReasonId() != that.getReasonId()) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getReasonId();
        return result;
    }
}
