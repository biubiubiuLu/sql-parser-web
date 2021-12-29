package org.apache.calcite.jdbc;

import org.apache.calcite.schema.Schema;

public class CalciteSchemaBuilder {

    public static CalciteSchema asRootSchema(Schema root) {
        return new SimpleCalciteSchema(null, root, "");
    }

    private CalciteSchemaBuilder() {}
}
