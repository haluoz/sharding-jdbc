package com.lxj.shardingjdbc.log;

/**
 * @author Xingjing.Li
 * @since 2021/12/1
 */
public class MethodExecuteResult {
    private boolean executionResult;
    private Exception exception;
    private String errorMessage;

    public MethodExecuteResult(boolean executionResult, Exception exception, String errorMessage) {
        this.executionResult = executionResult;
        this.exception = exception;
        this.errorMessage = errorMessage;
    }

    public boolean isExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(boolean executionResult) {
        this.executionResult = executionResult;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess(){
        return executionResult;
    }
}
