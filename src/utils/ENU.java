package utils;

public class ENU {
    public double xEast;
    public double yNorth;
    public double zUp;

    public ENU(double x, double y, double z)
    {
        xEast = x;
        yNorth = y;
        zUp = z;
    }

    @Override
    public String toString() {
        return "utils.ENU{" +
                "xEast=" + xEast +
                ", yNorth=" + yNorth +
                ", zUp=" + zUp +
                '}';
    }
}