package dtm.loggerj.core.handler;

import dtm.loggerj.core.LogType;

public abstract class HandlerObject {
    public abstract Object getvalue();
    public abstract LogType getLogType();
    public abstract Throwable getThrowable();
    public abstract String getGroup();

    public abstract void setValue(Object value);
}
