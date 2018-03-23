package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team2036.robot.cube.SetCubeGripSpeed

enum class SwitchSide {
    LEFT,
    RIGHT
}

class SideAutonomous constructor(switchSide: SwitchSide) : CommandGroup() {

    val angleToTurn = 90.0

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {

        val angle = when (switchSide) {
            SwitchSide.LEFT -> -angleToTurn
            SwitchSide.RIGHT -> angleToTurn
        }

        addSequential(JerkIt())
        addSequential(GoToDistance(feetToInches(1.0)))
        addSequential(TurnToAngle(-angle))
        addSequential(GoToDistance(feetToInches(5.0)))
        addSequential(TurnToAngle(angle))
        addSequential(GoToDistance(feetToInches(7.5)))
        addSequential(TurnToAngle(angle))
        addSequential(GoToDistance(feetToInches(15.0)))
        addSequential(TurnToAngle(angle))
        addSequential(GoToDistance(feetToInches(3.0), 2.0))
        addSequential(SetCubeGripSpeed(-1.0, 5.0))
    }

    fun feetToInches(feet: Double): Double {
        return feet * 12.0
    }


}