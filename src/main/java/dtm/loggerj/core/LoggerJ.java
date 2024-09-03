package dtm.loggerj.core;

public interface LoggerJ {
    void write(String msg, LogType logType);
    void write(String msg, LogType logType, String filePath);
    void write(String msg, LogType logType, Throwable throwable);
    void write(String msg, LogType logType, Throwable throwable, String filePath);
}
