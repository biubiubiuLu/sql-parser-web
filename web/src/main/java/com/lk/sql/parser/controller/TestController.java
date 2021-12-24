package com.lk.sql.parser.controller;

import com.lk.sql.parser.impl.MySqlParserImpl;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
                    System.out.println(sqlNode);
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
}
