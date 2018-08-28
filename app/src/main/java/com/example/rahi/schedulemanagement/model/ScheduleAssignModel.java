package com.example.rahi.schedulemanagement.model;

public class ScheduleAssignModel {
    private int vId, vEmpId, vS1Checked, vS2Checked, vS3Checked, vS4Checked;
    private String selectDate;

    public ScheduleAssignModel(int vId, int vEmpId, String selectDate, int vS1Checked, int vS2Checked, int vS3Checked, int vS4Checked) {
        this.vId = vId;
        this.vEmpId = vEmpId;
        this.selectDate = selectDate;
        this.vS1Checked = vS1Checked;
        this.vS2Checked = vS2Checked;
        this.vS3Checked = vS3Checked;
        this.vS4Checked = vS4Checked;
    }

    public ScheduleAssignModel(int vEmpId, String selectDate, int vS1Checked, int vS2Checked, int vS3Checked, int vS4Checked) {
        this.vEmpId = vEmpId;
        this.vS1Checked = vS1Checked;
        this.selectDate = selectDate;
        this.vS2Checked = vS2Checked;
        this.vS3Checked = vS3Checked;
        this.vS4Checked = vS4Checked;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public int getvEmpId() {
        return vEmpId;
    }

    public void setvEmpId(int vEmpId) {
        this.vEmpId = vEmpId;
    }

    public int getvS1Checked() {
        return vS1Checked;
    }

    public void setvS1Checked(int vS1Checked) {
        this.vS1Checked = vS1Checked;
    }

    public int getvS2Checked() {
        return vS2Checked;
    }

    public void setvS2Checked(int vS2Checked) {
        this.vS2Checked = vS2Checked;
    }

    public int getvS3Checked() {
        return vS3Checked;
    }

    public void setvS3Checked(int vS3Checked) {
        this.vS3Checked = vS3Checked;
    }

    public int getvS4Checked() {
        return vS4Checked;
    }

    public void setvS4Checked(int vS4Checked) {
        this.vS4Checked = vS4Checked;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }
}
