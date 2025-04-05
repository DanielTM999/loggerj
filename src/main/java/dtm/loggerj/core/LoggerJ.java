package dtm.loggerj.core;

import dtm.loggerj.core.handler.WriteHandler;

public interface LoggerJ extends LoggerJInfo, LoggerJError{
    void write(String msg, LogType logType);
    void write(String msg, LogType logType, String filePath);
    void write(String msg, LogType logType, Throwable throwable);
    void write(String msg, LogType logType, Throwable throwable, String filePath);

    void write(String msg, String group, LogType logType);
    void write(String msg, String group, LogType logType, String filePath);
    void write(String msg, String group, LogType logType, Throwable throwable);
    void write(String msg, String group, LogType logType, WriteHandler handler);
    void write(String msg, String group, LogType logType, Throwable throwable, String filePath);


}
