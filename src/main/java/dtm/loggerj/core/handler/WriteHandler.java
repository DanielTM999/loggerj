package dtm.loggerj.core.handler;

@FunctionalInterface
public interface WriteHandler {
    void onAction(HandlerObject handlerObject);
}
