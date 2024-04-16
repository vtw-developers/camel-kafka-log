package com.example.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("OrderService")
public class OrderService implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        if (System.currentTimeMillis() % 10 < 2) {
            exchange.getIn().setBody("Error");
            exchange.setProperty("result", "Error");
            exchange.setProperty("errorType", "NullPointerException");
            exchange.setProperty("errorMessage", "[품절 상품] 품절된 상품입니다.");
            throw new RuntimeException("SoldOut Error");
        }
        exchange.getIn().setBody("Order");
        exchange.setProperty("result", "Success");
    }
}
