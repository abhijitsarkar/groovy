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