package com.javaweb.repository.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

    @Override
    public List<RentAreaEntity> getValueByBuildingId(long id) {
        String sql = "SELECT * FROM rentarea Where rentarea.buildingid = " + id;
        List<RentAreaEntity> rentAreas = new ArrayList<>();
        try (Connection conn = ConnectionJDBCUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RentAreaEntity areaEntity = new RentAreaEntity();
                areaEntity.setValue(rs.getString("value"));
                rentAreas.add(areaEntity);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return rentAreas;
    }
}