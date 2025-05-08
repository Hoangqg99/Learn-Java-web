package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
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

	@Value("${dev.nguyen}")
	private String data;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(
			@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

	// Code Xuống BuildingRepositoryImpl tầng repository
	@PostMapping(value = "/api/building/")
	@Transactional
	public void creatBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictid());
		buildingEntity.setDistrict(districtEntity);
		entityManager.persist(buildingEntity); // insert
		System.out.println("vbri");
	}

	@PutMapping(value = "/api/building/")
	@Transactional
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		// buildingEntity.setId(1L);
		buildingEntity.setId(buildingRequestDTO.getId());
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictid());
		buildingEntity.setDistrict(districtEntity);
		entityManager.merge(buildingEntity); // update
		System.out.println("abc");
	}

	@DeleteMapping(value = "api/building/{id}")
	@Transactional
	public void deleteBuilding(@PathVariable Long[] id) {
		for (Long buildingId : id) {
			BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, buildingId);
			if (buildingEntity != null) {
				entityManager.remove(buildingEntity);
			}
		}
	}

}
