package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team2036.robot.cube.SetCubeGripSpeed

/**
 * A command that defines what happens in the autonomous period
 */
class CenterAutonomous constructor(direction: SwitchSide) : CommandGroup() {

    val angleToTurn = 50.0
    val angleToCorrect = 10.0

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {
        val initialAngle: Double
        val correctionAngle: Double
        when (direction) {
            SwitchSide.LEFT -> {
                initialAngle = -angleToTurn
                correctionAngle = angleToCorrect
            }
            SwitchSide.RIGHT -> {
                initialAngle = angleToTurn
                correctionAngle = -angleToCorrect
            }
        }
        addSequential(JerkIt())
        addSequential(TurnToAngle(initialAngle))
        addSequential(GoToDistance(feetToInches(10.0)))
        addSequential(TurnToAngle(correctionAngle))
        addSequential(GoToDistance(feetToInches(3.0), 2.0))
        addSequential(SetCubeGripSpeed(-1.0, 5.0))
    }

    fun feetToInches(feet: Double): Double {
        return feet * 12.0
    }


}