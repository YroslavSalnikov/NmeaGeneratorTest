
public class CoordinateConverter {

    private static final double a = 6378137.0; // Большая полуось эллипсоида Радиус Земли в метрах
    private static  final double b = 6356752.3142 ; // Малая полуось эллипсоида (полярный радиус Земли)
    private static final double e_sq = 6.69437999014e-3; // Эксцентриситет


// Конвертация геодезических координат (широта, долгота, высота) в ECEF (Earth-Centered, Earth-Fixed)
// Конвертирует геодезическую точку WGS-84 (широта, долгота, высота) в координаты ECEF (x, y, z).


    public static ECEF GeodeticToEcef(double lat, double lon, double h) {

        // Преобразование градусов в радианы
        double lambda = Math.toRadians(lat);  // Широта в радианах
        double phi = Math.toRadians(lon);     // Долгота в радианах

        // Вычисление синуса широты
        double s = Math.sin(lambda);
        // Вычисление радиуса кривизны в данной точке
        double N = a / Math.sqrt(1 - e_sq * s * s);

        //  Вычисление синуса и косинуса широты и долгот
        double sin_lambda = Math.sin(lambda);
        double cos_lambda = Math.cos(lambda);
        double cos_phi = Math.cos(phi);
        double sin_phi = Math.sin(phi);

        // Возвращение ECEF координат по формулам преобразования
        return new ECEF(
                (h + N) * cos_lambda * cos_phi,          // X координата
                (h + N) * cos_lambda * sin_phi,          // Y координата
                (h + (1 - e_sq) * N) * sin_lambda        // Z координата
        );
    }

    // Конвертирует координаты ECEF (x, y, z) в координаты East-North-Up (ENU) в локальной тангенциальной плоскости,
    // центрированной на геодезической точке (lat0, lon0, h0).


    public static ENU EcefToEnu(double x, double y, double z, double lat0, double lon0, double h0) {
        // Преобразование широты и долготы из градусов в радианы
        double lambda = Math.toRadians(lat0);  // Широта опорной точки
        double phi = Math.toRadians(lon0);     // Долгота опорной точки

        // Вычисление радиуса кривизны в данной точке
        double s = Math.sin(lambda);
        double N = a / Math.sqrt(1 - e_sq * s * s);

        // Вычисление синуса и косинуса широты и долготы
        double sin_lambda = Math.sin(lambda);
        double cos_lambda = Math.cos(lambda);
        double cos_phi = Math.cos(phi);
        double sin_phi = Math.sin(phi);

        // Вычисление координат ECEF для точки (lat0, lon0, h0)
        double x0 = (h0 + N) * cos_lambda * cos_phi;
        double y0 = (h0 + N) * cos_lambda * sin_phi;
        double z0 = (h0 + (1 - e_sq) * N) * sin_lambda;

        //  Вычисление разностей координат между целевой точкой и опорной
        double xd = x - x0;
        double yd = y - y0;
        double zd = z - z0;

        // Преобразование в координаты ENU (East, North, Up) с использованием матрицы поворота
        return new ENU(
                -sin_phi * xd + cos_phi * yd,     // East компонент
                -cos_phi * sin_lambda * xd - sin_lambda * sin_phi * yd + cos_lambda * zd,  // North компонент
                cos_lambda * cos_phi * xd + cos_lambda * sin_phi * yd + sin_lambda * zd    // Up компонент
        );
    }

    // Конвертация ENU обратно в ECEF
    // Обратное преобразование ecefToEnu.
    // Конвертирует координаты East-North-Up (xEast, yNorth, zUp) в
    // координаты ECEF (x, y, z).


    public static ECEF EnuToEcef(double xEast, double yNorth, double zUp, double lat0, double lon0, double h0) {

        // Преобразование широты и долготы из градусов в радианы
        double lambda = Math.toRadians(lat0);
        double phi = Math.toRadians(lon0);

        // Вычисление радиуса кривизны в данной точке
        double s = Math.sin(lambda);
        double N = a / Math.sqrt(1 - e_sq * s * s);

        // Вычисление синуса и косинуса широты и долготы
        double sin_lambda = Math.sin(lambda);
        double cos_lambda = Math.cos(lambda);
        double cos_phi = Math.cos(phi);
        double sin_phi = Math.sin(phi);

        // Вычисление координат ECEF для точки (lat0, lon0, h0)
        double x0 = (h0 + N) * cos_lambda * cos_phi;
        double y0 = (h0 + N) * cos_lambda * sin_phi;
        double z0 = (h0 + (1 - e_sq) * N) * sin_lambda;

        // Преобразование координат ENU обратно в ECEF Обратное преобразование из ENU в ECEF (обратная матрица поворота)
        double xd = -sin_phi * xEast - cos_phi * sin_lambda * yNorth + cos_lambda * cos_phi * zUp;
        double yd = cos_phi * xEast - sin_lambda * sin_phi * yNorth + cos_lambda * sin_phi * zUp;
        double zd = cos_lambda * yNorth + sin_lambda * zUp;


        // Возвращение к абсолютным ECEF координатам
        return new ECEF(
                xd + x0,  // X координата
                yd + y0,  // Y координата
                zd + z0   // Z координата
        );
    }


    // Конвертирует координаты ECEF (x, y, z) в геодезическую точку (lat, lon, h).
    public static Geodetic EcefToGeodetic(double x, double y, double z) {
        // Вспомогательные параметры эллипсоида
        double eps = e_sq / (1.0 - e_sq);
        // Расстояние от оси Z
        double p = Math.sqrt(x * x + y * y);

        // Вспомогательный угол
        double q = Math.atan2((z * a), (p * b));
        double sin_q = Math.sin(q);
        double cos_q = Math.cos(q);

        // Кубы тригонометрических функций для итеративного метода
        double sin_q_3 = sin_q * sin_q * sin_q;
        double cos_q_3 = cos_q * cos_q * cos_q;

        // Вычисление широты с учетом сжатия Земли
        double phi = Math.atan2(
                (z + eps * b * sin_q_3),
                (p - e_sq * a * cos_q_3)
        );

        // Долгота - простой арктангенс
        double lambda = Math.atan2(y, x);

        // Радиус кривизны первого вертикала
        double v = a / Math.sqrt(1.0 - e_sq * Math.sin(phi) * Math.sin(phi));

        // Возвращение геодезических координат (в градусах) и высоты
        return new Geodetic(
                Math.toDegrees(phi),          // Широта в градусах
                Math.toDegrees(lambda),       // Долгота в градусах
                (p / Math.cos(phi)) - v       // Высота над эллипсоидом
        );
    }
}