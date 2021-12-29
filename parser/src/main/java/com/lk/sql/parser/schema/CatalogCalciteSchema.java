package com.lk.sql.parser.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaVersion;
import org.apache.calcite.schema.Table;
import org.apache.calcite.util.NameMap;
import org.apache.calcite.util.NameMultimap;
import org.apache.calcite.util.NameSet;

import java.util.List;

public class CatalogCalciteSchema extends CalciteSchema {
    protected CatalogCalciteSchema(CalciteSchema parent, Schema schema, String name, NameMap<CalciteSchema> subSchemaMap, NameMap<TableEntry> tableMap, NameMap<LatticeEntry> latticeMap, NameMap<TypeEntry> typeMap, NameMultimap<FunctionEntry> functionMap, NameSet functionNames, NameMap<FunctionEntry> nullaryFunctionMap, List<? extends List<String>> path) {
        super(parent, schema, name, subSchemaMap, tableMap, latticeMap, typeMap, functionMap, functionNames, nullaryFunctionMap, path);
    }

    @Override
    protected CalciteSchema getImplicitSubSchema(String schemaName, boolean caseSensitive) {
        return null;
    }

    @Override
    protected TableEntry getImplicitTable(String tableName, boolean caseSensitive) {
        return null;
    }

    @Override
    protected TypeEntry getImplicitType(String name, boolean caseSensitive) {
        return null;
    }

    @Override
    protected TableEntry getImplicitTableBasedOnNullaryFunction(String tableName, boolean caseSensitive) {
        return null;
    }

    @Override
    protected void addImplicitSubSchemaToBuilder(ImmutableSortedMap.Builder<String, CalciteSchema> builder) {

    }

    @Override
    protected void addImplicitTableToBuilder(ImmutableSortedSet.Builder<String> builder) {

    }

    @Override
    protected void addImplicitFunctionsToBuilder(ImmutableList.Builder<Function> builder, String name, boolean caseSensitive) {

    }

    @Override
    protected void addImplicitFuncNamesToBuilder(ImmutableSortedSet.Builder<String> builder) {

    }

    @Override
    protected void addImplicitTypeNamesToBuilder(ImmutableSortedSet.Builder<String> builder) {

    }

    @Override
    protected void addImplicitTablesBasedOnNullaryFunctionsToBuilder(ImmutableSortedMap.Builder<String, Table> builder) {

    }

    @Override
    protected CalciteSchema snapshot(CalciteSchema parent, SchemaVersion version) {
        return null;
    }

    @Override
    protected boolean isCacheEnabled() {
        return false;
    }

    @Override
    public void setCache(boolean cache) {

    }

    @Override
    public CalciteSchema add(String name, Schema schema) {
        return null;
    }
}
