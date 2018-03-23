package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.command.Command


/**
 * A SetCubeGripState is a command that will change the state of the CubeGrip subsystem
 * The CubeGrip subsystem is always either inputting, outputting, or doing nothing
 */
class SetCubeGripSpeed
/**
 * As a constructor for SetCubeGripState, it says that it requires the CubeGrip subsystem
 */(val speed: Double, time: Double = 0.0) : Command() {

    var time: Long

    var startTime: Long = 0

    init {
        requires(cubeGrip)
        this.time = (time * 1E9).toLong()
    }

    override fun initialize() {
        startTime = System.nanoTime()
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
        if (time == 0L) {
            return false
        } else return time >= (System.nanoTime() - startTime)
    }

}