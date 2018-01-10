package frc.team2036.robot.subsystem

import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

val ramp = Ramp()

class Ramp : Subsystem() {

    val servoL = Servo(config("ports")("ramps")["servoL"] as Int)
    val servoR = Servo(config("ports")("ramps")["servoR"] as Int)

    override fun initDefaultCommand() {}

    /**
     * Rotate servos
     */
    fun releaseRamp() {
        servoL.angle += 180
        servoR.angle += 180
    }

}

