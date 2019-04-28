/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author marko
 */
public class User implements BaseEntity {

    private int userId;
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public List<BaseEntity> populate(ResultSet rs) throws Exception {
        try {
            List<BaseEntity> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"));
                users.add(user);
            }
            rs.close();

            return users;
        } catch (SQLException ex) {
            throw new Exception("Neuspešno učitavanje korisnika", ex);
        }
    }

    @Override
    public String getValuesForInsert() {
        return userId + ", '" + username + "', '" + password + "'";
    }

    @Override
    public BaseEntity convert(ResultSet rs) throws Exception {
        try {
            User user = null;
            if (rs.next()) {
                int korisnikID1 = rs.getInt("korisnikID");
                String ime1 = rs.getString("ime");
                String prezime1 = rs.getString("prezime");
                String korisnickoIme1 = rs.getString("korisnickoIme");
                String sifra = rs.getString("korisnickaSifra");
                user = new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"));

            }
            rs.close();

            return user;
        } catch (SQLException ex) {
            throw new Exception("Neuspešno učitavanje korisnika", ex);
        }
    }

    @Override
    public String getConditionWithIdentifier() {
        return "username='" + username + "' AND password='" + password + "'";
    }

    @Override
    public String getIdentifier() {
        return "userId";
    }

    @Override
    public Object get(String attributeName) {
        switch (attributeName) {
            case "userId":
                return userId;
            case "username":
                return username;
            case "password":
                return password;
            default:
                return null;
        }
    }

    @Override
    public void set(String attributeName, Object attributeValue) {
        switch (attributeName) {
            case "userId":
                setUserId(Integer.parseInt((String) attributeValue));
                break;
            case "username":
                setUsername((String) attributeValue);
                break;
            case "password":
                setPassword((String) attributeValue);
                break;
        }
    }

    @Override
    public String getJoinCondition() {
        return "";
    }

    @Override
    public String getSearchCondition(String searchText) {
        return "WHERE username='" + username + "'";
    }

    @Override
    public String getUpdateValues() {
        return "username='" + username + "', password='" + password + "'";
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
