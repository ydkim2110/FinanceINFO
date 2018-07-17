package com.example.anti2110.financeinfo.Model;

public class Info {

    private String category;
    private String companyName;
    private String endDate;
    private String startDate;
    private String title;


    public Info(String category, String companyName, String endDate, String startDate, String title) {
        this.category = category;
        this.companyName = companyName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.title = title;
    }

    public Info() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
