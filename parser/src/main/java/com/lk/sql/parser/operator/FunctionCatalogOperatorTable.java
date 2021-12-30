package com.lk.sql.parser.operator;

import com.lk.sql.parser.udf.BaseFunction;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.type.SqlOperandTypeChecker;
import org.apache.calcite.sql.type.SqlOperandTypeInference;
import org.apache.calcite.sql.type.SqlReturnTypeInference;
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
        JavaTypeFactory thenTypeFactory = this.typeFactory;
        SqlReturnTypeInference sqlReturnTypeInference = new SqlReturnTypeInference() {
            @Override
            public RelDataType inferReturnType(SqlOperatorBinding opBinding) {
                return thenTypeFactory.createType(String.class.getGenericSuperclass());
            }
        };
        SqlOperandTypeInference sqlOperandTypeInference = new SqlOperandTypeInference() {
            @Override
            public void inferOperandTypes(SqlCallBinding callBinding, RelDataType returnType, RelDataType[] operandTypes) {
                System.out.println(operandTypes);
            }
        };
        SqlOperandTypeChecker sqlOperandTypeChecker = new SqlOperandTypeChecker() {
            @Override
            public boolean checkOperandTypes(SqlCallBinding callBinding, boolean throwOnFailure) {
                return true;
            }

            @Override
            public SqlOperandCountRange getOperandCountRange() {
                return null;
            }

            @Override
            public String getAllowedSignatures(SqlOperator op, String opName) {
                return null;
            }

            @Override
            public Consistency getConsistency() {
                return null;
            }

            @Override
            public boolean isOptional(int i) {
                return false;
            }
        };
        SqlOperator sqlOperator = new BaseFunction(opName, sqlReturnTypeInference, sqlOperandTypeInference, sqlOperandTypeChecker, new ArrayList<>(), SqlFunctionCategory.USER_DEFINED_FUNCTION);
        operatorList.add(sqlOperator);
    }

    @Override
    public List<SqlOperator> getOperatorList() {
        return null;
    }
}
