package com.example.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("RequestLogProcessor")
public class RequestLogProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String httpMethod = exchange.getIn().getHeader("CamelHttpMethod", String.class);
        String requestUri = exchange.getIn().getHeader("CamelHttpUri", String.class);
        String userAgent = exchange.getIn().getHeader("user-agent", String.class);
        String result = (String) exchange.getProperty("result");
        String category = (String) exchange.getProperty("category");
        String errorType = (String) exchange.getProperty("errorType");
        String errorMessage = (String) exchange.getProperty("errorMessage");

        Map<String, Object> requestLog = new LinkedHashMap<>();
        requestLog.put("method", httpMethod);
        requestLog.put("path", requestUri);
        requestLog.put("user_agent", userAgent);
        requestLog.put("result", result);
        requestLog.put("category", category);
        requestLog.put("@timestamp", Instant.now().toString());

        // Error 일 경우 Error Type 과 Message 적재
        if (result.equals("Error")) {
            requestLog.put("errorType", errorType);
            requestLog.put("errorMessage", errorMessage);
        }

        exchange.getIn().setBody(requestLog);
    }
}
