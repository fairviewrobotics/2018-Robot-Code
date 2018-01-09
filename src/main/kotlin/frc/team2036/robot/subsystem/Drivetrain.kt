package frc.team2036.robot.subsystem

import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.Talon
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team2036.robot.command.FollowJoystick
import frc.team2036.robot.config


/**
 * Defines a drivetrain subsystem
 * Represents a RobotDrive that handles driving
 * This particular implementation uses arcadeDrive as opposed to mecanumDrive_Cartesian, mecanumDrive_Polar, or tankDrive
 */
class Drivetrain : Subsystem() {

    private val frontLeft = Talon(config("ports")("wheels")["frontLeft"] as Int)
    private val rearLeft = Talon(config("ports")("wheels")["backLeft"] as Int)
    private val left = SpeedControllerGroup(frontLeft, rearLeft)

    private val frontRight = Talon(config("ports")("wheels")["frontRight"] as Int)
    private val rearRight = Talon(config("ports")("wheels")["backRight"] as Int)
    private val right = SpeedControllerGroup(frontRight, rearRight)

    //The robot drive is the actual part of the code that controls robot movement; is constructed with Talons
    private val drive = DifferentialDrive(left, right)

    /**
     * Sets the default command as a followJoystick command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = FollowJoystick()
    }

    /**
     * Takes in an x movement and a y movement and actually moves the drivetrain by that amount
     */
    fun drive(x: Double, y: Double, multiplier: Double = config["motorScale"] as Double) {
        this.drive.arcadeDrive(x * multiplier, y * multiplier)
    }

}