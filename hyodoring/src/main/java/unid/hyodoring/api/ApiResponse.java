package unid.hyodoring.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import unid.hyodoring.api.code.BaseCode;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T>  {

    @JsonProperty("isSuccess")
    private boolean isSuccess; // 성공 여부
    private final String code; // 응답 코드
    private final String message; // 응답 메시지

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result; // 응답 데이터


    @JsonProperty("isSuccess")
    public boolean isSuccess() {
        return isSuccess;
    }

    public static <T> ApiResponse<T> onSuccess(SuccessStatus status, T result) {
        return new ApiResponse<>(status, result);
    }

    public static ApiResponse<Void> onSuccess(SuccessStatus status) {
        return new ApiResponse<>(status);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }

    public ApiResponse(SuccessStatus status, T result) {
        this.isSuccess = true;
        this.code = status.getCode();
        this.message = status.getMessage();
        this.result = result;
    }

    public ApiResponse(SuccessStatus status) {
        this.isSuccess = true;
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ApiResponse(ErrorStatus status) {
        this.isSuccess = false;
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public ApiResponse(ErrorStatus status, T result) {
        this.isSuccess = false;
        this.message = status.getMessage();
        this.code = status.getCode();
        this.result = result;
    }
}
