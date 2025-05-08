package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository

// Có hai file impl BuildingRepository file này + file JDBCRepositoryImpl
// Câu lệnh ưu tiên chọn giữa hai file
@Primary

public class BuildingRepositoryImpl implements BuildingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	// public List<BuildingEntity> findAll(BuildingSearchBuilder
	// buildingSearchBuilder) {

	// // JPQL : JPA Query Language
	// // Làm việc trực tiếp với entity thay vì trực tiếp csdl
	// // String sql = "FROM BuildingEntity b Where b.name Like '%building%' ";
	// // Query query = entityManager.createQuery(sql, BuildingEntity.class);

	// // SQL Native
	// // Câu lệnh thuần sql
	// String sql = "SELECT b.* FROM building b WHERE b.name like '%building%' ";
	// Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);

	// return query.getResultList();
	// }

	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		where_condition_normal(buildingSearchBuilder, sql);
		where_condition_special(sql, buildingSearchBuilder);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
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
							sql.append(" AND b.numberOfBasement = ").append(numberOfBasement);
						} else if (key.equalsIgnoreCase("staffId")) {
							Long staffId = Long.parseLong(value);
							sql.append(" AND assignmentbuilding.staffId = ").append(staffId);
						} else if (key.startsWith("rentPrice")) {
							Integer rentPrice = Integer.parseInt(value);
							if (key.endsWith("From")) {
								sql.append(" AND b.rentPrice >= ").append(rentPrice);
							} else {
								sql.append(" AND b.rentPrice <= ").append(rentPrice);
							}
						} else if (NumberUtil.isNumber(value)) {
							if (key.equalsIgnoreCase("districtId")) {
								Long districtId = Long.parseLong(value);
								sql.append(" AND b.districtId = ").append(districtId);
							}
						} else if (!key.equalsIgnoreCase("typeCode")) {
							sql.append(" AND b.").append(key).append(" LIKE '%").append(value).append("%'");
						}
					}
				}
			}
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

	@Override
	public void DeleteById(long id) {

	}
}