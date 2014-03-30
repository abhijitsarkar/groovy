/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN

def appenderList = ['CONSOLE', 'ROLLING']
def patternExpression = "%date{yyyy-MM-dd HH:mm:ss.SSS, EST} [%thread] [%-5level] %logger{3} - %msg%n"
def logDir = 'build/logs'

appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = patternExpression
    }
}

appender('ROLLING', RollingFileAppender) {
    file = "${logDir}/movie-database.log"

    encoder(PatternLayoutEncoder) {
        pattern = patternExpression
    }

    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "${logDir}/movie-database%i.log"
        minIndex = 1
        maxIndex = 2
    }

    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = '5MB'
    }
}

logger('org.springframework', WARN, ['ROLLING'], false)

root(INFO, appenderList)