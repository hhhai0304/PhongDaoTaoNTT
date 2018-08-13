package vn.name.hohoanghai.models;

public class RequestResult {
    private String header;
    private String body;
    private boolean isSuccess;
    private String errorMessage;

    public RequestResult(String errorMessage) {
        isSuccess = false;
        this.errorMessage = errorMessage;
    }

    public RequestResult(boolean isSuccess, String body) {
        this.isSuccess = isSuccess;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}