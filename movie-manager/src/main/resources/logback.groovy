/*
 * Copyright (c) ${date}, the original author or authors.
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

import org.apache.log4j.ConsoleAppender
//import org.apache.log4j.RollingFileAppender

def appenderList = ['CONSOLE']

appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%date{yyyy-MM-dd HH:mm:ss.SSS, EST} [%thread] [%-5level] %logger{3} - %msg%n'
    }
}

//appender("ROLLING", RollingFileAppender) {
//    encoder(PatternLayoutEncoder) {
//        Pattern = "%d %level %thread %mdc %logger - %m%n"
//    }
//    rollingPolicy(TimeBasedRollingPolicy) {
//        FileNamePattern = "${WEBAPP_DIR}/log/translator-%d{yyyy-MM}.zip"
//    }
//}

root(DEBUG, appenderList)