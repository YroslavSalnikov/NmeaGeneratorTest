package utils;

import java.io.Serializable;

public class EnuBase implements Serializable {
    public double Latitude;
    public double Longitude;
    public double Altitude;

    public EnuBase(double latitude, double longitude, double altitude) {
        Latitude = latitude;
        Longitude = longitude;
        Altitude = altitude;
    }

    public EnuBase() {
        this(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    }

    public void reset(double latitude, double longitude, double altitude) {
        Latitude = latitude;
        Longitude = longitude;
        Altitude = altitude;
    }

    public boolean isInitialized() {
        return (Latitude!=Double.MIN_VALUE && Longitude!=Double.MIN_VALUE && Altitude!=Double.MIN_VALUE);
    }

    @Override
    public String toString() {
        return "EnuBase{" +
                "Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", Altitude=" + Altitude +
                '}';
    }
}
