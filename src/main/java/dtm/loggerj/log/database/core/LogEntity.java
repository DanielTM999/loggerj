package dtm.loggerj.log.database.core;

import dtm.loggerj.core.LogType;

public interface LogEntity {
    String getMessage();
    String getGroup();
    String getThrowbleMessage();
    LogType getLogType();

    void setMessage(String message);
    void setGroup(String message);
    void setThrowbleMessage(String message);
    void setLogType(LogType logType);
}
