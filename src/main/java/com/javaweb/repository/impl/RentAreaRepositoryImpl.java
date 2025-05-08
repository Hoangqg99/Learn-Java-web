package com.javaweb.repository.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

public class RentAreaRepositoryImpl implements RentAreaRepository {
    @Override
    public List<RentAreaEntity> getValueByBuildingId(long id) {
        try {
            List<RentAreaEntity> result = new ArrayList<RentAreaEntity>();
            StringBuilder sql = new StringBuilder(
                    "SELECT rentarea.id, rentarea.value FROM rentarea WHERE buildingid = ");
            sql.append(id);
            Connection connection = ConnectionJDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setId(resultSet.getLong("id"));
                rentAreaEntity.setValue(resultSet.getInt("value"));
                result.add(rentAreaEntity);
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}