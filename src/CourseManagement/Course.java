package CourseManagement;

import UserManagement.Student;
import Database.DataInfo;
import Utilities.Validation;
import java.util.*;

public class Course implements DataInfo {

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons;
    private List<Student> students;

    public Course(String courseId, String title, String description, String instructorId, Lesson[] lessons) {
        this.lessons = new ArrayList<>();
        students = new ArrayList<>();
        setCourseId(courseId);
        setTitle(title);
        setDescription(description);
        setInstructorId(instructorId);
    }
    public Course(String courseId, String title, String instructorId, Lesson[] lessons) {
        this.lessons = new ArrayList<>();
        students = new ArrayList<>();
        setCourseId(courseId);
        setTitle(title);
        setDescription("");
        setInstructorId(instructorId);
    }


    @Override
    public String getSearchKey() { return courseId; }


    public void setCourseId(String courseId) {
        if(!Validation.isValidString(courseId)) {
            throw new IllegalArgumentException("Invalid Course ID");
        }

        this.courseId = courseId;
    }

    public void setTitle(String title) {
        if(!Validation.isValidString(title)) {
            throw new IllegalArgumentException("Invalid Title");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if(!Validation.isValidString(description)) {
            this.description = "";
            return;
        }
        this.description = description;
    }

    public void setInstructorId(String instructorId) {
        if(!Validation.isValidString(instructorId)) {
            throw new IllegalArgumentException("Invalid Instructor ID");
        }
        this.instructorId = instructorId;
    }

    public void addLesson(Lesson lesson) {
        if(lesson == null) {
            return;
        }

        lessons.add(lesson);
    }

    public void addStudent(Student student) {
        if(student == null) {
            return;
        }

        students.add(student);
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getInstructorId() { return instructorId; }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
        // I found out that ,clone() will not copy inside elements
        // so after doing research, you can actually send a List in an immutable form
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public boolean searchStudent(String key){
        for (int i = 0; i < students.size(); i++) {
            if (key != null && key.equals(students.get(i).getSearchKey()))
                return true;
        }
        return false;
    }

    public boolean searchLesson(String key){
        for (int i = 0; i < lessons.size(); i++) {
            if (key != null && key.equals(lessons.get(i).getSearchKey()))
                return true;
        }
        return false;
    }
}