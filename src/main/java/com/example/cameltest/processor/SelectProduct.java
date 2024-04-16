package com.example.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("SelectProduct")
public class SelectProduct implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        String[] category = {"Electronics", "clothes", "Food", "Sports"};

        // 각 카테고리에 대한 확률
        double[] probabilities = {0.5, 0.3, 0.1, 0.1};

        // 랜덤 값을 생성할 때 사용할 임계값 설정
        double threshold = Math.random();

        String selectedCategory = "";

        // 누적 확률을 계산하기 위한 변수
        double cumulativeProbability = 0.0;

        // 각 카테고리의 확률을 고려하여 랜덤 카테고리를 선택
        for (int i = 0; i < category.length; i++) {
            cumulativeProbability += probabilities[i];
            if (threshold <= cumulativeProbability) {
                selectedCategory = category[i];
                break;
            }
        }

        exchange.setProperty("category", selectedCategory);

        // 에러 여부에 따라 다른 메시지 설정
        if (System.currentTimeMillis() % 10 < 2) {
            exchange.getIn().setBody("Error");
            exchange.setProperty("result", "Error");
            exchange.setProperty("errorType", "NullPointerException");
            exchange.setProperty("errorMessage", "[선택 없음 오류] 상품이 선택되지 않았습니다.");
            throw new RuntimeException("NotExistChoice Error");
        } else {
            exchange.getIn().setBody("Product List");
            exchange.setProperty("result", "Success");
        }
    }
}
