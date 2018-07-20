package com.lixy.boothigh.utils;

import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.enums.DBTypeEnum;
import com.lixy.boothigh.enums.DbDataTypeEnum;
import com.lixy.boothigh.enums.DriverNameEnum;
import com.lixy.boothigh.enums.SourceDataTypeEnum;
import com.lixy.boothigh.vo.SourceDataInfoShowVO;
import com.lixy.boothigh.vo.SourceDataInfoVO;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: MR LIS
 * @Description:JDBC连接工具 mysql与tidb原理差不多，驱动一样
 * @Date: Create in 17:41 2018/5/24
 * @Modified By:
 */
public class GenDBUtils {
    /**
     * MYSQL前缀
     */
    private static String MYSQL_PREFIX = "jdbc:mysql://";
    /**
     * MYSQL后缀
     */
    private static String MYSQL_SUFFIX = "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    /**
     * ORACLE前缀
     */
    private static String ORACLE_PREFIX = "jdbc:oracle:thin:@//";
    /**
     * postgres前缀
     */
    private static String POSTGRES_PREFIX = "jdbc:postgresql://";

    /**
     * mysql查询列信息语句前缀
     */
    private static String COLUMN_MYSQL_PREFIX = "show full columns from ";
    /**
     * oracle 查询列信息语句前缀
     */
    private static String COLUMN_ORACLE_PREFIX = "select a.COLUMN_NAME,a.DATA_TYPE,b.COMMENTS from user_tab_columns a LEFT JOIN user_col_comments b ON a.table_name=b.table_name AND a.COLUMN_NAME=b.COLUMN_NAME where a.table_name=";
    /**
     * postgres 查询列信息语句前缀,同一个库的不同模式下不要有重复的数据库名
     */
    private static String COLUMN_POSTGRES_PREFIX = "SELECT\n" +
            "\tC.relname,\n" +
            "\tcol_description (A.attrelid, A.attnum) AS description,\n" +
            "\tformat_type (A.atttypid, A.atttypmod) AS data_type,\n" +
            "\tA.attname AS column_name\n" +
            "FROM\n" +
            "\tpg_class AS C,\n" +
            "\tpg_attribute AS A\n" +
            "WHERE A.attrelid = C.oid\n" +
            "AND A.attnum > 0\n" +
            "AND C.relname = ";

    /**
     * mysql字段属性、注释、数据类型
     */
    private static String MYSQL_COLUMN_NAME = "Field";
    private static String MYSQL_COLUMN_COMMENT = "Comment";
    private static String MYSQL_COLUMN_TYPE = "Type";
    /**
     * oracle字段属性、注释、数据类型
     */
    private static String ORACLE_COLUMN_NAME = "COLUMN_NAME";
    private static String ORACLE_COLUMN_COMMENT = "COMMENTS";
    private static String ORACLE_COLUMN_TYPE = "DATA_TYPE";
    /**
     * postgres字段属性、注释、数据类型
     */
    private static String POSTGRES_COLUMN_NAME = "column_name";
    private static String POSTGRES_COLUMN_COMMENT = "description";
    private static String POSTGRES_COLUMN_TYPE = "data_type";


    /**
     * 分页查询时的总记录sql和分页查询sql
     */
    public static String PAGE_COUNT_SQL = "countSql";
    public static String PAGE_QUERY_SQL = "querySql";

    /**
     * mysql库对应库对应的所有表sql,mysql针对库名
     */
    private static String TABLE_MYSQL_PREFIX = "select table_name, table_comment,table_rows as row_num from information_schema.tables where table_schema = ";
    /**
     * mysql库对应库对应的所有表记录数求和
     */
    private static String ROW_COUNT_MYSQL_PREFIX = "select sum(table_rows) as total_count from information_schema.tables where table_schema = ";

    /**
     * mysql库对应库对应的所有表个数
     */
    private static String TABLE_COUNT_MYSQL_PREFIX = "select count(TABLE_NAME) as count from  information_schema.tables where table_schema=  ";

    /**
     * oracle、库对应库对应的所有表sql，oracle也是后面跟库名
     */
    private static String TABLE_ORACLE_PREFIX = "select t.table_name as table_name,t.comments as table_comment,d.num_rows as row_num  from user_tab_comments t left join dba_tables d on t.table_name=d.table_name and d.owner= ";
    /**
     * oracle、库对应库对应的所有表记录数求和
     */
    private static String ROW_COUNT_ORACLE_PREFIX = "select sum(num_rows) as total_count from dba_tables where owner= ";
    /**
     * oracle、库对应库对应的所有表个数
     */
    private static String TABLE_COUNT_ORACLE_PREFIX = "select count(TABLE_NAME) as count from dba_tables where owner= ";
    /**
     * postgres库对应库对应的所有表sql，postgres后面跟模式名称，连接时已经确定了是哪个库
     */
    private static String TABLE_POSTGRES_PREFIX = "SELECT t.tablename as table_name,d.description as table_comment,c.reltuples as row_num FROM pg_tables t \n" +
            "JOIN pg_class c ON t.tablename=c.relname\n" +
            "left JOIN pg_description d ON c.oid = d.objoid  \n" +
            "AND d.objsubid = '0' \n" +
            "where t.schemaname= ";
    /**
     * postgres库对应库对应的所有表记录数求和
     */
    private static String ROW_COUNT_POSTGRES_PREFIX = "SELECT SUM(c.reltuples) as total_count FROM pg_tables t \n" +
            "JOIN pg_class c ON t.tablename=c.relname\n" +
            "where t.schemaname= ";

    /**
     * postgres库对应库对应的所有表个数
     */
    private static String TABLE_COUNT_POSTGRES_PREFIX = "select count(tablename) as count from pg_tables where schemaname= ";


    /**
     * 表名
     */
    private static String TABLE_NAME = "table_name";
    /**
     * 表备注
     */
    private static String TABLE_COMMENT = "table_comment";
    /**
     * 表总记录数
     */
    private static String TABLE_ROWNUM = "row_num";

    /**
     * 对应每个库的总记录数
     */
    private static String ROW_DB_TOTAL_COUNT = "total_count";

    /**
     * 对应每个库的表个数
     */
    private static String TABLE_DB_TOTAL_COUNT = "count";

    /**
     * 删表sql
     */
    private static String DROP_TABLE_SQL = "DROP TABLE ";


    /**
     * @param dataBaseConfig 数据库连接配置对象
     * @param tableName      表名
     * @return
     * @Author: MR LIS
     * @Description:获取数据库表的字段名、注释、数据类型
     * @Date: 17:45 2018/5/24
     */
    public static List<ColumnInfoVO> getAllColumnInfo(DataBaseConfig dataBaseConfig, String tableName) {
        List<ColumnInfoVO> voList = new ArrayList<>();
        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(getColumnPropertySQL(dataBaseConfig.getDbType(), tableName, dataBaseConfig.getDbTableSchema()));
            rs = stmt.executeQuery();
            ColumnInfoVO infoVO = null;
            while (rs.next()) {
                if (DBTypeEnum.DB_MYSQL.getDbName().equals(dataBaseConfig.getDbType()) || DBTypeEnum.DB_TIDB.getDbName().equals(dataBaseConfig.getDbType())) {
                    infoVO = new ColumnInfoVO(rs.getString(MYSQL_COLUMN_NAME), rs.getString(MYSQL_COLUMN_COMMENT) == null ? "" : rs.getString(MYSQL_COLUMN_COMMENT), convertDataType(rs.getString(MYSQL_COLUMN_TYPE)));
                } else if (DBTypeEnum.DB_ORACLE.getDbName().equals(dataBaseConfig.getDbType())) {
                    infoVO = new ColumnInfoVO(rs.getString(ORACLE_COLUMN_NAME), rs.getString(ORACLE_COLUMN_COMMENT) == null ? "" : rs.getString(ORACLE_COLUMN_COMMENT), convertDataType(rs.getString(ORACLE_COLUMN_TYPE)));
                } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equals(dataBaseConfig.getDbType())) {
                    infoVO = new ColumnInfoVO(rs.getString(POSTGRES_COLUMN_NAME), rs.getString(POSTGRES_COLUMN_COMMENT) == null ? "" : rs.getString(POSTGRES_COLUMN_COMMENT), convertDataType(rs.getString(POSTGRES_COLUMN_TYPE)));
                }
                voList.add(infoVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }

        return voList;
    }

    /**
     * 连接数量最大值
     */
    private static volatile AtomicInteger activeSize = new AtomicInteger(20);

    /**
     * 记录连接被创建个数
     */
    private static volatile AtomicInteger createCounter = new AtomicInteger(0);

    /**
     * 获取连接对象
     *
     * @param dataBaseConfig
     * @return
     */
    public synchronized static Connection getConnection(DataBaseConfig dataBaseConfig) {
        Connection conn = null;
        //判断当前被创建的连接个数是否大于等于最大数量
        while (createCounter.get() >= activeSize.get()) {
            try {
                  GenDBUtils.class.wait(3000);
                  break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            if (DBTypeEnum.DB_MYSQL.getDbName().equals(dataBaseConfig.getDbType()) || DBTypeEnum.DB_TIDB.getDbName().equals(dataBaseConfig.getDbType())) {
                Class.forName(DriverNameEnum.DRIVER_MYSQL.getDriverName());
                String url = MYSQL_PREFIX + dataBaseConfig.getDbIp() + ":" + dataBaseConfig.getDbPort() + "/" + dataBaseConfig.getDbServerName() + MYSQL_SUFFIX;
                conn = DriverManager.getConnection(url, dataBaseConfig.getDbUser(), dataBaseConfig.getDbPassword());
            } else if (DBTypeEnum.DB_ORACLE.getDbName().equals(dataBaseConfig.getDbType())) {
                Class.forName(DriverNameEnum.DRIVER_ORACLE.getDriverName());
                String url = ORACLE_PREFIX + dataBaseConfig.getDbIp() + ":" + dataBaseConfig.getDbPort() + "/" + dataBaseConfig.getDbServerName();
                conn = DriverManager.getConnection(url, dataBaseConfig.getDbUser(), dataBaseConfig.getDbPassword());
            } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equals(dataBaseConfig.getDbType())) {
                Class.forName(DriverNameEnum.DRIVER_POSTGRES.getDriverName());
                String url = POSTGRES_PREFIX + dataBaseConfig.getDbIp() + ":" + dataBaseConfig.getDbPort() + "/" + dataBaseConfig.getDbServerName();
                conn = DriverManager.getConnection(url, dataBaseConfig.getDbUser(), dataBaseConfig.getDbPassword());
            }

            createCounter.incrementAndGet();
            System.out.println("createCount:"+createCounter.get()+",activeSize:"+activeSize.get());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 获取列的属性信息的sql拼接
     *
     * @param dbType
     * @param tableName
     * @param tableSchema postgres区分不同的模式
     * @return
     */
    private static String getColumnPropertySQL(String dbType, String tableName, String tableSchema) {
        String sql = "";
        if (DBTypeEnum.DB_MYSQL.getDbName().equals(dbType) || DBTypeEnum.DB_TIDB.getDbName().equals(dbType)) {
            sql = COLUMN_MYSQL_PREFIX + tableName;
        } else if (DBTypeEnum.DB_ORACLE.getDbName().equals(dbType)) {
            sql = COLUMN_ORACLE_PREFIX + "'" + tableName + "'";
        } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equals(dbType)) {
            sql = COLUMN_POSTGRES_PREFIX + "'" + tableName + "'";
        }
        return sql;
    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 查询分页总记录数
     * @Date: 10:05 2018/5/25
     */
    public static int executePageTotalCount(DataBaseConfig dataBaseConfig, String tableName) {
        return queryPageTotalCount(dataBaseConfig, pagingCountSql(dataBaseConfig.getDbType(), tableName, dataBaseConfig.getDbTableSchema()));
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 查询总记录数
     * @Date: 10:05 2018/5/25
     */
    public static int queryPageTotalCount(DataBaseConfig dataBaseConfig, String countSql) {
        //查询总记录数
        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            stmt = conn.prepareStatement(countSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }

        return count;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 查询分页记录结果, 不含总记录数
     * @Date: 10:05 2018/5/25
     */
    public static List<List<Object>> executePage(DataBaseConfig dataBaseConfig, String tableName, int pageSize, int start, int end) {

        return executePageRecord(dataBaseConfig, pagingSql(dataBaseConfig.getDbType(), tableName, dataBaseConfig.getDbTableSchema(), pageSize, start, end));
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 查询分页记录结果, 不含总记录数
     * @Date: 10:05 2018/5/25
     */
    public static List<List<Object>> executePage(DataBaseConfig dataBaseConfig, String tableName, String columnArrStr, int pageSize, int start, int end) {

        return executePageRecord(dataBaseConfig, pagingSql(dataBaseConfig.getDbType(), tableName, dataBaseConfig.getDbTableSchema(), columnArrStr, pageSize, start, end));
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 查询分页记录结果, 不含总记录数
     * @Date: 10:05 2018/5/25
     */
    public static List<List<Object>> executePageRecord(DataBaseConfig dataBaseConfig, String querySql) {
        List<List<Object>> listList = new ArrayList<>();
        //查询总记录数
        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.prepareStatement(querySql);
            rs = stmt.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            //oracle会多带一列行数回来
            int rowNum = DBTypeEnum.DB_ORACLE.getDbName().equals(dataBaseConfig.getDbType()) ? data.getColumnCount() - 1 : data.getColumnCount();
            while (rs.next()) {
                List<Object> objectList = new ArrayList<>();
                for (int i = 1; i <= rowNum; i++) {
                    Object o = rs.getObject(i);
                    //不为null,且2014-01-01 15:05:29.0格式进行转换
                    if (!Objects.isNull(o)) {
                        String s = String.valueOf(o);
                        //判断是否为2014-01-01 15:05:29.0格式的时间
                        if (RegexUtils.validateTimestamp(s)) {
                            objectList.add(s.substring(0, s.indexOf(".")));
                            continue;
                        }

                    }
                    objectList.add(o);
                }
                listList.add(objectList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }
        return listList;

    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 总记录数sql
     * @Date: 9:56 2018/5/25
     */
    public static String pagingCountSql(String dbType, String tableName, String tableSchema) {
        if (StringUtils.isEmpty(dbType) || StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("sql或者数据库类型不能为空！");
        }
        String countSql = "";
        if (DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbType) || DBTypeEnum.DB_TIDB.getDbName().equals(dbType)) {
            countSql = "select count(*) as count from " + tableName + " t";

        } else if (DBTypeEnum.DB_ORACLE.getDbName().equalsIgnoreCase(dbType)) {
            countSql = "select count(*) as count from " + tableName + " t";

        } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equalsIgnoreCase(dbType)) {
            countSql = "select count(*) as count from " + tableSchema + "." + tableName + " t";
        }

        return countSql;
    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 分页sql
     * @Date: 9:56 2018/5/25
     */
    public static String pagingSql(String dbType, String tableName, String tableSchema, Integer size, Integer start, Integer end) {
        if (StringUtils.isEmpty(dbType) || StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("sql或者数据库类型不能为空！");
        }
        String querySql = "";
        if (DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbType) || DBTypeEnum.DB_TIDB.getDbName().equals(dbType)) {
            querySql = "select * from " + tableName + " limit " + start + "," + size;

        } else if (DBTypeEnum.DB_ORACLE.getDbName().equalsIgnoreCase(dbType)) {
            querySql = "select * from (select T.*,ROWNUM RN from " + tableName + "  T where ROWNUM <= " + end + ") where RN >" + start;

        } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equalsIgnoreCase(dbType)) {
            querySql = "select * from " + tableSchema + "." + tableName + " limit " + size + " offset  " + start;

        }

        return querySql;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 分页sql, 根据指定的列进行分页查询
     * @Date: 9:56 2018/5/25
     */
    public static String pagingSql(String dbType, String tableName, String tableSchema, String columnArrStr, Integer size, Integer start, Integer end) {
        if (StringUtils.isEmpty(dbType) || StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("sql或者数据库类型不能为空！");
        }
        String querySql = "";
        if (DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbType) || DBTypeEnum.DB_TIDB.getDbName().equals(dbType)) {
            querySql = "select " + columnArrStr + " from " + tableName + " limit " + start + "," + size;

        } else if (DBTypeEnum.DB_ORACLE.getDbName().equalsIgnoreCase(dbType)) {
            querySql = "select " + columnArrStr + " from (select T.*,ROWNUM RN from " + tableName + "  T where ROWNUM <= " + end + ") where RN >" + start;

        } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equalsIgnoreCase(dbType)) {
            querySql = "select " + columnArrStr + " from " + tableSchema + "." + tableName + " limit " + size + " offset  " + start;

        }

        return querySql;
    }

    /**
     * mysql与oracle参考对比，参考：https://blog.csdn.net/superit401/article/details/51565119
     */
    //int集合
    private static List<String> intList = new ArrayList<String>() {{
        //mysql数据库
        add("int");
        add("integer");
        add("tinyint");
        add("smallint");
        add("bigint");
        add("bigint");
        add("mediumint");
        add("numeric");
        //oracle数据库
        add("number");
    }};
    //double 集合
    private static List<String> floatList = new ArrayList<String>() {{
        //mysql数据库
        add("float");
        add("double");
        add("decimal");
        add("real");
        //oracle数据库
    }};

    //date 集合
    private static List<String> dateList = new ArrayList<String>() {{

        //mysql数据库
        add("date");
        add("datetime");
        add("time");
        add("timestamp");
        //oracle数据库

    }};
    //string 集合
    private static List<String> stringList = new ArrayList<String>() {{
        //mysql数据库
        add("char");
        add("varchar");
        add("text");
        add("tinytext");
        add("enum");
        //oracle数据库
    }};

    /**
     * @return
     * @Author: MR LIS
     * @Description: 转换数据类型
     * @Date: 10:56 2018/5/28
     */
    public static String convertDataType(String dataType) {
        for (String s : intList) {
            if (dataType.toLowerCase().indexOf(s.toLowerCase()) != -1)
                return DbDataTypeEnum.NUMBER.getType();
        }

        for (String s : floatList) {
            if (dataType.toLowerCase().indexOf(s.toLowerCase()) != -1)
                return DbDataTypeEnum.FLOAT.getType();
        }
        for (String s : dateList) {
            if (dataType.toLowerCase().indexOf(s.toLowerCase()) != -1)
                return DbDataTypeEnum.DATE.getType();
        }
        for (String s : stringList) {
            if (dataType.toLowerCase().indexOf(s.toLowerCase()) != -1)
                return DbDataTypeEnum.STRING.getType();
        }

        return DbDataTypeEnum.STRING.getType();
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据数据库连接+库名 得到对应的所有表信息 这里的库名为用户自己创建的，非系统库名
     * 其中包括：每个表对应的总记录数，表名，表的注释信息等
     * @Date: 9:43 2018/5/30
     */
    public static List<SourceDataInfoShowVO> getDbTableInfos(DataBaseConfig dataBaseConfig) {
        List<SourceDataInfoShowVO> showVOs = new ArrayList<>();
        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        SourceDataInfoShowVO showVO = null;
        try {
            stmt = conn.prepareStatement(assembleTableSql(dataBaseConfig.getDbType(), dataBaseConfig.getDbServerName(), dataBaseConfig.getDbTableSchema()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                showVO = new SourceDataInfoShowVO();
                SourceDataInfoVO sourceVO = new SourceDataInfoVO(dataBaseConfig.getDbId(), rs.getString(TABLE_NAME), rs.getString(TABLE_COMMENT) == null ? "" : rs.getString(TABLE_COMMENT), SourceDataTypeEnum.LOCAL.getCode());
                showVO.setCount(rs.getInt(TABLE_ROWNUM));
                showVO.setSourceDataInfoVO(sourceVO);
                showVOs.add(showVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }


        return showVOs;


    }


    /**
     * @param dbType     数据库类型
     * @param serverName 库名
     * @return
     * @Author: MR LIS
     * @Description: 拼接获取库对应的所有表信息
     * mysql、oracle根据库名去查找下面所有自己创建的表，postgres根据模式去找，同一个库下的不同模式不要有重复的表,postgres连接时已经制定了库
     * @Date: 9:56 2018/5/30
     */
    private static String assembleTableSql(String dbType, String serverName, String tableSchema) {
        if (StringUtils.isEmpty(dbType)) {
            throw new RuntimeException("数据库类型不能为空！");
        }
        return getAssembleSqlString(dbType, serverName, tableSchema, TABLE_MYSQL_PREFIX, TABLE_ORACLE_PREFIX, TABLE_POSTGRES_PREFIX);
    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据数据库连接+库名 得到对应的所有表记录求和总记录数
     * @Date: 9:43 2018/5/30
     */
    public static int getDbRowTotalCount(DataBaseConfig dataBaseConfig) {

        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try {
            stmt = conn.prepareStatement(assembleROWCountSql(dataBaseConfig.getDbType(), dataBaseConfig.getDbServerName(), dataBaseConfig.getDbTableSchema()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                totalCount = rs.getInt(ROW_DB_TOTAL_COUNT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }

        return totalCount;

    }


    /**
     * @param dbType     数据库类型
     * @param serverName 库名
     * @return
     * @Author: MR LIS
     * @Description: 拼接获取库对应的所有表记录求和总记录数
     * mysql、oracle根据库名去查找下面所有自己创建的表，postgres根据模式去找，同一个库下的不同模式不要有重复的表,postgres连接时已经制定了库
     * @Date: 9:56 2018/5/30
     */
    private static String assembleROWCountSql(String dbType, String serverName, String tableSchema) {
        if (StringUtils.isEmpty(dbType)) {
            throw new RuntimeException("数据库类型不能为空！");
        }
        return getAssembleSqlString(dbType, serverName, tableSchema, ROW_COUNT_MYSQL_PREFIX, ROW_COUNT_ORACLE_PREFIX, ROW_COUNT_POSTGRES_PREFIX);
    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 删表操作
     * @Date: 10:15 2018/6/1
     */
    public static void dropTable(DataBaseConfig dataBaseConfig, String tableName) {
        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(assembleDropTableSql(dataBaseConfig.getDbType(), tableName, dataBaseConfig.getDbTableSchema()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }
    }

    /**
     * 拼接删除表sql
     *
     * @param dbType
     * @param tableName
     * @return
     */
    private static String assembleDropTableSql(String dbType, String tableName, String tableSchema) {
        String dropSql = "";
        //postgres表名需要加引号
        if (DBTypeEnum.DB_POSTGRESQL.getDbName().equalsIgnoreCase(dbType)) {
            dropSql += DROP_TABLE_SQL + tableSchema + "." + "\"" + tableName + "\"";
        } else {
            dropSql += DROP_TABLE_SQL + tableName;
        }
        return dropSql;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据数据库连接+库名 得到库对应的表个数
     * @Date: 9:43 2018/5/30
     */
    public static int getDbTableTotalCount(DataBaseConfig dataBaseConfig) {

        Connection conn = getConnection(dataBaseConfig);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalCount = 0;
        try {
            stmt = conn.prepareStatement(assembleTableCountSql(dataBaseConfig.getDbType(), dataBaseConfig.getDbServerName(), dataBaseConfig.getDbTableSchema()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                totalCount = rs.getInt(TABLE_DB_TOTAL_COUNT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, stmt, rs);
        }

        return totalCount;

    }

    /**
     * @param dbType     数据库类型
     * @param serverName 库名
     * @return
     * @Author: MR LIS
     * @Description: 拼接获取库对应的所有表个数
     * mysql、oracle根据库名去查找下面所有自己创建的表，postgres根据模式去找，同一个库下的不同模式不要有重复的表,postgres连接时已经制定了库
     * @Date: 9:56 2018/5/30
     */
    private static String assembleTableCountSql(String dbType, String serverName, String tableSchema) {
        if (StringUtils.isEmpty(dbType)) {
            throw new RuntimeException("数据库类型不能为空！");
        }
        return getAssembleSqlString(dbType, serverName, tableSchema, TABLE_COUNT_MYSQL_PREFIX, TABLE_COUNT_ORACLE_PREFIX, TABLE_COUNT_POSTGRES_PREFIX);
    }

    /**
     * 拼接公共部分
     *
     * @param dbType
     * @param serverName
     * @param tableSchema
     * @param mysqlPrefix
     * @param oraclePrefix
     * @param postgresPrefix
     * @return
     */
    private static String getAssembleSqlString(String dbType, String serverName, String tableSchema, String mysqlPrefix, String oraclePrefix, String postgresPrefix) {
        String countSql = "";
        if (DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbType) || DBTypeEnum.DB_TIDB.getDbName().equals(dbType)) {
            countSql = mysqlPrefix + "'" + serverName + "'";
        } else if (DBTypeEnum.DB_ORACLE.getDbName().equalsIgnoreCase(dbType)) {
            countSql = oraclePrefix + "'" + serverName + "'";
        } else if (DBTypeEnum.DB_POSTGRESQL.getDbName().equalsIgnoreCase(dbType)) {
            countSql = postgresPrefix + "'" + tableSchema + "'";
        }
        return countSql;
    }

    /**
     * 关闭连接
     *
     * @param stmt
     * @param rs
     */
    public  synchronized static void closeConn(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * 连接数减去1
         */
        int current = createCounter.decrementAndGet();
        //判断是否小于总连接的个数，并通知
        if (current < activeSize.get()) {
            GenDBUtils.class.notify();
        }
    }

}
