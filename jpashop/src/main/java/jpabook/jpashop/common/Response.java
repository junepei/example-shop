package jpabook.jpashop.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Response <T>{
    private T data;
    private boolean success;
    private ResponseCode code;
    private String message;

    private Response(T data) {
        this.data = data;
        success = true;
        code = ResponseCode.SUCCESS;
    }
    private Response(boolean success, ResponseCode responseCode, String message) {
        this.success = success;
        this.code = responseCode;
        this.message = message;
    }
    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> success() {
        Response response = new Response();
        response.setSuccess(true);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

    public static <T> Response<T> error(ResponseCode responseCode, String message) {
        return new Response<>(false, responseCode, message);
    }
}
