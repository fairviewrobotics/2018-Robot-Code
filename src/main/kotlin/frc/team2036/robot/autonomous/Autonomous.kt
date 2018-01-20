package frc.team2036.robot.autonomous


import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.drivetrain.drivetrain
import frc.team2036.robot.joystick
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger
import java.lang.Math.pow
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier







val autonomous = Autonomous()


/**
 * A Command that connects the joystick to the drivetrain
 * Just sends all joystick inputs to the robot
 */
class Autonomous internal constructor() : Command() {

    lateinit private var modifier: TankModifier
    lateinit private var left: EncoderFollower
    lateinit private var right: EncoderFollower


    /**
     * Constructor for a FollowJoystick command
     * Just states that this command needs the drivetrain
     */
    init {
        requires(drivetrain)
    }

    /**
     * Moves the drive train based on joystick's x and y coordinates
     * Just takes in the joystick axes and passes them to the drivetrain to handle driving
     */
    override fun execute() {
        val encoder_position_left = 0
        val encoder_position_right = 0

        val l = left.calculate(encoder_position_left);
        val r = right.calculate(encoder_position_right);

        val gyro_heading =  0 //... your gyro code here ...    // Assuming the gyro is giving a value in degrees
        val desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        val angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        val turn = 0.8 * (-1.0/80.0) * angleDifference;

        drivetrain.setLeftSpeed(l + turn);
        drivetrain.setRightSpeed(r - turn);

    }

    /**
     * What happens when the command starts
     */
    override fun start() {
        logger.log("Program Flow", "FollowJoystick command starting.", LogType.TRACE)
        val points = arrayOf(Waypoint(-4.0, -1.0, Pathfinder.d2r(-45.0)), // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
                Waypoint(-2.0, -2.0, 0.0), // Waypoint @ x=-2, y=-2, exit angle=0 radians
                Waypoint(0.0, 0.0, 0.0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
        )

        val config = Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0)
        val trajectory = Pathfinder.generate(points, config)
        val modifier = TankModifier(trajectory).modify(0.5)

        val left = EncoderFollower(modifier.leftTrajectory)
        val right = EncoderFollower(modifier.rightTrajectory)

        // Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
        // 'getEncPosition' function.
        // 1000 is the amount of encoder ticks per full revolution
        // Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters

        val wheel_diameter = 0.1524 //6 inches in meters
        val ticks_per_revolution = 1000

        val left_encoder_position = 0
        left.configureEncoder(left_encoder_position, ticks_per_revolution, wheel_diameter);
        left.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0.0);

        val right_encoder_position = 0
        left.configureEncoder(left_encoder_position, ticks_per_revolution, wheel_diameter);
        left.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0.0);

    }

    /**
     * When the command is over, it sets the drivetrain to move by no amount
     */
    override fun end() {
        logger.log("Program Flow", "FollowJoystick command ending.", LogType.TRACE)
        drivetrain.drive(0.0, 0.0)
    }

    /**
     * FollowJoystick is never finished unless it is interrupted
     */
    override fun isFinished(): Boolean {
        return false
    }

    /**
     * Takes in a joystick x or y value and processes the value so that they can be adjusted
     * Would ideally work in polar and then return x and y components, but joystick has no polar methods
     */
    private fun processJoystickValue(component: Double): Double {
        val minimumWheelRotation = config("speeds")("wheels")["minimumWheelRotation"] as Int
        return (1 - minimumWheelRotation) * (pow(component, 3.0)) + minimumWheelRotation * if(component > 0) 1 else -1
    }

}