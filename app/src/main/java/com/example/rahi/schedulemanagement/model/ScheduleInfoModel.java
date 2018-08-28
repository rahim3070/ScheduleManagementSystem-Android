package com.example.rahi.schedulemanagement.model;

public class ScheduleInfoModel {
    private int id;
    private String fromDate, toDate, shiftFrom1, shiftTo1, shiftFrom2, shiftTo2, shiftFrom3, shiftTo3;

    public ScheduleInfoModel(int id, String fromDate, String toDate, String shiftFrom1, String shiftTo1, String shiftFrom2, String shiftTo2, String shiftFrom3, String shiftTo3) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.shiftFrom1 = shiftFrom1;
        this.shiftTo1 = shiftTo1;
        this.shiftFrom2 = shiftFrom2;
        this.shiftTo2 = shiftTo2;
        this.shiftFrom3 = shiftFrom3;
        this.shiftTo3 = shiftTo3;
    }

    public ScheduleInfoModel(String fromDate, String toDate, String shiftFrom1, String shiftTo1, String shiftFrom2, String shiftTo2, String shiftFrom3, String shiftTo3) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.shiftFrom1 = shiftFrom1;
        this.shiftTo1 = shiftTo1;
        this.shiftFrom2 = shiftFrom2;
        this.shiftTo2 = shiftTo2;
        this.shiftFrom3 = shiftFrom3;
        this.shiftTo3 = shiftTo3;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getShiftFrom1() {
        return shiftFrom1;
    }

    public void setShiftFrom1(String shiftFrom1) {
        this.shiftFrom1 = shiftFrom1;
    }

    public String getShiftTo1() {
        return shiftTo1;
    }

    public void setShiftTo1(String shiftTo1) {
        this.shiftTo1 = shiftTo1;
    }

    public String getShiftFrom2() {
        return shiftFrom2;
    }

    public void setShiftFrom2(String shiftFrom2) {
        this.shiftFrom2 = shiftFrom2;
    }

    public String getShiftTo2() {
        return shiftTo2;
    }

    public void setShiftTo2(String shiftTo2) {
        this.shiftTo2 = shiftTo2;
    }

    public String getShiftFrom3() {
        return shiftFrom3;
    }

    public void setShiftFrom3(String shiftFrom3) {
        this.shiftFrom3 = shiftFrom3;
    }

    public String getShiftTo3() {
        return shiftTo3;
    }

    public void setShiftTo3(String shiftTo3) {
        this.shiftTo3 = shiftTo3;
    }
}
