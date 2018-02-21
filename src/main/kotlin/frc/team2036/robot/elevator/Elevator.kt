package frc.team2036.robot.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
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

    val topLimit = DigitalInput(1)
    val bottomLimit = DigitalInput(0)

    /**
     * When the elevator is constructed, sets feedback sensor
     * See https://github.com/CrossTheRoadElec/Phoenix-Documentation#java---how-to-intellisensewhat-to-import
     */
    init {
        elevatorMotor.inverted = true
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

