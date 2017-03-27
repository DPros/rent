package com.proskurnia.VOs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class PersonVO implements Identified<Integer>, Serializable {
    protected int id;
    protected String passport;
    @NotEmpty
    protected String name;
    protected String address;
    protected String caf;
    protected String title;
    protected int titleId;
    protected String phone1;
    protected String phone2;
    protected String email1;
    protected String email2;
    protected boolean isOwner;

    public PersonVO() {
    }

    public PersonVO(int id, String passport, String name, String address, String caf, String title, int titleId, String phone1, String phone2, String email1, String email2, boolean isOwner) {
        this.id = id;
        this.passport = passport;
        this.name = name;
        this.address = address;
        this.caf = caf;
        this.title = title;
        this.titleId = titleId;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email1 = email1;
        this.email2 = email2;
        this.isOwner = isOwner;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean owner) {
        isOwner = owner;
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

    public String getCaf() {
        return caf;
    }

    public void setCaf(String caf) {
        this.caf = caf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonVO personVO = (PersonVO) o;

        if (id != personVO.id) return false;
        if (titleId != personVO.titleId) return false;
        if (isOwner != personVO.isOwner) return false;
        if (passport != null ? !passport.equals(personVO.passport) : personVO.passport != null) return false;
        if (name != null ? !name.equals(personVO.name) : personVO.name != null) return false;
        if (address != null ? !address.equals(personVO.address) : personVO.address != null) return false;
        if (caf != null ? !caf.equals(personVO.caf) : personVO.caf != null) return false;
        if (title != null ? !title.equals(personVO.title) : personVO.title != null) return false;
        if (phone1 != null ? !phone1.equals(personVO.phone1) : personVO.phone1 != null) return false;
        if (phone2 != null ? !phone2.equals(personVO.phone2) : personVO.phone2 != null) return false;
        if (email1 != null ? !email1.equals(personVO.email1) : personVO.email1 != null) return false;
        return email2 != null ? email2.equals(personVO.email2) : personVO.email2 == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (caf != null ? caf.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + titleId;
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
        result = 31 * result + (email1 != null ? email1.hashCode() : 0);
        result = 31 * result + (email2 != null ? email2.hashCode() : 0);
        result = 31 * result + (isOwner ? 1 : 0);
        return result;
    }
}
