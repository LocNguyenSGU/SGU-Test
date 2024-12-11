package payload.response;

import com.google.gson.annotations.Expose;

import java.io.PrintStream;

public class Response {
    @Expose
    private Object data;
    @Expose
    private int statusCode;
    @Expose
    private String message;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
