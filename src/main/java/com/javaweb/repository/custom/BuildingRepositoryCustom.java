package com.javaweb.repository.custom;

import java.util.List;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.builder.BuildingSearchBuilder;

public interface BuildingRepositoryCustom {
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);

    void DeleteById(Long id);

    void deleteByIdIn(Long[] ids);

    List<BuildingEntity> findByNameContaining(String name);

    List<BuildingEntity> findByNameContainingAndStreet(String name, String street);

}
