package com.javaweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository

// Có hai file impl BuildingRepository file này + file JDBCRepositoryImpl
// Câu lệnh ưu tiên chọn giữa hai file
@Primary

public class BuildingRepositoryImpl implements BuildingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {

		// JPQL : JPA Query Language
		// String sql = "FROM BuildingEntity b Where b.id = 1 ";
		// Query query = entityManager.createQuery(sql, BuildingEntity.class);
		// return query.getResultList();

		// SQL Native
		String sql = "SELECT b.* FROM building b WHERE b.name like '%building%' ";
		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
		return query.getResultList();
	}

	@Override
	public void DeleteById(long id) {

	}
}

// StringBuilder sql = new StringBuilder("SELECT b.* FROM building b WHERE
// 1=1");

// if (buildingSearchBuilder != null) {
// if (buildingSearchBuilder.getName() != null) {
// sql.append(" AND b.name LIKE :name");
// }
// if (buildingSearchBuilder.getDistrictId() != null) {
// sql.append(" AND b.districtid = :districtId");
// }
// if (buildingSearchBuilder.getFloorArea() != null) {
// sql.append(" AND b.floorarea = :floorArea");
// }
// if (buildingSearchBuilder.getRentPriceForm() != null) {
// sql.append(" AND b.rentprice >= :rentPriceFrom");
// }
// if (buildingSearchBuilder.getRentPriceTo() != null) {
// sql.append(" AND b.rentprice <= :rentPriceTo");
// }
// }

// Query query = entityManager.createNativeQuery(sql.toString(),
// BuildingEntity.class);

// if (buildingSearchBuilder != null) {
// if (buildingSearchBuilder.getName() != null) {
// query.setParameter("name", "%" + buildingSearchBuilder.getName() + "%");
// }
// if (buildingSearchBuilder.getDistrictId() != null) {
// query.setParameter("districtId", buildingSearchBuilder.getDistrictId());
// }
// if (buildingSearchBuilder.getFloorArea() != null) {
// query.setParameter("floorArea", buildingSearchBuilder.getFloorArea());
// }
// if (buildingSearchBuilder.getRentPriceForm() != null) {
// query.setParameter("rentPriceFrom",
// buildingSearchBuilder.getRentPriceForm());
// }
// if (buildingSearchBuilder.getRentPriceTo() != null) {
// query.setParameter("rentPriceTo", buildingSearchBuilder.getRentPriceTo());
// }
// }

// return query.getResultList();