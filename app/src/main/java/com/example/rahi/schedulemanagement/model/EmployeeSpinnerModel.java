package com.example.rahi.schedulemanagement.model;

public class EmployeeSpinnerModel {
    private int id;
    private String empName;

    public EmployeeSpinnerModel(int id, String empName) {
        this.id = id;
        this.empName = empName;
    }

    public EmployeeSpinnerModel(String empName) {
        this.empName = empName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
