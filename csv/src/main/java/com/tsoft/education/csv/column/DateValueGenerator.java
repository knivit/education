package com.tsoft.education.csv.column;

import java.text.Format;
import java.util.Date;
import java.util.Random;

public class DateValueGenerator extends Generator {
    private Random random = new Random();
    private Format formatter;
    private String dateFrom;
    private String dateTo;

    private Date from;
    private Date to;

    @Override
    public Object next() {
        if (from == null) {
            try {
                from = (Date)formatter.parseObject(dateFrom);
                to = (Date)formatter.parseObject(dateTo);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            if (to.getTime() < from.getTime()) {
                throw new IllegalArgumentException(DateValueGenerator.class.getSimpleName() + " parameter \"to\" (" + dateTo + ") is before \"from\" (" + dateFrom + ")");
            }
        }

        long max = to.getTime() - from.getTime();
        long rnd = Math.round(random.nextDouble() * max);
        return new Date(from.getTime() + rnd);
    }

    public void setFormatter(Format formatter) {
        this.formatter = formatter;
    }

    public void setFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
