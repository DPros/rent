package com.proskurnia.VOs;

import java.util.Currency;
import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public abstract class Payment {
    protected int id;
    protected Date date;
    protected Currency amount;
    protected String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getAmount() {
        return amount;
    }

    public void setAmount(Currency amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (getId() != payment.getId()) return false;
        if (!getDate().equals(payment.getDate())) return false;
        if (!getAmount().equals(payment.getAmount())) return false;
        return getComment() != null ? getComment().equals(payment.getComment()) : payment.getComment() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        return result;
    }
}
