package com.tsoft.education.csv.column;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class QueryValueGenerator extends Generator {
    private DataSource dataSource;
    private String query;

    private SqlRowSet rowSet;
    private int rowCount;

    public QueryValueGenerator(DataSource dataSource, String query) {
        this.dataSource = dataSource;
        this.query = query;
    }

    @Override
    public Object next() {
        retrieveData();

        if (rowCount == 0) {
            throw new IllegalArgumentException("Generator's " + getClass().getName() + " query \n" + query + "\n doesn't have any records");
        }

        // index can't be 0
        if (isRandomized()) {
            int index = getRandom(rowCount - 1) + 1;
            rowSet.first();
            rowSet.absolute(index);
        } else {
            if (!rowSet.next()) {
                rowSet.first();
            }
        }

        ComplexValue value = new ComplexValue(rowSet);
        return value;
    }

    private void retrieveData() {
        if (rowSet == null) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            rowSet = jdbcTemplate.queryForRowSet(query);

            // get the row count
            rowCount = 0;
            while (rowSet.next()) {
                rowCount ++;
            }
            rowSet.first();
        }
    }
}
