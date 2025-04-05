
/*
public class EcefToEnuConverter {
    // Параметры эллипсоида WGS84
    private static final double a = 6378137.0;          // большая полуось (м)
    private static final double f = 1 / 298.257223563;  // сжатие
    private static final double e2 = 2*f - f*f;        // квадрат эксцентриситета

    public static class EnuCoordinates {
        public double east;    // метры
        public double north;   // метры
        public double up;      // метры

        public EnuCoordinates(double e, double n, double u) {
            this.east = e;
            this.north = n;
            this.up = u;
        }
    }

    /**
     * Преобразование utils.ECEF в ENU
     * @param x, y, z - целевая точка в utils.ECEF (метры)
     * @param lat0, lon0, h0 - опорная точка (широта, долгота в градусах, высота в метрах)
     * @return ENU координаты
     */

/*
    public static EnuCoordinates ecefToEnu(double x, double y, double z,
                                           double lat0, double lon0, double h0) {
        // 1. Сначала преобразуем опорную точку в utils.ECEF
        double[] ecef0 = geodeticToEcef(lat0, lon0, h0);
        double x0 = ecef0[0], y0 = ecef0[1], z0 = ecef0[2];

        // 2. Вычисляем разницы координат
        double dx = x - x0;
        double dy = y - y0;
        double dz = z - z0;

        // 3. Вычисляем тригонометрические функции опорной точки
        double phi = Math.toRadians(lat0);
        double lambda = Math.toRadians(lon0);
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double sinLambda = Math.sin(lambda);
        double cosLambda = Math.cos(lambda);

        // 4. Применяем матрицу поворота
        double east = -sinLambda*dx + cosLambda*dy;
        double north = -sinPhi*cosLambda*dx - sinPhi*sinLambda*dy + cosPhi*dz;
        double up = cosPhi*cosLambda*dx + cosPhi*sinLambda*dy + sinPhi*dz;

        return new EnuCoordinates(east, north, up);
    }

    /**
     * Преобразование геодезических координат в utils.ECEF
     */

/*
    private static double[] geodeticToEcef(double lat, double lon, double h) {
        double phi = Math.toRadians(lat);
        double lambda = Math.toRadians(lon);
        double sinPhi = Math.sin(phi);
        double N = a / Math.sqrt(1 - e2*sinPhi*sinPhi);

        double x = (N + h) * Math.cos(phi) * Math.cos(lambda);
        double y = (N + h) * Math.cos(phi) * Math.sin(lambda);
        double z = (N*(1 - e2) + h) * sinPhi;

        return new double[]{x, y, z};
    }

    public static void main(String[] args) {
        // Пример: преобразование точки рядом с Эйфелевой башней
        // utils.ECEF координаты точки (примерные)
        double x = 4203000.0, y = 172000.0, z = 4780000.0;

        // Опорная точка (Эйфелева башня)
        double lat0 = 48.8583, lon0 = 2.2945, h0 = 300;

        EnuCoordinates enu = ecefToEnu(x, y, z, lat0, lon0, h0);
        System.out.printf("ENU координаты:\nEast: %.2f м\nNorth: %.2f м\nUp: %.2f м\n",
                enu.east, enu.north, enu.up);
    }
}
*/