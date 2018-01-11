package frc.team2036.robot.subsystem

import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config

val cubeGrip = CubeGrip()

class CubeGrip : Subsystem() {

    val leftMotor = Spark(config("ports")("intake")["left"] as Int)
    val rightMotor = Spark(config("ports")("intake")["right"] as Int)

    override fun initDefaultCommand() {
    }

    fun moveMotors(isEjecting : Boolean) {
        if (isEjecting) {
            TODO()
        } else {
            TODO()
        }
    }
}