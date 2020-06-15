package com.kevappsgaming.coursemanagementsqlite.utils;

public class CourseInformation {

    public String courseNumber;
    public String courseName;
    public String courseCredits;

    public CourseInformation(){

    }

    public CourseInformation(String courseNumber, String courseName, String courseCredits) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCredits() {
        return courseCredits;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCredits(String courseCredits) {
        this.courseCredits = courseCredits;
    }
}
