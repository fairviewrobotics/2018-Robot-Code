package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.koolKirljoystick
import frc.team2036.robot.koolKoyJoystick
import frc.team2036.robot.util.logger

//A global ToggleRamp object
val toggleRamp = ToggleRamp()

/**
 * A command that can be used to toggle the Ramp subsystem from either extended or not extended
 * Is an instant command and thus is only called once to achieve its purpose
 */
class ToggleRamp internal constructor() : Command() {

    /**
     * The constructor of this command mandates that this command will use the Ramp subsystem
     */
    init {
        requires(ramp)
    }

    /**
     * When this command gets called, makes the ramp toggle its state
     */
    override fun execute() {
        logger.log("Ramp State", "Toggling Ramp State.")
        ramp.releaseRamp(RampSide.LEFT, koolKoyJoystick.getX(GenericHID.Hand.kRight))
        ramp.releaseRamp(RampSide.RIGHT, koolKirljoystick.getX(GenericHID.Hand.kRight))
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end() {
        ramp.releaseRamp(RampSide.LEFT, 0.0)
        ramp.releaseRamp(RampSide.RIGHT, 0.0)
    }

}