package unid.hyodoring.api.exception;

import lombok.Getter;
import lombok.Setter;
import unid.hyodoring.api.code.status.ErrorStatus;

@Getter
@Setter
public class GeneralException extends RuntimeException {
    private ErrorStatus status;
    public GeneralException(ErrorStatus status) {
        super(status.getCode());
        this.status = status;
    }
}
