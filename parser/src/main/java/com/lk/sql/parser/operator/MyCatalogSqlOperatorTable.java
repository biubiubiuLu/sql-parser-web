package com.lk.sql.parser.operator;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.validate.SqlNameMatcher;

import java.util.List;

public class MyCatalogSqlOperatorTable implements SqlOperatorTable {
    @Override
    public void lookupOperatorOverloads(SqlIdentifier opName, SqlFunctionCategory category, SqlSyntax syntax, List<SqlOperator> operatorList, SqlNameMatcher nameMatcher) {
        System.out.println(opName);
        System.out.println(operatorList);
    }

    @Override
    public List<SqlOperator> getOperatorList() {
        throw new UnsupportedOperationException("This should never be called");
    }
}
