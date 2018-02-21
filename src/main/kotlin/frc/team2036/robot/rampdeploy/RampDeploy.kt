package frc.team2036.robot.rampdeploy

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

//A global Ramp object
val rampDeploy = RampDeploy()

class RampDeploy internal constructor() : Subsystem() {

    private val ramp = WPI_TalonSRX(config("ports")["rampRelease"] as Int)

    override fun initDefaultCommand() {
        this.defaultCommand = rampRelease
    }

    fun moveRampRelease(y: Double) {
        ramp.set(y)
    }

}

