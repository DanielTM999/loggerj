package dtm.loggerj.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;

public class ConsoleLog implements LoggerJ {

    private int nv;

    public ConsoleLog() {
        this.nv = 0;
    }

    public ConsoleLog(int nv) {
        this.nv = nv;
        if (nv < 0) {
            this.nv = 0;
        }
    }

    public ConsoleLog(LogType nv) {
        this.nv = nv.getValue();
    }

    @Override
    public void write(String msg, LogType logType) {
        write(msg, null, logType, null, null);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable) {
        write(msg, null, logType, throwable, null);
    }

    @Override
    public void write(String msg, LogType logType, String filePath) {
        write(msg, null, logType, null, filePath);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable, String filePath) {
        write(msg, null, logType, throwable, filePath);
    }

    @Override
    public void write(String msg, String group, LogType logType) {
        write(msg, group, logType, null, null);
    }

    @Override
    public void write(String msg, String group, LogType logType, String filePath) {
        write(msg, group, logType, null, filePath);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable) {
        write(msg, group, logType, throwable, null);
    }

    @Override
    public void write(String msg, String group, LogType logType, Throwable throwable, String filePath) {
        printLog(msg, group, logType, throwable);
    }
    

    private void printLog(String msg, String group, LogType logType, Throwable throwable) {
        new Thread(() -> {
            if (logType.getValue() >= nv) {
                String groupFormated = (group == null || group.isEmpty()) ? "Default" : group;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);

                String msgBulder = "%s [%s:'%s'] -> %s";

                if (throwable != null) {
                    msgBulder += ": throwable -> " + throwable.getMessage();
                }

                System.out.println(String.format(msgBulder, formattedDate, logType.toString(), groupFormated, msg));
            }
        }).start();
    }

    //error
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