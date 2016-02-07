package com.tsoft.education;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupBy {
    /**
     * Given transactions
     * Country | Price
     * ---------------
     *      US |    1
     *      US |    2
     *      ...
     *      US |   49
     *      RU |   50
     *      ...
     *      RU |   99
     *
     * do group them by country and find the sum, i.e.
     *      RU: 3725
     *      US: 1225
     */
    public static void main(String[] args) {
        GroupBy groupBy = new GroupBy();
        groupBy.start();
    }

    class Transaction {
        private String country;
        private int price;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    private void start() {
        List<Transaction> transactions = new ArrayList<>();

        // create transactions
        IntStream.range(1, 100).forEach((val) -> {
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
