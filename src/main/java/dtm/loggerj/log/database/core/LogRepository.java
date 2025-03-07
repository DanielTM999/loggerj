package dtm.loggerj.log.database.core;

public interface LogRepository<S extends LogEntity> {
    void saveLog(S entity);
}
