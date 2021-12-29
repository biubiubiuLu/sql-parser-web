package com.lk.sql.parser.validator;

import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.sql.validate.SqlValidatorCatalogReader;
import org.apache.calcite.sql.validate.SqlValidatorImpl;

/**
 * @author lukai
 * @creare 2021-12-29 18:08
 */
public class MySqlValidatorImpl extends SqlValidatorImpl {
    /**
     * Creates a validator.
     *
     * @param opTab         Operator table
     * @param catalogReader Catalog reader
     * @param typeFactory   Type factory
     * @param config        Config
     */
    public MySqlValidatorImpl(SqlOperatorTable opTab, SqlValidatorCatalogReader catalogReader, RelDataTypeFactory typeFactory, Config config) {
        super(opTab, catalogReader, typeFactory, config);
    }
}
