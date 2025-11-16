package CourseManagement;

import UserManagement.Student;
import Database.DataInfo;
import Utilities.Validation;

public class Course implements DataInfo {

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private Lesson[] lessons;
    private Student[] students;

    public Course(String courseId, String title, String description, String instructorId) {
        setCourseId(courseId);
        setTitle(title);
        setDescription(description);
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

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getInstructorId() { return instructorId; }
}