package frc.robot;

/**
 * Constants and tuning values
 */
public class Constants {
    // ROS other data
    public static double rosUpdateFrequency = 0.02;
    // ROS Name Data
    public static String rosTablename = "ROS"; // The string name of the table
    public static String starboardEncoderName = "Starboard";
    public static String portEncoderName = "Port";
    public static String rosIndexName = "rosIndex"; // Expected to be depreciated

    // Other
    public static int ticksPerMeter = 10000; // Calibrated 12/16/2019

    // PID Data slot (0-4)
	public static final int kSlotIdx = 0;

	// Loop ID (can have multiple)
	public static final int kPIDLoopIdx = 0;

    // Paramater config value
    public static final int kTimeoutMs = 30;
    
    // PID Gains
    public static float kfCalculation = (1.0f * 1023.0f) / 3000.0f; // (<optimal output speed> * 1023) / measured velocity
    public static final Gains kGains = new Gains(0, 0, 0, kfCalculation, 0, 1.0); // P, I, D, F, Zone, Peak output
}