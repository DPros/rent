package com.proskurnia.VOs;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class ServiceContractVO implements Identified<Integer> {
    private int id;
    private Timestamp startDate;
    private Timestamp endDate;
    private int serviceCompanyId;
    private String comment;
    private String accountLogin;
    private String accountPassword;
    private int buildingId;

    public ServiceContractVO() {
    }

    public ServiceContractVO(int id, Timestamp startDate, Timestamp endDate, int serviceCompanyId, String comment, String accountLogin, String accountPassword, int buildingId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.serviceCompanyId = serviceCompanyId;
        this.comment = comment;
        this.accountLogin = accountLogin;
        this.accountPassword = accountPassword;
        this.buildingId = buildingId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getServiceCompanyId() {
        return serviceCompanyId;
    }

    public void setServiceCompanyId(int serviceCompanyId) {
        this.serviceCompanyId = serviceCompanyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceContractVO that = (ServiceContractVO) o;

        if (id != that.id) return false;
        if (serviceCompanyId != that.serviceCompanyId) return false;
        if (buildingId != that.buildingId) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (accountLogin != null ? !accountLogin.equals(that.accountLogin) : that.accountLogin != null) return false;
        return accountPassword != null ? accountPassword.equals(that.accountPassword) : that.accountPassword == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + serviceCompanyId;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (accountLogin != null ? accountLogin.hashCode() : 0);
        result = 31 * result + (accountPassword != null ? accountPassword.hashCode() : 0);
        result = 31 * result + buildingId;
        return result;
    }
}
