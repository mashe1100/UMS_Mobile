package com.aseyel.tgbl.tristangaryleyesa.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Romeo on 2018-01-02.
 */

public class CokeMTCustomersModel {
    private String INCLUDED;
    private String MKTSGM;
    private String SEGM;
    private String CU;
    private String REGION;
    private String Customer_No;
    private String Sales_Route ;
    private String Name;
    private String Name_2;
    private String City;
    private String Postal_Code;
    private String Street;
    private String House_No;
    private String Street_4;
    private String Longitude;
    private String Latitude;
    private String Remarks;
    private String period;

    public CokeMTCustomersModel(ArrayList<HashMap<String, String>> mCokeMTCustomers){
        this.INCLUDED = mCokeMTCustomers.get(0).get("INCLUDED");
        this.MKTSGM = mCokeMTCustomers.get(0).get("MKTSGM");
        this.SEGM = mCokeMTCustomers.get(0).get("SEGM");
        this.CU = mCokeMTCustomers.get(0).get("CU");
        this.REGION = mCokeMTCustomers.get(0).get("REGION");
        this.Customer_No = mCokeMTCustomers.get(0).get("Customer_No");
        this.Sales_Route = mCokeMTCustomers.get(0).get("Sales_Route");
        this.Name = mCokeMTCustomers.get(0).get("Name");
        this.Name_2 = mCokeMTCustomers.get(0).get("Name_2");
        this.City = mCokeMTCustomers.get(0).get("City");
        this.Postal_Code = mCokeMTCustomers.get(0).get("Postal_Code");
        this.Street = mCokeMTCustomers.get(0).get("Street");
        this.House_No = mCokeMTCustomers.get(0).get("House_No");
        this.Street_4 = mCokeMTCustomers.get(0).get("Street_4");
        this.Longitude = mCokeMTCustomers.get(0).get("Longitude");
        this.Latitude = mCokeMTCustomers.get(0).get("Latitude");
        this.Remarks = mCokeMTCustomers.get(0).get("Remarks");
        this.period = mCokeMTCustomers.get(0).get("period");
    }

    public String getINCLUDED() {
        return INCLUDED;
    }

    public void setINCLUDED(String INCLUDED) {
        this.INCLUDED = INCLUDED;
    }

    public String getMKTSGM() {
        return MKTSGM;
    }

    public void setMKTSGM(String MKTSGM) {
        this.MKTSGM = MKTSGM;
    }

    public String getSEGM() {
        return SEGM;
    }

    public void setSEGM(String SEGM) {
        this.SEGM = SEGM;
    }

    public String getCU() {
        return CU;
    }

    public void setCU(String CU) {
        this.CU = CU;
    }

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }

    public String getCustomer_No() {
        return Customer_No;
    }

    public void setCustomer_No(String customer_No) {
        Customer_No = customer_No;
    }

    public String getSales_Route() {
        return Sales_Route;
    }

    public void setSales_Route(String sales_Route) {
        Sales_Route = sales_Route;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName_2() {
        return Name_2;
    }

    public void setName_2(String name_2) {
        Name_2 = name_2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouse_No() {
        return House_No;
    }

    public void setHouse_No(String house_No) {
        House_No = house_No;
    }

    public String getStreet_4() {
        return Street_4;
    }

    public void setStreet_4(String street_4) {
        Street_4 = street_4;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
