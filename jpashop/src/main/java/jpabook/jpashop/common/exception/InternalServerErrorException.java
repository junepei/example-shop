package jpabook.jpashop.common.exception;

import jpabook.jpashop.common.ResponseCode;

public class InternalServerErrorException extends RuntimeException{
    private final ResponseCode responseCode = ResponseCode.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
