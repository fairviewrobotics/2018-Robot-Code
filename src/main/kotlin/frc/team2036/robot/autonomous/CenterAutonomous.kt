package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain

enum class DIRECTION {
    LEFT,
    RIGHT
}

/**
 * A command that defines what happens in the autonomous period
 */
class CenterAutonomous constructor(direction: DIRECTION) : CommandGroup() {

    val angleToTurn = 50.0
    val angleToCorrect = 10.0

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {
        requires(drivetrain)
        requires(cubeGrip)
        val initialAngle: Double
        val correctionAngle: Double
        when (direction) {
            DIRECTION.LEFT -> {
                initialAngle = -angleToTurn
                correctionAngle = angleToCorrect
            }
            DIRECTION.RIGHT -> {
                initialAngle = angleToTurn
                correctionAngle = -angleToCorrect
            }
        }
        addSequential(TurnToAngle(initialAngle))
        addSequential(GoToDistance(feetToInches(10.0)))
        addSequential(TurnToAngle(correctionAngle))
        addSequential(GoToDistance(feetToInches(1.0)))
    }

    fun feetToInches(feet: Double): Double {
        return feet * 12.0
    }


}