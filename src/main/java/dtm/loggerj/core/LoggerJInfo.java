package dtm.loggerj.core;

public interface LoggerJInfo {
    default void writeInfo(String msg){
        writeInfo(msg, null, null, "/App.log");
    }
    default void writeInfo(String msg, String group){
        writeInfo(msg, group, null, "/App.log");
    }
    default void writeInfo(String msg, String group, String filePath){
        writeInfo(msg, group, null, "/App.log");
    }
    default void writeInfo(String msg, String group, Throwable throwable){
        writeInfo(msg, group, throwable, "/App.log");
    }
    void writeInfo(String msg, String group, Throwable throwable, String filePath);
}
