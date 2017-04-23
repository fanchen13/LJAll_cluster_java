package com.fanchen.sqliteutil;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by fanchen on 2017/4/23.
 */
public class SQLiteImpl implements ISQLite {

    public static Logger LOG = Logger.getLogger(SQLiteImpl.class);

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            LOG.info("load driver class");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/sample.db");

            if(connection!=null){
                LOG.info("get connection success");
            }else {
                LOG.info("get connection failed");
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(),e);
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        if(connection!=null){
            LOG.info("close sqlite db connection");
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(),e);
            }
        }
    }

    @Override
    public void insert(String tbName,String clusId, String feature, String docsNum) {
        Connection connection = getConnection();
        String sql_insert = "insert into " + tbName + " values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert);

            preparedStatement.setString(1,clusId);
            preparedStatement.setString(2,feature);
            preparedStatement.setString(3,docsNum);

            LOG.info("SQL : " + sql_insert);
            LOG.info("execute insert sql");
            preparedStatement.execute();

        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(String tbName, String feature, String docsNum, String condition_key,String condition_value) {
        Connection connection = getConnection();
        String sql_update = "update "+ tbName + " set feature=?,docsNum=? where "+ condition_key +"=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_update);

            preparedStatement.setString(1,feature);
            preparedStatement.setString(2,docsNum);

            preparedStatement.setString(3,condition_value);

            LOG.info("SQL : " + sql_update);
            LOG.info("execute update sql");
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deleteByClusId(String tbName,String clusId) {
        Connection connection = getConnection();
        String sql_delete = "delete from "+ tbName +" where clusId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_delete);

            preparedStatement.setString(1,clusId);

            LOG.info("SQL : " + sql_delete);
            LOG.info("execute delete sql");
            preparedStatement.execute();

        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
    }

    @Override
    public ResultSet query(String tbName) {
        Connection connection = getConnection();
        String sql_query = "select * from " + tbName;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);

            LOG.info("SQL : " + sql_query);
            LOG.info("execute query sql");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
        return resultSet;
    }

    @Override
    public ResultSet queryByClusId(String tbName,String clusId) {
        Connection connection = getConnection();
        String sql_query = "select * from "+ tbName + " where clusId=?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            preparedStatement.setString(1,clusId);

            LOG.info("SQL : " + sql_query);
            LOG.info("execute query sql");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
        }
        return resultSet;
    }
}
