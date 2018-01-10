package frc.team2036.robot.subsystem

import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.Talon
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team2036.robot.LogType
import frc.team2036.robot.command.followJoystick
import frc.team2036.robot.config
import frc.team2036.robot.logger

val drivetrain = Drivetrain()

/**
 * Defines a drivetrain subsystem
 * Represents a RobotDrive that handles driving
 * This particular implementation uses arcadeDrive as opposed to mecanumDrive_Cartesian, mecanumDrive_Polar, or tankDrive
 */
class Drivetrain : Subsystem() {

    private val left = SpeedControllerGroup(
            Talon(config("ports")("wheels")["frontLeft"] as Int),
            Talon(config("ports")("wheels")["backLeft"] as Int))
    private val right = SpeedControllerGroup(
            Talon(config("ports")("wheels")["frontRight"] as Int),
            Talon(config("ports")("wheels")["backRight"] as Int))

    //The robot drive is the actual part of the code that controls robot movement; is constructed with Talons
    private val drive = DifferentialDrive(left, right)

    /**
     * Sets the default command as a followJoystick command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = followJoystick
    }

    /**
     * Takes in an x movement and a y movement and actually moves the drivetrain by that amount
     */
    fun drive(x: Double, y: Double) {
        this.drive.arcadeDrive(x, y)
        logger.log("Drivetrain movement", "Drivetrain set to move by ($x, $y).", LogType.DEBUG)
    }

}