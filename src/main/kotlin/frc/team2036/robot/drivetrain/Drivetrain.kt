package frc.team2036.robot.drivetrain

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SPI
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

    /**
     * Constructs all the wheels aas SRX Talons
     */
    private val frontLeft = WPI_TalonSRX(config("ports")("wheels")["frontLeft"] as Int)
    private val backLeft = WPI_TalonSRX(config("ports")("wheels")["backLeft"] as Int)
    private val frontRight = WPI_TalonSRX(config("ports")("wheels")["frontRight"] as Int)
    private val backRight = WPI_TalonSRX(config("ports")("wheels")["backRight"] as Int)

    //The robot drive is the actual part of the code that controls robot movement; is constructed with the front talons
    private val drive = DifferentialDrive(frontLeft, frontRight)

    /**
     * Makes all the encoders from constants defined in config
     */
    private val encodingType = config("ports")("encoders")["type"] as CounterBase.EncodingType
    val frontLeftEncoder = Encoder(config("ports")("encoders")("frontLeft")["a"] as Int,
            config("ports")("encoders")("frontLeft")["b"] as Int, false, encodingType)
    val backLeftEncoder = Encoder(config("ports")("encoders")("backLeft")["a"] as Int,
            config("ports")("encoders")("backLeft")["b"] as Int, false, encodingType)
    val frontRightEncoder = Encoder(config("ports")("encoders")("frontRight")["a"] as Int,
            config("ports")("encoders")("frontRight")["b"] as Int, false, encodingType)
    val backRightEncoder = Encoder(config("ports")("encoders")("backRight")["a"] as Int,
            config("ports")("encoders")("backRight")["b"] as Int, false, encodingType)

    val ahrs = AHRS(SPI.Port.kMXP)


    /**
     * Constructor for a drivetrain
     * Inverts the left wheels and sets the back wheels to follow the front wheels
     */
    init {
        frontLeft.inverted = true
        backLeft.inverted = true
        backLeft.follow(frontLeft)
        backRight.follow(frontRight)
        ahrs.zeroYaw()
    }

    /**
     * Sets the default command as a followJoystick command
     * Drivetrain doesn't have a default command because it has different default commands in autonomous and teleop
     * @see <a href="https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/SixTalonArcadeDrive">Example Code</a>
     * @see <a href="https://wpilib.screenstepslive.com/s/currentCS/m/java/l/479803-talon-srx-can">WPI Docs</a>
     */
    override fun initDefaultCommand() {}

    /**
     * Takes in an x movement and a y movement and actually moves the drivetrain by that amount
     */
    fun drive(x: Double, y: Double) {
        this.drive.arcadeDrive(x, y)
        logger.log("Drivetrain movement", "Drivetrain set to move by (${-x}, $y).", LogType.DEBUG)

        logger.log("Back Left Encoder", backLeftEncoder.get().toDouble())
        logger.log("Front Left Encoder", -frontLeftEncoder.get().toDouble())
        logger.log("Back Right Encoder", backRightEncoder.get().toDouble())
        logger.log("Front Right Encoder", frontRightEncoder.get().toDouble())
        logger.log("Yaw", ahrs.angle)
    }

    /**
     * Takes in left and right speeds and moves the robot as a tank drive
     */
    fun tankDrive(left: Double, right: Double) {
        this.drive.tankDrive(left, right)
    }

}