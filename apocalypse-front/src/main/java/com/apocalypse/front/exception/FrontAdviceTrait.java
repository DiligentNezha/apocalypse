package com.apocalypse.front.exception;

import com.apocalypse.common.exception.FrontException;
import org.apiguardian.api.API;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

/**
 * @see HttpMediaTypeNotSupportedException
 * @see Status#UNSUPPORTED_MEDIA_TYPE
 */
@API(status = STABLE)
public interface FrontAdviceTrait extends AdviceTrait {

    @API(status = INTERNAL)
    @ExceptionHandler
    default ResponseEntity<Problem> handleFrontException(
            final FrontException frontException,
            final NativeWebRequest request) {
        String title = "System Busy";

        String msg = frontException.getMessage();
        String code = frontException.getCode();

        Problem frontProblem =
                Problem.builder()
                        .withTitle(title)
                        .with("code", code)
                        .with("msg", msg)
                        .build();
        return create(frontException, frontProblem, request);
    }

}
