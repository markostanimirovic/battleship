/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.silab.entity.BaseEntity;

import static rs.ac.bg.fon.silab.constant.Database.*;

/**
 *
 * @author marko
 */
public class DBBroker {

    private Connection connection;
    private static DBBroker instance;

    private DBBroker() {
    }

    public static synchronized DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }

        return instance;
    }

    public void loadDriver() throws Exception {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno učitavanje drajvera!", ex);
        }
    }

    public void openConnection() throws Exception {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno uspostavanje konekcije!", ex);
        }

    }

    public void closeConnection() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno zatvaranje konekcije!", ex);
        }
    }

    public void commit() throws Exception {
        try {
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno potvrđivanje transakcije!", ex);
        }
    }

    public void rollback() throws Exception {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno poništavanje transakcije!", ex);
        }
    }

    public List<BaseEntity> getList(BaseEntity baseEntity) throws Exception {
        try {
            String query = "SELECT * FROM " + baseEntity.getTableName() + baseEntity.getJoinCondition();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            List<BaseEntity> baseEntities = baseEntity.populate(rs);

            rs.close();
            statement.close();

            return baseEntities;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno učitavanje objekata!", ex);
        }
    }

    public void save(BaseEntity baseEntity) throws Exception {
        try {
            String query = "INSERT INTO " + baseEntity.getTableName() + " VALUES (" + baseEntity.getInsertValues() + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno čuvanje objekta!", ex);
        }
    }

    public BaseEntity get(BaseEntity baseEntity) throws Exception {
        try {
            String query = "SELECT * FROM " + baseEntity.getTableName() + baseEntity.getJoinCondition() + " WHERE " + baseEntity.getConditionWithIdentifier();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            BaseEntity entity = baseEntity.convert(rs);

            rs.close();
            statement.close();

            return entity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno učitavanje objekta!", ex);
        }
    }

    public void update(BaseEntity baseEntity) throws Exception {
        try {
            String query = "UPDATE " + baseEntity.getTableName() + " SET " + baseEntity.getUpdateValues() + " WHERE " + baseEntity.getConditionWithIdentifier();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno čuvanje objekta!", ex);
        }
    }

    public void delete(BaseEntity baseEntity) throws Exception {
        try {
            String query = "DELETE FROM " + baseEntity.getTableName() + " WHERE " + baseEntity.getConditionWithIdentifier();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno brisanje objekta!", ex);
        }
    }

    public List<BaseEntity> getListWithSearch(String searchText, BaseEntity baseEntity) throws Exception {
        try {
            String query = "SELECT * FROM " + baseEntity.getTableName() + baseEntity.getJoinCondition() + " " + baseEntity.getSearchCondition(searchText);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<BaseEntity> baseEntities = baseEntity.populate(rs);

            rs.close();
            statement.close();

            return baseEntities;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno učitavanje objekata!", ex);
        }
    }

    public int getMaxIdentifierValue(BaseEntity baseEntity) throws Exception {
        try {
            String query = "SELECT MAX(" + baseEntity.getIdentifier() + ") FROM " + baseEntity.getTableName();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();

            int max = rs.getInt(1);

            rs.close();
            statement.close();

            return max;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspešno učitavanje maksimalne vrednosti!", ex);
        }
    }

}
