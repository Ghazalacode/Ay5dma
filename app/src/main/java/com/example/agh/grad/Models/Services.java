package com.example.agh.grad.Models;


import org.parceler.Parcel;

import java.util.Map;

@Parcel(Parcel.Serialization.BEAN)
public class Services {

    String name;


    String serviceShortDesrption;
    String serviceProvider;
    Long phoneNumber;
    String serviceFullDesrption , locationAddress, locationName ;
    String latit , longit;
    Long likes,dislikes;

    Map<String, Boolean> tags ;
    Map<String, String> images ;

    public Services() {
    }


    public Services(String name, String serviceShortDesrption, String serviceProvider, Long phoneNumber, String serviceFullDesrption
            , String locationAddress, String locationName, String latit,String longit, Long likes, Long dislikes, Map<String, String> images, Map<String, Boolean> tags) {
        this.name = name;
        this.serviceShortDesrption = serviceShortDesrption;
        this.serviceProvider = serviceProvider;
        this.phoneNumber = phoneNumber;
        this.serviceFullDesrption = serviceFullDesrption;
        this.locationAddress = locationAddress;
        this.locationName = locationName;
        this.latit = latit;
        this.longit = longit;
        this.likes = likes;
      this.dislikes = dislikes;
        this.images = images;
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceShortDesrption(String serviceShortDesrption) {
        this.serviceShortDesrption = serviceShortDesrption;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setServiceFullDesrption(String serviceFullDesrption) {
        this.serviceFullDesrption = serviceFullDesrption;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLatit(String latit) {
        this.latit = latit;
    }
    public void setLongit(String longit) {
        this.longit = longit;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public void setDislikes(Long dislikes) {
       this. dislikes = dislikes;
    }

    public void setImages( Map<String, String>  images) {
        this.images = images;
    }

    public void setTags(Map<String, Boolean> tags) {
        this.tags = tags;
    }

    public String getName() {

        return name;
    }

    public String getServiceShortDesrption() {
        return serviceShortDesrption;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getServiceFullDesrption() {
        return serviceFullDesrption;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLatit() {
        return latit;
    }
    public String getLongit() {
        return longit;
    }
    public Long getLikes() {
        return likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public  Map<String, String>  getImages() {
        return images;
    }

    public Map<String, Boolean> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Services{" +
                "name='" + name + '\'' +
                ", serviceShortDesrption='" + serviceShortDesrption + '\'' +
                ", serviceProvider='" + serviceProvider + '\'' +
                ", phoneNumber='" + String.valueOf(phoneNumber) + '\'' +
                ", serviceFullDesrption='" + serviceFullDesrption + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", locationName='" + locationName + '\'' +
                ", location='" + latit +"," +longit+'\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", images="/* + if(images!=null){images.toString() }*/+
                ", tags=" + tags.toString() +
                '}';
    }
}