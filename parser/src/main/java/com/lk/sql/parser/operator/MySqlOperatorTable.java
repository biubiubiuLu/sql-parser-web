package com.lk.sql.parser.operator;

import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.type.*;
import org.apache.calcite.sql.util.ReflectiveSqlOperatorTable;


/**
 * @author lukai
 * @creare 2021-12-29 15:30
 */
public class MySqlOperatorTable extends ReflectiveSqlOperatorTable {

    private static MySqlOperatorTable instance;

    public static synchronized MySqlOperatorTable instance() {
        if (instance == null) {
            instance = new MySqlOperatorTable();
            instance.init();
        }
        return instance;
    }

    private static final SqlReturnTypeInference PROCTIME_TYPE_INFERENCE =
            createTimeIndicatorReturnType(false, true);

    private static SqlReturnTypeInference createTimeIndicatorReturnType(
            boolean isRowTime, boolean isTimestampLtz) {
        return ReturnTypes.explicit(
                factory -> {
                    return factory.createJavaType(Object.class);
                });
    }

    public static final SqlFunction MY_UDF =
            new SqlFunction(
                    "MY_CONCAT",
                    SqlKind.OTHER_FUNCTION,
                    ReturnTypes.cascade(
                            ReturnTypes.explicit(SqlTypeName.VARCHAR),
                            SqlTypeTransforms.TO_NULLABLE),
                    null,
                    OperandTypes.repeat(SqlOperandCountRanges.from(1), OperandTypes.STRING),
                    SqlFunctionCategory.STRING);
}
