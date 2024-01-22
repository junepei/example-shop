package jpabook.jpashop.common.exception;

import jpabook.jpashop.common.ResponseCode;

public class BadRequestException extends RuntimeException{
    private final ResponseCode responseCode = ResponseCode.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }
}
