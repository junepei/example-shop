package jpabook.jpashop.common;

import jpabook.jpashop.common.exception.BadRequestException;
import jpabook.jpashop.common.exception.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JpaShopExceptionHandler {
    private final static String COMMON_ERROR_MESSAGE = "서버에 알수 없는 오류가 발생했습니다.";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(exception.getResponseCode(), exception.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Response> handleInternalServerErrorException(InternalServerErrorException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(exception.getResponseCode(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ResponseCode.ERROR, COMMON_ERROR_MESSAGE));
    }
}
