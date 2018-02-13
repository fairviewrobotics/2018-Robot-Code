package frc.team2036.robot.util

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team2036.robot.config
import java.time.LocalDateTime

//A global logger object
val logger = Logger()

/**
 * While the tag is more specific to the type of logging, LogType specifies what in general the log message describes
 * A message's LogType doesn't actually determine whether it will appear or not; only used for general categorization
 */
enum class LogType {
    TRACE, DEBUG, INFO, NOTICE, WARN, ERROR, FATAL
}

/**
 * A class that handles logging and handles what messages get shown and what don't
 */
class Logger internal constructor() {

    /**
     * A class that is used internally to store all logged messages
     */
    data class Message(val tag: String, val message: String, val type: LogType) {
        val timeStamp = LocalDateTime.now()!!
        override fun toString(): String {
            return "[$timeStamp] {$type} $tag\t: $message"
        }
    }

    //Stores an array of log tags that the drivers will see regardless of whether verboseLogging is true or not
    private val necessaryLogs = arrayOf(
            "Driver Error",
            "CubeGrip State")
    //All the messages that have been sent through the logger; only would be necessary for debugging purposes
    //private val allMessages = mutableListOf<Message>()

    /**
     * Prints the given message conditionally: if the message is tagged with a necessary tag or verboseLogging is enabled
     */
    private fun conditionalPrint(message: Message) {
        if (config["verboseLogging"] as Boolean || necessaryLogs.contains(message.tag)) {
            println(message)
        }
        //allMessages.add(logMessage)
    }

    /**
     * Actually logs a message; if the tag is contained in necessaryLogs, the message is one a driver can see
     */
    fun log(tag: String, message: String, type: LogType = LogType.INFO) {
        val logMessage = Message(tag, message, type)
        SmartDashboard.putString("{$type}: $tag", message)
        conditionalPrint(logMessage)
    }

    /**
     * Logs a numerical message
     * This logging method passes numbers to the dashboard such that they can be graphed if needed
     */
    fun log(tag: String, data: Double, type: LogType = LogType.INFO) {
        val logMessage = Message(tag, data.toString(), type)
        SmartDashboard.putNumber("{$type}: $tag", data)
        conditionalPrint(logMessage)
    }

}