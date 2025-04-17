package com.javaweb.repository.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "building")
public class BuildingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // tự động tăng dần
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "numberofbasement")
	private Integer numberOfBasement;

	@Column(name = "ward")
	private String ward;

	@Column(name = "street")
	private String street;

	@ManyToOne
	@JoinColumn(name = "districtid")
	private DistrictEntity district;

	@Column(name = "floorarea")
	private Integer floorArea;

	@Column(name = "rentprice")
	private Integer rentPrice;

	@Column(name = "managername")
	private String managerName;

	@Column(name = "managerphonenumber")
	private String managerPhoneNumber;

	@OneToMany(mappedBy = "buildingId", fetch = FetchType.LAZY)
	private List<RentAreaEntity> rentArea;

	@ManyToMany(mappedBy = "buildingEntities")
	@Lazy
	private List<RentTypeEntity> rentTypeEntities;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}

	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DistrictEntity getDistrict() {
		return district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	public List<RentAreaEntity> getRentArea() {
		return rentArea;
	}

	public void setRentArea(List<RentAreaEntity> rentArea) {
		this.rentArea = rentArea;
	}

}
