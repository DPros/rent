package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public abstract class Payment implements Identified<Long> {
    protected long id;
    protected Timestamp date;
    protected BigDecimal amount;
    protected String comment;
    protected String accountNumber;
    protected String buildingAddress;

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
        if (getDate() != null ? !getDate().equals(payment.getDate()) : payment.getDate() != null) return false;
        if (getAmount() != null ? !getAmount().equals(payment.getAmount()) : payment.getAmount() != null) return false;
        if (getComment() != null ? !getComment().equals(payment.getComment()) : payment.getComment() != null)
            return false;
        return getAccountNumber() != null ? getAccountNumber().equals(payment.getAccountNumber()) : payment.getAccountNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (getAccountNumber() != null ? getAccountNumber().hashCode() : 0);
        return result;
    }

    public enum PaymentType {
        ServicePayment(0), BuildingPayment(1), ApartmentPayment(2), CreditPayment(3);

        private int val;

        PaymentType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static PaymentType valueOf(int i) {
            switch (i) {
                case 0:
                    return ServicePayment;
                case 1:
                    return BuildingPayment;
                case 2:
                    return ApartmentPayment;
                default:
                    return CreditPayment;
            }
        }
    }
}
