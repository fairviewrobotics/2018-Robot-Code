package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

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
class CubeGrip : Subsystem() {

    private val leftMotor = Spark(config("ports")("cubegrip")["leftSpark"] as Int) //The left input/output motor
    private val rightMotor = Spark(config("ports")("cubegrip")["rightSpark"] as Int) //The right input/output motor
    var state: CubeGripState = CubeGripState.IDLE //What the CubeGrip subsystem is doing/set to do at the current moment

    /**
     * Initializes the default command of the CubeGrip subsystem; CubeGrip doesn't have a default command
     */
    override fun initDefaultCommand() {}

    /**
     * What the CubeGrip does every tick
     * Just moves the motors based on state (eg. if the state is in INPUT, will move the motors such that the subsystem is inputting cubes)
     */
    override fun periodic() {
        //TODO move motors based on state
    }

}