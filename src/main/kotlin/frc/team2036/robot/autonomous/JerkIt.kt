package frc.team2036.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team2036.robot.cube.SetCubeGripSpeed
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain
import frc.team2036.robot.elevator.ToTop
import frc.team2036.robot.elevator.elevator

class JerkIt : CommandGroup() {
    init {
        requires(drivetrain)
        requires(cubeGrip)
        requires(elevator)
        addSequential(GoToDistance(6.0))
        addParallel(SetCubeGripSpeed(1.0, 2.0))
        addSequential(GoToDistance(-6.0))
        addSequential(ToTop())
    }
}
