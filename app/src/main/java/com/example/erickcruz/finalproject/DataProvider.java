package com.example.erickcruz.finalproject;

//Setting up setters and getters to display the information from the table
//Purpose of this class is to provide each row of data in the form of an objects

public class DataProvider {
    private String companyName;
    private String jobTitle;
    private String jobDesc;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public DataProvider(String companyName, String jobTitle, String jobDesc){
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;

    }
}
