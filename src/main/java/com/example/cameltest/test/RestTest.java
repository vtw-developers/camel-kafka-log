package com.example.cameltest.test;

import co.elastic.clients.elasticsearch.core.IndexRequest;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.platform.http.springboot.PlatformHttpMessage;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

//@Component
public class RestTest extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/")
                .get("hello")
                .to(direct("a").getUri());


        from(direct("a")).log("bbbbbbbbbbbbbbbbbbb")
//                .transform().constant(Map.of("id", "11", "name", "br"))
                .process(exchange -> {
                    String httpMethod = exchange.getIn().getHeader("CamelHttpMethod", String.class);
                    String requestUri = exchange.getIn().getHeader("CamelHttpUri", String.class);
                    String userAgent = exchange.getIn().getHeader("user-agent", String.class);

                    Map<String, Object> requestLog = new LinkedHashMap<>();
                    requestLog.put("method", httpMethod);
                    requestLog.put("path", requestUri);
                    requestLog.put("user_agent", userAgent);
                    requestLog.put("@timestamp", Instant.now().toString());


                    exchange.getIn().setBody(requestLog);
                })
            .to(kafka("TestTopic"))
//                .onCompletion().marshal().json()
            .log("전송완료2. ${body}, Type: ${body.getClass}");


        from(kafka("TestTopic"))
                .process(exchange -> {
                    exchange.getIn().getBody();
                    System.out.println(exchange.getIn().getBody());
                })
                .log("Received message from Kafka: body => ${body}, Type => ${body.getClass}")
                .to(elasticsearch("//elastic?operation=Index&indexName=myindex5"))
                .log("body: ${body}, Type: ${body.getClass}");
    }

}
