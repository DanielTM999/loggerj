package dtm.loggerj.log;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;

public class FileLog implements LoggerJ{

    private int nv;
    private String path = System.getProperty("user.dir")+"/App.log";

    public FileLog(){
        this.nv = 0;
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

    @Override
    public void write(String msg, LogType logType) {
        printLog(msg, logType, null, path);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable) {
        printLog(msg, logType, throwable, path);
    }

    @Override
    public void write(String msg, LogType logType, String filePath) {
        filePath = System.getProperty("user.dir") + "/" + filePath;
        printLog(msg, logType, null, filePath);
    }

    @Override
    public void write(String msg, LogType logType, Throwable throwable, String filePath) {
        filePath = System.getProperty("user.dir") + "/" + filePath;
        printLog(msg, logType, throwable, filePath);
    }

    private void printLog(String msg, LogType logType, Throwable throwable, String pathFile){
       new Thread(() -> {
            if(logType.getValue() >= nv){

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);

                String msgBulder = "%s [%s] -> %s";

                if(throwable != null){
                    msgBulder += ": throwable -> "+throwable.getMessage();
                }
                
                String toWrite = String.format(msgBulder, formattedDate, logType.toString(), msg);
                try {
                    writeFile(toWrite, pathFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
       }).start();
    }

    private void writeFile(String toWrite, String pathFile) throws Exception{
        File file = new File(pathFile);
        if(!file.exists()){
            file.createNewFile();
        }

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(toWrite);
            fileWriter.write(System.lineSeparator());
        }

    }
    
}
