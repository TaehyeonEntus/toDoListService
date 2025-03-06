package com.taehyeon.toDoListService.exception.taskException;

public class NoSuchTaskException extends RuntimeException {
    public NoSuchTaskException() {
    }

    public NoSuchTaskException(String message) {
        super(message);
    }

    public NoSuchTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTaskException(Throwable cause) {
        super(cause);
    }
}
