package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class BankVO implements Identified<Integer> {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public BankVO() {
    }

    public BankVO(int id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (o == null || getClass() != o.getClass()) return false;

        BankVO bankVO = (BankVO) o;

        if (id != bankVO.id) return false;
        if (name != null ? !name.equals(bankVO.name) : bankVO.name != null) return false;
        if (address != null ? !address.equals(bankVO.address) : bankVO.address != null) return false;
        if (phone != null ? !phone.equals(bankVO.phone) : bankVO.phone != null) return false;
        return email != null ? email.equals(bankVO.email) : bankVO.email == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
