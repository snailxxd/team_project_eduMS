package queries;

public class ApiResult {

    public boolean result;

    public String message;

    public Object return_data;

    public ApiResult(boolean result, Object return_data) {
        this.result = result;
        this.return_data = return_data;
    }

    public ApiResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ApiResult(boolean result, String message, Object return_data) {
        this.result = result;
        this.message = message;
        this.return_data = return_data;
    }
}
