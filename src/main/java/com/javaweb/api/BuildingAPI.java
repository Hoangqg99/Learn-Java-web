package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@PropertySource("classpath:application.properties")

public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private BuildingRepository buildingRepository;

	@Value("${dev.nguyen}")
	private String data;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value = "/api/building/")
	@Transactional
	public List<BuildingDTO> getBuilding(
			@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

	@GetMapping(value = "/api/building/name/{name}")
	@Transactional
	public BuildingDTO getBuildingByName(@PathVariable String name) {
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> buildings = buildingRepository.findByNameContaining(name);
		if (!buildings.isEmpty()) {
			BuildingEntity building = buildings.get(0);
			result.setName(building.getName());
			result.setNumberOfBasement(building.getNumberOfBasement());
			result.setManagerName(building.getManagerName());
			result.setManagerPhoneNumber(building.getManagerPhoneNumber());
			result.setFloorArea(building.getFloorArea());
		}
		return result;
	}

	@GetMapping(value = "/api/building/name/{name}/{street}")
	@Transactional
	public BuildingDTO getBuildingByName(@PathVariable String name, @PathVariable String street) {
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> buildingstreet = buildingRepository.findByNameContainingAndStreet(name, street);
		if (!buildingstreet.isEmpty()) {
			BuildingEntity building = buildingstreet.get(0);
			result.setName(building.getName());
			result.setNumberOfBasement(building.getNumberOfBasement());
			result.setManagerName(building.getManagerName());
			result.setManagerPhoneNumber(building.getManagerPhoneNumber());
			result.setFloorArea(building.getFloorArea());
		}
		return result;
	}

	@GetMapping(value = "/api/building/id/{id}")
	public BuildingDTO getBuildingById(@PathVariable Long id) {
		BuildingEntity building = buildingRepository.findById(id)
				.get();

		// Map BuildingEntity to BuildingDTO
		BuildingDTO result = new BuildingDTO();
		result.setName(building.getName());
		result.setNumberOfBasement(building.getNumberOfBasement());
		// result.setAddress(building.getAddress());
		result.setManagerName(building.getManagerName());
		result.setManagerPhoneNumber(building.getManagerPhoneNumber());
		result.setFloorArea(building.getFloorArea());
		// result.setRentArea(building.getRentArea());
		// result.setBrokerageFee(building.getBrokerageFee());
		// result.setServiceFee(building.getServiceFee());
		// result.setRentPrice(building.getRentPrice());
		// result.setFreeSpace(building.getFreeSpace());

		return result;
	}

	// Code Xuống BuildingRepositoryImpl tầng repository
	@PostMapping(value = "/api/building/")
	@Transactional
	public ResponseEntity<?> createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictid());
		buildingEntity.setDistrict(districtEntity);
		buildingRepository.save(buildingEntity);
		return ResponseEntity.ok("Building created successfully");
	}

	@PutMapping(value = "/api/building/")
	@Transactional
	public ResponseEntity<?> updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {

		BuildingEntity buildingEntity = buildingRepository.findById(buildingRequestDTO.getId())
				.orElseThrow(
						() -> new RuntimeException("Building not found with id: " +
								buildingRequestDTO.getId()));
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		buildingRepository.save(buildingEntity); // sửa theo id
		return ResponseEntity.ok("Building updated successfully");
	}

	@DeleteMapping(value = "api/building/{ids}")
	@Transactional
	public void deleteBuilding(@PathVariable Long[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}

}
