package com.apocalypse.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.annotation.concurrent.Immutable;
import java.net.URI;

/**
 * @author jingkaihui
 * @date 2019/2/11
 */
@Getter
@Setter
@Immutable
public final class LoginFailException  extends AbstractThrowableProblem {

    static final URI TYPE = URI.create("https://example.org/login-fail");

    private String code = "2000001";

    public LoginFailException() {
        super(TYPE, "Login Fail", Status.BAD_REQUEST, "login fail");
    }

    public LoginFailException(String message) {
        super(TYPE, "Login Fail", Status.BAD_REQUEST, message);
    }
}
