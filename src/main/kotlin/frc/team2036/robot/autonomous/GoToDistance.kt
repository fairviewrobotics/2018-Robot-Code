package frc.team2036.robot.autonomous

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Command
import frc.team2036.robot.config
import frc.team2036.robot.drivetrain.drivetrain


/**
 * A command that defines what happens in the autonomous period
 */
class GoToDistance internal constructor(distance: Double) : PIDOutput, Command() {

    var rotateToAngleRate: Double = 0.toDouble()

    /* The following PID Controller coefficients will need to be tuned */
    /* to match the dynamics of your drive system.  Note that the      */
    /* SmartDashboard in Test mode has support for helping you tune    */
    /* controllers by displaying a form where you can enter new P, I,  */
    /* and D constants and test the mechanism.                         */

    val kP = 0.03
    val kI = 0.00
    val kD = 0.00
    val kF = 0.00

    val kToleranceDegrees = 2.0


    var distance = distance

    var ahrs = AHRS(SPI.Port.kMXP)

    var turnController = PIDController(kP, kI, kD, kF, ahrs, this)


    val diameter = config("sizes")["diameter"] as Double //wheel diameter in inches
    val ticksPerRev = config("sizes")["ticksPerRev"] as Double //how many encoder ticks happen in 1 wheel revolution
    val circumference = diameter * Math.PI //circumference of a wheel

    /**
     * When the command is created, it specifies that it needs the drivetrain and cubegrip
     */
    init {
        requires(drivetrain)

        turnController.setInputRange(-180.0, 180.0)
        turnController.setOutputRange(-1.0, 1.0)
        turnController.setAbsoluteTolerance(kToleranceDegrees)
        turnController.setContinuous(true)
        turnController.disable()
    }

    override fun initialize() {
        ahrs.zeroYaw()

        drivetrain.leftEncoder.reset()
        drivetrain.rightEncoder.reset()

    }

    /**
     * While the command is running, the robot moves forward
     */
    override fun execute() {

        if (!turnController.isEnabled) {
            turnController.setpoint = ahrs.yaw.toDouble()
            rotateToAngleRate = 0.0 // This value will be updated in the pidWrite() method.
            turnController.enable()
        }


        val magnitude = 0.5
        val leftStickValue = magnitude + rotateToAngleRate
        val rightStickValue = magnitude - rotateToAngleRate
        drivetrain.tankDrive(leftStickValue, rightStickValue)
    }

    /**
     * The command finishes once the drivetrain has moved past the specified distance
     */
    override fun isFinished(): Boolean {
        return pulsesToInches(getAverageEncoderPosition().toInt()) > distance
    }

    fun pulsesToInches(pulses: Int): Double {
        return circumference / ticksPerRev * pulses
    }

    fun inchesToPulses(inches: Double): Int {
        return (inches / circumference * ticksPerRev).toInt()
    }

    /**
     * When the command is over, it sets the cubegrip to be outputting
     */
    override fun end() {
        if (turnController.isEnabled) {
            turnController.disable()
        }
    }

    override fun pidWrite(output: Double) {
        rotateToAngleRate = output
    }

    private fun getAverageEncoderPosition(): Double {
        return (drivetrain.leftEncoder.get().toDouble() + drivetrain.rightEncoder.get().toDouble()) / 2
    }
}