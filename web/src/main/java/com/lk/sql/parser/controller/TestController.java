package com.lk.sql.parser.controller;

import com.lk.sql.parser.impl.MySqlParserImpl;
import com.lk.sql.parser.operator.FunctionCatalogOperatorTable;
import com.lk.sql.parser.operator.MyCatalogSqlOperatorTable;
import com.lk.sql.parser.operator.MySqlOperatorTable;
import com.lk.sql.parser.schema.CatalogCalciteSchema;
import com.lk.sql.parser.schema.CatalogManagerSchema;
import com.lk.sql.parser.udf.BaseFunction;
import com.lk.sql.parser.udf.TestFunction;
import com.lk.sql.parser.validator.MySqlValidatorImpl;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.Lex;
import org.apache.calcite.config.NullCollation;
import org.apache.calcite.jdbc.*;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.ReflectiveSqlOperatorTable;
import org.apache.calcite.sql.util.SqlOperatorTables;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author lukai
 * @creare 2021-12-10 15:54
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    public static class HrSchema {
        public final Table1[] emps =  new Table1[]{Table1.of("lk","123"),Table1.of("zm","456")};;
        public final String[] depts = new String[5];
    }

    public static class  Table1{

        public String nameTest;

        public String valueTest;

        public Table1(){

        }

        public Table1(String name,String value){
            this.nameTest = name;
            this.valueTest = value;
        }

        public static Table1 of(String name,String value){
            return new Table1(name,value);
        }

    }

    @PostMapping("sql")
    public String queryTrack(@RequestBody Map<String, String> paramMap) {
        String sql = paramMap.get("sql");
        try {
            SqlParser.Config config = SqlParser.configBuilder().setParserFactory(MySqlParserImpl.FACTORY).setLex(Lex.MYSQL).build();
            SqlParser sqlParser = SqlParser.create(sql, config);
            SqlNodeList sqlNodeList = sqlParser.parseStmtList();
            List<SqlNode> sqlNodes = sqlNodeList.getList();
            for(SqlNode sqlNode : sqlNodes){
                doValidate(sqlNode);
            }
            Class.forName("org.apache.calcite.jdbc.Driver");
            Properties info = new Properties();
            info.setProperty("lex","JAVA");
            Connection connection = DriverManager.getConnection("jdbc:calcite:",info);
            CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
            SchemaPlus rootSchema = calciteConnection.getRootSchema();
            Schema javaSchema = new ReflectiveSchema(new HrSchema());
            rootSchema.add("hr",javaSchema);
            Statement statement = calciteConnection.createStatement();
            ResultSet resultSet =  statement.executeQuery("select * from hr.emps");
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }

        } catch (Exception e) {
            logger.error("sql error ", e);
        }
        return sql;
    }

    private SqlNode doValidate(SqlNode sqlNode) throws Exception {
        if (sqlNode.getKind().belongsTo(SqlKind.DDL) || sqlNode.getKind() == SqlKind.INSERT
                || sqlNode.getKind() == SqlKind.CREATE_FUNCTION
                || sqlNode.getKind() == SqlKind.DROP_FUNCTION
                || sqlNode.getKind() == SqlKind.OTHER_DDL) {
            return sqlNode;
        }

        JavaTypeFactory typeFactory = new JavaTypeFactoryImpl();
        SqlOperatorTable operatorTable = SqlOperatorTables.chain(new MyCatalogSqlOperatorTable(),new FunctionCatalogOperatorTable(typeFactory),MySqlOperatorTable.instance(),SqlStdOperatorTable.instance());
        SqlValidator.Config config = SqlValidator.Config.DEFAULT
                .withIdentifierExpansion(true)
                .withDefaultNullCollation(NullCollation.LOW)
                .withTypeCoercionEnabled(false);
        JavaTypeFactoryImpl javaTypeFactory = new JavaTypeFactoryImpl();

        CalciteCatalogReader calciteCatalogReader = new CalciteCatalogReader(CalciteSchemaBuilder.asRootSchema(new CatalogManagerSchema()), new ArrayList<>(), javaTypeFactory, CalciteConnectionConfig.DEFAULT);
        SqlValidatorImpl mySqlValidatorImpl = new MySqlValidatorImpl(operatorTable, calciteCatalogReader, typeFactory, config);
        SqlNode newSqlNode = mySqlValidatorImpl.validate(sqlNode);
        return newSqlNode;
    }
}
