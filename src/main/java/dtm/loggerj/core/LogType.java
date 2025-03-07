package dtm.loggerj.core;

import java.util.Arrays;

public enum LogType {
    ERROR(2),
    WARNING(1),
    INFO(0);

    private final int value;

    LogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LogType getByValue(int value, LogType defaultLogType){
        return Arrays.asList(LogType.values()).stream().filter(type -> type.getValue() == value).findFirst().orElse(defaultLogType);
    }
}
