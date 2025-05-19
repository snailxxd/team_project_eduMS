package queries;

import entities.Course;

import java.util.List;

public class CourseList {
    private int count;
    private List<Course> courses;

    public CourseList(List<Course> courses) {
        this.count = courses.size();
        this.courses = courses;
    }
    public int getCount() {return count;}
    public void setCount(int count) {this.count = count;}

    public List<Course> getCards() {return courses;}
    public void setCards(List<Course> courses) {this.courses = courses;}
}
