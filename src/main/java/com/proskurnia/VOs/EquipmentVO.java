package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class EquipmentVO {

    private String id;
    private String wifiName;
    private String wifiPassword;
    private int serviceContractId;

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
