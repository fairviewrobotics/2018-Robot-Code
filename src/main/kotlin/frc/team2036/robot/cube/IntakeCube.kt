package frc.team2036.robot.cube

import edu.wpi.first.wpilibj.command.Command

val intakeCube = IntakeCube()

class IntakeCube : Command() {

    override fun isFinished(): Boolean {
        return false
    }
}