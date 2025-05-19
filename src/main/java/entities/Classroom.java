package entities;

public class Classroom {
    private String campus;
    private int classroomId;
    private int roomNumber;
    private int capacity;
    private String building;

    public Classroom() {};

    public Classroom(String campus, int classroomId, int roomNumber, int capacity, String building) {
        this.campus = campus;
        this.classroomId = classroomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.building = building;
    }

    public String toString() {
        return "Classroom{" +
                "campus='" + campus + '\'' +
                ", classroomId=" + classroomId +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", building='" + building + '\'' +
                '}';
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}