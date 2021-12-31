package com.lk.sql.parser.functions.inference;

import com.lk.sql.parser.type.inference.ArgumentCount;
import org.apache.calcite.sql.SqlOperandCountRange;

/**
 * @author lukai
 * @creare 2021-12-31 11:41
 */
public class ArgumentCountRange implements SqlOperandCountRange {

    private final ArgumentCount argumentCount;

    public ArgumentCountRange(ArgumentCount argumentCount) {
        this.argumentCount = argumentCount;
    }

    @Override
    public boolean isValidCount(int count) {
        return argumentCount.isValidCount(count);
    }

    @Override
    public int getMin() {
        return argumentCount.getMinCount().orElse(-1);
    }

    @Override
    public int getMax() {
        return argumentCount.getMaxCount().orElse(-1);
    }
}
