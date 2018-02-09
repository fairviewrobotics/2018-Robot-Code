package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.cube.CubeGripState
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain

val autonomous = Autonomous()

/**
 *
 */
class Autonomous internal constructor(): Command() {

    val distanceToMove = 60 //in inches

    init {
        requires(drivetrain)
    }

    override fun execute() {
        drivetrain.drive(0.0, 0.5)
    }

    fun pulsesToInches(pulses: Int): Double {
        return 1.0
    }

    override fun isFinished(): Boolean {
        return pulsesToInches(drivetrain.frontRightEncoder.get()) > distanceToMove
    }

    override fun end() {
        cubeGrip.state = CubeGripState.OUTPUT
    }
}