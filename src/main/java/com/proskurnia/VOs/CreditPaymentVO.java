package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class CreditPaymentVO extends Payment {

    private boolean deposit;
    private int contractId;
    private String roomNumber;

    public CreditPaymentVO() {
    }

    public CreditPaymentVO(long id, Timestamp date, BigDecimal amount, String comment, boolean deposit, int contractId, String accountNumber, String buildingAddress, String roomNumber) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.deposit = deposit;
        this.contractId = contractId;
        this.accountNumber = accountNumber;
        this.buildingAddress = buildingAddress;
        this.roomNumber = roomNumber;
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

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Override
    public String getDescription() {
        return roomNumber;
    }

    @Override
    public String amountForOwnerReport() {
        return '+' + amount.toString();
    }

    @Override
    public String amountForTenantReport() {
        return '-' + amount.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditPaymentVO)) return false;
        if (!super.equals(o)) return false;

        CreditPaymentVO that = (CreditPaymentVO) o;

        if (isDeposit() != that.isDeposit()) return false;
        return getContractId() == that.getContractId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isDeposit() ? 1 : 0);
        result = 31 * result + getContractId();
        return result;
    }
}
