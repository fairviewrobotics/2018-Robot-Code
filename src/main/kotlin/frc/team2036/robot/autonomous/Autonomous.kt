package frc.team2036.robot.autonomous

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
            Waypoint(-4.0, -1.0, Pathfinder.d2r(-45.0)), //Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            Waypoint(-2.0, -2.0, 0.0), //Waypoint @ x=-2, y=-2, exit angle=0 radians
            Waypoint(0.0, 0.0, 0.0) // Waypoint @ x=0, y=0,   exit angle=0 radians
    )
    val config = Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0)
    val trajectory = Pathfinder.generate(points, config)
    val modifier = TankModifier(trajectory).modify(0.5)
    val left = EncoderFollower(modifier.leftTrajectory)
    val right = EncoderFollower(modifier.rightTrajectory)


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
        val gyroHeading = 0 // Assuming the gyro is giving a value in degrees
        val desiredHeading = Pathfinder.r2d(left.heading) // Should also be in degrees
        val angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading)
        val turn = 0.8 * (-1.0 / 80.0) * angleDifference
        drivetrain.tankDrive(left.calculate(drivetrain.frontLeftEncoder.get()) + turn, right.calculate(drivetrain.frontRightEncoder.get()) - turn)
    }

    /**
     * What happens when the command starts
     */
    override fun start() {
        logger.log("Program Flow", "Autonomous command starting.", LogType.TRACE)
        // Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
        // 'getEncPosition' function.
        // 1000 is the amount of encoder ticks per full revolution
        // Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
        val wheelDiameter = 0.1524 //6 inches in meters
        val ticksPerRevolution = 1000
        val leftEncoderPosition = drivetrain.frontLeftEncoder.get()
        val rightEncoderPosition = drivetrain.frontRightEncoder.get()
        left.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0.0)
        left.configureEncoder(leftEncoderPosition, ticksPerRevolution, wheelDiameter)
        right.configureEncoder(rightEncoderPosition, ticksPerRevolution, wheelDiameter)
        right.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0.0)
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