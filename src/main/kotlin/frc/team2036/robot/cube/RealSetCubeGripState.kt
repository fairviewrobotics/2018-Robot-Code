package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.command.Command


/**
 * A SetCubeGripState is a command that will change the state of the CubeGrip subsystem
 * The CubeGrip subsystem is always either inputting, outputting, or doing nothing
 */
class setCubeGripSpeed internal constructor(speed: Double) : Command() {

    val speed = speed

    /**
     * As a constructor for SetCubeGripState, it says that it requires the CubeGrip subsystem
     */
    init {
        requires(cubeGrip)
    }

    /**
     * Sets the CubeGrip state to whatever this command's state field is
     */
    override fun execute() {

        cubeGrip.setSpeed(speed)
    }

    /**
     * SetCubeGripState should never finish
     */
    override fun isFinished(): Boolean {
        return false
    }

}