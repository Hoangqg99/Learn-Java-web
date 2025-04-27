package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.repository.DistrictRepository;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

    // @Override
    // public DistrictEntity findNameById(Long id) {
    // String sql = "SELECT d.name FROM district d WHERE d.id = ?";
    // DistrictEntity districtEntity = new DistrictEntity();
    // try (Connection conn = ConnectionJDBCUtil.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // pstmt.setLong(1, id);
    // try (ResultSet rs = pstmt.executeQuery()) {
    // if (rs.next()) {
    // districtEntity.setName(rs.getString("name"));
    // }
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // System.out.println("Connected database failed");
    // }
    // return districtEntity;
    // }
    @Override
    public DistrictEntity findNameById(Long id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM district WHERE id = ");
            sql.append(id);
            Connection connection = ConnectionJDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            DistrictEntity districtEntity = new DistrictEntity();
            if (resultSet.next()) {
                districtEntity.setId(resultSet.getLong("id"));
                districtEntity.setName(resultSet.getString("name"));
                districtEntity.setCode(resultSet.getString("code"));
            }
            return districtEntity;
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }

    }
}
