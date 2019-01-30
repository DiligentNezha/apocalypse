package com.apocalypse.front.exception;

import com.apocalypse.common.exception.ControllerException;
import org.apiguardian.api.API;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

/**
 * @see ControllerException
 * @see Status#INTERNAL_SERVER_ERROR
 */
@API(status = STABLE)
public interface ControllerAdviceTrait extends AdviceTrait {

    @API(status = INTERNAL)
    @ExceptionHandler
    default ResponseEntity<Problem> handleFrontException(
            final ControllerException controllerException,
            final NativeWebRequest request) {
        String title = "System Busy";

        String msg = controllerException.getMessage();
        String code = controllerException.getCode();

        switch (code.charAt(1)) {
            case '1':
                title = "DB Exception";
                break;
            case '2':
                title = "Service Exception";
                break;
            case '3':
                title = "Dubbo Exception";
                break;
            case '4':
                title = "Business Exception";
                break;
            case '5':
                title = "Controller Exception";
                break;
            default:
        }

        Problem frontProblem =
                Problem.builder()
                        .withTitle(title)
                        .with("code", code)
                        .with("msg", msg)
                        .build();
        return create(controllerException, frontProblem, request);
    }

}
