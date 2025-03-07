package dtm.loggerj.log.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;
import dtm.loggerj.log.ConsoleLog;
import dtm.loggerj.log.database.core.LogEntity;
import dtm.loggerj.log.database.core.LogRepository;

public class DatabaseLog<S extends LogEntity> implements LoggerJ{
    
    private final LogRepository<S> repository;
    private final Supplier<S> activatorFunction;
    private final int nv;

    public DatabaseLog(LogRepository<S> repository, Supplier<S> activatorFunction){
        this.repository = repository;
        this.activatorFunction = activatorFunction;
        this.nv = LogType.INFO.getValue();
    }

    public DatabaseLog(int nv, LogRepository<S> repository, Supplier<S> activatorFunction){
        this.repository = repository;
        this.activatorFunction = activatorFunction;
        this.nv = nv;
    }

    public DatabaseLog(LogType nv, LogRepository<S> repository, Supplier<S> activatorFunction){
        this.repository = repository;
        this.activatorFunction = activatorFunction;
        this.nv = nv.getValue();
    }

    @Override
    public void writeInfo(String msg, String group, Throwable throwable, String filePath) {
        write(msg, group, LogType.INFO, null, null);
    }

    @Override
    public void writeError(String msg, String group, Throwable throwable, String filePath) {
        write(msg, group, LogType.ERROR, null, null);
    }

    @Override
    public void write(String msg, LogType logType) {
        write(msg, null, logType, null, null);
    }

    @Override
    public void write(String msg, LogType logType, String filePath) {
        write(msg, null, logType, null, null);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable) {
        write(msg, null, logType, null, null);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable, String filePath) {
        write(msg, null, logType, null, null);
    }

    @Override
    public void write(String msg, String group, LogType logType) {
        write(msg, group, logType, null, null);
    }

    @Override
    public void write(String msg, String group, LogType logType, String filePath) {
        write(msg, group, logType, null, null);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable) {
        write(msg, group, logType, throwable, null);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable, String filePath) {
        try (ExecutorService executor =  Executors.newSingleThreadExecutor()){
            executor.execute(() -> {
                try{
                    if(logType.getValue() >= nv){
                        String groupFormated = (group == null || group.isEmpty()) ? "Default" : group;
                        S entity = activatorFunction.get();
                        if(entity == null){
                            System.out.println("invalid activatorFunction in "+getClass());
                        }
                        entity.setGroup(groupFormated);
                        entity.setMessage(msg);
                        entity.setThrowbleMessage((throwable != null) ? throwable.getMessage() : "");
                        entity.setLogType(logType);
            
                        this.repository.saveLog(entity);
                    }
                } catch (Exception e) {
                    new ConsoleLog().writeError("Error in "+getClass(), e);
                }
            });
        }
    }


}
