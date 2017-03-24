package com.proskurnia.VOs;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class RentingContractVO implements Identified<Integer> {
    private int id;
    private BigDecimal rentPrice;
    private BigDecimal deposit;
    private boolean depositReturned;
    private BigDecimal balance;
    private BigDecimal estimatedFees;
    private Timestamp startDate;
    private Timestamp expectedEndDate;
    private Timestamp actualEndDate;
    private int tenantId;
    private int apartmentId;

    public RentingContractVO() {
    }

    public RentingContractVO(int id, BigDecimal rentPrice, BigDecimal deposit, boolean depositReturned, BigDecimal balance, BigDecimal estimatedFees, Timestamp startDate, Timestamp expectedEndDate, Timestamp actualEndDate, int tenantId, int apartmentId) {
        this.id = id;
        this.rentPrice = rentPrice;
        this.deposit = deposit;
        this.depositReturned = depositReturned;
        this.balance = balance;
        this.estimatedFees = estimatedFees;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.actualEndDate = actualEndDate;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public boolean isDepositReturned() {
        return depositReturned;
    }

    public void setDepositReturned(boolean depositReturned) {
        this.depositReturned = depositReturned;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getEstimatedFees() {
        return estimatedFees;
    }

    public void setEstimatedFees(BigDecimal estimatedFees) {
        this.estimatedFees = estimatedFees;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Timestamp expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Timestamp getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Timestamp actualEndDate) {
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

        if (getId() != that.getId()) return false;
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
        int result = getId();
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
