package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
    private String name;
    private long floorArea;
    private String ward;
    private String street;
    private String districtcode;
    private Integer numberOfFloors;
    private List<String> typeCode = new ArrayList<>();
    private String managerName;
    private String managerPhoneNumber;
    private Long rentPriceForm;
    private Long rentPriceTo;
    private Long areaFrom;
    private Long areaTo;
    private long staffId;

    public BuildingSearchBuilder(Builder builder) {
        this.name = builder.name;
        this.floorArea = builder.floorArea;
        this.ward = builder.ward;
        this.street = builder.street;
        this.districtcode = builder.districtcode;
        this.numberOfFloors = builder.numberOfFloors;
        this.typeCode = builder.typeCode;
        this.managerName = builder.managerName;
        this.managerPhoneNumber = builder.managerPhoneNumber;
        this.rentPriceForm = builder.rentPriceForm;
        this.rentPriceTo = builder.rentPriceTo;
        this.areaFrom = builder.areaFrom;
        this.areaTo = builder.areaTo;
        this.staffId = builder.staffId;
    }

    public String getName() {
        return name;
    }

    public long getFloorArea() {
        return floorArea;
    }

    public String getWard() {
        return ward;
    }

    public String getStreet() {
        return street;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public List<String> getTypeCode() {
        return typeCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public Long getRentPriceForm() {
        return rentPriceForm;
    }

    public Long getRentPriceTo() {
        return rentPriceTo;
    }

    public Long getAreaFrom() {
        return areaFrom;
    }

    public Long getAreaTo() {
        return areaTo;
    }

    public long getStaffId() {
        return staffId;
    }

    public static class Builder {
        private String name;
        private long floorArea;
        private String ward;
        private String street;
        private String districtcode;
        private Integer numberOfFloors;
        private List<String> typeCode = new ArrayList<>();
        private String managerName;
        private String managerPhoneNumber;
        private Long rentPriceForm;
        private Long rentPriceTo;
        private Long areaFrom;
        private Long areaTo;
        private long staffId;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFloorArea(long floorArea) {
            this.floorArea = floorArea;
            return this;
        }

        public Builder setWard(String ward) {
            this.ward = ward;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setDistrictcode(String districtcode) {
            this.districtcode = districtcode;
            return this;
        }

        public Builder setNumberOfFloors(Integer numberOfFloors) {
            this.numberOfFloors = numberOfFloors;
            return this;
        }

        public Builder setTypeCode(List<String> typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public Builder setManagerName(String managerName) {
            this.managerName = managerName;
            return this;
        }

        public Builder setManagerPhoneNumber(String managerPhoneNumber) {
            this.managerPhoneNumber = managerPhoneNumber;
            return this;
        }

        public Builder setRentPriceForm(Long rentPriceForm) {
            this.rentPriceForm = rentPriceForm;
            return this;
        }

        public Builder setRentPriceTo(Long rentPriceTo) {
            this.rentPriceTo = rentPriceTo;
            return this;
        }

        public Builder setAreaFrom(Long areaFrom) {
            this.areaFrom = areaFrom;
            return this;
        }

        public Builder setAreaTo(Long areaTo) {
            this.areaTo = areaTo;
            return this;
        }

        public Builder setStaffId(long staffId) {
            this.staffId = staffId;
            return this;
        }

        public BuildingSearchBuilder build() {
            return new BuildingSearchBuilder(this);
        }
    }

}
