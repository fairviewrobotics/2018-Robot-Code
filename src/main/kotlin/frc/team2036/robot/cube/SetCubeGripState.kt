package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.joystick

val setCubeGripState = SetCubeGripState()

/**
 * A SetCubeGripState is a command that will change the state of the CubeGrip subsystem
 * The CubeGrip subsystem is always either inputting, outputting, or doing nothing
 */
class SetCubeGripState internal constructor() : Command() {

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
        cubeGrip.state = when {
            joystick.getTriggerAxis(GenericHID.Hand.kLeft) > .75 -> CubeGripState.INPUT
            joystick.getTriggerAxis(GenericHID.Hand.kRight) > .75 -> CubeGripState.OUTPUT
            else -> CubeGripState.IDLE
        }
    }

    /**
     * SetCubeGripState should never finish
     */
    override fun isFinished(): Boolean {
        return false
    }

}