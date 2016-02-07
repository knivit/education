package com.tsoft.education;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();

        // create transactions
        IntStream.range(0, 100).forEach((val) -> {
            Transaction tr = new Transaction();
            tr.setCountry((val < 50) ? "US" : "RU");
            tr.setPrice(val);
            transactions.add(tr);
        });

        // group them by country
        Map<String, List<Transaction>> result =
                transactions.stream()
                .filter((tr) -> tr.getPrice() > 0)
                .collect(Collectors.groupingBy(Transaction::getCountry));

        // finding sum(price) by country
        for (String country : result.keySet()) {
            int sum = result.get(country).stream().mapToInt(Transaction::getPrice).sum();
            System.out.println(country + " = " + sum);
        }
    }
}
