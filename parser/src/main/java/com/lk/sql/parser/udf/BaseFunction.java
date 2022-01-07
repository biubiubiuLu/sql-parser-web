package com.lk.sql.parser.udf;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.type.SqlOperandTypeChecker;
import org.apache.calcite.sql.type.SqlOperandTypeInference;
import org.apache.calcite.sql.type.SqlReturnTypeInference;

import java.util.List;

/**
 * @author lukai
 * @creare 2021-12-22 18:12
 */
public class BaseFunction extends SqlFunction {


    public BaseFunction(SqlIdentifier sqlIdentifier, SqlReturnTypeInference returnTypeInference, SqlOperandTypeInference operandTypeInference, SqlOperandTypeChecker operandTypeChecker, List<RelDataType> paramTypes, SqlFunctionCategory funcType) {
        super(sqlIdentifier, returnTypeInference, operandTypeInference, operandTypeChecker, paramTypes, funcType);
    }

}
