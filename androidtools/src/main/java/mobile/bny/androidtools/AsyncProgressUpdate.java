package mobile.bny.androidtools;

public class AsyncProgressUpdate {
    private int progress;
    private String message;
    private Object[] params;

    public AsyncProgressUpdate(String message) {
        this.message = message;
    }

    public AsyncProgressUpdate(String message, Object[] params) {
        this.message = message;
        this.params = params;
    }

    public AsyncProgressUpdate(int progress, String message) {
        this(progress, message, (Object[]) null);
    }

    public AsyncProgressUpdate(int progress, String message, Object... params) {
        this.progress = progress;
        this.message = message;
        this.params = params;
    }

    public int getProgress() {
        return progress;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getParams() {
        return params;
    }
}
