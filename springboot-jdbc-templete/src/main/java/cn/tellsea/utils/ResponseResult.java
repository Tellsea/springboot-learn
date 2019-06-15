package cn.tellsea.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private Integer code;

    private String msg;

    private Object data;
}
