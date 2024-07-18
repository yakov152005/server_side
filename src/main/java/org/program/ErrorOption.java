package org.program;

import static org.program.Constants.ErrorNum.*;
import static org.program.Constants.ErrorText.*;

public enum ErrorOption {
    E_0(ERROR_0,TEXT_1),
    E_1(ERROR_1,TEXT_2),
    E_2(ERROR_2,TEXT_1),
    E_3(ERROR_3,TEXT_3),
    E_4(ERROR_4,TEXT_4),
    E_5(ERROR_5,TEXT_5),
    E_6(ERROR_6,TEXT_6),
    E_7(ERROR_7,TEXT_7),
    E_3_0(ERROR_3_0,TEXT_3_0),
    E_3_1(ERROR_3_1,TEXT_3_1),
    E_3_2(ERROR_3_2,TEXT_3_2),
    E_3_3(ERROR_3_3,TEXT_3_3),
    E_3_5(ERROR_3_5,TEXT_3_5)
    ;

    private final int code;
    private final String message;

    ErrorOption(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorOption fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ErrorOption error : values()) {
            if (error.code == code) {
                return error;
            }
        }
        return null;
    }
}

