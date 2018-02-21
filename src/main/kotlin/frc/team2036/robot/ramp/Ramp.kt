package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.DriverStation.MatchType
import edu.wpi.first.wpilibj.DriverStation.getInstance
import edu.wpi.first.wpilibj.Spark
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team2036.robot.config
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

//A global Ramp object
val ramp = Ramp()

/**
 * The ramp is a subsystem that will either extend or de-extend
 * Controlled by 2 servos, its only purpose is to be in either of those toggled states
 */

enum class RampSide {
    LEFT, RIGHT
}
class Ramp internal constructor() : Subsystem() { // TODO VERY IMPORTANT: STOP MOTOR ON STALL

    private val leftSpark = Spark(config("ports")("ramps")["leftSpark"] as Int) //The servo that is on the left side of the robot
    private val rightSpark = Spark(config("ports")("ramps")["rightSpark"] as Int) //The servo that is on the right side of the robot

    /**
     * A required method for any subsystem
     * Ramp doesn't have default command
     */
    override fun initDefaultCommand() {
        this.defaultCommand = toggleRamp
    }

    /**
     * Switches the ramp state (eg. if the ramps are up, puts them down; if they are down, picks them up)
     * Only needs to be called once to switch the state
     */
    fun releaseRamp(side: RampSide, y: Double) {
        if (getInstance().matchType != MatchType.Practice && Timer.getMatchTime() > 30) {
            logger.log("Driver Error", "Attempted ramp-state toggle before last 30 seconds of match", LogType.WARN)
            return
        }

        val motor = when (side) {
            RampSide.LEFT -> leftSpark
            RampSide.RIGHT -> rightSpark
        }

        motor.speed = y
    }

}

