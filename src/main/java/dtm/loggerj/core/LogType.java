package dtm.loggerj.core;

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
}
