package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.cube.CubeGripState
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain

//A global autonomous object
val autonomous = Autonomous()

/**
 * A command that defines what happens in the autonomous period
 */
class Autonomous internal constructor(): Command() {

    private val diameter = config("sizes")["diameter"] as Double //wheel diameter in inches
    private val ticksPerRev = config("sizes")["ticksPerRev"] as Double //how many encoder ticks happen in 1 wheel revolution
    private val circumference = diameter * Math.PI //circumference of a wheel

    val distanceToMove = 60 //how far forward this command will move the drivetrain in inches

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {
        requires(drivetrain)
        requires(cubeGrip)
    }

    /**
     * While the command is running, the robot moves forward
     */
    override fun execute() {
        drivetrain.drive(0.0, 0.5)
    }

    /**
     * Converts a number of pulses (which is what encoder values return) to a distance in inches
     */
    fun pulsesToInches(pulses: Int): Double {
        return circumference / ticksPerRev * pulses
    }

    /**
     * The command finishes once the drivetrain has moved past the specified distance
     */
    override fun isFinished(): Boolean {
        return pulsesToInches(drivetrain.frontRightEncoder.get()) >= distanceToMove
    }

    /**
     * When the command is over, it sets the cubegrip to be outputting
     */
    override fun end() {
        cubeGrip.state = CubeGripState.OUTPUT
    }
}