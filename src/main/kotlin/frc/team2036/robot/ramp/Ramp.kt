package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

val ramp = Ramp()

/**
 * The ramps extend out of the robot to allow other robots to climb on top of it and gain the high ground
 * TODO: Allow ramp to activate only during the last 30 seconds of the match
 */
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

