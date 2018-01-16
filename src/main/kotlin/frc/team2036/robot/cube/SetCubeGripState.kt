package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team2036.robot.util.logger

val intakeCubes = SetCubeGripState(CubeGripState.INPUT) //Makes the global command that will intake cubes
val outputCubes = SetCubeGripState(CubeGripState.OUTPUT) //Makes the global command that will output cubes
val nothing = SetCubeGripState(CubeGripState.IDLE) //Makes the global command that won't move the CubeGrip system

/**
 * A SetCubeGripState is a command that will change the state of the CubeGrip subsystem
 * The CubeGrip subsystem is always either inputting, outputting, or doing nothing
 * However the command is constructed will determine how it will set the CubeGrip state
 */
class SetCubeGripState internal constructor(val state: CubeGripState) : InstantCommand() {

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
        logger.log("CubeGrip State", "Setting CubeGrip state to ${this.state}")
        cubeGrip.state = this.state
    }

}