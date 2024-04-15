/**
     * Turns the robot left by approximately 90 degrees.
     */
    private static void turnLeft90() {
        // Turn left 90 degrees (adjust motor speeds and duration as needed)
        leftMotor.setSpeed(150); // Adjust motor speed for smoother turning
        rightMotor.setSpeed(150); // Adjust motor speed for smoother turning

        // Rotate left motor backward and right motor forward to turn left
        leftMotor.backward();
        rightMotor.forward();

        // Wait for a duration to achieve a turn (adjust as needed)
        try {
            Thread.sleep(900); // Adjust duration for smoother turning
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Plays a simple melody.
     */
    private static void playMusic() {
        int[] notes = { 440, 494, 523, 587, 659, 698, 784, 880 }; // Frequencies of notes (A4 - A5)
        int[] durations = { 200, 200, 200, 200, 200, 200, 200, 200 }; // Durations of notes (milliseconds)

        // Play the melody
        for (int i = 0; i < notes.length; i++) {
            Sound.playTone(notes[i], durations[i]);
            try {
                Thread.sleep(50); // Pause between notes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
