package com.aseyel.tgbl.tristangaryleyesa.model;

/**
 * Created by Romeo on 2018-01-05.
 */

public class LocalJobOrdersModel {
    public  String JobOrderId;
    public  String Title;
    public  String Details;
    public  String Date;


    public LocalJobOrdersModel(String JobOrderId, String Title, String Details, String Date) {

        this.JobOrderId = JobOrderId;
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;

    }

    public String getJobOrderId() {
        return JobOrderId;
    }

    public void setLocalJobOrderId(String jobOrderId) {
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

}
