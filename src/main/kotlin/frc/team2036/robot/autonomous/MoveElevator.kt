package frc.team2036.robot.autonomous

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.elevator.elevator
import frc.team2036.robot.joystick


class MoveElevator constructor(val targetPosition: Double) : Command() {

    init {
        requires(elevator)

        elevator.elevatorMotor.setSelectedSensorPosition(0,0,0)
    }

    override fun execute() {
        elevator.elevatorMotor.set(ControlMode.MotionMagic, this.targetPosition)
    }

    override fun isFinished(): Boolean {
        return false
    }


}