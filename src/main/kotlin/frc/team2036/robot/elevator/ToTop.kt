package frc.team2036.robot.elevator

import edu.wpi.first.wpilibj.command.Command

val toTop = ToTop()

class ToTop internal constructor() : Command() {

    /**
     * Upon construction, a ChangeElevator requires an elevator
     */
    init {
        requires(elevator)
    }

    /**
     * Every time this command executes, it moves the elevator based on the right koolKoyJoystick value
     */
    override fun execute() {
        elevator.drive(.4)
    }

    /**
     * This command never finishes
     */
    override fun isFinished(): Boolean {
        return !elevator.topLimit.get()
    }

    override fun end() {
        elevator.drive(0.0)
    }
}