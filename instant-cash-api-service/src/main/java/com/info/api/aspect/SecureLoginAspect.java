package com.info.api.aspect;

import com.info.api.constants.Constants;
import com.info.api.dto.ic.APIResponse;
import com.info.api.service.common.CommonService;
import com.info.api.util.ParseUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.info.api.util.ObjectConverter.convertObjectToString;
import static com.info.api.util.ParseUtil.isNullOrEmpty;

@Aspect
@Component
@RequiredArgsConstructor
public class SecureLoginAspect {
    private final Logger logger = LoggerFactory.getLogger(SecureLoginAspect.class);

    private final HttpServletRequest request;
    private final CommonService commonService;

    @Pointcut("execution(* com.info.api.controller.*.*(..))")
    public void validateUserCredentialsPointcut() {
    }

    @Around("validateUserCredentialsPointcut()")
    public ResponseEntity<APIResponse<?>> validateLoginCredentials(ProceedingJoinPoint joinPoint) throws Throwable {
        String userId = request.getHeader("userId"), password = request.getHeader("password");
        if (isNullOrEmpty(userId, password) || !commonService.isAuthorizedRequest(userId, password)) {
            logger.error(Constants.UNAUTHORIZED_ACCESS + " ==> userId: {}, password: {}", userId, password);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ParseUtil.unAuthorized());
        }
        return (ResponseEntity<APIResponse<?>>) joinPoint.proceed();
    }


}
