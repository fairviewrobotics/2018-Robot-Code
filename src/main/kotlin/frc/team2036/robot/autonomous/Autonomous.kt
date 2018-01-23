package frc.team2036.robot.autonomous

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.drivetrain.drivetrain
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.followers.EncoderFollower
import jaci.pathfinder.modifiers.TankModifier

val autonomous = Autonomous()

/**
 * A Command that runs the robot through the given points using PathFinder and encoders
 */
class Autonomous internal constructor() : Command() {

    val points = arrayOf(
            Waypoint(-4.0, 0.0, Pathfinder.d2r(0.0)),
            Waypoint(-2.0, 0.0, Pathfinder.d2r(0.0))
    )

    val max_velocity = 1.0
    val max_acceleration = 0.3
    val max_jerk = 30.0

    val config = Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, max_velocity, max_acceleration, max_jerk)
    val trajectory = Pathfinder.generate(points, config)
    val modifier = TankModifier(trajectory).modify(0.56515)

    val left = EncoderFollower(modifier.leftTrajectory)
    val right = EncoderFollower(modifier.rightTrajectory)

    val wheel_diameter = 0.1524 //6 inches in meters
    val ticks_per_revolution = 360 //Number of encoder ticks per full revolution

    val ahrs = AHRS(SPI.Port.kMXP)

    val k_p = 1.0
    val k_i = 0.0
    val k_d = 0.0
    val k_v = 1 / max_velocity
    val k_a = 0.0


    /**
     * Constructor for the autonomous command
     * States that the autonomous command needs the drivetrain
     */
    init {
        requires(drivetrain)
    }

    /**
     * Moves the drive train based Encoder readings to go through all of the points
     */
    override fun execute() {
        logger.log("Executing auto","ok", LogType.TRACE)
        val encoder_position_left = -drivetrain.frontLeftEncoder.get()
        val encoder_position_right = drivetrain.frontRightEncoder.get()

        val l = left.calculate(encoder_position_left);
        val r = right.calculate(encoder_position_right);

        //val gyro_heading =  ahrs.angle //... your gyro code here ...    // Assuming the gyro is giving a value in degrees

        //val desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        //val angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);

        //logger.log("Angle difference",angleDifference)
        //val turn = 0.8 * (-1.0/80.0) * angleDifference;
        val turn = 0

        drivetrain.tankDrive(l+turn,r-turn)
    }

    /**
     * What happens when the command starts
     */
    override fun start() {
        logger.log("Program Flow", "Auto starting get out of the way", LogType.DEBUG)

        val left_encoder_position = -drivetrain.frontLeftEncoder.get()
        left.configureEncoder(left_encoder_position, ticks_per_revolution, wheel_diameter);
        left.configurePIDVA(k_p, k_i, k_d, k_v, k_a);

        val right_encoder_position = drivetrain.frontRightEncoder.get()
        right.configureEncoder(right_encoder_position, ticks_per_revolution, wheel_diameter);
        right.configurePIDVA(k_p, k_i, k_d, k_v, k_a);

        ahrs.zeroYaw()
    }

    /**
     * Command finishing gets logged
     */
    override fun end() {
        logger.log("Program Flow", "Autonomous command ending.", LogType.TRACE)
    }

    /**
     * The autonomous command isn't finished until it's interrupted
     */
    override fun isFinished(): Boolean {
        return false
    }


}