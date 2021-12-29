package com.lk.sql.parser.controller;

import com.lk.sql.parser.impl.MySqlParserImpl;
import com.lk.sql.parser.operator.MySqlOperatorTable;
import com.lk.sql.parser.validator.MySqlValidatorImpl;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.Lex;
import org.apache.calcite.config.NullCollation;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParser;
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
                    if(sqlNode.getKind() == SqlKind.SELECT){
                        SqlSelect sqlSelect = (SqlSelect) sqlNode;
                        SqlNode sqlWhere = sqlSelect.getWhere();
                         sqlSelect.getSelectList();
                        if(sqlWhere.getKind() == SqlKind.EQUALS){
                            SqlBasicCall equalsBasicCall = (SqlBasicCall) sqlWhere;
                            SqlNode[] operandNodes =  equalsBasicCall.getOperands();
                            for(SqlNode operandNode : operandNodes){
                                if(operandNode.getKind() == SqlKind.OTHER_FUNCTION){
                                    SqlBasicCall functionCall = (SqlBasicCall) operandNode;
                                    functionCall = functionCall;
                                }
                            }
                        }
                    }
                    System.out.println(sqlNode);
                }
            }
        } catch (Exception e) {
            logger.error("sql error ", e);
        }
        return sql;
    }
    private void do1(SqlNode sqlNode) throws Exception{
        RelDataTypeFactory typeFactory = new JavaTypeFactoryImpl();
        SqlOperatorTable operatorTable = MySqlOperatorTable.instance();
        SqlValidator.Config config = SqlValidator.Config.DEFAULT
                .withIdentifierExpansion(true)
                .withDefaultNullCollation(NullCollation.LOW)
                .withTypeCoercionEnabled(false);
        CalciteSchema calciteSchema = CalciteSchema.createRootSchema(false);
        JavaTypeFactoryImpl javaTypeFactory = new JavaTypeFactoryImpl();
        CalciteCatalogReader calciteCatalogReader = new CalciteCatalogReader(calciteSchema, new ArrayList<>(), javaTypeFactory, CalciteConnectionConfig.DEFAULT);
        SqlValidatorImpl mySqlValidatorImpl = new MySqlValidatorImpl(operatorTable, calciteCatalogReader, typeFactory, config);
        SqlNode newSqlNode =  mySqlValidatorImpl.validate(sqlNode);
        System.out.println(newSqlNode);
    }
}
