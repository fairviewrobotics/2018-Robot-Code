package frc.team2036.robot

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team2036.robot.elevator.toBottom
import frc.team2036.robot.elevator.toTop
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

//Stores a global koolKoyJoystick object
val koolKoyJoystick = XboxController(config("ports")["koolKoyJoystick"] as Int)
val koolKirljoystick = XboxController(config("ports")["koolKirljoystick"] as Int)

//Binds koolKoyJoystick buttons to commands using ports defined in Config; called from Robot
fun initButtons() {
    logger.log("Joystick", "Binding commands to buttons.", LogType.TRACE)

    val elevatorToTopButton = JoystickButton(koolKirljoystick, 3)
    val elevatorToBottomButton = JoystickButton(koolKirljoystick, 0)

    elevatorToTopButton.whenPressed(toTop)
    elevatorToBottomButton.whenPressed(toBottom)
}

/**
 * Sets rumble for controller 2 for both hands
 */
fun rumble(rumble: Double) {
    koolKirljoystick.setRumble(GenericHID.RumbleType.kLeftRumble, rumble)
    koolKirljoystick.setRumble(GenericHID.RumbleType.kRightRumble, rumble)
}