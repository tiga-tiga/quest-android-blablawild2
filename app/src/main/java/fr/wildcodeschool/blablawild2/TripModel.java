package fr.wildcodeschool.blablawild2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bastienwcs on 15/04/18.
 */

public class TripModel implements Parcelable {

    private String departure;
    private String destination;
    private String date;

    public TripModel(String departure, String destination, String date) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
        @Override
        public TripModel createFromParcel(Parcel in) {
            return new TripModel(in);
        }

        @Override
        public TripModel[] newArray(int size) {
            return new TripModel[size];
        }
    };

    protected TripModel(Parcel in) {
        departure = in.readString();
        destination = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(departure);
        dest.writeString(destination);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
