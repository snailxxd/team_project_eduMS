package com.infoa.educationms.DTO;

public class CaClassroomDTO {
    private Integer classroomId;
    private int capacity;
    private String building;

    // Constructors, getters, and setters
    public CaClassroomDTO() {}

    public CaClassroomDTO(Integer classroomId, int capacity, String building) {
        this.classroomId = classroomId;
        this.capacity = capacity;
        this.building = building;
    }

    // Getters and setters
    public Integer getClassroomId() { return classroomId; }
    public void setClassroomId(Integer classroomId) { this.classroomId = classroomId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
}