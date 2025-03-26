
import java.util.Scanner;

public class NMEAGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод угла поворота
        System.out.print("Введите угол поворота (в градусах): ");
        String course = scanner.nextLine();

        // Ввод скорости в узлах
        System.out.print("Введите скорость (в узлах): ");
        String speedKnots = scanner.nextLine();

        // Ввод скорости в км/ч
        System.out.print("Введите скорость (в км/ч): ");
        String speedKmh = scanner.nextLine();

        // Генерация сообщения VTG
        String vtgMessage = generateVTG(course, "T", "0.0", "M", speedKnots, speedKmh, "A");
        System.out.println(vtgMessage);

        scanner.close();
    }

    public static String generateVTG(String course, String courseReference, String magneticCourse, String magneticReference, String speedKnots, String speedKmh, String mode) {
        // Форматирование сообщения VTG
        StringBuilder vtg = new StringBuilder();
        vtg.append("$GPVTG,"); // Начало сообщения VTG
        vtg.append(course).append(",").append(courseReference).append(","); // Курс
        vtg.append(magneticCourse).append(",").append(magneticReference).append(","); // Магнитный курс
        vtg.append(speedKnots).append(",N,"); // Скорость в узлах
        vtg.append(speedKmh).append(",K,"); // Скорость в км/ч
        vtg.append(mode); // Режим
        vtg.append("*").append(calculateChecksum(vtg.toString())); // Контрольная сумма
        return vtg.toString();
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
