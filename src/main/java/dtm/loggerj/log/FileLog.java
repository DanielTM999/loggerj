package dtm.loggerj.log;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dtm.loggerj.core.LogType;
import dtm.loggerj.core.LoggerJ;

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
        if(filePath.startsWith("/")){
            filePath = path + filePath;
        }else{
            filePath = path + "/" + filePath;
        }
        printLog(msg, logType, throwable, filePath);
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

    protected void printLog(String msg, LogType logType, Throwable throwable, String pathFile){
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
    
}
