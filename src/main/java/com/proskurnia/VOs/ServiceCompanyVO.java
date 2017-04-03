package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class ServiceCompanyVO implements Identified<Integer> {

    private int id;
    private String name;
    private String address;
    private String phone1;
    private String phone2;
    private String email;
    private int typeId;
    private String typeName;
    private String comment;

    public ServiceCompanyVO() {
    }

    public ServiceCompanyVO(int id, String name, String address, String phone1, String phone2, String email, int typeId, String comment, String typeName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.typeId = typeId;
        this.comment = comment;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceCompanyVO that = (ServiceCompanyVO) o;

        if (id != that.id) return false;
        if (typeId != that.typeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (phone1 != null ? !phone1.equals(that.phone1) : that.phone1 != null) return false;
        if (phone2 != null ? !phone2.equals(that.phone2) : that.phone2 != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
