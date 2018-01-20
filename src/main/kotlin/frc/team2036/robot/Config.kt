package frc.team2036.robot

import edu.wpi.first.wpilibj.CounterBase
import org.jire.kton.kton

/**
 * A JSONesque collection of constants
 * Constants are inputted almost exactly like JSON, but there are a few key differences:
 *  1. Comments can be included
 *  2. To attach something to a key, instead of using the ":" from JSON, a "to" is used
 *  3. Lists and Hierarchies both use {} and immediately follow the identifier and don't need the "to" from 2
 *
 * Access:
 *  1. To access an object (something that has {}) we must use () and a string of the name
 *  2. To access a value (something that has a set value) we must use [] and a string of the name
 */
val config = kton {
    "ports" {
        "joystick" to 5
        "wheels" {
            "frontLeft" to 2
            "frontRight" to 3
            "backLeft" to 1
            "backRight" to 0
        }
        "ramps" {
            "leftServo" to 11
            "rightServo" to 12
        }
        "cubeGrip" {
            "leftSpark" to 14
            "rightSpark" to 13
        }
        "encoders" {
            "type" to CounterBase.EncodingType.k2X
            "backRight" {
                "a" to 2
                "b" to 3
            }
            "backLeft" {
                "a" to 0
                "b" to 1
            }
            "frontRight" {
                "a" to 6
                "b" to 7
            }
            "frontLeft" {
                "a" to 4
                "b" to 5
            }
        }
    }
    "speeds" {
        "wheels" {
            "minimumWheelRotation" to 0.05
            "xMultiplier" to 0.7
            "yMultiplier" to 0.9
            "deadZoneRadius" to 0.1
        }
        "cubeGrip" to 0.5
    }
    "buttons" {
        "ramps" {
            "toggleButton" to 15
        }
        "cubeGrip" {
            "inputButton" to 1
            "outputButton" to 2
            "idleButton" to 3
        }
    }
    "verboseLogging" to true //Whether the logger should show all messages, or if it should just show what the drivers need to see
}