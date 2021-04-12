package com.example.mobdevspacexapp.data.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rocket implements Parcelable {

    private String name;
    private String imageLink;
    private String description;
    private boolean isActive;
    private float heightMeters;
    private float heightFeet;
    private float diameterMeters;
    private float diameterFeet;
    private long massLbs;
    private long massKgs;
    private String firstFlightDate;

    protected Rocket(Parcel in) {
        name = in.readString();
        imageLink = in.readString();
        description = in.readString();
        isActive = in.readByte() != 0;
        heightMeters = in.readFloat();
        heightFeet = in.readFloat();
        diameterMeters = in.readFloat();
        diameterFeet = in.readFloat();
        massLbs = in.readLong();
        massKgs = in.readLong();
        firstFlightDate = in.readString();
    }

    public static final Creator<Rocket> CREATOR = new Creator<Rocket>() {
        @Override
        public Rocket createFromParcel(Parcel in) {
            return new Rocket(in);
        }

        @Override
        public Rocket[] newArray(int size) {
            return new Rocket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageLink);
        dest.writeString(description);
        dest.writeBoolean(isActive);
        dest.writeFloat(heightMeters);
        dest.writeFloat(heightFeet);
        dest.writeFloat(diameterMeters);
        dest.writeFloat(diameterFeet);
        dest.writeLong(massLbs);
        dest.writeLong(massKgs);
        dest.writeString(firstFlightDate);
    }
}
