package com.apocalypse.front.exception;

import com.apocalypse.common.exception.FrontException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.zalando.fauxpas.FauxPas.throwingSupplier;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, FrontAdviceTrait {

    @Override
    public ResponseEntity<Problem> create(final Throwable throwable, final Problem problem,
                                          final NativeWebRequest request, final HttpHeaders headers) {

        final HttpStatus status = HttpStatus.valueOf(Optional.ofNullable(problem.getStatus())
                .orElse(Status.INTERNAL_SERVER_ERROR)
                .getStatusCode());

        log(throwable, problem, request, status);

        String code = "1001";

        if (throwable instanceof FrontException) {
            code = ((FrontException) throwable).getCode();
        }

        Problem finalProblem;

        if (throwable instanceof ThrowableProblem) {
            finalProblem = Problem.builder()
                    .with("code", code)
                    .withType(problem.getType())
                    .withInstance(problem.getInstance())
                    .withStatus(problem.getStatus())
                    .withDetail(problem.getDetail())
                    .withTitle(problem.getTitle())
                    .withCause(((ThrowableProblem) throwable).getCause()).build();
        } else {
            finalProblem = Problem.builder()
                    .with("code", code)
                    .withType(problem.getType())
                    .withInstance(problem.getInstance())
                    .withStatus(problem.getStatus())
                    .withDetail(problem.getDetail())
                    .withTitle(problem.getTitle()).build();
        }

        return process(negotiate(request).map(contentType ->
                ResponseEntity
                        .status(status)
                        .headers(headers)
                        .contentType(contentType)
                        .body(finalProblem))
                .orElseGet(throwingSupplier(() -> {
                    final ResponseEntity<Problem> fallback = fallback(throwable, finalProblem, request, headers);

                    if (fallback.getBody() == null) {
                        final ServerHttpResponse response = new ServletServerHttpResponse(
                                request.getNativeResponse(HttpServletResponse.class));

                        response.setStatusCode(fallback.getStatusCode());
                        response.getHeaders().putAll(fallback.getHeaders());
                        response.getBody(); // just so we're actually flushing the body...
                        response.flush();
                    }

                    return fallback;
                })), request);
    }
}
