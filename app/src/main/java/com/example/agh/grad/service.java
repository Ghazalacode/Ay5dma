package com.example.agh.grad;

/**
 * Created by cz on 17/06/17.
 */

public class service {
    String serviceName;


    String serviceShortDesrption;
    String serviceProvider;
    String phoneNumber;
    String serviceFullDesrption;
    String location;
    int likeCounter, DislikeCounter, imagurl;

    public int getLikeCounter() {
        return likeCounter;
    }

    public int getDislikeCounter() {
        return DislikeCounter;
    }

    public String getLocation() {
        return location;
    }

    public String getServiceFullDesrption() {

        return serviceFullDesrption;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDesrption() {
        return serviceShortDesrption;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public int getImagurl() {
        return imagurl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public service(String serviceName, String serviceDesrption, String serviceProvider, String phoneNumber, String location, String serviceFullDesrption) {
        this.serviceName = serviceName;
        this.serviceShortDesrption = serviceDesrption;
        this.serviceProvider = serviceProvider;
        this.phoneNumber = phoneNumber;
        this.serviceFullDesrption = serviceFullDesrption;
        this.location = location;

    }


}
