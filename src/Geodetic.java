

public class Geodetic {
    public double latitude;
    public double longitude;
    public double altitude;

    public Geodetic(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;

        System.out.println(latitude + " " + "широта");
        System.out.println(longitude + " " + "долгота");
        System.out.println(altitude + " " + "высота");


        transmitLatLonAlt(); // Метод для передачи широты, долготы, высоты
    }


    public void transmitLatLonAlt() {
        GPSGenerator gpsGenerator = new GPSGenerator();
        gpsGenerator.transmitLatLonAlt(latitude, longitude, altitude);
    }
}