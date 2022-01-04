package com.lk.sql.parser.operator;

import com.lk.sql.parser.functions.inference.ArgumentCountRange;
import com.lk.sql.parser.functions.inference.TypeInferenceOperandChecker;
import com.lk.sql.parser.type.inference.ConstantArgumentCount;
import com.lk.sql.parser.udf.BaseFunction;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.type.*;
import org.apache.calcite.sql.validate.SqlNameMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FunctionCatalogOperatorTable implements SqlOperatorTable {


    private JavaTypeFactory typeFactory;

    public FunctionCatalogOperatorTable(JavaTypeFactory typeFactory) {
        this.typeFactory = typeFactory;

    }

    @Override
    public void lookupOperatorOverloads(SqlIdentifier opName, SqlFunctionCategory category, SqlSyntax syntax, List<SqlOperator> operatorList, SqlNameMatcher nameMatcher) {
        if(category != null && category.isFunction()) {
            SqlOperator sqlOperator = new BaseFunction(opName, ReturnTypes.ARG0_NULLABLE, null, OperandTypes.CHARACTER, new ArrayList<>(), SqlFunctionCategory.STRING);
            operatorList.add(sqlOperator);
        }
    }

    @Override
    public List<SqlOperator> getOperatorList() {
        return null;
    }
}
