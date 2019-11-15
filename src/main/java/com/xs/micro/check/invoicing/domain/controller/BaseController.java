package com.xs.micro.check.invoicing.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * BaseController
 *
 * @author chenjunlong
 * @Date 2015-8-11
 */
public class BaseController {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * request
     */
    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    /**
     * response
     */
    private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request.set(request);
        this.response.set(response);
    }

    protected final HttpServletRequest getRequest() {
        return request.get();
    }

    protected final HttpServletResponse getResponse() {
        return response.get();
    }

}