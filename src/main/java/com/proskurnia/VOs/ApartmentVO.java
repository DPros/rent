package com.proskurnia.VOs;

/**
 * Created by dmpr0116 on 07.03.2017.
 */
public class ApartmentVO implements Identified<Integer> {
    private int id;
    private String roomNumber;
    private double size;
    private int buildingId;

    public ApartmentVO() {
    }

    public ApartmentVO(int id, String roomNumber, double size, int buildingId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.size = size;
        this.buildingId = buildingId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}