package com.aseyel.tgbl.tristangaryleyesa.model;

public class TranslatorModel {
    public String Title;
    public String Mesasge;
    public String TimeStamp;
    public int MessageType;



    public TranslatorModel(String Title, String Message, String TimeStamp, int MessageType){
        this.Title = Title;
        this.Mesasge = Message;
        this.TimeStamp = TimeStamp;
        this.MessageType = MessageType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMesasge() {
        return Mesasge;
    }

    public void setMesasge(String mesasge) {
        Mesasge = mesasge;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }
}
