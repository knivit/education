package com.tsoft.alm;

public class Deposit {
    public static void main(String[] args) {
        Deposit deposit = new Deposit();
        deposit.start();
    }

    private void start() {
        final double DEPOSIT = 100000.0;
        final double YEAR_PERCENT = 11.0;
        final double MONTH_PERCENT = 10.0;

        double year_month_pcnt = Math.pow(100.0 + YEAR_PERCENT, 1/12.0) - 1;
        System.out.println("Deposit " + DEPOSIT + ", " + YEAR_PERCENT + " % per year");
        System.out.println("or " + year_month_pcnt + " % per month");
        double year_val = DEPOSIT;
        for (int n = 1; n < 5; n ++) {
            year_val = year_val + year_val * YEAR_PERCENT / 100.0;
            System.out.println("At the end of " + n + " year: " + year_val);
        }

        System.out.println("Deposit " + DEPOSIT + ", " + MONTH_PERCENT / 12 + " % per month");
        double month_val = DEPOSIT;
        for (int n = 0; n < 13; n ++) {
            month_val = month_val + month_val * MONTH_PERCENT / 12.0 / 100.0;
            System.out.println("At the end of " + n + " month: " + month_val);
        }

        System.out.println("\nMarket value of shares");
        double val = 0;
        int NUM_YEARS = 3;
        for (int i = 1; i <= NUM_YEARS; i ++) {
            val = val + 10.8 / Math.pow(1.05, i);
            System.out.println("MV after dividends, at the end of " + i + " year: " + val);
        }
        double roe = 100.0 / Math.pow(1.05, NUM_YEARS);
        System.out.println("Plus return on equity at "  + NUM_YEARS + " year = " + roe);
        System.out.println("Result: MV (Market value of shares) = " + (roe + val));
    }
}
