package com.xs.micro.check.invoicing.extension;

import com.xs.micro.check.invoicing.domain.pojo.vo.GlobalResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller全局处理handle类
 *
 * @author typ
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截统一的异常，返回code=500和异常信息
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public GlobalResponseEntity jsonErrorHandler(HttpServletRequest request, Throwable e) {
        String requestURI = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        LOG.error("GlobalExceptionHandler, requestURI={}, parameterMap={}", requestURI, parameterMap, e);
        return GlobalResponseEntity.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
