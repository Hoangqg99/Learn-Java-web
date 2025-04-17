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
		// Làm việc trực tiếp với entity thay vì trực tiếp csdl
		// String sql = "FROM BuildingEntity b Where b.name Like '%building%' ";
		// Query query = entityManager.createQuery(sql, BuildingEntity.class);

		// SQL Native
		// Câu lệnh thuần sql
		String sql = "SELECT b.* FROM building b WHERE b.name like '%building%' ";
		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);

		return query.getResultList();
	}

	@Override
	public void DeleteById(long id) {

	}
}