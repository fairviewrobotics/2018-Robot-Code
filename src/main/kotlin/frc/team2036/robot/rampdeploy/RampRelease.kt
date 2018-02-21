package frc.team2036.robot.rampdeploy

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.koolKirljoystick
import frc.team2036.robot.ramp.RampSide
import frc.team2036.robot.ramp.ramp
import frc.team2036.robot.util.logger


val rampRelease = RampRelease()

class RampRelease internal constructor() : Command() {

    init {
        requires(ramp)
    }

    override fun execute() {
        logger.log("Ramp Release State", "Toggling Ramp State.")

        val y = when {
            koolKirljoystick.getBumperPressed(GenericHID.Hand.kLeft) -> -.4
            koolKirljoystick.getBumperPressed(GenericHID.Hand.kRight) -> .4
            else -> 0.0
        }

        rampDeploy.moveRampRelease(y)
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end() {
        ramp.releaseRamp(RampSide.LEFT, 0.0)
        ramp.releaseRamp(RampSide.RIGHT, 0.0)
    }

}