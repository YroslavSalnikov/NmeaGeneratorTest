package utils;

public class Geodetic  {
    public double latitude;
    public double longitude;
    public double altitude;
    public Geodetic(double latitude, double longitude, double altitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return  "lat:" + latitude +
                ", lon:" + longitude +
                ", alt:" + altitude;
    }
}