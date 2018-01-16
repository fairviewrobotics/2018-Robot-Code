package frc.team2036.robot

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.Logger

/**
 * Entry point for custom code
 * Defines the main robot class that will be run by the RIO
 * Is an iterative robot so that it can define a set of functions that periodically gets executed
 */
class Robot : IterativeRobot() {

    private val logger = Logger()

    /**
     * The entry point for a robot, run at the very beginning
     * Entry code doesn't go in a constructor, goes here
     */
    override fun robotInit() {
        logger.log("Program Flow", "Robot initializing.", LogType.TRACE)
        //TODO log all config stuff and ports here?
    }

    /**
     * What happens when the robot enters autonomous
     * Defines what commands to run over the autonomous period
     */
    override fun autonomousInit() {
        logger.log("Program Flow", "Robot autonomous starting.", LogType.TRACE)
    }

    /**
     * What happens periodically or every tick in autonomous
     * Nothing special is defined here; this method will just continually run the commands that were set in autonomousInit
     */
    override fun autonomousPeriodic() {
        // Scheduler is what handles all the commands, we periodically run the Scheduler for the Commands to work
        Scheduler.getInstance().run()
    }

    /**
     * What happens when the robot enters teleop
     */
    override fun teleopInit() {
        logger.log("Program Flow", "Robot teleoperated starting.", LogType.TRACE)
    }

    /**
     * What happens periodically or every tick in teleop
     */
    override fun teleopPeriodic() {
        // Scheduler is what handles all the commands, we periodically run the Scheduler for the Commands to work
        Scheduler.getInstance().run()
        logger.log("Joystick X", joystick.x)
        logger.log("Joystick Y", joystick.y)
    }

}