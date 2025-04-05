package dtm.loggerj.log;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;
import dtm.loggerj.core.handler.HandlerObject;
import dtm.loggerj.core.handler.WriteHandler;

public class FileLog implements LoggerJ{

    private int nv;
    protected String path = System.getProperty("user.dir");

    public FileLog(){
        this.nv = 0;
    }

    public FileLog(String area){
        this.nv = 0;
        setArea(area);
    }

    public FileLog(int nv){
        this.nv = nv;
        if(nv < 0){
            this.nv = 0;
        }
    }

    public FileLog(LogType nv){
        this.nv = nv.getValue();  
    }

    public FileLog(int nv, String area){
        this.nv = nv;
        if(nv < 0){
            this.nv = 0;
        }
        setArea(area);
    }

    public FileLog(LogType nv, String area){
        this.nv = nv.getValue();
        setArea(area);
    }

    @Override
    public void write(String msg, LogType logType) {
        write(msg, logType, null, "/App.log");
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable) {
        write(msg, logType, throwable, "/App.log");
    }

    @Override
    public void write(String msg, LogType logType, String filePath) {
        write(msg, logType, null, filePath);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable, String filePath) {
        write(msg, null, logType, throwable, filePath);
    }

    @Override
    public void write(String msg, String group, LogType logType) {
        write(msg, group, logType, null, "/App.log");
    }

    @Override
    public void write(String msg, String group, LogType logType, String filePath) {
        write(msg, group, logType, null, filePath);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable) {
        write(msg, group, logType, throwable, "/App.log");
    }

    @Override
    public void write(String msg, String group, LogType logType, WriteHandler handler) {
        printLog(msg, group, logType, null, path +"/App.log", handler);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable, String filePath) {
        if(filePath.startsWith("/")){
            filePath = path + filePath;
        }else{
            filePath = path + "/" + filePath;
        }
        printLog(msg, group, logType, throwable, filePath);
    }
    
    public void setArea(String area){
        if(!area.startsWith("/")){
            path = path + "/" + area;
        }else{
            path = path + area;
        }

        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    protected void printLog(String msg, String group, LogType logType, Throwable throwable, String pathFile){
        printLog(msg, group, logType, throwable, pathFile, null);
    }

    protected void printLog(String msg, String group, LogType logType, Throwable throwable, String pathFile, WriteHandler handler){
       new Thread(() -> {
            if(logType.getValue() >= nv){

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);
                String groupFormated = (group == null || group.isEmpty()) ? "Default" : group;
                
                String msgBulder = "%s [%s:'%s'] -> %s";

                final AtomicReference<String> finalMessage = new AtomicReference<>(
                        String.format(msgBulder, formattedDate, logType.toString(), groupFormated, msg)
                );

                if (throwable != null) {
                    finalMessage.set(finalMessage.get() + ": throwable -> " + throwable.getMessage());
                }

                if(handler != null){
                    handler.onAction(new HandlerObject() {
                        @Override
                        public Object getvalue() {
                            return finalMessage.get();
                        }

                        @Override
                        public LogType getLogType() {
                            return logType;
                        }

                        @Override
                        public Throwable getThrowable() {
                            return throwable;
                        }

                        @Override
                        public String getGroup() {
                            return group;
                        }

                        @Override
                        public void setValue(Object value) {
                            finalMessage.set(value.toString());
                        }
                    });
                }
                String toWrite = finalMessage.get();
                try {
                    writeFile(toWrite, pathFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
       }).start();
    }

    protected void writeFile(String toWrite, String pathFile) throws Exception{
        File file = new File(pathFile);
        if(!file.exists()){
            file.createNewFile();
        }

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(toWrite);
            fileWriter.write(System.lineSeparator());
        }

    }


    //info
    @Override
    public void writeInfo(String msg, String group, Throwable throwable, String filePath) {
        write(msg, group, LogType.INFO, throwable, filePath);
    }

    //error
    @Override
    public void writeError(String msg, String group, Throwable throwable, String filePath) {
        write(msg, group, LogType.ERROR, throwable, filePath);
    }

}
