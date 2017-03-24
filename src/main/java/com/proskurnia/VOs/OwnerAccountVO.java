package com.proskurnia.VOs;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class OwnerAccountVO implements Identified<Integer>{
    private int id;
    private String number;
    private int bankId;
    private BigDecimal balance;
    private int ownerId;

    public OwnerAccountVO() {
    }

    public OwnerAccountVO(int id, String number, int bankId, BigDecimal balance, int ownerId) {
        this.id = id;
        this.number = number;
        this.bankId = bankId;
        this.balance = balance;
        this.ownerId = ownerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        if (id != that.id) return false;
        if (bankId != that.bankId) return false;
        if (ownerId != that.ownerId) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + bankId;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + ownerId;
        return result;
    }
}
