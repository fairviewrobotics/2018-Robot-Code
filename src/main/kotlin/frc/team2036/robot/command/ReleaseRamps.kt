package frc.team2036.robot.command

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team2036.robot.subsystem.ramp

val releaseRamps = ReleaseRamps()

class ReleaseRamps : InstantCommand() {

    override fun execute() {
        ramp.releaseRamp()
    }

}