package cn.tellsea.exception;

import lombok.Getter;
import lombok.Setter;

public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String message) {
        this.msg = message;
    }
}
