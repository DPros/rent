package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class CreditPaymentVO extends Payment{

    private boolean deposit;
    private boolean confirmed;
    private int contractId;

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
