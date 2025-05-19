package queries;

import entities.Course;

public class CourseQueryCondition {
    private String name;
    private String courseID;
    private String term;
    private String department;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCourseID() { return courseID; }

    public void setCourseID(String courseID) { this.courseID = courseID; }

    public String Term() { return term; }

    public void setTerm(String term) { this.term = term; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }
}
