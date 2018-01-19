package frc.team2036.robot.drivetrain

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team2036.robot.config
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger


val drivetrain = Drivetrain()

/**
 * Defines a drivetrain subsystem
 * Represents a RobotDrive that handles driving
 * This particular implementation uses arcadeDrive as opposed to mecanumDrive_Cartesian, mecanumDrive_Polar, or tankDrive
 */
class Drivetrain internal constructor() : Subsystem() {

    private val frontLeft = WPI_TalonSRX(config("ports")("wheels")["frontLeft"] as Int)
    private val backLeft = WPI_TalonSRX(config("ports")("wheels")["backLeft"] as Int)

    private val frontRight = WPI_TalonSRX(config("ports")("wheels")["frontRight"] as Int)
    private val backRight = WPI_TalonSRX(config("ports")("wheels")["backRight"] as Int)

    //The robot drive is the actual part of the code that controls robot movement; is constructed with Talons
    private val drive = DifferentialDrive(frontLeft, frontRight)

    init {
        frontLeft.inverted = true
        backLeft.inverted = true

        backLeft.follow(frontLeft)
        backRight.follow(frontRight)
    }

    /**
     * Sets the default command as a followJoystick command
     * It also sets the back wheels to follow the front wheels
     * @see <a href="https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/SixTalonArcadeDrive">Example Code</a>
     * @see <a href="https://wpilib.screenstepslive.com/s/currentCS/m/java/l/479803-talon-srx-can">WPI Docs</a>
     */
    override fun initDefaultCommand() {
        this.defaultCommand = followJoystick
    }

    /**
     * Takes in an x movement and a y movement and actually moves the drivetrain by that amount
     */
    fun drive(x: Double, y: Double) {
        this.drive.arcadeDrive(-x, y)
        // TODO UPDATE LOGGER
        logger.log("Drivetrain movement", "Drivetrain set to move by ($x, $y).", LogType.DEBUG)
    }

    fun setLeftSpeed(v: Double) {
        frontLeft.set(v)
    }

    fun setRightSpeed(v: Double) {
        frontRight.set(v)
    }

}