package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class CreditPaymentVO extends Payment {

    private boolean deposit;
    private boolean confirmed;
    private int contractId;

    public CreditPaymentVO() {
    }

    public CreditPaymentVO(long id, Timestamp date, BigDecimal amount, String comment, boolean deposit, boolean confirmed, int contractId, String accountNumber, String buildingAddress) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.confirmed = confirmed;
        this.deposit = deposit;
        this.contractId = contractId;
        this.accountNumber = accountNumber;
        this.buildingAddress = buildingAddress;
    }

    public PaymentType getType() {
        return PaymentType.CreditPayment;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditPaymentVO)) return false;
        if (!super.equals(o)) return false;

        CreditPaymentVO that = (CreditPaymentVO) o;

        if (isDeposit() != that.isDeposit()) return false;
        if (isConfirmed() != that.isConfirmed()) return false;
        return getContractId() == that.getContractId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isDeposit() ? 1 : 0);
        result = 31 * result + (isConfirmed() ? 1 : 0);
        result = 31 * result + getContractId();
        return result;
    }
}
