package com.example.rahi.schedulemanagement.model;

public class ScheduleInfoViewModel {
    private int id;
    private String schDate, shift1, shift2, shift3;

    public ScheduleInfoViewModel(int id, String schDate, String shift1, String shift2, String shift3) {
        this.id = id;
        this.schDate = schDate;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getShift1() {
        return shift1;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public String getShift2() {
        return shift2;
    }

    public void setShift2(String shift2) {
        this.shift2 = shift2;
    }

    public String getShift3() {
        return shift3;
    }

    public void setShift3(String shift3) {
        this.shift3 = shift3;
    }
}
