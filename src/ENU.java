

public class ENU {
    public double xEast;
    public double yNorth;
    public double zUp;

    public ENU(double x, double y, double z) {
        xEast = x;
        yNorth = y;
        zUp = z;


        CoordinateConverter.EcefToGeodetic(xEast, yNorth, zUp);
    }

}