package indi.zyf.sso.core.security.exception;


import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerificationCodeException(String msg) {
        super(msg);
    }
}
