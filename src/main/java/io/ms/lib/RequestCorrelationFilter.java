package io.ms.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class RequestCorrelationFilter implements AsyncHandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCorrelationFilter.class);

    public static final String DEFAULT_CORRELATION_ID_HEADER_NAME = "requestCorrelationId";
    public static final String DEFAULT_LOGGER_MDC_NAME = "requestId";

    private String correlationHeaderName;
    private String loggerMdcName;

    @PostConstruct
    public void init() throws Exception {
        correlationHeaderName = DEFAULT_CORRELATION_ID_HEADER_NAME;
        loggerMdcName = DEFAULT_LOGGER_MDC_NAME;
    }


    protected String getCorrelationHeaderName() {
        return correlationHeaderName;
    }

    protected String getLoggerMdcName() {
        return loggerMdcName;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader(correlationHeaderName);
        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        RequestCorrelationContext.getCurrent().setCorrelationId(correlationId);
        MDC.put(loggerMdcName, correlationId);
        LOGGER.debug("Correlation id={} request={}", correlationId, request.getPathInfo());
        response.setHeader("traceId", MDC.get(loggerMdcName));
        MDC.remove(loggerMdcName);
        RequestCorrelationContext.clearCurrent();
    }
}