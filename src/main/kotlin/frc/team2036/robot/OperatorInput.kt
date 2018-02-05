package frc.team2036.robot

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team2036.robot.ramp.toggleRamp
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

//Stores a global joystick object
val joystick = XboxController(config("ports")["joystick"] as Int)

//Binds joystick buttons to commands using ports defined in Config; called from Robot
fun initButtons() {
    logger.log("Joystick", "Binding commands to buttons.", LogType.TRACE)
    JoystickButton(joystick, config("buttons")("ramps")["toggleButton"] as Int).whenPressed(toggleRamp)
}