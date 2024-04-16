package com.example.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("SelectCart")
public class SelectCart implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        if (System.currentTimeMillis() % 10 < 2) {
            exchange.getIn().setBody("Error");
            exchange.setProperty("result", "Error");
            exchange.setProperty("errorType", "NullPointerException");
            exchange.setProperty("errorMessage", "[선택 없음 오류] 장바구니가 비어있습니다.");
            throw new RuntimeException("NotExistChoice Error");
        }
        exchange.getIn().setBody("Cart List");
        exchange.setProperty("result", "Success");
    }
}
