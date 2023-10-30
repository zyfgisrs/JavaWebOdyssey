package com.zhouyf.chapter5;

public class Course {
    private String courseName;

    private int score;

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getScore() {
        return score;
    }

    public Course(String courseName, int score){
        this.courseName = courseName;
        this.score = score;
    }
}
