package com.lk.sql.parser.functions.inference;

import com.lk.sql.parser.type.inference.ConstantArgumentCount;
import org.apache.calcite.sql.SqlCallBinding;
import org.apache.calcite.sql.SqlOperandCountRange;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.type.SqlOperandTypeChecker;

/**
 * @author lukai
 * @creare 2021-12-31 15:42
 */
public class TypeInferenceOperandChecker implements SqlOperandTypeChecker {
    @Override
    public boolean checkOperandTypes(SqlCallBinding callBinding, boolean throwOnFailure) {
        return false;
    }

    @Override
    public SqlOperandCountRange getOperandCountRange() {
        return new ArgumentCountRange(ConstantArgumentCount.of(1));
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
}
