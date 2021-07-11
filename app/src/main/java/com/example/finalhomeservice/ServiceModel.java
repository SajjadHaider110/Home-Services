package com.example.finalhomeservice;

public class ServiceModel {

    String Address,FullName,Mobile,url;

    public ServiceModel() {
    }

    public ServiceModel(String Address, String FullName, String Mobile, String url) {
        this.Address = Address;
        this.FullName = FullName;
        this.Mobile = Mobile;
        this.url=url;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }
}
