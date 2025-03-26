
public class CalculationOfTheTrajectoryOfMovement {

    // Параметры автомобиля
    private static final double L = 2.3; // Длинна колёсной базы (м)
    private static final double W = 2.23; // Ширина колеи (м) // не используется
    private static final double V = 10.0; // Скорость автомобиля (м/с)
    private static final double dt = 0.1; // Шаг по времени (с)
    private static final  double K = 34.24; // Передаточное отношение рулевого мех.

    // Начальные условия

    private static double x = 0.0; // Начальная координата X (м)
    private static double y = 0.0; // Начальная координата Y (м)
    private static double psi; // Расчетный угол ориентации колес во времени (рад)
    private static double theta_i_rad;


    public static void main(String[] args) {

        double theta_R = 270; // Угол поворота рулевого колеса (theta_R)


        // Шаг 1: Рассчитаем угол поворота внутреннего колеса
        double theta_i; // Угол поворота внутреннего колеса
        theta_i = theta_R /K;

        convertDegreesToRadians (theta_i); // Переводим в радианы theta_i угол поворота внутреннего колеса получаем theta_i_rad
        SimulatingMovement(); // Выводим координаты x и y каждую 0,1 сек.

        }

    private static void convertDegreesToRadians (double theta_i)  {
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
        // Время работы цикла моделирования (с)
        double simulationTime = 10.0;


        System.out.println("Time (s)\tX (m)\t\tY (m)\t\tPsi (rad)");
        for (double t = 0; t <= simulationTime; t += dt) {

            // Выводим текущие координаты и угол
            System.out.printf("%.2f\t\t%.3f\t%.3f\t%.3f%n", t, x, y, psi);

            // Обновляем координат x и y (расчетное состояния автомобиля)
            upDateState();

        }
    }
}