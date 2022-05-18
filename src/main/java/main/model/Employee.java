package main.model;


public class Employee {

    private Integer id;
    private String name;
    private String surName;
    private String tabNum;
    private Boolean active;

    public Employee(Integer id, String name, String surName, String tabNum, Boolean active) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.tabNum = tabNum;
        this.active = active;
    }

    public Employee(String name, String surName, String tabNum) {
        this.name = name;
        this.surName = surName;
        this.tabNum = tabNum;
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getTabNum() {
        return tabNum;
    }

    public void setTabNum(String tabNum) {
        this.tabNum = tabNum;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", tabNum='" + tabNum + '\'' +
                ", active=" + active +
                '}';
    }
}
