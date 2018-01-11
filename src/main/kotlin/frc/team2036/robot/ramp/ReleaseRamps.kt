package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.command.InstantCommand

val releaseRamps = ReleaseRamps()

class ReleaseRamps : InstantCommand() {

    override fun execute() {
        ramp.releaseRamp()
    }

}