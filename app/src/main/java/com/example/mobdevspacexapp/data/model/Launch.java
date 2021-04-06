package com.example.mobdevspacexapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Launch implements Parcelable {
    private int flightNumber;
    private String name;
    private String detail;
    private String dateUtc;
    private String patchLinkSmall;
    private Long dateTimeUnix;

    protected Launch(Parcel in) {
        flightNumber = in.readInt();
        name = in.readString();
        detail = in.readString();
        dateUtc = in.readString();
        patchLinkSmall = in.readString();
        dateTimeUnix = in.readLong();
    }

    public static final Creator<Launch> CREATOR = new Creator<Launch>() {
        @Override
        public Launch createFromParcel(Parcel in) {
            return new Launch(in);
        }

        @Override
        public Launch[] newArray(int size) {
            return new Launch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flightNumber);
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeString(dateUtc);
        dest.writeString(patchLinkSmall);
        dest.writeLong(dateTimeUnix);
    }
}
