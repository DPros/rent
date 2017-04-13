package com.proskurnia.VOs;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class RentingContractVO implements Identified<Integer>, Serializable {
    private int id;
    @DecimalMin("0.00")
    private BigDecimal rentPrice = BigDecimal.ZERO;
    @DecimalMin("0.00")
    private BigDecimal deposit = BigDecimal.ZERO;
    private boolean depositReturned;
    @DecimalMin("0.00")
    private BigDecimal estimatedFees = BigDecimal.ZERO;
    private Timestamp startDate;
    private Timestamp expectedEndDate;
    private Timestamp actualEndDate;
    private int tenantId;
    private int apartmentId;
    private int buildingId;
    private String address;
    private String roomNumber;
    private String tenantName;

    public RentingContractVO() {
    }

    public RentingContractVO(int id, BigDecimal rentPrice, BigDecimal deposit, boolean depositReturned, BigDecimal estimatedFees, Timestamp startDate, Timestamp expectedEndDate, Timestamp actualEndDate, int tenantId, int apartmentId, String tenantName, int buildingId, String address, String roomNumber) {
        this.id = id;
        this.rentPrice = rentPrice;
        this.deposit = deposit;
        this.depositReturned = depositReturned;
        this.estimatedFees = estimatedFees;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.actualEndDate = actualEndDate;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
        this.tenantName = tenantName;
        this.buildingId = buildingId;
        this.address = address;
        this.roomNumber = roomNumber;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
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
        if (o == null || getClass() != o.getClass()) return false;

        RentingContractVO that = (RentingContractVO) o;

        if (id != that.id) return false;
        if (depositReturned != that.depositReturned) return false;
        if (tenantId != that.tenantId) return false;
        if (apartmentId != that.apartmentId) return false;
        if (rentPrice != null ? !rentPrice.equals(that.rentPrice) : that.rentPrice != null) return false;
        if (deposit != null ? !deposit.equals(that.deposit) : that.deposit != null) return false;
        if (estimatedFees != null ? !estimatedFees.equals(that.estimatedFees) : that.estimatedFees != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (expectedEndDate != null ? !expectedEndDate.equals(that.expectedEndDate) : that.expectedEndDate != null)
            return false;
        return actualEndDate != null ? actualEndDate.equals(that.actualEndDate) : that.actualEndDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (rentPrice != null ? rentPrice.hashCode() : 0);
        result = 31 * result + (deposit != null ? deposit.hashCode() : 0);
        result = 31 * result + (depositReturned ? 1 : 0);
        result = 31 * result + (estimatedFees != null ? estimatedFees.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (expectedEndDate != null ? expectedEndDate.hashCode() : 0);
        result = 31 * result + (actualEndDate != null ? actualEndDate.hashCode() : 0);
        result = 31 * result + tenantId;
        result = 31 * result + apartmentId;
        return result;
    }
}
