package com.apocalypse.front.exception;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.exception.FrontException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.fauxpas.FauxPas;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {

    @Override
    public ResponseEntity<Problem> create(final Throwable throwable, final Problem problem, final NativeWebRequest request, final HttpHeaders headers) {
        HttpStatus status = HttpStatus.valueOf(((StatusType) Optional.ofNullable(problem.getStatus()).orElse(org.zalando.problem.Status.INTERNAL_SERVER_ERROR)).getStatusCode());
        this.log(throwable, problem, request, status);

        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            request.setAttribute("javax.servlet.error.exception", throwable, 0);
        }
        String msg = "访问出错，请查看problem了解详细错误信息";
        if (throwable instanceof FrontException) {
            msg = throwable.getLocalizedMessage();
        }

        Problem response1 =
                Problem.builder().with("code", ((FrontException) throwable).getCode()).with("msg", msg).with("problem",
                        problem).build();
        return this.process((ResponseEntity)this.negotiate(request).map((contentType) -> {
            return ((ResponseEntity.BodyBuilder)ResponseEntity.status(status).headers(headers)).contentType(contentType).body(response1);
        }).orElseGet(FauxPas.throwingSupplier(() -> {
            ResponseEntity<Problem> fallback = this.fallback(throwable, problem, request, headers);
            if (fallback.getBody() == null) {
                ServerHttpResponse response = new ServletServerHttpResponse((HttpServletResponse)request.getNativeResponse(HttpServletResponse.class));
                response.setStatusCode(fallback.getStatusCode());
                response.getHeaders().putAll(fallback.getHeaders());
                response.getBody();
                response.flush();
            }

            return fallback;
        })), request);
    }

    public String test(String name) throws FrontException{
        try {
            if (StrUtil.isEmpty(name)) {
                throw new FrontException("具体业务code", "业务失败具体原因");
            }
            return name;
        } catch (Exception e) {
            //记录日志
            throw new FrontException();
        }
    }
}
