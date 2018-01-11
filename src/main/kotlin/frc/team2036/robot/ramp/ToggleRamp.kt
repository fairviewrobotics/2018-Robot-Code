package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team2036.robot.logger

//A global ToggleRamp object
val toggleRamp = ToggleRamp()

/**
 * A command that can be used to toggle the Ramp subsystem from either extended or not extended
 * Is an instant command and thus is only called once to achieve its purpose
 */
class ToggleRamp : InstantCommand() {

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
        ramp.toggleRampState()
    }

}