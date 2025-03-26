// Часть 1


import java.util.ArrayList;

public class Test {


        public static void main(String[] args) {
            // Данные
            double v = 10.0; // скорость в м/с
            double theta_i = 0.242759432; // угол поворота внутреннего колеса в радианах
            double W = 1.5; // ширина колеи в м
            double L = 2.5; // длина колёсной базы в м
            double delta_t = 0.1; // временной интервал в с
            double total_time = 60.0; // общее время в с


            // Начальные условия
            double x = 0.0; // начальная координата x
            double y = 0.0; // начальная координата y
            double psi = 0.0; // начальный угол ориентации


            // Вычисление радиуса поворота
            double R = (L / Math.tan(theta_i)) + (W / 2);


            // Вычисление угловой скорости
            double omega = v / R;


            // Список для хранения координат
            ArrayList<double[]> trajectory = new ArrayList<>();


            // Цикл расчета
            for (double t = 0; t < total_time; t += delta_t) {
                // Обновление угла ориентации
                psi += omega * delta_t;


                // Вычисление изменения координат
                double delta_x = v * Math.cos(psi) * delta_t;
                double delta_y = v * Math.sin(psi) * delta_t;


                // Обновление координат
                x += delta_x;
                y += delta_y;


                // Сохранение текущих координат
                trajectory.add(new double[]{x, y});
            }


            // Вывод траектории
            System.out.println("Траектория автомобиля (x, y):");
            for (double[] point : trajectory) {
                System.out.printf("%.4f %.4f\n", point[0], point[1]);
            }
        }
    }


