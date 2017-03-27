package com.proskurnia.VOs;

import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class BuildingVO implements Identified<Integer> {
    private int id;
    private String address;
    private Timestamp acquisitionDate;
    private Timestamp constructionDate;
    private Timestamp dateOfSale;
    private boolean manageable;
    private String comment;
    private int ownerAccountId;
    private String ownerName;
    private int emptyApartments;

    public BuildingVO() {
    }

    public BuildingVO(int id, String address, Timestamp acquisitionDate, Timestamp constructionDate, Timestamp dateOfSale, boolean manageable, String comment, int ownerAccountId, int emptyApartments, String ownerName) {
        this.id = id;
        this.address = address;
        this.ownerName = ownerName;
        this.acquisitionDate = acquisitionDate;
        this.constructionDate = constructionDate;
        this.dateOfSale = dateOfSale;
        this.manageable = manageable;
        this.comment = comment;
        this.ownerAccountId = ownerAccountId;
        this.emptyApartments = emptyApartments;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Timestamp acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Timestamp getConstructionDate() {
        return constructionDate;
    }

    public void setConstructionDate(Timestamp constructionDate) {
        this.constructionDate = constructionDate;
    }

    public Timestamp getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Timestamp dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public boolean isManageable() {
        return manageable;
    }

    public void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(int ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public int getEmptyApartments() {
        return emptyApartments;
    }

    public void setEmptyApartments(int emptyApartments) {
        this.emptyApartments = emptyApartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingVO that = (BuildingVO) o;

        if (id != that.id) return false;
        if (manageable != that.manageable) return false;
        if (ownerAccountId != that.ownerAccountId) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (acquisitionDate != null ? !acquisitionDate.equals(that.acquisitionDate) : that.acquisitionDate != null)
            return false;
        if (constructionDate != null ? !constructionDate.equals(that.constructionDate) : that.constructionDate != null)
            return false;
        if (dateOfSale != null ? !dateOfSale.equals(that.dateOfSale) : that.dateOfSale != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (acquisitionDate != null ? acquisitionDate.hashCode() : 0);
        result = 31 * result + (constructionDate != null ? constructionDate.hashCode() : 0);
        result = 31 * result + (dateOfSale != null ? dateOfSale.hashCode() : 0);
        result = 31 * result + (manageable ? 1 : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + ownerAccountId;
        return result;
    }
}
