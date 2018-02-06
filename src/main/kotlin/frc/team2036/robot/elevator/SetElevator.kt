package frc.team2036.robot.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.InstantCommand

/**
 * A command that sets the elevator to move to a specific point
 * Can be constructed anywhere and doesn't have an internal constructor
 * Is essentially just a reified function: only gets called once
 */
class SetElevator(private val targetPosition: Double) : InstantCommand() {

    /**
     * Constructs a SetElevator that sets the starting sensor position to 0 with no timeout
     */
    init {
        requires(elevator)
        elevator.elevatorMotor.setSelectedSensorPosition(0,0,0)
    }

    /**
     * Actually moves the elevator to the selected position
     */
    override fun execute() {
        elevator.elevatorMotor.set(ControlMode.MotionMagic, this.targetPosition)
    }


}