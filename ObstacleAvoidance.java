// Obstacle detection and avoidance code for the robot
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
