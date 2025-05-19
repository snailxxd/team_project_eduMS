package entities;

public class Course {
    private int courseId;
    private String title;
    private String deptName;
    private int credits;
    private String introduction;
    private int capacity;

    public Course() {}

    public Course(int courseId, String title, String deptName, int credits, String introduction, int capacity) {
        this.courseId = courseId;
        this.title = title;
        this.deptName = deptName;
        this.credits = credits;
        this.introduction = introduction;
        this.capacity = capacity;
    }

    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", deptName='" + deptName + '\'' +
                ", credits=" + credits +
                ", introduction='" + introduction + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
