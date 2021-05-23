package io.ms.lib;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class RequestCorrelationFilter implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCorrelationFilter.class);

    private static final String DEFAULT_CORRELATION_ID_HEADER_NAME = "requestId";
    private static final String DEFAULT_LOGGER_MDC_NAME = "requestId";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader(DEFAULT_CORRELATION_ID_HEADER_NAME);
        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        RequestCorrelationContext.getCurrent().setCorrelationId(correlationId);
        response.setHeader(DEFAULT_CORRELATION_ID_HEADER_NAME, correlationId);
        MDC.put(DEFAULT_LOGGER_MDC_NAME, correlationId);
        LOGGER.error("Correlation id={} set for request={}", correlationId, request.getPathInfo());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestCorrelationContext.clearCurrent();
    }
}