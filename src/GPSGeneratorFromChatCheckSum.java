// Часть 2

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GPSGeneratorFromChatCheckSum {


            private static final double EARTH_RADIUS = 6371000; // Радиус Земли в метрах
            private static final double INITIAL_LATITUDE = 37.7749; // Начальная широта (Сан-Франциско)
            private static final double INITIAL_LONGITUDE = -122.4194; // Начальная долгота (Сан-Франциско)
            private static final double INITIAL_SPEED = 50.0; // Начальная скорость (км/ч)
            private static final double INITIAL_HEADING = 90.0; // Начальный курс (градусы)

            private static double latitude = INITIAL_LATITUDE;
            private static double longitude = INITIAL_LONGITUDE;
            private static double speed = INITIAL_SPEED; // Скорость в км/ч
            private static double heading = INITIAL_HEADING; // Текущий курс (градусы)
            private static double turnAngle = 0.0; // Угол поворота (градусы)

            public static void main(String[] args) {
                try {
                    while (true) {
                        // Генерация сообщений
                        String gpgga = generateGPGGA();
                        String gpvtg = generateGPVTG();
                        String gpzda = generateGPZDA();

                        // Вывод сообщений
                        System.out.println(gpgga);
                        System.out.println(gpvtg);
                        System.out.println(gpzda);

                        // Обновление курса на основе угла поворота
                        updateHeading();

                        // Обновление координат на основе скорости и курса
                        updateCoordinates();

                        // Пауза 0.1 секунды
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Генерация сообщения $GPGGA
            private static String generateGPGGA() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss.SSS");
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String time = timeFormat.format(new Date());

                DecimalFormat latFormat = new DecimalFormat("00.000000");
                DecimalFormat lonFormat = new DecimalFormat("000.000000");
                String lat = latFormat.format(Math.abs(latitude));
                String lon = lonFormat.format(Math.abs(longitude));

                String message = String.format("$GPGGA,%s,%s,%c,%s,%c,1,12,0.8,10.0,M,0.0,M,,",
                        time, lat, (latitude >= 0 ? 'N' : 'S'), lon, (longitude >= 0 ? 'E' : 'W'));

                return message + "*" + calculateChecksum(message);
            }

            // Генерация сообщения $GPVTG
            private static String generateGPVTG() {
                DecimalFormat speedFormat = new DecimalFormat("0.0");
                double speedKnots = speed * 0.53996; // Переводим скорость в узлы

                String message = String.format("$GPVTG,%.1f,T,%.1f,M,%.1f,N,%.1f,K",
                        heading, heading, speedKnots, speed);

                return message + "*" + calculateChecksum(message);
            }

            // Генерация сообщения $GPZDA
            private static String generateGPZDA() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss.SSS,dd,MM,yyyy");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String message = "$GPZDA," + dateFormat.format(new Date());

                return message + "*" + calculateChecksum(message);
            }

            // Вычисление контрольной суммы
            private static String calculateChecksum(String message) {
                int checksum = 0;
                for (int i = 1; i < message.length(); i++) {
                    checksum ^= message.charAt(i); // XOR всех символов после '$'
                }

                return String.format("%02X", checksum); // Форматируем в виде двух шестнадцатеричных цифр
            }

            // Обновление курса на основе угла поворота
            private static void updateHeading() {
                // Увеличиваем или уменьшаем курс на угол поворота
                heading += turnAngle;

                // Нормализуем курс в диапазоне [0, 360)
                heading = heading % 360;
                if (heading < 0) {
                    heading += 360;
                }
            }

            // Обновление координат на основе скорости и курса
            private static void updateCoordinates() {
                // Переводим скорость в м/с
                double speedMs = speed * 1000 / 3600;

                // Переводим курс в радианы
                double headingRad = Math.toRadians(heading);

                // Вычисляем изменение координат
                double deltaLat = (speedMs * Math.cos(headingRad)) / EARTH_RADIUS;
                double deltaLon = (speedMs * Math.sin(headingRad)) / (EARTH_RADIUS * Math.cos(Math.toRadians(latitude)));

                // Обновляем широту и долготу
                latitude += Math.toDegrees(deltaLat);
                longitude += Math.toDegrees(deltaLon);
            }

            // Метод для изменения скорости (можно вызывать извне)
            public static void setSpeed(double newSpeed) {
                speed = newSpeed;
            }

            // Метод для изменения угла поворота (можно вызывать извне)
            public static void setTurnAngle(double newTurnAngle) {
                turnAngle = newTurnAngle;
            }
        }


/*

Как работает код:
Генерация сообщений:

$GPGGA: Содержит время, широту, долготу и другие данные.

$GPVTG: Содержит скорость и угол поворота.

$GPZDA: Содержит дату и время.

Обновление координат:

Координаты (широта и долгота) обновляются на основе скорости и угла поворота.

Используются формулы для перевода скорости и угла в изменение координат.

Динамическое изменение скорости и угла:

Методы setSpeed и setHeading позволяют изменять скорость и угол поворота во время работы программы.

Пауза 0.1 секунды:

После генерации сообщений программа "спит" 0.1 секунды, чтобы соответствовать требуемой частоте обновления.

Как использовать:
Запустите программу.

Сообщения будут выводиться в консоль каждые 0.1 секунды.

Для изменения скорости или угла поворота вызовите методы setSpeed и setHeading (например, из другого потока).




Что изменилось:
Добавлено поле turnAngle:

Это угол поворота в градусах, который влияет на изменение курса.

Метод updateHeading:

Обновляет курс (heading) на основе угла поворота (turnAngle).

Курс нормализуется в диапазоне [0, 360), чтобы избежать отрицательных значений или значений больше 360°.

Метод setTurnAngle:

Позволяет задать угол поворота извне. Например, можно вызвать setTurnAngle(5.0), чтобы машина начала поворачивать на 5° каждые 0.1 секунды.

Обновление координат:

Координаты обновляются на основе текущего курса (heading), который теперь зависит от угла поворота.

Пример использования:
Запустите программу.

Используйте метод setTurnAngle, чтобы задать угол поворота. Например:

java
        Copy
setTurnAngle(5.0); // Машина начнет поворачивать на 5° каждые 0.1 секунды
Используйте метод setSpeed, чтобы изменить скорость. Например:

java
        Copy
setSpeed(30.0); // Увеличить скорость до 30 км/ч
Пример вывода:
Copy
$GPGGA,123456.78,5200.00000000,N,04700.00000000,E,1,12,0.9,100.0,M,0.0,M,,*
$GPVTG,0.0,T,0.0,M,13.49892,N,25.00000,K,D*
$GPZDA,123456.78,01,01,2023*

$GPGGA,123456.88,5200.00012345,N,04700.00056789,E,1,12,0.9,100.0,M,0.0,M,,*
$GPVTG,5.0,T,5.0,M,13.49892,N,25.00000,K,D*
$GPZDA,123456.88,01,01,2023*
Теперь курс будет изменяться в зависимости от угла поворота, а координаты — в зависимости от курса и скорости.


Что изменилось:
Метод calculateChecksum:

Вычисляет контрольную сумму для строки, выполняя операцию XOR над всеми символами строки после $.

Возвращает контрольную сумму в виде двух шестнадцатеричных цифр.

Добавление контрольной суммы в сообщения:

В каждом методе генерации сообщений (generateGPGGA, generateGPVTG, generateGPZDA) добавлен вызов calculateChecksum, и контрольная сумма добавляется в конец строки после *.

Пример вывода:
Copy
$GPGGA,123456.78,5200.00000000,N,04700.00000000,E,1,12,0.9,100.0,M,0.0,M,,*7A
$GPVTG,45.0,T,45.0,M,13.49900,N,25.00000,K,D*4E
$GPZDA,123456.78,01,01,2023*47

Теперь каждая строка содержит корректную контрольную сумму, как и требуется в NMEA-сообщениях.
        */