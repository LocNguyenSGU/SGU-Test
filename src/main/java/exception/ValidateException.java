package exception;

import java.util.Map;

public class ValidateException extends RuntimeException {
    private Map<String , String> errors;
    public ValidateException(Map<String, String> errors) {
        super("Validation Exception");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
