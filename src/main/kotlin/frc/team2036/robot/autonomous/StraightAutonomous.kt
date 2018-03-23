package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team2036.robot.cube.SetCubeGripSpeed

class StraightAutonomous constructor() : CommandGroup() {

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {
        addSequential(JerkIt())
        addSequential(GoToDistance(feetToInches(10.0)), 8.0)
        addSequential(SetCubeGripSpeed(-1.0, 5.0))
    }

    fun feetToInches(feet: Double): Double {
        return feet * 12.0
    }


}