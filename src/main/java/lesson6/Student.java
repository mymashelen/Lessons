package lesson6;

import java.util.*;

public class Student {
    private String name;
    private String group;
    private int course;
    private Map<String, Integer> grades;
    public Student() {
    }

    public Student(String name, String group, int course) {
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = new HashMap<>();
    }

    public void addGrade(String name, int grade) {
        this.grades.put(name, grade);
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public String getName() {
        return name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public double calculateAverageGrade(){
        if(this.grades.isEmpty()){return 0;}
        int sum = 0;
        for (int grade: grades.values()){
            sum += grade;
        }
        return (double) sum/grades.size();
    }
}
