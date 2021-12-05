package com.example.sidedeleteproject.contentprovider;

public class Student {
    private int stuId;
    private String stuName;
    private String stuGender;
    private int stuAge;


    public Student() {
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public Student(int stuId, String stuName, String stuGender, int stuAge) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.stuGender = stuGender;
        this.stuAge = stuAge;
    }


    public int getStuId() {
        return stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public String getStuGender() {
        return stuGender;
    }

    public int getStuAge() {
        return stuAge;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", stuGender='" + stuGender + '\'' +
                ", stuAge='" + stuAge + '\'' +
                '}';
    }
}
