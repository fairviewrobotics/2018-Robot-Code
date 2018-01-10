package frc.team2036.robot

import java.time.LocalDateTime

//A global logger object
val logger = Logger()

/**
 * A class that handles logging and handles what messages get shown and what don't
 */
class Logger {

    /**
     * Stores an array of log tags that the drivers will see regardless of whether verboseLogging is true or not
     */
    val necessaryLogs = arrayOf("")

    /**
     * Actually logs a message; if the tag is contained in necessaryLogs, the message is one a driver can see
     */
    fun log(tag: String, message: String) {
        println("[${LocalDateTime.now()}] $tag\t: $message")
        if (config["verboseLogging"] as Boolean || necessaryLogs.contains(tag)) {
            //TODO put message in dashboard
        }
    }

}