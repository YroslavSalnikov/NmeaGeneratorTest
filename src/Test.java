// Часть 1



public class Test {
    public static double latitudeIn;
    public static double longitudeIn;
    public static double altitudeIn;

    double x = 4203000.0, y = 172000.0, z = 4780000.0;



    public static class Geodetic{
        public Geodetic(double lat, double lon, double h) {
            latitudeIn = lat;
            longitudeIn = lon;
            altitudeIn = h;
        }
    }


    private static final double a = 6378137.0; // Большая полуось эллипсоида Радиус Земли в метрах
    private static  final double b = 6356752.3142 ; // Малая полуось эллипсоида (полярный радиус Земли)
    private static final double e_sq = 6.69437999014e-3; // Эксцентриситет





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
