package dtm.loggerj.core;

public interface LoggerJError {
    
    default void writeError(String msg){
        writeError(msg, null, null, "/App.log");
    }
    default void writeError(String msg, String group){
        writeError(msg, group, null, "/App.log");
    }
    default void writeError(String msg, Throwable throwable){
        writeError(msg, null, throwable, "/App.log");
    }
    default void writeError(String msg, String group, String filePath){
        writeError(msg, group, null, "/App.log");
    }
    default void writeError(String msg, String group, Throwable throwable){
        writeError(msg, group, throwable, "/App.log");
    }
    void writeError(String msg, String group, Throwable throwable, String filePath);
}
