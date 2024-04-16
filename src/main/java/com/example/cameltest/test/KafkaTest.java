package com.example.cameltest.test;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class KafkaTest extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(timer("timer12").period(5000))
                .setHeader("name").simple("byeongryeol")
                .setBody(constant("{\"name\": \"John\", \"age\": 30}"))
                .to(kafka("TestTopic"))
                .log("전송완료. ${body}, Type: ${body.getClass}");

        from(kafka("TestTopic"))
                .to(elasticsearch("//elasticsearch?operation=INDEX&indexName=myindex"))
                .log("body: ${body}, headers: ${headers}");
    }
}
