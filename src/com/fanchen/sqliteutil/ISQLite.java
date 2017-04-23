package com.fanchen.sqliteutil;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by fanchen on 2017/4/23.
 */
public interface ISQLite {

    Connection getConnection();

    void closeConnection(Connection connection);

    void insert(String tbName,String clusId,String feature,String docsNum);

    void update(String tbName,String feature,String docsNum,String condition_key,String condition_value);

    void deleteByClusId(String tbName,String clusId);

    ResultSet query(String tbName);

    ResultSet queryByClusId(String tbName,String clusId);
}
