package com.lk.sql.parser.schema;

import com.lk.sql.parser.table.CatalogSchemaTable;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.rel.type.RelProtoDataType;
import org.apache.calcite.schema.*;

import java.util.Collection;
import java.util.Set;

public class CatalogManagerSchema implements Schema {
    @Override
    public Table getTable(String name) {
        return new CatalogSchemaTable();
    }

    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public RelProtoDataType getType(String name) {
        return null;
    }

    @Override
    public Set<String> getTypeNames() {
        return null;
    }

    @Override
    public Collection<Function> getFunctions(String name) {
        return null;
    }

    @Override
    public Set<String> getFunctionNames() {
        return null;
    }

    @Override
    public Schema getSubSchema(String name) {
        return null;
    }

    @Override
    public Set<String> getSubSchemaNames() {
        return null;
    }

    @Override
    public Expression getExpression(SchemaPlus parentSchema, String name) {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Schema snapshot(SchemaVersion version) {
        return null;
    }
}
