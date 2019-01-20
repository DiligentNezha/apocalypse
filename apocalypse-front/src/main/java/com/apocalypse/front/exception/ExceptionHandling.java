package com.apocalypse.front.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {

}
