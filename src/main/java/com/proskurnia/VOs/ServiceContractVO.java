package com.proskurnia.VOs;

import java.util.Date;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class ServiceContractVO {
    private int id;
    private Date startDate;
    private Date endDate;
    private int serviceCompanyId;
    private String comment;
    private String accountLogin;
    private String accountPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
}
