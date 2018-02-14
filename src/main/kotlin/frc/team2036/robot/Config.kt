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
    "sizes" {
        "diameter" to 6 // in inches TODO
        "ticksPerRev" to 360 // TODO
    }
    "ports" {
        "joystick" to 5
        "wheels" {
            /**
             * Ports for real robot:
             * Front
             *  Left: 0
             *  Right: 1
             * Back
             *  Left: 3
             *  Right: 2
             * Ports for test robot:
             * Front
             *  Left: 2
             *  Right: 3
             * Back
             *  Left: 1
             *  Right: 0
             */
            "frontLeft" to 0
            "frontRight" to 4
            "backLeft" to 3
            "backRight" to 2
        }
        "ramps" {
            "leftSpark" to 11
            "rightSpark" to 12
        }
        "cubeGrip" {
            "leftSpark" to 14
            "rightSpark" to 13
        }
        "elevator" to 1
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
        "elevator" to 0.7
    }
    "buttons" {
        "ramps" {
            "toggleButton" to 15
        }
    }
    "verboseLogging" to true //Whether the logger should show all messages, or if it should just show what the drivers need to see
}