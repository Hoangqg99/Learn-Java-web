package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
@Primary
@PropertySource("classpath:application.properties")
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // @Value("${spring.datasource.url}")
    // private String DB_URL;

    // @Value("${spring.datasource.username}")
    // private String USER;

    // @Value("${spring.datasource.password}")
    // private String PASS;

    public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
        Long staffId = buildingSearchBuilder.getStaffId();
        if (staffId != null) {
            sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
        }
        List<String> typeCode = buildingSearchBuilder.getTypeCode();
        if (typeCode != null && typeCode.size() != 0) {
            sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
            sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
        }
    }

    public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
            for (Field item : fields) {
                item.setAccessible(true);
                String fieldName = item.getName();
                if (!fieldName.equals("staffId") && !fieldName.equals("typeCode")
                        && !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
                    Object value = item.get(buildingSearchBuilder);
                    if (value != null) {
                        if (item.getType().getName().equals("java.lang.Long")
                                || item.getType().getName().equals("java.lang.Integer")) {
                            where.append(" AND b.").append(fieldName).append(" = ").append(value);
                        } else if (item.getType().getName().equals("java.lang.String")) {
                            where.append(" AND b.").append(fieldName).append(" LIKE '%").append(value).append("%' ");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        Long staffId = buildingSearchBuilder.getStaffId();
        if (staffId != null) {
            where.append(" AND assignmentbuilding.staffid = ").append(staffId);
        }
        Long rentAreaTo = buildingSearchBuilder.getAreaTo();
        Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
        if (rentAreaFrom != null || rentAreaTo != null) {
            where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
            if (rentAreaFrom != null) {
                where.append(" AND r.value >= ").append(rentAreaFrom);
            }
            if (rentAreaTo != null) {
                where.append(" AND r.value <= ").append(rentAreaTo);
            }
            where.append(" ) ");
        }

        Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
        Long rentPriceFrom = buildingSearchBuilder.getRentPriceForm();
        if (rentPriceFrom != null || rentPriceTo != null) {
            if (rentPriceFrom != null) {
                where.append(" AND b.rentprice >= ").append(rentPriceFrom);
            }
            if (rentPriceTo != null) {
                where.append(" AND b.rentprice <= ").append(rentPriceTo);
            }
        }

        List<String> typeCode = buildingSearchBuilder.getTypeCode();
        if (typeCode != null && !typeCode.isEmpty()) {
            where.append(" AND (");
            String sql = typeCode.stream()
                    .map(it -> "renttype.code LIKE '%" + it + "%'")
                    .collect(Collectors.joining(" OR "));
            where.append(sql);
            where.append(") ");
        }
    }

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        StringBuilder sql = new StringBuilder(
                "SELECT b.id, b.name, b.districtid, b.street, b.floorarea, b.ward, " +
                        "b.numberofbasement, b.managername, b.managerphonenumber, " +
                        "b.rentprice, b.servicefee, b.brokeragefee FROM building b ");
        joinTable(buildingSearchBuilder, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        queryNormal(buildingSearchBuilder, where);
        querySpecial(buildingSearchBuilder, where);
        where.append(" GROUP BY b.id");
        sql.append(where);

        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
        return query.getResultList();
    }

    // List<BuildingEntity> result = new ArrayList<>();
    // try (
    // Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    // // Connection conn = ConnectionJDBCUtil.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql.toString());
    // ResultSet rs = pstmt.executeQuery()) {

    // while (rs.next()) {
    // BuildingEntity buildingEntity = new BuildingEntity();
    // buildingEntity.setId(rs.getLong("id"));
    // buildingEntity.setName(rs.getString("name"));
    // buildingEntity.setStreet(rs.getString("street"));
    // buildingEntity.setWard(rs.getString("ward"));
    // // buildingEntity.setDistrictId(rs.getLong("districtid"));
    // buildingEntity.setRentPrice(rs.getInt("rentprice"));
    // buildingEntity.setFloorArea(rs.getInt("floorarea"));
    // buildingEntity.setManagerName(rs.getString("managername"));
    // buildingEntity.setManagerPhoneNumber(rs.getString("managerphonenumber"));
    // result.add(buildingEntity);
    // }
    // }catch(
    // SQLException ex)
    // {
    // ex.printStackTrace();
    // }return result;
    // }

    // @Override
    public void DeleteById(long id) {
        // TODO: Implement delete logic
    }
}