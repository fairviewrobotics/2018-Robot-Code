package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config
import frc.team2036.robot.util.logger

//Constructs a global CubeGrip object
val cubeGrip = CubeGrip()

/**
 * An enum holding the different options for what state the CubeGrip subsystem can be in
 */
enum class CubeGripState {
    INPUT, OUTPUT, IDLE
}

/**
 * The cube grip is the subsystem that holds the cubes
 * It is in any of 3 states: intake, output, and idle
 */
class CubeGrip internal constructor() : Subsystem() {

    private val leftMotor = Spark(config("ports")("cubeGrip")["leftSpark"] as Int) //The left input/output motor
    private val rightMotor = Spark(config("ports")("cubeGrip")["rightSpark"] as Int) //The right input/output motor
    var state: CubeGripState = CubeGripState.IDLE //What the CubeGrip subsystem is doing/set to do at the current moment
        /**
         * CubeGrip state setter sets the cubegrip state and logs it
         */
        set(value) {
            logger.log("CubeGrip State", "Setting CubeGrip state to $value")
            field = value
        }

    /**
     * Initializes the default command of the CubeGrip subsystem; CubeGrip doesn't have a default command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = setCubeGripState
    }

    /**
     * Sets both motor speeds
     * Because both motors should always be going in opposite directions, passes opposite values to both motors
     */
    fun setSpeed(speed: Double) {
        leftMotor.speed = speed
        rightMotor.speed = -speed
    }

}