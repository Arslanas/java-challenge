package jp.co.axa.apidemo.exception;


import lombok.Data;

/**
 * Data class meant to be serialized as json API response to provide error information
 */
@Data
public class ErrorInfo {

    private String errorCode; // could be used by thirdParty services or frontend to provide meaningful error handling
    private String message; // description of error

    public ErrorInfo(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
