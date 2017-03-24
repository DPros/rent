package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class DebitPaymentVO extends Payment {

    private DebitPaymentType type;
    private String description;
    private int reasonId;

    public DebitPaymentVO(int id, Timestamp date, BigDecimal amount, String comment, DebitPaymentType type, int reasonId, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.type = type;
        this.reasonId = reasonId;
        this.description = description;
    }

    public DebitPaymentType getType() {
        return type;
    }

    public void setType(DebitPaymentType type) {
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

    public enum DebitPaymentType {
        ServicePayment(0), BuildingPayment(1), ApartmentPayment(2);

        private int val;

        DebitPaymentType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
}
