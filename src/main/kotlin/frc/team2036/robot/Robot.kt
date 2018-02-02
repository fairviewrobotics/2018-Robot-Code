package frc.team2036.robot

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import frc.team2036.robot.autonomous.autonomous
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain
import frc.team2036.robot.drivetrain.followJoystick
import frc.team2036.robot.elevator.elevator
import frc.team2036.robot.ramp.ramp
import frc.team2036.robot.util.LogType
import frc.team2036.robot.util.logger

/**
 * Entry point for custom code
 * Defines the main robot class that will be run by the RIO
 * Is an iterative robot so that it can define a set of functions that periodically gets executed
 */
class Robot : IterativeRobot() {

    private val robotSubsystems = arrayOf(cubeGrip, drivetrain, elevator, ramp)

    /**
     * The entry point for a robot, run at the very beginning
     * Entry code doesn't go in a constructor, goes here
     */
    override fun robotInit() {
        logger.log("Program Flow", "Robot initializing with ${robotSubsystems.size} subsystems.", LogType.TRACE)
        initButtons() //TODO: move this to teleopInit?

    }

    /**
     * What happens when the robot enters autonomous
     * Defines what commands to run over the autonomous period
     */
    override fun autonomousInit() {
        logger.log("Program Flow", "Robot autonomous starting.", LogType.TRACE)
        autonomous.start()
        Scheduler.getInstance().add(autonomous)
    }

    /**
     * What happens periodically or every tick in autonomous
     * Nothing special is defined here; this method will just continually run the commands that were set in autonomousInit
     */
    override fun autonomousPeriodic() {
        // Scheduler is what handles all the commands, we periodically run the Scheduler for the Commands to work
        logger.log("Auto periodic", "ok", LogType.TRACE)
        Scheduler.getInstance().run()
    }

    /**
     * What happens when the robot enters teleop
     */
    override fun teleopInit() {
        logger.log("Program Flow", "Robot teleoperated starting.", LogType.TRACE)
        followJoystick.start()
        Scheduler.getInstance().add(followJoystick)
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