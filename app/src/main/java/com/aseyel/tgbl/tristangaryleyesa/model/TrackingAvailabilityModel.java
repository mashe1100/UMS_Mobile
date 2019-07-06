package com.aseyel.tgbl.tristangaryleyesa.model;

/**
 * Created by Romeo on 2018-01-02.
 */

public class TrackingAvailabilityModel {
    public  String productCode;
    public  String productName;
    public  String productPrice;
    public  String productType;
    public  String productComment;
    public  String productTimeStamp;
    public  String productModifiedby;
    public  String productPicturePath;



    public TrackingAvailabilityModel(String productCode,
                                     String productName,
                                     String productPrice,
                                     String productType,
                                     String productComment,
                                     String productTimeStamp,
                                     String productPicturePath,
                                     String productModifiedby) {

        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productComment = productComment;
        this.productTimeStamp = productTimeStamp;
        this.productPicturePath = productPicturePath;
        this.productModifiedby = productModifiedby;

    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public String getProductTimeStamp() {
        return productTimeStamp;
    }

    public void setProductTimeStamp(String productTimeStamp) {
        this.productTimeStamp = productTimeStamp;
    }

    public String getProductModifiedby() {
        return productModifiedby;
    }

    public void setProductModifiedby(String productModifiedby) {
        this.productModifiedby = productModifiedby;
    }

    public String getProductPicturePath() {
        return productPicturePath;
    }

    public void setProductPicturePath(String productPicturePath) {
        this.productPicturePath = productPicturePath;
    }

}
