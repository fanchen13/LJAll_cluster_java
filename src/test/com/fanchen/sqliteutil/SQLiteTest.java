package com.fanchen.sqliteutil;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.*;

/**
 * Created by fanchen on 2017/4/23.
 */
public class SQLiteTest {

    public static Logger LOG = Logger.getLogger(SQLiteTest.class);


    @Test
    public void testGetConnection(){
        ISQLite sqLite = new SQLiteImpl();
        sqLite.getConnection();
    }

    @Test
    public void testCloseConnection(){
        ISQLite sqLite = new SQLiteImpl();
        Connection connection = sqLite.getConnection();

        sqLite.closeConnection(connection);
    }

    @Test
    public void testInsert(){
        ISQLite isqLite = new SQLiteImpl();
        isqLite.insert("message","20","导员 厉害 陕西 说辞 小雨 学校 充磁 保卫 报名","15");

    }

    @Test
    public void testUpdate(){
        ISQLite isqLite = new SQLiteImpl();
        isqLite.update("message","导员 厉害 陕西","3","clusId","10");
    }

    @Test
    public void testDeleteByClusId(){
        ISQLite isqLite = new SQLiteImpl();
        isqLite.deleteByClusId("message","10");
    }

    @Test
    public void testQuery() throws SQLException {
        ISQLite isqLite = new SQLiteImpl();
        ResultSet resultSet = isqLite.query("message");

        while (resultSet.next()){

            LOG.info("clusId : " + resultSet.getString("clusId"));

        }
    }
    @Test
    public void testQueryByClusId() throws SQLException {

        ISQLite isqLite = new SQLiteImpl();
        ResultSet resultSet = isqLite.queryByClusId("message","10");

        while (resultSet.next()){
            LOG.info("feature : " + resultSet.getString("feature"));
        }
    }
    public static void main(String... args){

        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection =
                    DriverManager.getConnection("jdbc:sqlite:db/sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists person_1");
            statement.executeUpdate("create table person_1 (id integer, name string)");
            statement.executeUpdate("insert into person_1 values(1, 'leo')");
            statement.executeUpdate("insert into person_1 values(2, 'yui')");

            ResultSet resultSet = statement.executeQuery("select * from person_1");

            while (resultSet.next()){

                System.out.println("name : "+ resultSet.getString("name"));
                System.out.println("id : "+resultSet.getString("id"));
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(),e);
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(),e);
                }
            }
        }
    }

}
