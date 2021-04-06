package com.example.mobdevspacexapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Launch {
    private int flightNumber;
    private String flightName;
    private String dateUtc;
    private String patchLinkSmall;
}
