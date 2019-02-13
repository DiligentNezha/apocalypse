package com.apocalypse.front.exception;

import com.apocalypse.common.exception.ProblemMarker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, ControllerAdviceTrait {

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return entity;
        }
        Problem problem = entity.getBody();

        String code = "1001";
        String msg = "system busy";

        ProblemBuilder builder = Problem.builder()
                .withType(problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with("path", request.getNativeRequest(HttpServletRequest.class).getRequestURI())
                .with("code", code)
                .with("msg", ((ThrowableProblem) problem).getMessage());

        if (problem instanceof ConstraintViolationProblem) {
            builder.with("violations", ((ConstraintViolationProblem) problem).getViolations())
                    .with("code", "10002");
        } else if (problem instanceof DefaultProblem){
            builder.withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
        } else if (problem instanceof ProblemMarker) {
            builder.with("code", ((ProblemMarker) problem).getCode());
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

}
