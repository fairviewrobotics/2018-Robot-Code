package frc.team2036.robot.elevator

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

val elevator = Elevator()

class Elevator internal constructor() : Subsystem() {

    private val elevatorMotor = WPI_TalonSRX(config("ports")["elevator"] as Int)

    private val motorSpeed = config("speeds")["elevator"] as Double

    init {
        // See https://github.com/CrossTheRoadElec/Phoenix-Documentation#java---how-to-intellisensewhat-to-import
        elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0)
    }

    /**
     * Initializes the default command of the CubeGrip subsystem; CubeGrip doesn't have a default command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = changeElevator
    }


    fun drive(y: Double) {
        logger.log("Elevator Y", y, LogType.DEBUG)
        elevatorMotor.set(y * motorSpeed)
    }

    override fun periodic() {
        logger.log("Elevator position", elevatorMotor.getSelectedSensorPosition(0).toDouble())
    }
}

