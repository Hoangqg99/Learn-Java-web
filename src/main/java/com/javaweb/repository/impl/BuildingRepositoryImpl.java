package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

    // public static void joinTable(BuildingSearchBuilder buildingSearchBuilder,
    // StringBuilder sql) {
    // Long staffId = buildingSearchBuilder.getStaffId();
    // if (staffId != null) {
    // sql.append(" INNER JOIN assignmentbuilding ON b.id =
    // assignmentbuilding.buildingid ");
    // }
    // List<String> typeCode = buildingSearchBuilder.getTypeCode();
    // if (typeCode != null && typeCode.size() != 0) {
    // sql.append(" INNER JOIN buildingrenttype ON b.id =
    // buildingrenttype.buildingid ");
    // sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid
    // ");
    // }
    // }

    // public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder,
    // StringBuilder where) {
    // try {
    // Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
    // for (Field item : fields) {
    // item.setAccessible(true);
    // String fieldName = item.getName();
    // if (!fieldName.equals("staffId") && !fieldName.equals("typeCode")
    // && !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
    // Object value = item.get(buildingSearchBuilder);
    // if (value != null) {
    // if (item.getType().getName().equals("java.lang.Long")
    // || item.getType().getName().equals("java.lang.Integer")) {
    // where.append(" AND b.").append(fieldName).append(" = ").append(value);
    // } else if (item.getType().getName().equals("java.lang.String")) {
    // where.append(" AND LOWER(b.").append(fieldName).append(") LIKE
    // LOWER('%").append(value)
    // .append("%')");
    // }
    // }
    // }
    // }
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }
    // }

    // public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder,
    // StringBuilder where) {
    // Long staffId = buildingSearchBuilder.getStaffId();
    // if (staffId != null) {
    // where.append(" AND assignmentbuilding.staffid = ").append(staffId);
    // }
    // Long rentAreaTo = buildingSearchBuilder.getAreaTo();
    // Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
    // if (rentAreaFrom != null || rentAreaTo != null) {
    // where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid
    // ");
    // if (rentAreaFrom != null) {
    // where.append(" AND r.value >= ").append(rentAreaFrom);
    // }
    // if (rentAreaTo != null) {
    // where.append(" AND r.value <= ").append(rentAreaTo);
    // }
    // where.append(" ) ");
    // }

    // Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
    // Long rentPriceFrom = buildingSearchBuilder.getRentPriceForm();
    // if (rentPriceFrom != null || rentPriceTo != null) {
    // where.append(" AND (");
    // if (rentPriceFrom != null && rentPriceTo != null) {
    // where.append("b.rentprice BETWEEN ").append(rentPriceFrom).append(" AND
    // ").append(rentPriceTo);
    // } else if (rentPriceFrom != null) {
    // where.append("b.rentprice >= ").append(rentPriceFrom);
    // } else if (rentPriceTo != null) {
    // where.append("b.rentprice <= ").append(rentPriceTo);
    // }
    // where.append(")");
    // }

    // List<String> typeCode = buildingSearchBuilder.getTypeCode();
    // if (typeCode != null && !typeCode.isEmpty()) {
    // where.append(
    // " AND EXISTS (SELECT 1 FROM buildingrenttype brt INNER JOIN renttype rt ON
    // brt.renttypeid = rt.id WHERE brt.buildingid = b.id AND rt.code IN (");
    // where.append(
    // String.join(",", typeCode.stream().map(code -> "'" + code +
    // "'").collect(Collectors.toList())));
    // where.append("))");
    // }
    // }

    // @Override
    // public List<BuildingEntity> findAll(BuildingSearchBuilder
    // buildingSearchBuilder) {
    // StringBuilder sql = new StringBuilder(
    // "SELECT b.id, b.name, b.districtid, b.street, b.floorarea, b.ward, " +
    // "b.numberofbasement, b.managername, b.managerphonenumber, " +
    // "b.rentprice, b.servicefee, b.brokeragefee FROM building b ");
    // joinTable(buildingSearchBuilder, sql);
    // StringBuilder where = new StringBuilder(" WHERE 1=1 ");
    // queryNormal(buildingSearchBuilder, where);
    // querySpecial(buildingSearchBuilder, where);
    // where.append(" GROUP BY b.id");
    // sql.append(where);

    // List<BuildingEntity> result = new ArrayList<>();
    // try (Connection conn = ConnectionJDBCUtil.getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

    // try (ResultSet rs = pstmt.executeQuery()) {
    // while (rs.next()) {
    // BuildingEntity buildingEntity = new BuildingEntity();
    // buildingEntity.setId(rs.getLong("id"));
    // buildingEntity.setName(rs.getString("name"));
    // buildingEntity.setStreet(rs.getString("street"));
    // buildingEntity.setWard(rs.getString("ward"));
    // buildingEntity.setServiceFee(rs.getString("servicefee"));
    // buildingEntity.setBrokerageFee(rs.getString("brokeragefee"));
    // buildingEntity.setDistrictId(rs.getLong("districtid"));
    // buildingEntity.setRentPrice(rs.getInt("rentprice"));
    // buildingEntity.setFloorArea(rs.getInt("floorarea"));
    // buildingEntity.setManagerName(rs.getString("managername"));
    // buildingEntity.setManagerPhoneNumber(rs.getString("managerphonenumber"));
    // result.add(buildingEntity);
    // }
    // }
    // } catch (SQLException ex) {
    // throw new RuntimeException("Error executing building search query", ex);
    // }
    // return result;
    // }

    // @Override
    // public void DeleteById(long id) {
    // // TODO: Implement delete logic
    // }

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT b.id, b.name, b.districtid, b.street, b.floorarea, b.ward, b.numberOfBasement, b.managerName, b.managerPhoneNumber, ");
            sql.append("b.rentprice, b.servicefee, b.brokeragefee FROM building b ");
            joinTable(buildingSearchBuilder, sql);
            where_condition_normal(buildingSearchBuilder, sql);
            where_condition_special(sql, buildingSearchBuilder);
            Connection connection = ConnectionJDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            System.out.println(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            List<BuildingEntity> results = new ArrayList<BuildingEntity>();
            while (resultSet.next()) {
                BuildingEntity buildingEntity = new BuildingEntity();
                buildingEntity.setId(resultSet.getLong("id"));
                buildingEntity.setName(resultSet.getString("name"));
                buildingEntity.setStreet(resultSet.getString("street"));
                buildingEntity.setWard(resultSet.getString("ward"));
                buildingEntity.setServiceFee(resultSet.getString("servicefee"));
                buildingEntity.setBrokerageFee(resultSet.getString("brokeragefee"));
                buildingEntity.setDistrictId(resultSet.getLong("districtid"));
                buildingEntity.setRentPrice(resultSet.getInt("rentprice"));
                buildingEntity.setFloorArea(resultSet.getInt("floorArea"));
                buildingEntity.setManagerName(resultSet.getString("managername"));
                buildingEntity.setManagerPhoneNumber(resultSet.getString("managerphonenumber"));
                results.add(buildingEntity);
            }
            return results;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
        if (buildingSearchBuilder.getTypeCode() != null && !buildingSearchBuilder.getTypeCode().isEmpty()) {
            sql.append(" INNER JOIN buildingrenttype ON buildingrenttype.buildingId = b.id ");
            sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeId ");
        }
        if (buildingSearchBuilder.getStaffId() != null) {
            sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
        }
    }

    private void where_condition_normal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
        sql.append(" WHERE 1 = 1 ");
        try {
            Field[] feilds = BuildingSearchBuilder.class.getDeclaredFields();
            for (Field feild : feilds) {
                feild.setAccessible(true);
                String key = feild.getName().trim();
                if (!key.toLowerCase().startsWith("area") && !key.equalsIgnoreCase("typeCode")) {
                    String value = feild.get(buildingSearchBuilder) == null ? null
                            : feild.get(buildingSearchBuilder).toString();
                    if (StringUtil.checkString(value)) {
                        if (key.equalsIgnoreCase("numberOfBasement")) {
                            Integer numberOfBasement = Integer.parseInt(value);
                            sql.append(" AND b.numberofbasement = ").append(numberOfBasement);
                        } else if (key.equalsIgnoreCase("staffId")) {
                            Long staffId = Long.parseLong(value);
                            sql.append(" AND assignmentbuilding.staffid = ").append(staffId);
                        } else if (key.equalsIgnoreCase("districtId")) {
                            Long districtId = Long.parseLong(value);
                            sql.append(" AND b.districtid = ").append(districtId);
                        } else if (key.startsWith("rentPrice")) {
                            Integer rentPrice = Integer.parseInt(value);
                            if (key.endsWith("From")) {
                                sql.append(" AND b.rentPrice >= ").append(rentPrice);
                            } else {
                                sql.append(" AND b.rentPrice <= ").append(rentPrice);
                            }
                        } else if (!key.equalsIgnoreCase("typeCode")) {
                            sql.append(" AND b.").append(key).append(" LIKE '%").append(value).append("%'");
                        }
                    }
                }
            }
            System.out.println("Final SQL: " + sql.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void where_condition_special(StringBuilder sql, BuildingSearchBuilder buildingSearchBuilder) {
        Long areaFrom = buildingSearchBuilder.getAreaFrom();
        Long areaTo = buildingSearchBuilder.getAreaTo();
        if (areaFrom != null || areaTo != null) {
            sql.append(" AND EXISTS (SELECT rentarea.value FROM rentarea WHERE rentarea.buildingid = b.id ");
            if (areaFrom != null) {
                sql.append(" AND rentarea.value >= ").append(areaFrom);
            }
            if (areaTo != null) {
                sql.append(" AND rentarea.value <= ").append(areaTo);
            }
            sql.append(" ) ");
        }
        List<String> typeCode = buildingSearchBuilder.getTypeCode();
        if (typeCode != null && typeCode.size() > 0) {
            String typeSQL = String.join(", ",
                    typeCode.stream().map(item -> "'" + item + "'").collect(Collectors.toList()));
            sql.append(" AND renttype.code IN ( " + typeSQL + " ) ");
        }
        sql.append(" GROUP BY b.id");
    }
}