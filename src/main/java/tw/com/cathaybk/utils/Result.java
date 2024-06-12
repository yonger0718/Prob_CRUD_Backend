package tw.com.cathaybk.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(1);
        return result;
    }
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(msg);
        return result;
    }
}
