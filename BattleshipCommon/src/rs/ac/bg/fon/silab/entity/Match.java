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

/**
 *
 * @author marko
 */
public class Match implements BaseEntity {

    private int matchId;
    private User winner;
    private User looser;

    public Match() {
    }

    public Match(User winner, User looser) {
        this.winner = winner;
        this.looser = looser;
    }

    public Match(int matchId, User winner, User looser) {
        this.matchId = matchId;
        this.winner = winner;
        this.looser = looser;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getLooser() {
        return looser;
    }

    public void setLooser(User looser) {
        this.looser = looser;
    }

    @Override
    public String getTableName() {
        return "`match`";
    }

    @Override
    public List<BaseEntity> populate(ResultSet rs) throws Exception {
        try {
            List<BaseEntity> matches = new ArrayList<>();
            while (rs.next()) {
                User winner = new User(rs.getInt("winner.id"), rs.getString("winner.username"), rs.getString("winner.password"));
                User looser = new User(rs.getInt("looser.id"), rs.getString("looser.username"), rs.getString("looser.password"));
                Match match = new Match(rs.getInt("matchId"), winner, looser);
                matches.add(match);
            }

            return matches;
        } catch (SQLException ex) {
            throw new Exception("Neuspešno učitavanje mečeva", ex);
        }
    }

    @Override
    public String getInsertValues() {
        return matchId + ", " + winner.getUserId() + ", " + looser.getUserId();
    }

    @Override
    public BaseEntity convert(ResultSet rs) throws Exception {
        try {
            Match match = null;
            if (rs.next()) {
                User winner = new User(rs.getInt("winner.id"), rs.getString("winner.username"), rs.getString("winner.password"));
                User looser = new User(rs.getInt("looser.id"), rs.getString("looser.username"), rs.getString("looser.password"));
                match = new Match(rs.getInt("matchId"), winner, looser);
            }

            return match;
        } catch (SQLException ex) {
            throw new Exception("Neuspešno učitavanje meča", ex);
        }
    }

    @Override
    public String getConditionWithIdentifier() {
        return "matchId=" + matchId;
    }

    @Override
    public String getIdentifier() {
        return "matchId";
    }

    @Override
    public Object get(String attributeName) {
        switch (attributeName) {
            case "matchId":
                return matchId;
            case "winner":
                return winner;
            case "looser":
                return looser;
            default:
                return null;
        }
    }

    @Override
    public void set(String attributeName, Object attributeValue) {
        switch (attributeName) {
            case "matchId":
                setMatchId(Integer.parseInt((String) attributeValue));
                break;
            case "winner":
                setWinner((User) attributeValue);
                break;
            case "looser":
                setLooser((User) attributeValue);
                break;
        }
    }

    @Override
    public String getJoinCondition() {
        return " JOIN user winner ON (`match`.winnerId=winner.userId) JOIN user looser ON (`match`.looserId=looser.userId)";
    }

    @Override
    public String getSearchCondition(String searchText) {
        return "WHERE winner.username LIKE '%" + searchText + "%' OR looser.username LIKE '%" + searchText + "%'";
    }

    @Override
    public String getUpdateValues() {
        return "winnerId=" + winner.getUserId() + ", looserId=" + looser.getUserId();
    }

    @Override
    public String toString() {
        return "Winner: " + winner + ", Looser: " + looser;
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
        final Match other = (Match) obj;
        if (this.matchId != other.matchId) {
            return false;
        }
        return true;
    }

}
