package com.lk.sql.parser.controller;

import com.lk.sql.parser.impl.MySqlParserImpl;
import com.lk.sql.parser.operator.FunctionCatalogOperatorTable;
import com.lk.sql.parser.operator.MyCatalogSqlOperatorTable;
import com.lk.sql.parser.operator.MySqlOperatorTable;
import com.lk.sql.parser.schema.CatalogCalciteSchema;
import com.lk.sql.parser.schema.CatalogManagerSchema;
import com.lk.sql.parser.validator.MySqlValidatorImpl;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.Lex;
import org.apache.calcite.config.NullCollation;
import org.apache.calcite.jdbc.CalciteRootSchema;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.CalciteSchemaBuilder;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlOperatorTables;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lukai
 * @creare 2021-12-10 15:54
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("sql")
    public String queryTrack(@RequestBody Map<String, String> paramMap) {
        String sql = paramMap.get("sql");
        String[] sqls = sql.split(";");
        try {
            SqlParser sqlParser = null;
            for (String sqlItem : sqls) {
                if (sqlParser == null) {
                    SqlParser.Config config = SqlParser.configBuilder().setParserFactory(MySqlParserImpl.FACTORY).setLex(Lex.MYSQL).build();
                    sqlParser = SqlParser.create(sqlItem, config);
                    SqlNode sqlNode = sqlParser.parseStmt();
                    do1(sqlNode);
                } else {
                    SqlNode sqlNode = sqlParser.parseQuery(sqlItem);
                    do1(sqlNode);

//                    if (sqlNode.getKind() == SqlKind.SELECT) {
//                        SqlSelect sqlSelect = (SqlSelect) sqlNode;
//                        SqlNode sqlWhere = sqlSelect.getWhere();
//                        sqlSelect.getSelectList();
//                        if (sqlWhere.getKind() == SqlKind.EQUALS) {
//                            SqlBasicCall equalsBasicCall = (SqlBasicCall) sqlWhere;
//                            SqlNode[] operandNodes = equalsBasicCall.getOperands();
//                            for (SqlNode operandNode : operandNodes) {
//                                if (operandNode.getKind() == SqlKind.OTHER_FUNCTION) {
//                                    SqlBasicCall functionCall = (SqlBasicCall) operandNode;
//                                    functionCall = functionCall;
//                                }
//                            }
//                        }
//                    }
//                    System.out.println(sqlNode);
                }
            }
        } catch (Exception e) {
            logger.error("sql error ", e);
        }
        return sql;
    }

    private void do1(SqlNode sqlNode) throws Exception {
        if (sqlNode.getKind().belongsTo(SqlKind.DDL) || sqlNode.getKind() == SqlKind.INSERT
                || sqlNode.getKind() == SqlKind.CREATE_FUNCTION
                || sqlNode.getKind() == SqlKind.DROP_FUNCTION
                || sqlNode.getKind() == SqlKind.OTHER_DDL) {
            return;

        }

        JavaTypeFactory typeFactory = new JavaTypeFactoryImpl();
        SqlOperatorTable operatorTable = SqlOperatorTables.chain(new MyCatalogSqlOperatorTable(),new FunctionCatalogOperatorTable(typeFactory), MySqlOperatorTable.instance());
        SqlValidator.Config config = SqlValidator.Config.DEFAULT
                .withIdentifierExpansion(true)
                .withDefaultNullCollation(NullCollation.LOW)
                .withTypeCoercionEnabled(false);
        JavaTypeFactoryImpl javaTypeFactory = new JavaTypeFactoryImpl();

        CalciteCatalogReader calciteCatalogReader = new CalciteCatalogReader(CalciteSchemaBuilder.asRootSchema(new CatalogManagerSchema()), new ArrayList<>(), javaTypeFactory, CalciteConnectionConfig.DEFAULT);
        SqlValidatorImpl mySqlValidatorImpl = new MySqlValidatorImpl(operatorTable, calciteCatalogReader, typeFactory, config);
        SqlNode newSqlNode = mySqlValidatorImpl.validate(sqlNode);
        System.out.println(newSqlNode);
    }
}
