package frc.team2036.robot.elevator

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.koolKirljoystick
import frc.team2036.robot.rumble

val changeElevator = ChangeElevator()

/**
 * A command that changes the elevator height based on koolKoyJoystick values
 * Is run continually
 */
class ChangeElevator internal constructor() : Command() {

    /**
     * Upon construction, a ChangeElevator requires an elevator
     */
    init {
        requires(elevator)
    }

    /**
     * Every time this command executes, it moves the elevator based on the right koolKoyJoystick value
     */
    override fun execute() {
        val driveValue = -koolKirljoystick.getY(GenericHID.Hand.kRight)

        if ((driveValue > 0 && !elevator.topLimit.get()) || (driveValue < 0 && !elevator.bottomLimit.get())) {
            rumble(1.0)
        } else {
            elevator.drive(driveValue)
            rumble(0.0)
        }
    }

    /**
     * This command never finishes
     */
    override fun isFinished(): Boolean {
        return false
    }

    /**
     * Takes in a koolKoyJoystick x or y value and processes the value so that they can be adjusted
     * Would ideally work in polar and then return x and y components, but koolKoyJoystick has no polar methods
     * TODO: should this code be common to followjoystick and this? if so, this method should be moved to operatorInput
     */
    private fun processJoystickValue(component: Double): Double {
        val minimumWheelRotation = config("speeds")("wheels")["minimumWheelRotation"] as Double
        return (1 - minimumWheelRotation) * (Math.pow(component, 3.0)) + minimumWheelRotation * if (component > 0) 1 else -1
    }

}