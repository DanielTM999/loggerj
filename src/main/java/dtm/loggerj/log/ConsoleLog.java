package dtm.loggerj.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;

public class ConsoleLog implements LoggerJ{

    private int nv;

    public ConsoleLog(){
        this.nv = 0;
    }

    public ConsoleLog(int nv){
        this.nv = nv;
        if(nv < 0){
            this.nv = 0;
        }
    }

    public ConsoleLog(LogType nv){
        this.nv = nv.getValue();  
    }

    @Override
    public void write(String msg, LogType logType) {
        printLog(msg, logType, null);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable) {
        printLog(msg, logType, throwable);
    }

    private void printLog(String msg, LogType logType, Throwable throwable){
       new Thread(() -> {
            if(logType.getValue() >= nv){

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);

                String msgBulder = "%s [%s] -> %s";

                if(throwable != null){
                    msgBulder += ": throwable -> "+throwable.getMessage();
                }
                
                System.out.println(String.format(msgBulder, formattedDate, logType.toString(), msg));
            }
       }).start();
    }

    @Override
    public void write(String msg, LogType logType, String filePath) {
        printLog(msg, logType, null);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable, String filePath) {
        printLog(msg, logType, throwable);
    }
    
}
