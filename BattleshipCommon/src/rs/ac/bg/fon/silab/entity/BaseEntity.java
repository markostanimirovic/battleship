/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author marko
 */
public interface BaseEntity extends Serializable {

    String getTableName();

    List<BaseEntity> populate(ResultSet rs) throws Exception;

    String getInsertValues();

    BaseEntity convert(ResultSet rs) throws Exception;

    String getConditionWithIdentifier();

    String getIdentifier();

    Object get(String attributeName);

    void set(String attributeName, Object attributeValue);

    String getJoinCondition();

    String getSearchCondition(String searchText);

    String getUpdateValues();

}
