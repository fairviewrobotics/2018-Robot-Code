package frc.team2036.robot.rampdeploy

import edu.wpi.first.wpilibj.Talon
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

//A global Ramp object
val rampDeploy = RampDeploy()

class RampDeploy internal constructor() : Subsystem() {

    private val ramp = Talon(config("ports")["rampRelease"] as Int)

    override fun initDefaultCommand() {
        this.defaultCommand = rampRelease
    }

    fun moveRampRelease(y: Double) {
        ramp.set(y)
    }

}

