package com.proskurnia.VOs;

import java.util.Currency;
import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class RentingContractVO {
    private int number;
    private Currency rentPrice;
    private Currency deposit;
    private boolean depositReturned;
    private Currency balance;
    private Currency estimatedFees;
    private Date startDate;
    private Date expectedEndDate;
    private Date actualEndDate;
    private int tenantId;
    private int apartmentId;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Currency getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Currency rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Currency getDeposit() {
        return deposit;
    }

    public void setDeposit(Currency deposit) {
        this.deposit = deposit;
    }

    public boolean isDepositReturned() {
        return depositReturned;
    }

    public void setDepositReturned(boolean depositReturned) {
        this.depositReturned = depositReturned;
    }

    public Currency getBalance() {
        return balance;
    }

    public void setBalance(Currency balance) {
        this.balance = balance;
    }

    public Currency getEstimatedFees() {
        return estimatedFees;
    }

    public void setEstimatedFees(Currency estimatedFees) {
        this.estimatedFees = estimatedFees;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentingContractVO)) return false;

        RentingContractVO that = (RentingContractVO) o;

        if (getNumber() != that.getNumber()) return false;
        if (isDepositReturned() != that.isDepositReturned()) return false;
        if (getTenantId() != that.getTenantId()) return false;
        if (getApartmentId() != that.getApartmentId()) return false;
        if (!getRentPrice().equals(that.getRentPrice())) return false;
        if (!getDeposit().equals(that.getDeposit())) return false;
        if (!getBalance().equals(that.getBalance())) return false;
        if (!getEstimatedFees().equals(that.getEstimatedFees())) return false;
        if (!getStartDate().equals(that.getStartDate())) return false;
        if (!getExpectedEndDate().equals(that.getExpectedEndDate())) return false;
        return getActualEndDate() != null ? getActualEndDate().equals(that.getActualEndDate()) : that.getActualEndDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber();
        result = 31 * result + getRentPrice().hashCode();
        result = 31 * result + getDeposit().hashCode();
        result = 31 * result + (isDepositReturned() ? 1 : 0);
        result = 31 * result + getBalance().hashCode();
        result = 31 * result + getEstimatedFees().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getExpectedEndDate().hashCode();
        result = 31 * result + (getActualEndDate() != null ? getActualEndDate().hashCode() : 0);
        result = 31 * result + getTenantId();
        result = 31 * result + getApartmentId();
        return result;
    }
}
