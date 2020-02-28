package com.aseyel.tgbl.tristangaryleyesa.model;

/**
 * Created by Romeo on 2018-01-02.
 */

public class TrackingActivationModel {
    public  String Id;
    public  String Title;
    public  String Details;
    public  String Date;
    public String Filepath;
    public String Category;

    public TrackingActivationModel(String Id, String Title, String Details, String Date,String Category, String Filepath) {
        this.Id = Id;
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;
        this.Filepath = Filepath;
        this.Category = Category;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getFilepath() {
        return Filepath;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
