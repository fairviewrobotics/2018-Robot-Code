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

val oldAutonomous = OldAutonomous()

/**
 * A Command that runs the robot through the given points using PathFinder and encoders
 */
class OldAutonomous internal constructor() : Command() {

    private val points = arrayOf(
            Waypoint(0.0, 15.0, Pathfinder.d2r(0.0)),
            Waypoint(6.0, 15.0, Pathfinder.d2r(0.0)),
            Waypoint(9.0, 20.0, Pathfinder.d2r(90.0))
    )

    private val maxVelocity = 5.57743 // ft/s
    private val maxAcceleration = 6.56168 // ft/s^2
    private val maxJerk = 196.85 // ft/s^3

    private val config = Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, maxVelocity, maxAcceleration, maxJerk)
    private val trajectory = Pathfinder.generate(points, config)
    private val modifier = TankModifier(trajectory).modify(1.854166667) //feet

    private val left = EncoderFollower(modifier.leftTrajectory)
    private val right = EncoderFollower(modifier.rightTrajectory)

    private val wheelDiameter = 0.5 //6 inches in feet
    private val ticksPerRevolution = 360 //Number of encoder ticks per full revolution

    private val ahrs = AHRS(SPI.Port.kMXP)

    private val k_p = 0.8
    private val k_i = 0.0
    private val k_d = 0.0
    private val k_v = 1 / maxVelocity
    private val k_a = 0.0


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
        val encoderPositionLeft = -drivetrain.frontLeftEncoder.get()
        val encoderPositionRight = drivetrain.frontRightEncoder.get()

        val l = left.calculate(encoderPositionLeft)
        val r = right.calculate(encoderPositionRight)

        logger.log("Left speed", l)
        logger.log("Right speed", r)

        val gyroHeading = ahrs.angle //... your gyro code here ...    // Assuming the gyro is giving a value in degrees

        val desiredHeading = Pathfinder.r2d(left.heading)  // Should also be in degrees

        val angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);

        logger.log("Angle difference", angleDifference)
        val turn = -(0.8 * (-1.0 / 80.0) * angleDifference)

        drivetrain.tankDrive(-(l + turn), r - turn)
    }

    /**
     * What happens when the command starts
     */
    override fun start() {
        logger.log("Program Flow", "Auto starting get out of the way", LogType.DEBUG)

        val leftEncoderPosition = -drivetrain.frontLeftEncoder.get()
        left.configureEncoder(leftEncoderPosition, ticksPerRevolution, wheelDiameter);
        left.configurePIDVA(k_p, k_i, k_d, k_v, k_a);

        val rightEncoderPosition = drivetrain.frontRightEncoder.get()
        right.configureEncoder(rightEncoderPosition, ticksPerRevolution, wheelDiameter);
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
