package frc.team2036.robot.util

import org.koin.dsl.module.applicationContext

val robotModule = applicationContext {
    provide { Logger() }
}