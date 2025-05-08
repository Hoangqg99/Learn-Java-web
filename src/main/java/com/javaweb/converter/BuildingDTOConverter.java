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

        if (item == null) {
            return null;
        }

        BuildingDTO building = modelMapper.map(item, BuildingDTO.class);

        // Safely set address
        String districtName = (item.getDistrict() != null && item.getDistrict().getName() != null)
                ? item.getDistrict().getName()
                : "";
        String address = String.join(",",
                item.getStreet() != null ? item.getStreet() : "",
                item.getWard() != null ? item.getWard() : "",
                districtName);
        building.setAddress(address);

        // Safely set rent area
        List<RentAreaEntity> rentAreas = item.getRentArea();
        if (rentAreas != null) {
            String areaResult = rentAreas.stream()
                    .map(it -> it.getValue().toString())
                    .collect(Collectors.joining(","));
            building.setRentArea(areaResult);
        } else {
            building.setRentArea("");
        }

        return building;
    }
}
