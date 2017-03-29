package com.proskurnia.VOs;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class EquipmentVO implements Identified<String> {

    @NotEmpty
    private String id;
    private String wifiName;
    private String wifiPassword;
    @Min(1)
    private int serviceContractId;
    private int buildingId;
    private String address;

    public EquipmentVO() {
    }

    public EquipmentVO(String id, String wifiName, String wifiPassword, int serviceContractId, int buildingId, String address) {
        this.id = id;
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
        this.serviceContractId = serviceContractId;
        this.buildingId = buildingId;
        this.address = address;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public int getServiceContractId() {
        return serviceContractId;
    }

    public void setServiceContractId(int serviceContractId) {
        this.serviceContractId = serviceContractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentVO that = (EquipmentVO) o;

        if (getServiceContractId() != that.getServiceContractId()) return false;
        if (!getId().equals(that.getId())) return false;
        if (getWifiName() != null ? !getWifiName().equals(that.getWifiName()) : that.getWifiName() != null)
            return false;
        return getWifiPassword() != null ? getWifiPassword().equals(that.getWifiPassword()) : that.getWifiPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getWifiName() != null ? getWifiName().hashCode() : 0);
        result = 31 * result + (getWifiPassword() != null ? getWifiPassword().hashCode() : 0);
        result = 31 * result + getServiceContractId();
        return result;
    }
}
