

public class Main {
    public static double SPEED = 50.0; // Начальная скорость (км/ч)
    static double theta_R = 270; // Угол поворота рулевого колеса (theta_R)



    public static void main(String[] args) {


        CalculationOfTheTrajectoryOfMovement transfer = new CalculationOfTheTrajectoryOfMovement();
        transfer.inSpeedAndAngle(SPEED, theta_R);
        transfer.startCalculationOfTheTrajectory();




        }
    }
