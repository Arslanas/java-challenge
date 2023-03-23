package jp.co.axa.apidemo.exception;

import lombok.Getter;

/**
 * Thrown when resource was not found when queried by ID.
 */
public class EntityNotFound extends RuntimeException implements ApiException{
    @Getter
    private final String message;
    @Getter
    private final String errorCode = "ENTITY_NOT_FOUND";

    public EntityNotFound(long id) {
        this.message = String.format("Resource was not found by id [%s]", id);
    }


    @Override
    public ErrorInfo getErrorInfo() {
        return new ErrorInfo(errorCode, message);
    }
}
