package com.example.mobdevspacexapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Company {

    private String name;
    private String description;
    private String founder;
    private int foundedYear;
    private int numberOfEmployees;
    private int numberOfVehicles;
    private int numberOfLaunchSites;
    private int numberOfTestSites;
    private String websiteLink;
    private String flickrLink;
    private String twitterLink;

}
