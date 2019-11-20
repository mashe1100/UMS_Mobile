package com.aseyel.tgbl.tristangaryleyesa.model;

/**
 * Created by Romeo on 2018-01-02.
 */

public class JobOrdersDetailsModel {
    public String JobOrderId;
    public String AccountNumber;
    public String Title;
    public String Details;
    public String Date;
    public String AccountName;
    public String MeterNumber;
    public double Latitude;
    public double Longitude;
    public String JobOrderDate;
    public String Status;
    public String Accomplishment;
    public String Print;





    public JobOrdersDetailsModel(String JobOrderId, String AccountNumber, String AccountName, String MeterNumber, String Title, String Details, String Date, double Latitude, double Longitude, String JobOrderDate, String Status,String Accomplishment,String Print) {

        this.JobOrderId = JobOrderId;
        this.AccountNumber = AccountNumber;
        this.AccountName = AccountName;
        this.MeterNumber = MeterNumber;
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.JobOrderDate = JobOrderDate;
        this.Accomplishment = Accomplishment;
        this.Status = Status;
        this.Print = Print;

    }

    public String getJobOrderId() {
        return JobOrderId;
    }

    public void setJobOrderId(String jobOrderId) {
        JobOrderId = jobOrderId;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
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

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getMeterNumber() {
        return MeterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        MeterNumber = meterNumber;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getJobOrderDate() {
        return JobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        JobOrderDate = jobOrderDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAccomplishment() {
        return Accomplishment;
    }

    public void setAccomplishment(String accomplishment) {
        Accomplishment = accomplishment;
    }
}
