

public class CalculationOfTheTrajectoryOfMovement {

    private static double theta;
    private static double V; // Скорость автомобиля (м/с)

    public void inSpeedAndAngle(double SPEED, double theta_v) {
        V = SPEED;
        theta = theta_v;
    }


    private static final double lat0 = 52.0; // Начальная широта
    private static final double lon0 = 47.0; // Начальная долгота
    private static final double h0 = 0.0; // Высота не меняется равно 0

    // Параметры автомобиля
    private static final double L = 2.3; // Длинна колёсной базы (м)
    // private static final double W = 2.23; // Ширина колеи (м) // не используется
    private static final double dt = 0.1; // Шаг по времени (с)
    private static final double K = 34.24; // Передаточное отношение рулевого мех.

    // Начальные условия
    private static double x = 0.0; // Начальная координата X (м)
    private static double y = 0.0; // Начальная координата Y (м)
    private static double z = 0.0; // Координата высоты (всегда равна 0)
    private static double psi; // Расчетный угол ориентации колес во времени (рад)
    private static double theta_i_rad; // Угол поворота внутреннего колеса в радианах


    public void startCalculationOfTheTrajectory() {

        // Шаг 1: Рассчитаем угол поворота внутреннего колеса

        // Угол поворота внутреннего колеса
        double theta_i = theta / K;

        GPSGenerator.setTurnAngle(theta_i); // Передаем угол поворота внутреннего колеса в GPSGenerator

        convertDegreesToRadians(theta_i); // Переводим в радианы theta_i угол поворота внутреннего колеса получаем theta_i_rad
        SimulatingMovement(); // Выводим координаты x и y каждую 0,1 сек.

    }


    private static void convertDegreesToRadians(double theta_i) {
        // Шаг 2: Переводим угол поворота внутреннего колеса в радианы
        double pi = 3.1415926535;
        theta_i_rad = (theta_i * pi) / 180;
    }

    // Метод создан для расчёта радиуса поворота
    private static double calculateRadius(double theta_i_rad) {
        return (L / Math.tan(theta_i_rad));
    }


    // Метод для обновления координат x и y (расчетное состояния автомобиля)
    private static void upDateState() {
        // Шаг 3: Рассчитываем радиус поворота машины
        double R = calculateRadius(theta_i_rad);

        // Шаг 4: Рассчитываем угловую скорость
        double omega = V / R; // (рад/c)

        // Обновляем угол ориентации
        psi += omega * dt; // (рад)

        // Рассчитываем изменение координат
        double dx = V * Math.cos(psi) * dt;
        double dy = V * Math.sin(psi) * dt;

        // Обновляем координаты
        x += dx;
        y += dy;
    }

    // Метод для вывода координат x и y каждую 0,1 сек.
    private static void SimulatingMovement() {
        // Шаг 2: Моделируем движение

        System.out.println("Time (s)\tX (m)\t\tY (m)\t\tPsi (rad)");
       // for (double t = 0; t <= simulationTime; t += dt) {
            while (true){
            // Передаем текущие координаты и угол в EcefToEnu
            CoordinateConverter.EcefToEnu(x, y, z, lat0, lon0, h0);

            // Обновляем координат x и y (расчетное состояния автомобиля)
            upDateState();
                GPSGenerator gpsGenerator = new GPSGenerator();
                gpsGenerator.inSpeed(V);
                GPSGenerator.transmit();


        }
    }
}