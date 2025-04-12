package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public BuildingDTO toBuildingDTO(BuildingEntity item) {
        BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
        building.setName(item.getName());
        // DistrictEntity districtEntity =
        // districtRepository.findNameById(item.getDistrictId()); 1
        // DistrictEntity districtEntity = item.getDistrict(); 2
        building.setAddress(item.getStreet() + "," + item.getWard() + "," + item.getDistrict().getName());
        List<RentAreaEntity> rentAreas = item.getRentArea();
        // List<RentAreaEntity> rentAreas =
        // rentAreaRepository.getValueByBuildingId(item.getId());
        String areaResult = rentAreas.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
        building.setRentArea(areaResult);

        return building;
    }

}
