package exception;

import java.util.Map;

public class RetrieveException extends RuntimeException{
    private Map<String , String> errors;
    public RetrieveException(Map<String, String> errors) {
        super("Get Data Exception");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
