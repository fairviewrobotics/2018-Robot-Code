package frc.team2036.robot.elevator

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.joystick2

val changeElevator = ChangeElevator()

/**
 * A command that changes the elevator height based on joystick values
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
     * Sets rumble for controller 2 for both hands
     */
    private fun rumble(rumble: Double) {
        joystick2.setRumble(GenericHID.RumbleType.kLeftRumble, rumble)
        joystick2.setRumble(GenericHID.RumbleType.kRightRumble, rumble)
    }

    /**
     * Every time this command executes, it moves the elevator based on the right joystick value
     */
    override fun execute() {
//        elevator.drive(-processJoystickValue(joystick.getY(GenericHID.Hand.kRight)))
        elevator.drive(joystick2.getY(GenericHID.Hand.kRight))
        val sensor = elevator.elevatorMotor.sensorCollection

        if (sensor.isFwdLimitSwitchClosed || sensor.isRevLimitSwitchClosed) {
            rumble(1.0)
        } else {
            rumble(0.0)
        }
        // TODO: add elevator top and bottom code
    }

    /**
     * This command never finishes
     */
    override fun isFinished(): Boolean {
        return false
    }

    /**
     * Takes in a joystick x or y value and processes the value so that they can be adjusted
     * Would ideally work in polar and then return x and y components, but joystick has no polar methods
     * TODO: should this code be common to followjoystick and this? if so, this method should be moved to operatorInput
     */
    private fun processJoystickValue(component: Double): Double {
        val minimumWheelRotation = config("speeds")("wheels")["minimumWheelRotation"] as Double
        return (1 - minimumWheelRotation) * (Math.pow(component, 3.0)) + minimumWheelRotation * if (component > 0) 1 else -1
    }

}