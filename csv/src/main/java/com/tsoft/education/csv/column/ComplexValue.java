package com.tsoft.education.csv.column;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashMap;

public class ComplexValue {
    private HashMap<String, Object> rowValues;

    public ComplexValue(SqlRowSet rowSet) {
        this(rowSet, false);
    }

    public ComplexValue(SqlRowSet rowSet, boolean isNullValues) {
        rowValues = new HashMap<String, Object>(rowSet.getMetaData().getColumnCount());

        int index = 1;
        for (String columnName : rowSet.getMetaData().getColumnNames()) {
            rowValues.put(columnName.toLowerCase(), isNullValues ? null : rowSet.getObject(index ++));
        }
    }

    public Object getColumnValue(String columnName) {
        columnName = columnName.toLowerCase();
        if (rowValues.containsKey(columnName)) {
            return rowValues.get(columnName);
        }

        throw new IllegalArgumentException("Unknown column '" + columnName + "'. Possible values are " + rowValues.keySet().toString());
    }

    @Override
    public String toString() {
        return "ComplexValue{" +
                "rowValues=" + rowValues +
                '}';
    }
}
