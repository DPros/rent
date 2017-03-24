package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class BankVO {
    private int id;
    private String name;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankVO)) return false;

        BankVO bankVO = (BankVO) o;

        if (getId() != bankVO.getId()) return false;
        if (!getName().equals(bankVO.getName())) return false;
        return getAddress() != null ? getAddress().equals(bankVO.getAddress()) : bankVO.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
