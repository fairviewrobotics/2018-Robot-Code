package frc.team2036.robot.ramp

import edu.wpi.first.wpilibj.DriverStation.MatchType
import edu.wpi.first.wpilibj.DriverStation.getInstance
import edu.wpi.first.wpilibj.Servo
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
class Ramp internal constructor() : Subsystem() {

    private val leftServo = Servo(config("ports")("ramps")["leftServo"] as Int) //The servo that is on the left side of the robot
    private val rightServo = Servo(config("ports")("ramps")["rightServo"] as Int) //The servo that is on the right side of the robot
    var isExtended = false //What state the ramp is in; the ramp is either extended or it isn't

    /**
     * A required method for any subsystem
     * Ramp doesn't have default command
     */
    override fun initDefaultCommand() {}

    /**
     * Switches the ramp state (eg. if the ramps are up, puts them down; if they are down, picks them up)
     * Only needs to be called once to switch the state
     */
    fun toggleRampState() {
        if (getInstance().matchType != MatchType.Practice && Timer.getMatchTime() > 30) {
            logger.log("Driver Error", "Attempted ramp-state toggle before last 30 seconds of match", LogType.WARN)
            return
        }
        if (!this.isExtended) {
            logger.log("Ramp State", "Extending ramp.")
            leftServo.angle += 180
            rightServo.angle += 180
        } else {
            logger.log("Ramp State", "Withdrawing ramp.")
            leftServo.angle -= 180
            rightServo.angle -= 180
        }
    }

}

