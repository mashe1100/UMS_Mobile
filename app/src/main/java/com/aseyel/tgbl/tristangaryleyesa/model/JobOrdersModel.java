package com.aseyel.tgbl.tristangaryleyesa.model;

/**
 * Created by Romeo on 2018-01-02.
 */

public class JobOrdersModel {
    public  String JobOrderId;
    public  String Title;
    public  String Details;
    public  String Date;
    public  String Cycle;




    public JobOrdersModel(String JobOrderId, String Title, String Details, String Date, String Cycle) {

        this.JobOrderId = JobOrderId;
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;
        this.Cycle = Cycle;

    }

    public String getJobOrderId() {
        return JobOrderId;
    }

    public void setJobOrderId(String jobOrderId) {
        JobOrderId = jobOrderId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCycle() {
        return Cycle;
    }

    public void setCycle(String cycle) {
        Cycle = cycle;
    }

}
