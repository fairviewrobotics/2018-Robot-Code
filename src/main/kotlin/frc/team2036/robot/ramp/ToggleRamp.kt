package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team2036.robot.joystick
import frc.team2036.robot.joystick2
import frc.team2036.robot.util.logger

//A global ToggleRamp object
val toggleRamp = ToggleRamp()

/**
 * A command that can be used to toggle the Ramp subsystem from either extended or not extended
 * Is an instant command and thus is only called once to achieve its purpose
 */
class ToggleRamp internal constructor() : InstantCommand() {

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
        ramp.releaseRamp(RampSide.LEFT, joystick.y)
        ramp.releaseRamp(RampSide.RIGHT, joystick2.y)
    }

}