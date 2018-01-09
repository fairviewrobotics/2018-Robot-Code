package frc.team2036.robot.command

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.subsystem.Drivetrain

/**
 * A Command that connects the joystick to the drivetrain
 * Just sends all joystick inputs to the robot
 */
class FollowJoystick : Command() {

    //Stores the WPILib joystick for internal use
    private val joystick = Joystick(config("ports")["joystick"] as Int)

    //Constructs an immutable drivetrain
    private val drivetrain = Drivetrain()

    /**
     * Constructor for a FollowJoystick command
     * Just states that this command needs the drivetrain
     */
    init {
        requires(Drivetrain())
    }

    /**
     * Moves the drive train based on joystick's x and y coordinates
     * Just takes in the joystick axes and passes them to the drivetrain to handle driving
     */
    override fun execute() {
        drivetrain.drive(joystick.x, joystick.y)
    }

    /**
     * When the command is over, it sets the drivetrain to move by no amount
     */
    override fun end() {
        drivetrain.drive(0.0, 0.0)
    }

    /**
     * TODO figure out how this works
     */
    override fun isFinished(): Boolean {
        return false
    }
}