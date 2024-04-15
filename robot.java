

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.hardware.Sound;

/**
 * This class implements a line follower robot that can avoid obstacles.
 */
public class LineFollowerRobot {
    static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
    static EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
    static long startTime; // Variable to store start time
    static int obstacleCount = 0; // Variable to count obstacles

    /**
     * The main method of the LineFollowerAvoidingObstacles class.
     * Starts the robot and controls its behavior.
     * @param args The command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialization of sensors and motors
        SampleProvider colorProvider = colorSensor.getRedMode();
        SampleProvider distanceProvider = distanceSensor.getDistanceMode();
        float[] colorSample = new float[colorProvider.sampleSize()];
        float[] distanceSample = new float[distanceProvider.sampleSize()];

        // Set initial motor speeds
        leftMotor.setSpeed(200);
        rightMotor.setSpeed(200);

        boolean obstacleDetected = false;
        startTime = System.currentTimeMillis(); // Record start time

        // Main control loop
        while (!Button.ESCAPE.isDown()) {
            colorProvider.fetchSample(colorSample, 0);
            distanceProvider.fetchSample(distanceSample, 0);

            float colorValue = colorSample[0] * 100; // Scale to 0-100 range
            float distanceValue = distanceSample[0] * 100; // Convert to centimeters

            // Line following behavior
            if (!obstacleDetected) {
                if (colorValue < 13) { // Adjust the threshold as needed
                    leftMotor.forward();
                    rightMotor.forward();
                } else {
                    leftMotor.forward();
                    rightMotor.backward();
                }
            }
            // Obstacle detection and avoidance
            if (!obstacleDetected && distanceValue < 10) { // Obstacle detected within 10 cm
                obstacleDetected = true;
                leftMotor.stop();
                rightMotor.stop();
                obstacleCount++; // Increment obstacle count
                if (obstacleCount == 2) { // Stop when second obstacle is detected
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    LCD.drawString("Elapsed Time: " + elapsedTime / 1000 + " seconds", 0, 0);
                    playMusic(); // Play music before stopping
                    Sound.beepSequence(); // Play a beep sound
                    Button.waitForAnyPress();
                    break; // Exit the loop after displaying elapsed time
                }
                turnRight90();
                moveForward();
                turnLeft90();
                moveForward();
                turnLeft90();
                moveForward();
                obstacleDetected = false; // Resume line following after obstacle avoidance
            }
