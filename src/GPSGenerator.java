
// Часть 2

    import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

    public class GPSGenerator {
        private static final Random random = new Random();
        private static double latitude = 37.7749; // начальная широта
        private static double longitude = -122.4194; // начальная долгота
        private static double speed = 50.0; // скорость в узлах
        private static double heading = 90.0; // угол поворота в градусах

        public static void main(String[] args) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                private int count = 0;

                @Override
                public void run() {
                    if (count < 600) { // 600 раз по 100 мс = 60 секунд
                        String ggaMessage = generateGPGGA();
                        String vtgMessage = generateGPVTG(speed, heading);
                        String zdaMessage = generateGPZDA();

                        System.out.println(ggaMessage);
                        System.out.println(vtgMessage);
                        System.out.println(zdaMessage);

                        updatePosition(); // обновление позиции
                        count++;
                    } else {
                        timer.cancel(); // остановка таймера после 1 минуты
                    }
                }
            }, 0, 100); // запуск сразу и каждые 100 мс
        }

        private static String generateGPGGA() {
            String time = new SimpleDateFormat("HHmmss.SSS").format(new Date());
            String quality = "1"; // 1 = GPS fix
            int satellites = 12; // количество спутников
            double hdop = 0.8; // горизонтальная точность
            double altitude = 10.0; // высота в метрах

            return String.format("$GPGGA,%s,%f,N,%f,W,%s,%d,%.1f,%f,M,0.0,M,,*",
                    time, latitude, longitude, quality, satellites, hdop, altitude);
        }

        private static String generateGPVTG(double speed, double heading) {
            return String.format("$GPVTG,%.1f,T,%.1f,M,%.1f,N,%.1f,K*",
                    heading, heading, speed, speed * 1.15078); // 1 узел = 1.15078 миль в час
        }

        private static String generateGPZDA() {
            String time = new SimpleDateFormat("HHmmss").format(new Date());
            String date = new SimpleDateFormat("ddMMyy").format(new Date());

            return String.format("$GPZDA,%s,%s,00,00*",
                    time, date);
        }

        private static void updatePosition() {
            // Конвертация скорости из узлов в градусы
            double distanceInDegrees = speed * 0.000164578; // 1 узел = 0.000164578 градуса в секунду
            latitude += distanceInDegrees * Math.cos(Math.toRadians(heading));
            longitude += distanceInDegrees * Math.sin(Math.toRadians(heading));
        }
    }



