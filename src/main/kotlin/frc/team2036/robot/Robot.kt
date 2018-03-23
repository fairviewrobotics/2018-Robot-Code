package frc.team2036.robot

import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler
import frc.team2036.robot.autonomous.GoToDistance
import frc.team2036.robot.autonomous.SwitchSide
import frc.team2036.robot.autonomous.TurnToAngle
import frc.team2036.robot.cube.cubeGrip
import frc.team2036.robot.drivetrain.drivetrain
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

    enum class POSITION {
        LEFT,
        CENTER,
        RIGHT
    }

    private val startPosition = POSITION.CENTER

    private var gameData: String = DriverStation.getInstance().gameSpecificMessage


    //All commands the robot can see; this isn't used and this field is black magic that makes the code work
    private val robotSubsystems = arrayOf(cubeGrip, drivetrain, elevator, ramp)

    override fun robotInit() {
        logger.log("Program Flow", "Robot initializing with ${robotSubsystems.size} subsystems.", LogType.TRACE)
        initButtons() //TODO: move this to teleopInit?
    }

    /**
     * What happens when the robot enters autonomous
     * Starts running the given autonomous command
     */
    override fun autonomousInit() {
        logger.log("Program Flow", "Robot autonomous starting.", LogType.TRACE)
        gameData = DriverStation.getInstance().gameSpecificMessage

        val switchPosition = when (gameData[0].toUpperCase()) {
            'L' -> SwitchSide.LEFT
            'R' -> SwitchSide.RIGHT
            else -> SwitchSide.LEFT
        }

        val scheduler = Scheduler.getInstance()

//        scheduler.add(when (startPosition) {
//            POSITION.LEFT -> {
//                when (switchPosition) {
//                    SwitchSide.LEFT -> StraightAutonomous()
//                    SwitchSide.RIGHT -> SideAutonomous(switchPosition)
//                }
//            }
//            POSITION.CENTER -> {
//                when (switchPosition) {
//                    SwitchSide.LEFT -> CenterAutonomous(switchPosition)
//                    SwitchSide.RIGHT -> CenterAutonomous(switchPosition)
//                }
//            }
//            POSITION.RIGHT -> {
//                when (switchPosition) {
//                    SwitchSide.LEFT -> SideAutonomous(switchPosition)
//                    SwitchSide.RIGHT -> StraightAutonomous()
//                }
//            }
//        })
        scheduler.add(GoToDistance(5.0 * 12.0))
        scheduler.add(TurnToAngle(90.0))
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
     * Cancels the autonomous command and starts the followJoystick command
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
        logger.log("Joystick X", koolKoyJoystick.x)
        logger.log("Joystick Y", koolKoyJoystick.y)
    }

}