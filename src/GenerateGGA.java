
// Часть 2


public class GenerateGGA {

    public static void main(String[] args) {
        // Пример данных для генерации сообщения GGA
        String latitude = "53.2885";  // Широта
        String longitude = "50.8313"; // Долгота
        String quality = "1"; // Качество сигнала (1 = GPS фиксирован)
        String satellites = "8"; // Количество спутников
        String hdop = "0.9"; // Горизонтальная точность
        String altitude = "15.0"; // Высота над уровнем моря
        String altitudeUnit = "M"; // Единица измерения высоты
        String ageOfDiff = ""; // Возраст дифференциального сигнала
        String stationId = ""; // ID станции

        // Генерация сообщения GGA
        String ggaMessage = generateGGA(latitude, longitude, quality, satellites, hdop, altitude, altitudeUnit, ageOfDiff, stationId);
        System.out.println(ggaMessage);
    }



    public static String generateGGA(String latitude, String longitude, String quality, String satellites, String hdop, String altitude, String altitudeUnit, String ageOfDiff, String stationId) {
        // Форматирование сообщения GGA
        StringBuilder gga = new StringBuilder();
        gga.append("$GPGGA,"); // Начало сообщения GGA
        gga.append(getCurrentTime()).append(","); // Время
        gga.append(latitude).append(","); // Широта
        gga.append("N,"); // Северное полушарие
        gga.append(longitude).append(","); // Долгота
        gga.append("W,"); // Западное полушарие
        gga.append(quality).append(","); // Качество сигнала
        gga.append(satellites).append(","); // Количество спутников
        gga.append(hdop).append(","); // Горизонтальная точность
        gga.append(altitude).append(","); // Высота
        gga.append(altitudeUnit).append(","); // Единица измерения высоты
        gga.append(ageOfDiff).append(","); // Возраст дифференциального сигнала
        gga.append(stationId); // ID станции
        gga.append("*").append(calculateChecksum(gga.toString())); // Контрольная сумма
        return gga.toString();
    }

    private static String getCurrentTime() {
        // Возвращает текущее время в формате HHMMSS
        java.time.LocalTime now = java.time.LocalTime.now();
        return String.format("%02d%02d%02d", now.getHour(), now.getMinute(), now.getSecond());
    }

    private static String calculateChecksum(String nmea) {
        // Вычисляет контрольную сумму для NMEA-сообщения
        int checksum = 0;
        for (char c : nmea.toCharArray()) {
            checksum ^= c;
        }
        return String.format("%02X", checksum);
    }




}
