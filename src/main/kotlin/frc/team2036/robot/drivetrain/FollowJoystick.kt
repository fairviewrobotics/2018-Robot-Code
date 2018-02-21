package frc.team2036.robot.drivetrain

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.cube.CubeGripState
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.koolKoyJoystick
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

val followJoystick = FollowJoystick()

/**
 * A Command that connects the koolKoyJoystick to the drivetrain
 * Just sends all koolKoyJoystick inputs to the robot
 */
class FollowJoystick internal constructor() : Command() {

    private val xMultiplier = config("speeds")("wheels")["xMultiplier"] as Double
    private val yMultiplier = config("speeds")("wheels")["yMultiplier"] as Double

    /**
     * Constructor for a FollowJoystick command
     * Just states that this command needs the drivetrain
     */
    init {
        requires(drivetrain)
    }

    /**
     * Moves the drive train based on koolKoyJoystick's x and y coordinates
     * Just takes in the koolKoyJoystick axes and passes them to the drivetrain to handle driving
     */
    override fun execute() {
        drivetrain.drive(-processJoystickValue(koolKoyJoystick.getX(GenericHID.Hand.kLeft)) * xMultiplier, processJoystickValue(koolKoyJoystick.getY(GenericHID.Hand.kLeft)) * yMultiplier)
    }

    /**
     * What happens when the command starts
     */
    override fun start() {
        logger.log("Program Flow", "FollowJoystick command starting.", LogType.TRACE)
    }

    /**
     * When the command is over, it sets the drivetrain to move by no amount
     */
    override fun end() {
        logger.log("Program Flow", "FollowJoystick command ending.", LogType.TRACE)
        drivetrain.drive(0.0, 0.0)
        cubeGrip.state = CubeGripState.IDLE
    }

    /**
     * FollowJoystick is never finished unless it is interrupted
     */
    override fun isFinished(): Boolean {
        return false
    }

    /**
     * Takes in a koolKoyJoystick x or y value and processes the value so that they can be adjusted
     * Would ideally work in polar and then return x and y components, but koolKoyJoystick has no polar methods
     */
    private fun processJoystickValue(component: Double): Double {
        val minimumWheelRotation = config("speeds")("wheels")["minimumWheelRotation"] as Double
        return (1 - minimumWheelRotation) * (Math.pow(component, 3.0)) + minimumWheelRotation * if (component > 0) 1 else -1
    }

}