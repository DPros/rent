package com.proskurnia.VOs;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class ApartmentVO implements Identified<Integer> {
    private int id;
    @NotEmpty
    private String roomNumber;
    private double size;
    private int buildingId;
    private String address;
    private String tenantName;
    private Integer rentingContractId;

    public ApartmentVO() {
    }

    public ApartmentVO(int id, String roomNumber, double size, int buildingId, String address, Integer rentingContractId, String tenantName) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.size = size;
        this.buildingId = buildingId;
        this.address = address;
        this.tenantName = tenantName;
        this.rentingContractId = rentingContractId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getRentingContractId() {
        return rentingContractId;
    }

    public void setRentingContractId(Integer rentingContractId) {
        this.rentingContractId = rentingContractId;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApartmentVO)) return false;

        ApartmentVO that = (ApartmentVO) o;

        if (getId() != that.getId()) return false;
        if (Double.compare(that.getSize(), getSize()) != 0) return false;
        if (getBuildingId() != that.getBuildingId()) return false;
        if (getRoomNumber() != null ? !getRoomNumber().equals(that.getRoomNumber()) : that.getRoomNumber() != null)
            return false;
        return getAddress() != null ? getAddress().equals(that.getAddress()) : that.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + (getRoomNumber() != null ? getRoomNumber().hashCode() : 0);
        temp = Double.doubleToLongBits(getSize());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getBuildingId();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}