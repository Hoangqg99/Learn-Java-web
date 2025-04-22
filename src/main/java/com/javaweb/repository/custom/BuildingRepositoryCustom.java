package com.javaweb.repository.custom;

import java.util.List;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.builder.BuildingSearchBuilder;

public interface BuildingRepositoryCustom {
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
