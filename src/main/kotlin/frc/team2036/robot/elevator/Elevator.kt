package frc.team2036.robot.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

//Constructs a global elevator object
val elevator = Elevator()

/**
 * The elevator subsystem is the subsystem that handles moving up and down cubes
 * TODO: get this explained to us and write better doc for this
 */
class Elevator internal constructor() : Subsystem() {

    val elevatorMotor = WPI_TalonSRX(config("ports")["elevator"] as Int)
    private val motorSpeed = config("speeds")["elevator"] as Double

    /**
     * When the elevator is constructed, sets feedback sensor
     * See https://github.com/CrossTheRoadElec/Phoenix-Documentation#java---how-to-intellisensewhat-to-import
     */
    init {
        this.elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0)
        this.elevatorMotor.setSensorPhase(true)

        this.elevatorMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0)
        this.elevatorMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0)

        /* set the peak and nominal outputs */
        this.elevatorMotor.configNominalOutputForward(0.0, 10)
        this.elevatorMotor.configNominalOutputReverse(0.0, 10)
        this.elevatorMotor.configPeakOutputForward(0.2, 10)
        this.elevatorMotor.configPeakOutputReverse(-1.0, 10)

        /* set closed loop gains in slot0 - see documentation */
        this.elevatorMotor.selectProfileSlot(0, 0)
        this.elevatorMotor.config_kF(0, 0.2, 0)
        this.elevatorMotor.config_kP(0, 2.2, 0)
        this.elevatorMotor.config_kI(0, 0.0, 0)
        this.elevatorMotor.config_kD(0, 0.0, 0)
        /* set acceleration and vcruise velocity - see documentation */
        this.elevatorMotor.configMotionCruiseVelocity(100, 0)
        this.elevatorMotor.configMotionAcceleration(50, 0)
        /* zero the sensor */
        this.elevatorMotor.setSelectedSensorPosition(0, 0, 0)
    }

    /**
     * Initializes the default command of the CubeGrip subsystem; CubeGrip doesn't have a default command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = changeElevator
    }

    /**
     * Sets the elevator's height to the given value
     * TODO: rename?
     */
    fun drive(y: Double) {
        logger.log("Elevator Y", y, LogType.DEBUG)
        elevatorMotor.set(ControlMode.PercentOutput, y * motorSpeed)
    }

    /**
     * Continuously logs the elevator's position
     */
    override fun periodic() {
        logger.log("Elevator position", elevatorMotor.getSelectedSensorPosition(0).toDouble())
    }

}

