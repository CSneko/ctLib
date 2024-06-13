package org.cneko.ctlib;


import java.util.logging.Logger;


public class Meta {
    public static Meta INSTANCE = new Meta();
    public Logger defaultLogger = Logger.getLogger("ctLib");
    public org.slf4j.Logger logger;
    // 获取java自带的Logger
    public Logger getDefaultLogger() {
        return defaultLogger;
    }
    public void setDefaultLogger(Logger logger) {
        defaultLogger = logger;
    }
    // 获取slf4j的Logger
    public org.slf4j.Logger getSlf4jLogger() {
        return logger;
    }
    public void setSlf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }
}
