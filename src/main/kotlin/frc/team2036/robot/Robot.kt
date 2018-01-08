package frc.team2036.robot

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler

/**
 * Entry point for custom code
 * Defines the main robot class that will be run by the RIO
 * Is an iterative robot so that it can define a set of functions that periodically gets executed
 */
class Robot: IterativeRobot() {

    /**
     * The entry point for a robot, run at the very beginning
     * Entry code doesn't go in a constructor, goes here
     */
    override fun robotInit() {

    }

    override fun autonomousInit() {

    }

    override fun autonomousPeriodic() {
        // Scheduler is what handles all the commands, we periodically run the Scheduler for the Commands to work
        Scheduler.getInstance().run()
    }

    override fun teleopInit() {

    }

    override fun teleopPeriodic() {
        // Scheduler is what handles all the commands, we periodically run the Scheduler for the Commands to work
        Scheduler.getInstance().run()
    }
}