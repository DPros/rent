package com.proskurnia.VOs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class OwnerAccountVO implements Identified<String>{
    @NotEmpty
    private String id;
    private int bankId;
    private BigDecimal balance;
    private int ownerId;
    private String bankName;

    public OwnerAccountVO() {
    }

    public OwnerAccountVO(String id, int bankId, BigDecimal balance, int ownerId, String bankName) {
        this.id = id;
        this.bankId = bankId;
        this.balance = balance;
        this.ownerId = ownerId;
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerAccountVO that = (OwnerAccountVO) o;

        if (bankId != that.bankId) return false;
        if (ownerId != that.ownerId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + bankId;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + ownerId;
        return result;
    }
}
