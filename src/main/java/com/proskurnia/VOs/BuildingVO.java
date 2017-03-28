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
    private String ownerAccountId;
    private String ownerName;
    private int emptyApartments;
    private int ownerId;

    public BuildingVO() {
    }

    public BuildingVO(int id, String address, Timestamp acquisitionDate, Timestamp constructionDate, Timestamp dateOfSale, boolean manageable, String comment, String ownerAccountId, int emptyApartments, String ownerName, int ownerId) {
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
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
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
        if (!(o instanceof BuildingVO)) return false;

        BuildingVO that = (BuildingVO) o;

        if (getId() != that.getId()) return false;
        if (isManageable() != that.isManageable()) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getAcquisitionDate() != null ? !getAcquisitionDate().equals(that.getAcquisitionDate()) : that.getAcquisitionDate() != null)
            return false;
        if (getConstructionDate() != null ? !getConstructionDate().equals(that.getConstructionDate()) : that.getConstructionDate() != null)
            return false;
        if (getDateOfSale() != null ? !getDateOfSale().equals(that.getDateOfSale()) : that.getDateOfSale() != null)
            return false;
        if (getComment() != null ? !getComment().equals(that.getComment()) : that.getComment() != null) return false;
        return getOwnerAccountId() != null ? getOwnerAccountId().equals(that.getOwnerAccountId()) : that.getOwnerAccountId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getAcquisitionDate() != null ? getAcquisitionDate().hashCode() : 0);
        result = 31 * result + (getConstructionDate() != null ? getConstructionDate().hashCode() : 0);
        result = 31 * result + (getDateOfSale() != null ? getDateOfSale().hashCode() : 0);
        result = 31 * result + (isManageable() ? 1 : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (getOwnerAccountId() != null ? getOwnerAccountId().hashCode() : 0);
        return result;
    }
}
