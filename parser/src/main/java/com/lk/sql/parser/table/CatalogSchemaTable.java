package com.lk.sql.parser.table;

import com.lk.sql.parser.model.TestModel;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.TemporalTable;
import org.apache.calcite.schema.impl.AbstractTable;

import javax.annotation.Nonnull;

public class CatalogSchemaTable extends AbstractTable implements TemporalTable {
    @Nonnull
    @Override
    public String getSysStartFieldName() {
        return null;
    }

    @Nonnull
    @Override
    public String getSysEndFieldName() {
        return null;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return typeFactory.createJavaType(TestModel.class);
    }
}
